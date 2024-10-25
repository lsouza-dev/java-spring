package lsouza.dev.tabelafipe.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record Marca(@JsonAlias("codigo") Integer codigo,
                    @JsonAlias("nome") String marca) {
    @Override
    public String toString() {
        return "Codigo=" + codigo +
                "\tMarca='" + marca+"\n";
    }
}
