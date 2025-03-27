import model.Baralho;
import model.Carta;
import model.Jogador;
import view.GameView;

import java.util.List;

public class GameController {
    private Baralho baralho;
    private Jogador jogador1;
    private Jogador jogador2;
    private GameView view;

    public GameController(Jogador jogador1, Jogador jogador2, GameView view) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.view = view;
        this.baralho = new Baralho();
    }

    public void iniciarJogo() {
        baralho.embaralhar();

        for (int i = 0; i < 3; i++) {
            jogador1.receberCarta(baralho.distribuirCarta());
            jogador2.receberCarta(baralho.distribuirCarta());
        }

        view.exibirMensagem("Cartas distribuídas!\n");
        mostrarCartasDosJogadores();

        jogarRodada();
    }

    private void mostrarCartasDosJogadores() {
        view.exibirMensagem("Jogador: " + jogador1.getNome());
        for (Carta c : jogador1.getMao()) {
            view.mostrarCarta(c);
        }

        view.exibirMensagem("\nJogador: " + jogador2.getNome());
        for (Carta c : jogador2.getMao()) {
            view.mostrarCarta(c);
        }

        view.exibirMensagem("\n");
    }

    public void jogarRodada() {
        // Cada jogador joga a carta da posição 0
        Carta carta1 = jogador1.jogarCarta(0);
        Carta carta2 = jogador2.jogarCarta(0);

        view.exibirMensagem(jogador1.getNome() + " jogou: " + carta1);
        view.exibirMensagem(jogador2.getNome() + " jogou: " + carta2);

        int resultado = compararCartas(carta1, carta2);

        if (resultado > 0) {
            jogador1.pontuar();
            view.exibirMensagem(jogador1.getNome() + " venceu a rodada!");
        } else if (resultado < 0) {
            jogador2.pontuar();
            view.exibirMensagem(jogador2.getNome() + " venceu a rodada!");
        } else {
            view.exibirMensagem("Rodada empatada!");
        }

        view.exibirMensagem("\nPontuação:");
        view.exibirMensagem(jogador1.getNome() + ": " + jogador1.getPontuacao());
        view.exibirMensagem(jogador2.getNome() + ": " + jogador2.getPontuacao());
    }

    private int compararCartas(Carta c1, Carta c2) {
        List<String> ordem = List.of("4", "5", "6", "7", "Q", "J", "K", "A", "2", "3");

        int valor1 = ordem.indexOf(c1.getValor());
        int valor2 = ordem.indexOf(c2.getValor());

        return Integer.compare(valor1, valor2);
    }
}
