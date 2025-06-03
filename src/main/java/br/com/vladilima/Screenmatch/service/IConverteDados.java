package br.com.vladilima.Screenmatch.service;

import br.com.vladilima.Screenmatch.model.Serie;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> classe);

}
