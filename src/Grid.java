import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Grid {
    private static final int SIZE = 4;
    private static final Point[][] RIGHT_ROUTE = {
            { new Point(0, 3), new Point(0, 2), new Point(0, 1), new Point(0, 0) },
            { new Point(1, 3), new Point(1, 2), new Point(1, 1), new Point(1, 0) },   
            { new Point(2, 3), new Point(2, 2), new Point(2, 1), new Point(2, 0) },   
            { new Point(3, 3), new Point(3, 2), new Point(3, 1), new Point(3, 0) }
    };
    private static final Point[][] LEFT_ROUTE = {
            { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3) },
            { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
            { new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(2, 3) },
            { new Point(3, 0), new Point(3, 1), new Point(3, 2), new Point(3, 3) }
    };
    private static final Point[][] TOP_ROUTE = {
            { new Point(0, 3), new Point(1, 3), new Point(2, 3), new Point(3, 3) },
            { new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2) },
            { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
            { new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0) }
    };
    private static final Point[][] BOTTOM_ROUTE = {
            { new Point(3, 3), new Point(2, 3), new Point(1, 3), new Point(0, 3) },
            { new Point(3, 2), new Point(2, 2), new Point(1, 2), new Point(0, 2) },
            { new Point(3, 1), new Point(2, 1), new Point(1, 1), new Point(0, 1) },
            { new Point(3, 0), new Point(2, 0), new Point(1, 0), new Point(0, 0) }

    };
    private Hashtable<Point, Cell> cells;

    private static Point[] get2RandomPositions() {
        Random rand = new Random();
        Point[] pos = new Point[2];
        pos[0] = new Point(rand.nextInt(SIZE), rand.nextInt(SIZE));
        pos[1] = new Point(rand.nextInt(SIZE), rand.nextInt(SIZE));
        
        while(pos[1] == pos[0]) {
            pos[1] = new Point(rand.nextInt(SIZE), rand.nextInt(SIZE)); 
        }
        
        return pos;
    }
    
    
    public Grid() {
        cells = new Hashtable<Point, Cell>();
        
        Point[] startingCellsPositions = get2RandomPositions();
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Point pos = new Point(i, j);
                if (pos.equals(startingCellsPositions[0]) || pos.equals(startingCellsPositions[1])) {
                    cells.put(pos, Cell.create2or4Cell());
                } else {
                    cells.put(pos, new Cell(0));
                }
            }
        }
    }
    
    public Cell getCell(int row, int col) {
        return this.cells.get(new Point(row, col)).clone();
    }
    
    public int getNumOfEmptyCells() {
        int num = 0;
        for (Cell cell : this.cells.values()) {
            if (cell.isEmpty()) {
                ++num;
            }
        }
        return num;
    }
    
    public void fillRandomEmptyCell() {
        int numEmpty = this.getNumOfEmptyCells();
        
        if (numEmpty == 0) {
            return;
        }

        int nthToFill = (new Random()).nextInt(numEmpty); 
        
        for(Point p : this.cells.keySet()) {
            if (!this.cells.get(p).isEmpty()) continue;
            if (nthToFill != 0) {
                --nthToFill;
            } else {
                this.cells.put(p, Cell.create2or4Cell());
                break;
            }
            
        }
    }
    
    public void move(Point[][] routes) {
        ArrayList<Point> mergeBlacklist = new ArrayList<Point>();
        
        for (int i = 0; i < SIZE; i++) {
           for(int j = 1; j < SIZE; j++) {
               for(int k = 0; j - k - 1 >= 0; k++) {
                   Point currentIdx = routes[i][j-k];
                   Point targetIdx = routes[i][j-k-1];
                   Cell current = this.cells.get(currentIdx);
                   
                   if(current.isEmpty()) {
                       continue;
                   }
                   
                   Cell target = this.cells.get(targetIdx);
                   if (target.isEmpty()) {
                       this.cells.put(targetIdx, current);
                       this.cells.put(currentIdx, Cell.createEmptyCell());
                   }
                   else if (!mergeBlacklist.contains(targetIdx) && !mergeBlacklist.contains(currentIdx) && current.equals(target)) {
                       target.doubleValue();
                       mergeBlacklist.add(targetIdx);
                       this.cells.put(currentIdx, Cell.createEmptyCell());
                   }
               }
           }
        }
        
        this.fillRandomEmptyCell();
    }
    
    public int getScore() {
        int score = 0;
        for (Cell cell : this.cells.values()) {
            if (cell.getValue() > score) {
                score = cell.getValue();
            }
        }
        return score;
    }
    
    public boolean hasLost() {
        int numEmpty = this.getNumOfEmptyCells();
        
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                Point pos = new Point(i, j);
                Cell cell = this.cells.get(pos);
                Point[] neighbours = {
                   new Point(i-1, j), new Point(i + 1, j), new Point(i, j-1), new Point(i, j+1)     
                };
                for(int k = 0; k < 4; k++) {
                   Point neighbourPos =  neighbours[k];
                   if (neighbourPos.x >= 0 && neighbourPos.y >= 0 && neighbourPos.x < 4 && neighbourPos.y < 4) {
                       if (cell.equals(this.cells.get(neighbourPos))) {
                           return false;
                       }
                   }
                }
            }
        }
        
        return numEmpty == 0;
    }
    
    public boolean hasWon() {
        for (Cell cell : this.cells.values()) {
            if (cell.getValue() == 2048) {
                return true;
            }
        }
        return false;
    }
    
    public void up() {
       this.move(TOP_ROUTE); 
    }
    
    public void down() {
       this.move(BOTTOM_ROUTE);
    }
    
    public void left() {
        this.move(LEFT_ROUTE);
    }
    
    public void right() {
        this.move(RIGHT_ROUTE);
    }
    
    public String toString() {
        String repr = "";
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                repr += String.format("%5s", this.cells.get(new Point(i, j)).getValue());
            }
            repr += "\n";
        }
        
        return repr;
    }
}
