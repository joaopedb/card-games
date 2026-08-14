package game.models;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LeitorCartasFactory {
    
    private class MoldeBancoJson {
        String tema;
        List<CartaSuperTrunfo> cartas;
    }
    
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