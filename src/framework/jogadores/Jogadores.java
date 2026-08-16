package framework.jogadores;

import framework.cartas.Carta;

/**
 * Contrato de um jogador de cartas.
 * <p>
 * Ponto de extensão do framework: cada jogo cliente implementa os seus
 * jogadores com o comportamento que precisar (ex.: jogador com dinheiro,
 * jogador com estratégia de decisão embutida, etc.).
 */
public interface Jogadores {

    /**
     * Retorna o nome do jogador (ex.: "Você", "Computador", "Ana").
     *
     * @return o nome do jogador
     */
    String getNome();

    /**
     * Entrega uma carta ao jogador, adicionando-a à sua mão.
     *
     * @param carta a carta a ser recebida
     */
    void receberCarta(Carta carta);

    /**
     * Retorna a mão de cartas do jogador.
     *
     * @return a mão do jogador (nunca {@code null})
     */
    Mao getMao();
}
