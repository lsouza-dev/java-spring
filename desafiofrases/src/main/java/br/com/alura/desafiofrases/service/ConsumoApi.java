package br.com.alura.desafiofrases.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
    HttpClient client;
    HttpRequest request;
    HttpResponse<String>response;


    public String consumir(String URL){
        client = HttpClient.newBuilder().build();
        request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
        try {
            response = client.send(request,HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
