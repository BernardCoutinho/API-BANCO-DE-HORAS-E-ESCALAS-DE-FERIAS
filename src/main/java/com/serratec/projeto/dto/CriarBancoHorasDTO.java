package com.serratec.projeto.dto;

import java.io.Serializable;

public class CriarBancoHorasDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5432625611955805739L;

	private Long id_usuario;
	private Long saldo_minutos;

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Long getSaldo_minutos() {
		return saldo_minutos;
	}

	public void setSaldo_minutos(Long saldo_minutos) {
		this.saldo_minutos = saldo_minutos;
	}

	public CriarBancoHorasDTO(Long id_usuario, Long saldo_minutos) {
		super();
		this.id_usuario = id_usuario;
		this.saldo_minutos = saldo_minutos;
	}

	public CriarBancoHorasDTO() {
		super();

	}

}
