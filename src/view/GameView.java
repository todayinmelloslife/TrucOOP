import java.util.List;
import java.util.Scanner;

public class GameView {
    private Scanner scanner = new Scanner(System.in);

    public void exibirMensagem(String msg) {
        System.out.println(msg);
    }

    
    public void mostrarCarta(Carta carta) {
        System.out.println(" - " + carta); // usa o toString() da Carta
    }


    public Carta escolherCarta(List<Carta> mao) {
        exibirMensagem("Escolha uma carta para jogar:");
        for (int i = 0; i < mao.size(); i++) {
            System.out.println((i + 1) + " - " + mao.get(i));
        }

        int escolha;
        while (true) {
            try {
                System.out.print("Digite o número da carta: ");
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha > 0 && escolha <= mao.size()) {
                    break;
                }
            } catch (NumberFormatException e) {
                exibirMensagem("Entrada inválida. Digite um número.");
            }
            System.out.println("Escolha inválida. Tente novamente.");
        }

        return mao.get(escolha - 1);
    }

    public boolean desejaPedirTruco() {
        exibirMensagem("Deseja pedir truco? (s/n)");
        return respostaSimNao();
    }

    public boolean respostaSimNao() {
        String resposta;
        while (true) {
            resposta = scanner.nextLine().trim().toLowerCase(); // Usar nextLine() para evitar problemas de leitura
            if (resposta.equals("s")) {
                return true;
            } else if (resposta.equals("n")) {
                return false;
            }
            exibirMensagem("Resposta inválida. Digite 's' para sim ou 'n' para não.");
        }
    }

    public void closeScanner() {
        scanner.close();
    }

    public Scanner getScanner() {
        return scanner;
    }
}
