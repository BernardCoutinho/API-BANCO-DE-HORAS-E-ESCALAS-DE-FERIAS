package com.serratec.projeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.serratec.projeto.dto.AlterarEquipeDTO;
import com.serratec.projeto.dto.CriarEquipeDTO;
import com.serratec.projeto.dto.EquipeDTO;
import com.serratec.projeto.dto.UsuarioDTO;
import com.serratec.projeto.exception.RecursoBadRequestException;
import com.serratec.projeto.exception.RecursoNotFoundException;
import com.serratec.projeto.model.Equipe;
import com.serratec.projeto.model.Usuario;
import com.serratec.projeto.repository.EquipeRepository;
import com.serratec.projeto.repository.UsuarioRepository;

@Service
public class EquipeService {
	private EquipeRepository equipeRepository;
	private UsuarioRepository usuarioRepository;

	@Autowired
	public EquipeService(UsuarioRepository usuarioRepository, EquipeRepository equipeRepository) {
		super();
		this.equipeRepository = equipeRepository;
		this.usuarioRepository = usuarioRepository;
	}

	/**
	 * MÉTODO PARA CRIAR UMA EQUIPE
	 * 
	 * @param request
	 * @return RETORNA UMA EQUIPE
	 */

	public EquipeDTO criarEquipe(CriarEquipeDTO criarEquipeDTO) throws RecursoBadRequestException {

		if (equipeRepository.findByNomeEquipe(criarEquipeDTO.getNomeEquipe()).isPresent()) {
			throw new RecursoBadRequestException("Nome de equipe já cadastrado!");
		}

		Equipe equipe = new Equipe(null);
		equipe.setNomeEquipe(criarEquipeDTO.getNomeEquipe());
		equipe.setLiderEquipe(criarEquipeDTO.getLiderEquipe());
		equipeRepository.save(equipe);
		return new EquipeDTO(equipe);
	}

	/**
	 * MÉTODO PARA LISTAR EQUIPES
	 * 
	 * @return UMA LISTA DE EQUIPES
	 */

	public List<EquipeDTO> listar() {
		List<Equipe> equipes = equipeRepository.findAll();
		List<EquipeDTO> equipesDTO = new ArrayList<EquipeDTO>();

		for (Equipe equipe : equipes) {
			EquipeDTO equipeDTO = new EquipeDTO(equipe);
			equipesDTO.add(equipeDTO);
		}

		return equipesDTO;
	}

	/**
	 * MÉTODO PARA BUSCAR UMA EQUIPE POR ID
	 * 
	 * @param id
	 * @return RETORNA UMA EQUIPE
	 */

	public EquipeDTO buscar(Long id) throws RecursoNotFoundException {
		Optional<Equipe> equipe = equipeRepository.findById(id);
		if (equipe.isPresent()) {
			return new EquipeDTO(equipe.get());
		} else {
			throw new RecursoNotFoundException("Equipe não encontrada!");
		}
	}

	/**
	 * MÉTODO PARAR ALTERAR UMA EQUIPE
	 * 
	 * @param id
	 * @param request
	 * @return RETORNA UMA EQUIPE ALTERADA
	 */

	public EquipeDTO alterar(Long id, AlterarEquipeDTO alterarEquipeDTO) throws RecursoNotFoundException {
		if (equipeRepository.existsById(id)) {
			Equipe equipe = new Equipe(alterarEquipeDTO);
			equipe.setIdEquipe(id);
			equipe.setLiderEquipe(alterarEquipeDTO.getLiderEquipe());
			equipe.setNomeEquipe(alterarEquipeDTO.getNomeEquipe());
			equipeRepository.save(equipe);
			return new EquipeDTO(equipe);
		}
		throw new RecursoNotFoundException("Equipe não encontrada!");

	}

	/**
	 * MÉTODO PARA DELETAR UMA EQUIPE
	 * 
	 * @param id
	 * @return
	 */

	public ResponseEntity<Void> deletar(Long id) {
		if (!equipeRepository.existsById(id))
			return ResponseEntity.notFound().build();
		equipeRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * MÉTODO PARA RETORNAR O NUMERO DE MEMBROS DA EQUIPE
	 * 
	 * @param id_equipe
	 * @return RETORNA O NUMERO DE MEMBROS DA EQUIPE
	 */

	public int tamanhoEquipe(Long id_equipe) {
		Equipe equipe = equipeRepository.getById(id_equipe);
		List<Usuario> lista = usuarioRepository.findAll();
		int numeroDeMembros = 0;
		for (Usuario user : lista) {
			if (user.getEquipe() == equipe) {
				numeroDeMembros += 1;
			}
		}
		return numeroDeMembros;
	}

	/**
	 * MÉTODO PARA LISTAR OS MEMBROS DA EQUIPE
	 * 
	 * @param id_equipe
	 * @return UMA LISTA DE MEMBROS DA EQUIPE
	 */

	public List<UsuarioDTO> membrosEquipe(Long id_equipe) {
		Equipe equipe = equipeRepository.getById(id_equipe);
		List<Usuario> lista = usuarioRepository.findAll();
		List<UsuarioDTO> listaDTO = new ArrayList<UsuarioDTO>();
		for (Usuario user : lista) {
			if (user.getEquipe() == equipe) {
				UsuarioDTO userDTO = new UsuarioDTO();
				userDTO.setNivel(user.getNivel());
				userDTO.setDataContratacao(user.getDataContratacao());
				userDTO.setDataVencimento(user.getDataVencimento());
				userDTO.setDataPodeIniciarFerias(user.getDataPodeIniciarFerias());
				userDTO.setDataDeveIniciarFerias(user.getDataDeveIniciarFerias());
				userDTO.setEmail(user.getEmail());
				userDTO.setNome(user.getNome());
				userDTO.setEquipe(user.getEquipe());
				userDTO.setIdUsuario(user.getIdUsuario());
				userDTO.setFotoBase64(user.getFotoBase64());
				listaDTO.add(userDTO);
			}
		}
		return listaDTO;
	}
}