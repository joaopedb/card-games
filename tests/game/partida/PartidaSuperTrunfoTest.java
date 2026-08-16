package game.partida;

import framework.jogadores.Jogadores;
import framework.partida.Observer;
import game.models.BaralhoPadrao;
import game.models.CartaSuperTrunfo;
import game.models.JogadorPadrao;
import game.models.ComparadorSuperTrunfo;
import game.models.EstrategiaAleatoria;
import game.models.EstrategiaEscolhaAtributo;
import game.models.EstrategiaMelhorAtributo;
import game.models.LeitorCartasFactory;
import game.models.RegrasSuperTrunfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PartidaSuperTrunfoTest {

    /** Estratégia de teste: sempre escolhe o primeiro atributo (determinístico). */
    private static class EstrategiaPrimeiroAtributo implements EstrategiaEscolhaAtributo {
        @Override public String escolherAtributo(CartaSuperTrunfo carta, List<String> atributos) {
            return atributos.get(0);
        }
    }

    private static class ObservadorDeTeste implements Observer {
        final List<String> mensagens = new ArrayList<>();
        @Override public void notificar(String mensagem) { mensagens.add(mensagem); }
    }

    private BaralhoPadrao criarBaralho() {
        return new BaralhoPadrao(LeitorCartasFactory.criarCartas("data/trunfoChars.json"));
    }

    private PartidaSuperTrunfo criarPartida(List<Jogadores> jogadores, Map<Jogadores, EstrategiaEscolhaAtributo> estrategias) {
        return new PartidaSuperTrunfo(criarBaralho(), jogadores, estrategias, new RegrasSuperTrunfo(new ComparadorSuperTrunfo()));
    }

    private PartidaSuperTrunfo criarPartida2Jogadores(EstrategiaEscolhaAtributo estrategiaA,
                                                      EstrategiaEscolhaAtributo estrategiaB) {
        JogadorPadrao jogadorA = new JogadorPadrao("Jogador A");
        JogadorPadrao jogadorB = new JogadorPadrao("Jogador B");
        Map<Jogadores, EstrategiaEscolhaAtributo> estrategias = new HashMap<>();
        estrategias.put(jogadorA, estrategiaA);
        estrategias.put(jogadorB, estrategiaB);
        return criarPartida(List.of(jogadorA, jogadorB), estrategias);
    }

    @Test
    void partidaCompletaTerminaComVencedor() {
        PartidaSuperTrunfo partida = criarPartida2Jogadores(new EstrategiaMelhorAtributo(), new EstrategiaAleatoria());
        ObservadorDeTeste observador = new ObservadorDeTeste();
        partida.adicionarObserver(observador);
        partida.iniciarPartida();

        boolean declarouFim = observador.mensagens.stream().anyMatch(m -> m.contains("Fim de partida"));
        assertTrue(declarouFim, "A partida deveria declarar o fim");

        boolean declarouVencedor = observador.mensagens.stream()
                .anyMatch(m -> m.contains("venceu a partida"));
        assertTrue(declarouVencedor, "Deveria haver um vencedor declarado");
    }

    @Test
    void partidaComTresJogadoresTerminaComVencedor() {
        JogadorPadrao jogadorA = new JogadorPadrao("A");
        JogadorPadrao jogadorB = new JogadorPadrao("B");
        JogadorPadrao jogadorC = new JogadorPadrao("C");
        Map<Jogadores, EstrategiaEscolhaAtributo> estrategias = new HashMap<>();
        estrategias.put(jogadorA, new EstrategiaMelhorAtributo());
        estrategias.put(jogadorB, new EstrategiaAleatoria());
        estrategias.put(jogadorC, new EstrategiaMelhorAtributo());

        PartidaSuperTrunfo partida = criarPartida(List.of(jogadorA, jogadorB, jogadorC), estrategias);
        ObservadorDeTeste observador = new ObservadorDeTeste();
        partida.adicionarObserver(observador);
        partida.iniciarPartida();

        boolean declarouFim = observador.mensagens.stream().anyMatch(m -> m.contains("Fim de partida"));
        assertTrue(declarouFim, "A partida com 3 jogadores deveria terminar");
    }

    @Test
    void partidaSemCartasNoBaralhoNaoPodeComecar() {
        // Com 2 jogadores e baralho vazio, a distribuição não entrega cartas e a partida
        // deve terminar imediatamente sem exceção (nenhum jogador tem cartas).
        BaralhoPadrao vazio = new BaralhoPadrao();
        JogadorPadrao a = new JogadorPadrao("A");
        JogadorPadrao b = new JogadorPadrao("B");
        Map<Jogadores, EstrategiaEscolhaAtributo> estrategias = new HashMap<>();
        estrategias.put(a, new EstrategiaPrimeiroAtributo());
        estrategias.put(b, new EstrategiaPrimeiroAtributo());

        PartidaSuperTrunfo partida = new PartidaSuperTrunfo(vazio, List.of(a, b), estrategias,
                new RegrasSuperTrunfo(new ComparadorSuperTrunfo()));
        ObservadorDeTeste observador = new ObservadorDeTeste();
        partida.adicionarObserver(observador);
        partida.iniciarPartida();

        assertTrue(observador.mensagens.stream().anyMatch(m -> m.contains("Fim de partida")));
    }

    @Test
    void partidaExigePeloMenosDoisJogadores() {
        JogadorPadrao a = new JogadorPadrao("A");
        Map<Jogadores, EstrategiaEscolhaAtributo> estrategias = new HashMap<>();
        estrategias.put(a, new EstrategiaPrimeiroAtributo());

        assertThrows(IllegalArgumentException.class,
                () -> criarPartida(List.of(a), estrategias));
    }

    @Test
    void partidaExigeEstrategiaParaTodosOsJogadores() {
        JogadorPadrao a = new JogadorPadrao("A");
        JogadorPadrao b = new JogadorPadrao("B");
        Map<Jogadores, EstrategiaEscolhaAtributo> estrategias = new HashMap<>();
        estrategias.put(a, new EstrategiaPrimeiroAtributo());
        // b não tem estratégia

        assertThrows(IllegalArgumentException.class,
                () -> criarPartida(List.of(a, b), estrategias));
    }

    @Test
    void distribuicaoInicialEntregaMetadeDasCartasACadaJogador() {
        List<CartaSuperTrunfo> cartas = LeitorCartasFactory.criarCartas("data/trunfoChars.json");
        assertEquals(32, cartas.size());

        JogadorPadrao a = new JogadorPadrao("A");
        JogadorPadrao b = new JogadorPadrao("B");
        new RegrasSuperTrunfo(new ComparadorSuperTrunfo())
                .distribuirCartas(new BaralhoPadrao(cartas), List.of(a, b));

        assertEquals(16, a.getMao().tamanho());
        assertEquals(16, b.getMao().tamanho());
    }

    @Test
    void rodadaGeraEventosVisiveisParaObserver() {
        PartidaSuperTrunfo partida = criarPartida2Jogadores(new EstrategiaPrimeiroAtributo(), new EstrategiaPrimeiroAtributo());
        ObservadorDeTeste observador = new ObservadorDeTeste();
        partida.adicionarObserver(observador);
        partida.iniciarPartida();

        assertFalse(observador.mensagens.isEmpty());
        assertTrue(observador.mensagens.stream().anyMatch(m -> m.contains("Rodada")));
    }
}
