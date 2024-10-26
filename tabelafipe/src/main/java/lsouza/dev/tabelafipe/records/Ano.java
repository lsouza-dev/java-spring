package lsouza.dev.tabelafipe.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Ano(@JsonAlias("codigo") String codigo,
                  @JsonAlias("nome") String nome) {
}
