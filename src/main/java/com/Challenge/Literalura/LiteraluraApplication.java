package com.Challenge.Literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.Challenge.Literalura.Repository.LibroRepository;
import com.Challenge.Literalura.Vista.Menu;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Menu menu = new Menu(repository);
		menu.iniciar();
	}

}
