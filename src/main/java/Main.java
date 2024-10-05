import com.mongodb.MongoException;
import java.util.Scanner;
import static services.MongoClientService.*;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int option = 0;
            while (option != 8) {
                System.out.println("Dados de Ranqueamento do jogo Valorant.");
                System.out.println("Referente ao Episode 4: Act 3.");
                System.out.println("Escolha uma opção:");
                System.out.println("1. Top players com mais headshots");
                System.out.println("2. Top jogadores com mais mortes totais");
                System.out.println("3. Top armas com escolha primária por media de vitoria");
                System.out.println("4. Top players com maior taxa de vitoria");
                System.out.println("5. Top armas com escolha primária por total de players");
                System.out.println("6. Top agentes com escolha primária por total de players");
                System.out.println("7. Quantidade total de players por ranking");
                System.out.println("8. Sair");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        topPlayersComMaisHeadshot();
                        break;
                    case 2:
                        topJogadoresComMaisMortesTotais();
                        break;
                    case 3:
                        topArmasComEscolhaPrimariaPorMediaDeVitoria();
                        break;
                    case 4:
                        topPlayersComMaiorTaxaDeVitoria();
                        break;
                    case 5:
                        topArmasComEscolhaPrimariaPorTotalDePlayers();
                        break;
                    case 6:
                        topAgentesComEscolhaPrimariaPorTotalDePlayers();
                        break;
                    case 7:
                        quantidadeTotalDePlayersPorRanking();
                        break;
                    case 8:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (MongoException e) {
            System.out.println("ERROR -> " + e.getMessage());
        }
    }
}
