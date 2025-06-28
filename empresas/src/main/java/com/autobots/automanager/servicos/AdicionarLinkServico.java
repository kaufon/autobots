package com.autobots.automanager.servicos;

import java.util.Set;

public interface AdicionarLinkServico<T> {
  public void adicionarLink(Set<T> lista);

  public void adicionarLink(T objeto);
}
