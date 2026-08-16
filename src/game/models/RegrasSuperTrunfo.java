package game.models;

import framework.baralho.Baralho;
import framework.cartas.Carta;
import framework.jogadores.Jogadores;
import framework.regras.Regras;
import java.util.List;

/**
 * Regras do Super Trunfo: distribuição alternada das cartas entre os jogadores
 * e comparação por atributo numérico, delegada à estratégia de comparação
 * injetada (padrão Strategy). A regra especial da carta super trunfo fica
 * na estratégia de comparação.
 */
public class RegrasSuperTrunfo implements Regras {

    private final EstrategiaComparacao comparador;

    /**
     * Cria as regras do Super Trunfo com o comparador informado.
     *
     * @param comparador estratégia de comparação entre cartas
     */
    public RegrasSuperTrunfo(EstrategiaComparacao comparador) {
        this.comparador = comparador;
    }

    @Override
    public void distribuirCartas(Baralho baralho, List<? extends Jogadores> jogadores) {
        if (jogadores == null || jogadores.isEmpty()) {
            throw new IllegalArgumentException("É preciso haver pelo menos um jogador para distribuir cartas.");
        }
        baralho.embaralhar();
        int indice = 0;
        while (baralho.temCartas()) {
            jogadores.get(indice % jogadores.size()).receberCarta(baralho.comprarCarta());
            indice++;
        }
    }

    @Override
    public int comparar(Carta carta1, Carta carta2, String atributo) {
        return comparador.comparar(carta1, carta2, atributo);
    }
}
