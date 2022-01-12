package com.serratec.projeto.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.serratec.projeto.model.RegistroCompensacao;

public class CriarRegistroCompensacaoDTO implements Serializable {

	private static final long serialVersionUID = -3349053831371097628L;

	private Long idUsuario;
	private LocalDate dataCompensacao;
	private LocalTime hrInicioCompensacao;
	private LocalTime hrTerminoCompensacao;

	public CriarRegistroCompensacaoDTO() {
		super();

	}

	public CriarRegistroCompensacaoDTO(RegistroCompensacao registro) {
		super();

		this.dataCompensacao = registro.getData();
		this.hrInicioCompensacao = registro.getHoraInicio();
		this.hrTerminoCompensacao = registro.getHoraTermino();

	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public LocalDate getDataCompensacao() {
		return dataCompensacao;
	}

	public void setDataCompensacao(LocalDate dataCompensacao) {
		this.dataCompensacao = dataCompensacao;
	}

	public LocalTime getHrInicioCompensacao() {
		return hrInicioCompensacao;
	}

	public void setHrInicioCompensacao(LocalTime hrInicioCompensacao) {
		this.hrInicioCompensacao = hrInicioCompensacao;
	}

	public LocalTime getHrTerminoCompensacao() {
		return hrTerminoCompensacao;
	}

	public void setHrTerminoCompensacao(LocalTime hrTerminoCompensacao) {
		this.hrTerminoCompensacao = hrTerminoCompensacao;
	}

}
