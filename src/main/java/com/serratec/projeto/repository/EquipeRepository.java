package com.serratec.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serratec.projeto.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

	Optional<Equipe> findByNomeEquipe(String nomeEquipe);

	Boolean existsByNomeEquipe(String nomeEquipe);

	Optional<Equipe> findByLiderEquipe(String liderEquipe);

}
