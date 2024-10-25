package lsouza.dev.tabelafipe.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Api {
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;


    public String ConsumirApi(String url){
        String json = "";

        client = HttpClient.newBuilder().build();
        request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        try {
            response = client.send(request,HttpResponse.BodyHandlers.ofString());
            json =    response.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return json;
    }

}
