package services;

import static com.mongodb.client.model.Accumulators.avg;
import java.util.concurrent.atomic.AtomicInteger;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import utils.PlotGraph;
import java.util.ArrayList;
import java.util.List;

public class MongoClientService {

    static MongoClient mongoClient = getClient();

    public static void topPlayersComMaisHeadshot() {
        System.out.println("Top 5 players com mais headshots");

        List<String> playerNames = new ArrayList<>();
        List<String> headshots = new ArrayList<>();

        try {
            MongoCollection<Document> collection = mongoClient.getDatabase("valorant").getCollection("ep4_act3_ranked_leaderboards");
            Bson sortStage = Aggregates.sort(Sorts.orderBy(Sorts.descending("headshots")));
            Bson limitStage = Aggregates.limit(5);
            Bson projectStage = Aggregates.project(Projections.fields(Projections.include("name", "tag", "region", "headshots"), Projections.exclude("_id")));
            collection.aggregate(List.of(sortStage, limitStage, projectStage)).forEach(document -> {
                System.out.println(document.toJson());
                playerNames.add(document.getString("name"));
                headshots.add(document.getString("headshots"));
            });

            PlotGraph.plotPlayerHeadshotGraph(playerNames, headshots);
        } catch (Exception e) {
            System.out.println("ERROR -> " + e.getMessage());
        }
        System.out.println("--------------------------------");
    }

    public static void topJogadoresComMaisMortesTotais() {
        List<String> playerNames = new ArrayList<>();
        List<String> mortes = new ArrayList<>();

        System.out.println("Top 5 jogadores com mais mortes totais");
        try {
            MongoCollection<Document> collection = mongoClient.getDatabase("valorant").getCollection("ep4_act3_ranked_leaderboards");
            Bson sortStage = Aggregates.sort(Sorts.orderBy(Sorts.descending("deaths")));
            Bson limitStage = Aggregates.limit(5);
            Bson projectStage = Aggregates.project(Projections.fields(Projections.include("name", "tag", "deaths"), Projections.exclude("_id")));
            collection.aggregate(List.of(sortStage, limitStage, projectStage)).forEach(document ->
            {
                System.out.println(document.toJson());
                playerNames.add(document.getString("name"));
                mortes.add(document.getString("deaths"));
            });

            PlotGraph.plotPlayerDeathsGraph(playerNames, mortes);
        } catch (Exception e) {
            System.out.println("ERROR -> " + e.getMessage());
        }
        System.out.println("--------------------------------");
    }

    public static void topArmasComEscolhaPrimariaPorMediaDeVitoria() {
        System.out.println("Top 5 armas com escolha primária por media de vitoria");
        List<String> weapon = new ArrayList<>();
        List<String> winrate = new ArrayList<>();

        try {
            MongoCollection<Document> collection = mongoClient.getDatabase("valorant").getCollection("ep4_act3_ranked_leaderboards");

            Bson groupStage = Aggregates.group("$gun1_name", avg("avg_win_percent", "$win_percent"));
            Bson sortStage = Aggregates.sort(Sorts.orderBy(Sorts.descending("avg_win_percent")));
            Bson limitStage = Aggregates.limit(5);
            collection.aggregate(List.of(groupStage, sortStage, limitStage)).forEach(document ->
            {
                System.out.println(document.toJson());
                weapon.add(document.getString("_id"));
                winrate.add(String.valueOf(document.getDouble("avg_win_percent")));
            });

            PlotGraph.plotPrimaryWeaponWinrateGraph(weapon, winrate);
        } catch (Exception e) {
            System.out.println("ERROR -> " + e.getMessage());
        }
        System.out.println("--------------------------------");
    }

    public static void topPlayersComMaiorTaxaDeVitoria() {
        System.out.println("Top 3 players com maior taxa de vitória com pelo menos 10 vitórias");

        List<String> playerNames = new ArrayList<>();
        List<Double> winrates = new ArrayList<>();

        try {
            MongoCollection<Document> collection = mongoClient.getDatabase("valorant").getCollection("ep4_act3_ranked_leaderboards");
            Bson sortStage = Aggregates.sort(Sorts.orderBy(Sorts.descending("wins")));
            Bson matchStage = Aggregates.match(Filters.gte("wins", 10));
            Bson limitStage = Aggregates.limit(3);
            Bson projectStage = Aggregates.project(
                    Projections.fields(Projections.include("name", "tag", "region", "win_percent"),
                            Projections.exclude("_id")));

            AtomicInteger contador = new AtomicInteger();

            collection.aggregate(List.of(sortStage, matchStage, limitStage, projectStage)).forEach(document ->
            {
                if (contador.get() < 2) {
                    System.out.println(document.toJson());
                    playerNames.add(document.getString("name"));
                    winrates.add(document.getDouble("win_percent"));
                    contador.getAndIncrement();
                    return;
                }
                playerNames.add(document.getString("name"));
                winrates.add(Double.parseDouble(String.valueOf(document.getInteger("win_percent"))));
            });

            PlotGraph.plotPlayersWithMostWinrate(playerNames, winrates);
        } catch (Exception e) {
            System.out.println("ERROR -> " + e.getMessage());
        }
        System.out.println("--------------------------------");
    }

