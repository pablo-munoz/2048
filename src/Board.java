import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Board extends JPanel {
    private int padding;
    private int cellLength;
    private Grid grid;
    private static final Color FONT_COLOR = new Color(119, 110, 101);
    
    public Board(int length, Grid grid) {
        this.grid = grid;
        this.padding = length / 20;
        this.cellLength = (length - (5 * this.padding)) / 4;
        
        this.setPreferredSize(new Dimension(length, length));
        this.setBackground(new Color(187, 173, 160));
        this.setFocusable(true);
    }
    
    public void setGrid(Grid grid) {
        this.grid = grid;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                this.drawCell(g, i, j, this.grid.getCell(i, j));
            }
        }
    }
    
    public Color getNumberColor(Cell cell) {
        switch (cell.getValue()) {
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
    
    public void drawCell(Graphics g, int rowN, int colN, Cell cell) {
        Color previousColor = g.getColor();
        Font previousFont = g.getFont();

        g.setFont(new Font("Helvetica", Font.BOLD, this.cellLength / 2));
        FontMetrics fm = g.getFontMetrics();
        
        int y = this.padding + (this.padding + this.cellLength) * rowN;
        int x = this.padding + (this.padding + this.cellLength) * colN;
        
        g.setColor(this.getNumberColor(cell));
        g.fillRoundRect(x, y, this.cellLength, this.cellLength, 20, 20);
        
        if (cell.getValue() > 0) {
            g.setColor(FONT_COLOR);
            int strWidth = fm.stringWidth(cell.toString());
            int strHeight = fm.getAscent();
            int numX = (x + ((this.cellLength) / 2)) - (strWidth / 2);
            int numY = y + (this.cellLength) - ((this.cellLength - strHeight) / 2);
            g.drawString(cell.toString(), numX, numY);
        }
        
        g.setColor(previousColor);
        g.setFont(previousFont);
    }
}
