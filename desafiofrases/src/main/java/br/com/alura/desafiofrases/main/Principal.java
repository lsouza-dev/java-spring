package br.com.alura.desafiofrases.main;

import br.com.alura.desafiofrases.model.Personagem;
import br.com.alura.desafiofrases.model.RSerie;
import br.com.alura.desafiofrases.model.Serie;
import br.com.alura.desafiofrases.repository.SerieRepository;
import br.com.alura.desafiofrases.service.ConsumoApi;
import br.com.alura.desafiofrases.service.ConverteDados;

import java.util.List;
import java.util.Scanner;

public class Principal {

    final String URL = "http://www.omdbapi.com/?t=";
    final String API_KEY = "&apikey=97a578a4";
    Scanner scanner = new Scanner(System.in);
    ConsumoApi api = new ConsumoApi();
    ConverteDados conversor = new ConverteDados();
    SerieRepository repository;

    public Principal(SerieRepository repository) {
        this.repository = repository;
    }


    public void showMenu(){
        var opcao = -1;
        while(opcao != 0){
                System.out.println("""
            ******************** BUSCA FRASES ***********************
            
            1 - Pesquisar Série
            2 - Cadastrar Personagem e Frase
            3 - Exibir Filmes
            4 - Exibir Frases
            
            0 - Sair
            Digite a opção desejada:
            """);
                opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao){
                    case 1:
                        System.out.println("Digite o nome do filme:");
                        var nomeFilme = scanner.nextLine();
                        var json = api.consumir(URL+nomeFilme.replace(" ","+")+API_KEY);
                        var rSerie = conversor.converter(json, RSerie.class);

                        if(rSerie.titulo() == null){
                            System.out.println("Não foi encontrada uma série com o nome inserido.");
                        }else{
                            Serie serie = new Serie(rSerie);
                            System.out.printf("Série: %s foi cadastrada com sucesso\n!",serie.getTitulo());
                            repository.save(serie);
                        }

                        break;
                    case 2:
                        var outroPersonagem = "s";
                        while (outroPersonagem.equalsIgnoreCase("s")) {
                            System.out.println("Digite um trecho do nome da série que o personagem pertence:");
                            var trecho = scanner.nextLine();
                            Serie serieBuscada = repository.obterSeriePorTrecho(trecho);
                            System.out.println(serieBuscada);
                            System.out.println("Digite o nome do personagem:");
                            var nomePersonagem = scanner.nextLine();
                            System.out.println("Digite a frase do personagem:");
                            var frase = scanner.nextLine();

                            Personagem personagem = new Personagem(nomePersonagem, frase, serieBuscada);
                            serieBuscada.getPersonagemList().add(personagem);
                            repository.save(serieBuscada);

                            System.out.printf("Personagem %s Adiconado com sucesso á : %s\n", nomePersonagem, serieBuscada.getTitulo());
                            System.out.println("Deseja adicionar outro personagem? (S/N)");
                            outroPersonagem = scanner.nextLine();
                        }
                        break;
                    case 3:
                        var series = repository.findAll();
                        series.forEach(System.out::println);
                        break;
                    case 4:
                        List<Personagem> personagens = repository.obterTodosOsPersonagens();
                        personagens.forEach(System.out::println);
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
        }
    }
}
