package game.models;

import framework.cartas.Carta;

/**
 * Estratégia de comparação entre duas cartas (padrão Strategy).
 * <p>
 * Permite trocar a regra de comparação do jogo sem alterar a partida.
 * A implementação concreta é específica do jogo: o Super Trunfo compara
 * atributos numéricos; outro jogo poderia comparar naipes, valores, etc.
 */
public interface EstrategiaComparacao {

    /**
     * Compara duas cartas segundo o atributo escolhido.
     *
     * @param carta1          a primeira carta
     * @param carta2          a segunda carta
     * @param atributoEscolhido o atributo a ser comparado
     * @return positivo se {@code carta1} vence, negativo se {@code carta2}
     *         vence e zero em caso de empate
     */
    int comparar(Carta carta1, Carta carta2, String atributoEscolhido);
}
