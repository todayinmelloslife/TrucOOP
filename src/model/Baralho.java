import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho { 
    private List<Carta> cartas;

    public Baralho() {
        cartas = new ArrayList<>();

        String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
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

    public Carta distribuirCarta() {
        if (!cartas.isEmpty()) {
            return cartas.remove(0);
        } else {
            return null;
        }
    }

    public int tamanho() {
        return cartas.size();
    }

    public void mostrarBaralho() {
        for (Carta carta : cartas) {
            System.out.println(carta);
        }
    }
}