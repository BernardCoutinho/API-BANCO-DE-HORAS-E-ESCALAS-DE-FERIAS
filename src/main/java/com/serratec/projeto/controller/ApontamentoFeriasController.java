package com.serratec.projeto.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.serratec.projeto.dto.AlterarApontamentoFeriasDTO;
import com.serratec.projeto.dto.ApontamentoFeriasDTO;
import com.serratec.projeto.dto.CriarApontamentoFeriasDTO;
import com.serratec.projeto.dto.CriarMarcarFolgaPorPeriodoDTO;
import com.serratec.projeto.dto.UsuarioDTO;
import com.serratec.projeto.model.ApontamentoFerias;
import com.serratec.projeto.service.ApontamentoFeriasService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/apontamento")
public class ApontamentoFeriasController {
	private ApontamentoFeriasService service;

	@Autowired
	public ApontamentoFeriasController(ApontamentoFeriasService service) {
		super();
		this.service = service;
	}

	@PostMapping
	@ApiOperation(value = "Cadastrar um apontamento", notes = "Cadastro de um apontamento de férias")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastra um apontamento de férias"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.CREATED)

	public CriarApontamentoFeriasDTO criarApontamentoFerias(@RequestBody CriarApontamentoFeriasDTO request) {
		return service.marcarFolga(request);
	}

	@PostMapping("/periodo")
	@ApiOperation(value = "Cadastrar um apontamento", notes = "Cadastro de um apontamento de férias")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastra um apontamento de férias"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.CREATED)

	public List<CriarApontamentoFeriasDTO> criarApontamentoFeriasPorPeriodo(
			@RequestBody CriarMarcarFolgaPorPeriodoDTO request) {
		return service.marcarFolgaPorPeriodo(request);
	}

	@GetMapping
	@ApiOperation(value = "Listar apontamento férias", notes = "Lista os apontamentos férias")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma lista de apontamento férias"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public List<ApontamentoFerias> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Buscar um apontamento", notes = "Busca um apontamento de férias")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um apontamento de férias"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<ApontamentoFerias> buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);

	}

	@GetMapping("/membros_equipe_folga_dia/{idEquipe}/{dia}")
	@ApiOperation(value = "Buscar membros de uma equipe de folga no dia", notes = "Busca um membros de uma equipe de folga no dia")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma lista de membros de folga no dia"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<List<UsuarioDTO>> listaMembrosDeFeriasNoDia(@PathVariable("idEquipe") Long idEquipe, @PathVariable("dia") String diaR) {
		
              
		LocalDate dia = LocalDate.parse(diaR, DateTimeFormatter.ISO_DATE);
		Long equipe = idEquipe;
		return ResponseEntity.ok(service.listaUsuariosDaEquipeDeFolgaNoDia(dia, equipe));
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar apontamento", notes = "Alteração de um apontamento de férias")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Altera um apontamento de férias"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.OK)
	public ApontamentoFeriasDTO alterar(@PathVariable Long id,
			@Validated @RequestBody AlterarApontamentoFeriasDTO response) {
		return service.alterar(id, response);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar um apontamento", notes = "Deleta apontamento de férias")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Exclui um apontamento de férias"),
			@ApiResponse(code = 204, message = "Exclui um apontamento e retorna vazio"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		return service.deletar(id);

	}
}