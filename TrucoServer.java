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

        Jogador jogador1 = new Jogador(nome1);
        Jogador jogador2 = new Jogador(nome2);

        RemoteGameView view = new RemoteGameView(in1, out1, in2, out2);
        GameController controller = new GameController(jogador1, jogador2, view); // Certifique-se de que o construtor está correto
        controller.iniciarJogo();

        socket1.close();
        socket2.close();
        server.close();
    }
}
