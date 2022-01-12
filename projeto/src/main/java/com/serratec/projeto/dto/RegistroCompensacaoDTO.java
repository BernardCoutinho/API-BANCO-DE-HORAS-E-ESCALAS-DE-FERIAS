package com.serratec.projeto.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.serratec.projeto.model.RegistroCompensacao;
import com.serratec.projeto.model.Usuario;

public class RegistroCompensacaoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7813754997477933515L;

	private Long idRegCompensacao;
	private Usuario usuario;
	private LocalDate data;
	private LocalTime horaInicio;
	private LocalTime horaTermino;
	private Long horaTotal;

	public RegistroCompensacaoDTO() {
		super();
	}

	public RegistroCompensacaoDTO(RegistroCompensacao registroCompensacao) {
		super();
		this.idRegCompensacao = registroCompensacao.getIdRegCompensacao();
		this.usuario = registroCompensacao.getUsuario();
		this.data = registroCompensacao.getData();
		this.horaInicio = registroCompensacao.getHoraInicio();
		this.horaTermino = registroCompensacao.getHoraTermino();
		this.horaTotal = registroCompensacao.getHoraTotal();
	}

	public Long getIdRegCompensacao() {
		return idRegCompensacao;
	}

	public void setIdRegCompensacao(Long idRegCompensacao) {
		this.idRegCompensacao = idRegCompensacao;
	}

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
