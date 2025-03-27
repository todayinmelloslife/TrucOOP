import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitação do nome dos jogadores
        System.out.print("Digite o nome do Jogador 1: ");
        String nomeJogador1 = scanner.nextLine();

        System.out.print("Digite o nome do Jogador 2: ");
        String nomeJogador2 = scanner.nextLine();

        // Criação dos jogadores
        Jogador jogador1 = new Jogador(nomeJogador1);
        Jogador jogador2 = new Jogador(nomeJogador2);

        // Criação da interface do jogo
        GameView gameView = new GameView();

        // Inicialização do controlador do jogo
        GameController gameController = new GameController(jogador1, jogador2, gameView);

        // Início do jogo
        gameController.iniciarJogo();

        scanner.close();
    }
}
