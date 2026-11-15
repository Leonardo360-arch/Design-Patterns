package br.com.leonardo.factorymethod;

public final class TestesFactoryMethod {
    private static int aprovados;

    private TestesFactoryMethod() {
    }

    public static void main(String[] args) {
        executar("Criador de bicicleta fornece o produto correto", () ->
                verificar(new LogisticaBicicleta().criarEntregador() instanceof EntregadorBicicleta,
                        "A logística de bicicleta deve criar um entregador de bicicleta."));

        executar("Criador de moto fornece o produto correto", () ->
                verificar(new LogisticaMoto().criarEntregador() instanceof EntregadorMoto,
                        "A logística de moto deve criar um entregador de moto."));

        executar("Modalidades reais entregam para o destinatário informado", () -> {
            String bicicleta = new LogisticaBicicleta().processarEntrega("Leonardo");
            String moto = new LogisticaMoto().processarEntrega("Leonardo");
            verificar(bicicleta != null && bicicleta.contains("Leonardo"),
                    "O resultado da bicicleta deve identificar o destinatário.");
            verificar(moto != null && moto.contains("Leonardo"),
                    "O resultado da moto deve identificar o destinatário.");
            verificar(!bicicleta.equals(moto), "As modalidades devem produzir resultados distintos.");
        });

        executar("Fluxo comum delega ao produto criado", () -> {
            LogisticaEspia logistica = new LogisticaEspia();
            String resultado = logistica.processarEntrega("Cliente");
            verificar("resultado do produto".equals(resultado),
                    "O fluxo deve devolver o resultado do produto.");
            verificar(logistica.criacoes == 1 && logistica.entregas == 1,
                    "Cada processamento deve criar e acionar um entregador uma vez.");
            verificar("Cliente".equals(logistica.destinatarioRecebido),
                    "O produto deve receber o destinatário informado.");
        });

        executar("Espaços nas extremidades são removidos antes da entrega", () -> {
            LogisticaEspia logistica = new LogisticaEspia();
            logistica.processarEntrega("\u2003  Ana Maria \t\u2003");
            verificar("Ana Maria".equals(logistica.destinatarioRecebido),
                    "Normalização deve remover espaços nas extremidades e preservar os internos.");
        });

        executar("Destinatário nulo é rejeitado antes da criação", () -> {
            LogisticaEspia logistica = new LogisticaEspia();
            esperarArgumentoInvalido(() -> logistica.processarEntrega(null));
            verificar(logistica.criacoes == 0 && logistica.entregas == 0,
                    "Uma solicitação inválida não deve criar nem executar o produto.");
        });

        executar("Destinatários vazios ou em branco são rejeitados", () -> {
            for (String destinatario : new String[] {"", "   ", "\t\n", "\u2003"}) {
                LogisticaEspia logistica = new LogisticaEspia();
                esperarArgumentoInvalido(() -> logistica.processarEntrega(destinatario));
                verificar(logistica.criacoes == 0 && logistica.entregas == 0,
                        "Entrada em branco não deve criar nem executar o produto.");
            }
        });

        executar("Nova modalidade reutiliza o fluxo sem alterar a classe base", () -> {
            LogisticaEntrega logisticaDrone = new LogisticaEntrega() {
                @Override
                protected Entregador criarEntregador() {
                    return destinatario -> "Drone entregou para " + destinatario;
                }
            };
            verificar("Drone entregou para João".equals(logisticaDrone.processarEntrega(" João ")),
                    "Um novo criador deve reutilizar a validação e o processamento existentes.");
        });

        System.out.println("Resultado: " + aprovados + "/8 testes aprovados.");
    }

    private static void executar(String nome, Runnable teste) {
        try {
            teste.run();
            aprovados++;
            System.out.println("OK - " + nome);
        } catch (AssertionError | RuntimeException falha) {
            throw new AssertionError("FALHOU - " + nome, falha);
        }
    }

    private static void verificar(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }

    private static void esperarArgumentoInvalido(Runnable operacao) {
        try {
            operacao.run();
        } catch (IllegalArgumentException esperada) {
            return;
        }
        throw new AssertionError("Era esperada uma IllegalArgumentException.");
    }

    private static final class LogisticaEspia extends LogisticaEntrega {
        private int criacoes;
        private int entregas;
        private String destinatarioRecebido;

        @Override
        protected Entregador criarEntregador() {
            criacoes++;
            return destinatario -> {
                entregas++;
                destinatarioRecebido = destinatario;
                return "resultado do produto";
            };
        }
    }
}
