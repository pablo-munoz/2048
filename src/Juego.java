import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Juego extends JPanel {
    private static final int GRID_WIDTH = 500;
    private static final int GRID_HEIGHT = 500;
    private static final int PADDING = 15;
    private static final int CELL_WIDTH = (GRID_WIDTH - (PADDING * (Constants.NUM_ROWS + 1))) / Constants.NUM_ROWS;
    private static final int CELL_HEIGHT = (GRID_HEIGHT - (PADDING * (Constants.NUM_COLS + 1))) / Constants.NUM_COLS;
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
                int tecla = e.getKeyCode();
                switch (tecla) {
                case KeyEvent.VK_UP:
                    Juego.this.tablero.arriba();
                    break;
                case KeyEvent.VK_DOWN:
                    Juego.this.tablero.abajo();
                    break;
                case KeyEvent.VK_LEFT:
                    Juego.this.tablero.izquierda();
                    break;
                case KeyEvent.VK_RIGHT:
                    Juego.this.tablero.derecha();
                    break;
                }
                Juego.this.repaint();

            }
        });

        this.setFocusable(true);
        this.requestFocus();
    }

    public Color getNumberColor(Cell cuadro) {
        switch (cuadro.getValor()) {
        case 2:
            return new Color(0xeee4da);
        case 4:
            return new Color(0xede0c8);
        case 8:
            return new Color(0xf2b179);
        case 16:
            return new Color(0xf59563);
        case 32:
            return new Color(0xf67c5f);
        case 64:
            return new Color(0xf65e3b);
        case 128:
            return new Color(0xedcf72);
        case 256:
            return new Color(0xedcc61);
        case 512:
            return new Color(0xedc850);
        case 1024:
            return new Color(0xedc53f);
        case 2048:
            return new Color(0xedc22e);
        }
        return new Color(0xcdc1b4);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color previousColor = g.getColor();
        Font previousFont = g.getFont();

        g.setFont(new Font("Helvetica", Font.BOLD, FONT_SIZE));
        FontMetrics fm = g.getFontMetrics();

        for (int numRow = 0; numRow < Constants.NUM_ROWS; numRow++) {
            for (int numCol = 0; numCol < Constants.NUM_COLS; numCol++) {
                Cell cell = this.tablero.getCellAt(numRow, numCol);
                int cellY = (PADDING + 40) + (PADDING + CELL_WIDTH - 25) * numRow;
                int cellX = (PADDING + 40) + (PADDING + CELL_HEIGHT - 25) * numCol;
                g.setColor(getNumberColor(cell));
                g.fillRect(cellX, cellY, CELL_WIDTH - 25, CELL_HEIGHT - 25);
                if (cell.getValor() > 0) {
                    g.setColor(FONT_COLOR);
                    int strWidth = fm.stringWidth(cell.toString());
                    int strHeight = fm.getAscent();
                    int numX = (cellX + ((CELL_WIDTH - 25) / 2)) - (strWidth / 2);
                    int numY = cellY + (CELL_HEIGHT - 25) - (((CELL_HEIGHT - 20) - strHeight) / 2);
                    g.drawString(cell.toString(), numX, numY);
                }
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.BOLD, FONT_SIZE / 4));
        g.drawString("Score: " + this.tablero.getScore(), this.getWidth() - 100, 20);

        g.setColor(previousColor);
        g.setFont(previousFont);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("2048");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Juego juego = new Juego();

        frame.add(juego);
        frame.pack();

        frame.setVisible(true);

    }
}