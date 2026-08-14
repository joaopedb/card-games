package game.models;

import framework.cartas.Carta;

public interface EstrategiaComparacao {
    int comparar(Carta carta1, Carta carta2, String atributoEscolhido);
}
