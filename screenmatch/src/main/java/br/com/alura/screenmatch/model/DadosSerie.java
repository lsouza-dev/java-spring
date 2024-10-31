package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Ignora as propriedades do Json que não foram inseridas no Alias
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("Genre") String genero,
                         @JsonAlias("Plot") String sinopse,
                         @JsonAlias("Actors") String atores,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("imdbRating") String avaliacao) {
}
// JsonAlias é usado para pesquisar um atributo com o nome inserido no parâmetro
// logo após essa conversão, os valores serão atribuídos e exibidos nos atributos
// da classe DadosSerie