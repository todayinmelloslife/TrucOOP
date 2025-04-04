import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TrucoClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            // Mensagem de boas-vindas
            System.out.println("Bem-vindo ao Truco!");

            handleServerCommunication(in, out, scanner);

        } catch (Exception e) {
            System.out.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace(); // Added for better debugging
        }
    }

    private static void handleServerCommunication(ObjectInputStream in, ObjectOutputStream out, Scanner scanner) throws IOException, ClassNotFoundException {
        while (true) {
            Object resposta = in.readObject();
            if (resposta instanceof String) {
                String msg = (String) resposta;
                System.out.println(msg);

                if (msg.toLowerCase().contains("jogo encerrado")) {
                    out.writeObject("ack"); // Send acknowledgment to the server
                    break;
                }

                if (msg.toLowerCase().contains("fim") || msg.toLowerCase().contains("encerrado")) {
                    break;
                }

                String entrada = scanner.nextLine();
                if (entrada != null && !entrada.trim().isEmpty()) {
                    out.writeObject(entrada);
                    out.flush(); // Garante que os dados sejam enviados imediatamente
                }
            } else if (resposta instanceof String[]) {
                // Recebe as cartas enviadas pelo servidor
                String[] cartas = (String[]) resposta;
                System.out.println("Suas cartas:");
                for (String carta : cartas) {
                    System.out.println("- " + carta);
                }

                // Notifica o servidor que as cartas foram recebidas
                out.writeObject("cartas recebidas");
                out.flush();

                // Aguarda confirmação do servidor de que ambos os jogadores receberam as cartas
                Object confirmacao = in.readObject();
                if (confirmacao instanceof String && ((String) confirmacao).equalsIgnoreCase("cartas distribuídas")) {
                    System.out.println("As cartas foram distribuídas para ambos os jogadores.");
                    System.out.println("A carta virada é: " + in.readObject()); // Recebe a carta virada do servidor
                    System.out.println("\nRodada 1 de 12");
                    System.out.println("Escolha a carta para jogar:");
                    iniciarJogo(scanner, out);
                }
            }
        }
    }

    private static void iniciarJogo(Scanner scanner, ObjectOutputStream out) throws IOException {
        System.out.println("Faça sua jogada ou digite 'truco' para pedir truco:");
        while (true) {
            String jogada = scanner.nextLine();
            if (jogada != null && !jogada.trim().isEmpty()) {
                out.writeObject(jogada);
                out.flush(); // Garante que os dados sejam enviados imediatamente

                if (jogada.equalsIgnoreCase("fim")) {
                    System.out.println("Você encerrou o jogo.");
                    break;
                }
            }
        }
    }
}