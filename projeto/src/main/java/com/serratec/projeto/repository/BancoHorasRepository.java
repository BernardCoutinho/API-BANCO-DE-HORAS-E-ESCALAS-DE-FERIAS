package com.serratec.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.serratec.projeto.model.BancoHoras;
import com.serratec.projeto.model.Usuario;

@Repository
public interface BancoHorasRepository extends JpaRepository <BancoHoras, Long> {

	public BancoHoras findByUsuario(Usuario id_usuario);
}
