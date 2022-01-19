package com.serratec.projeto.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class AlterarApontamentoFeriasDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8104767388433616676L;

	private LocalDate diaFolga;
	private Long idUsuario;

	public LocalDate getDiaFolga() {
		return diaFolga;
	}

	public void setDiaFolga(LocalDate diaFolga) {
		this.diaFolga = diaFolga;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

}
