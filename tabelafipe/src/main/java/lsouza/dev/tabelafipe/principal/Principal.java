package lsouza.dev.tabelafipe.principal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lsouza.dev.tabelafipe.records.Carro;
import lsouza.dev.tabelafipe.records.Marca;
import lsouza.dev.tabelafipe.services.Api;
import lsouza.dev.tabelafipe.services.ConverteDados;

import java.util.*;

public class Principal {
    Scanner scanner = new Scanner(System.in);
    ObjectMapper mapper = new ObjectMapper();
    Marca Marca;
    Api api = new Api();
    ConverteDados conversor = new ConverteDados();
    private final  String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    private String ENDERECOATUALIZADO;

    public void exibirMenu() {
        System.out.println("Digite o tipo do veículo:\nCarros\tMotos\tCaminhoes");
        String tipoVeiculo = scanner.nextLine();

        ENDERECOATUALIZADO = ENDERECO + tipoVeiculo.toLowerCase() + "/marcas/";

        var json = api.ConsumirApi(ENDERECOATUALIZADO);
        List<Marca> marcasList = new ArrayList<>();
        try {
            marcasList = mapper.readValue(json, new TypeReference<List<Marca>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        marcasList.forEach(m -> {
            System.out.println(String.format("Código: %d\tMarca: %s", m.codigo(), m.marca()));
        });

        System.out.println("Digite o código da marca escolhida:");
        var codMarca = scanner.nextInt();
        scanner.nextLine();

        ENDERECOATUALIZADO += String.format("%d/modelos", codMarca);
        json = api.ConsumirApi(ENDERECOATUALIZADO);
        //System.out.println(json);

        try {
           Carro carroEscolhido = mapper.readValue(json, new TypeReference<Carro>() {});
            System.out.println(carroEscolhido);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
