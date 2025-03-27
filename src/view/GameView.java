import java.util.List;

public class GameView {

    public void exibirMensagem(String msg) {
        System.out.println(msg);
    }

    
    public void mostrarCarta(Carta carta) {
        System.out.println(" - " + carta); // usa o toString() da Carta
    }


    public Carta escolherCarta(List<Carta> mao) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'escolherCarta'");
    }
}
