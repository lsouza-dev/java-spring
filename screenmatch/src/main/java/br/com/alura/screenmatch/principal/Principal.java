package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();
    private SerieRepository serieRepository;


    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0){
            var menu = """
                \n
                1 - Buscar séries
                2 - Buscar episódios
                3 - Listar séries buscadas
                4 - Buscar série por título
                5 - Buscar série por ator
                6 - Buscar série por quantidade de temporadas
                7 - Buscar Top 5 séries
                8 - Buscar séries por categoria
                9 - Buscar série por quantidade máxima de temporadas 
                    e mínimo de avaliação
                
                0 - Sair                                 
                """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarSeriePorQuantidadeDeTemporada();
                    break;
                case 7:
                    buscarTopSeries();
                    break;
                case 8:
                    buscarSeriePorCategoria();
                    break;
                case 9:
                    buscarSeriePorMaxTemporadasEMinAvaliacao();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSeriePorMaxTemporadasEMinAvaliacao() {
        System.out.println("Digite a quantidade maxíma de temporadas: ");
        int qtdTemp = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Digite a avaliação mínima da série: ");
        double avalMin = leitura.nextDouble();
        leitura.nextLine();

        Optional<List<Serie>> series = serieRepository.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(qtdTemp,avalMin);
        if(series.isPresent()){
            series.ifPresent(s -> s.forEach(System.out::println));
        }else System.out.println("Não foi possível encontrar as séries com a quantidade de temporada e avaliação inseridas.");
    }

    private void buscarSeriePorCategoria() {
        System.out.println("Digite a categoria para busca:");
        var genero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(genero);
        Optional<List<Serie>> seriesPorCategoria = serieRepository.findByGenero(categoria);
        if(seriesPorCategoria.isPresent()){
            System.out.printf("Séries encontradas com a categoria: %s",genero);
            seriesPorCategoria.ifPresent(s -> s.forEach(System.out::println));
        }else System.out.printf("Não foram encontradas as séries com a categoria: %s",genero);
    }

    private void buscarTopSeries() {
        Optional<List<Serie>> topSeries = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        if(topSeries.isPresent()){
            System.out.println("Top 5 Séries:");
            topSeries.ifPresent(s -> s.forEach(System.out::println));
        }
        else System.out.println("Não foi possível encontrar as séries.");

    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        // Inserindo a série no banco
        serieRepository.save(serie);
        //dadosSeries.add(dados);

        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){

        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome:");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie.toLowerCase());

        if(serie.isPresent()) {
            List<DadosTemporada> temporadas = new ArrayList<>();
            var serieEncontrada = serie.get();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> listaDeEpisodios = temporadas.stream()
                    // Criando um novo fluxo de dados pegando a lista
                    // de episódios da temporada encontrada
                    .flatMap(t -> t.episodios().stream()
                            // Para cada episódio, será criado um novo episódio
                            // Passando o numero da temporadada e o episódio da iteração
                        .map(e -> new Episodio(t.temporada(),e)))
                    // Criando uma nova lista de episódio
                    .toList();

            serieEncontrada.setEpisodioList(listaDeEpisodios);
            serieRepository.save(serieEncontrada);

        }else System.out.println("Série não encontrada!");
    }

    private void listarSeriesBuscadas(){
        // Buscando todas as séries cadastradas no banco
        series = serieRepository.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getId))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome:");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serieBuscada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie.toLowerCase());

        if(serieBuscada.isPresent()) System.out.println("Dados da série: "+serieBuscada);
        else System.out.println("Série não encontrada!");
    }


    private void buscarSeriePorAtor() {
        System.out.println("Digite o nome do ator:");
        String nomeAtor = leitura.nextLine();
        System.out.println("Digite a avaliação mínima da série:");
        double mediaAvaliacao = leitura.nextDouble();
        leitura.nextLine();

        Optional<List<Serie>> series = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor,mediaAvaliacao);

        if(series.isPresent()) {
            System.out.println("Séries encontradas:\n" );
            series.ifPresent(s -> s.forEach(System.out::println));
        }
        else System.out.println("Não foi encontrada nenhuma série com o ator digitado!");
    }

    private void buscarSeriePorQuantidadeDeTemporada() {
        System.out.println("Digite a quantidade de temporadas: ");
        int qtdTemporadas = leitura.nextInt();
        leitura.nextLine();

        Optional<List<Serie>> series = serieRepository.findByTotalTemporadasGreaterThanEqual(qtdTemporadas);
        if(series.isPresent()){
            System.out.printf("Séries com mais de %d temporadas: ",qtdTemporadas);
            series.ifPresent(s -> s.forEach(System.out::println));
        }
        else System.out.println("Não foi possível contrar as séries!");
    }


}