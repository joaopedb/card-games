package framework.baralho;

import framework.cartas.Carta;

/**
 * Contrato de um baralho de cartas.
 * <p>
 * Ponto de extensão do framework: cada jogo cliente implementa o seu próprio
 * baralho (ex.: baralho com cartas repetidas, baralho infinito, etc.).
 */
public interface Baralho {

    /**
     * Embaralha as cartas do baralho, alterando a ordem de compra.
     */
    void embaralhar();

    /**
     * Remove e retorna a carta do topo do baralho.
     *
     * @return a carta comprada
     * @throws IllegalStateException se o baralho estiver vazio
     */
    Carta comprarCarta();

    /**
     * Verifica se ainda há cartas disponíveis para compra.
     *
     * @return {@code true} se houver pelo menos uma carta, {@code false} caso contrário
     */
    boolean temCartas();
}