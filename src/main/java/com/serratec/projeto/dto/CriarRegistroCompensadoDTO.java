package com.serratec.projeto.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.serratec.projeto.model.RegistroCompensado;

public class CriarRegistroCompensadoDTO implements Serializable {

	private static final long serialVersionUID = -3349053831371097628L;

	private Long id_usuario;
	private LocalDate dataCompensado;
	private LocalTime hrInicioCompensado;
	private LocalTime hrTerminoCompensado;

	public CriarRegistroCompensadoDTO() {
		super();

	}

	public CriarRegistroCompensadoDTO(RegistroCompensado registro) {
		super();

		this.dataCompensado = registro.getData();
		this.hrInicioCompensado = registro.getHoraInicio();
		this.hrTerminoCompensado = registro.getHoraTermino();

	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public LocalDate getDataCompensado() {
		return dataCompensado;
	}

	public void setDataCompensado(LocalDate dataCompensado) {
		this.dataCompensado = dataCompensado;
	}

	public LocalTime getHrInicioCompensado() {
		return hrInicioCompensado;
	}

	public void setHrInicioCompensado(LocalTime hrInicioCompensado) {
		this.hrInicioCompensado = hrInicioCompensado;
	}

	public LocalTime getHrTerminoCompensado() {
		return hrTerminoCompensado;
	}

	public void setHrTerminoCompensado(LocalTime hrTerminoCompensado) {
		this.hrTerminoCompensado = hrTerminoCompensado;
	}

}
