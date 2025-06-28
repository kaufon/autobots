package com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.excecoes.AutenticacaoException;
import com.autobots.automanager.excecoes.SemAutorizacaoException;
import com.autobots.automanager.providers.AutenticacaoProvedor;
import com.autobots.automanager.repositorios.UsuarioRepository;

@Service
public class ObterUsuarioServico {
  @Autowired
  private UsuarioRepository usuarioRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public Usuario obterUsuario(Long id) {
    var usuario = usuarioRepositorio.findById(id);
    if (usuario.isEmpty()) {
      throw new RuntimeException("Usuário não encontrado");
    }

    var usuarioAutenticado = autenticacaoProvedor.getUsuario();
    if (usuarioAutenticado.getPerfil() == PerfilUsuario.CLIENTE) {
      if (usuarioAutenticado.getId() != usuario.get().getId()) {
        throw new AutenticacaoException("Usuário não autorizado a acessar este recurso");
      }
    }

    if (usuarioAutenticado.getPerfil() == PerfilUsuario.VENDEDOR
        && usuario.get().getPerfil() != PerfilUsuario.CLIENTE) {
      if (usuarioAutenticado.getId() != usuario.get().getId()) {
        throw new SemAutorizacaoException();
      }
    }

    if (usuarioAutenticado.getPerfil() == PerfilUsuario.GERENTE
        && usuario.get().getPerfil() == PerfilUsuario.ADMIN) {
      throw new SemAutorizacaoException();
    }

    return usuario.get();
  }
}
