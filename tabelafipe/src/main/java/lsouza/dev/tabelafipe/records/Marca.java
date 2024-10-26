package lsouza.dev.tabelafipe.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record Marca(@JsonAlias("codigo") Integer codigo,
                    @JsonAlias("nome") String marca) {

}
