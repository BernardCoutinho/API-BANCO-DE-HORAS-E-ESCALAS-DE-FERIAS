package com.serratec.projeto.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.serratec.projeto.model.Nivel;


public class AlterarUsuarioDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -672174063424967326L;

	private Nivel nivel;
	private String email;
	private Long equipe;
	private String nome;
	private String password;
	private LocalDate dataPodeIniciarferias;
	private LocalDate dataDeveIniciarferias;
	public LocalDate dataVencimento;
	public LocalDate dataContratacao;
	private String fotoBase64;
	
	

	public String getFotoBase64() {
		return fotoBase64;
	}

	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public LocalDate getDataPodeIniciarferias() {
		return dataPodeIniciarferias;
	}

	public void setDataPodeIniciarferias(LocalDate dataPodeIniciarferias) {
		this.dataPodeIniciarferias = dataPodeIniciarferias;
	}

	public LocalDate getDataDeveIniciarferias() {
		return dataDeveIniciarferias;
	}

	public void setDataDeveIniciarferias(LocalDate dataDeveIniciarferias) {
		this.dataDeveIniciarferias = dataDeveIniciarferias;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getEquipe() {
		return equipe;
	}

	public void setEquipe(Long equipe) {
		this.equipe = equipe;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(LocalDate dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

}
