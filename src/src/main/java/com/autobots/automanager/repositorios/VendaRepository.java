package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    boolean existsByIdentificacao(String identificacao);
}
