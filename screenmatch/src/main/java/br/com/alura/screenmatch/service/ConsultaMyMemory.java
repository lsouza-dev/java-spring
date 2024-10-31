package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.DadosTraducao;

import java.net.URLEncoder;

public class ConsultaMyMemory {
    public static String obterTraducao(String text) {
        // Criando o conversor de dados
        ConverteDados conversor = new ConverteDados();

        // Criando a API
        ConsumoApi consumo = new ConsumoApi();

        // Definindo que os textos abaixo são parâmetros da querry
        String texto = URLEncoder.encode(text);
        final String langpair = URLEncoder.encode("en|pt-br");

        // Criando a URL completa
        String url = "https://api.mymemory.translated.net/get?q=" + texto + "&langpair=" + langpair;

        // Criando o json após o consumo da api
        var json = consumo.obterDados(url);

        // Armazenando os dados dentro da classe após converte-los à um objeto
        // Logo após, retornando o texo traduzido
        DadosTraducao traducao = conversor.obterDados(json, DadosTraducao.class);
        return traducao.dadosResposta().textoTraduzido();
    }
}