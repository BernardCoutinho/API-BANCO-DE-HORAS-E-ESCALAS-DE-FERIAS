package com.serratec.projeto.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.serratec.projeto.model.Usuario;

public class AlterarRegistroCompensadoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2905720669323514464L;

	private Usuario usuario;
	private LocalDate data;
	private LocalTime horaInicio;
	private LocalTime horaTermino;
	private Long horaTotal;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraTermino() {
		return horaTermino;
	}

	public void setHoraTermino(LocalTime horaTermino) {
		this.horaTermino = horaTermino;
	}

	public Long getHoraTotal() {
		return horaTotal;
	}

	public void setHoraTotal(Long horaTotal) {
		this.horaTotal = horaTotal;
	}

}
