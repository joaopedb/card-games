package framework.excecoes;

/**
 * Exceção de domínio do framework: indica uma operação inválida sobre um
 * baralho vazio (ex.: tentar comprar uma carta sem haver cartas disponíveis).
 * <p>
 * É uma exceção não verificada (estende {@link IllegalStateException}), pois
 * representa um estado inválido do objeto; os jogos clientes podem capturá-la
 * para tratar o caso de forma específica, se desejarem.
 */
public class BaralhoVazioException extends IllegalStateException {

    /**
     * Cria a exceção com a mensagem de erro.
     *
     * @param mensagem descrição do erro ocorrido
     */
    public BaralhoVazioException(String mensagem) {
        super(mensagem);
    }
}
