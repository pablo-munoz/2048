import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Juego extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(119, 110, 101);
    private static final int GRID_WIDTH = 500;
    private static final int GRID_HEIGHT = 500;
    private static final int GRID_SIZE = 4;
    private static final int PADDING = 15;
    private static final int CELL_WIDTH = (GRID_WIDTH - (PADDING * (GRID_SIZE + 1))) / GRID_SIZE;
    private static final int CELL_HEIGHT = (GRID_HEIGHT - (PADDING * (GRID_SIZE + 1))) / GRID_SIZE;
    
    private Celda[][] cuadros;
    
    public Juego() {
        this.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
        this.setBackground(BACKGROUND_COLOR);
        this.cuadros = new Celda[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                cuadros[i][j] = new Celda();
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Color previousColor = g.getColor();
        Font previousFont = g.getFont();

        g.setFont(new Font("Helvetica", Font.PLAIN, CELL_HEIGHT)); 
        FontMetrics fm = g.getFontMetrics();
        int strWidth = fm.stringWidth("2");
        int strHeight = fm.getAscent();
        
        for (int numRow = 0; numRow < this.cuadros.length; numRow ++) {
            for (int numCol = 0; numCol < this.cuadros.length; numCol++) {
                int cellX = PADDING + (PADDING + CELL_WIDTH) * numRow;
                int cellY = PADDING + (PADDING + CELL_HEIGHT) * numCol;
                g.setColor(new Color(205, 193, 180));
                g.fillRect(cellX, cellY, CELL_WIDTH, CELL_HEIGHT);
                
                g.setColor(BACKGROUND_COLOR);
                int numX = (cellX + (CELL_WIDTH / 2)) - (strWidth / 2);
                int numY = cellY + CELL_HEIGHT - ((CELL_HEIGHT - strHeight) / 2);
                g.drawString("2", numX, numY);
            }
        }
        
        g.setColor(previousColor);
        g.setFont(previousFont);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        
        Juego juego = new Juego();
          
        frame.add(juego);
        frame.pack();

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
