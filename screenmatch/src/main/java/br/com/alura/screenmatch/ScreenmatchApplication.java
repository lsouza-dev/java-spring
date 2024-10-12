package br.com.alura.screenmatch;

import br.com.alura.screenmatch.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Primeiro projeto Spring sem web");
		var consumoApi = new ConsumoApi();
		var json = consumoApi.ObterDados("http://www.omdbapi.com/?t=gilmore+girls&Season=1&apikey=97a578a4");
		//System.out.println(json);
		//json = consumoApi.ObterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
	}
}
