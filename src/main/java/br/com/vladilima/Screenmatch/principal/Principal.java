package br.com.vladilima.Screenmatch.principal;

import br.com.vladilima.Screenmatch.model.Episodio;
import br.com.vladilima.Screenmatch.model.Serie;
import br.com.vladilima.Screenmatch.model.Temporada;
import br.com.vladilima.Screenmatch.service.ConsumoAPI;
import br.com.vladilima.Screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=aaac8324";

    public void exibeMenu() {
        System.out.println("Informe o Nome da Série para Pesquisar:");
        var nomeSerie = scanner.nextLine().replaceAll(" ", "+");

        var busca = ADDRESS + nomeSerie + API_KEY;
        var json = consumoApi.obterDados(busca);

        Serie serie = conversor.obterDados(json, Serie.class);
        System.out.println(serie);

        List<Temporada> temporadaList = new ArrayList<>();

        for (int i = 1; i<= serie.totalTemporadas(); i++) {
            busca = ADDRESS + nomeSerie + "&season=" + i + API_KEY;
            json = consumoApi.obterDados(busca);
            Temporada temporada = conversor.obterDados(json, Temporada.class);
            temporadaList.add(temporada);
            System.out.println(temporada);
        }

        temporadaList.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<Episodio> episodioList = temporadaList.stream()
                .flatMap(t -> t.episodios().stream())
                .toList();

        System.out.println("Top 5 Episódios:");
        episodioList.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(Episodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

    }

}
