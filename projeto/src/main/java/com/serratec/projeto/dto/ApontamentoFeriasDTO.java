package com.serratec.projeto.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.serratec.projeto.model.ApontamentoFerias;
import com.serratec.projeto.model.Usuario;

public class ApontamentoFeriasDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4713959247551300537L;

	private Long id_apontamento;
	private LocalDate diaFolga;
	private Usuario usuario;

	public ApontamentoFeriasDTO() {
		super();
	}

	public ApontamentoFeriasDTO(ApontamentoFerias apontamentoFerias) {
		super();
		this.id_apontamento = apontamentoFerias.getId_apontamento();
		this.diaFolga = apontamentoFerias.getDiaFolga();
		this.usuario = apontamentoFerias.getUsuario();
	}

	public Long getId_apontamento() {
		return id_apontamento;
	}

	public void setId_apontamento(Long id_apontamento) {
		this.id_apontamento = id_apontamento;
	}

	public LocalDate getDiaFolga() {
		return diaFolga;
	}

	public void setDiaFolga(LocalDate diaFolga) {
		this.diaFolga = diaFolga;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
