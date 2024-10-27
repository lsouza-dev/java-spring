package lsouza.dev.tabelafipe.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(@JsonAlias("modelos")List<Modelo> modelos,
                      @JsonAlias("anos") List<Ano> anos) {
}
