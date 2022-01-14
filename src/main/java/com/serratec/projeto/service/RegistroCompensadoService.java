package com.serratec.projeto.service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.serratec.projeto.dto.AlterarRegistroCompensadoDTO;
import com.serratec.projeto.dto.CriarRegistroCompensadoDTO;
import com.serratec.projeto.dto.RegistroCompensadoDTO;
import com.serratec.projeto.exception.RecursoNotFoundException;
import com.serratec.projeto.model.RegistroCompensado;
import com.serratec.projeto.repository.RegistroCompensadoRepository;
import com.serratec.projeto.repository.UsuarioRepository;

@Service
public class RegistroCompensadoService {

	private RegistroCompensadoRepository repository;
	private UsuarioRepository usuarioRepository;

	@Autowired
	public RegistroCompensadoService(RegistroCompensadoRepository repository, UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.repository = repository;
	}

	/**
	 * MÉTODO PARA CRIAR UM REGISTRO COMPENSADO
	 * 
	 * @param request
	 * @return UM REGISTRO COMPENSADO
	 */

	public RegistroCompensado criarRegistroCompensado(@RequestBody CriarRegistroCompensadoDTO request) {
		RegistroCompensado registro = new RegistroCompensado();
		registro.setUsuario(usuarioRepository.getById(request.getId_usuario()));
		registro.setHoraInicio(request.getHrInicioCompensado());
		registro.setHoraTermino(request.getHrTerminoCompensado());
		registro.setData(request.getDataCompensado());
		long minTotais = Duration.between(request.getHrInicioCompensado(), request.getHrTerminoCompensado())
				.toMinutes();
		registro.setHoraTotal(minTotais);
		return repository.save(registro);
	}

	/**
	 * MÉTODO PARA LISTAR REGISTRO COMPENSADO
	 * 
	 * @return UMA LISTA DE REGISTRO COMPENSADO
	 */

	public List<RegistroCompensado> listar() {
		return repository.findAll();

	}

	public ResponseEntity<List<RegistroCompensado>> listarRegUsuario(Long id) throws RecursoNotFoundException {
		if (!usuarioRepository.existsById(id))
			throw new RecursoNotFoundException("Usuário não encontrado");

		List<RegistroCompensado> listR = repository.findAll();
		listR = listR.stream().filter((u) -> u.getUsuario().getIdUsuario() == id).collect(Collectors.toList());
		return ResponseEntity.ok(listR);
	}

	/**
	 * MÉTODO PARA BUSCAR REGISTRO COMPENSADO POR ID
	 * 
	 * @param id
	 * @return UM REGISTRO COMPENSADO POR ID
	 */

	public ResponseEntity<RegistroCompensado> buscarPorId(Long id) throws RecursoNotFoundException {
		Optional<RegistroCompensado> registro = repository.findById(id);
		if (registro.isPresent()) {
			return ResponseEntity.ok(registro.get());
		} else {
			throw new RecursoNotFoundException("Registro Compensado não encontrado");
		}

	}

	/**
	 * MÉTODO PARA ALTERAR UM REGISTRO COMPENSADO
	 * 
	 * @param id
	 * @param response
	 * @return UM REGISTRO COMPENSADO ALTERADO
	 */

	public RegistroCompensadoDTO alterar(Long id, AlterarRegistroCompensadoDTO alterarRegistroCompensadoDTO)
			throws RecursoNotFoundException {
		if (repository.existsById(id)) {
			RegistroCompensado registroCompensado = new RegistroCompensado(alterarRegistroCompensadoDTO);
			registroCompensado.setData(alterarRegistroCompensadoDTO.getData());
			registroCompensado.setHoraInicio(alterarRegistroCompensadoDTO.getHoraInicio());
			registroCompensado.setHoraTermino(alterarRegistroCompensadoDTO.getHoraTermino());
			registroCompensado.setHoraTotal(alterarRegistroCompensadoDTO.getHoraTotal());
			registroCompensado.setUsuario(alterarRegistroCompensadoDTO.getUsuario());

			repository.save(registroCompensado);
			return new RegistroCompensadoDTO(registroCompensado);
		} else {
			throw new RecursoNotFoundException("Registro Compensado não encontrado");
		}

	}

	/**
	 * MÉTODO PARA DELETAR UM REGISTRO COMPENSADO
	 * 
	 * @param id
	 * @return
	 */

	public ResponseEntity<Void> deletar(Long id) {
		if (!repository.existsById(id))
			return ResponseEntity.notFound().build();
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}