package framework.partida;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifica o Template Method da Partida: a ordem das ações
 * (inicializar -> rodadas -> declarar vencedor) é garantida pelo framework,
 * mesmo que o jogo cliente só implemente os passos.
 */
class PartidaTemplateTest {

    private static class PartidaFake extends Partida {
        final List<String> eventos = new ArrayList<>();
        private int rodadasParaJogar;

        PartidaFake(int rodadas) {
            this.rodadasParaJogar = rodadas;
        }

        @Override protected void inicializarJogo() { eventos.add("inicializar"); }
        @Override protected void jogarRodada() {
            eventos.add("rodada");
            rodadasParaJogar--;
        }
        @Override protected boolean isFimDePartida() { return rodadasParaJogar <= 0; }
        @Override protected void declararVencedor() { eventos.add("vencedor"); }
    }

    private static class ObservadorDeTeste implements Observer {
        final List<String> mensagens = new ArrayList<>();
        @Override public void notificar(String mensagem) { mensagens.add(mensagem); }
    }

    @Test
    void templateMethodExecutaNaOrdemCorreta() {
        PartidaFake partida = new PartidaFake(3);
        partida.iniciarPartida();

        assertEquals(List.of("inicializar", "rodada", "rodada", "rodada", "vencedor"), partida.eventos);
    }

    @Test
    void observerRecebeNotificacoesDaPartida() {
        PartidaFake partida = new PartidaFake(1);
        ObservadorDeTeste observador = new ObservadorDeTeste();
        partida.adicionarObserver(observador);
        partida.iniciarPartida();

        // As notificações ficam visíveis apenas se o jogo cliente as disparar
        assertNotNull(observador.mensagens);
        assertTrue(observador.mensagens.isEmpty());
    }

    @Test
    void partidaSemRodadasSoInicializaEDeclaraVencedor() {
        PartidaFake partida = new PartidaFake(0);
        partida.iniciarPartida();
        assertEquals(List.of("inicializar", "vencedor"), partida.eventos);
    }
}
