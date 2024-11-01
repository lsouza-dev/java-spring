package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    Optional<List<Serie>> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor,Double mediaAvaliacao);
    Optional<List<Serie>> findByTotalTemporadasGreaterThanEqual(Integer qtdTemporadas);
    Optional<List<Serie>> findTop5ByOrderByAvaliacaoDesc();
    Optional<List<Serie>> findByGenero(Categoria categoria);
    Optional<List<Serie>> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer maxTemporadas,Double minAvaliacao);

    // JPQL é uma estrutura de sintaxe para DB
    // Select * se torna Select apelidoSerie, o apelidoSerie é apenas uma forma
    // de referenciar a entidade de você deseja mapear na na consulta
    // nesse caso, a entidade é Serie.
    // Para referenciarmos os atributos/coluna da série na consulta, inserimos antes da
    // variável de parâmetro do método dois pontos : , dessa forma, a JPQL entende que está sendo
    // passado uma "variavel" dentro da consulta, tornando a consulta dinâmica
    @Query("SELECT apelidoSerie FROM Serie apelidoSerie WHERE apelidoSerie.totalTemporadas <= :maxTemporadas AND apelidoSerie.avaliacao >= :minAvaliacao")
    Optional<List<Serie>> seriePorTemporadaEAvaliacao(Integer maxTemporadas,Double minAvaliacao);

    // Selecionar episodio De Serie s UNINDO as colunas pela Serie.episodios
    // e buscando pelos episodios com titulo ILIKE (lower case) que contenham %trecho%
    @Query("SELECT ep FROM Serie s JOIN s.episodios ep WHERE ep.titulo ILIKE %:trecho%")
    Optional<List<Episodio>> episodiosPorTrecho(String trecho);

    @Query("SELECT ep FROM Serie s JOIN s.episodios ep Where s = :serie ORDER BY ep.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("SELECT ep FROM Serie s JOIN s.episodios ep WHERE s = :serie AND YEAR(ep.dataLancamento) >= :ano ORDER BY ep.dataLancamento ASC")
    List<Episodio> episodiosAPartirDoAno(Serie serie,Integer ano);
}
