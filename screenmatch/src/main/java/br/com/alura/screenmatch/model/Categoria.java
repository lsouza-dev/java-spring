package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action","Ação"),
    ROMANCE("Romance","Romance"),
    COMEDIA("Comedy","Comédia"),
    DRAMA("Drama","Drama"),
    CRIME("Crime","Crime"),
    ANIMACAO("Animation","Animação"),
    AVENTURA("Adventure","Aventura");

    private String categoriaOmdb;
    private String categoriaEmPortugues;

    Categoria(String categorialOmdb,String categoriaEmPortugues){
        this.categoriaOmdb = categorialOmdb;
        this.categoriaEmPortugues = categoriaEmPortugues;
    }

    public static  Categoria fromString(String text){
        // Recebe o valor do OMDB e compara com os contrutores inseridos acima em cada constante
        for(Categoria categoria:Categoria.values()){
            // Se o valor do construtor de cada categoria for igual a algum
            // dos gêneros encontrados no omdbi no momento da conversão, ele
            // atribuirá o valor convertido dentro da constante
            if(categoria.categoriaOmdb.equalsIgnoreCase(text)) return categoria;
        }
        throw  new IllegalArgumentException("Nenhuma categoria econtrada para a string buscada:" + text);
    }

    public static  Categoria fromPortugues(String text){
        // Recebe o valor do OMDB e compara com os contrutores inseridos acima em cada constante
        for(Categoria categoria:Categoria.values()){
            // Se o valor do construtor de cada categoria for igual a algum
            // dos gêneros encontrados no omdbi no momento da conversão, ele
            // atribuirá o valor convertido dentro da constante
            if(categoria.categoriaEmPortugues.equalsIgnoreCase(text)) return categoria;
        }
        throw  new IllegalArgumentException("Nenhuma categoria econtrada para a string buscada:" + text);
    }

}
