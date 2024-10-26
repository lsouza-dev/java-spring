package lsouza.dev.tabelafipe.models;

import java.util.List;

public class Modelo {
    private int codigo;
    private String modelo;
    private List<Ano> anos;

    public Modelo(int codigo, String modelo, List<Ano> anos) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.anos = anos;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public List<Ano> getAnos() {
        return anos;
    }

    public void setAnos(List<Ano> anos) {
        this.anos = anos;
    }

    @Override
    public String toString() {
        return "Código=" + codigo +
                "\tModelo='" + modelo + "\nAnos:" + anos;
    }
}
