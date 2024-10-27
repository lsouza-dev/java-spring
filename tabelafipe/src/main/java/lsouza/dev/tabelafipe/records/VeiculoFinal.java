package lsouza.dev.tabelafipe.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VeiculoFinal(@JsonAlias("Valor") String valor,
                           @JsonAlias("Marca") String marca,
                           @JsonAlias("Modelo") String modelo   ,
                           @JsonAlias("AnoModelo") Integer ano  ,
                           @JsonAlias("Combustivel") String combustivel,
                           @JsonAlias("CodigoFipe") String codigoFipe,
                           @JsonAlias("MesReferencia") String mesReferencia,
                           @JsonAlias("SiglaCombustivel") String siglaCombustivel) {

    @Override
    public String toString() {
        return "Valor=" + valor + '\t' +
                "Marca=" + marca + '\t' +
                "Modelo='" + modelo + '\t' +
                "Ano=" + ano + '\t' +
                "Combustivel='" + combustivel + '\t' +
                "CodigoFipe='" + codigoFipe + '\t' +
                "MesReferencia='" + mesReferencia + '\t' +
                "SiglaCombustivel='" + siglaCombustivel ;
    }
}
