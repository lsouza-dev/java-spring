package br.com.alura.desafiofrases.service;

import br.com.alura.desafiofrases.dto.SerieDTO;
import br.com.alura.desafiofrases.model.Personagem;
import br.com.alura.desafiofrases.model.Serie;
import br.com.alura.desafiofrases.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SerieService {

    @Autowired
    SerieRepository repository;

    public SerieDTO obterSerie(){
        Random random = new Random();

        var lastSerieID = repository.findAll().getLast().getId();
        long randomID = random.nextLong(lastSerieID);
        Optional<Serie> optSerie =  repository.findById(randomID);
        if(optSerie.isPresent()){
            Serie s = optSerie.get();
            var personagens = repository.obterPersonagensPorSerie(s.getId());
            int randomPerson = random.nextInt(personagens.size());
            return new SerieDTO(s.getTitulo(),s.getPersonagemList().get(randomPerson).getFrase(),s.getPersonagemList().get(randomPerson).getPersonagem(),s.getPoster());
        } else return null;

    }

    public Serie obterMaiorIndexSerie() {
        return repository.obterUltimaSerieCadastrada();
    }

    public List<Personagem> obterPersonagensPorSerie(Long idSerie){
        return repository.obterPersonagensPorSerie(idSerie);
    }
}
