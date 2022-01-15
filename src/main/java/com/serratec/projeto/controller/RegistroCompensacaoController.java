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

import com.serratec.projeto.dto.AlterarRegistroCompensacaoDTO;
import com.serratec.projeto.dto.CriarRegistroCompensacaoDTO;
import com.serratec.projeto.dto.RegistroCompensacaoDTO;
import com.serratec.projeto.model.RegistroCompensacao;
import com.serratec.projeto.service.RegistroCompensacaoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/registro-compensacao")
public class RegistroCompensacaoController {

	private RegistroCompensacaoService service;

	@Autowired
	public RegistroCompensacaoController(RegistroCompensacaoService service) {
		super();
		this.service = service;
	}

	@PostMapping
	@ApiOperation(value = "Cadastrar um registro compensação", notes = "Cadastro de um registro compensação")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastra um registro compensação"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.CREATED)
	public RegistroCompensacao criarRegistroCompensacao(@RequestBody CriarRegistroCompensacaoDTO request) {
		return service.criarRegistroCompensacao(request);
	}

	@GetMapping
	@ApiOperation(value = "Listar registros compensações", notes = "Lista os registros compensações")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma lista de registros compensações"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public List<RegistroCompensacao> listar() {
		return service.listar();
	}

	@GetMapping("/usuario/{id}")
	@ApiOperation(value = "Listar registros compensações de um usuário", notes = "Lista os registros de compensações de um usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna uma lista de registros compensações do usuário"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<List<RegistroCompensacao>> listRegUser(@PathVariable Long id) {
		return service.listarRegUsuario(id);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Buscar um registro compensação", notes = "Busca um registro compensação")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um registro compensação"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<RegistroCompensacao> buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar registro compensação", notes = "Alteração de um registro compensação")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Altera um registro compensação"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.OK)
	public RegistroCompensacaoDTO alterar(@PathVariable Long id,
			@Validated @RequestBody AlterarRegistroCompensacaoDTO response) {
		return service.alterar(id, response);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar um registro compensação", notes = "Deleta um registro compensação")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Exclui um registro compensação"),
			@ApiResponse(code = 204, message = "Exclui um registro compensação e retorna vazio"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		return service.deletar(id);

	}

}
