package com.autobots.automanager.servicos;

import java.util.Set;

import com.autobots.automanager.entidades.Servico;

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
  }
}
