// package com.autobots.automanager.servicos;
//
// import java.util.Set;
//
// import org.springframework.hateoas.Link;
// import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
// import org.springframework.stereotype.Service;
//
// import com.autobots.automanager.controles.VeiculoController;
// import com.autobots.automanager.entidades.Veiculo;
//
//
// @Service
// public class AdicionarLinkVeiculoServico implements AdicionarLinkServico<Veiculo> {
//
//   @Override
//   public void adicionarLink(Set<Veiculo> veiculos) {
//     for (Veiculo veiculo : veiculos) {
//       adicionarLink(veiculo);
//     }
//   }
//
//   @Override
//   public void adicionarLink(Veiculo veiculo) {
//     Link linkObter = WebMvcLinkBuilder
//         .linkTo(WebMvcLinkBuilder
//             .methodOn(VeiculoController.class)
//             .obterVeiculo(veiculo.getId()))
//         .withRel("obter veiculo");
//     Link linkObterTodos = WebMvcLinkBuilder
//         .linkTo(WebMvcLinkBuilder
//             .methodOn(VeiculoController.class)
//             .listarVeiculos())
//         .withRel("obter todos os veiculos");
//     Link linkCadastrar = WebMvcLinkBuilder
//         .linkTo(WebMvcLinkBuilder
//             .methodOn(VeiculoController.class)
//             .criarVeiculo(null))
//         .withRel("cadastrar veiculo");
//     Link linkExcluir = WebMvcLinkBuilder
//         .linkTo(WebMvcLinkBuilder
//             .methodOn(VeiculoController.class)
//             .deletarVeiculo(null))
//         .withRel("excluir veiculo");
//     veiculo.add(linkObter);
//     veiculo.add(linkObterTodos);
//     veiculo.add(linkCadastrar);
//     veiculo.add(linkExcluir);
//   }
// }
