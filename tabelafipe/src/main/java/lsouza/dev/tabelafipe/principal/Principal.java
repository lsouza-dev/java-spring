package lsouza.dev.tabelafipe.principal;

import lsouza.dev.tabelafipe.models.Marca;
import lsouza.dev.tabelafipe.services.Api;
import lsouza.dev.tabelafipe.services.ConverteDados;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Scanner;

public class Principal {
    Scanner scanner = new Scanner(System.in);
    Marca marca;
    Api api = new Api();
    ConverteDados conversor = new ConverteDados();
    private final  String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    public void exibirMenu(){
        System.out.println("Digite o tipo do veículo:\nCarros\tMotos\tCaminhoes");
        String tipoVeiculo = scanner.nextLine();
        var json = api.ConsumirApi(ENDERECO + tipoVeiculo +"/marcas/");


        System.out.println(json);
    }
}
