package com.autobots.automanager.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@EqualsAndHashCode(callSuper = false)
public class Empresa extends RepresentationModel<Empresa> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String razaoSocial;

  @Column
  private String nomeFantasia;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Telefone> telefones = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private Endereco endereco;

  @Column(nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate cadastro;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Usuario> usuarios = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Mercadoria> mercadorias = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Servico> servicos = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Veiculo> veiculos = new ArrayList<>();

  @OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Venda> vendas = new ArrayList<>();

  public List<Venda> obterVendasPorCliente(Usuario cliente) {
    return vendas
        .stream()
        .filter(venda -> venda.getCliente().getId() == cliente.getId())
        .collect(Collectors.toList());
  }

  public List<Venda> obterVendasPorVendedor(Usuario vendedor) {
    return vendas
        .stream()
        .filter(venda -> venda.getVendedor().getId() == vendedor.getId())
        .collect(Collectors.toList());
  }

  public List<Usuario> obterUsuariosPorPerfil(PerfilUsuario perfil) {
    switch (perfil) {
      case ADMIN:
        return usuarios;
      case GERENTE:
        return usuarios
            .stream()
            .filter(usuario -> usuario.getPerfil() != PerfilUsuario.ADMIN)
                    .collect(Collectors.toList());
      case VENDEDOR:
        return usuarios
            .stream()
            .filter(usuario -> usuario.getPerfil() == PerfilUsuario.CLIENTE)
            .collect(Collectors.toList());
      default:
        return usuarios;
    }
  }
}
