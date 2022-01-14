package com.serratec.projeto.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.serratec.projeto.dto.AlterarUsuarioDTO;
import com.serratec.projeto.dto.CriarUsuarioDTO;
import com.serratec.projeto.dto.UsuarioDTO;
import com.serratec.projeto.exception.RecursoBadRequestException;
import com.serratec.projeto.model.ApontamentoFerias;
import com.serratec.projeto.model.Foto;
import com.serratec.projeto.model.Usuario;
import com.serratec.projeto.repository.UsuarioRepository;
import com.serratec.projeto.service.ApontamentoFeriasService;
import com.serratec.projeto.service.FotoService;
import com.serratec.projeto.service.UsuarioService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	private UsuarioService service;
	private ApontamentoFeriasService apontamentoService;

	@Autowired
	public UsuarioController(UsuarioService service, ApontamentoFeriasService apontamentoService) {
		super();
		this.service = service;
		this.apontamentoService = apontamentoService;
	}

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	FotoService fotoService;

	@PostMapping
	@ApiOperation(value = "Cadastrar um usuario", notes = "Cadastro de usuario")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastra um usuario"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.CREATED)

	public ResponseEntity<Object> inserir(@Valid @RequestBody CriarUsuarioDTO criarUsuarioDTO) {
		try {
			UsuarioDTO usuarioDTO = service.criarUsuario(criarUsuarioDTO);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(usuarioDTO.getNome()).toUri();
			return ResponseEntity.created(uri).body(usuarioDTO);
		} catch (RecursoBadRequestException recursoBadRequestException) {
			return ResponseEntity.badRequest().body(recursoBadRequestException.getMessage());
		}
	}

	@PostMapping("/fotos/adicionar/{id}")
	@ApiOperation(value = "Adicionar uma foto a um usuario", notes = "Inserção de uma foto para um usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna o usuario e a foto inserida"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<Object> inserirFoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		try {
			return ResponseEntity.ok(fotoService.inserir(id, file));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@GetMapping("/{id}/foto")
	@ApiOperation(value = "Obter foto do usuario", notes = "Busca de foto correspondente a um usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma foto de um usuario"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<byte[]> obterFoto(@PathVariable Long id) {
		Foto foto = fotoService.obterPorId(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", foto.getTipo());
		headers.add("content-length", String.valueOf(foto.getDados().length));
		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
	}

	@GetMapping
	@ApiOperation(value = "Listar usuários", notes = "Retorna uma lista de usuários")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma lista de usuários"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<List<UsuarioDTO>> listar() {
		return ResponseEntity.ok(service.listar());
	}

	@GetMapping("/ferias/{id}")
	@ApiOperation(value = "Listar apontamento de férias do usuario", notes = "lista de apontamento de férias do usuário")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna as férias do usuário"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<List<ApontamentoFerias>> feriasUsuario(@PathVariable Long id) {
		Optional<Usuario> UsuarioDTO = usuarioRepository.findById(id);
		if (!UsuarioDTO.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(apontamentoService.feriasDoUsuario(id));
	}

	@GetMapping("{id}")
	@ApiOperation(value = "Buscar um usuario por id", notes = "Busca um usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um usuario"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
		Optional<Usuario> UsuarioDTO = usuarioRepository.findById(id);
		if (!UsuarioDTO.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(service.buscar(id));
	}

	@PutMapping("{id}")
	@ApiOperation(value = "Alterar usuario", notes = "Alteração de um usuario")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Altera um usuario"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> alterar(@PathVariable Long id,
			@Valid @RequestBody AlterarUsuarioDTO alterarUsuarioDTO) throws RecursoBadRequestException {

		if (service.alterar(id, alterarUsuarioDTO) != null) {
			return ResponseEntity.ok(alterarUsuarioDTO);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("{id}")
	@ApiOperation(value = "Deletar um usuario", notes = "Deleta usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Exclui um usuario"),
			@ApiResponse(code = 204, message = "Exclui um usuario e retorna vazio"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })

	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/fotos/deletar/{id}")
	@ApiOperation(value = "Deletar foto de um usuario por ID da foto", notes = "Exclusão de uma foto de um usuario pelo nº do ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Deleta foto de um usuario"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Recurso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro de servidor") })
	public ResponseEntity<Void> deletarFotoPorId(@PathVariable Long id) {
		if (fotoService.obterPorId(id) != null) {
			fotoService.deletarPorId(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
