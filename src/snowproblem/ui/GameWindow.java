package snowproblem.ui;

import snowproblem.levels.*;
import snowproblem.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Creates the main game window and connects the board, buttons, labels, and levels.
public class GameWindow extends JFrame {

    // Stores the current board, level, board panel, and high score manager.
    private GameBoard board;
    private Level currentLevel;
    private BoardPanel boardPanel;
    private HighScoreManager highScores = new HighScoreManager();

    // Stores the labels and level selector shown at the top and bottom of the window.
    private JLabel moveLabel;
    private JLabel bestScoreLabel;
    private JLabel statusLabel;
    private JLabel levelLabel;
    private JComboBox<String> levelSelector;

    // Creates the window, builds the user interface, and loads the first level.
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

    // Builds the top bar, board area, and status bar.
    private void buildUI() {
        setLayout(new BorderLayout(0, 0));

        // Creates the top bar with the level selector, buttons, moves, and best score.
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        topBar.setBackground(new Color(30, 70, 120));

        levelLabel = new JLabel("Level:");
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        levelSelector = new JComboBox<String>(buildLevelNames());
        levelSelector.setFont(new Font("SansSerif", Font.PLAIN, 13));
        levelSelector.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int idx = levelSelector.getSelectedIndex();

                if (idx >= 0) {
                    loadLevel(LevelRegistry.getAllLevels().get(idx));
                }
            }
        });

        JButton resetBtn = new JButton("Reset");
        resetBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                resetLevel();
            }
        });

        JButton nextBtn = new JButton("Next Level");
        nextBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        nextBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nextLevel();
            }
        });

        moveLabel = new JLabel("Moves: 0");
        moveLabel.setForeground(Color.WHITE);
        moveLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        bestScoreLabel = new JLabel("Best: ---");
        bestScoreLabel.setForeground(Color.WHITE);
        bestScoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        topBar.add(levelLabel);
        topBar.add(levelSelector);
        topBar.add(resetBtn);
        topBar.add(nextBtn);
        topBar.add(Box.createHorizontalStrut(20));
        topBar.add(moveLabel);
        topBar.add(Box.createHorizontalStrut(20));
        topBar.add(bestScoreLabel);

        add(topBar, BorderLayout.NORTH);

        // Creates the board panel and sends board clicks to handleMoveRequest.
        boardPanel = new BoardPanel(null);
        boardPanel.setMoveCallback(new java.util.function.BiConsumer<int[], int[]>() {
            @Override
            public void accept(int[] from, int[] to) {
                handleMoveRequest(from, to);
            }
        });

        add(boardPanel, BorderLayout.CENTER);

        // Creates the bottom status bar with instructions for the player.
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        statusBar.setBackground(new Color(20, 50, 90));

        statusLabel = new JLabel("Select a snowball, then click where to move it.");
        statusLabel.setForeground(new Color(200, 220, 255));
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel hintLabel = new JLabel(
                "  |  Click a piece to select, click an adjacent cell to stack, or click in a direction to slide.");
        hintLabel.setForeground(new Color(150, 180, 220));
        hintLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));

        statusBar.add(statusLabel);
        statusBar.add(hintLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

        // Builds the names shown inside the level selector.
    private String[] buildLevelNames() {
        List<Level> levels = LevelRegistry.getAllLevels();
        String[] names = new String[levels.size()];

        for (int i = 0; i < levels.size(); i++) {
            names[i] = levels.get(i).toString();
        }

        return names;
    }

    // Loads a level and resets the board, move count, status, and best score.
    private void loadLevel(Level level) {
        currentLevel = level;
        board = new GameBoard(level.buildGrid(), level.getSnowmenRequired());

        boardPanel.setBoard(board);
        boardPanel.setHeadColor(level.getHeadColor());

        updateStatus("Level loaded: " + level + "  —  Build " + level.getSnowmenRequired() + " snowman(s)!");
        updateMoveLabel();
        updateBestScoreLabel();
        boardPanel.repaint();
    }

    // Restarts the current level.
    private void resetLevel() {
        loadLevel(currentLevel);
    }

    // Moves to the next level if there is one.
    private void nextLevel() {
        List<Level> levels = LevelRegistry.getAllLevels();
        int currentIndex = levels.indexOf(currentLevel);

        if (currentIndex < levels.size() - 1) {
            levelSelector.setSelectedIndex(currentIndex + 1);
        } else {
            updateStatus("You are already on the last level.");
        }
    }

    // Receives the two clicked cells and decides whether to stack or slide.
    private void handleMoveRequest(int[] from, int[] to) {
        int fr = from[0];
        int fc = from[1];
        int tr = to[0];
        int tc = to[1];

        int dr = tr - fr;
        int dc = tc - fc;

        Direction dir = null;

        if (dr == 0 && dc > 0) {
            dir = Direction.RIGHT;
        } else if (dr == 0 && dc < 0) {
            dir = Direction.LEFT;
        } else if (dc == 0 && dr > 0) {
            dir = Direction.DOWN;
        } else if (dc == 0 && dr < 0) {
            dir = Direction.UP;
        }

        if (dir == null) {
            updateStatus("Please click in the same row or column as the selected piece.");
            return;
        }

        Piece fromPiece = board.getPiece(fr, fc);

        if (fromPiece == null) {
            updateStatus("No piece selected.");
            return;
        }

        // Adjacent cells try stacking first.
        boolean adjacent = (Math.abs(dr) + Math.abs(dc)) == 1;
        boolean stacked = false;

        if (adjacent) {
            stacked = board.stack(fr, fc, dir);

            if (stacked) {
                if (board.isGameWon()) {
                    highScores.saveScore(currentLevel.getNumber(), board.getMoveCount());
                    updateBestScoreLabel();
                    updateStatus("Stacked! You win!");
                } else {
                    updateStatus("Stacked! Keep going!");
                }
            }
        }

        // If stacking did not happen, try sliding instead.
        if (!stacked) {
            if (fromPiece.getType() == PieceType.SNOWMAN_HEAD) {
                updateStatus("Snowman heads can only stack onto a snowball stack — they can't slide.");
                return;
            }

            boolean moved = board.move(fr, fc, dir);

            if (moved) {
                if (board.isGameOver()) {
                    updateStatus("Game over! A snowball fell off the board. Press Reset to try again.");
                } else if (board.isGameWon()) {
                    highScores.saveScore(currentLevel.getNumber(), board.getMoveCount());
                    updateBestScoreLabel();
                    updateStatus("Congratulations! You built all the snowmen in " + board.getMoveCount() + " moves!");
                } else {
                    updateStatus("Moved " + fromPiece.getType().name().toLowerCase().replace('_', ' ')
                            + " " + dir.name().toLowerCase() + ".");
                }
            } else {
                updateStatus("Can't move that piece in that direction.");
            }
        }

        updateMoveLabel();
        boardPanel.repaint();
    }

    // Changes the message shown at the bottom of the window.
    private void updateStatus(String msg) {
        statusLabel.setText(msg);
    }

    // Updates the move counter at the top of the window.
    private void updateMoveLabel() {
        moveLabel.setText("Moves: " + board.getMoveCount());
    }

    // Updates the best score shown at the top of the window.
    private void updateBestScoreLabel() {
        int best = highScores.getBestScore(currentLevel.getNumber());

        if (best == -1) {
            bestScoreLabel.setText("Best: ---");
        } else {
            bestScoreLabel.setText("Best: " + best);
        }
    }
}