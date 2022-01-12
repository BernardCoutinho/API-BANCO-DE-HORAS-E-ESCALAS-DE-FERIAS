package com.serratec.projeto.dto;

import java.io.Serializable;

public class UsuarioFotoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9159845856010861984L;

	private Long id;

	public UsuarioFotoDTO() {

	}

	public UsuarioFotoDTO(Long id) {

		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		UsuarioFotoDTO other = (UsuarioFotoDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
