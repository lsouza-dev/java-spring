package lsouza.dev.tabelafipe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{
    @Override
    public <T> T converteDados(String json, Class<T> genericClass) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json,genericClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
