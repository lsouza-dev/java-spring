package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.service.ConsultaMyMemory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity // Definindo que será uma tabela no DB
@Table(name = "series") // Definindo o nome da tabela
public class Serie {
    @Id // Atribuindo ao id uma anotação que diz que será um ID no banco
    // Definindo a estrategia de geração do ID como IDENTITY =  AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;


    //@Transient // Ignora temporariamente esse atributo no banco
    // Dizendo que a relação é de 1 pra N
    // e Referenciando a serie como atributo
    // que faz essa conexão com a foreignKey
    // CascadeType.ALL define que todas as vezes que houver alteração
    // Na série, realizará a alteração nos episodios
    @OneToMany(mappedBy = "serie",cascade = CascadeType.ALL, fetch =FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();

    public Serie(){}

    public Serie(SerieDTO serieDTO){
        this.id = serieDTO.id();
        this.titulo = String.valueOf(serieDTO.titulo());
        this.totalTemporadas = serieDTO.totalTemporadas();
        this.avaliacao = serieDTO.avaliacao();
        this.genero = serieDTO.genero();
        this.atores = String.valueOf(serieDTO.atores());
        this.poster = serieDTO.poster();
        this.sinopse = serieDTO.sinopse();
    }

    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        // Utilizando um método estático para traduzir a sinopse da série
        this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()).trim();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        // Atribuindo para cada episódio a série no qual o setter é invocado
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return
                        "ID=" + id +
                        "genero=" + genero +
                        ", titulo='" + titulo + '\'' +
                        ", totalTemporadas=" + totalTemporadas +
                        ", avaliacao=" + avaliacao +
                        ", atores='" + atores + '\'' +
                        ", poster='" + poster + '\'' +
                        ", sinopse='" + sinopse + '\''+
                        ", episodios='" + episodios + '\'';
    }
}