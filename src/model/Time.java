import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por representar um time no jogo de Truco.
 */
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
}
