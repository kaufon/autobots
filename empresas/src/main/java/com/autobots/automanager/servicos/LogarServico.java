package com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager; // IMPORT THIS!
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // IMPORT THIS!
import org.springframework.security.core.AuthenticationException; // IMPORT THIS!
import org.springframework.stereotype.Service;

import com.autobots.automanager.controles.AutenticacaoControlador.Credencial;
import com.autobots.automanager.excecoes.AutenticacaoException;
// import com.autobots.automanager.providers.AutenticacaoProvedor; // REMOVE THIS IMPORT
import com.autobots.automanager.providers.JwtProvedor;

@Service
public class LogarServico {

  // Inject Spring Security's AuthenticationManager
  private final AuthenticationManager authenticationManager;

  @Autowired
  private JwtProvedor jwtProvedor;

  // Use constructor injection for required dependencies
  public LogarServico(AuthenticationManager authenticationManager, JwtProvedor jwtProvedor) {
    this.authenticationManager = authenticationManager;
    this.jwtProvedor = jwtProvedor;
  }

  public String login(Credencial credencial) {
    try {
      // This is the correct way to trigger authentication
      // The AuthenticationManager will use the configured AuthenticationProviders
      // (e.g., DaoAuthenticationProvider with your UserDetailsService)
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(credencial.getNomeUsuario(), credencial.getSenha()));

      // If authentication succeeds (no exception is thrown), generate the token
      var token = jwtProvedor.gerarToken(credencial.getNomeUsuario());
      return token;

    } catch (AuthenticationException e) {
      // Catch specific authentication failures from Spring Security
      throw new AutenticacaoException("Crendecias invalidas");
    } catch (Exception e) {
      // Catch any other unexpected errors
      throw new AutenticacaoException("Credencias invalidas");
    }
  }
}
