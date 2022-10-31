package Entities;

public class Funcionario {
    private boolean ocupado;
    private Pedido pedidoAtual;
    private String nome;

    public Funcionario(String nome){
        this.ocupado = false;
        this.pedidoAtual = null;
        this.nome = nome;
    }

    public boolean isOcupado(){
        return this.ocupado;
    }

    public Pedido getPedidoAtual(){
        return this.pedidoAtual;
    }

    public void setOcupado(boolean ocupado){
        this.ocupado = ocupado;
    }

    public void setPedido(Pedido pedido){
        this.pedidoAtual = pedido;
    }

    public String getNome(){
        return this.nome;
    }
}
