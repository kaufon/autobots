package com.autobots.automanager.controles;

import java.util.List;

import com.autobots.automanager.entidades.Usuario;

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
@RequestMapping("/usuario")
public class UsuarioController {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${usuario.service.url}")
  private String usuarioServiceUrl;

  private HttpHeaders createAuthHeaders(String authorizationHeader) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authorizationHeader);
    return headers;
  }

  @GetMapping("{empresaID}/listar")
  @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
  public ResponseEntity<List<Usuario>> listarUsuarios(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID) {
    String url = usuarioServiceUrl + "/usuario/{empresaID}/listar";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<List<Usuario>> resposta = restTemplate.exchange(
          url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Usuario>>() {
          }, empresaID);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN','GERENTE','CLIENTE')")
  public ResponseEntity<Usuario> obterUsuario(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long id) {
    String url = usuarioServiceUrl + "/usuario/{id}";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<Usuario> resposta = restTemplate.exchange(
          url, HttpMethod.GET, requestEntity, Usuario.class, id);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("""
      hasRole('ADMIN') or
      (hasRole('GERENTE') and #usuario.perfil.name() != 'ADMIN') or
      (hasRole('VENDEDOR') and #usuario.perfil.name() == 'CLIENTE')
      """)
  @PostMapping("{empresaID}/cadastro")
  public ResponseEntity<Usuario> criarUsuario(@RequestHeader("Authorization") String authorizationHeader,
      @RequestBody Usuario usuario, @PathVariable Long empresaID) {
    String url = usuarioServiceUrl + "/usuario/{empresaID}/cadastro";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Usuario> requestEntity = new HttpEntity<>(usuario, headers);

      ResponseEntity<Usuario> resposta = restTemplate.exchange(
          url, HttpMethod.POST, requestEntity, Usuario.class, empresaID);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("""
      hasRole('ADMIN') or
      (hasRole('GERENTE') and #usuarioAtualizado.perfil.name() != 'ADMIN') or
      (hasRole('VENDEDOR') and #usuarioAtualizado.perfil.name() == 'CLIENTE')
      """)
  @PutMapping("/{empresaID}/atualizar/{id}")
  public ResponseEntity<Usuario> atualizarUsuario(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID, @PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
    String url = usuarioServiceUrl + "/usuario/{empresaID}/atualizar/{id}";
    try {
      HttpHeaders headers = createAuthHeaders(authorizationHeader);
      HttpEntity<Usuario> requestEntity = new HttpEntity<>(usuarioAtualizado, headers);

      ResponseEntity<Usuario> resposta = restTemplate.exchange(
          url, HttpMethod.PUT, requestEntity, Usuario.class, empresaID, id);

      return resposta;
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @DeleteMapping("/{empresaID}/deletar/{id}")
  public ResponseEntity<Void> deletarUsuario(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable Long empresaID, @PathVariable Long id) {
    String url = usuarioServiceUrl + "/usuario/{empresaID}/deletar/{id}";
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
