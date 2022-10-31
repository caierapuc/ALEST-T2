package Entities;

public class Pedido {
    private static int globalId = 1;
    private final int id;
    private int numProdutos;
    private int produtosSeparados;
    private Etapa etapa;
    private Funcionario funcionarioResponsavel;

    public Pedido(String produtos){
        this.id = globalId;
        ++globalId;
        this.numProdutos = produtos.split(",").length;
        this.etapa = Etapa.SEPARACAO;
    }

    public Funcionario getFuncionarioResponsavel(){
        return this.funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(Funcionario funcionario){
        this.funcionarioResponsavel = funcionario;
    }

    public int getNumProdutos(){
        return this.numProdutos;
    }

    public int getProdutosSeparados(){
        return this.produtosSeparados;
    }

    public void incrementaProdutosSeparados(){
        if ((this.numProdutos - this.produtosSeparados) != 0)
            this.produtosSeparados++;
    }

    public Etapa getEtapa(){
        return this.etapa;
    }

    public void setEtapa(Etapa etapa){
        this.etapa = etapa;
    }

    public int getId(){
        return this.id;
    }

    public boolean etapaAprovada(){
        if (this.numProdutos == this.produtosSeparados){
            this.etapa = Etapa.ENTREGA;
            return true;
        }
        return false;
    }
}
