package com.serratec.projeto.dto;

import java.io.Serializable;

import com.serratec.projeto.model.Equipe;

public class EquipeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5341034667596677744L;

	private Long idEquipe;
	private String nomeEquipe;
	private String liderEquipe;

	public EquipeDTO() {
		super();
	}

	public EquipeDTO(Equipe equipe) {
		super();
		this.idEquipe = equipe.getIdEquipe();
		this.nomeEquipe = equipe.getNomeEquipe();
		this.liderEquipe = equipe.getLiderEquipe();
	}

	public Long getIdEquipe() {
		return idEquipe;
	}

	public void setIdEquipe(Long idEquipe) {
		this.idEquipe = idEquipe;
	}

	public String getNomeEquipe() {
		return nomeEquipe;
	}

	public void setNomeEquipe(String nomeEquipe) {
		this.nomeEquipe = nomeEquipe;
	}

	public String getLiderEquipe() {
		return liderEquipe;
	}

	public void setLiderEquipe(String liderEquipe) {
		this.liderEquipe = liderEquipe;
	}

}
