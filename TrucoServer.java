import java.net.*;
import java.io.*;

public class TrucoServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(12345);
        System.out.println("Aguardando jogadores...");

        Socket socket1 = server.accept();
        System.out.println("Jogador 1 conectado.");
        ObjectOutputStream out1 = new ObjectOutputStream(socket1.getOutputStream());
        ObjectInputStream in1 = new ObjectInputStream(socket1.getInputStream());

        Socket socket2 = server.accept();
        System.out.println("Jogador 2 conectado.");
        ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());
        ObjectInputStream in2 = new ObjectInputStream(socket2.getInputStream());

        out1.writeObject("Digite o nome do Jogador 1:");
        String nomeJogador1 = (String) in1.readObject();

        out2.writeObject("Digite o nome do Jogador 2:");
        String nomeJogador2 = (String) in2.readObject();

        Jogador jogador1 = new Jogador(nomeJogador1, 1);
        Jogador jogador2 = new Jogador(nomeJogador2, 2);

        Time time1 = new Time("Time 1");
        Time time2 = new Time("Time 2");
        time1.adicionarJogador(jogador1);
        time2.adicionarJogador(jogador2);

        RemoteGameView gameView = new RemoteGameView(in1, out1, in2, out2);
        GameController gameController = new GameController(time1, time2, gameView);

        gameController.iniciarJogo();

        while (true) {
            out1.writeObject("Jogador 1, escolha uma carta digitando o número correspondente:");
            String escolha1 = (String) in1.readObject();
            String cartaEscolhida1 = jogador1.escolherCarta(escolha1);
            out1.writeObject("Jogador 1 escolheu: " + cartaEscolhida1);

            out2.writeObject("Jogador 2, escolha uma carta digitando o número correspondente:");
            String escolha2 = (String) in2.readObject();
            String cartaEscolhida2 = jogador2.escolherCarta(escolha2);
            out2.writeObject("Jogador 2 escolheu: " + cartaEscolhida2);

            if (gameController.verificarFimDeJogo()) {
                out1.writeObject("Jogo encerrado!");
                out2.writeObject("Jogo encerrado!");
                break;
            }
        }

        // Wait for clients to acknowledge the end of the game
        out1.writeObject("Jogo encerrado. Obrigado por jogar!");
        out2.writeObject("Jogo encerrado. Obrigado por jogar!");
        in1.readObject(); // Wait for acknowledgment from client 1
        in2.readObject(); // Wait for acknowledgment from client 2

        socket1.close();
        socket2.close();
        server.close();
    }
}
