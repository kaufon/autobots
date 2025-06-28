package com.autobots.automanager.controles;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.EmpresaRepository;
import com.autobots.automanager.repositorios.ServicoRepository;
import com.autobots.automanager.servicos.AdicionarLinkServicoServico;

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
@RequestMapping("/servico")
public class ServicoController {

  @Autowired
  private ServicoRepository servicoRepository;

  @Autowired
    private AdicionarLinkServicoServico adicionarLink;
  
  @Autowired
    private EmpresaRepository empresRepository;

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PostMapping("/{empresaID}/cadastro")
    public ResponseEntity<Servico> criarServico(@RequestBody Servico servico, @PathVariable Long empresaID) {
    Servico novoServico = servicoRepository.save(servico);
    var empresa = empresRepository.findById(empresaID).get();
    empresa.getServicos().add(novoServico);
    empresRepository.save(empresa);
    adicionarLink.adicionarLink(novoServico);
    return ResponseEntity.ok(novoServico);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/listar")
  public ResponseEntity<List<Servico>> listarServicos() {
    List<Servico> servicos = servicoRepository.findAll();
    if (servicos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    adicionarLink.adicionarLink(new HashSet<>(servicos));
    return ResponseEntity.ok(servicos);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{id}")
  public ResponseEntity<Servico> obterServico(@PathVariable Long id) {
    Optional<Servico> servico = servicoRepository.findById(id);
    if (servico.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    adicionarLink.adicionarLink(servico.get());
    return ResponseEntity.ok(servico.get());
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PutMapping("/{empresaID}/atualizar/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long empresaID, @PathVariable Long id, @RequestBody Servico dadosAtualizados) {
    Optional<Servico> servicoOptional = servicoRepository.findById(id);
    if (servicoOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

      var empresa = empresRepository.findById(empresaID).get();
    
    Servico servico = servicoOptional.get();
    servico.setNome(dadosAtualizados.getNome());
    servico.setDescricao(dadosAtualizados.getDescricao());
    servico.setValor(dadosAtualizados.getValor());
    
    Servico atualizado = servicoRepository.save(servico);
    
      empresa.getServicos().remove(servico);
      empresa.getServicos().add(atualizado);
      empresRepository.save(empresa);
    
    adicionarLink.adicionarLink(atualizado);
    return ResponseEntity.ok(atualizado);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @DeleteMapping("/{empresaID}/deletar/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long empresaID, @PathVariable Long id) {
      Optional<Servico> servicoOptional = servicoRepository.findById(id);
      if (servicoOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

      var empresa = empresRepository.findById(empresaID).get();
    
      Servico servico = servicoOptional.get();
      servicoRepository.delete(servico);
      empresa.getServicos().remove(servico);
      empresRepository.save(empresa);
    
    return ResponseEntity.noContent().build();
  }
}
