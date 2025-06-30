package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping
public class AutenticacaoControlador {

  @Autowired
  private RestTemplate restTemplate;

  @Data
  public static class Credencial {
    private String nomeUsuario;
    private String senha;
  }

  @Value("${empresa.service.url}")
  private String empresaServiceUrl;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Jwt {
    private String token;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Credencial credencial) {
    String url = empresaServiceUrl + "/autenticacao/login";

    try {
      ResponseEntity<Jwt> respostaMicroservico = restTemplate.postForEntity(
          url,
          credencial,
          Jwt.class);

      return ResponseEntity.ok(respostaMicroservico.getBody());

    } catch (RestClientException e) {
      return ResponseEntity
          .status(403) // 503 Service Unavailable (Serviço Indisponível)
          .body("Erro ao se comunicar com o serviço de autenticação: " + e.getMessage());
    }
  }
}
