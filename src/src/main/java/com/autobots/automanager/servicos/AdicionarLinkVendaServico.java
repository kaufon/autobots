package com.autobots.automanager.servicos;

import java.util.Set;

import com.autobots.automanager.controles.VendaController;
import com.autobots.automanager.entidades.Venda;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class AdicionarLinkVendaServico implements AdicionarLinkServico<Venda> {

  @Override
  public void adicionarLink(Set<Venda> clientes) {
    for (Venda cliente : clientes) {
      long id = cliente.getId();
    }
  }

  @Override
  public void adicionarLink(Venda cliente) {
  }
}
