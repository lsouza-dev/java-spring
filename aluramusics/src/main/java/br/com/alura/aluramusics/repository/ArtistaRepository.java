package br.com.alura.aluramusics.repository;

import br.com.alura.aluramusics.model.Artista;
import br.com.alura.aluramusics.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista,Long> {

    Optional<Artista> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Artista a WHERE a.id = :id ")
    Artista buscaMusicaPorIdArtista(long id);

    @Query("SELECT m FROM Artista a JOIN a.musicas m WHERE a.nome ILIKE %:trechoNome% ")
    List<Musica> buscarMusicasPorNomeArtista(String trechoNome);
}
