package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Principal {
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private DadosSerie dadosSerie;
    private DadosTemporada dadosTemporada;
    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=97a578a4";

    public void exibeMenu(){
        Scanner leitura = new Scanner(System.in);
        System.out.println("Digite o nome da série:");

        var nomeSerie = leitura.nextLine();
        // Cosumindo a Api e obtendo o JSON da série
        var json = consumoApi.ObterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);

        conversor = new ConverteDados();
        // Convertendo o json para uma classe Record DadosSerie
        dadosSerie = conversor.obterDados(json,DadosSerie.class);
        System.out.println(dadosSerie);

        System.out.println("Deseja ver todas as temporadas?\n[1] Sim \t[2] Não\nResposta: ");
        var respostaTemporadas = leitura.nextInt();
        if(respostaTemporadas == 1) exibeTemporadas();
    }

    public void exibeTemporadas(){
        // Criando uma lista de Dados das temporadas e fazendo com que a iteração ocorra até o tamanho do total de temporadas
        // após realizar a iteração, a temporada será adicionada à lista de temporadas.
        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1 ; i <= dadosSerie.totalTemporadas(); i++){
            // Atualizando a URL da API para o JSON retornar os dados da temporada
            var json = consumoApi.ObterDados(String.format("%s%s&season=%d%s", ENDERECO,dadosSerie.titulo().replace(" ","+"),i,API_KEY));
            dadosTemporada = conversor.obterDados(json,DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        // Percorrendo cada temporada e acessando seus episódios
        // Percorrendo cada episódio e exibindo seu título

//        temporadas.forEach(temp -> temp.episodios().forEach(ep -> {
//            System.out.println(String.format("Episódio:%s\nAvaliação:%s\n\n",ep.titulo(),ep.avaliacao()));
//        }));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(temp -> temp.episodios().stream())
                .collect(Collectors.toList());
        // Se a lista precisa ser alterada em algum momento = collect(Collectors.toList());
        // Se a lista é imutável = .toList();

        System.out.println("\nTop 5 Episódios:");
        dadosEpisodios.stream()
                // Filtrando por cada episódio que a avaliação NÃO SEJA N/A
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                // Organizando a lista em ordem e depois colocando em forma decrescente
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                //Limitando a lista a 5
                .limit(5)
                .forEach(System.out::println);
    }
}
