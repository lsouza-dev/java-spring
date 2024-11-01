package br.com.alura.aluramusics.repository;

import br.com.alura.aluramusics.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista,Long> {

    Optional<Artista> findByNomeContainingIgnoreCase(String nome);
}
