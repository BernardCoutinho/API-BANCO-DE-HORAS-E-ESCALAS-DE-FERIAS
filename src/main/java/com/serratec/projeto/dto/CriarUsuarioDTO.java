
package com.serratec.projeto.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.serratec.projeto.model.Nivel;
import com.serratec.projeto.model.Usuario;

public class CriarUsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8094928243445171924L;

	private Long idEquipe;

	private Nivel nivel;

	@NotBlank
	private String nome;

	@NotBlank
	@Email
	@Size(max = 60)
	private String email;

	private String username;
	
	private String fotoBase64;

	private LocalDate dataContratacao;

	@NotBlank
	private String password;

	public CriarUsuarioDTO() {
		super();
	}

	public CriarUsuarioDTO(Usuario usuario) {
		this.nivel = usuario.getNivel();
		this.nome = usuario.getNome();
		this.username = usuario.getUsername();
		this.password = usuario.getPassword();
		this.email = usuario.getEmail();
		this.dataContratacao = usuario.getDataContratacao();
		this.fotoBase64 = usuario.getFotoBase64();

	}

	public String getFotoBase64() {
		return fotoBase64;
	}

	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}

	public Long getIdEquipe() {
		return idEquipe;
	}

	public void setIdEquipe(Long idEquipe) {
		this.idEquipe = idEquipe;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
