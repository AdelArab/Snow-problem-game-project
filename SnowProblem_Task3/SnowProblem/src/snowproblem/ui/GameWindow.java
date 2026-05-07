package snowproblem.ui;

import snowproblem.levels.*;
import snowproblem.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Main application window for Snow Problem.
 *
 * Contains the board panel, level selector, move counter, status bar,
 * and reset button. Handles translating board-panel click pairs into
 * move or stack operations on the GameBoard.
 */
public class GameWindow extends JFrame {

    private GameBoard board;
    private Level currentLevel;
    private BoardPanel boardPanel;

    private JLabel moveLabel;
    private JLabel statusLabel;
    private JLabel levelLabel;
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

    // ── UI construction ───────────────────────────────────────────────────────

    private void buildUI() {
        setLayout(new BorderLayout(0, 0));

        // Top bar
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        topBar.setBackground(new Color(30, 70, 120));

        levelLabel = new JLabel("Level:");
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        levelSelector = new JComboBox<>(buildLevelNames());
        levelSelector.setFont(new Font("SansSerif", Font.PLAIN, 13));
        levelSelector.addActionListener(e -> {
            int idx = levelSelector.getSelectedIndex();
            if (idx >= 0) {
                loadLevel(LevelRegistry.getAllLevels().get(idx));
            }
        });

        JButton resetBtn = new JButton("Reset");
        resetBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        resetBtn.addActionListener(e -> resetLevel());

        JButton nextBtn = new JButton("Next Level");
        nextBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        nextBtn.addActionListener(e -> nextLevel());

        moveLabel = new JLabel("Moves: 0");
        moveLabel.setForeground(Color.WHITE);
        moveLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        topBar.add(levelLabel);
        topBar.add(levelSelector);
        topBar.add(resetBtn);
        topBar.add(nextBtn);
        topBar.add(Box.createHorizontalStrut(20));
        topBar.add(moveLabel);

        add(topBar, BorderLayout.NORTH);

        // Board
        boardPanel = new BoardPanel(null);
        boardPanel.setMoveCallback(this::handleMoveRequest);
        add(boardPanel, BorderLayout.CENTER);

        // Status bar
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

    private String[] buildLevelNames() {
        List<Level> levels = LevelRegistry.getAllLevels();
        String[] names = new String[levels.size()];
        for (int i = 0; i < levels.size(); i++) {
            names[i] = levels.get(i).toString();
        }
        return names;
    }

    // ── Level management ──────────────────────────────────────────────────────

    private void loadLevel(Level level) {
        currentLevel = level;
        board = new GameBoard(level.buildGrid(), level.getSnowmenRequired());
        boardPanel.setBoard(board);
        boardPanel.setHeadColor(level.getHeadColor());  
        updateStatus("Level loaded: " + level + "  —  Build " + level.getSnowmenRequired() + " snowman(s)!");
        updateMoveLabel();
        boardPanel.repaint();
    }

    private void resetLevel() {
        loadLevel(currentLevel);
    }

    private void nextLevel() {
    List<Level> levels = LevelRegistry.getAllLevels();
    int currentIndex = levels.indexOf(currentLevel);

    if (currentIndex < levels.size() - 1) {
        levelSelector.setSelectedIndex(currentIndex + 1);
    } else {
        updateStatus("You are already on the last level.");
    }
}

    // ── Move handling ─────────────────────────────────────────────────────────

    /**
     * Receives a (from, to) click pair from the BoardPanel and decides whether
     * to attempt a slide move or a stack operation.
     *
     * @param from {fromRow, fromCol}
     * @param to   {toRow, toCol}
     */
    private void handleMoveRequest(int[] from, int[] to) {
        int fr = from[0], fc = from[1];
        int tr = to[0],   tc = to[1];

        int dr = tr - fr;
        int dc = tc - fc;

        // Determine direction
        Direction dir = null;
        if (dr == 0 && dc > 0) dir = Direction.RIGHT;
        else if (dr == 0 && dc < 0) dir = Direction.LEFT;
        else if (dc == 0 && dr > 0) dir = Direction.DOWN;
        else if (dc == 0 && dr < 0) dir = Direction.UP;

        if (dir == null) {
            updateStatus("Please click in the same row or column as the selected piece.");
            return;
        }

        Piece fromPiece = board.getPiece(fr, fc);
        if (fromPiece == null) {
            updateStatus("No piece selected.");
            return;
        }

        // Adjacent cells → try to stack first
        boolean adjacent = (Math.abs(dr) + Math.abs(dc)) == 1;
        boolean stacked = false;

        if (adjacent) {
            stacked = board.stack(fr, fc, dir);
            if (stacked) {
                updateStatus("Stacked! " + (board.isGameWon() ? "You win!" : "Keep going!"));
            }
        }

        // If not stacked, try a slide move
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
                    updateStatus("Congratulations! You built all the snowmen in " + board.getMoveCount() + " moves!");
                } else {
                    updateStatus("Moved " + fromPiece.getType().name().toLowerCase().replace('_', ' ') + " " + dir.name().toLowerCase() + ".");
                }
            } else {
                updateStatus("Can't move that piece in that direction.");
            }
        }

        updateMoveLabel();
        boardPanel.repaint();
    }

    // ── UI helpers ────────────────────────────────────────────────────────────

    private void updateStatus(String msg) {
        statusLabel.setText(msg);
    }

    private void updateMoveLabel() {
        moveLabel.setText("Moves: " + board.getMoveCount());
    }
}
