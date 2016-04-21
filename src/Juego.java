import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Juego extends JPanel {
    private static final int GRID_WIDTH = 500;
    private static final int GRID_HEIGHT = 500;
    private static final int GRID_SIZE = 4;
    private static final int PADDING = 15;
    private static final int CELL_WIDTH = (GRID_WIDTH - (PADDING * (GRID_SIZE + 1))) / GRID_SIZE;
    private static final int CELL_HEIGHT = (GRID_HEIGHT - (PADDING * (GRID_SIZE + 1))) / GRID_SIZE;
    // We want the font size to be roughly a half of the cell height
    private static final int FONT_SIZE = CELL_HEIGHT / 2;
    private static final Color BACKGROUND_COLOR = new Color(187, 173, 160);
    private static final Color FONT_COLOR = new Color(119, 110, 101);
    
    private Tablero tablero;
    
    public Juego() {
        this.tablero = new Tablero();
        
        this.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
        this.setBackground(BACKGROUND_COLOR);
        
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                Juego.this.tablero.arriba();
                Juego.this.repaint();
            }
        });
        
        this.setFocusable(true);
        this.requestFocus();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Color previousColor = g.getColor();
        Font previousFont = g.getFont();

        g.setFont(new Font("Helvetica", Font.BOLD, FONT_SIZE)); 
        FontMetrics fm = g.getFontMetrics();

        
        for (int numRow = 0; numRow < 4; numRow ++) {
            for (int numCol = 0; numCol < 4; numCol++) {
                Celda cuadro = this.tablero.getCuadros()[numRow][numCol];
                int cellX = PADDING + (PADDING + CELL_WIDTH) * numRow;
                int cellY = PADDING + (PADDING + CELL_HEIGHT) * numCol;
                g.setColor(new Color(205, 193, 180));
                g.fillRect(cellX, cellY, CELL_WIDTH, CELL_HEIGHT);

                if (cuadro.getValor() > 0) {
                    g.setColor(FONT_COLOR);
                    int strWidth = fm.stringWidth(cuadro.toString());
                    int strHeight = fm.getAscent();
                    int numX = (cellX + (CELL_WIDTH / 2)) - (strWidth / 2);
                    int numY = cellY + CELL_HEIGHT - ((CELL_HEIGHT - strHeight) / 2);
                    g.drawString(cuadro.toString(), numX, numY);
                }
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
