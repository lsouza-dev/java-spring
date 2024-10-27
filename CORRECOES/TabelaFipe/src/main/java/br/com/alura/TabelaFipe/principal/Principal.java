package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.services.ConsumoApi;
import br.com.alura.TabelaFipe.services.ConverteDados;
import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.Escaper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    Scanner reader = new Scanner(System.in);
    ConsumoApi api = new ConsumoApi();
    ConverteDados conversor = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    public void ExibeMenu(){
        var menu = """
                *** OPÇÕES ***
                Carro
                Moto
                Caminhao
                
                Digite uma das opções para consultar:
                """;
        System.out.println(menu);
        var opcao = reader.nextLine();
        String endereco;

        if(opcao.toLowerCase().contains(("carr"))) endereco = URL_BASE + "carros/marcas";
        else if(opcao.toLowerCase().contains(("mot"))) endereco = URL_BASE + "motos/marcas";
        else endereco = URL_BASE + "caminhoes/marcas";

        var json = api.obterDados(endereco);

        // Criando uma lista de marcas e ordenando as mesmas por código em ordem crescente
        var marcas = conversor.obterLista(json,Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach( d -> {
                    System.out.println(String.format("Código: %s\tNome: %s", d.codigo(),d.nome()));
                });
        System.out.println("\nInforme o código da marca para consulta:");
        var codMarca = reader.nextLine();

        // Atualizando a URL da Api e criando uma lista de modelos
        endereco += String.format("/%s/modelos", codMarca);
        json = api.obterDados(endereco);
        var modeloList = conversor.obterDados(json, Modelos.class);
        // Exibindo as listas de modelos por código
        System.out.println("\nModelos dessa marca:");
        modeloList.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(d -> {
                    System.out.println(String.format("Código: %s\tNome: %s", d.codigo(),d.nome()));
                });
        System.out.println("\nDigite um trecho do nome a ser buscado:");
        var trecho = reader.nextLine();

        // Criando uma nova lista com os modelos filtrados,
        // usando um filtro convertendo o nome para minúscula e
        // usando o trecho inserido para fazer a pesquisa, no final, transformando
        // a stream em uma nova lista
        List<Dados> modelosFiltrados = modeloList.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(trecho))
                .toList();

        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach( m -> {
            System.out.println(String.format("Código: %s\tNome: %s", m.codigo(),m.nome()));
        });

        System.out.println("Digite por favor o código do modelo para buscar os valores de avaliação:");
        var codModelo = reader.nextLine();

        // Atualizando a URL para pesquisar os anos
        endereco += String.format("/%s/anos", codModelo);
        json = api.obterDados(endereco);

        // Criando uma lista com os anos pesquisados
        List<Dados> anos = conversor.obterLista(json,Dados.class);

        List<Veiculo> veiculosList = new ArrayList<>();

        // Iterando utilizando todos os valores dentro da lista de anos
        for (int i = 0; i < anos.size(); i++) {
            // Atualizando a URL fazendo com que pesquise novamente a cada ano que houver
            // dentro da lista
            var enderecoAnos = endereco + String.format("/%s", anos.get(i).codigo());
            //Obtendo os dados e criando um objeto que recebe o veiculo encontrado
            // em cada iteração, logo após, adiciona o mesmo em uma lista de veiculos
            json = api.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json,Veiculo.class);
            veiculosList.add(veiculo);
        }

        // Exibindo os modelos dos veículos com todos os anos disponíveis
        System.out.println("\nTodos os veículos encontrados com avaliação por ano:");
        veiculosList.forEach(System.out::println);
    }



}
