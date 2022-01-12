package com.serratec.projeto.dto;

import java.io.Serializable;

import com.serratec.projeto.model.BancoHoras;
import com.serratec.projeto.model.Usuario;

public class BancoHorasDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7642545916809353437L;

	private Long id_banco;
	private Usuario usuario;
	private Long saldo_minutos;

	public BancoHorasDTO() {
		super();
	}

	public BancoHorasDTO(BancoHoras bancoHoras) {
		super();
		this.id_banco = bancoHoras.getId_banco();
		this.usuario = bancoHoras.getUsuario();
		this.saldo_minutos = bancoHoras.getSaldo_minutos();
	}

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

}
