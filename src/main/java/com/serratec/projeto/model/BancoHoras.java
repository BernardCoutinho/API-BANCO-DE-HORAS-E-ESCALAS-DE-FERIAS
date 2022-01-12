package com.serratec.projeto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.serratec.projeto.dto.AlterarBancoHorasDTO;

@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Table
@Entity
public class BancoHoras implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -362847116337141897L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_banco")
	private Long id_banco;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "saldo_minutos")
	private Long saldo_minutos;

	public Long getId_banco() {
		return id_banco;
	}

	public void setId_banco(Long id_banco) {
		this.id_banco = id_banco;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getSaldo_minutos() {
		return saldo_minutos;
	}

	public void setSaldo_minutos(Long saldo_minutos) {
		this.saldo_minutos = saldo_minutos;
	}

	public BancoHoras() {
		super();

	}

	public BancoHoras(Long id_banco, Usuario usuario, Long saldo_minutos) {
		super();
		this.id_banco = id_banco;
		this.usuario = usuario;
		this.saldo_minutos = saldo_minutos;

	}

	public BancoHoras(AlterarBancoHorasDTO alterarBancoHorasDTO) {

	}

}
