package br.com.alura.aluramusics.principal;

import br.com.alura.aluramusics.model.Artista;
import br.com.alura.aluramusics.model.Musica;
import br.com.alura.aluramusics.model.TipoArtista;
import br.com.alura.aluramusics.repository.ArtistaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private ArtistaRepository repository;
    private Scanner scanner = new Scanner(System.in);
    private List<Artista> artistasBusca;
    //private List<Musica> musicasBusca;

    public Principal(ArtistaRepository repository) {
        this.repository = repository;
    }

    public void exibirMenu() {
        var opcao = -1;
        while (opcao != 0) {
            System.out.println("""
                    ********** Alura Musics *********
                    
                    1 - Adicionar Artista
                    2 - Adicionar Musica
                    3 - Exibir Artistas
                    4 - Exibir Musicas
                    5 - Buscar Artistas Por ID
                    6 - Buscar Musicas Por ID
                    7 - Buscar Musicas por Artista
                    
                    0 - SAIR
                    
                    """);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarArtista();
                    break;
                case 2:
                    adicionarMusica();
                    break;
                case 3:
                    exibirArtistas();
                    break;
                case 4:
                    exibirMusicas();
                    break;

                case 5:
                    buscarArtistas();
                    break;
                case 6:
                    buscarMusicas();
                    break;
                case 7:
                    buscarMusicasPorArtista();
                    break;
                case 0:
                    System.out.println("Saindo da aplicação...");
                    break;

                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

    private void adicionarArtista() {
        var cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("S")){
            System.out.println("Informe o nome do artista:");
            var nome = scanner.nextLine();
            System.out.println("Informe o tipo desse artista: (solo,dupla ou banda)");
            var tipo = scanner.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());

            // Criando um artista
            Artista artista = new Artista(nome,tipoArtista);
            // Salvando o artista
            repository.save(artista);
            System.out.println("Cadastrar novo artista? (S/N)");
            cadastrarNovo = scanner.nextLine();
        }
    }

    private void adicionarMusica() {
        System.out.println("Cadastrar música de que artista?");
        var nome = scanner.nextLine();
        // Buscando o artista no banco
        Optional<Artista> artista = repository.findByNomeContainingIgnoreCase(nome);
        if(artista.isPresent()){
            System.out.println("Digite o título da música:");
            var nomeMusica = scanner.nextLine();
            Musica musica = new Musica(nomeMusica);
            // Atribuindo à musica o artista
            musica.setArtista(artista.get());
            // Atribuindo ao artista a musica
            artista.get().getMusicas().add(musica);
            // Salvando o artista encontrado
            repository.save(artista.get());
        }else System.out.println("Artista não encontrado.");
    }

    private void exibirArtistas() {
        List<Artista> artistas = repository.findAll();
        artistas.forEach(System.out::println);
    }

    private void exibirMusicas() {
        List<Artista> artistas = repository.findAll();
        artistas.forEach(a -> a.getMusicas()
                .forEach(System.out::println));
    }

    private void buscarArtistas() {
        
    }

    private void buscarMusicas() {
    }

    private void buscarMusicasPorArtista() {
    }
}