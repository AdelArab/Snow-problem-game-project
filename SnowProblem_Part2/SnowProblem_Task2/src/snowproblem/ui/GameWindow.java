package snowproblem.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import snowproblem.levels.Level;
import snowproblem.levels.LevelRegistry;
import snowproblem.model.Direction;
import snowproblem.model.GameBoard;

/** Main game window for Task 2 movement. */
public class GameWindow extends JFrame {

    private GameBoard board;
    private Level currentLevel;
    private BoardPanel boardPanel;
    private JLabel moveLabel;
    private JLabel statusLabel;
    private JComboBox<String> levelSelector;

    public GameWindow() {
        super("Snow Problem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        buildUI();
        loadLevel(LevelRegistry.getAllLevels().get(0));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildUI() {
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(30, 75, 120));

        JLabel levelLabel = new JLabel("Level:");
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        levelSelector = new JComboBox<>(new String[] {"Level 1: Starter 1"});
        levelSelector.addActionListener(e -> loadLevel(LevelRegistry.getAllLevels().get(0)));

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        resetButton.addActionListener(e -> loadLevel(currentLevel));

        moveLabel = new JLabel("Moves: 0");
        moveLabel.setForeground(Color.WHITE);
        moveLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        topBar.add(levelLabel);
        topBar.add(levelSelector);
        topBar.add(resetButton);
        topBar.add(Box.createHorizontalStrut(20));
        topBar.add(moveLabel);

        boardPanel = new BoardPanel(new GameBoard(LevelRegistry.getAllLevels().get(0).buildGrid()));
        boardPanel.setMoveCallback(this::handleMoveRequest);

        statusLabel = new JLabel("Click a large or small snowball, then click an adjacent direction cell.");
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(15, 55, 95));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        add(topBar, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void loadLevel(Level level) {
        currentLevel = level;
        board = new GameBoard(level.buildGrid());
        boardPanel.setBoard(board);
        updateMoveLabel();
        updateStatus("Level loaded: " + level + " — move large and small snowballs.");
    }

    private void handleMoveRequest(int[] from, int[] to) {
        int dr = to[0] - from[0];
        int dc = to[1] - from[1];

        if (Math.abs(dr) + Math.abs(dc) != 1) {
            updateStatus("Click an adjacent cell to choose the movement direction.");
            return;
        }

        Direction direction = getDirection(dr, dc);
        boolean moved = board.move(from[0], from[1], direction);

        if (moved) {
            if (board.isGameOver()) {
                updateStatus("Game over! A snowball fell off the board. Press Reset to try again.");
            } else {
                updateStatus("Moved snowball " + direction.name().toLowerCase() + ".");
            }
        } else {
            updateStatus("Cannot move that piece in that direction.");
        }

        updateMoveLabel();
        boardPanel.repaint();
    }

    private Direction getDirection(int dr, int dc) {
        if (dr == -1) return Direction.UP;
        if (dr == 1) return Direction.DOWN;
        if (dc == -1) return Direction.LEFT;
        return Direction.RIGHT;
    }

    private void updateMoveLabel() {
        moveLabel.setText("Moves: " + board.getMoveCount());
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
}
