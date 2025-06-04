package br.com.vladilima.Screenmatch.principal;

import br.com.vladilima.Screenmatch.model.Episodio;
import br.com.vladilima.Screenmatch.model.EpisodioRecord;
import br.com.vladilima.Screenmatch.model.SerieRecord;
import br.com.vladilima.Screenmatch.model.TemporadaRecord;
import br.com.vladilima.Screenmatch.service.ConsumoAPI;
import br.com.vladilima.Screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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

        SerieRecord serieRecord = conversor.obterDados(json, SerieRecord.class);
        System.out.println(serieRecord);

        List<TemporadaRecord> temporadaRecordList = new ArrayList<>();

        for (int i = 1; i<= serieRecord.totalTemporadas(); i++) {
            busca = ADDRESS + nomeSerie + "&season=" + i + API_KEY;
            json = consumoApi.obterDados(busca);
            TemporadaRecord temporadaRecord = conversor.obterDados(json, TemporadaRecord.class);
            temporadaRecordList.add(temporadaRecord);
            //System.out.println(temporadaRecord);
        }

        List<EpisodioRecord> episodioRecordList = temporadaRecordList.stream()
                .flatMap(t -> t.episodioRecords().stream())
                .toList();

//        -----------------------
//        System.out.println("Top 5 Episódios:");
//        episodioRecordList.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(EpisodioRecord::avaliacao).reversed())
//                .limit(5)
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadaRecordList.stream()
                .flatMap(t -> t.episodioRecords().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).toList();

        //episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios?");
        var ano = scanner.nextInt();
        scanner.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataDeLancamento() != null && e.getDataDeLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Título: " + e.getTitulo() +
                        ". Temporada: " + e.getTemporada() +
                        ". Data de Lançamento: " + e.getDataDeLancamento().format(formatter) + "."
                ));

    }

}
