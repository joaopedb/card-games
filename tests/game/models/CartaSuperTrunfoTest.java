package game.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartaSuperTrunfoTest {

    @Test
    void gettersRetornamValoresDaCarta() {
        CartaSuperTrunfo carta = new CartaSuperTrunfo("1A", "Ryu", 76, 74, 68, 81, 67, false);

        assertEquals("Ryu", carta.getNome());
        assertEquals(76, carta.getForca());
        assertEquals(74, carta.getSaude());
        assertEquals(68, carta.getMobilidade());
        assertEquals(81, carta.getTecnicas());
        assertEquals(67, carta.getAlcance());
        assertFalse(carta.isSuperTrunfo());
    }

    @Test
    void cartaSuperTrunfoEhMarcada() {
        CartaSuperTrunfo akuma = new CartaSuperTrunfo("6B", "Akuma", 96, 47, 86, 98, 74, true);
        assertTrue(akuma.isSuperTrunfo());
    }

    @Test
    void getValorAtributoPorNome() {
        CartaSuperTrunfo carta = new CartaSuperTrunfo("1A", "Ryu", 76, 74, 68, 81, 67, false);
        assertEquals(76, carta.getValorAtributo("forca"));
        assertEquals(81, carta.getValorAtributo("tecnicas"));
    }

    @Test
    void getValorAtributoDesconhecidoRetornaZero() {
        CartaSuperTrunfo carta = new CartaSuperTrunfo("1A", "Ryu", 76, 74, 68, 81, 67, false);
        assertEquals(0, carta.getValorAtributo("inexistente"));
    }

    @Test
    void getNomesAtributosListaTodosOsAtributos() {
        CartaSuperTrunfo carta = new CartaSuperTrunfo("1A", "Ryu", 76, 74, 68, 81, 67, false);
        assertEquals(5, carta.getNomesAtributos().size());
        assertTrue(carta.getNomesAtributos().contains("forca"));
        assertTrue(carta.getNomesAtributos().contains("alcance"));
    }
}
