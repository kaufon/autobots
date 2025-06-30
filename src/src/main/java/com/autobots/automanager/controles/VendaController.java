package com.autobots.automanager.controles;

import java.util.List;

import com.autobots.automanager.entidades.Venda;

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
@RequestMapping("/venda")
public class VendaController {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${venda.service.url}")
  private String vendaServiceUrl;

  private HttpHeaders createAuthHeaders(String authorizationHeader) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authorizationHeader);
    return headers;
  }

  @GetMapping("{empresaID}/listar")
  public ResponseEntity<List<Venda>> listarVendas(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID) {
    String url = vendaServiceUrl + "/venda/{empresaID}/listar";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<List<Venda>> resposta = restTemplate.exchange(
          url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Venda>>() {
          }, empresaID);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("""
      hasRole('ADMIN') or
      hasRole('GERENTE') or
      (hasRole('VENDEDOR') and #venda.vendedor.id == authentication.principal.id)
      """)
  @PostMapping("{empresaID}/cadastro")
  public ResponseEntity<?> criar(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Venda venda,
      @PathVariable Long empresaID) {
    String url = vendaServiceUrl + "/venda/{empresaID}/cadastro";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Venda> requestEntity = new HttpEntity<>(venda, headers);

      ResponseEntity<Venda> resposta = restTemplate.exchange(
          url, HttpMethod.POST, requestEntity, Venda.class, empresaID);

      return ResponseEntity.status(resposta.getStatusCode()).body(resposta.getBody());
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Venda> obterVenda(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long id) {
    String url = vendaServiceUrl + "/venda/{id}";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<Venda> resposta = restTemplate.exchange(
          url, HttpMethod.GET, requestEntity, Venda.class, id);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PutMapping("/{empresaID}/atualizar/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
  public ResponseEntity<Venda> atualizar(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID, @PathVariable Long id, @RequestBody Venda novaVenda) {
    String url = vendaServiceUrl + "/venda/{empresaID}/atualizar/{id}";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Venda> requestEntity = new HttpEntity<>(novaVenda, headers);

      ResponseEntity<Venda> resposta = restTemplate.exchange(
          url, HttpMethod.PUT, requestEntity, Venda.class, empresaID, id);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
  @DeleteMapping("/{empresaID}/{id}")
  public ResponseEntity<Void> deletar(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID, @PathVariable Long id) {
    String url = vendaServiceUrl + "/venda/{empresaID}/{id}";
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
