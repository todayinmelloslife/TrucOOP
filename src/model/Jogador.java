import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private List<Carta> mao;
    private int pontuacao;

    public Jogador(String nome) {
        this.nome = nome;
        this.mao = new ArrayList<>();
        this.pontuacao = 0;
    }

    public void receberCarta(Carta carta) {
        mao.add(carta);
    }

    public Carta jogarCarta(int indice) {
        return mao.remove(indice);
    }

    public void pontuar() {
        pontuacao++;
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

    public Carta jogarCarta(Carta escolherCarta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'jogarCarta'");
    }
}
