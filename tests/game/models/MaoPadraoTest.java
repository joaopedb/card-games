package game.models;

import framework.cartas.Carta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaoPadraoTest {

    private static class CartaSimples implements Carta {
        private final String nome;
        CartaSimples(String nome) { this.nome = nome; }
        @Override public String getNome() { return nome; }
    }

    @Test
    void maoNovaEstaVazia() {
        MaoPadrao mao = new MaoPadrao();
        assertTrue(mao.estaVazia());
        assertEquals(0, mao.tamanho());
    }

    @Test
    void adicionarCartaAumentaTamanho() {
        MaoPadrao mao = new MaoPadrao();
        mao.adicionarCarta(new CartaSimples("A"));
        mao.adicionarCarta(new CartaSimples("B"));
        assertEquals(2, mao.tamanho());
        assertFalse(mao.estaVazia());
    }

    @Test
    void removerCartaRetornaCartaNoIndice() {
        MaoPadrao mao = new MaoPadrao();
        mao.adicionarCarta(new CartaSimples("A"));
        mao.adicionarCarta(new CartaSimples("B"));
        mao.adicionarCarta(new CartaSimples("C"));

        Carta removida = mao.removerCarta(1);
        assertEquals("B", removida.getNome());
        assertEquals(2, mao.tamanho());
    }

    @Test
    void removerCartaComIndiceInvalidoLancaExcecao() {
        MaoPadrao mao = new MaoPadrao();
        mao.adicionarCarta(new CartaSimples("A"));

        assertThrows(IndexOutOfBoundsException.class, () -> mao.removerCarta(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> mao.removerCarta(1));
    }

    @Test
    void removerTodasAsCartasDeixaMaoVazia() {
        MaoPadrao mao = new MaoPadrao();
        mao.adicionarCarta(new CartaSimples("A"));
        mao.removerCarta(0);
        assertTrue(mao.estaVazia());
    }
}
