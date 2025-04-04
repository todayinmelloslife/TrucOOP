import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.List;

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

                if (msg.toLowerCase().contains("suas cartas:")) {
                    System.out.println("Escolha uma carta digitando o número correspondente:");
                    String escolha = scanner.nextLine();
                    try {
                        int indice = Integer.parseInt(escolha) - 1; // Converte para índice baseado em 0
                        System.out.println("Escolha inválida. Tente novamente.");
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Digite um número correspondente à carta.");
                    }
                    out.flush();
                    continue;
                }

                String entrada = scanner.nextLine();
                if (entrada != null && !entrada.trim().isEmpty()) {
                    out.writeObject(entrada);
                    out.flush(); // Garante que os dados sejam enviados imediatamente
                }
            } else if (resposta instanceof List) {
                // Recebe a lista de cartas enviadas pelo servidor
                List<String> cartas = (List<String>) resposta;
                System.out.println("Suas cartas:");
                for (int i = 0; i < cartas.size(); i++) {
                    System.out.println((i + 1) + " - " + cartas.get(i)); // Lista enumerada
                }

                System.out.println("Escolha uma carta digitando o número correspondente:");
                String escolha = scanner.nextLine();
                try {
                    int indice = Integer.parseInt(escolha) - 1; // Converte para índice baseado em 0
                    if (indice >= 0 && indice < cartas.size()) {
                        String cartaEscolhida = cartas.get(indice);
                        System.out.println("Você escolheu a carta: " + cartaEscolhida);
                        out.writeObject(cartaEscolhida); // Envia a carta escolhida ao servidor
                    } else {
                        System.out.println("Escolha inválida. Tente novamente.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um número correspondente à carta.");
                }
                out.flush();
            }
        }
    }

    private static void iniciarJogo(Scanner scanner, ObjectOutputStream out) throws IOException {
        System.out.println("Faça sua jogada. Digite '1', '2', ou '3' para escolher uma carta ou 'truco' para pedir truco:");
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