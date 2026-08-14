package game;

import game.models.CartaSuperTrunfo;
import game.models.LeitorCartasFactory;
import java.util.List;

public class MainTeste {
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