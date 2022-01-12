package com.serratec.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serratec.projeto.model.ApontamentoFerias;
import com.serratec.projeto.model.Usuario;

@Repository
public interface ApontamentoFeriasRepository extends JpaRepository<ApontamentoFerias, Long>{

	public ApontamentoFerias findByUsuario(Usuario id_usuario);
}
