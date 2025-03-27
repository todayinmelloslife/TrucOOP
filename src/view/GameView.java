package src.model.carta;

public class GameView {

    public void exibirMensagem(String msg) {
        System.out.println(msg);
    }

    
    public void mostrarCarta(Carta carta) {
        System.out.println(" - " + carta); // usa o toString() da Carta
    }
}
