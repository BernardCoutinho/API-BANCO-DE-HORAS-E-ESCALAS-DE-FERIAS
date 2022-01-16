package com.serratec.projeto.dto;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String email;
	private String foto;

	public JwtResponse(String accessToken, Long id, String email, String foto) {
		this.token = accessToken;
		this.id = id;
		this.email = email;
		this.foto = foto;
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
