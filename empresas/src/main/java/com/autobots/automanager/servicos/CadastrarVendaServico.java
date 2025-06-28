package com.autobots.automanager.servicos;


import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.providers.AutenticacaoProvedor;
import com.autobots.automanager.repositorios.EmpresaRepository;
import com.autobots.automanager.repositorios.VendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CadastrarVendaServico {
  @Autowired
  private EmpresaRepository empresaRepositorio;


  @Autowired
  private VendaRepository vendaRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public void cadastrarVenda(Venda venda,Long empresaID) {
    var empresa = empresaRepositorio.findById(empresaID).get();
    vendaRepositorio.save(venda);
    var perfil = autenticacaoProvedor.getPerfil();
    if (perfil == PerfilUsuario.VENDEDOR) {
      var usuario = autenticacaoProvedor.getUsuario();
      venda.setVendedor(usuario);
    }
    empresa.getVendas().add(venda);
    empresaRepositorio.save(empresa);
  }
}
