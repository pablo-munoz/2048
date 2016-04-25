import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Tablero {
	private static final Random random = new Random();
	private ArrayList<ArrayList<Cell>> cells;
	private int score;
	private boolean won;
	private boolean lost;

	public Tablero() {
		this(Tablero.genRandomValues());

	}

	public Tablero(int[] initialNums) {
	    this.won = false;
	    this.lost = false;
	    
	    this.cells = new ArrayList<ArrayList<Cell>>();
	    
	    for(int i = 0; i < Constants.NUM_ROWS; i++) {
	        cells.add(new ArrayList<Cell>());
	        for(int j = 0; j < Constants.NUM_COLS; j++) {
	            cells.get(i).add(new Cell(initialNums[i * Constants.NUM_ROWS + j]));
	        }
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

	public ArrayList<ArrayList<Cell>> getCuadros() {
		return this.cells;
	}

	public void pedirMovimiento(){
		Scanner sc = new Scanner(System.in);
		String movimiento="";
		System.out.println("2048");

		try{
			do{
				movimiento=JOptionPane.showInputDialog("Movimiento? 1:izquierda 2:derecha 3:arriba 4:abajo 0:salir");
				if(movimiento.equals("1")){ //derecha
					this.izquierda();
				}
				else if(movimiento.equals("2")){ //izquierda
					this.derecha();
				}
				else if(movimiento.equals("3")){
					this.arriba();
				}
				else if(movimiento.equals("4")){
					this.abajo();
				}
				else if(movimiento.equals("0")){
					System.out.println("\nJuego Terminado");
				}
				else{
					System.out.println("\nElige otra opción");
				}
			}while(!movimiento.equals("0"));
		}
		catch(NullPointerException e){
			System.out.println("\nJuego Terminado");
		}
	}

	public void imprimeTablero(){
		for(int i = 0; i < Constants.NUM_ROWS; i++){
			for(int j=0; j< Constants.NUM_COLS; j++){
				System.out.print(this.getCellAt(i, j) + " ");
			}
			System.out.println();
		}
	}

	public int getScore(){
		return this.score;
	}

	public int getEmptyCellsNumber() {
	    int count = 0;

	    for(int i = 0; i < Constants.NUM_ROWS; i++){
	        for(int j=0; j< Constants.NUM_COLS; j++){
	            if (this.getCellAt(i, j).isEmpty()) {
	                ++count;
	            }
	        }
	    }
	    return count;
	}

	public boolean hasLost() {
	    return this.getEmptyCellsNumber() == 0;
	}
	
	/*
	 * This method mutates the cells, putting them in the "next state" of the game.
	 * When this is called the cells are collapsed to a side (up, down, left o right)
	 * an adjacent cells of the same value are combined into a single cell with double that value.
	 */
	private void resolveNextState() {
	    ArrayList<ArrayList<Cell>> nextCells = (ArrayList<ArrayList<Cell>>) this.cells.clone();
	    
	    for (int i = 0; i < Constants.NUM_ROWS; i++) {
	        ArrayList<Cell> row = nextCells.get(i);

	        int removed = 0;
	        
	        int idx = 0;
	        while(idx < row.size()) {
	            if (row.get(idx).isEmpty()) {
	                row.remove(idx);
	                ++removed;
	            } else{
	                ++idx;
	            }
	        }
	        
	        idx = 0;
	        while(idx < row.size() - 1) {
	            if (row.get(idx).getValor() == row.get(idx + 1).getValor()) {
	                row.get(idx + 1).incrementa();
	                row.remove(idx);
	                ++removed;
	            } else {
	                ++idx;
	            }
	        }

	        // Add as many empty cells as were removed in the process at the beginning of the row
	        for (int k = 0; k < removed; k++) {
	            row.add(0, new Cell(0));
	        }
	        
	        this.cells = nextCells;
	    }
	    
	    this.generateNewValuedCell();
	}

	
	/*
	 * This method generates a number in the tablero. It is called whenever
	 * the user makes a movement and there is at least one empty Celda.
	*/
	private void generateNewValuedCell(){
	    ArrayList<Integer[]> emptyCellIndexes = new ArrayList<Integer[]>();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
			    if (this.getCellAt(i, j).isEmpty()) {
			        Integer[] index = new Integer[2];
			        index[0] = i;
			        index[1] = j;
			        emptyCellIndexes.add(index);
			    }
			}
		}
		
		Integer[] rowToGenerateIdx = emptyCellIndexes.get(Tablero.random.nextInt(emptyCellIndexes.size()));
		int row = rowToGenerateIdx[0];
		int col = rowToGenerateIdx[1];
		this.cells.get(row).set(col, new Cell((Tablero.random.nextInt(2) + 1) * 2));
		
		if (this.getEmptyCellsNumber() == 0) {
		}
	}
	
	private void rotateLeft() {
	    ArrayList<ArrayList<Cell>> translatedCuadros = new ArrayList<ArrayList<Cell>>();
	    ArrayList<ArrayList<Cell>> rotatedCuadros = new ArrayList<ArrayList<Cell>>(); 
	    
	    for(int i = 0; i < Constants.NUM_ROWS; i++) {
	        translatedCuadros.add(new ArrayList<Cell>());
	        for(int j = 0; j < Constants.NUM_COLS; j++) {
	            translatedCuadros.get(i).add(this.getCellAt(j, i));
	        }
	    }
	    
	    for(int i = 0; i < Constants.NUM_ROWS; i++) {
	        rotatedCuadros.add(new ArrayList<Cell>());
	        for(int j = 0; j < Constants.NUM_COLS; j++) {
	            rotatedCuadros.get(i).add(translatedCuadros.get(i).get(Constants.NUM_COLS - j - 1));
	        }
	    }

	    this.cells = rotatedCuadros;
	}

	private void rotateRight() {
	    this.rotateLeft();
	    this.rotateLeft();
	    this.rotateLeft();
	}


	public void arriba() {
	    this.rotateLeft();
		this.resolveNextState();
		this.rotateRight();
	}
	
	public void abajo() {
	   this.rotateRight();
	   this.resolveNextState(); 
	   this.rotateLeft();
	}

	public void izquierda() {
	    this.rotateLeft();
	    this.rotateLeft();
	    this.resolveNextState();
	    this.rotateRight();
	    this.rotateRight();
	}

	public void derecha() {
		this.resolveNextState();
	}
	
	public Cell getCellAt(int row, int col) {
	    return this.cells.get(row).get(col);
	}
}
