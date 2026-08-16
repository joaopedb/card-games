package game.models;

import framework.cartas.Carta;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Carta do Super Trunfo.
 * Os atributos ficam em um mapa (nome -> valor), então qualquer atributo
 * presente no JSON é carregado automaticamente, sem precisar mudar o código
 * quando um novo atributo for adicionado.
 */
public class CartaSuperTrunfo implements Carta {
    private String id;
    private String nome;

    @SerializedName("super_trunfo")
    private boolean isSuperTrunfo;

    private Map<String, Integer> atributos = new LinkedHashMap<>();

    /**
     * Cria uma carta do Super Trunfo com os cinco atributos padrão.
     *
     * @param id          identificador da carta (ex.: "1A")
     * @param nome        nome da carta (ex.: "Ryu")
     * @param forca       valor do atributo força
     * @param saude       valor do atributo saúde
     * @param mobilidade  valor do atributo mobilidade
     * @param tecnicas    valor do atributo técnicas
     * @param alcance     valor do atributo alcance
     * @param isSuperTrunfo se a carta é a super trunfo
     */
    public CartaSuperTrunfo(String id, String nome, int forca, int saude, int mobilidade, int tecnicas, int alcance, boolean isSuperTrunfo) {
        this.id = id;
        this.nome = nome;
        this.isSuperTrunfo = isSuperTrunfo;
        this.atributos.put("forca", forca);
        this.atributos.put("saude", saude);
        this.atributos.put("mobilidade", mobilidade);
        this.atributos.put("tecnicas", tecnicas);
        this.atributos.put("alcance", alcance);
    }

    /**
     * Retorna o nome da carta (ex.: "Ryu").
     *
     * @return o nome da carta
     */
    @Override
    public String getNome() { return nome; }

    /**
     * Retorna o valor do atributo "forca".
     *
     * @return o valor de força da carta
     */
    public int getForca() { return getValorAtributo("forca"); }

    /**
     * Retorna o valor do atributo "saude".
     *
     * @return o valor de saúde da carta
     */
    public int getSaude() { return getValorAtributo("saude"); }

    /**
     * Retorna o valor do atributo "mobilidade".
     *
     * @return o valor de mobilidade da carta
     */
    public int getMobilidade() { return getValorAtributo("mobilidade"); }

    /**
     * Retorna o valor do atributo "tecnicas".
     *
     * @return o valor de técnicas da carta
     */
    public int getTecnicas() { return getValorAtributo("tecnicas"); }

    /**
     * Retorna o valor do atributo "alcance".
     *
     * @return o valor de alcance da carta
     */
    public int getAlcance() { return getValorAtributo("alcance"); }

    /**
     * Indica se esta é a carta super trunfo (vence qualquer outra).
     *
     * @return {@code true} se for a carta super trunfo
     */
    public boolean isSuperTrunfo() { return isSuperTrunfo; }

    /**
     * Retorna os nomes de todos os atributos da carta, na ordem do JSON.
     *
     * @return lista com os nomes dos atributos
     */
    public List<String> getNomesAtributos() {
        return new ArrayList<>(atributos.keySet());
    }

    /**
     * Retorna o valor de um atributo pelo nome.
     *
     * @param atributo o nome do atributo (ex.: "forca")
     * @return o valor do atributo, ou {@code 0} se o atributo não existir
     */
    public int getValorAtributo(String atributo) {
        Integer valor = atributos.get(atributo);
        return valor == null ? 0 : valor;
    }
}
