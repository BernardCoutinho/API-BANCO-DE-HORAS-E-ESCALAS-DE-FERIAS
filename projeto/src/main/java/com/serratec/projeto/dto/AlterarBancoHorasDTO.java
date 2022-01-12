package com.serratec.projeto.dto;

import java.io.Serializable;

import com.serratec.projeto.model.Usuario;

public class AlterarBancoHorasDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7604730066358539479L;

	private Usuario usuario;
	private Long saldo_minutos;

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

}
