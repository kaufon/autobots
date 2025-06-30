package com.autobots.automanager.controles;

import java.util.List;

import com.autobots.automanager.entidades.Servico;

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
@RequestMapping("/servico")
public class ServicoController {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${servico.service.url}")
  private String servicoServiceUrl;

  private HttpHeaders createAuthHeaders(String authorizationHeader) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authorizationHeader);
    return headers;
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PostMapping("/{empresaID}/cadastro")
  public ResponseEntity<Servico> criarServico(@RequestHeader("Authorization") String authorizationHeader,
      @RequestBody Servico servico, @PathVariable Long empresaID) {
    String url = servicoServiceUrl + "/servico/{empresaID}/cadastro";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Servico> requestEntity = new HttpEntity<>(servico, headers);

      ResponseEntity<Servico> resposta = restTemplate.exchange(
          url, HttpMethod.POST, requestEntity, Servico.class, empresaID);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/listar")
  public ResponseEntity<List<Servico>> listarServicos(@RequestHeader("Authorization") String authorizationHeader) {
    String url = servicoServiceUrl + "/servico/listar";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<List<Servico>> resposta = restTemplate.exchange(
          url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Servico>>() {
          });

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{id}")
  public ResponseEntity<Servico> obterServico(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long id) {
    String url = servicoServiceUrl + "/servico/{id}";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<Servico> resposta = restTemplate.exchange(
          url, HttpMethod.GET, requestEntity, Servico.class, id);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PutMapping("/{empresaID}/atualizar/{id}")
  public ResponseEntity<Servico> atualizarServico(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID, @PathVariable Long id, @RequestBody Servico dadosAtualizados) {
    String url = servicoServiceUrl + "/servico/{empresaID}/atualizar/{id}";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Servico> requestEntity = new HttpEntity<>(dadosAtualizados, headers);

      ResponseEntity<Servico> resposta = restTemplate.exchange(
          url, HttpMethod.PUT, requestEntity, Servico.class, empresaID, id);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @DeleteMapping("/{empresaID}/deletar/{id}")
  public ResponseEntity<Void> deletarServico(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID, @PathVariable Long id) {
    String url = servicoServiceUrl + "/servico/{empresaID}/deletar/{id}";
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
