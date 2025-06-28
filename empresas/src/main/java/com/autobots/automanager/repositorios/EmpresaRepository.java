
package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Empresa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
  @Query("SELECT e FROM Empresa e JOIN e.usuarios u WHERE u.id = :usuarioId")
  Optional<Empresa> findByUsuariosId(@Param("usuarioId") Long usuarioId);
}
