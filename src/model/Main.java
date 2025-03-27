public class Main {
    public static void main(String[] args) {
        // Criação dos jogadores
        Jogador jogador1 = new Jogador("Dudinha");
        Jogador jogador2 = new Jogador("Murilo");

        // Criação da interface do jogo
        GameView gameView = new GameView();

        // Inicialização do controlador do jogo
        GameController gameController = new GameController(jogador1, jogador2, gameView);

        // Início do jogo
        gameController.iniciarJogo();
    }
}
