package Entities;

import java.util.Properties;

public class Entregador extends Funcionario {
    private int entregas = 0;
    
    public Entregador(String nome){
        super(nome);
    }

    public int getEntregas(){
        return this.entregas;
    }

    public void incrementaNumeroDeColetas(){
        this.entregas++;
    }
}