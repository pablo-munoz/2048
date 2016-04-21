import java.util.Random;

public class Tablero {
    private static final Random random = new Random();
    private Celda[][] cuadros;
    private int score;
    private boolean gano;
    private boolean perdio;

    public Tablero() {
        this(Tablero.genRandomValues());

    }
    
    public Tablero(int[] initialNums) {
        this.cuadros = new Celda[4][4];
        
        for (int i = 0; i < 16; i++) {
            this.cuadros[i / 4][i % 4] = new Celda(initialNums[i]);
        }
    }
    
    /*
     * Returns a 16 element int array whose values can be used to instatiate
     * a Tablero where two cells have numbers and the rest are "empty".
     */
    public static int[] genRandomValues() {
        int initial1Index, initial2Index;
        initial1Index = initial2Index = random.nextInt(15) + 1;
        while(initial1Index == initial2Index) {
            initial2Index = random.nextInt(16);
        }
        
        // Makes use of the fact that int array values default to 0
        int[] initialValues = new int[16];
        initialValues[initial1Index] = (random.nextInt(2) + 1) * 2;
        initialValues[initial2Index] = (random.nextInt(2) + 1) * 2;
        return initialValues;
    }
    
    public Celda[][] getCuadros() {
        return this.cuadros;
    }
    
    public void arriba() {
        this.cuadros[0][0].incrementa();
    }
    
    public void derecha() {
        
    }
    
    public void abajo() {
        
    }
    
    public void izquierda() {
        
    }
}
