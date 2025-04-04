public class Carta {
    private String valor;
    private String naipe;

    public Carta(String valor, String naipe) {
        this.valor = valor;
        this.naipe = naipe;
    }

    public String getValor() {
        return valor;
    }

    public String getNaipe() {
        return naipe;
    }

    @Override
    public String toString() {
        return valor + " de " + naipe;
    }

    public static String get(int indice) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    public static int size() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'size'");
    }
}