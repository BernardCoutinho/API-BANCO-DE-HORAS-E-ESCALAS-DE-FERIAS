package com.serratec.projeto.dto;



public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String email;
	private String foto;
	private String nome;
	private String nivel;

	public JwtResponse(String accessToken, Long id, String email, String foto, String nome, String nivel) {
		this.token = accessToken;
		this.id = id;
		this.email = email;
		this.foto = foto;
		this.nome = nome;
		this.nivel = nivel;
		
	}
	
	

	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getNivel() {
		return nivel;
	}



	public void setNivel(String nivel) {
		this.nivel = nivel;
	}



	public String getFoto() {
		return foto;
	}



	public void setFoto(String foto) {
		this.foto = foto;
	}



	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
