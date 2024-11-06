package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();
    private SerieRepository serieRepository;
    private Serie serieBusca;

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
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
                        e mínimo de avaliação (Derived Query)
                    10 - Buscar série por quantidade máxima de temporadas 
                        e mínimo de avaliação (JPQL)
                    11 - Buscar episódio por trecho (JPQL)
                    12 - Top episódios por série
                    13 - Buscar episódios a partir de uma data
                    
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
                case 10:
                    buscarSerieMaxTempEMinAvalJPQL();
                    break;
                case 11:
                    buscarEpisodioPorTrecho();
                    break;
                case 12:
                    topEpisodiosPorSerie();
                    break;
                case 13:
                    buscarEpisodiosPorSerieEAno();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }


    private void buscarSeriePorCategoria() {
        System.out.println("Digite a categoria para busca:");
        var genero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(genero);
        List<Serie> seriesPorCategoria = serieRepository.findByGenero(categoria);

        System.out.printf("Séries encontradas com a categoria: %s", genero);
        seriesPorCategoria.forEach(System.out::println);

    }

    private void buscarTopSeries() {
        List<Serie> topSeries = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        topSeries.forEach(System.out::println);
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

    private void buscarEpisodioPorSerie() {

        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome:");
        var nomeSerie = leitura.nextLine();

        Serie serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie.toLowerCase());

        List<DadosTemporada> temporadas = new ArrayList<>();


        for (int i = 1; i <= serie.getTotalTemporadas(); i++) {
            var json = consumo.obterDados(ENDERECO + serie.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
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
                        .map(e -> new Episodio(t.temporada(), e)))
                // Criando uma nova lista de episódio
                .toList();

        serie.setEpisodios(listaDeEpisodios);
        serieRepository.save(serie);


    }

    private void listarSeriesBuscadas() {
        // Buscando todas as séries cadastradas no banco
        series = serieRepository.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getId))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome:");
        var nomeSerie = leitura.nextLine();
        serieBusca = serieRepository.findByTituloContainingIgnoreCase(nomeSerie.toLowerCase());

        System.out.println("Dados da série: " + serieBusca);
    }


    private void buscarSeriePorAtor() {
        System.out.println("Digite o nome do ator:");
        String nomeAtor = leitura.nextLine();
        System.out.println("Digite a avaliação mínima da série:");
        double mediaAvaliacao = leitura.nextDouble();
        leitura.nextLine();

        List<Serie> series = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, mediaAvaliacao);


        System.out.println("Séries encontradas:\n");
        series.forEach(System.out::println);


    }

    private void buscarSeriePorQuantidadeDeTemporada() {
        System.out.println("Digite a quantidade de temporadas: ");
        int qtdTemporadas = leitura.nextInt();
        leitura.nextLine();

        List<Serie> series = serieRepository.findByTotalTemporadasGreaterThanEqual(qtdTemporadas);

        System.out.printf("Séries com mais de %d temporadas: ", qtdTemporadas);
        series.forEach(System.out::println);
    }

    private void buscarSeriePorMaxTemporadasEMinAvaliacao() {
        System.out.println("Digite a quantidade maxíma de temporadas: ");
        int qtdTemp = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Digite a avaliação mínima da série: ");
        double avalMin = leitura.nextDouble();
        leitura.nextLine();

        List<Serie> series = serieRepository.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(qtdTemp, avalMin);

        series.forEach(System.out::println);

    }

    private void buscarSerieMaxTempEMinAvalJPQL() {
        System.out.println("Qual a quantidade máxima de temporadas?");
        var qtdTemporadas = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Qual a avaliação mínima?");
        var minAvaliacao = leitura.nextDouble();
        leitura.nextLine();
        List<Serie> series = serieRepository.seriePorTemporadaEAvaliacao(qtdTemporadas, minAvaliacao);

        System.out.println("Séries encontradas:");
        series.forEach(System.out::println);

    }

    private void buscarEpisodioPorTrecho() {
        System.out.println("Digite um trecho do título: ");
        String trecho = leitura.nextLine();
        List<Episodio> episodios = serieRepository.episodiosPorTrecho(trecho.toLowerCase());

        System.out.printf("Episódios encontrado com o trecho: %s\n", trecho);
        episodios.forEach(System.out::println);
    }

    private void topEpisodiosPorSerie() {

        Serie serie = serieBusca;
        List<Episodio> topEpisodios = serieRepository.topEpisodiosPorSerie(serie);
        topEpisodios.forEach(e -> {
            System.out.printf("Série: %s - Episódio: %d - Titulo: %s - Avaliação: %.2f \n",
                    e.getSerie().getTitulo(), e.getNumero(),
                    e.getTitulo(), e.getAvaliacao());
        });
    }

    private void buscarEpisodiosPorSerieEAno() {
        buscarSeriePorTitulo();

        Serie serie = serieBusca;
        System.out.println("Digite a partir de qual ano deseja buscar a série:");
        Integer ano = leitura.nextInt();
        leitura.nextLine();
        List<Episodio> episodios = serieRepository.episodiosAPartirDoAno(serie, ano);
        episodios.forEach(e -> {
            System.out.printf("Série: %s - Episódio: %d - Titulo: %s - Data: %s\n",
                    e.getSerie().getTitulo(), e.getNumero(),
                    e.getTitulo(), e.getDataLancamento().toString());
        });

    }
}

