package com.autobots.automanager.controles;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.autobots.automanager.servicos.LogarServico;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping
public class AutenticacaoControlador {
  @Autowired
  private LogarServico loginServico;

  @Data
  public static class Credencial {
    private String nomeUsuario;
    private String senha;
  }

  @Data
  @AllArgsConstructor
  public static class Jwt {
    private String token;
  }

  @PostMapping("/autenticacao/login")
  public ResponseEntity<Jwt> login(@RequestBody Credencial credencial) {
    var token = loginServico.login(credencial);
    var jwt = new Jwt(token);
    return ResponseEntity.ok(jwt);
  }
}
