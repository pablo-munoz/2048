import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Game {
    private Grid grid;
    private JFrame frame;
    private Board board;
    private JLayeredPane rootPane = new JLayeredPane();
    private JPanel endGamePanel;
    private JButton continueButton;
    private JButton newGameButton;
    private JLabel finalScore;
    private JLabel title;
    private JLabel ongoingScore;
    private JPanel statusPanel;
    private KeyAdapter boardControls;
    private boolean endlessMode = false;
    
    public Game() {
        this.grid = new Grid();
        
        this.frame = new JFrame("2048");
        this.frame.setPreferredSize(new Dimension(500, 620));
        
        this.rootPane.setBounds(0, 0, 500, 600);
        this.frame.add(this.rootPane);
        
        this.title = new JLabel("2048");
        this.title.setFont(new Font("Arial", Font.BOLD, 36));
        this.ongoingScore = new JLabel("Score: " + this.grid.getScore());
        this.statusPanel = new JPanel();
        this.statusPanel.add(this.title);
        this.statusPanel.add(this.ongoingScore);
        this.statusPanel.setBounds(0, 0, 500, 100);
        this.statusPanel.setBackground(new Color(187, 173, 160));
        
        this.rootPane.add(this.statusPanel, new Integer(2), 0);
        
        this.endGamePanel = new JPanel();
        this.finalScore = new JLabel("");
        Game.this.finalScore.setForeground(Color.WHITE);
        Game.this.finalScore.setFont(new Font("Arial", Font.BOLD, 18));
        
        this.continueButton = new JButton("Continue");
        this.continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                Game.this.endGamePanel.setVisible(false);
                Game.this.restoreBoardControls();
                Game.this.endlessMode = true;
            }
        });
        
        this.newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                Game.this.grid = new Grid();
                Game.this.board.setGrid(Game.this.grid);
                Game.this.endGamePanel.setVisible(false);
                Game.this.board.repaint();
                Game.this.ongoingScore.setText("Score: " + Game.this.grid.getScore());
                Game.this.restoreBoardControls();

            }
        });

        this.endGamePanel.setBackground(new Color(0, 0, 0, 55));
        this.endGamePanel.add(this.continueButton);
        this.endGamePanel.add(newGameButton);
        this.endGamePanel.add(this.finalScore);
        this.endGamePanel.setBounds(50, 250, 400, 40);
        this.endGamePanel.setVisible(false);
        this.rootPane.add(this.endGamePanel, new Integer(1), 0);
        
        this.board = new Board(500, this.grid);
        this.board.setBounds(0, 100, 500, 500);
        this.board.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    Game.this.grid.up();
                    break;
                case KeyEvent.VK_DOWN:
                    Game.this.grid.down();
                    break;
                case KeyEvent.VK_LEFT:
                    Game.this.grid.left();
                    break;
                case KeyEvent.VK_RIGHT:
                    Game.this.grid.right();
                    break;
                }
                Game.this.ongoingScore.setText("Score: " + Game.this.grid.getScore());
                Game.this.board.repaint();
                
                boolean won = Game.this.grid.hasWon();
                boolean lost = Game.this.grid.hasLost();

                if ((!Game.this.endlessMode && won) || lost) {
                    Game.this.deactivateBoardControls();
                    Game.this.finalScore.setText("Final score: " + Game.this.grid.getScore());
                    Game.this.endGamePanel.setVisible(true);
                    
                    if (lost) {
                        Game.this.continueButton.setEnabled(false);
                    }
                }
            }
        });
        this.board.setOpaque(true);
        this.rootPane.add(this.board, new Integer(0), 0);
        
        
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }
    
    public void deactivateBoardControls() {
        this.boardControls = (KeyAdapter) Game.this.board.getKeyListeners()[0];
        this.board.removeKeyListener(Game.this.boardControls);
    }
    
    public void restoreBoardControls() {
        this.continueButton.setEnabled(true);
        this.board.addKeyListener(Game.this.boardControls);
        this.boardControls = null;
    }
    

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.grid);
    }
}
