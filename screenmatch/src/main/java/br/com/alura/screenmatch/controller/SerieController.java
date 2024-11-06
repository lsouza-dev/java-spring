package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Atribuindo à classe o controller da API
@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    // Definindo que a rota do método abaixo é
    // https://......../series
    @GetMapping
    public List<SerieDTO> obterSeries(){
      return service.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series(){
        return service.obterTop5Series();

    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterUltimosLancamentos(){
        return service.obterUltimoslncamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterSeriePorId(@PathVariable long id){
        return service.obterSeriPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable long id){
        return  service.obterTodasTemporadas(id);
    }


    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadasPorNumero(@PathVariable long id,@PathVariable long numero){
        return service.obterTemporadasPorNumero(id,numero);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obterTopEpisodiosSerie(@PathVariable("id") long idTemporada){
        return  service.obterTopEpisodiosPorIdSerie(idTemporada);
    }

    @GetMapping("/categoria/{categoria}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String categoria){
        return service.obterSeriesPorCategoria(categoria);
    }
}
