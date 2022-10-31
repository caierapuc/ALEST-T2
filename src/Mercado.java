import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

import Entities.*;

public class Mercado {
    private ArrayList<Separador> separadores;
    private ArrayList<Entregador> entregadores;
    private Queue<Pedido> pedidosParaColeta = new LinkedList<Pedido>();
    private Queue<Pedido> pedidosParaEntrega = new LinkedList<Pedido>();
    private ArrayList<Pedido> pedidosFinalizados = new ArrayList<Pedido>();
    private ArrayList<Pedido> pedidosCancelados = new ArrayList<Pedido>();
    private int tentativasDeCancelamento = 0;

    public Mercado(){
        this.separadores = new ArrayList<Separador>();
        this.separadores.add(new Separador("João"));
        this.separadores.add(new Separador("Andrá"));
        this.separadores.add(new Separador("Arthur"));

        this.entregadores = new ArrayList<Entregador>();
        this.entregadores.add(new Entregador("Edilson"));
        this.entregadores.add(new Entregador("Eduardo"));
        this.entregadores.add(new Entregador("Luan"));
    }

    public void executa(){
        try {
            int opcao = 0;
            
            do {
                Scanner sc = new Scanner(System.in);

                System.out.print("\nMENU DO MERCADO:\n[1] Receber pedido.\n[2] Ver andamento dos pedidos.\n[3] Cancelar pedido.\n[4] Proximo turno.\n[0] Sair.\n\nEscolha uma das opções a cima: ");
                opcao = sc.nextInt();
                
                sc.close();

                switch(opcao){
                    case 1:
                        this.receberNovoPedido();
                        // this.proximoTurno();
                        break;
                    case 2:
                        //TODO: Refazer andamento, formatar de um jeito mais bonito kkkkk
                        this.verAndamento();
                        // this.proximoTurno();
                        break;
                    case 3:
                        //TODO
                        //this.cancelarUmPedido();
                        // this.proximoTurno();
                        break;
                    case 4:
                        //TODO: Pode deixar pra mim esse
                        // this.proximoTurno();
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente!");
                }
            }
            while (opcao != 0);
        }
        catch(Exception ex){
            if (ex instanceof InputMismatchException)
                System.err.println("Tipo de entrada incorreto, tente novamente");
            else
                System.err.println("Algum erro foi encontrado: " + ex.getMessage());
        }
    }

    private void receberNovoPedido(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite a lista de produtos, separados por uma vírgula (,):");
        String novoPedido = sc.nextLine();
        sc.close();

        if (novoPedido.length() <= 0)
            System.out.println("Nenhum produto escolhido, seguindo para o próximo turno");
        else
            this.pedidosParaColeta.add(new Pedido(novoPedido));
    }

    private void verAndamento(){
        System.out.println("\nAtualmente existem " + (this.pedidosParaColeta.size() + this.pedidosParaEntrega.size() + this.pedidosFinalizados.size() + this.pedidosCancelados.size()) + " pedidos realizados, sendo " + this.pedidosParaColeta.size() + " deles em etapa de coleta e " + this.pedidosParaEntrega.size() + " em fase de entrega.");
        System.out.println("Pedidos finalizados: " + this.pedidosFinalizados.size());
        System.out.println("Tentativas de cancelamento: " + this.tentativasDeCancelamento);
        System.out.println("Pedidos cancelados: " + this.pedidosCancelados.size());
        System.out.println("\nEntregadores:");
        this.entregadores.stream().forEach(x -> System.out.println("    Nome: " + x.getNome() + ", Entregas: " + x.getEntregas()));
        System.out.println("\nSeparadores:");
        this.separadores.stream().forEach(x -> System.out.println("    Nome: " + x.getNome() + ", Produtos separados: " + x.getNumeroDeColetas()));
    }
}