package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private DadosSerie dadosSerie;
    private DadosTemporada dadosTemporada;
    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=97a578a4";
    Scanner leitura = new Scanner(System.in);

    public void exibeMenu(){

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

        System.out.println("\nTop 10 Episódios:");
        dadosEpisodios.stream()
                // Filtrando por cada episódio que a avaliação NÃO SEJA N/A
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro filtro(N/A): " + e))
                // Organizando a lista em ordem e depois colocando em forma decrescente
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação: " + e))
                //Limitando a lista a 5
                .limit(10)
                .peek(e -> System.out.println("Limite: " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Maeamentop: " + e))
                .forEach(System.out::println);

        List<Episodio> episodiosList = temporadas.stream()
                // Criando um novo fluxo de dados com a lista de episódios
                .flatMap(t -> t.episodios().stream()
                        // Criando cada Episódio utilizando o número da temporada e os dadosEpisodios
                        .map(dadosEpisodio -> new Episodio(t.temporada(),dadosEpisodio)))
                // Criando uma nova lista após o processo, no qual será armazenada dentro da episodiosList
                .collect(Collectors.toList());

        System.out.println("\n\nEpisódios:");
        episodiosList.forEach(System.out::println);

        //System.out.println("A partir de que ano você deseja ver os episodios?");
        //var ano = leitura.nextInt();
        //leitura.nextLine();

//        LocalDate dataBusca = LocalDate.of(ano,1,1);
//
//        // Definindo o formatador de data no padrão pt-BR
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodiosList.stream()
//                // Filtrando os filmes por filmes que não têm a data de lancamento null e que
//                // O ano de lançamento seja posterior >= o ano informado
//                .filter(ep -> ep.getDataLancamento() != null &&
//                        ep.getDataLancamento().isAfter(dataBusca))
//                .forEach(ep -> System.out.println(
//                        String.format("Temporada: %d \tEpisódio: %s \tAno Lançamento: %s",
//                                ep.getTemporada(),ep.getTitulo(),
//                                ep.getDataLancamento().format(formatter))
//                ));

        System.out.println("Digite um trecho do título que deseja buscar:");

        String trechoTitulo = leitura.nextLine();

        Optional<Episodio> episodioBuscado = episodiosList.stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(trechoTitulo.toLowerCase()))
                .peek(e -> System.out.println("Titulo Encontrado: "+  e))
                .findFirst();

        if(episodioBuscado.isPresent()) System.out.println("Episódio Encontrado!\nTemporada: " + episodioBuscado.get().getTemporada());
        else System.out.println("Episódio Não encontrado!");
    }
}
