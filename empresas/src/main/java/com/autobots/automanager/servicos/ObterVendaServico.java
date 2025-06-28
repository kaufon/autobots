package com.autobots.automanager.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.excecoes.SemAutorizacaoException;
import com.autobots.automanager.providers.AutenticacaoProvedor;
import com.autobots.automanager.repositorios.VendaRepository;

@Service
public class ObterVendaServico {
  @Autowired
  private VendaRepository vendaRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public Venda obterVenda(Long id) {
    Optional<Venda> venda = vendaRepositorio.findById(id);
    if (venda.isEmpty()) {
      throw new RuntimeException("Venda não encontrada");
    }

    var perfil = autenticacaoProvedor.getPerfil();
    switch (perfil) {
      case CLIENTE:
        if (venda.get().getCliente().getId() != autenticacaoProvedor.getUsuario().getId()) {
          throw new SemAutorizacaoException();
        }
        break;
      case VENDEDOR:
        if (venda.get().getVendedor().getId() != autenticacaoProvedor.getUsuario().getId()) {
          throw new SemAutorizacaoException();
        }
        break;
      default:
        break;
    }

    return venda.get();
  }
}
