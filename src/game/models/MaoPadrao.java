package game.models;

import framework.cartas.Carta;
import framework.jogadores.Mao;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação de mão usada pelo jogo Super Trunfo: uma lista simples de
 * cartas (baseada no contrato {@link framework.jogadores.Mao}). A coleção
 * interna é privada e só é manipulada através dos métodos da interface.
 */
public class MaoPadrao implements Mao {

    /** Coleção interna encapsulada: nunca é exposta diretamente. */
    private final List<Carta> cartas = new ArrayList<>();

    /**
     * Cria uma mão vazia.
     */
    public MaoPadrao() {
    }

    @Override
    public void adicionarCarta(Carta carta) {
        cartas.add(carta);
    }

    @Override
    public Carta removerCarta(int indice) {
        if (indice < 0 || indice >= cartas.size()) {
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
        return cartas.remove(indice);
    }

    @Override
    public int tamanho() {
        return cartas.size();
    }

    @Override
    public boolean estaVazia() {
        return cartas.isEmpty();
    }
}
