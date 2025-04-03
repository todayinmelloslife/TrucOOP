import java.util.ArrayList;
import java.util.List;


public class Time {
    private String nome;
    private List<Jogador> jogadores;

    public Time(String nome) {
        this.nome = nome;
        this.jogadores = new ArrayList<>();
    }

    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public String getNome() {
        return nome;
    }

    public int getPontuacaoTotal() {
        return jogadores.stream().mapToInt(Jogador::getPontuacao).sum();
    }
}
