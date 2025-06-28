package com.autobots.automanager.entidades;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends RepresentationModel<Usuario> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column
  private String nomeSocial;

  @Enumerated(EnumType.STRING)
  private PerfilUsuario perfil;

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco_id", nullable = true)
  private Endereco endereco;

  @Column(nullable = false)
  private Boolean inativo;

  @Column(nullable = true)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate ultimoAcesso = LocalDate.now();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id")
  private List<Documento> documentos = new ArrayList<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id")
  private List<Telefone> telefones = new ArrayList<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id")
  private List<Email> emails = new ArrayList<>();

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Credencial credencial;

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  private List<Veiculo> veiculos = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  @JsonIgnore
  private List<Venda> vendas = new ArrayList<>();

}
