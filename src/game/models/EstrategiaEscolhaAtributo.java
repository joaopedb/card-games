package game.models;

import java.util.List;

/**
 * Estratégia de decisão do jogador virtual (ou humano) na escolha do atributo
 * a disputar. Permite trocar o comportamento do computador sem alterar a partida
 * (ex.: aleatória, melhor atributo, etc.).
 */
public interface EstrategiaEscolhaAtributo {

    /**
     * Escolhe o atributo que a carta deve disputar.
     *
     * @param carta     a carta que está na disputa
     * @param atributos a lista de nomes de atributos disponíveis
     * @return o nome do atributo escolhido
     */
    String escolherAtributo(CartaSuperTrunfo carta, List<String> atributos);
}
