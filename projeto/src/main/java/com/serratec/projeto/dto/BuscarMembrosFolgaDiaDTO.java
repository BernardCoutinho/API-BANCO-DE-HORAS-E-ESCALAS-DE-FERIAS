package com.serratec.projeto.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class BuscarMembrosFolgaDiaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9061771851440646251L;

	private Long id_equipe;
	private LocalDate dia;

	public Long getId_equipe() {
		return id_equipe;
	}

	public void setId_equipe(Long id_equipe) {
		this.id_equipe = id_equipe;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public BuscarMembrosFolgaDiaDTO(Long id_equipe, LocalDate dia) {
		super();
		this.id_equipe = id_equipe;
		this.dia = dia;
	}

}
