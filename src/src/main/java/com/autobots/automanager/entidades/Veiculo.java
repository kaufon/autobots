package com.autobots.automanager.entidades;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(exclude = { "proprietario", "vendas" },callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo extends RepresentationModel<Veiculo> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private TipoVeiculo tipo;
  @Column(nullable = false)
  private String modelo;
  @Column(nullable = false)
  private String placa;

  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JsonIgnore
  private Usuario proprietario;
  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JsonIgnore
  private Set<Venda> vendas = new HashSet<>();
}
