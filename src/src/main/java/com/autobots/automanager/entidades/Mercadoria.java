package com.autobots.automanager.entidades;

import java.util.Date;

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
public class Mercadoria extends RepresentationModel<Mercadoria> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date validade;
	@Column(nullable = false)
	private Date fabricao;
	@Column(nullable = false)
	private Date cadastro;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private long quantidade;
	@Column(nullable = false)
	private double valor;
	@Column()
	private String descricao;
}
