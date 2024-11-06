package br.com.alura.desafiofrases.repository;

import br.com.alura.desafiofrases.model.Personagem;
import br.com.alura.desafiofrases.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie,Long> {


    @Query("SELECT s FROM Serie s WHERE s.titulo ILIKE %:trecho%")
    Serie obterSeriePorTrecho(String trecho);

    @Query("SELECT p FROM Serie s JOIN s.personagemList p")
    List<Personagem> obterTodosOsPersonagens();

    @Query("SELECT s FROM Serie s ORDER BY s.id DESC LIMIT 1")
    Serie obterUltimaSerieCadastrada();

    @Query("SELECT p FROM Serie s JOIN s.personagemList p WHERE s.id = :idSerie")
    List<Personagem> obterPersonagensPorSerie(long idSerie);
}
