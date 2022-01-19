package com.serratec.projeto.dto;

import java.io.Serializable;

public class CriarEquipeDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1336679057708738654L;

	private String nomeEquipe;
	private String liderEquipe;

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
