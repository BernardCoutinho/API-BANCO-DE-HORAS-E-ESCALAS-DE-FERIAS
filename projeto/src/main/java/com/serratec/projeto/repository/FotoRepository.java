package com.serratec.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serratec.projeto.model.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Integer> {

	boolean existsById(Long id);

	void deleteById(Long id);

	Optional<Foto> findById(Long id);

}
