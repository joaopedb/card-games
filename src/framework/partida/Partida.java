package framework.partida;

public abstract class Partida {
    
    // Passos que cada jogo específico vai implementar
    protected abstract void inicializarJogo();
    protected abstract void jogarRodada();
    protected abstract boolean isFimDePartida();
    protected abstract void declararVencedor();
    
    // O Template Method que define a ordem das ações (não pode ser alterado)
    public final void iniciarPartida() {
        inicializarJogo();
        
        while (!isFimDePartida()) {
            jogarRodada();
        }
        
        declararVencedor();
    }
}