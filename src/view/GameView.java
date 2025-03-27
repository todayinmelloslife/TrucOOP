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
            System.out.print("Digite o número da carta: ");
            escolha = scanner.nextInt();
            if (escolha > 0 && escolha <= mao.size()) {
                break;
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
            resposta = scanner.next().toLowerCase();
            if (resposta.equals("s")) {
                return true;
            } else if (resposta.equals("n")) {
                return false;
            }
            exibirMensagem("Resposta inválida. Digite 's' para sim ou 'n' para não.");
        }
    }
}
