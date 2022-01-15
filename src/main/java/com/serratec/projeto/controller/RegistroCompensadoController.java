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

import com.serratec.projeto.dto.AlterarRegistroCompensadoDTO;
import com.serratec.projeto.dto.CriarRegistroCompensadoDTO;
import com.serratec.projeto.dto.RegistroCompensadoDTO;
import com.serratec.projeto.model.RegistroCompensado;
import com.serratec.projeto.service.RegistroCompensadoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/registro-compensado")
public class RegistroCompensadoController {

	private RegistroCompensadoService service;

	@Autowired
	public RegistroCompensadoController(RegistroCompensadoService service) {
		super();
		this.service = service;
	}

	@PostMapping
	@ApiOperation(value = "Cadastrar um registro compensado", notes = "Cadastro de um registro compensado")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastra um registro compensado"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.CREATED)
	public RegistroCompensado criarRegistroCompensado(@RequestBody CriarRegistroCompensadoDTO request) {
		return service.criarRegistroCompensado(request);
	}

	@GetMapping
	@ApiOperation(value = "Listar registros compensados", notes = "Lista os registros compensados")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma lista de registros compensados"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public List<RegistroCompensado> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Buscar um registro compensado", notes = "Busca um registro compensado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um registro compensado"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<RegistroCompensado> buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);

	}

	@GetMapping("/usuario/{id}")
	@ApiOperation(value = "Buscar os registros compensados de um usuário", notes = "Busca os registros compensados de um usuário")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna os registros compensados do usuário"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<List<RegistroCompensado>> listRegUser(@PathVariable Long id) {

		return service.listarRegUsuario(id);

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar registro compensado", notes = "Alteração de um registro compensado")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Altera um registro compensado"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.OK)
	public RegistroCompensadoDTO alterar(@PathVariable Long id,
			@Validated @RequestBody AlterarRegistroCompensadoDTO response) {
		return service.alterar(id, response);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar um registro compensado", notes = "Deleta um registro compensado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Exclui um registro compensado"),
			@ApiResponse(code = 204, message = "Exclui um registro compensado e retorna vazio"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		return service.deletar(id);

	}

}
