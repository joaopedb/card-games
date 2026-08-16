package game.controller;

import framework.partida.Observer;
import game.models.CartaSuperTrunfo;
import game.models.EstrategiaEscolhaAtributo;
import java.util.List;
import java.util.Scanner;

/**
 * Estratégia do jogador humano: mostra os atributos da carta e
 * lê a escolha digitada no console. O computador pode trocar por
 * qualquer outra implementação de EstrategiaEscolhaAtributo.
 */
public class EstrategiaHumana implements EstrategiaEscolhaAtributo {

    private final Scanner scanner;
    private final Observer view;

    /**
     * Cria a estratégia de input do jogador humano.
     *
     * @param scanner origem da entrada digitada (console)
     * @param view    view usada para exibir as opções ao jogador
     */
    public EstrategiaHumana(Scanner scanner, Observer view) {
        this.scanner = scanner;
        this.view = view;
    }

    /**
     * Exibe a carta e os atributos disponíveis e lê a escolha do jogador,
     * validando a entrada até que seja uma opção válida.
     */
    @Override
    public String escolherAtributo(CartaSuperTrunfo carta, List<String> atributos) {
        view.notificar("Sua carta: " + carta.getNome());
        for (int i = 0; i < atributos.size(); i++) {
            String atributo = atributos.get(i);
            view.notificar("  " + (i + 1) + ". " + atributo + " (" + carta.getValorAtributo(atributo) + ")");
        }

        while (true) {
            view.notificar("Escolha o atributo (1 a " + atributos.size() + "): ");
            try {
                int opcao = Integer.parseInt(scanner.nextLine().trim());
                if (opcao >= 1 && opcao <= atributos.size()) {
                    return atributos.get(opcao - 1);
                }
                view.notificar("Opção inválida. Tente novamente.");
            } catch (NumberFormatException e) {
                view.notificar("Digite um número válido.");
            }
        }
    }
}
