package com.autobots.automanager.entidades;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
public class Endereco extends RepresentationModel<Endereco> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String estado;
	@Column(nullable = false)
	private String cidade;
	@Column(nullable = false)
	private String bairro;
	@Column(nullable = false)
	private String rua;
	@Column(nullable = false)
	private String numero;
	@Column(nullable = false)
	private String codigoPostal;
	@Column()
	private String informacoesAdicionais;
}
