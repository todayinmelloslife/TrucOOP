import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {
    private List<Carta> cartas;
    private Carta vira;
    private List<String> manilhas;

    public Baralho() {
        cartas = new ArrayList<>();

        String[] valores = {"4", "5", "6", "7", "Q", "J", "K", "A", "2", "3"};
        String[] naipes = {"Ouros", "Copas", "Espadas", "Paus"};

        for (String valor : valores) {
            for (String naipe : naipes) {
                cartas.add(new Carta(valor, naipe));
            }
        }
    }

    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    public void definirVira() {
        vira = cartas.remove(0); // A primeira carta do baralho é a "vira"
        definirManilhas();
    }

    private void definirManilhas() {
        String[] valores = {"4", "5", "6", "7", "Q", "J", "K", "A", "2", "3"};
        int indiceVira = List.of(valores).indexOf(vira.getValor());
        manilhas = new ArrayList<>();

        for (int i = 1; i <= 4; i++) { // As próximas 4 cartas na sequência são as manilhas
            int indiceManilha = (indiceVira + i) % valores.length;
            manilhas.add(valores[indiceManilha]);
        }
    }

    public List<String> getManilhas() {
        return manilhas;
    }

    public Carta getVira() {
        return vira;
    }

    public Carta distribuirCarta() {
        if (cartas.isEmpty()) {
            throw new IllegalStateException("O baralho está vazio!");
        }
        return cartas.remove(0);
    }

    public int tamanho() {
        return cartas.size();
    }
}