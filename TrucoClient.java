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
            }
        }
    }
}