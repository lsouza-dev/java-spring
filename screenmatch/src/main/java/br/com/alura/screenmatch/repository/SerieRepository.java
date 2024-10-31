package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    Optional<List<Serie>> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor,Double mediaAvaliacao);
    Optional<List<Serie>> findByTotalTemporadasGreaterThanEqual(Integer qtdTemporadas);
    Optional<List<Serie>> findTop5ByOrderByAvaliacaoDesc();
    Optional<List<Serie>> findByGenero(Categoria categoria);
    Optional<List<Serie>> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer maxTemporadas,Double minAavaliacao);
}
