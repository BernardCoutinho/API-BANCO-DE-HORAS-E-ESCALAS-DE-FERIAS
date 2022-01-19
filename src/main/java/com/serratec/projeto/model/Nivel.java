package com.serratec.projeto.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.serratec.projeto.exception.EnumValidationExcepetion;

public enum Nivel {
	SENIOR, JUNIOR, TRAINEE, PLENO;

	@JsonCreator
	public static Nivel verifica(String valor) throws EnumValidationExcepetion {
		for (Nivel nivel : values()) {
			if (valor.equals(nivel.name())) {
				return nivel;
			}
		}
		throw new EnumValidationExcepetion("Categoria inválida!");
	}
}
