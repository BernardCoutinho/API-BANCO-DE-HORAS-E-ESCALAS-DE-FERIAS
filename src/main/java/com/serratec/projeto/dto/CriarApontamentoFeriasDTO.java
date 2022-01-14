package com.serratec.projeto.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.serratec.projeto.model.ApontamentoFerias;

public class CriarApontamentoFeriasDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4639646823267241330L;

	private Long id_usuario;
	private String nome;
	private LocalDate diaFolga;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public LocalDate getDiaFolga() {
		return diaFolga;
	}

	public void setDiaFolga(LocalDate diaFolga) {
		this.diaFolga = diaFolga;
	}

	public CriarApontamentoFeriasDTO() {
		super();

	}

	public CriarApontamentoFeriasDTO(Long id_usuario, LocalDate diaFolga, String nome) {
		super();
		this.id_usuario = id_usuario;
		this.diaFolga = diaFolga;
		this.nome = nome;
	}

	public CriarApontamentoFeriasDTO(ApontamentoFerias aponta) {
		super();
		this.id_usuario = aponta.getUsuario().getIdUsuario();
		this.nome = aponta.getUsuario().getNome();
		this.diaFolga = aponta.getDiaFolga();
	}
}
