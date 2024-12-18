package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import jakarta.persistence.*;

public record SerieDTO
        (long id,
         String titulo,
         Integer totalTemporadas,
         Double avaliacao,
         Categoria genero,
         String atores,
         String poster,
         String sinopse
        ) {
}
