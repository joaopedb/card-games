package game.models;

import framework.cartas.Carta;

/**
 * Comparador do Super Trunfo (implementação de {@link EstrategiaComparacao}).
 * <p>
 * Aplica a regra especial do jogo: a carta super trunfo vence qualquer outra
 * independentemente do atributo escolhido. Fora isso, compara os valores
 * numéricos do atributo informado (comparação dinâmica por mapa de atributos).
 */
public class ComparadorSuperTrunfo implements EstrategiaComparacao {

    /**
     * Cria o comparador.
     */
    public ComparadorSuperTrunfo() {
    }

    @Override
    public int comparar(Carta c1, Carta c2, String atributo) {
        CartaSuperTrunfo carta1 = (CartaSuperTrunfo) c1;
        CartaSuperTrunfo carta2 = (CartaSuperTrunfo) c2;

        // Regra especial do Super Trunfo: a carta super trunfo vence qualquer outra,
        // independentemente do atributo escolhido.
        if (carta1.isSuperTrunfo() && !carta2.isSuperTrunfo()) {
            return 1;
        }
        if (carta2.isSuperTrunfo() && !carta1.isSuperTrunfo()) {
            return -1;
        }

        // Comparação dinâmica: funciona com qualquer atributo presente na carta,
        // inclusive atributos novos adicionados ao JSON.
        int valor1 = carta1.getValorAtributo(atributo);
        int valor2 = carta2.getValorAtributo(atributo);

        // Se o valor1 for maior, retorna um número positivo (carta1 ganha).
        // Se for menor, negativo. Se for igual, retorna zero.
        return Integer.compare(valor1, valor2);
    }
}
