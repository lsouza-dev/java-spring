package br.com.alura.desafiofrases.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados {
    ObjectMapper mapper = new ObjectMapper();

    public <T> T converter(String json,Class<T> classe){
        try {
            return  mapper.readValue(json,classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
