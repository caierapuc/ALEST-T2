package Entities;

public class Separador extends Funcionario {
    private int numeroDeColetas = 0;
    private int produtosSeparados;

    public Separador(String nome){
        super(nome);
    }

    public boolean executaFuncao(){
        if (this.getPedidoAtual().etapaAprovada())
            return true;
        
        this.produtosSeparados++;
        return false;
    }

    public int getNumeroDeColetas(){
        return this.numeroDeColetas;
    }

    public void incrementaNumeroDeColetas(){
        this.numeroDeColetas++;
    }

    public int getProdutosSeparados(){
        return this.produtosSeparados;
    }

    public void incrementaProdutosSeparados(){
        if ((this.getPedidoAtual().getNumProdutos() - this.produtosSeparados) != 0)
            this.produtosSeparados++;
    }
}