package game.controller;

import framework.jogadores.Jogadores;
import game.models.BaralhoPadrao;
import game.models.CartaSuperTrunfo;
import game.models.JogadorPadrao;
import game.models.ComparadorSuperTrunfo;
import game.models.EstrategiaEscolhaAtributo;
import game.models.EstrategiaMelhorAtributo;
import game.models.LeitorCartasFactory;
import game.models.RegrasSuperTrunfo;
import game.partida.PartidaSuperTrunfo;
import game.view.SuperTrunfoView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Controller do Super Trunfo: monta as dependências (baralho, jogadores,
 * regras, estratégias e view) e dispara a partida. Nenhuma regra de jogo
 * fica aqui — basta trocar as estratégias para mudar a dificuldade ou o
 * tipo de jogador (humano/automatizado).
 */
public class SuperTrunfoController {

    /**
     * Cria o controller.
     */
    public SuperTrunfoController() {
    }

    /**
     * Executa uma partida completa de Super Trunfo: carrega as cartas do JSON,
     * monta o baralho, cria os jogadores (humano + computador), pluga a view
     * como observadora e dispara a partida.
     */
    public void jogar() {
        Scanner scanner = new Scanner(System.in);
        SuperTrunfoView view = new SuperTrunfoView();

        List<CartaSuperTrunfo> cartas = LeitorCartasFactory.criarCartas("data/trunfoChars.json");
        if (cartas.isEmpty()) {
            view.notificar("Não foi possível carregar as cartas do arquivo JSON.");
            return;
        }

        BaralhoPadrao baralho = new BaralhoPadrao(cartas);
        JogadorPadrao humano = new JogadorPadrao("Você");
        JogadorPadrao computador = new JogadorPadrao("Computador");
        List<Jogadores> jogadores = List.of(humano, computador);

        // Cada jogador tem a sua estratégia: humano digita, computador usa IA
        Map<Jogadores, EstrategiaEscolhaAtributo> estrategias = new HashMap<>();
        estrategias.put(humano, new EstrategiaHumana(scanner, view));
        estrategias.put(computador, new EstrategiaMelhorAtributo());

        PartidaSuperTrunfo partida = new PartidaSuperTrunfo(
                baralho,
                jogadores,
                estrategias,
                new RegrasSuperTrunfo(new ComparadorSuperTrunfo()));

        partida.adicionarObserver(view);
        partida.iniciarPartida();
    }
}
