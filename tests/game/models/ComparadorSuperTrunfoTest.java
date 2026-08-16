package game.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComparadorSuperTrunfoTest {

    private final ComparadorSuperTrunfo comparador = new ComparadorSuperTrunfo();

    @Test
    void cartaComMaiorValorGanha() {
        CartaSuperTrunfo forte = new CartaSuperTrunfo("1A", "Forte", 90, 50, 50, 50, 50, false);
        CartaSuperTrunfo fraca = new CartaSuperTrunfo("1B", "Fraca", 40, 50, 50, 50, 50, false);

        assertTrue(comparador.comparar(forte, fraca, "forca") > 0);
        assertTrue(comparador.comparar(fraca, forte, "forca") < 0);
    }

    @Test
    void valoresIguaisRetornamZero() {
        CartaSuperTrunfo carta1 = new CartaSuperTrunfo("1A", "C1", 70, 50, 50, 50, 50, false);
        CartaSuperTrunfo carta2 = new CartaSuperTrunfo("1B", "C2", 70, 50, 50, 50, 50, false);

        assertEquals(0, comparador.comparar(carta1, carta2, "forca"));
    }

    @Test
    void superTrunfoVenceMesmoComValorMenor() {
        CartaSuperTrunfo akuma = new CartaSuperTrunfo("6B", "Akuma", 10, 10, 10, 10, 10, true);
        CartaSuperTrunfo forte = new CartaSuperTrunfo("1A", "Forte", 99, 99, 99, 99, 99, false);

        assertTrue(comparador.comparar(akuma, forte, "forca") > 0);
        assertTrue(comparador.comparar(forte, akuma, "forca") < 0);
    }

    @Test
    void superTrunfoVenceEmQualquerAtributo() {
        CartaSuperTrunfo akuma = new CartaSuperTrunfo("6B", "Akuma", 10, 10, 10, 10, 10, true);
        CartaSuperTrunfo forte = new CartaSuperTrunfo("1A", "Forte", 99, 99, 99, 99, 99, false);

        for (String atributo : akuma.getNomesAtributos()) {
            assertTrue(comparador.comparar(akuma, forte, atributo) > 0, "Deveria vencer em: " + atributo);
        }
    }

    @Test
    void superTrunfoVsSuperTrunfoComparaAtributo() {
        CartaSuperTrunfo akuma = new CartaSuperTrunfo("6B", "Akuma", 96, 47, 86, 98, 74, true);
        CartaSuperTrunfo outro = new CartaSuperTrunfo("7B", "Outro", 50, 50, 50, 50, 50, true);

        assertTrue(comparador.comparar(akuma, outro, "forca") > 0);
        assertTrue(comparador.comparar(outro, akuma, "forca") < 0);
    }
}
