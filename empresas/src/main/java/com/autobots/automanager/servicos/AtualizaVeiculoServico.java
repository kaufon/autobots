package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Veiculo;


@Service
public class AtualizaVeiculoServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  public void atualizar(Veiculo veiculo, Veiculo atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.getTipo().toString())) {
        veiculo.setTipo(atualizacao.getTipo());
      }
      if (!verificaStringServico.verificar(atualizacao.getModelo())) {
        veiculo.setModelo(atualizacao.getModelo());
      }
      if (!verificaStringServico.verificar(atualizacao.getPlaca())) {
        veiculo.setPlaca(atualizacao.getPlaca());
      }
    }
  }

  public void atualizar(List<Veiculo> veiculos, List<Veiculo> atualizacoes) {
    for (Veiculo atualizacao : atualizacoes) {
      for (Veiculo veiculo : veiculos) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == veiculo.getId()) {
            atualizar(veiculo, atualizacao);
          }
        }
      }
    }
  }
}
