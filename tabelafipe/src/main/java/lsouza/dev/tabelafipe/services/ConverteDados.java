package lsouza.dev.tabelafipe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ConverteDados implements IConverteDados{
    @Override
    public <T> T converteDados(String json, Class<T> genericClass) {
        ObjectMapper mapper = new ObjectMapper();

        List<T> genericList = new ArrayList<>();
        try {
            return mapper.readValue(json,genericClass);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
