package snowproblem.ui;

import snowproblem.model.GameBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window for Snow Problem — Part 1.
 * Displays the game board with Level 1 pieces. No interaction yet.
 */
public class GameWindow extends JFrame {

    public GameWindow() {
        super("Snow Problem — Level 1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Title bar
        JLabel title = new JLabel("Snow Problem  |  Level 1: First Flurry", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(30, 70, 120));
        title.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        // Board
        GameBoard board = new GameBoard();
        BoardPanel boardPanel = new BoardPanel(board);

        // Status bar
        JLabel status = new JLabel(
            "  Goal: stack the small snowball onto the large one, then place the head on top.",
            SwingConstants.LEFT);
        status.setFont(new Font("SansSerif", Font.ITALIC, 12));
        status.setForeground(new Color(200, 220, 255));
        status.setOpaque(true);
        status.setBackground(new Color(20, 50, 90));
        status.setBorder(BorderFactory.createEmptyBorder(7, 8, 7, 8));

        setLayout(new BorderLayout());
        add(title,      BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(status,     BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