    public static void topArmasComEscolhaPrimariaPorTotalDePlayers() {
        System.out.println("Top 3 armas com escolha primária por total de players");
        List<String> weapons = new ArrayList<>();
        List<String> total = new ArrayList<>();

        try {
            MongoCollection<Document> collection = mongoClient.getDatabase("valorant").getCollection("ep4_act3_ranked_leaderboards");

            Bson groupStage = Aggregates.group("$gun1_name", Accumulators.sum("count", 1));
            Bson sortStage = Aggregates.sort(Sorts.orderBy(Sorts.descending("count")));
            Bson limitStage = Aggregates.limit(3);
            Bson projectStage = Aggregates.project(
                    Projections.fields(
                            Projections.excludeId(),
                            Projections.include("count"),
                            Projections.computed("arma", "$_id")
                    )
            );
            collection.aggregate(List.of(groupStage, sortStage, limitStage, projectStage)).forEach(document ->
            {
                System.out.println(document.toJson());
                weapons.add(document.getString("arma"));
                total.add(String.valueOf(document.getInteger("count")));
            });

            PlotGraph.plotPrimaryWeaponChoiceCount(weapons, total);
        } catch (Exception e) {
            System.out.println("ERROR -> " + e.getMessage());
        }
        System.out.println("--------------------------------");
    }

    public static void topAgentesComEscolhaPrimariaPorTotalDePlayers() {
        System.out.println("Top 3 agentes com escolha primária por total de players");

        List<String> agents = new ArrayList<>();
        List<String> total = new ArrayList<>();

        try {
            MongoCollection<Document> collection = mongoClient.getDatabase("valorant").getCollection("ep4_act3_ranked_leaderboards");

            Bson groupStage = Aggregates.group("$agent_1", Accumulators.sum("count", 1));
            Bson sortStage = Aggregates.sort(Sorts.orderBy(Sorts.descending("count")));
            Bson limitStage = Aggregates.limit(3);
            Bson projectStage = Aggregates.project(
                    Projections.fields(
                            Projections.excludeId(),
                            Projections.include("count"),
                            Projections.computed("agente", "$_id")
                    )
            );
            collection.aggregate(List.of(groupStage, sortStage, limitStage, projectStage)).forEach(document ->
            {
                System.out.println(document.toJson());
                agents.add(document.getString("agente"));
                total.add(String.valueOf(document.getInteger("count")));
            });
            PlotGraph.plotPrimarAgentChoiceCount(agents, total);
        } catch (Exception e) {
            System.out.println("ERROR -> " + e.getMessage());
        }
        System.out.println("--------------------------------");
    }

    public static void quantidadeTotalDePlayersPorRanking() {
        System.out.println("Quantidade total de players por ranking");
        List<String> rankings = new ArrayList<>();
        List<String> total = new ArrayList<>();

        try {
            MongoCollection<Document> collection = mongoClient.getDatabase("valorant").getCollection("ep4_act3_ranked_leaderboards");

            Bson groupStage = Aggregates.group("$rating", Accumulators.sum("count", 1));
            Bson sortStage = Aggregates.sort(Sorts.orderBy(Sorts.descending("count")));
            Bson projectStage = Aggregates.project(
                    Projections.fields(
                            Projections.excludeId(),
                            Projections.include("count"),
                            Projections.computed("ranking", "$_id")

                    )
            );
            collection.aggregate(List.of(groupStage, sortStage, projectStage)).forEach(document ->
            {
                System.out.println(document.toJson());
                rankings.add(document.getString("ranking"));
                total.add(String.valueOf(document.getInteger("count")));
            });

            PlotGraph.plotTotalPlayersPerRankCount(rankings, total);
        } catch (Exception e) {
            System.out.println("ERROR -> " + e.getMessage());
        }
        System.out.println("--------------------------------");
    }

    public static synchronized MongoClient getClient() {
        MongoClient mongoClient;
        try {
            ConnectionString connectionString = new ConnectionString(System.getenv("MONGO_URL"));
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();
            mongoClient = MongoClients.create(settings);

            MongoDatabase database = mongoClient.getDatabase("admin");
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

        } catch (Exception e) {
            throw new RuntimeException("Unable to connect to MongoDB", e);
        }

        return mongoClient;
    }
}