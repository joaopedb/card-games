package game.models;

import framework.cartas.Carta;

public class ComparadorSuperTrunfo implements EstrategiaComparacao {

    @Override
    public int comparar(Carta c1, Carta c2, String atributo) {
        // Transformamos a carta genérica na nossa carta específica para ler os atributos
        CartaSuperTrunfo carta1 = (CartaSuperTrunfo) c1;
        CartaSuperTrunfo carta2 = (CartaSuperTrunfo) c2;

        int valor1 = pegarValorAtributo(carta1, atributo);
        int valor2 = pegarValorAtributo(carta2, atributo);

        // Se o valor1 for maior, retorna um número positivo (carta1 ganha). 
        // Se for menor, negativo. Se for igual, retorna zero.
        return Integer.compare(valor1, valor2);
    }

    private int pegarValorAtributo(CartaSuperTrunfo carta, String atributo) {
        switch (atributo.toLowerCase()) {
            case "forca": return carta.getForca();
            case "saude": return carta.getSaude();
            case "mobilidade": return carta.getMobilidade();
            case "tecnicas": return carta.getTecnicas();
            case "alcance": return carta.getAlcance();
            default: return 0;
        }
    }
}