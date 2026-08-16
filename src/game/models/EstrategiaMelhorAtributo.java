package game.models;

import java.util.List;

/**
 * Estratégia "esperta": o computador escolhe o atributo em que a própria carta
 * tem o maior valor, aumentando as chances de vencer a rodada.
 */
public class EstrategiaMelhorAtributo implements EstrategiaEscolhaAtributo {

    /**
     * Cria a estratégia de melhor atributo.
     */
    public EstrategiaMelhorAtributo() {
    }

    /**
     * Escolhe o atributo em que a carta tem o maior valor.
     */
    @Override
    public String escolherAtributo(CartaSuperTrunfo carta, List<String> atributos) {
        String melhor = atributos.get(0);
        for (String atributo : atributos) {
            if (carta.getValorAtributo(atributo) > carta.getValorAtributo(melhor)) {
                melhor = atributo;
            }
        }
        return melhor;
    }
}
