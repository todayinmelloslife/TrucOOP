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

        out1.writeObject("Digite seu nome:");
        String nome1 = (String) in1.readObject();

        out2.writeObject("Digite seu nome:");
        String nome2 = (String) in2.readObject();

        Jogador jogador1 = new Jogador(nome1, 1); // Assign ID 1
        Jogador jogador2 = new Jogador(nome2, 2); // Assign ID 2

        Time time1 = new Time("Time 1");
        Time time2 = new Time("Time 2");
        time1.adicionarJogador(jogador1);
        time2.adicionarJogador(jogador2);

        RemoteGameView view = new RemoteGameView(in1, out1, in2, out2);
        GameController controller = new GameController(time1, time2, view);

        // Sincroniza a carta "vira" e distribui cartas diferentes para os jogadores
        controller.prepararBaralho();
        out1.writeObject("A carta virada é: " + controller.getBaralho().getVira());
        out2.writeObject("A carta virada é: " + controller.getBaralho().getVira());

        controller.distribuirCartas(); // Garante que ambos os jogadores recebam cartas diferentes
        out1.writeObject("Cartas distribuídas!");
        out2.writeObject("Cartas distribuídas!");

        controller.iniciarJogo();

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
