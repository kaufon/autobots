package com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;


@Service
public class AtualizaUsuarioServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  @Autowired
  private AtualizaEnderecoServico atualizaEnderoServico;

  @Autowired
  private AtualizaDocumentoServico atualizaDocumentoServico;

  @Autowired
  private AtualizaTelefoneServico atualizaTelefoneServico;

  public void atualizar(Usuario cliente, Usuario clienteAtualizado) {
    atualizarDados(cliente, clienteAtualizado);
    atualizaEnderoServico.atualizar(cliente.getEndereco(), clienteAtualizado.getEndereco());
    atualizaDocumentoServico.atualizar(cliente.getDocumentos(), clienteAtualizado.getDocumentos());
    atualizaTelefoneServico.atualizar(cliente.getTelefones(), clienteAtualizado.getTelefones());
  }

  private void atualizarDados(Usuario cliente, Usuario clienteAtualizado) {
    if (!verificaStringServico.verificar(clienteAtualizado.getNome())) {
      cliente.setNome(clienteAtualizado.getNome());
    }
    if (!verificaStringServico.verificar(clienteAtualizado.getNomeSocial())) {
      cliente.setNomeSocial(clienteAtualizado.getNomeSocial());
    }
  }
}
