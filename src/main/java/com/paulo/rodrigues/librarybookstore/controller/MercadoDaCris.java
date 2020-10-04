/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.controller;

import java.util.Scanner;

/**
 *
 * @author Cristina do Couto
 */
public class MercadoDaCris {

    public static void main(String[] args) {
        executar();
    }

    public static void executar() {

        String nomeProduto = "";
        double precoProduto = 0.0;
        int quantidade = 0;
        double valorPedido = 0.0;
        String pagamentoAvista = "";
        char corredorDoProduto;
        corredorDoProduto = 'D';
        double valorEntrega = 0.0;
        valorEntrega = 10.0;
        boolean entregaGratis = false;
        String haveraEntrega = "";
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do produto :");
        nomeProduto = scanner.nextLine();
        System.out.println("Digite o preco do produto");
        precoProduto = scanner.nextDouble();
        System.out.println("Digite quantidade do produto");
        quantidade = scanner.nextInt();
        valorPedido = quantidade * precoProduto;
        System.out.println("Pagamento a vista (S/N)?");
        pagamentoAvista = scanner.next();
        System.out.println("Haverá entrega (S/N)?");
        haveraEntrega = scanner.next();

        if (pagamentoAvista.equals("S")) {
            //DESCONTO DE 1 REAL NO PAGAMENTO A VISTA
            valorPedido = valorPedido - 1;
        }
        if (haveraEntrega.equals("S")) {
            entregaGratis = valorPedido > 300;

            if (!entregaGratis) {
                //deve pagar entrega
                valorPedido = valorPedido + valorEntrega;
            }
            System.out.println("Haverá entrega");
        } else {
            System.out.println("Não haverá entrega");
        }

        System.out.println("Nome do Produto: " + nomeProduto);
        System.out.println("Valor do Pedido: " + valorPedido);
        System.out.println("Pagamento A Vista:" + pagamentoAvista);
        System.out.println("Corredor do Produto:" + corredorDoProduto);

        if (haveraEntrega.equals("S")) {
            System.out.println("Valor da Entrega:" + valorEntrega);
            System.out.println("Entrega Grátis:" + entregaGratis);
        }

        if (quantidade < 15) {
            System.out.print("caixa rápido");
        } else {
            System.out.print("caixa normal");
        }
    }
}
