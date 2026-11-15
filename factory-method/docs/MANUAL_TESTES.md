# Manual de instalação, execução e testes

Este guia começa do zero: um PC com **Windows 10 ou 11, sem Java e sem IDE instalada**. Ele considera um computador com arquitetura x64.

Você vai instalar o JDK, baixar o projeto e usar o PowerShell do próprio Windows. O JDK fornece `javac`, que compila o código, e `java`, que executa o programa. IntelliJ, Eclipse, VS Code, Git e banco de dados não são necessários para seguir este guia.

O sistema simula entregas pelo terminal. Não existe uma janela gráfica nem uma entrega real.

## 1. Instalar o Java

1. Acesse a [página oficial do Eclipse Temurin JDK 21](https://adoptium.net/temurin/releases/?version=21&os=windows&arch=x64&package=jdk).
2. Confira as opções: **JDK 21 LTS**, **Windows**, **x64** e **JDK**. Baixe o instalador **`.msi`**. O JRE sozinho não fornece o compilador necessário.
3. Abra o instalador. Leia a licença e avance se concordar com os termos.
4. Em **Custom Setup / Configuração Personalizada**, mantenha habilitada a opção de adicionar o Java ao **PATH**. A configuração de `JAVA_HOME` é opcional para os comandos deste projeto.
5. Conclua a instalação e feche os terminais que estavam abertos.

Fonte das etapas do instalador: [documentação oficial do Temurin para Windows](https://adoptium.net/pt-BR/installation/windows).

### Conferir a instalação

Abra o menu Iniciar, pesquise **PowerShell** e abra o programa. Digite um comando por vez, pressionando Enter depois de cada um:

```powershell
java -version
javac -version
```

**Resultado esperado:** os dois comandos mostram uma versão, como `21.0.x`. O projeto requer JDK 17 ou superior; este manual usa o JDK 21. Os números de atualização podem variar.

Se aparecer “não é reconhecido”, consulte a seção de problemas comuns antes de continuar.

## 2. Baixar o projeto sem instalar Git

1. Entre na sua conta do GitHub e abra o [repositório Design-Patterns](https://github.com/Leonardo360-arch/Design-Patterns).
2. Confira se a branch selecionada é **main**.
3. Clique em **Code** e depois em **Download ZIP**.
4. No Explorador de Arquivos, clique com o botão direito no ZIP e selecione **Extrair Tudo**. Não execute os comandos dentro do arquivo compactado.
5. Entre na pasta extraída e localize a subpasta **`factory-method`**.
6. Abra essa subpasta. Ela deve conter `src`, `docs` e `README.md`.

O repositório é privado, portanto o download exige acesso à conta autorizada. Se você já tem uma cópia local atualizada, pode usá-la.

Fonte do download pelo navegador: [documentação do GitHub](https://docs.github.com/pt/repositories/working-with-files/using-files/downloading-source-code-archives).

## 3. Abrir o terminal no lugar certo

Com a pasta **`factory-method`** aberta no Explorador de Arquivos:

1. Clique na barra de endereço, onde aparece o caminho da pasta.
2. Digite `powershell` e pressione Enter.
3. Na janela que abrir, execute:

```powershell
Get-ChildItem
```

**Resultado esperado:** a listagem mostra a pasta `src`. Esse é o local de onde devem partir todos os próximos comandos. Não entre nas subpastas do pacote Java.

## 4. Compilar e executar

Copie **um comando por vez**. Copie somente o conteúdo dos blocos, sem os marcadores do Markdown ou o prefixo `PS C:\...>`.

### Preparar a exibição de acentos

```powershell
chcp 65001
```

Esse comando configura a página de código do terminal para UTF-8. Uma mensagem informando a página ativa é normal.

### Compilar os arquivos Java

```powershell
$fontes = Get-ChildItem ".\src\main\java\br\com\leonardo\factorymethod\*.java" | Select-Object -ExpandProperty FullName
javac -encoding UTF-8 -d out $fontes
```

**Resultado esperado:** o compilador termina sem mostrar erros e cria os arquivos compilados na pasta `out`. Não mostrar mensagem de sucesso é normal. Se houver erro, resolva-o antes de executar.

### Abrir o programa

```powershell
java "-Dfile.encoding=UTF-8" -cp out br.com.leonardo.factorymethod.Main
```

O terminal deve apresentar:

```text
Sistema de entregas — Factory Method

1 - Entrega de bicicleta
2 - Entrega de moto
0 - Sair
Escolha uma opção:
```

Digite o número e pressione Enter. Para uma entrega, o programa pergunta `Nome do destinatário:`. Digite o nome e pressione Enter novamente.

Depois de uma entrega ou de uma entrada inválida, o menu aparece de novo. Para encerrar normalmente, escolha `0`. Se necessário, `Ctrl+C` interrompe a execução.

## 5. Realizar os testes manuais

Execute os casos abaixo na ordem. **Enter** significa pressionar a tecla, não digitar a palavra. Nos casos com espaços, digite os espaços indicados. Não inclua aspas no nome.

Antes de CT-01, abra o programa com o comando da seção 4. Nos testes seguintes, continue usando o menu. Se tiver encerrado o programa, execute o comando novamente.

| Caso | O que fazer | Resultado esperado |
| --- | --- | --- |
| CT-01: abertura | Inicie o programa. | Aparecem o título, as opções `1`, `2` e `0` e a solicitação de escolha. |
| CT-02: bicicleta | Digite `1`, Enter, depois `Leonardo`, Enter. | Aparece `Entrega para Leonardo realizada de bicicleta.` e o menu retorna. |
| CT-03: moto | Digite `2`, Enter, depois `Cliente`, Enter. | Aparece `Entrega para Cliente realizada de moto.` e o menu retorna. |
| CT-04: número inválido | No menu, digite `9` e Enter. | Aparece `Opção inválida. Escolha 1, 2 ou 0.` e o menu retorna. |
| CT-05: letras no menu | No menu, digite `abc` e Enter. | Aparece a mesma mensagem de opção inválida. O programa continua aberto. |
| CT-06: menu vazio | No menu, pressione Enter sem digitar. | Aparece a mensagem de opção inválida. O programa continua aberto. |
| CT-07: nome vazio | Digite `1`, Enter e, no nome, apenas Enter. | Aparece `Informe o nome do destinatário.`. Nenhuma entrega ocorre e o programa volta ao menu principal. |
| CT-08: nome só com espaços | Digite `2`, Enter e, no nome, três espaços e Enter. | Aparece `Informe o nome do destinatário.`. Nenhuma entrega ocorre e o programa volta ao menu principal. |
| CT-09: nome com espaços nas pontas | Digite `1`, Enter. No nome, digite três espaços, `Ana Maria`, três espaços e Enter. | Aparece `Entrega para Ana Maria realizada de bicicleta.`. O programa remove os espaços das pontas e preserva o espaço entre os nomes. |
| CT-10: repetição e recuperação | Digite `9`, Enter. Depois faça uma entrega por bicicleta para `Ana` e uma por moto para `Bruno`. | O erro inicial não impede as entregas. As mensagens mostram os nomes e as modalidades corretos, sem reiniciar o programa. |
| CT-11: encerramento | No menu, digite `0` e Enter. | Aparece `Programa encerrado.` e o terminal volta a aceitar comandos do PowerShell. |

**Regra de aprovação:** o comportamento observado deve corresponder ao esperado. Não marque um caso como aprovado se ele não tiver sido executado.

### Conferência opcional do fim da entrada

Com o programa encerrado e o terminal na pasta `factory-method`, execute:

```powershell
cmd /c "java -cp out br.com.leonardo.factorymethod.Main < nul"
```

O comando inicia o programa sem fornecer entrada. **Resultado esperado:** o menu aparece e o programa encerra sem apresentar uma exceção ou ficar aguardando indefinidamente. Nesse caso, não é obrigatório aparecer `Programa encerrado.`, pois essa mensagem pertence à opção `0`.

## 6. Executar os oito testes automatizados

Esses testes verificam a lógica das classes. Eles complementam os testes manuais do menu.

1. Se o programa estiver aberto, escolha `0`.
2. Continue na pasta `factory-method`, depois da compilação da seção 4.
3. Compile a classe de testes:

```powershell
javac -encoding UTF-8 -cp out -d out .\src\test\java\br\com\leonardo\factorymethod\TestesFactoryMethod.java
```

4. Execute os testes:

```powershell
java "-Dfile.encoding=UTF-8" -cp out br.com.leonardo.factorymethod.TestesFactoryMethod
```

**Resultado esperado:** oito linhas iniciadas por `OK -` e, ao final:

```text
Resultado: 8/8 testes aprovados.
```

Os testes cobrem os produtos criados, a delegação ao entregador, a normalização dos nomes, a rejeição de entradas inválidas antes da criação e uma nova modalidade definida apenas no teste. A modalidade por drone usada nessa verificação não aparece no menu do programa.

Se aparecer `FALHOU` ou uma exceção, registre a saída no relatório. Não altere o resultado esperado apenas para considerar o teste aprovado.

## 7. Registrar o resultado da sua execução

Copie o modelo abaixo para um arquivo de texto, preencha e guarde com as evidências. Ele é um modelo em branco, não um relatório de testes já realizados no seu PC.

```text
RELATÓRIO DE TESTE - FACTORY METHOD

Responsável:
Data e horário:
Windows e arquitetura:
Saída de java -version:
Saída de javac -version:
Versão do projeto (commit ou data em que baixou o ZIP):

CT-01: Não executado
CT-02: Não executado
CT-03: Não executado
CT-04: Não executado
CT-05: Não executado
CT-06: Não executado
CT-07: Não executado
CT-08: Não executado
CT-09: Não executado
CT-10: Não executado
CT-11: Não executado
Fim da entrada (opcional): Não executado
Testes automatizados: Não executado

Para cada falha:
- Caso e passos realizados:
- Resultado esperado:
- Resultado observado:
- Evidência (print ou texto da saída):

Conclusão:
```

Substitua `Não executado` por **Aprovado**, **Reprovado** ou **Bloqueado** somente depois da tentativa. Use **Bloqueado** quando um problema anterior impedir a execução, como a instalação do JDK incompleta.

Para evidências rápidas, capture o terminal com `Win+Shift+S`. Dê preferência a um print com o nome do caso, a entrada utilizada e a saída correspondente.

## 8. Problemas comuns

| Problema | Como resolver |
| --- | --- |
| `java` ou `javac` não é reconhecido | Feche e abra novamente o PowerShell. Se continuar, confira a instalação do JDK e a opção PATH no instalador. |
| `java` funciona, mas `javac` não | Confira se instalou o JDK completo. Execute `Get-Command java` e `Get-Command javac` para identificar os caminhos encontrados. |
| Não encontro `src` ou o comando não encontra arquivos | Volte à pasta `factory-method` que contém `src`. Confirme que extraiu o ZIP. Repita a compilação nessa pasta. |
| `Could not find or load main class` | Compile novamente e execute a partir de `factory-method`. Use o nome completo `br.com.leonardo.factorymethod.Main`, sem `.java` nem `.class`. |
| `UnsupportedClassVersionError` | O Java usado para executar é mais antigo que o compilador. Confira as duas versões, ajuste o ambiente para o mesmo JDK e compile novamente. |
| Acentos aparecem incorretamente | Reabra o terminal, execute `chcp 65001` e use o comando `java "-Dfile.encoding=UTF-8" ...` indicado neste guia. |
| O programa não aceita comandos do PowerShell | Ele pode estar aguardando uma opção ou um nome. Volte ao menu e escolha `0` antes de executar comandos do terminal. |
| Alterei o código, mas o resultado continua igual | Compile novamente. O comando `java -cp out ...` executa os arquivos compilados em `out`. |

## 9. Sequência curta para a apresentação

Com a instalação e a compilação já concluídas:

1. Abra o programa.
2. Mostre uma entrega por bicicleta.
3. Mostre uma entrega por moto.
4. Digite uma opção inválida para mostrar a recuperação.
5. Encerre com `0`.
6. Explique: **“A classe base organiza a entrega. As subclasses escolhem qual entregador criar.”**

Faça o ensaio antes da apresentação e guarde uma captura das duas modalidades como apoio.
