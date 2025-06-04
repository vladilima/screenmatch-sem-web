package br.com.vladilima.Screenmatch;

import br.com.vladilima.Screenmatch.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class
ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal menu = new Principal();

		menu.exibeMenu();

//		endereco = "https://www.omdbapi.com/?t=" + "supernatural&season=1&episode=2" + APIKey;
//		json = consumoApi.obterDados(endereco);
//		Episodio episodio = conversor.obterDados(json, Episodio.class);
//		System.out.println(episodio);

	}
}
