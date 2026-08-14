package framework.baralho;

import framework.cartas.Carta;

public interface Baralho {
    void embaralhar();
    Carta comprarCarta();
    boolean temCartas();
}