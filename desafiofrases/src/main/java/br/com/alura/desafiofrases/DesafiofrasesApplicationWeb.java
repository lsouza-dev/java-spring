package br.com.alura.desafiofrases;

import br.com.alura.desafiofrases.main.Principal;
import br.com.alura.desafiofrases.repository.SerieRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class DesafiofrasesApplicationWeb {

		@Autowired
		private SerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DesafiofrasesApplicationWeb.class, args);
	}
}
