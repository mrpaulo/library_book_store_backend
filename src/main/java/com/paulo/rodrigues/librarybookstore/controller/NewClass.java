/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package com.paulo.rodrigues.librarybookstore.controller;

import java.util.Scanner;

/**
 *
 * @author paulo
 */
public class NewClass {

    public static void main(String[] args) {
        executar();
    }

    public static void executar() {
        //Declaração de constantes
        String MENSAGEM_MAIOR = "Você é maior de idade";
        String MENSAGEM_MENOR = "Você é menor de idade";
        int IDADE_LEGAL = 18;
        int ANO_ATUAL = 2020;
        String SEXO_FEMININO = "Pulseira rosa";
        String SEXO_MASCULINO = "Pulseira azul";

        //Declaração de variaveis
        int idade = 0;
        String nomeCompleto = "";
        String sexo = "";
        int anoNascimento = 0;
        Double altura = 0.0;
        Double IMC = 0.0;
        double peso = 0.0;

        System.out.printf("Olá mundo!!!\n\n");
        //Preenchendo o cadastro, atribuições de valores para as varáveis
        Scanner scanner = new Scanner(System.in);

        System.out.printf("Informe o nome completo:\n");
        nomeCompleto = scanner.nextLine();

        System.out.printf("Informe o sexo:\n");
        sexo = scanner.next();

        System.out.printf("Informe o ano de nascimento:\n");
        anoNascimento = scanner.nextInt();

        System.out.println("Informe a sua altura:\n");
        altura = scanner.nextDouble();

        System.out.println("Informe a sua peso:\n");
        peso = scanner.nextDouble();

        idade = calcularIdade(anoNascimento);

        if (idade >= IDADE_LEGAL) {
            System.out.println("Olá " + nomeCompleto + ". " + MENSAGEM_MAIOR);
        } else {
            System.out.println("Olá " + nomeCompleto + ". " + MENSAGEM_MENOR);
        }

        if (sexo.equals("M")) {
            System.out.println(SEXO_MASCULINO);
        } else {
            System.out.println(SEXO_FEMININO);
        }
        System.out.println("Você tem " + idade + " anos de idade.");
        System.out.println("Você tem " + altura + " de altura.");
        
        IMC = calcularIMC(peso, altura);
        
        System.out.println("Você tem" + IMC);
    }

    public static int calcularIdade(int anoNascimentoParametro) {
        int result = 0;
        result = 2020 - anoNascimentoParametro;
        return result;
    }

    public static double calcularIMC(double pesoParametro, double alturaParametro) {
        double resultado = 0.0;
        resultado = pesoParametro / (alturaParametro * alturaParametro);
        return resultado;
    }
}
