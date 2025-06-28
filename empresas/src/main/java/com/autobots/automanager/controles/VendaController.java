package com.autobots.automanager.controles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.EmpresaRepository;
import com.autobots.automanager.repositorios.VendaRepository;
import com.autobots.automanager.servicos.AdicionarLinkVendaServico;
import com.autobots.automanager.servicos.AtualizaVendaServico;
import com.autobots.automanager.servicos.ObterVendaServico;
import org.springframework.http.HttpStatus;
import com.autobots.automanager.servicos.ObterVendasServico;

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
@RequestMapping("/venda")
public class VendaController {

  @Autowired
  private VendaRepository vendaRepository;

  @Autowired
  private AtualizaVendaServico atualizaVendaServico;

  @Autowired
  private AdicionarLinkVendaServico adicionarLink;

  @Autowired
  private ObterVendaServico obterVendaServico;

  @Autowired
  private ObterVendasServico obterVendasServico;


  @Autowired
  private EmpresaRepository empresaRepository;

  @GetMapping("{empresaID}/listar")
  public ResponseEntity<List<Venda>> listarVendas(@PathVariable("empresaID") Long empresaID) {
    var vendas = obterVendasServico.obterVendas(empresaID);
    adicionarLink.adicionarLink(new HashSet<>(vendas));
    var lista = new ArrayList<>(vendas);
    return ResponseEntity.ok(lista);
  }

  @PreAuthorize("""
      hasRole('ADMIN') or
      hasRole('GERENTE') or
      (hasRole('VENDEDOR') and #venda.vendedor.id == authentication.principal.usuario.id)
      """)
  @PostMapping("{empresaID}/cadastro")
    public ResponseEntity<?> criar(@RequestBody Venda venda,@PathVariable("empresaID") Long empresaID) {
      if (vendaRepository.existsByIdentificacao(venda.getIdentificacao())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Venda com identificacao já existe!");
      }
    var empresa = empresaRepository.findById(empresaID).get();
    venda.setEmpresa(empresa);
    vendaRepository.save(venda);
    empresa.getVendas().add(venda);
    empresaRepository.save(empresa);
    return ResponseEntity.ok(venda);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Venda> obterVenda(@PathVariable Long id) {
    var venda = obterVendaServico.obterVenda(id);
    adicionarLink.adicionarLink(venda);
    return ResponseEntity.ok(venda);
  }

  @PutMapping("/{empresaID}/atualizar/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<Venda> atualizar(@PathVariable Long empresaID, @PathVariable Long id, @RequestBody Venda novaVenda) {
    return vendaRepository.findById(id)
        .map(venda -> {
            var empresa = empresaRepository.findById(empresaID).get();
          atualizaVendaServico.atualizar(venda, novaVenda);
          Venda vendaAtualizada = vendaRepository.save(venda);
            empresa.getVendas().remove(venda);
            empresa.getVendas().add(vendaAtualizada);
            empresaRepository.save(empresa);
          adicionarLink.adicionarLink(vendaAtualizada);
          return ResponseEntity.ok(vendaAtualizada);
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @DeleteMapping("/{empresaID}/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long empresaID, @PathVariable Long id) {
    Optional<Venda> vendaOptional = vendaRepository.findById(id);
    if (vendaOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
      var empresa = empresaRepository.findById(empresaID).get();
      Venda venda = vendaOptional.get();
      vendaRepository.delete(venda);
      empresa.getVendas().remove(venda);
      empresaRepository.save(empresa);
    return ResponseEntity.noContent().build();
  }
}
