package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}
