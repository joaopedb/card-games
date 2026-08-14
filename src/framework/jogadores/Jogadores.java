package framework.jogadores;

import framework.cartas.Carta;

public interface Jogadores {
    String getNome();
    void receberCarta(Carta carta);
    Mao getMao();
}
