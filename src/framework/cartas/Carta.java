package framework.cartas;

/**
 * Contrato mínimo de uma carta de jogo.
 * <p>
 * Cada jogo cliente implementa esta interface com seus próprios dados
 * (ex.: {@code CartaSuperTrunfo} tem atributos numéricos; uma carta de baralho
 * comum teria naipe e valor). O framework nunca conhece os detalhes
 * específicos da carta — apenas o nome.
 */
public interface Carta {

    /**
     * Retorna o nome identificador da carta (ex.: "Ryu", "Ás de Espadas").
     *
     * @return o nome da carta
     */
    String getNome();
}
