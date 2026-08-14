package game.models;

import framework.cartas.Carta;
import com.google.gson.annotations.SerializedName;

public class CartaSuperTrunfo implements Carta {
    private String id;
    private String nome;

    @SerializedName("super_trunfo")
    private boolean isSuperTrunfo;

    private Atributos atributos;

    private static class Atributos {
        int forca;
        int saude;
        int mobilidade;
        int tecnicas;
        int alcance;
    }

    public CartaSuperTrunfo(String id, String nome, int forca, int saude, int mobilidade, int tecnicas, int alcance, boolean isSuperTrunfo) {
        this.id = id;
        this.nome = nome;
        this.isSuperTrunfo = isSuperTrunfo;
        this.atributos = new Atributos();
        this.atributos.forca = forca;
        this.atributos.saude = saude;
        this.atributos.mobilidade = mobilidade;
        this.atributos.tecnicas = tecnicas;
        this.atributos.alcance = alcance;
    }

    @Override
    public String getNome() { return nome; }
    public int getForca() { return atributos.forca; }
    public int getSaude() { return atributos.saude; }
    public int getMobilidade() { return atributos.mobilidade; }
    public int getTecnicas() { return atributos.tecnicas; }
    public int getAlcance() { return atributos.alcance; }   
    public boolean isSuperTrunfo() { return isSuperTrunfo; }
}