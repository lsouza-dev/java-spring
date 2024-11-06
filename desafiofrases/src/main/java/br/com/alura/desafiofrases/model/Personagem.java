package br.com.alura.desafiofrases.model;

import jakarta.persistence.*;

@Entity
@Table (name = "personagens")
public class Personagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String personagem;
    private String frase;
    @ManyToOne
    private Serie serie;

    public Personagem(){}

    public Personagem(String nomePersonagem, String frase, Serie serieBuscada) {
        this.personagem = nomePersonagem;
        this.frase = frase;
        this.serie = serieBuscada;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonagem() {
        return personagem;
    }

    public void setPersonagem(String personagem) {
        this.personagem = personagem;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "Id=" + id +
                ", personagem='" + personagem + '\'' +
                ", frase='" + frase + '\'' +
                ", serie=" + serie.getTitulo();
    }
}
