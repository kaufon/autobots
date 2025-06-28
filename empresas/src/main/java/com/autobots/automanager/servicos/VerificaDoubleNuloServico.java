package com.autobots.automanager.servicos;

import org.springframework.stereotype.Service;

@Service
public class VerificaDoubleNuloServico {
  public boolean verificar(Double dado) {
    boolean nulo = true;
    if (!(dado == null)) {
      try {
        Double.parseDouble(Double.toString(dado));
        return true;
      } catch (NumberFormatException e) {
        return false;
      }
    }
    return nulo;
  }
}
