package game.models;

import framework.cartas.Carta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JogadorPadraoTest {

    private static class CartaSimples implements Carta {
        private final String nome;
        CartaSimples(String nome) { this.nome = nome; }
        @Override public String getNome() { return nome; }
    }

    @Test
    void jogadorGuardaSeuNome() {
        JogadorPadrao jogador = new JogadorPadrao("Ana");
        assertEquals("Ana", jogador.getNome());
    }

    @Test
    void receberCartaColocaCartaNaMao() {
        JogadorPadrao jogador = new JogadorPadrao("Ana");
        jogador.receberCarta(new CartaSimples("X"));

        assertEquals(1, jogador.getMao().tamanho());
        assertEquals("X", jogador.getMao().removerCarta(0).getNome());
    }

    @Test
    void receberVariasCartasAcumulaNaMao() {
        JogadorPadrao jogador = new JogadorPadrao("Bia");
        jogador.receberCarta(new CartaSimples("1"));
        jogador.receberCarta(new CartaSimples("2"));
        jogador.receberCarta(new CartaSimples("3"));
        assertEquals(3, jogador.getMao().tamanho());
    }
}
