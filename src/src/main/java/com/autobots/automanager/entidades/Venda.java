package com.autobots.automanager.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "cliente", "vendedor", "veiculo" }, callSuper = false)
public class Venda extends RepresentationModel<Venda> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate cadastro;

  @Column(nullable = false, unique = true)
  private String identificacao;

  @ManyToOne(fetch = FetchType.EAGER)
  private Usuario cliente;

  @ManyToOne(fetch = FetchType.EAGER)
  private Usuario vendedor;

  @ManyToMany(fetch = FetchType.EAGER )
  private List<Mercadoria> mercadorias = new ArrayList<>();

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Servico> servicos = new ArrayList<>();

  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Veiculo veiculo;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonBackReference
  private Empresa empresa;
}
