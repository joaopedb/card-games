package framework.partida;

import java.util.ArrayList;
import java.util.List;

/**
 * Estrutura base de uma partida de jogo de cartas (padrão Template Method).
 * <p>
 * Define o fluxo fixo da partida — {@link #iniciarPartida()} — que não pode
 * ser alterado pelos jogos clientes: inicializar, jogar rodadas até o fim e
 * declarar o vencedor. Cada jogo cliente estende esta classe e implementa
 * apenas os passos abstratos, reaproveitando o esqueleto do fluxo.
 * <p>
 * A partida também gerencia observadores ({@link Observer}): o jogo chama
 * {@link #notificar(String)} para reportar eventos e as views plugadas
 * recebem as mensagens sem que a lógica do jogo dependa da apresentação.
 */
public abstract class Partida {

    /** Observadores (ex.: a view) que acompanham os eventos da partida. */
    private final List<Observer> observadores = new ArrayList<>();

    /**
     * Cria uma partida sem observadores registrados.
     */
    public Partida() {
    }

    /**
     * Passo 1 do fluxo: prepara o jogo (monta baralho, distribui cartas, etc.).
     */
    protected abstract void inicializarJogo();

    /**
     * Passo 2 do fluxo: executa uma rodada do jogo.
     */
    protected abstract void jogarRodada();

    /**
     * Passo 3 do fluxo: indica se a partida deve terminar.
     *
     * @return {@code true} se a partida acabou, {@code false} para continuar
     */
    protected abstract boolean isFimDePartida();

    /**
     * Passo 4 do fluxo: anuncia o resultado/vencedor da partida.
     */
    protected abstract void declararVencedor();

    /**
     * Registra um observador para receber os eventos da partida.
     *
     * @param observer a view/observador a ser notificado
     */
    public void adicionarObserver(Observer observer) {
        observadores.add(observer);
    }

    /**
     * Envia uma mensagem de evento para todos os observadores registrados.
     * <p>
     * Usado pelos jogos clientes dentro dos passos abstratos para reportar
     * o andamento da partida à view.
     *
     * @param mensagem a mensagem a ser notificada
     */
    protected void notificar(String mensagem) {
        for (Observer observer : observadores) {
            observer.notificar(mensagem);
        }
    }

    /**
     * Template Method: executa o fluxo completo da partida na ordem fixa
     * inicializar → rodadas → declarar vencedor. Não pode ser sobrescrito.
     */
    public final void iniciarPartida() {
        inicializarJogo();

        while (!isFimDePartida()) {
            jogarRodada();
        }

        declararVencedor();
    }
}