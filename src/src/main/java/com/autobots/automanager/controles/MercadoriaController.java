package com.autobots.automanager.controles;

import java.util.List;

import com.autobots.automanager.entidades.Mercadoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaController {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${mercadoria.service.url}")
  private String mercadoriaServiceUrl;

  private HttpHeaders createAuthHeaders(String authorizationHeader) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authorizationHeader);
    return headers;
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{empresaID}/listar")
  public ResponseEntity<List<Mercadoria>> listarMercadorias(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID) {
    String url = mercadoriaServiceUrl + "/mercadoria/{empresaID}/listar";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<List<Mercadoria>> resposta = restTemplate.exchange(
          url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Mercadoria>>() {
          }, empresaID);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{empresaID}/{id}")
  public ResponseEntity<Mercadoria> obterMercadoria(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID, @PathVariable Long id) {
    String url = mercadoriaServiceUrl + "/mercadoria/{empresaID}/{id}";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<Mercadoria> resposta = restTemplate.exchange(
          url, HttpMethod.GET, requestEntity, Mercadoria.class, empresaID, id);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PostMapping("/{empresaID}/cadastro")
  public ResponseEntity<?> criar(@RequestHeader("Authorization") String authorizationHeader,
      @RequestBody Mercadoria mercadoria, @PathVariable Long empresaID) {
    String url = mercadoriaServiceUrl + "/mercadoria/{empresaID}/cadastro";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Mercadoria> requestEntity = new HttpEntity<>(mercadoria, headers);

      ResponseEntity<Void> resposta = restTemplate.exchange(
          url, HttpMethod.POST, requestEntity, Void.class, empresaID);

      return new ResponseEntity<>(resposta.getStatusCode());
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PutMapping("/{empresaID}/atualizar/{id}")
  public ResponseEntity<Mercadoria> atualizar(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID, @PathVariable Long id, @RequestBody Mercadoria novaMercadoria) {
    String url = mercadoriaServiceUrl + "/mercadoria/{empresaID}/atualizar/{id}";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Mercadoria> requestEntity = new HttpEntity<>(novaMercadoria, headers);

      ResponseEntity<Mercadoria> resposta = restTemplate.exchange(
          url, HttpMethod.PUT, requestEntity, Mercadoria.class, empresaID, id);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @DeleteMapping("/{empresaID}/deletar/{id}")
  public ResponseEntity<Void> deletar(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID, @PathVariable Long id) {
    String url = mercadoriaServiceUrl + "/mercadoria/{empresaID}/deletar/{id}";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<Void> resposta = restTemplate.exchange(
          url, HttpMethod.DELETE, requestEntity, Void.class, empresaID, id);

      return new ResponseEntity<>(resposta.getStatusCode());
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }
}
