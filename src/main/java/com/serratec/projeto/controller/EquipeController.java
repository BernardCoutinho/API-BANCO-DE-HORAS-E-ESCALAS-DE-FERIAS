package com.serratec.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.serratec.projeto.dto.AlterarEquipeDTO;
import com.serratec.projeto.dto.CriarEquipeDTO;
import com.serratec.projeto.dto.EquipeDTO;
import com.serratec.projeto.dto.UsuarioDTO;
import com.serratec.projeto.service.EquipeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/equipes")
public class EquipeController {
	private EquipeService service;

	@Autowired
	public EquipeController(EquipeService service) {
		super();
		this.service = service;
	}

	@PostMapping
	@ApiOperation(value = "Cadastrar uma equipe", notes = "Cadastro de uma equipe")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastra uma equipe"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.CREATED)
	public EquipeDTO criarEquipe(@RequestBody CriarEquipeDTO request) {
		return service.criarEquipe(request);
	}

	@GetMapping
	@ApiOperation(value = "Listar equipes", notes = "Lista as equipes")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma lista de equipes"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public List<EquipeDTO> listar() {
		return service.listar();
	}

	@GetMapping("/{id}/membros")
	@ApiOperation(value = "Buscar um membro da equipe por id", notes = "Busca um membro da equipe")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um membro da equipe"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public List<UsuarioDTO> membrosEquipe(@PathVariable Long id) {
		return service.membrosEquipe(id);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Buscar uma equipe por id", notes = "Busca uma equipe")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma equipe"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public EquipeDTO buscarPorId(@PathVariable Long id) {
		return service.buscar(id);

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar equipe", notes = "Alteração de uma equipe")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Altera uma equipe"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.OK)
	public EquipeDTO alterarEquipeDTO(@PathVariable Long id, @Validated @RequestBody AlterarEquipeDTO response) {
		return service.alterar(id, response);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar uma equipe", notes = "Deleta equipe")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Exclui uma equipe"),
			@ApiResponse(code = 204, message = "Exclui uma equipe e retorna vazio"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		return service.deletar(id);

	}
}