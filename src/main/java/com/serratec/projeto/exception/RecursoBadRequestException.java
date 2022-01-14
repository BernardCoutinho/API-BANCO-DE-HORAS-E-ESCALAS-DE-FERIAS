package com.serratec.projeto.exception;

import java.util.Date;

public class RecursoBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 3015792880071967129L;

	public RecursoBadRequestException() {
		super("Parâmetro inválido");
	}

	public RecursoBadRequestException(String mensagem) {
		super(mensagem);
	}

	public RecursoBadRequestException(String mensagem, Date dataInicioFerias, Date dataFimferias) {
		super(mensagem);
	}

}