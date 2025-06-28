package com.autobots.automanager.servicos;

import java.util.Set;

import com.autobots.automanager.controles.ServicoController;
import com.autobots.automanager.entidades.Servico;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;


@Service
public class AdicionarLinkServicoServico implements AdicionarLinkServico<Servico> {
  @Override
  public void adicionarLink(Set<Servico> servicos) {
    for (Servico servico : servicos) {
      adicionarLink(servico);
    }
  }

  @Override
  public void adicionarLink(Servico servico) {
    var linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoController.class)
            .obterServico(servico.getId()))
        .withRel("obter servico");
    var linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoController.class)
            .listarServicos())
        .withRel("obter todos os servicos");
    servico.add(linkObter);
    servico.add(linkObterTodos);
  }
}
