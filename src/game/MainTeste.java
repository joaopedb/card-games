package game;

import game.models.CartaSuperTrunfo;
import game.models.LeitorCartasFactory;
import java.util.List;

/**
 * Exemplo simples de utilização da fábrica de cartas: carrega o baralho
 * do JSON e exibe as primeiras cartas. Serve como demonstração mínima
 * de uso da API de criação de cartas.
 */
public class MainTeste {

    /**
     * Construtor privado: a classe só serve como ponto de entrada.
     */
    private MainTeste() {
    }

    /**
     * Executa o exemplo de utilização da fábrica de cartas.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        System.out.println("Testando a fábrica de cartas...\n");
        
        List<CartaSuperTrunfo> baralho = LeitorCartasFactory.criarCartas("data/trunfoChars.json");
        
        if (baralho.isEmpty()) {
            System.out.println("Vixe, o baralho está vazio! Verifique se o caminho do arquivo JSON está correto.");
        } else {
            System.out.println("Sucesso! A fábrica montou " + baralho.size() + " cartas.");
            System.out.println("Aqui estão as três primeiras cartas que vieram do contêiner:");

            for (int i = 0; i < 3 && i < baralho.size(); i++) {
                CartaSuperTrunfo carta = baralho.get(i);
                System.out.println("- " + carta.getNome() + " (Força: " + carta.getForca() + ")");
            }
        }
    }
}