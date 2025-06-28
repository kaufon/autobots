package com.autobots.automanager.servicos;

import java.util.List;

import com.autobots.automanager.entidades.Documento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtualizaDocumentoServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  public void atualizar(Documento documento, Documento atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.getTipo().toString())) {
        documento.setTipo(atualizacao.getTipo());
      }
      if (!verificaStringServico.verificar(atualizacao.getNumero())) {
        documento.setNumero(atualizacao.getNumero());
      }
    }
  }

  public void atualizar(List<Documento> documentos, List<Documento> atualizacoes) {
    for (Documento atualizacao : atualizacoes) {
      for (Documento documento : documentos) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == documento.getId()) {
            atualizar(documento, atualizacao);
          }
        }
      }
    }
  }
}
