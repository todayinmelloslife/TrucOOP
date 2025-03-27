import java.util.List;

public class GameController {
    private Baralho baralho;
    private Jogador jogador1;
    private Jogador jogador2;
    private GameView view;
    private int pontosRodada = 1;

    public GameController(Jogador jogador1, Jogador jogador2, GameView view) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.view = view;
        this.baralho = new Baralho();
    }

    public void iniciarJogo() {
        baralho.embaralhar();
        baralho.definirVira();

        view.exibirMensagem("A carta virada é: " + baralho.getVira());
        // Linha removida: view.exibirMensagem("As manilhas são: " + baralho.getManilhas() + "\n");

        for (int i = 0; i < 3; i++) {
            jogador1.receberCarta(baralho.distribuirCarta());
            jogador2.receberCarta(baralho.distribuirCarta());
        }

        view.exibirMensagem("Cartas distribuídas!\n");
        mostrarCartasDosJogadores();

        while (!jogador1.getMao().isEmpty() && !jogador2.getMao().isEmpty()) {
            jogarRodada();
        }

        exibirResultadoFinal();
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

    private void jogarRodada() {
        Carta carta1 = realizarJogada(jogador1, jogador2);
        Carta carta2 = realizarJogada(jogador2, jogador1);

        view.exibirMensagem(jogador1.getNome() + " jogou: " + carta1);
        view.exibirMensagem(jogador2.getNome() + " jogou: " + carta2);

        processarResultadoRodada(carta1, carta2);
    }

    private Carta realizarJogada(Jogador jogador, Jogador oponente) {
        view.exibirMensagem(jogador.getNome() + ", escolha a carta para jogar:");
        if (view.desejaPedirTruco() && !processarPedidoDeTruco(oponente, jogador)) {
            return null; // Pula para a próxima rodada
        }
        return jogador.jogarCarta(jogador.getMao().indexOf(escolherCarta(jogador.getMao())));
    }

    private void processarResultadoRodada(Carta carta1, Carta carta2) {
        int resultado = compararCartas(carta1, carta2);

        if (resultado > 0) {
            jogador1.pontuar(pontosRodada);
            view.exibirMensagem(jogador1.getNome() + " venceu a rodada!");
        } else if (resultado < 0) {
            jogador2.pontuar(pontosRodada);
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
        List<String> manilhas = baralho.getManilhas();

        boolean c1Manilha = manilhas.contains(c1.getValor());
        boolean c2Manilha = manilhas.contains(c2.getValor());

        if (c1Manilha && c2Manilha) {
            return Integer.compare(manilhas.indexOf(c1.getValor()), manilhas.indexOf(c2.getValor()));
        } else if (c1Manilha) {
            return 1;
        } else if (c2Manilha) {
            return -1;
        }

        int valor1 = ordem.indexOf(c1.getValor());
        int valor2 = ordem.indexOf(c2.getValor());

        return Integer.compare(valor1, valor2);
    }

    private Carta escolherCarta(List<Carta> mao) {
        return view.escolherCarta(mao);
    }

    private void exibirResultadoFinal() {
        view.exibirMensagem("\nFim da partida!");
        view.exibirMensagem("Pontuação final:");
        view.exibirMensagem(jogador1.getNome() + ": " + jogador1.getPontuacao());
        view.exibirMensagem(jogador2.getNome() + ": " + jogador2.getPontuacao());

        if (jogador1.getPontuacao() > jogador2.getPontuacao()) {
            view.exibirMensagem(jogador1.getNome() + " venceu a partida!");
        } else if (jogador1.getPontuacao() < jogador2.getPontuacao()) {
            view.exibirMensagem(jogador2.getNome() + " venceu a partida!");
        } else {
            view.exibirMensagem("A partida terminou empatada!");
        }
    }

    private boolean processarPedidoDeTruco(Jogador oponente, Jogador quemPediu) {
        view.exibirMensagem(oponente.getNome() + ", você aceita o truco? (s/n)");
        if (!view.respostaSimNao()) {
            view.exibirMensagem(oponente.getNome() + " não aceitou o truco!");
            quemPediu.pontuar(pontosRodada);
            view.exibirMensagem(quemPediu.getNome() + " ganhou " + pontosRodada + " pontos!");
            return false; // O truco foi recusado, pula para a próxima rodada
        }
        view.exibirMensagem(oponente.getNome() + " aceitou o truco!");
        pontosRodada = Math.min(pontosRodada + 3, 12); // Incrementa os pontos da rodada
        return true; // O truco foi aceito
    }
}
