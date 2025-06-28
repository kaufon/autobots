package com.autobots.automanager.excecoes;

public class SemAutorizacaoException extends RuntimeException {
  public SemAutorizacaoException() {
    super("Usuário não autorizado");
  }
}
