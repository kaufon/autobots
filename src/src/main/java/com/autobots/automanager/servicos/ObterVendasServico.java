package com.autobots.automanager.servicos;

import java.util.List;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.providers.AutenticacaoProvedor;
import com.autobots.automanager.repositorios.EmpresaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObterVendasServico {

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  @Autowired
  private EmpresaRepository empresaRepositorio;

  public List<Venda> obterVendas(Long epresaId) {
    var perfil = autenticacaoProvedor.getPerfil();
    var empresa = empresaRepositorio.findById(epresaId).get();
    if (perfil == PerfilUsuario.CLIENTE) {
      var vendas = empresa.obterVendasPorCliente(autenticacaoProvedor.getUsuario());
      return vendas;
    }

    if (perfil == PerfilUsuario.VENDEDOR) {
      var vendas = empresa.obterVendasPorCliente(autenticacaoProvedor.getUsuario());
      return vendas;
    }

    return empresa.getVendas();
  }
}
