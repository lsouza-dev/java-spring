package br.com.alura.desafiofrases.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    private String poster;
    @OneToMany(mappedBy = "serie" ,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Personagem> personagemList;

    public  Serie(){}

    public Serie(RSerie rSerie){
        this.titulo = String.valueOf(rSerie.titulo());
        this.poster = String.valueOf(rSerie.poster());
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public List<Personagem> getPersonagemList() {
        return personagemList;
    }

    public void setPersonagemList(List<Personagem> personagemList) {
        this.personagemList = personagemList;
    }

    @Override
    public String toString() {
        return "Id=" + id +
                ", titulo='" + titulo + '\'' +
                ", poster='" + poster + '\'' +
                ", personagemList=" + personagemList ;
    }
}
