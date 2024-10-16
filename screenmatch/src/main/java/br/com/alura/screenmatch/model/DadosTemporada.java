package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") Integer temporada,
                             @JsonAlias("totalSeasons") Integer totalDeTemporadas,
                             @JsonAlias("Episodes") List<DadosEpisodio> episodios) {
}