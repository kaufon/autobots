package com.autobots.automanager.providers;

import java.time.LocalDate;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtProvedor {
  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.issuer}")
  private String issuer;

  public String gerarToken(String email) {
    var expirationDate = LocalDate.now().plusDays(7);
    var algorithm = Algorithm.HMAC256(this.secret);
    var jwt = JWT.create()
        .withIssuer(issuer)
        .withSubject(email)
        .withExpiresAt(expirationDate.atStartOfDay(ZoneOffset.of("-03:00")).toInstant())
        .sign(algorithm);
    return jwt;
  }

  public String validarToken(String token) {
    try {
      var algorithm = Algorithm.HMAC256(this.secret);
      var subject = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
      return subject.getSubject();
    } catch (Exception exception) {
      throw new BadCredentialsException("Token inválido");
    }
  }

}
