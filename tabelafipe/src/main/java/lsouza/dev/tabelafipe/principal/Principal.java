package lsouza.dev.tabelafipe.principal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lsouza.dev.tabelafipe.records.*;
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
        System.out.println("\n***************************************\nDigite o tipo do veículo:\nCarros\nMotos\nCaminhoes");
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

        System.out.println("MARCAS:");
        marcasList.forEach(m -> {
            System.out.println(String.format("Código: %d\tMarca: %s", m.codigo(), m.marca()));
        });

        System.out.println("\nDigite o código da marca escolhida:");
        var codMarca = scanner.nextInt();
        scanner.nextLine();

        ENDERECOATUALIZADO += String.format("%d/modelos/", codMarca);
        json = api.ConsumirApi(ENDERECOATUALIZADO);
        //System.out.println(json);

        List<Modelo> modeloList;
        try {
            Veiculo veiculoEscolhido = mapper.readValue(json, new TypeReference<Veiculo>() {});
            modeloList = veiculoEscolhido.modelos();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nMODELOS:");
        modeloList.forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do veículo para consulta:");
        var trechoVeiculo = scanner.nextLine();

        List<Modelo> modeloProcurado = modeloList.stream()
                .filter(m -> m.nome().contains(trechoVeiculo) || m.nome().contains(trechoVeiculo.toUpperCase()))
                .toList();

        System.out.println("MODELOS ENCONTRADOS:");
        modeloProcurado.forEach(System.out::println);

        System.out.println("\nDigite o código para consultar valores:");
        var codConsulta = scanner.nextInt();
        scanner.nextLine();

        ENDERECOATUALIZADO += String.format("%d/anos/", codConsulta);
        json = api.ConsumirApi(ENDERECOATUALIZADO);
        List<Ano> anosVeiculoPesquisado;
        try {
             anosVeiculoPesquisado = mapper.readValue(json, new TypeReference<List<Ano>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<VeiculoFinal> veiculoFinalList = new ArrayList<>();
        anosVeiculoPesquisado.forEach(m -> {
            var pesquisaVeiculo = api.ConsumirApi(ENDERECOATUALIZADO+m.codigo());
            try {
                VeiculoFinal veiculoFinal = mapper.readValue(pesquisaVeiculo,VeiculoFinal.class);
                veiculoFinalList.add(veiculoFinal);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        veiculoFinalList.forEach(System.out::println);

        //https://parallelum.com.br/fipe/api/v1/carros/marcas/171/modelos/8958/anos/
    }
}
