import java.util.Random;

public class Cell {
    private int valor;
    private static final Random random = new Random();

    /*
     * A default Celda is instatiated with a valor of 2 or 4.
     */
    public Cell() {
        this.valor = (random.nextInt(2) + 1) * 2;
    }

    public Cell(int valor) {
        this.valor = valor;
    }

    /*
     * Doubles the Celda valor.
     */
    public void incrementa() {
        if (this.valor == 0) {
            this.valor = 2;
        } else {         
            this.valor *= 2;
        }
    }

    public int getValor() {
        return this.valor;
    }
    
    public boolean isEmpty() {
        return this.valor == 0;
    }

    public String toString() {
        return new Integer(this.valor).toString();
    }
}