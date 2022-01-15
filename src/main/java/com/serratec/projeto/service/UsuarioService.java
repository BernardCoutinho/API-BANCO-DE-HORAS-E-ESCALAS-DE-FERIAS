package com.serratec.projeto.service;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.serratec.projeto.config.MailConfig;
import com.serratec.projeto.dto.AlterarUsuarioDTO;
import com.serratec.projeto.dto.CriarBancoHorasDTO;
import com.serratec.projeto.dto.CriarUsuarioDTO;
import com.serratec.projeto.dto.UsuarioDTO;
import com.serratec.projeto.exception.RecursoBadRequestException;
import com.serratec.projeto.exception.RecursoNotFoundException;
import com.serratec.projeto.model.Usuario;
import com.serratec.projeto.repository.BancoHorasRepository;
import com.serratec.projeto.repository.EquipeRepository;
import com.serratec.projeto.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EquipeRepository equipeRepository;

	@Autowired
	private BancoHorasService bhService;

	@Autowired
	private BancoHorasRepository bhRepository;

	@Autowired
	MailConfig mailConfig;

	@Autowired
	PasswordEncoder encoder;

	/**
	 * MÉTODO PARA CRIAR UM NOVO USUÁRIO
	 * 
	 * @param criarUsuarioDTO
	 * @return UM NOVO USUÁRIO
	 * @throws RecursoBadRequestException
	 */

	public UsuarioDTO criarUsuario(CriarUsuarioDTO criarUsuarioDTO) throws RecursoBadRequestException {

		if (usuarioRepository.findByUsername(criarUsuarioDTO.getUsername()).isPresent()) {
			throw new RecursoBadRequestException("Nome de usuário já cadastrado!");
		}

		if (usuarioRepository.findByEmail(criarUsuarioDTO.getEmail()).isPresent()) {
			throw new RecursoBadRequestException("Email já cadastrado!");
		}

		Usuario usuario = new Usuario();
		Long saldo = (long) 0;
		usuario.setNome(criarUsuarioDTO.getNome());
		usuario.setUsername(criarUsuarioDTO.getUsername());
		usuario.setEquipe(equipeRepository.getById(criarUsuarioDTO.getIdEquipe()));
		usuario.setNivel(criarUsuarioDTO.getNivel());
		usuario.setDataContratacao(criarUsuarioDTO.getDataContratacao());
		usuario.setDataPodeIniciarFerias(criarUsuarioDTO.getDataContratacao().plusYears(1));
		usuario.setDataDeveIniciarFerias(criarUsuarioDTO.getDataContratacao().plusMonths(23));
		usuario.setDataVencimento(criarUsuarioDTO.getDataContratacao().plusYears(2));
		usuario.setEmail(criarUsuarioDTO.getEmail());
		usuario.setQtdDiasFerias(30);
		usuario.setPassword(encoder.encode(criarUsuarioDTO.getPassword()));
		usuarioRepository.save(usuario);
		CriarBancoHorasDTO bancoDTO = new CriarBancoHorasDTO(usuario.getIdUsuario(), saldo);
		bhService.criarBancoHoras(bancoDTO);

		String texto = "Usuario cadastrado com sucesso!\nSeu Login: %s \nSua Senha: %s\nAo acessar pela primeira vez mude a sua senha!\nAcesse pelo Link para mudar: %s";
		texto = String.format(texto, usuario.getEmail(), usuario.getPassword(), "LinkDaPag");
		mailConfig.enviarEmail(criarUsuarioDTO.getEmail(), "Cadastro de Usuário Concluído", texto);

		return new UsuarioDTO(usuario);

	}

	/**
	 * MÉTODO PARA INSERIR FOTO
	 */
	private UsuarioDTO adicionarUriFoto(Usuario usuario) {

		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setIdUsuario(usuario.getIdUsuario());
		usuarioDto.setNome(usuario.getNome());
		usuarioDto.setUsername(usuario.getUsername());
		usuarioDto.setEmail(usuario.getEmail());
		usuarioDto.setNivel(usuario.getNivel());
		usuarioDto.setEquipe(usuario.getEquipe());
		usuarioDto.setDataContratacao(usuario.getDataContratacao());
		usuarioDto.setDataPodeIniciarFerias(usuario.getDataPodeIniciarFerias());
		usuarioDto.setDataDeveIniciarFerias(usuario.getDataDeveIniciarFerias());
		usuarioDto.setDataVencimento(usuario.getDataVencimento());
//		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/projeto-alter/usuarios/{id}/foto")
//				.buildAndExpand(usuario.getIdUsuario()).toUri();
//		usuarioDto.setUri(uri.toString());

		return usuarioDto;
	}

	/**
	 * MÉTODO PARA LISTAR TODOS OS USUARIOS
	 * 
	 * @return UMA LISTA DE USUARIOS
	 */

	public List<UsuarioDTO> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<UsuarioDTO> usuariosDTO = new ArrayList<UsuarioDTO>();

		for (Usuario usuario : usuarios) {
			UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
			usuariosDTO.add(usuarioDTO);
		}

		return usuariosDTO;
	}

	/**
	 * MÉTODO PARA BUSCAR USUARIO POR ID
	 * 
	 * @param id
	 * @return
	 */

	public UsuarioDTO buscar(Long id) throws RecursoNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isPresent()) {
			return adicionarUriFoto(usuario.get());
		} else {
			throw new RecursoNotFoundException("Usuario não encontrado");
		}
	}

	/**
	 * MÉTODO PARA DELETAR UM USUARIO
	 * 
	 * @param id
	 * @return
	 */

	public ResponseEntity<UsuarioDTO> deletar(Long id) {
		if (!usuarioRepository.existsById(id))
			return ResponseEntity.notFound().build();
		Usuario user = usuarioRepository.getById(id);
		bhService.deletar(bhRepository.findByUsuario(user).getId_banco());
		usuarioRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * MÉTODO PARA ALTERAR ALGUM REGISTRO DO USUARIO
	 * 
	 * @param id
	 * @param request
	 * @return
	 */

	public UsuarioDTO alterar(Long id, AlterarUsuarioDTO alterarUsuarioDTO) throws RecursoNotFoundException {
		if (usuarioRepository.existsById(id)) {
			Usuario usuario = new Usuario(alterarUsuarioDTO);
			usuario.setIdUsuario(id);
			usuario.setNome(alterarUsuarioDTO.getNome());
			usuario.setPassword(encoder.encode(alterarUsuarioDTO.getPassword()));
			usuario.setEmail(alterarUsuarioDTO.getEmail());
			usuario.setNivel(alterarUsuarioDTO.getNivel());
			usuario.setEquipe(equipeRepository.getById(alterarUsuarioDTO.getEquipe()));
			usuario.setDataContratacao(alterarUsuarioDTO.getDataContratacao());
			usuario.setDataPodeIniciarFerias(alterarUsuarioDTO.getDataPodeIniciarferias());
			usuario.setDataDeveIniciarFerias(alterarUsuarioDTO.getDataDeveIniciarferias());
			usuario.setDataVencimento(alterarUsuarioDTO.getDataVencimento());
			String texto = "Usuario alterado com sucesso!\nSeu Login: %s \nSua Senha: %s";
			texto = String.format(texto, usuario.getEmail(), usuario.getPassword());
			adicionarUriFoto(usuario);
			mailConfig.enviarEmail(usuario.getEmail(), "Alteração de Usuário Concluída", texto);

			usuarioRepository.save(usuario);
			return new UsuarioDTO(usuario);
		}
		throw new RecursoNotFoundException("Usuario não encontrado");
	}

	/**
	 * MÉTODO PARA DESCONTAR AS FÉRIAS DO USUÁRIO
	 * 
	 * @param usuario
	 * @param numDias
	 * @return
	 */

	public UsuarioDTO DescontarFerias(Usuario usuario, int numDias) {
		Usuario user = usuario;
		user.setQtdDiasFerias(user.getQtdDiasFerias() - numDias);
		usuarioRepository.save(user);
		return new UsuarioDTO(user);
	}

	/**
	 * MÉTODO PARA ACRESCENTAR AS FÉRIAS DO USUÁRIO
	 */

	public UsuarioDTO acrescentarFerias(Long id, int numDias) {
		Usuario user = usuarioRepository.getById(id);
		user.setQtdDiasFerias(user.getQtdDiasFerias() + numDias);
		usuarioRepository.save(user);
		return new UsuarioDTO(user);
	}

	/**
	 * MÉTODO PARA RESETAR A QUANTIDADE DE FÉRIAS E ALTERAR AS DATAS DO USUÁRIO
	 */

	public void resetarQuantidadeFeriasEAlterarDatas() {
		List<Usuario> listUser = usuarioRepository.findAll();
		LocalDate diaAtual = LocalDate.now();
		listUser.stream().filter((e) -> e.getDataVencimento().getDayOfYear() == diaAtual.getDayOfYear()).forEach(e -> {
			Usuario user = e;
			user.setQtdDiasFerias(30);
			user.setDataPodeIniciarFerias(user.getDataPodeIniciarFerias().plusYears(1));
			user.setDataDeveIniciarFerias(user.getDataDeveIniciarFerias().plusYears(1));
			user.setDataVencimento(user.getDataVencimento().plusYears(1));
			usuarioRepository.save(user);
		});
	}

}
