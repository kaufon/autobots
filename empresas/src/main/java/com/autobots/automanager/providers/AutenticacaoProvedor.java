package com.autobots.automanager.providers;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.excecoes.AutenticacaoException;
import com.autobots.automanager.seguranca.SegurancaUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AutenticacaoProvedor {
  @Autowired
  private AuthenticationManager authenticationManager;


  @Autowired
  private PasswordEncoder passwordEncoder;

  public boolean validarCredenciais(String email, String password) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
    try {
      var authentication = authenticationManager.authenticate(authenticationToken);
      return authentication.isAuthenticated();
    } catch (Exception e) {
      throw new AutenticacaoException("Credenciais inválidas");
    }
  }

  public Usuario registrar(Usuario usuario) {
    var credencial = usuario.getCredencial();
    var encryptedPassword = passwordEncoder.encode(credencial.getSenha());
    credencial.setSenha(encryptedPassword);
    usuario.setCredencial(credencial);
    return usuario;
  }

  public Usuario getUsuario() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof SegurancaUsuario segurancaUsuario) {
      return segurancaUsuario.getUsuario();
    }
    throw new AutenticacaoException("Usuário não autenticado");
  }

  public PerfilUsuario getPerfil() {
    var usuario = getUsuario();
    return usuario.getPerfil();
  }

  public Long getUsuarioId() {
    var usuario = getUsuario();
    return usuario.getId();
  }
}
