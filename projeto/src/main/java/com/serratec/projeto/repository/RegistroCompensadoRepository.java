package com.serratec.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serratec.projeto.dto.AlterarRegistroCompensadoDTO;
import com.serratec.projeto.model.RegistroCompensado;

@Repository
public interface RegistroCompensadoRepository extends JpaRepository <RegistroCompensado, Long> {

	AlterarRegistroCompensadoDTO save(AlterarRegistroCompensadoDTO alterarRegistroCompensadoDTO);

}
