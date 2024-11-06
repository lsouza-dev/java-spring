package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obterTodasAsSeries(){
        return converteDados(repository.findAll());

    }

    public List<SerieDTO> obterTop5Series(){
        return converteDados(repository.findTop5ByOrderByAvaliacaoDesc());
    }


    private List<SerieDTO> converteDados(List<Serie> series){
                return series.stream()
                .map( s -> new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(),s.getAvaliacao(),s.getGenero(),s.getAtores(),s.getPoster(),s.getSinopse()))
                .toList();
    }

    private List<EpisodioDTO> converteEpisodio(List<Episodio> episodios){
        return episodios.stream()
                .map(e -> new EpisodioDTO(e.getTemporada(),e.getNumero(),e.getTitulo()))
                .toList();
    }

    public List<SerieDTO> obterUltimoslncamentos(){return converteDados(repository.lancamentosMaisRecentes());}


    public SerieDTO obterSeriPorId(long id) {
        Optional<Serie> serieOptional =  repository.findById(id);
        if(serieOptional.isPresent()){
            Serie s = serieOptional.get();
            return  new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(),s.getAvaliacao(),s.getGenero(),s.getAtores(),s.getPoster(),s.getSinopse());
        }
        return  null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(long id) {
//        return repository.episodiosPorIdSerie(id).stream()
//                .map(e -> new EpisodioDTO(e.getTemporada(),e.getNumero(),e.getTitulo()))
//                .toList();
        //return converteEpisodio(repository.episodiosPorIdSerie(id));
        Optional<Serie> serieOptional =  repository.findById(id);
        if(serieOptional.isPresent()){
            Serie s = serieOptional.get();
            return  s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(),e.getNumero(),e.getTitulo()))
                    .toList();
        }
        return  null;

    }

    public List<EpisodioDTO> obterTemporadasPorNumero(long id, long numero) {
        return repository.obterEpisodiosPorTemporada(id,numero).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(),e.getNumero(),e.getTitulo()))
                .toList();
    }

    public List<SerieDTO> obterSeriesPorCategoria(String categoria) {
        Categoria cat  = Categoria.fromPortugues(categoria);
        return converteDados(repository.findByGenero(cat));
    }

    public List<EpisodioDTO> obterTopEpisodiosPorIdSerie(long idSerie) {
        return repository.topEpisodiosPorIdSerie(idSerie).stream()
                .map(e -> new EpisodioDTO(e.getNumero(),e.getTemporada(),e.getTitulo()))
                .toList();
    }
}
