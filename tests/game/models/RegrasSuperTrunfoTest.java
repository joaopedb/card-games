package game.models;

import game.models.BaralhoPadrao;
import game.models.JogadorPadrao;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegrasSuperTrunfoTest {

    private final RegrasSuperTrunfo regras = new RegrasSuperTrunfo(new ComparadorSuperTrunfo());

    @Test
    void distribuirCartasAlternaEntreJogadores() {
        BaralhoPadrao baralho = new BaralhoPadrao(LeitorCartasFactory.criarCartas("data/trunfoChars.json"));
        JogadorPadrao jogadorA = new JogadorPadrao("A");
        JogadorPadrao jogadorB = new JogadorPadrao("B");

        regras.distribuirCartas(baralho, List.of(jogadorA, jogadorB));

        assertEquals(16, jogadorA.getMao().tamanho());
        assertEquals(16, jogadorB.getMao().tamanho());
        assertFalse(baralho.temCartas());
    }

    @Test
    void distribuirCartasParaTresJogadores() {
        BaralhoPadrao baralho = new BaralhoPadrao(LeitorCartasFactory.criarCartas("data/trunfoChars.json"));
        JogadorPadrao jogadorA = new JogadorPadrao("A");
        JogadorPadrao jogadorB = new JogadorPadrao("B");
        JogadorPadrao jogadorC = new JogadorPadrao("C");

        regras.distribuirCartas(baralho, List.of(jogadorA, jogadorB, jogadorC));

        assertEquals(11, jogadorA.getMao().tamanho());
        assertEquals(11, jogadorB.getMao().tamanho());
        assertEquals(10, jogadorC.getMao().tamanho());
        assertFalse(baralho.temCartas());
    }

    @Test
    void distribuirSemJogadoresLancaExcecao() {
        BaralhoPadrao baralho = new BaralhoPadrao(LeitorCartasFactory.criarCartas("data/trunfoChars.json"));
        assertThrows(IllegalArgumentException.class, () -> regras.distribuirCartas(baralho, List.of()));
    }

    @Test
    void compararDelegaParaEstrategiaDeComparacao() {
        CartaSuperTrunfo forte = new CartaSuperTrunfo("1A", "Forte", 90, 50, 50, 50, 50, false);
        CartaSuperTrunfo fraca = new CartaSuperTrunfo("1B", "Fraca", 40, 50, 50, 50, 50, false);

        assertTrue(regras.comparar(forte, fraca, "forca") > 0);
        assertTrue(regras.comparar(fraca, forte, "forca") < 0);
    }
}
