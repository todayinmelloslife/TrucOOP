import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private List<Carta> mao;
    private int pontuacao;
    private int id; // Add ID field

    public Jogador(String nome, int id) { // Update constructor
        this.nome = nome;
        this.id = id;
        this.mao = new ArrayList<>();
        this.pontuacao = 0;
    }

    public void receberCarta(Carta carta) {
        mao.add(carta);
    }

    public Carta jogarCarta(int indice) {
        return mao.remove(indice);
    }

    public void pontuar(int pontos) {
        pontuacao += pontos;
    }

    public List<Carta> getMao() {
        return mao;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getNome() {
        return nome;
    }

    public int getId() { // Add getter for ID
        return id;
    }

    public String escolherCarta(String escolha) {
        try {
            int indice = Integer.parseInt(escolha) - 1; // Converte para índice baseado em 0
            if (indice >= 0 && indice < mao.size()) {
                return mao.get(indice).toString(); // Retorna a carta escolhida como string
            } else {
                return "Escolha inválida.";
            }
        } catch (NumberFormatException e) {
            return "Entrada inválida. Digite um número correspondente à carta.";
        }
    }

    @Override
    public String toString() {
        return "Jogador: " + nome + " | Pontuação: " + pontuacao;
    }
}
