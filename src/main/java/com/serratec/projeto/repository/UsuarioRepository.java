package com.serratec.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serratec.projeto.model.Usuario;

import java.util.Date;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNome(String nome);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
    Optional<Usuario> findByEmail(String email);
    
    Optional<Usuario> findByDataContratacao(Date data);
    
    Optional<Usuario> findByUsername(String username);

}
