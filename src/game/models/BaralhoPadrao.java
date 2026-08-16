package game.models;

import framework.baralho.Baralho;
import framework.cartas.Carta;
import framework.excecoes.BaralhoVazioException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementação de baralho usada pelo jogo Super Trunfo: recebe cartas,
 * embaralha e entrega uma a uma (baseada no contrato {@link framework.baralho.Baralho}).
 */
public class BaralhoPadrao implements Baralho {

    /** Coleção interna encapsulada: nunca é exposta diretamente. */
    private final List<Carta> cartas = new ArrayList<>();

    /**
     * Cria um baralho vazio.
     */
    public BaralhoPadrao() {
    }

    /**
     * Cria um baralho já com as cartas informadas.
     *
     * @param cartas as cartas iniciais do baralho
     */
    public BaralhoPadrao(List<? extends Carta> cartas) {
        this.cartas.addAll(cartas);
    }

    @Override
    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    @Override
    public Carta comprarCarta() {
        if (cartas.isEmpty()) {
            throw new BaralhoVazioException("Baralho vazio: não há cartas para comprar.");
        }
        return cartas.remove(0);
    }

    @Override
    public boolean temCartas() {
        return !cartas.isEmpty();
    }
}
