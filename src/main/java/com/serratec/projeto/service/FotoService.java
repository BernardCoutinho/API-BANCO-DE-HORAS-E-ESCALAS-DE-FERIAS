package com.serratec.projeto.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.serratec.projeto.exception.RecursoNotFoundException;
import com.serratec.projeto.model.Foto;
import com.serratec.projeto.model.Usuario;
import com.serratec.projeto.repository.FotoRepository;
import com.serratec.projeto.repository.UsuarioRepository;

@Service
public class FotoService {
	
	@Autowired
	private FotoRepository fotoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Foto inserir(Long id, MultipartFile file) throws IOException {
		Foto foto = new Foto();
		foto.setNome(file.getName());
		foto.setDados(file.getBytes());
		foto.setTipo(file.getContentType());
		Usuario usuario = usuarioRepository.getById(id);
		foto.setUsuario(usuario);

		return fotoRepository.save(foto);
	}
	

	public Foto obterPorId(Long id) throws RecursoNotFoundException {
		Optional<Foto> foto = fotoRepository.findById(id);

		if (foto.isPresent()) {
			return foto.get();
		} else {
			throw new RecursoNotFoundException("Foto de usuario não encontrada");
		}
//		return null;
	}
	
	public void deletarPorId(Long id) {
		if (fotoRepository.existsById(id)) {
			fotoRepository.deleteById(id);
		}
	}
}

