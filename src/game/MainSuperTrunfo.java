package game;

import game.controller.SuperTrunfoController;

/**
 * Ponto de entrada do jogo Super Trunfo.
 * A execução começa no controller, que monta e dispara a partida.
 */
public class MainSuperTrunfo {

    /**
     * Construtor privado: a classe só serve como ponto de entrada.
     */
    private MainSuperTrunfo() {
    }

    /**
     * Ponto de entrada do jogo.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        new SuperTrunfoController().jogar();
    }
}
