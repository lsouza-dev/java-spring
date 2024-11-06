package br.com.alura.desafiofrases.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RSerie(@JsonAlias("Title") String titulo,
                     @JsonAlias("Poster") String poster) {
}
