package com.serratec.projeto.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.serratec.projeto.model.Nivel;
import com.serratec.projeto.model.Equipe;
import com.serratec.projeto.model.Usuario;

public class UsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8854328668515411143L;

	private Long idUsuario;
	private Equipe equipe;
	private Nivel nivel;
	private String nome;
	private String email;
	private LocalDate dataContratacao;
	private LocalDate dataVencimento;
	private LocalDate dataPodeIniciarFerias;
	private LocalDate dataDeveIniciarFerias;
	private String username;
	private String fotoBase64;

	public String getFotoBase64() {
		return fotoBase64;
	}

	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Usuario usuario) {
		super();
		this.idUsuario = usuario.getIdUsuario();
		this.equipe = usuario.getEquipe();
		this.nivel = usuario.getNivel();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.dataContratacao = usuario.getDataContratacao();
		this.dataVencimento = usuario.getDataVencimento();
		this.dataPodeIniciarFerias = usuario.getDataPodeIniciarFerias();
		this.dataDeveIniciarFerias = usuario.getDataDeveIniciarFerias();
		this.username = usuario.getUsername();
		this.fotoBase64 = usuario.getFotoBase64();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(LocalDate dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPodeIniciarFerias() {
		return dataPodeIniciarFerias;
	}

	public void setDataPodeIniciarFerias(LocalDate dataPodeIniciarFerias) {
		this.dataPodeIniciarFerias = dataPodeIniciarFerias;
	}

	public LocalDate getDataDeveIniciarFerias() {
		return dataDeveIniciarFerias;
	}

	public void setDataDeveIniciarFerias(LocalDate dataDeveIniciarFerias) {

	}

	
}
