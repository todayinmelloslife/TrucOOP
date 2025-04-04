import java.io.*;
import java.util.List;

public class RemoteGameView {
    private ObjectInputStream in1;
    private ObjectOutputStream out1;
    private ObjectInputStream in2;
    private ObjectOutputStream out2;
    private int jogadorAtual = 1;
    public RemoteGameView(ObjectInputStream in1, ObjectOutputStream out1,
                          ObjectInputStream in2, ObjectOutputStream out2) {
        this.in1 = in1;
        this.out1 = out1;
        this.in2 = in2;
        this.out2 = out2;
    }

    public void exibirMensagem(String msg) {
        try {
            out1.writeObject(msg);
            out2.writeObject(msg);
        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    public boolean desejaPedirTruco() {
        String pergunta = "Deseja pedir truco? (s/n)";
        return respostaSimNao(pergunta);
    }

    public Carta escolherCarta(List<Carta> mao) {
        try {
            ObjectOutputStream out = jogadorAtual == 1 ? out1 : out2;
            ObjectInputStream in = jogadorAtual == 1 ? in1 : in2;

            out.writeObject("Escolha uma carta para jogar:");
            for (int i = 0; i < mao.size(); i++) {
                out.writeObject((i + 1) + " - " + mao.get(i)); // Lista enumerada
            }
            out.writeObject("Digite o número da carta:");

            int escolha;
            while (true) {
                try {
                    escolha = Integer.parseInt(((String) in.readObject()).trim());
                    if (escolha >= 1 && escolha <= mao.size()) {
                        break;
                    }
                    out.writeObject("Escolha inválida. Tente novamente.");
                } catch (Exception e) {
                    out.writeObject("Entrada inválida. Digite um número válido.");
                }
            }
            return mao.get(escolha - 1);

        } catch (Exception e) {
            System.out.println("Erro ao escolher carta: " + e.getMessage());
            return null;
        }
    }

    public boolean respostaSimNao(String pergunta) {
        try {
            ObjectOutputStream out = jogadorAtual == 1 ? out1 : out2;
            ObjectInputStream in = jogadorAtual == 1 ? in1 : in2;

            out.writeObject(pergunta);

            while (true) {
                String resposta = ((String) in.readObject()).trim().toLowerCase();
                if (resposta.equals("s")) return true;
                if (resposta.equals("n")) return false;
                out.writeObject("Resposta inválida. Digite 's' ou 'n'.");
            }
        } catch (Exception e) {
            System.out.println("Erro na respostaSimNao: " + e.getMessage());
            return false;
        }
    }

    public ObjectInputStream getScanner() {
        return jogadorAtual == 1 ? in1 : in2;
    }

    public void mostrarCarta(Carta carta, Jogador jogador) {
        try {
            ObjectOutputStream out = jogador.getId() == 1 ? out1 : out2; // Use ID instead of name
            out.writeObject("Sua carta: " + carta);
            out.flush();
        } catch (IOException e) {
            System.out.println("Erro ao mostrar carta: " + e.getMessage());
        }
    }

    public void setJogadorAtual(int jogador) {
        this.jogadorAtual = jogador;
        exibirMensagem("Agora é a vez do Jogador " + jogadorAtual);
    }

    public void close() {
        try {
            in1.close();
            out1.close();
            in2.close();
            out2.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar conexões: " + e.getMessage());
        }
    }

    public void enviarCartaParaCliente(Jogador jogador, Carta carta) {
        try {
            ObjectOutputStream out = jogador.getId() == 1 ? out1 : out2; // Use ID instead of name
            out.writeObject(carta != null ? carta.toString() : null); // Use null to indicate no more cards
            out.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar carta para o cliente: " + e.getMessage());
        }
    }

    public void enviarCartasParaJogador(Jogador jogador) {
        try {
            ObjectOutputStream out = jogador.getId() == 1 ? out1 : out2; // Use ID to determine the output stream
            List<Carta> mao = jogador.getMao();
            StringBuilder cartasFormatadas = new StringBuilder("Suas cartas:\n");

            if (jogador.getId() == 1) {
                // Envia as 3 primeiras cartas para o jogador 1
                for (int i = 0; i < Math.min(3, mao.size()); i++) {
                    cartasFormatadas.append((i + 1)).append(" - ").append(mao.get(i)).append("\n");
                }
            } else if (jogador.getId() == 2) {
                // Envia as 3 últimas cartas para o jogador 2
                for (int i = Math.max(0, mao.size() - 3); i < mao.size(); i++) {
                    cartasFormatadas.append((i - (mao.size() - 3) + 1)).append(" - ").append(mao.get(i)).append("\n");
                }
            }

            out.writeObject(cartasFormatadas.toString());
            out.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar cartas para o jogador: " + e.getMessage());
        }
    }
}