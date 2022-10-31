package Entities;

public class Pedido {
    private static int globalId = 1;
    private final int id;
    private int numProdutos;
    private Etapa etapa;
    private Funcionario funcionarioResponsavel;

    public Pedido(String produtos){
        this.id = globalId;
        ++globalId;
        this.numProdutos = produtos.split(",").length;
        this.etapa = Etapa.SEPARACAO;
        this.funcionarioResponsavel = null;
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
        if (this.etapa == Etapa.SEPARACAO && this.funcionarioResponsavel instanceof Separador){
            Separador func = (Separador)getFuncionarioResponsavel();
            
            if (this.numProdutos == func.getProdutosSeparados()){
                this.etapa = Etapa.ENTREGA;
                return true;
            }
            return false;
        }
        else if (this.etapa == Etapa.SEPARACAO && this.funcionarioResponsavel instanceof Entregador){
            Entregador func = (Entregador)getFuncionarioResponsavel();

            if (func.getRodadasParaEntrega() == 0){
                this.etapa = Etapa.FINALIZADO;
                return true;
            }
            return false;
        }
        else if (etapa == Etapa.FINALIZADO)
            return true;
        return false;
    }
}
