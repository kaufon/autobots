package com.autobots.automanager.controles;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.EmpresaRepository;
import com.autobots.automanager.servicos.AdicionarLinkEmpresaServico;
import com.autobots.automanager.servicos.AtualizaEmpresaServico;


@RestController
public class EmpresaController {
  @Autowired
  private EmpresaRepository repositorio;

  @Autowired
  private AdicionarLinkEmpresaServico adicionaLinkEmpresaServico;

  @Autowired
  private AtualizaEmpresaServico atualizaEmpresaServico;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/empresa/cadastrar")
  public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
    repositorio.save(empresa);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/empresa/listar")
  public ResponseEntity<List<Empresa>> obterEmpresas() {
    List<Empresa> empresas = repositorio.findAll();
    if (empresas.isEmpty()) {
      ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkEmpresaServico.adicionarLink(new HashSet<>(empresas));
      ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(empresas, HttpStatus.OK);
      return resposta;
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/empresa/{id}")
  public ResponseEntity<Empresa> obterEmpresa(@PathVariable long id) {
    Optional<Empresa> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Empresa> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkEmpresaServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/empresa/atualizar")
  public ResponseEntity<?> atualizarEmpresa(@RequestBody Empresa empresaAtualizado) {
    Optional<Empresa> empresa = repositorio.findById(empresaAtualizado.getId());
    if (empresa.isPresent()) {
      atualizaEmpresaServico.atualizar(empresa.get(), empresaAtualizado);
      repositorio.save(empresa.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/empresa/excluir")
  public ResponseEntity<?> excluirEmpresa(@RequestBody Empresa exclusao) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    Optional<Empresa> empresa = repositorio.findById(exclusao.getId());
    if (empresa.isPresent()) {
      repositorio.delete(empresa.get());
      status = HttpStatus.OK;
    }
    return new ResponseEntity<>(status);
  }
}
