package com.autobots.automanager.servicos;

import java.util.Set;

import com.autobots.automanager.entidades.Empresa;

import org.springframework.stereotype.Component;

@Component
public class AdicionarLinkEmpresaServico implements AdicionarLinkServico<Empresa> {

  @Override
  public void adicionarLink(Set<Empresa> clientes) {
    for (Empresa cliente : clientes) {
    }
  }

  @Override
  public void adicionarLink(Empresa cliente) {
  }
}
