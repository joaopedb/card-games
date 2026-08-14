package framework.jogadores;

import framework.cartas.Carta;
import java.util.ArrayList;
import java.util.List;

public class MaoPadrao implements Mao {
    private final List<Carta> cartas = new ArrayList<>();

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
