package game.models;

import framework.cartas.Carta;
import framework.jogadores.Jogadores;
import framework.jogadores.Mao;

/**
 * Implementação de jogador usada pelo jogo Super Trunfo: tem um nome e
 * uma mão de cartas (baseada no contrato {@link framework.jogadores.Jogadores}).
 */
public class JogadorPadrao implements Jogadores {

    private final String nome;
    private final Mao mao = new MaoPadrao();

    /**
     * Cria um jogador com o nome informado e uma mão vazia.
     *
     * @param nome o nome do jogador
     */
    public JogadorPadrao(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void receberCarta(Carta carta) {
        mao.adicionarCarta(carta);
    }

    @Override
    public Mao getMao() {
        return mao;
    }
}
