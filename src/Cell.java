public class Cell {
   int value;
   
   private static int getRandomInitialValue() {
       return Math.random() < 0.8 ? 2 : 4;
   }
   
   public static Cell create2or4Cell() {
       return new Cell(Cell.getRandomInitialValue());
   }
   
   public static Cell createEmptyCell() {
       return new Cell(0);
   }
   
   public Cell(int value) {
       this.value = value;
   }
   
   public int getValue() {
       return this.value;
   }
   
   public void doubleValue() {
       this.value *= 2;
   }
   
   public boolean isEmpty() {
       return this.value == 0;
   }
   
   public boolean equals(Cell other) {
       return this.value == other.value;
   }
   
   public Cell clone() {
       return new Cell(this.value);
   }
   
   public String toString() {
       return "" + this.value;
   }
}
