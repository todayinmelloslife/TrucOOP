import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do Jogador 1: ");
        String nomeJogador1 = scanner.nextLine();

        System.out.print("Digite o nome do Jogador 2: ");
        String nomeJogador2 = scanner.nextLine();

        Jogador jogador1 = new Jogador(nomeJogador1);
        Jogador jogador2 = new Jogador(nomeJogador2);

        GameView gameView = new GameView();

        GameController gameController = new GameController(jogador1, jogador2, gameView);

        gameController.iniciarJogo();

        scanner.close();
    }
}
