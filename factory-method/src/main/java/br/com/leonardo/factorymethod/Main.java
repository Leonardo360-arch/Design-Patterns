package br.com.leonardo.factorymethod;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.println("Sistema de entregas — Factory Method");

            while (true) {
                System.out.println();
                System.out.println("1 - Entrega de bicicleta");
                System.out.println("2 - Entrega de moto");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");

                if (!scanner.hasNextLine()) {
                    return;
                }

                String opcao = scanner.nextLine().strip();
                if (opcao.equals("0")) {
                    System.out.println("Programa encerrado.");
                    return;
                }

                // O menu seleciona o criador; o produto é criado pela subclasse.
                LogisticaEntrega logistica = switch (opcao) {
                    case "1" -> new LogisticaBicicleta();
                    case "2" -> new LogisticaMoto();
                    default -> null;
                };

                if (logistica == null) {
                    System.out.println("Opção inválida. Escolha 1, 2 ou 0.");
                    continue;
                }

                System.out.print("Nome do destinatário: ");
                if (!scanner.hasNextLine()) {
                    return;
                }

                try {
                    String resultado = logistica.processarEntrega(scanner.nextLine());
                    System.out.println(resultado);
                } catch (IllegalArgumentException erro) {
                    System.out.println(erro.getMessage());
                }
            }
        }
    }
}
