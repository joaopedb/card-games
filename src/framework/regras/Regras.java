package framework.regras;

import framework.baralho.Baralho;
import framework.cartas.Carta;
import framework.jogadores.Jogadores;
import java.util.List;

/**
 * Contrato das regras de um jogo de cartas.
 * <p>
 * Ponto de extensão do framework: cada jogo implementa as suas regras —
 * como as cartas são distribuídas e como uma disputa entre cartas é decidida
 * (ex.: Super Trunfo compara atributos, Blackjack soma valores, Truco compara
 * com o contexto da vira). A partida usa esta abstração em vez de conhecer
 * regras específicas de qualquer jogo.
 */
public interface Regras {

    /**
     * Distribui as cartas do baralho entre os jogadores.
     * A forma de distribuição varia por jogo (alternada, todas para um, etc.).
     *
     * @param baralho   baralho com as cartas a distribuir
     * @param jogadores lista de jogadores que receberão cartas
     */
    void distribuirCartas(Baralho baralho, List<? extends Jogadores> jogadores);

    /**
     * Compara duas cartas em uma disputa de rodada.
     *
     * @param carta1  a primeira carta
     * @param carta2  a segunda carta
     * @param contexto dado extra específico do jogo (ex.: atributo escolhido
     *                 no Super Trunfo); pode ser {@code null} se não houver
     * @return valor positivo se {@code carta1} vence, negativo se {@code carta2}
     *         vence e zero em caso de empate
     */
    int comparar(Carta carta1, Carta carta2, String contexto);
}
