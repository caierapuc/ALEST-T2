package Entities;

public class Separador extends Funcionario {
    private int numeroDeColetas = 0;

    public Separador(String nome){
        super(nome);
    }

    public boolean executaFuncao(){
        var pedido = this.getPedidoAtual();

        if (pedido.etapaAprovada())
            return true;
        
        pedido.incrementaProdutosSeparados();
        return false;
    }

    public int getNumeroDeColetas(){
        return this.numeroDeColetas;
    }

    public void incrementaNumeroDeColetas(){
        this.numeroDeColetas++;
    }
}