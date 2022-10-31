package Entities;

public class Entregador extends Funcionario {
    private int entregas = 0;
    private int rodadasParaEntraga;
    
    public Entregador(String nome){
        super(nome);
    }

    public int getEntregas(){
        return this.entregas;
    }

    public void incrementaNumeroDeColetas(){
        this.entregas++;
    }

    public int getRodadasParaEntrega(){
        return this.rodadasParaEntraga;
    }

    public void setRodadasParaEntrega(){
        do{
            this.rodadasParaEntraga = (int)Math.floor(Math.random() * 10);
        }
        while (this.rodadasParaEntraga > 8 && this.rodadasParaEntraga < 4);
    }

    public boolean executaFuncao(){
        if (getPedidoAtual().etapaAprovada())
            return true;
        
        rodadasParaEntraga--;
        return false;
    }
}