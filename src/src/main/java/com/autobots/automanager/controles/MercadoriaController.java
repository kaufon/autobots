package com.autobots.automanager.controles;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.EmpresaRepository;
import com.autobots.automanager.repositorios.MercadoriaRepository;
import com.autobots.automanager.servicos.AdicionarLinkMercadoriaServico;
import com.autobots.automanager.servicos.AtualizaMercadoriaServico;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaController {

  @Autowired
  private MercadoriaRepository mercadoriaRepository;

  @Autowired
  private AdicionarLinkMercadoriaServico adicionarLink;

  @Autowired
  private EmpresaRepository empresRepository;

  @Autowired
  private AtualizaMercadoriaServico atualizaMercadoriaServico;

  @PreAuthorize("hasRole('ADMIN') OR hasRole('GERENTE') OR hasRole('VENDEDOR')")
  @GetMapping("{empresaID}/listar")
    public ResponseEntity<List<Mercadoria>> listarMercadorias(@PathVariable Long empresaID) {
    List<Mercadoria> mercadorias = mercadoriaRepository.findAll();
    if (mercadorias.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    adicionarLink.adicionarLink(new HashSet<>(mercadorias));
    return ResponseEntity.ok(mercadorias);
  }

  @PreAuthorize("hasRole('ADMIN') OR hasRole('GERENTE') OR hasRole('VENDEDOR')")
  @GetMapping("/{empresaID}/{id}")
    public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable Long empresaID, @PathVariable Long id) {
    Optional<Mercadoria> mercadoria = mercadoriaRepository.findById(id);
    if (mercadoria.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    adicionarLink.adicionarLink(mercadoria.get());
    return ResponseEntity.ok(mercadoria.get());
  }

  @PreAuthorize("hasRole('ADMIN') OR hasRole('GERENTE')")
  @PostMapping("{empresaID}/cadastro")
  public ResponseEntity<?> criar(@RequestBody Mercadoria mercadoria,@PathVariable Long empresaID) {
    var empresa = empresRepository.findById(empresaID).get();
    mercadoriaRepository.save(mercadoria);
    empresa.getMercadorias().add(mercadoria);
    empresRepository.save(empresa);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN') OR hasRole('GERENTE')")
  @PutMapping("/{empresaID}/atualizar/{id}")
    public ResponseEntity<Mercadoria> atualizar(@PathVariable Long empresaID, @PathVariable Long id, @RequestBody Mercadoria novaMercadoria) {
    Optional<Mercadoria> mercadoriaOptional = mercadoriaRepository.findById(id);
    if (mercadoriaOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var empresa = empresRepository.findById(empresaID).get();
  
    Mercadoria mercadoria = mercadoriaOptional.get();

    atualizaMercadoriaServico.atualizar(mercadoria, novaMercadoria);

    Mercadoria atualizada = mercadoriaRepository.save(mercadoria);
  
    empresa.getMercadorias().remove(mercadoria);
    empresa.getMercadorias().add(atualizada);
    empresRepository.save(empresa);
  
    adicionarLink.adicionarLink(atualizada);
    return ResponseEntity.ok(atualizada);
  }

  @PreAuthorize("hasRole('ADMIN') OR hasRole('GERENTE')")
  @DeleteMapping("/{empresaID}/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long empresaID, @PathVariable Long id) {
    Optional<Mercadoria> mercadoriaOptional = mercadoriaRepository.findById(id);
    if (mercadoriaOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
  
    var empresa = empresRepository.findById(empresaID).get();
  
    Mercadoria mercadoria = mercadoriaOptional.get();
    mercadoriaRepository.delete(mercadoria);
    empresa.getMercadorias().remove(mercadoria);
    empresRepository.save(empresa);
  
    return ResponseEntity.noContent().build();
  }
}
