package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.autobots.automanager.entidades.Servico;


public class AtualizaServicoServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  @Autowired
  private VerificaDoubleNuloServico verificaDoubleServico;

  public void atualizar(Servico servico, Servico atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.getNome())) {
        servico.setNome(atualizacao.getNome());
      }
      if (!verificaDoubleServico.verificar(atualizacao.getValor())) {
        servico.setValor(atualizacao.getValor());
      }
      if (!verificaStringServico.verificar(atualizacao.getDescricao())) {
        servico.setDescricao(atualizacao.getDescricao());
      }
    }
  }

  public void atualizar(List<Servico> servicos, List<Servico> atualizacoes) {
    for (Servico atualizacao : atualizacoes) {
      for (Servico servico : servicos) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == servico.getId()) {
            atualizar(servico, atualizacao);
          }
        }
      }
    }
  }
}
