package com.autobots.automanager.controles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.providers.AutenticacaoProvedor;
import com.autobots.automanager.repositorios.EmpresaRepository;
import com.autobots.automanager.repositorios.UsuarioRepository;
import com.autobots.automanager.servicos.AdicionarLinkUsuarioServico;
import com.autobots.automanager.servicos.AtualizaUsuarioServico;
import com.autobots.automanager.servicos.ObterUsuarioServico;
import com.autobots.automanager.servicos.ObterUsuariosServicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private EmpresaRepository empresaRepository;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  @Autowired
  private AdicionarLinkUsuarioServico adicionarLink;

  @Autowired
  private AtualizaUsuarioServico atualizaUsuarioServico;

  @Autowired
  private ObterUsuarioServico obterUsuarioServico;

  @Autowired
  private ObterUsuariosServicos obterUsuariosServicos;

  @GetMapping("{empresaID}/listar")
  @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
  public ResponseEntity<List<Usuario>> listarUsuarios(@PathVariable("empresaID") Long empresaID) {
    var usuarios = obterUsuariosServicos.obterUsuarios(empresaID);
    adicionarLink.adicionarLink(new HashSet<>(usuarios));
    var listaUsuarios = new ArrayList<>(usuarios);
    return ResponseEntity.ok(listaUsuarios);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN','GERENTE','CLIENTE')")
  public ResponseEntity<Usuario> obterUsuario(@PathVariable Long id) {
    var usuario = obterUsuarioServico.obterUsuario(id);
    adicionarLink.adicionarLink(usuario);
    return ResponseEntity.ok(usuario);
  }

  @PreAuthorize("""
      hasRole('ADMIN') or
      (hasRole('GERENTE') and #usuario.perfil.name() != 'ADMIN') or
      (hasRole('VENDEDOR') and #usuario.perfil.name() == 'CLIENTE')
      """)
  @PostMapping("{empresaID}/cadastro")
  @Transactional
  public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario ,@PathVariable("empresaID") Long empresaID) {
    var empresa = empresaRepository.findById(empresaID).get();
    autenticacaoProvedor.registrar(usuario);
    usuarioRepository.save(usuario);
    empresa.getUsuarios().add(usuario);
    empresaRepository.save(empresa);
    return ResponseEntity.ok(usuario);
  }

  @PreAuthorize("""
      hasRole('ADMIN') or
      (hasRole('GERENTE') and #usuarioAtualizacao.perfil.name() != 'ADMIN') or
      (hasRole('VENDEDOR') and #usuarioAtualizacao.perfil.name() == 'CLIENTE')
      """)
  @PutMapping("/{empresaID}/atualizar/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long empresaID, @PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
    Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
    if (usuarioOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

      var empresa = empresaRepository.findById(empresaID).get();
  
    Usuario usuario = usuarioOptional.get();
    atualizaUsuarioServico.atualizar(usuario, usuarioAtualizado);
    Usuario usuarioSalvo = usuarioRepository.save(usuario);
  
      empresa.getUsuarios().remove(usuario);
      empresa.getUsuarios().add(usuarioSalvo);
      empresaRepository.save(empresa);
  
    adicionarLink.adicionarLink(usuarioSalvo);
    return ResponseEntity.ok(usuarioSalvo);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @DeleteMapping("/{empresaID}/deletar/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long empresaID, @PathVariable Long id) {
      Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
      if (usuarioOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

      var empresa = empresaRepository.findById(empresaID).get();
  
      Usuario usuario = usuarioOptional.get();
      usuarioRepository.delete(usuario);
      empresa.getUsuarios().remove(usuario);
      empresaRepository.save(empresa);
  
    return ResponseEntity.noContent().build();
  }
}
