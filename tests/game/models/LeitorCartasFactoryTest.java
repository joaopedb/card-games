package game.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeitorCartasFactoryTest {

    @Test
    void carregaTodasAsCartasDoJson() {
        List<CartaSuperTrunfo> cartas = LeitorCartasFactory.criarCartas("data/trunfoChars.json");
        assertEquals(32, cartas.size());
    }

    @Test
    void primeiraCartaEhRyu() {
        List<CartaSuperTrunfo> cartas = LeitorCartasFactory.criarCartas("data/trunfoChars.json");
        assertEquals("Ryu", cartas.get(0).getNome());
    }

    @Test
    void akumaEhASuperTrunfo() {
        List<CartaSuperTrunfo> cartas = LeitorCartasFactory.criarCartas("data/trunfoChars.json");
        long quantasSuperTrunfo = cartas.stream().filter(CartaSuperTrunfo::isSuperTrunfo).count();
        assertEquals(1, quantasSuperTrunfo);
        assertTrue(cartas.stream().anyMatch(c -> c.isSuperTrunfo() && c.getNome().equals("Akuma")));
    }

    @Test
    void atributosSaoCarregadosDoJson() {
        List<CartaSuperTrunfo> cartas = LeitorCartasFactory.criarCartas("data/trunfoChars.json");
        CartaSuperTrunfo zangief = cartas.stream()
                .filter(c -> c.getNome().equals("Zangief"))
                .findFirst()
                .orElseThrow();

        assertEquals(99, zangief.getSaude());
        assertEquals(97, zangief.getForca());
        assertEquals(18, zangief.getMobilidade());
    }

    @Test
    void arquivoInexistenteRetornaListaVaziaSemExcecao() {
        List<CartaSuperTrunfo> cartas = LeitorCartasFactory.criarCartas("data/arquivo-que-nao-existe.json");
        assertNotNull(cartas);
        assertTrue(cartas.isEmpty());
    }
}
