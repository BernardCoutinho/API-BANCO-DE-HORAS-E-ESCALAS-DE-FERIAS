package com.serratec.projeto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.serratec.projeto.dto.AlterarEquipeDTO;

@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Entity
@Table(name = "equipe")
public class Equipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_equipe")
	private Long idEquipe;

	@Column(name = "nome_equipe")
	private String nomeEquipe;

	@Column(name = "lider_equipe")
	private String liderEquipe;

	public Equipe(AlterarEquipeDTO alterarEquipeDTO) {

	}

	public Equipe() {
		super();
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

	public Equipe(Long idEquipe, String nomeEquipe, String liderEquipe) {
		super();
		this.idEquipe = idEquipe;
		this.nomeEquipe = nomeEquipe;
		this.liderEquipe = liderEquipe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEquipe == null) ? 0 : idEquipe.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipe other = (Equipe) obj;
		if (idEquipe == null) {
			if (other.idEquipe != null)
				return false;
		} else if (!idEquipe.equals(other.idEquipe))
			return false;
		return true;
	}

}
