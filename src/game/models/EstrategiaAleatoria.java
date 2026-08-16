package game.models;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Estratégia aleatória: o computador escolhe um atributo ao acaso.
 * Útil para criar níveis de dificuldade diferentes ou partidas imprevisíveis.
 */
public class EstrategiaAleatoria implements EstrategiaEscolhaAtributo {

    /**
     * Cria a estratégia aleatória.
     */
    public EstrategiaAleatoria() {
    }

    /**
     * Escolhe um atributo ao acaso.
     */
    @Override
    public String escolherAtributo(CartaSuperTrunfo carta, List<String> atributos) {
        int indice = ThreadLocalRandom.current().nextInt(atributos.size());
        return atributos.get(indice);
    }
}
