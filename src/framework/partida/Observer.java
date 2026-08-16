package framework.partida;

/**
 * Observador de eventos de uma partida (padrão Observer).
 * <p>
 * As views implementam esta interface para receber as mensagens geradas
 * pela partida (início, rodadas, resultado, vencedor). Isso desacopla a
 * lógica do jogo da apresentação: a mesma partida pode ser exibida em
 * console, interface gráfica ou log sem alterar o jogo.
 */
public interface Observer {

    /**
     * Recebe uma mensagem de evento da partida.
     *
     * @param mensagem a mensagem a ser exibida/processada pela view
     */
    void notificar(String mensagem);
}
