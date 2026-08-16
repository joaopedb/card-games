package game.models;

import framework.cartas.Carta;
import framework.excecoes.BaralhoVazioException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaralhoPadraoTest {

    private static class CartaSimples implements Carta {
        private final String nome;
        CartaSimples(String nome) { this.nome = nome; }
        @Override public String getNome() { return nome; }
    }

    private List<Carta> criarCartas(int quantidade) {
        List<Carta> cartas = new ArrayList<>();
        for (int i = 1; i <= quantidade; i++) {
            cartas.add(new CartaSimples("Carta " + i));
        }
        return cartas;
    }

    @Test
    void baralhoVazioNaoTemCartas() {
        BaralhoPadrao baralho = new BaralhoPadrao();
        assertFalse(baralho.temCartas());
    }

    @Test
    void baralhoComCartasTemCartas() {
        BaralhoPadrao baralho = new BaralhoPadrao(criarCartas(5));
        assertTrue(baralho.temCartas());
    }

    @Test
    void comprarCartaRemoveDoTopo() {
        List<Carta> cartas = criarCartas(3);
        BaralhoPadrao baralho = new BaralhoPadrao(cartas);
        assertEquals("Carta 1", baralho.comprarCarta().getNome());
        assertEquals("Carta 2", baralho.comprarCarta().getNome());
        assertEquals("Carta 3", baralho.comprarCarta().getNome());
        assertFalse(baralho.temCartas());
    }

    @Test
    void comprarCartaDeBaralhoVazioLancaExcecao() {
        BaralhoPadrao baralho = new BaralhoPadrao();
        assertThrows(BaralhoVazioException.class, baralho::comprarCarta);
    }

    @Test
    void embaralharPreservaQuantidadeDeCartas() {
        BaralhoPadrao baralho = new BaralhoPadrao(criarCartas(10));
        baralho.embaralhar();

        int contadas = 0;
        while (baralho.temCartas()) {
            assertNotNull(baralho.comprarCarta());
            contadas++;
        }
        assertEquals(10, contadas);
    }

    @Test
    void embaralharMudaAOrdemDasCartas() {
        // Com 20 cartas, é praticamente certo que a ordem muda após o embaralhamento
        BaralhoPadrao original = new BaralhoPadrao(criarCartas(20));
        List<String> antes = new ArrayList<>();
        while (original.temCartas()) {
            antes.add(original.comprarCarta().getNome());
        }

        BaralhoPadrao embaralhado = new BaralhoPadrao(criarCartas(20));
        embaralhado.embaralhar();
        List<String> depois = new ArrayList<>();
        while (embaralhado.temCartas()) {
            depois.add(embaralhado.comprarCarta().getNome());
        }

        assertNotEquals(antes, depois);
        assertEquals(antes.size(), depois.size());
    }
}
