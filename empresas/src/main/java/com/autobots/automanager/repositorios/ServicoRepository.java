package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
