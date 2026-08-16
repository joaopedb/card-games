package game.models;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Fábrica de cartas do Super Trunfo (padrão Factory Method).
 * <p>
 * Lê o arquivo JSON com os dados das cartas (tema + cartas) e cria as
 * instâncias de {@link CartaSuperTrunfo} para compor o baralho.
 * A estrutura esperada do JSON:
 * <pre>
 * { "tema": "...", "cartas": [ { "id": "1A", "nome": "Ryu",
 *     "atributos": { "forca": 76, ... }, "super_trunfo": false } ] }
 * </pre>
 */
public class LeitorCartasFactory {

    /** Molde da estrutura raiz do arquivo JSON. */
    private class MoldeBancoJson {
        String tema;
        List<CartaSuperTrunfo> cartas;
    }

    /**
     * Construtor privado: a fábrica só é usada de forma estática.
     */
    private LeitorCartasFactory() {
    }

    /**
     * Cria a lista de cartas a partir do arquivo JSON informado.
     *
     * @param caminhoArquivo caminho do arquivo JSON (ex.: {@code data/trunfoChars.json})
     * @return a lista de cartas carregadas; lista vazia se o arquivo não
     *         puder ser lido ou estiver com formato inválido (sem lançar exceção)
     */
    public static List<CartaSuperTrunfo> criarCartas(String caminhoArquivo) {
        Gson gson = new Gson();

        try (Reader leitor = new FileReader(caminhoArquivo)) {
            MoldeBancoJson banco = gson.fromJson(leitor, MoldeBancoJson.class);
            return banco.cartas;

        } catch (Exception e) {
            System.out.println("Ops! Problema ao ler o arquivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}