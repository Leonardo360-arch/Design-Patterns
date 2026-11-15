# Design Patterns em Java

Projetos pequenos para estudar padrões de projeto, com exemplos executáveis pelo terminal e explicações em português.

| Padrão | Projeto | Documentação |
| --- | --- | --- |
| Factory Method | Entregas por bicicleta e moto | [Abrir o projeto](factory-method/README.md) |

## Primeiro projeto

O exemplo de **Factory Method** demonstra como uma classe mantém o fluxo de uma entrega enquanto suas subclasses escolhem qual entregador criar.

Requisito: **JDK 17 ou superior**. Cada exemplo mantém seu código e suas instruções na própria pasta.

```bash
cd factory-method
javac -encoding UTF-8 -d out src/main/java/br/com/leonardo/factorymethod/*.java
java -cp out br.com.leonardo.factorymethod.Main
```

O sistema simula as entregas pelo terminal e utiliza dados em memória.
