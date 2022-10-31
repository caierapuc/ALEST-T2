import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import Entities.*;

public class Mercado {
    private ArrayList<Separador> separadores;
    private ArrayList<Entregador> entregadores;
    private Queue<Pedido> pedidosParaColeta = new LinkedList<Pedido>();
    private Queue<Pedido> pedidosParaEntrega = new LinkedList<Pedido>();
    private ArrayList<Pedido> pedidosFinalizados = new ArrayList<Pedido>();
    private ArrayList<Pedido> pedidosCancelados = new ArrayList<Pedido>();
    private int tentativasDeCancelamento = 0;

    Scanner sc = new Scanner(System.in);

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
        int opcao = -1;
        
        do {
            try {
                Scanner sc = new Scanner(System.in);

                System.out.print("\nMENU DO MERCADO:\n[1] Receber pedido (Inicia automaticamente um novo turno).\n[2] Ver andamento dos pedidos.\n[3] Cancelar pedido.\n[4] Proximo turno.\n[0] Sair.\n\nEscolha uma das opções a cima: ");
                opcao = sc.nextInt();

                switch(opcao){
                    case 1:
                        this.receberNovoPedido();
                        this.proximoTurno();
                        break;
                    case 2:
                        this.verAndamento();
                        break;
                    case 3:
                        this.cancelarUmPedido();
                        break;
                    case 4:
                        this.proximoTurno();
                        break;
                    case 0:
                        sc.close();
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente!");
                }
            }
            catch(Exception ex){
                if (ex instanceof InputMismatchException)
                    System.err.println("Tipo de entrada incorreto, tente novamente");
                else
                    System.err.println("Algum erro foi encontrado: " + ex.getMessage());
            }
        }
        while (opcao != 0);
    }

    private void receberNovoPedido(){
        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = sc.nextLine();
        System.out.println("Digite a lista de produtos, separados por uma vírgula (,):");
        String novoPedido = sc.nextLine();
        novoPedido += "a";

        if (novoPedido.length() <= 0)
            System.out.println("Nenhum produto escolhido, seguindo para o próximo turno");
        else
            this.pedidosParaColeta.add(new Pedido(novoPedido, nomeCliente));
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

    private void proximoTurno(){
        List<Separador> separadoresDisponiveis = separadores.stream().filter(x -> !x.isOcupado()).collect(Collectors.toList());
        List<Entregador> entregadoresDisponiveis = entregadores.stream().filter(x -> !x.isOcupado()).collect(Collectors.toList());
        List<Pedido> novosPedidos = pedidosParaColeta.stream().filter(x -> x.getFuncionarioResponsavel() == null).collect(Collectors.toList());

        int count = 0;
        
        for (var obj: separadoresDisponiveis){
            if ((novosPedidos.size() - count) > 0){
                obj.zeraProdutosSeparados();
                novosPedidos.get(count).setFuncionarioResponsavel(obj);
                obj.setPedido(novosPedidos.get(count));
                obj.setOcupado(true);
                
                count++;
            }
        }
        
        List<Separador> separadoresOcupados = separadores.stream().filter(x -> x.isOcupado()).collect(Collectors.toList());
        for (var obj: separadoresOcupados){
            if (obj.executaFuncao()){
                pedidosParaEntrega.add(obj.getPedidoAtual());
                pedidosParaColeta.remove(obj.getPedidoAtual());
                obj.getPedidoAtual().setFuncionarioResponsavel(null);
                obj.setPedido(null);
                obj.setOcupado(false);
                obj.zeraProdutosSeparados();
            }
        }
        
        List<Pedido> novasEntregas = pedidosParaEntrega.stream().filter(x -> x.getFuncionarioResponsavel() == null).collect(Collectors.toList());
        count = 0;
        for (var obj: entregadoresDisponiveis){
            if ((novasEntregas.size() - count) > 0){
                novasEntregas.get(count).setFuncionarioResponsavel(obj);
                obj.setPedido(novasEntregas.get(count));
                obj.setOcupado(true);
                obj.setRodadasParaEntrega();
                
                count++;
            }
        }

        List<Entregador> entregadoresOcupados = entregadores.stream().filter(x -> x.isOcupado()).collect(Collectors.toList());
        for (var obj: entregadoresOcupados){
            if (obj.executaFuncao()){
                pedidosFinalizados.add(obj.getPedidoAtual());
                pedidosParaEntrega.remove(obj.getPedidoAtual());
                obj.getPedidoAtual().setFuncionarioResponsavel(null);
                obj.setPedido(null);
                obj.setOcupado(false);
            }
        }
    }
    
    private void cancelarUmPedido(){
        tentativasDeCancelamento++;

        List<Pedido> pedidosElegiveis = pedidosParaColeta.stream().collect(Collectors.toList());
        pedidosElegiveis.addAll(pedidosParaEntrega.stream().filter(x -> x.getFuncionarioResponsavel() == null).collect(Collectors.toList()));

        int count = 1;
        System.out.println("\nEscolha um pedido elegível para cancelamento:");
        
        for (var obj: pedidosElegiveis){
            System.out.printf("[%d] - %s.\n", count, obj.getNome());
            count++;
        }
        System.out.print("[0] - Desistir do cancelamento.\nEscolha: ");

        int item = (sc.nextInt() - 1);

        if (item == -1)
            return;
        
        if (pedidosElegiveis.stream().anyMatch(x -> x.getId() == item)){
            Pedido pedido = pedidosElegiveis.get(item);
            
            if (pedidosParaColeta.stream().anyMatch(x -> x.getId() == item))
                pedidosParaColeta.remove(pedido);
            else
                pedidosParaEntrega.remove(pedido);

            pedido.getFuncionarioResponsavel().setOcupado(false);
            pedido.getFuncionarioResponsavel().setPedido(null);
            pedido.setFuncionarioResponsavel(null);

            pedidosCancelados.add(pedido);
        }
    }
}