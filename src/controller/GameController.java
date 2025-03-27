import src.model.Baralho;
import src.model.Carta;
import src.model.Jogador;
import src.view.GameView;

public class GameController {
    private Baralho baralho;
    private Jogador jogador;
    private GameView view;

    // Construtor: recebe o jogador e a view
    public GameController(Jogador jogador, GameView view) {
        this.jogador = jogador;
        this.view = view;
        this.baralho = new Baralho(); // cria e prepara o baralho
    }

    // Método principal para iniciar o jogo
    public void iniciarJogo() {
        baralho.embaralhar(); // embaralha as cartas

        // entrega 3 cartas para o jogador
        for (int i = 0; i < 3; i++) {
            Carta carta = baralho.distribuirCarta();
            jogador.receberCarta(carta);
        }

        // mostra no terminal as cartas recebidas
        view.exibirMensagem("Jogador: " + jogador.getNome());
        view.exibirMensagem("Cartas recebidas:");
        for (Carta c : jogador.getMao()) {
            view.mostrarCarta(c);
        }
    }
}
