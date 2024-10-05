# Projeto de Visualização de Dados do jogo Valorant

Este projeto em Java utiliza a biblioteca JFreeChart/Swing e o mongoDB para gerar gráficos de estatísticas de jogo, como taxa de vitória de armas, jogadores com maior taxa de vitória, escolha de armas e agentes, e total de jogadores por ranking.

## Requisitos

- Java 20 ou superior
- Maven
- mongoDB

### Configuração do Projeto

1. Clone o repositório:
    ```sh
    git clone <URL_DO_REPOSITORIO>
    cd <NOME_DO_DIRETORIO>
    ```

2. Importe a collection da pasta collection_mongo no seu mongoDB

3. Adicione nas variaveis de ambiente o MONGO_URL com a uri do seu mongoDB:
   ```sh
    MONGO_URL=uri_do_seu_mongo 
    ```

4. Ou edite o MongoClientService em:
    ```sh
    ConnectionString connectionString = new ConnectionString(System.getenv("MONGO_URL"));

### Executar classe Main

### Gráficos Disponíveis

1. **Gráfico de Taxa de Vitória por Arma Primária**
    ```java
    List<String> weapons = Arrays.asList("Arma1", "Arma2", "Arma3", "Arma4", "Arma5");
    List<String> winrates = Arrays.asList("50.0", "45.0", "60.0", "55.0", "65.0");
    PlotGraph.plotPrimaryWeaponWinrateGraph(weapons, winrates);
    ```

2. **Gráfico de Taxa de Vitória por Jogador**
    ```java
    List<String> playerNames = Arrays.asList("Jogador1", "Jogador2", "Jogador3");
    List<Double> winrates = Arrays.asList(70.0, 65.0, 60.0);
    PlotGraph.plotPlayersWithMostWinrate(playerNames, winrates);
    ```

3. **Gráfico de Escolha de Armas Primárias por Total de Players**
    ```java
    List<String> weapons = Arrays.asList("Arma1", "Arma2", "Arma3");
    List<String> total = Arrays.asList("100", "150", "200");
    PlotGraph.plotPrimaryWeaponChoiceCount(weapons, total);
    ```

4. **Gráfico de Escolha de Agentes Primários por Total de Players**
    ```java
    List<String> agents = Arrays.asList("Agente1", "Agente2", "Agente3");
    List<String> total = Arrays.asList("120", "130", "140");
    PlotGraph.plotPrimarAgentChoiceCount(agents, total);
    ```

5. **Gráfico Total de Players por Ranking**
    ```java
    List<String> rankings = Arrays.asList("Ranking1", "Ranking2", "Ranking3");
    List<String> total = Arrays.asList("300", "400", "500");
    PlotGraph.plotTotalPlayersPerRankCount(rankings, total);
    ```

## Contribuição

1. Faça um fork do projeto.
2. Crie uma nova branch: `git checkout -b minha-nova-funcionalidade`
3. Faça suas alterações e commit: `git commit -m 'Adiciona nova funcionalidade'`
4. Envie para o repositório remoto: `git push origin minha-nova-funcionalidade`
5. Abra um Pull Request.