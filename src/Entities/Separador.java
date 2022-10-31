package Entities;

public class Separador extends Funcionario {
    private int numeroDeColetas = 0;
    private int produtosSeparados = 0;;

    public Separador(String nome){
        super(nome);
    }

    public boolean executaFuncao(){
        if (this.getPedidoAtual().etapaAprovada())
            return true;
        
        this.produtosSeparados++;
        this.numeroDeColetas++;
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

    public void zeraProdutosSeparados(){
        this.produtosSeparados = 0;
    }

    public void incrementaProdutosSeparados(){
        if ((this.getPedidoAtual().getNumProdutos() - this.produtosSeparados) != 0)
            this.produtosSeparados++;
    }
}