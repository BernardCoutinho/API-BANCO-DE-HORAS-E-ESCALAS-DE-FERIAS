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

import com.serratec.projeto.dto.AlterarBancoHorasDTO;
import com.serratec.projeto.dto.BancoHorasDTO;
import com.serratec.projeto.dto.CriarBancoHorasDTO;
import com.serratec.projeto.dto.CriarRegistroCompensacaoDTO;
import com.serratec.projeto.dto.CriarRegistroCompensadoDTO;
import com.serratec.projeto.model.BancoHoras;
import com.serratec.projeto.service.BancoHorasService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/bancoHoras")
public class BancoHorasController {
	private BancoHorasService service;

	@Autowired
	public BancoHorasController(BancoHorasService service) {
		super();
		this.service = service;
	}

	@PostMapping
	@ApiOperation(value = "Cadastrar um banco de horas", notes = "Cadastro de um banco de horas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastra um banco de horas"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.CREATED)
	public BancoHoras criarBancoHoras(@RequestBody CriarBancoHorasDTO request) {
		return service.criarBancoHoras(request);
	}

	@GetMapping
	@ApiOperation(value = "Listar banco de horas", notes = "lista os bancos de horas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma lista de bancos de horas"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public List<BancoHoras> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Buscar um banco de horas", notes = "Busca um banco de horas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um banco de horas"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<BancoHoras> buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar banco de horas", notes = "Alteração de um banco de horas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Altera um banco de horas"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.OK)
	public BancoHorasDTO alterar(@PathVariable Long id, @Validated @RequestBody AlterarBancoHorasDTO response) {
		return service.alterar(id, response);
	}

	@PutMapping("/adicionarHoras")
	@ApiOperation(value = "Adicionar Horas no banco de horas", notes = "Adiciona horas no banco de horas do usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna as horas implementadas do banco de horas do usuário"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<BancoHoras> adicionarHoras(@Validated @RequestBody CriarRegistroCompensacaoDTO request) {
		return service.adicionarHoras(request);

	}

	@PutMapping("/removerHoras")
	@ApiOperation(value = "Remover horas", notes = "Remove as horas do banco de horas do usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna as horas removidas do banco de horas do usuário"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<BancoHoras> removerHoras(@Validated @RequestBody CriarRegistroCompensadoDTO request) {
		return service.removerHoras(request);

	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar um banco de horas", notes = "Deleta um banco de horas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Exclui um banco de horas"),
			@ApiResponse(code = 204, message = "Exclui um apontamento e retorna vazio"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		return service.deletar(id);

	}
}