package game.partida;

import framework.baralho.Baralho;
import framework.jogadores.Jogadores;
import framework.partida.Partida;
import framework.regras.Regras;
import game.models.CartaSuperTrunfo;
import game.models.EstrategiaEscolhaAtributo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Partida de Super Trunfo (padrão Template Method: herda o fluxo do framework).
 * <p>
 * Suporta partidas com dois ou mais jogadores, cada um com a sua estratégia
 * de escolha de atributo (humano via console, computador via IA). Assim, a
 * mesma partida pode combinar jogadores humanos e automatizados e pode ser
 * expandida para mais jogadores sem alterar o framework.
 * <p>
 * Regras: cada jogador vira a carta do topo; quem está na vez escolhe o
 * atributo; a carta com o maior valor no atributo vence e leva as cartas
 * (e o monte, se houver) para o fundo da própria mão. Empate: as cartas vão
 * para o monte e quem está na vez continua escolhendo. A carta super trunfo
 * vence qualquer outra (regra tratada na estratégia de comparação).
 */
public class PartidaSuperTrunfo extends Partida {

    /**
     * Limite de segurança de rodadas: garante que a partida sempre termina,
     * mesmo que as cartas fiquem circulando entre as mãos sem ninguém zerar
     * (comum com estratégias determinísticas). No limite, vence quem tiver
     * mais cartas na mão.
     */
    private static final int MAX_RODADAS = 1000;

    private final Baralho baralho;
    private final List<Jogadores> jogadores;
    private final Map<Jogadores, EstrategiaEscolhaAtributo> estrategias;
    private final Regras regras;
    private final List<CartaSuperTrunfo> monte = new ArrayList<>();

    private int indiceJogadorAtivo;
    private int rodada = 0;
    private final Map<Jogadores, Integer> rodadasGanhas = new HashMap<>();

    /**
     * Cria uma partida de Super Trunfo com os jogadores informados.
     *
     * @param baralho    baralho com as cartas do jogo
     * @param jogadores  lista de jogadores (2 ou mais)
     * @param estrategias estratégia de escolha de atributo de cada jogador
     * @param regras     regras do jogo (distribuição e comparação)
     */
    public PartidaSuperTrunfo(Baralho baralho,
                              List<Jogadores> jogadores,
                              Map<Jogadores, EstrategiaEscolhaAtributo> estrategias,
                              Regras regras) {
        if (jogadores == null || jogadores.size() < 2) {
            throw new IllegalArgumentException("Super Trunfo exige pelo menos 2 jogadores.");
        }
        if (!estrategias.keySet().containsAll(jogadores)) {
            throw new IllegalArgumentException("Todo jogador precisa de uma estratégia de escolha de atributo.");
        }
        this.baralho = baralho;
        this.jogadores = jogadores;
        this.estrategias = estrategias;
        this.regras = regras;
        for (Jogadores jogador : jogadores) {
            rodadasGanhas.put(jogador, 0);
        }
    }

    @Override
    protected void inicializarJogo() {
        regras.distribuirCartas(baralho, jogadores);

        indiceJogadorAtivo = (int) (Math.random() * jogadores.size());

        StringBuilder resumo = new StringBuilder("Baralho embaralhado e distribuído!");
        for (Jogadores jogador : jogadores) {
            resumo.append(" ").append(jogador.getNome()).append(" tem ").append(jogador.getMao().tamanho()).append(" cartas.");
        }
        notificar(resumo.toString());
        notificar("Quem começa escolhendo o atributo: " + jogadorAtivo().getNome());
    }

