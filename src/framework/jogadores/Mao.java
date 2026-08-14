package framework.jogadores;

import framework.cartas.Carta;

public interface Mao {
    void adicionarCarta(Carta carta);
    Carta removerCarta(int indice);
    int tamanho();
    boolean estaVazia();
}
