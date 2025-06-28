

package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Documento;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
  Optional<Documento> findByNumero(String numero);
}
