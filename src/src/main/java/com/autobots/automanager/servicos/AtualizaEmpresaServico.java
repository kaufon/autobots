package com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;


@Service
public class AtualizaEmpresaServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  @Autowired
  private AtualizaEnderecoServico atualizaEnderoServico;

  @Autowired
  private AtualizaTelefoneServico atualizaTelefoneServico;

  public void atualizar(Empresa empresa, Empresa empresaAtualizado) {
    atualizarDados(empresa, empresaAtualizado);
    atualizaEnderoServico.atualizar(empresa.getEndereco(), empresaAtualizado.getEndereco());
    atualizaTelefoneServico.atualizar(empresa.getTelefones(), empresaAtualizado.getTelefones());
  }

  public void atualizarDados(Empresa empresa, Empresa atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.getRazaoSocial())) {
        empresa.setRazaoSocial(atualizacao.getRazaoSocial());
      }
      if (!verificaStringServico.verificar(atualizacao.getNomeFantasia())) {
        empresa.setNomeFantasia(atualizacao.getNomeFantasia());
      }
    }
  }
}
