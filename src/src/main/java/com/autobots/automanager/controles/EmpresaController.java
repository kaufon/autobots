package com.autobots.automanager.controles;

import java.util.List;

import com.autobots.automanager.entidades.Empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class EmpresaController {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${empresa.service.url}")
  private String empresaServiceUrl;

  private HttpHeaders createAuthHeaders(String authorizationHeader) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authorizationHeader);
    return headers;
  }

  @PostMapping("/empresa/cadastrar")
  public ResponseEntity<?> cadastrarEmpresa(@RequestHeader("Authorization") String authorizationHeader,
      @RequestBody Empresa empresa) {
    String url = empresaServiceUrl + "/empresa/cadastrar";
    try {
      System.out.println("auth header: " + authorizationHeader);
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      System.out.println(headers);
      HttpEntity<Empresa> requestEntity = new HttpEntity<>(empresa, headers);

      ResponseEntity<Void> resposta = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
      return new ResponseEntity<>(resposta.getStatusCode());
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }
  }

  @GetMapping("/empresa/listar")
  public ResponseEntity<List<Empresa>> obterEmpresas(@RequestHeader("Authorization") String authorizationHeader) {
    String url = empresaServiceUrl + "/empresa/listar";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      System.out.println("auth header: " + authorizationHeader);
      System.out.println("url: " + url);
      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
      System.out.println("requestEntity: " + requestEntity);

      ResponseEntity<List<Empresa>> resposta = restTemplate.exchange(
          url,
          HttpMethod.GET,
          requestEntity, // Enviando a requisição com o cabeçalho
          new ParameterizedTypeReference<List<Empresa>>() {
          });
      System.out.println("resposta: " + resposta);
      return resposta;
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      throw e;
    }
  }

  @GetMapping("/empresa/{id}")
  public ResponseEntity<Empresa> obterEmpresa(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable long id) {
    String url = empresaServiceUrl + "/empresa/" + id;
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<Empresa> resposta = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Empresa.class);
      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PutMapping("/empresa/atualizar")
  public ResponseEntity<?> atualizarEmpresa(@RequestHeader("Authorization") String authorizationHeader,
      @RequestBody Empresa empresaAtualizado) {
    String url = empresaServiceUrl + "/empresa/atualizar";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Empresa> requestEntity = new HttpEntity<>(empresaAtualizado, headers);

      ResponseEntity<Void> resposta = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
      return new ResponseEntity<>(resposta.getStatusCode());
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }
  }

  @DeleteMapping("/empresa/excluir")
  public ResponseEntity<?> excluirEmpresa(@RequestHeader("Authorization") String authorizationHeader,
      @RequestBody Empresa empresaParaExcluir) {
    String url = empresaServiceUrl + "/empresa/excluir";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Empresa> requestEntity = new HttpEntity<>(empresaParaExcluir, headers);

      ResponseEntity<Void> resposta = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);
      return new ResponseEntity<>(resposta.getStatusCode());
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }
  }
}
