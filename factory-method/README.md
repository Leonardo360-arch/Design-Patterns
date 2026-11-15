# Factory Method — entregas por bicicleta e moto

Projeto didático em Java que simula entregas pelo terminal. O objetivo é demonstrar como o **Factory Method** permite variar o objeto criado enquanto preserva o fluxo que o utiliza.

O exemplo usa somente a biblioteca padrão do Java, com dados em memória. Não exige banco de dados, Maven, Gradle ou serviços externos.

**Começando do zero no Windows?** Abra o [manual de instalação, execução e testes](docs/MANUAL_TESTES.md). O guia explica como instalar o JDK, baixar o projeto sem Git, executar sem IDE e registrar os resultados dos testes.

## Como funciona

1. O usuário escolhe bicicleta ou moto no menu.
2. O programa seleciona a logística correspondente e solicita o destinatário.
3. `LogisticaEntrega.processarEntrega()` valida o destinatário e remove espaços das extremidades com `strip()`.
4. Esse método chama `criarEntregador()`, cuja implementação pertence à logística escolhida.
5. O entregador criado realiza a simulação e devolve uma mensagem.

Destinatário nulo, vazio ou composto apenas por espaços gera `IllegalArgumentException` antes da criação do entregador. Entradas inválidas no menu permitem tentar novamente; a opção `0` e o fim da entrada encerram o programa.

## Participantes do padrão

| Papel no GoF | Elemento do projeto | Responsabilidade |
|---|---|---|
| Product | `Entregador` | Define o contrato `String entregar(String destinatario)`. |
| Concrete Products | `EntregadorBicicleta` e `EntregadorMoto` | Implementam a entrega de cada modalidade. |
| Creator | `LogisticaEntrega` | Mantém o fluxo de processamento e declara o método de criação. |
| Factory Method | `protected abstract Entregador criarEntregador()` | Permite que cada subclasse determine o produto concreto. |
| Concrete Creators | `LogisticaBicicleta` e `LogisticaMoto` | Sobrescrevem o método de criação para fornecer o entregador correspondente. |
| Cliente | `Main` | Lê as entradas, seleciona a logística e solicita o processamento. |

O método público `processarEntrega()` é `final`: as subclasses variam a criação, preservando a validação e a sequência comum de processamento.

## Por que é Factory Method

A decisão sobre o entregador concreto está nas subclasses de `LogisticaEntrega`, por meio da sobrescrita de `criarEntregador()`. O fluxo comum utiliza o contrato `Entregador`.

Uma **Simple Factory** concentraria essa escolha em um método com condicionais, por exemplo `criarEntregador(tipo)`, retornando diretamente bicicleta ou moto. Aqui, a variação acontece por herança e sobrescrita do método de criação.

O menu pode ter um `switch` para selecionar `LogisticaBicicleta` ou `LogisticaMoto`. Essa seleção faz parte da montagem da aplicação: ela não substitui o Factory Method, pois o menu não cria os entregadores.

## Requisitos

- JDK 17 ou superior instalado.
- Comandos `java` e `javac` disponíveis no terminal.
- Terminal aberto na pasta `factory-method`.

Para verificar o ambiente:

```text
java -version
javac -version
```

Os arquivos de produção ficam em `src/main/java/br/com/leonardo/factorymethod/`, todos no pacote `br.com.leonardo.factorymethod`.

## Executar no Windows PowerShell

Na raiz do repositório:

```powershell
cd factory-method
javac -encoding UTF-8 -d out src/main/java/br/com/leonardo/factorymethod/*.java
java -cp out br.com.leonardo.factorymethod.Main
```

## Executar no Linux ou macOS

Na raiz do repositório:

```bash
cd factory-method
javac -encoding UTF-8 -d out src/main/java/br/com/leonardo/factorymethod/*.java
java -cp out br.com.leonardo.factorymethod.Main
```

O parâmetro `-d out` direciona os arquivos compilados para a pasta `out`. Após alterar um arquivo Java, compile novamente antes de executar.

## Usar o programa

| Entrada no menu | Ação |
|---|---|
| `1` | Selecionar entrega por bicicleta. |
| `2` | Selecionar entrega por moto. |
| `0` | Encerrar. |
| Outra entrada | Informar a opção inválida e permitir nova tentativa. |

Após escolher uma modalidade, informe um destinatário, como `Leonardo`. A mensagem apresentada identifica a modalidade escolhida e o destinatário da entrega simulada.

## Executar os testes

Os testes ficam em `src/test/java/br/com/leonardo/factorymethod/TestesFactoryMethod.java` e usam um executável Java próprio, sem dependências de teste externas.

Com o terminal na pasta `factory-method`, execute os mesmos comandos no PowerShell, Linux ou macOS:

```text
javac -encoding UTF-8 -d out src/main/java/br/com/leonardo/factorymethod/*.java
javac -encoding UTF-8 -cp out -d out src/test/java/br/com/leonardo/factorymethod/TestesFactoryMethod.java
java -cp out br.com.leonardo.factorymethod.TestesFactoryMethod
```

Para a conferência manual do menu, experimente ambas as modalidades, uma opção inexistente, um destinatário em branco e a opção `0`. Também confira o encerramento quando a entrada termina, sem exibição de stack trace.

## Adicionar outra modalidade

Para acrescentar uma entrega por carro:

1. Crie `EntregadorCarro`, implementando `Entregador` e o método `entregar()`.
2. Crie `LogisticaCarro`, estendendo `LogisticaEntrega` e sobrescrevendo `criarEntregador()` para retornar `new EntregadorCarro()`.
3. Inclua uma opção no menu que selecione `LogisticaCarro`.
4. Acrescente testes para a nova modalidade.

O fluxo de `LogisticaEntrega` e os entregadores existentes continuam utilizáveis sem alterações. O menu precisa conhecer a nova logística para disponibilizá-la ao usuário.

## Limites do exemplo

A entrega é uma simulação textual. O escopo inicial concentra-se na criação dos objetos e no polimorfismo; cadastro, preço, rota e acompanhamento de pedidos podem ser estudados em outra etapa.
