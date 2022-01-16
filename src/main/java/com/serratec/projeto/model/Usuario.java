
package com.serratec.projeto.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.serratec.projeto.dto.AlterarUsuarioDTO;

@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Entity
@Table
public class Usuario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4261664564407516496L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_equipe")
	private Equipe equipe;

	@Enumerated(EnumType.STRING)
	private Nivel nivel;

	@Column
	private String nome;

	@Column
	private String username;

	@Column
	private String email;

	@Column
	private String password;
	
	@Column(columnDefinition="text", length=10485760)
	private String fotoBase64;

	@Column(name = "data_contratacao")
	private LocalDate dataContratacao;

	@Column(name = "data_vencimento_ferias")
	private LocalDate dataVencimento;

	@Column(name = "data_pode_iniciar_ferias")
	private LocalDate dataPodeIniciarFerias;

	@Column(name = "data_deve_iniciar_ferias")
	private LocalDate dataDeveIniciarFerias;

	@Max(value = 30)
	@Column(name = "qtdDiasFerias")
	private int qtdDiasFerias;

	public int getQtdDiasFerias() {
		return qtdDiasFerias;
	}

	public void setQtdDiasFerias(int qtdDiasFerias) {
		this.qtdDiasFerias = qtdDiasFerias;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Usuario() {
	}

	public Usuario(AlterarUsuarioDTO alterarUsuarioDTO) {
		// TODO Auto-generated constructor stub
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

	public String getFotoBase64() {
		return fotoBase64;
	}

	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		this.dataDeveIniciarFerias = dataDeveIniciarFerias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", equipe=" + equipe + ", nivel=" + nivel + ", nome=" + nome
				+ ", username=" + username + ", email=" + email + ", password=" + password + ", dataContratacao="
				+ dataContratacao + ", dataVencimento=" + dataVencimento + ", dataPodeIniciarFerias="
				+ dataPodeIniciarFerias + ", dataDeveIniciarferias=" + dataDeveIniciarFerias + ", qtdDiasFerias="
				+ qtdDiasFerias + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}
}
