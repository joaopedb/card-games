package game.view;

import framework.partida.Observer;

/**
 * View de console do Super Trunfo.
 * Implementa o Observer do framework: a partida notifica os eventos
 * e a view apenas os exibe, sem conhecer as regras do jogo.
 */
public class SuperTrunfoView implements Observer {

    /**
     * Cria a view de console.
     */
    public SuperTrunfoView() {
    }

    @Override
    public void notificar(String mensagem) {
        System.out.println(mensagem);
    }
}
