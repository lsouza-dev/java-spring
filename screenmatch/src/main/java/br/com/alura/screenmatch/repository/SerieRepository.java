package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie,Long> {
    Serie findByTituloContainingIgnoreCase(String nomeSerie);
    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor,Double mediaAvaliacao);
    List<Serie> findByTotalTemporadasGreaterThanEqual(Integer qtdTemporadas);
    List<Serie> findTop5ByOrderByAvaliacaoDesc();
    List<Serie> findByGenero(Categoria categoria);
    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer maxTemporadas,Double minAvaliacao);

    // JPQL é uma estrutura de sintaxe para DB
    // Select * se torna Select apelidoSerie, o apelidoSerie é apenas uma forma
    // de referenciar a entidade de você deseja mapear na na consulta
    // nesse caso, a entidade é Serie.
    // Para referenciarmos os atributos/coluna da série na consulta, inserimos antes da
    // variável de parâmetro do método dois pontos : , dessa forma, a JPQL entende que está sendo
    // passado uma "variavel" dentro da consulta, tornando a consulta dinâmica
    @Query("SELECT apelidoSerie FROM Serie apelidoSerie WHERE apelidoSerie.totalTemporadas <= :maxTemporadas AND apelidoSerie.avaliacao >= :minAvaliacao")
    List<Serie> seriePorTemporadaEAvaliacao(Integer maxTemporadas,Double minAvaliacao);


    @Query("SELECT s FROM Serie s " +
            "JOIN s.episodios e " +
            "GROUP BY s " +
            "ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> lancamentosMaisRecentes();


    @Query("SELECT ep FROM Serie s JOIN s.episodios ep WHERE s.id = :id")
    List<Episodio> episodiosPorIdSerie(long id);

    @Query("SELECT e FROM Serie s JOIN s.episodios e " +
            "WHERE s.id = :id " +
            "AND e.temporada = :numero")
    List<Episodio> obterEpisodiosPorTemporada(Long id, Long numero);

    // Selecionar episodio De Serie s UNINDO as colunas pela Serie.episodios
    // e buscando pelos episodios com titulo ILIKE (lower case) que contenham %trecho%
    @Query("SELECT ep FROM Serie s JOIN s.episodios ep WHERE ep.titulo ILIKE %:trecho%")
    List<Episodio> episodiosPorTrecho(String trecho);

    @Query("SELECT ep FROM Serie s JOIN s.episodios ep Where s = :serie ORDER BY ep.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("SELECT ep FROM Serie s JOIN s.episodios ep WHERE s.id = :idSerie ORDER BY ep.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorIdSerie(long idSerie);

    @Query("SELECT ep FROM Serie s JOIN s.episodios ep WHERE s = :serie AND YEAR(ep.dataLancamento) >= :ano ORDER BY ep.dataLancamento ASC")
    List<Episodio> episodiosAPartirDoAno(Serie serie,Integer ano);

}
