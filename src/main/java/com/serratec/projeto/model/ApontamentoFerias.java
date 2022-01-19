package com.serratec.projeto.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.serratec.projeto.dto.AlterarApontamentoFeriasDTO;

@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Entity
@Table(name = "apontamento_ferias")
public class ApontamentoFerias {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_apontamento")
	private Long id_apontamento;

	@Column(name = "dia_folga")
	private LocalDate diaFolga;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public Long getId_apontamento() {
		return id_apontamento;
	}

	public void setId_apontamento(Long id_apontamento) {
		this.id_apontamento = id_apontamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDate getDiaFolga() {
		return diaFolga;
	}

	public void setDiaFolga(LocalDate diaFolga) {
		this.diaFolga = diaFolga;
	}

	public ApontamentoFerias() {
		super();

	}

	public ApontamentoFerias(AlterarApontamentoFeriasDTO alterarApontamentoFeriasDTO) {

	}

}
