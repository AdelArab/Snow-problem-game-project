package snowproblem.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import snowproblem.levels.Level;
import snowproblem.levels.LevelRegistry;
import snowproblem.model.GameBoard;

/** Main application window for the Task 1 version of the game. */
public class GameWindow extends JFrame {
    private GameBoard board;
    private BoardPanel boardPanel;
    private JLabel statusLabel;

    public GameWindow() {
        super("Snow Problem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Level level = LevelRegistry.getAllLevels().get(0);
        board = level.buildBoard();

        buildUI(level);
        pack();
        setLocationRelativeTo(null);
    }

    private void buildUI(Level level) {
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(30, 75, 125));

        JLabel levelLabel = new JLabel("Level:");
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JComboBox<String> levelSelector = new JComboBox<>(new String[] {level.toString()});

        topBar.add(levelLabel);
        topBar.add(levelSelector);
        topBar.add(Box.createHorizontalStrut(20));

        boardPanel = new BoardPanel(board);

        statusLabel = new JLabel("Level loaded: " + level + " — Build " + level.getSnowmenRequired() + " snowman!");
        statusLabel.setOpaque(true);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBackground(new Color(15, 55, 95));

        add(topBar, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }
}
