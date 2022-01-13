package com.serratec.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serratec.projeto.model.RegistroCompensacao;
import com.serratec.projeto.model.Usuario;

@Repository
public interface RegistroCompensacaoRepository extends JpaRepository <RegistroCompensacao, Long> {

	public RegistroCompensacao findByUsuario(Usuario id_usuario);
}
