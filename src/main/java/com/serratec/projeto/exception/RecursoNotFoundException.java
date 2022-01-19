package com.serratec.projeto.exception;

public class RecursoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3015792880071967120L;

	public RecursoNotFoundException() {
		super("Recurso não encontrado");
	}

	public RecursoNotFoundException(String mensagem) {
		super(mensagem);
	}

}
