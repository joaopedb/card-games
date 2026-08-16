package framework.jogadores;

import framework.cartas.Carta;

/**
 * Contrato da mão de cartas de um jogador.
 * <p>
 * Ponto de extensão do framework: cada jogo cliente pode definir regras de
 * mão próprias (ex.: mão com limite de cartas, mão ordenada por valor, etc.).
 * A coleção interna nunca é exposta diretamente — apenas via estes métodos.
 */
public interface Mao {

    /**
     * Adiciona uma carta à mão.
     *
     * @param carta a carta a ser adicionada
     */
    void adicionarCarta(Carta carta);

    /**
     * Remove e retorna a carta na posição indicada.
     *
     * @param indice posição da carta (baseado em zero)
     * @return a carta removida
     * @throws IndexOutOfBoundsException se o índice for inválido
     */
    Carta removerCarta(int indice);

    /**
     * Retorna a quantidade de cartas na mão.
     *
     * @return o número de cartas
     */
    int tamanho();

    /**
     * Verifica se a mão está sem cartas.
     *
     * @return {@code true} se a mão estiver vazia, {@code false} caso contrário
     */
    boolean estaVazia();
}
