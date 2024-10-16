package br.com.alura.screenmatch.service;

public interface IConverteDados {
    <T> T obterDados(String json,Class<T> classe);
    // T é usado quando queremos passar algo genérico
    // No exemplo acima, quero usar e retornar uma classe,porém, ainda não sei
    // qual classe será usada, então definimos o generic.
}