    @Override
    protected void jogarRodada() {
        rodada++;

        // Apenas jogadores que ainda têm cartas participam da rodada
        List<Jogadores> emJogo = new ArrayList<>();
        for (Jogadores jogador : jogadores) {
            if (!jogador.getMao().estaVazia()) {
                emJogo.add(jogador);
            }
        }
        if (emJogo.isEmpty()) {
            return; // ninguém tem cartas (empate simultâneo): a partida termina
        }

        Jogadores ativo = emJogo.get(indiceJogadorAtivo % emJogo.size());

        // Cada jogador em jogo vira a carta do topo da mão
        Map<Jogadores, CartaSuperTrunfo> cartasViradas = new HashMap<>();
        for (Jogadores jogador : emJogo) {
            cartasViradas.put(jogador, (CartaSuperTrunfo) jogador.getMao().removerCarta(0));
        }

        notificar("");
        notificar("═══ Rodada " + rodada + " ═══");
        notificar(ativo.getNome() + " joga: " + cartasViradas.get(ativo).getNome());

        // Quem está na vez escolhe o atributo (humano digita; computador usa estratégia)
        String atributo = estrategias.get(ativo)
                .escolherAtributo(cartasViradas.get(ativo), cartasViradas.get(ativo).getNomesAtributos());

        // Revela as cartas dos demais jogadores
        for (Jogadores jogador : emJogo) {
            if (jogador != ativo) {
                CartaSuperTrunfo carta = cartasViradas.get(jogador);
                notificar(jogador.getNome() + " joga: " + carta.getNome()
                        + " (" + atributo + ": " + carta.getValorAtributo(atributo) + ")");
            }
        }

        // Determina a melhor carta da rodada no atributo escolhido
        Jogadores vencedor = ativo;
        CartaSuperTrunfo melhorCarta = cartasViradas.get(ativo);
        for (Jogadores jogador : emJogo) {
            CartaSuperTrunfo carta = cartasViradas.get(jogador);
            if (regras.comparar(carta, melhorCarta, atributo) > 0) {
                vencedor = jogador;
                melhorCarta = carta;
            }
        }

        // Verifica empate no topo: se outra carta empatou com a melhor, ninguém leva
        boolean empatouNoTopo = false;
        for (Jogadores jogador : emJogo) {
            if (jogador != vencedor && regras.comparar(cartasViradas.get(jogador), melhorCarta, atributo) == 0) {
                empatouNoTopo = true;
                break;
            }
        }

        if (empatouNoTopo) {
            monte.addAll(cartasViradas.values());
            notificar("Empate! As cartas vão para o monte (" + monte.size() + " cartas no monte).");
        } else {
            entregarCartas(vencedor, cartasViradas, emJogo);
            rodadasGanhas.merge(vencedor, 1, Integer::sum);
            notificar(vencedor.getNome() + " vence a rodada!");
        }
    }

    private void entregarCartas(Jogadores vencedor, Map<Jogadores, CartaSuperTrunfo> cartasViradas, List<Jogadores> emJogo) {
        for (CartaSuperTrunfo carta : cartasViradas.values()) {
            vencedor.getMao().adicionarCarta(carta);
        }
        for (CartaSuperTrunfo carta : monte) {
            vencedor.getMao().adicionarCarta(carta);
        }
        monte.clear();
        indiceJogadorAtivo = emJogo.indexOf(vencedor); // quem vence escolhe o atributo na próxima rodada
        notificar(vencedor.getNome() + " agora tem " + vencedor.getMao().tamanho() + " cartas.");
    }

    private Jogadores jogadorAtivo() {
        return jogadores.get(indiceJogadorAtivo);
    }

    @Override
    protected boolean isFimDePartida() {
        // Limite de rodadas: garante o término da partida em qualquer cenário
        if (rodada >= MAX_RODADAS) {
            return true;
        }
        // Termina quando sobrar no máximo um jogador com cartas
        long comCartas = jogadores.stream().filter(j -> !j.getMao().estaVazia()).count();
        return comCartas <= 1;
    }

    @Override
    protected void declararVencedor() {
        notificar("");
        notificar("═══════════════════════════════════");
        if (rodada >= MAX_RODADAS) {
            notificar("Limite de " + MAX_RODADAS + " rodadas atingido — vence quem tiver mais cartas.");
        }
        notificar("Fim de partida!");

        // Decide pelo jogador com mais cartas na mão; em empate, pelo nº de rodadas vencidas
        Jogadores vencedor = jogadores.get(0);
        int melhorCartas = vencedor.getMao().tamanho();
        int melhorRodadas = rodadasGanhas.getOrDefault(vencedor, 0);
        for (Jogadores jogador : jogadores) {
            int cartas = jogador.getMao().tamanho();
            int rodadas = rodadasGanhas.getOrDefault(jogador, 0);
            if (cartas > melhorCartas || (cartas == melhorCartas && rodadas > melhorRodadas)) {
                vencedor = jogador;
                melhorCartas = cartas;
                melhorRodadas = rodadas;
            }
        }

        // Verifica se há empate total (mesmas cartas e mesmas rodadas vencidas)
        boolean empateTotal = false;
        for (Jogadores jogador : jogadores) {
            if (jogador != vencedor
                    && jogador.getMao().tamanho() == melhorCartas
                    && rodadasGanhas.getOrDefault(jogador, 0) == melhorRodadas) {
                empateTotal = true;
                break;
            }
        }

        if (empateTotal) {
            notificar("Empate total! Ninguém levou a melhor.");
        } else {
            notificar(vencedor.getNome() + " venceu a partida! Parabéns!");
        }

        StringBuilder placar = new StringBuilder("Placar — ");
        for (Jogadores jogador : jogadores) {
            placar.append(jogador.getNome()).append(": ").append(rodadasGanhas.getOrDefault(jogador, 0)).append(" rodadas | ");
        }
        notificar(placar.toString());
    }
}
