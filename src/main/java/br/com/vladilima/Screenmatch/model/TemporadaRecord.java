package br.com.vladilima.Screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TemporadaRecord(@JsonAlias("Season") Integer numero,
                              @JsonAlias("Episodes") List<EpisodioRecord> episodioRecords) {
}
