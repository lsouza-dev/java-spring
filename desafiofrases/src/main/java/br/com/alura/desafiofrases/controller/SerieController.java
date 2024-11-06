package br.com.alura.desafiofrases.controller;

import br.com.alura.desafiofrases.dto.SerieDTO;
import br.com.alura.desafiofrases.model.Serie;
import br.com.alura.desafiofrases.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping("/frases")
    private SerieDTO obterSerieSelecionada(){
        Random random = new Random();
        Serie ultimaSerie =  service.obterMaiorIndexSerie();
        long min = 0;
        long max = ultimaSerie.getId();
        Long id = random.nextLong(max - min + 1) + min;
        return service.obterSerie();
    }
}
