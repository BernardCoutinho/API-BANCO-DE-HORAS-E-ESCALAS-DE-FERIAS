package com.serratec.projeto.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serratec.projeto.config.security.jwt.JwtUtils;
import com.serratec.projeto.config.security.service.UserDetailsImpl;
import com.serratec.projeto.dto.CriarUsuarioDTO;
import com.serratec.projeto.dto.JwtResponse;
import com.serratec.projeto.dto.LoginRequest;
import com.serratec.projeto.dto.MessageResponse;
import com.serratec.projeto.model.Usuario;
import com.serratec.projeto.repository.UsuarioRepository;
import com.serratec.projeto.service.UsuarioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsuarioRepository repository;

	@Autowired
	UsuarioService userService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println(loginRequest.getEmail());
		System.out.println(loginRequest.getPassword());

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());	
		Optional<Usuario> user = repository.findByEmail(loginRequest.getEmail());
		userDetails.setId(user.get().getIdUsuario());
		userDetails.setEmail(user.get().getEmail());
		return ResponseEntity
				.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), user.get().getFotoBase64(), user.get().getNome(), user.get().getNivel().name()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody CriarUsuarioDTO request) {
		if (repository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Erro: Email já está sendo usado!"));
		}

		// Create new user's account
		userService.criarUsuario(request);

		return ResponseEntity.ok(new MessageResponse("Usuário registrado com sucesso!"));
	}
}
