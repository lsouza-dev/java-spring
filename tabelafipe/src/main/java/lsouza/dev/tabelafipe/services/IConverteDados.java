package lsouza.dev.tabelafipe.services;

public interface IConverteDados {
    <T> T converteDados(String json,Class<T> genericClass);
}
