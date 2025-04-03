import java.util.List;

public class GameController {
    private Baralho baralho;
    private Jogador jogador1;
    private Jogador jogador2;
    private RemoteGameView view;
    private int pontosRodada = 1;
    private Time time1;
    private Time time2;

    public GameController(Time time1, Time time2, RemoteGameView view) {
        this.time1 = time1;
        this.time2 = time2;
        this.view = view;
        this.baralho = new Baralho();
    }

    public GameController(Jogador jogador1, Jogador jogador2, RemoteGameView view) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.view = view;
        this.baralho = new Baralho();
    }


    public void iniciarJogo() {
        prepararBaralho();
        view.exibirMensagem("Cartas distribuídas!\n");
        mostrarCartasDosJogadores();

        int rodada = 0;
        while (!jogador1.getMao().isEmpty() && !jogador2.getMao().isEmpty() && rodada < 12) {
            rodada++;
            view.exibirMensagem("Rodada " + rodada + " de 12");
            jogarRodada();
        }

        exibirResultadoFinal();
    }

    private void prepararBaralho() {
        baralho.embaralhar();
        baralho.definirVira();
        view.exibirMensagem("A carta virada é: " + baralho.getVira());
        distribuirCartas(); // Ensure cards are distributed immediately after showing the "vira"
    }

    private void distribuirCartas() {
        for (int i = 0; i < 3; i++) {
            jogador1.receberCarta(baralho.distribuirCarta());
            jogador2.receberCarta(baralho.distribuirCarta());
        }
        view.enviarCartasParaJogador(jogador1); // Envia cartas para o jogador 1
        view.enviarCartasParaJogador(jogador2); // Envia cartas para o jogador 2
    }

    private void mostrarCartasDosJogadores() {
        view.enviarCartasParaJogador(jogador1); // Envia cartas apenas para o jogador 1
        view.enviarCartasParaJogador(jogador2); // Envia cartas apenas para o jogador 2
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
        view.exibirMensagem(time1.getNome() + ": " + time1.getPontuacaoTotal());
        view.exibirMensagem(time2.getNome() + ": " + time2.getPontuacaoTotal());

        if (time1.getPontuacaoTotal() > time2.getPontuacaoTotal()) {
            view.exibirMensagem(time1.getNome() + " venceu a partida!");
        } else if (time1.getPontuacaoTotal() < time2.getPontuacaoTotal()) {
            view.exibirMensagem(time2.getNome() + " venceu a partida!");
        } else {
            view.exibirMensagem("A partida terminou empatada!");
        }
    }

    private boolean processarPedidoDeTruco(Jogador oponente, Jogador quemPediu) {
        pontosRodada = 3; // Truco starts at 3 points
        view.exibirMensagem(oponente.getNome() + ", escolha uma opção:");
        view.exibirMensagem("1 - Aceitar o truco");
        view.exibirMensagem("2 - Pedir 6 pontos");
        view.exibirMensagem("3 - Recusar");

        int resposta = obterRespostaTruco();

        if (resposta == 3) { // Recusar
            view.exibirMensagem(oponente.getNome() + " recusou o truco!");
            quemPediu.pontuar(pontosRodada);
            view.exibirMensagem(quemPediu.getNome() + " ganhou " + pontosRodada + " pontos!");
            return false;
        } else if (resposta == 2) { // Pedir 6
            return escalarTruco(6, quemPediu, oponente);
        } else { // Aceitar
            view.exibirMensagem(oponente.getNome() + " aceitou o truco!");
            return true;
        }
    }

    private boolean escalarTruco(int novaPontuacao, Jogador quemPediu, Jogador oponente) {
        pontosRodada = novaPontuacao;
        view.exibirMensagem(quemPediu.getNome() + ", escolha uma opção:");
        view.exibirMensagem("1 - Aceitar jogar por " + novaPontuacao + " pontos");
        view.exibirMensagem("2 - Pedir " + (novaPontuacao + 3) + " pontos");
        view.exibirMensagem("3 - Recusar");

        int resposta = obterRespostaTruco();

        if (resposta == 3) { // Recusar
            view.exibirMensagem(quemPediu.getNome() + " recusou jogar por " + novaPontuacao + " pontos!");
            oponente.pontuar(pontosRodada);
            view.exibirMensagem(oponente.getNome() + " ganhou " + pontosRodada + " pontos!");
            return false;
        } else if (resposta == 2) { // Pedir mais pontos
            return escalarTruco(novaPontuacao + 3, oponente, quemPediu);
        } else { // Aceitar
            view.exibirMensagem(quemPediu.getNome() + " aceitou jogar por " + novaPontuacao + " pontos!");
            return true;
        }
    }

    private int obterRespostaTruco() {
        while (true) {
            try {
                int resposta = Integer.parseInt(lerLinha().trim());
                if (resposta >= 1 && resposta <= 3) {
                    return resposta;
                }
            } catch (NumberFormatException e) {
                view.exibirMensagem("Entrada inválida. Digite um número entre 1 e 3.");
            }
            view.exibirMensagem("Escolha inválida. Tente novamente.");
        }
    }

    private String lerLinha() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        return scanner.nextLine();
    }
}
