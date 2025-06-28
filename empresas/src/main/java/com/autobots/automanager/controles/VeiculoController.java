// package com.autobots.automanager.controles;
//
// import com.autobots.automanager.entidades.Veiculo;
// import com.autobots.automanager.repositorios.VeiculoRepository;
// import com.autobots.automanager.servicos.AdicionarLinkVeiculoServico;
// import com.autobots.automanager.servicos.AtualizaVeiculoServico;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
//
// import java.util.HashSet;
// import java.util.List;
// import java.util.Optional;
//
// @RestController
// @RequestMapping("/veiculo")
// public class VeiculoController {
//
//   @Autowired
//   private VeiculoRepository veiculoRepository;
//
//   @Autowired
//   private AdicionarLinkVeiculoServico adicionarLink;
//
//   @Autowired
//   private AtualizaVeiculoServico atualizaVeiculoServico;
//
//   @PostMapping("/cadastro")
//   public ResponseEntity<Veiculo> criarVeiculo(@RequestBody Veiculo veiculo) {
//     Veiculo novoVeiculo = veiculoRepository.save(veiculo);
//     adicionarLink.adicionarLink(novoVeiculo);
//     return ResponseEntity.ok(novoVeiculo);
//   }
//
//   @GetMapping("/listar")
//   public ResponseEntity<List<Veiculo>> listarVeiculos() {
//     List<Veiculo> veiculos = veiculoRepository.findAll();
//     if (veiculos.isEmpty()) {
//       return ResponseEntity.noContent().build();
//     }
//     adicionarLink.adicionarLink(new HashSet<>(veiculos));
//     return ResponseEntity.ok(veiculos);
//   }
//
//   @GetMapping("/{id}")
//   public ResponseEntity<Veiculo> obterVeiculo(@PathVariable Long id) {
//     Optional<Veiculo> veiculo = veiculoRepository.findById(id);
//     if (veiculo.isEmpty()) {
//       return ResponseEntity.notFound().build();
//     }
//     adicionarLink.adicionarLink(veiculo.get());
//     return ResponseEntity.ok(veiculo.get());
//   }
//
//   @PutMapping("/atualizar/{id}")
//   public ResponseEntity<Veiculo> atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo dadosAtualizados) {
//     Optional<Veiculo> optionalVeiculo = veiculoRepository.findById(id);
//     if (optionalVeiculo.isEmpty()) {
//       return ResponseEntity.notFound().build();
//     }
//
//     Veiculo veiculo = optionalVeiculo.get();
//     atualizaVeiculoServico.atualizar(veiculo, dadosAtualizados);
//     Veiculo veiculoAtualizado = veiculoRepository.save(veiculo);
//     adicionarLink.adicionarLink(veiculoAtualizado);
//     return ResponseEntity.ok(veiculoAtualizado);
//   }
//
//   @DeleteMapping("/deletar/{id}")
//   public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id) {
//     Optional<Veiculo> veiculo = veiculoRepository.findById(id);
//     if (veiculo.isEmpty()) {
//       return ResponseEntity.notFound().build();
//     }
//
//     veiculoRepository.delete(veiculo.get());
//     return ResponseEntity.noContent().build();
//   }
// }
