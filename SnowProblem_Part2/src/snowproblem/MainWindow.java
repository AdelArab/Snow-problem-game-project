package snowproblem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * MainWindow is the top-level application window for Parts 1 & 2.
 *
 * Part 2 scope:
 *   - Creates a GameBoard populated with Level 1 data
 *   - Renders the board using BoardPanel and the provided PNG graphics
 *   - Displays level metadata (level number, par, piece legend)
 *   - No interactive movement yet (that is Part 3)
 *
 * Entry point: {@link #main(String[])}
 */
public class MainWindow extends JFrame {

    private final GameBoard board;
    private final BoardPanel boardPanel;

    // ── Construction ─────────────────────────────────────────────────────

    public MainWindow() {
        super("❄  Snow Problem  –  Level 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // ── Build Level 1 board ───────────────────────────────────────────
        List<Level> levels = Level.buildAllLevels();
        Level level1 = levels.get(0); // index 0 = level number 1

        board = new GameBoard();
        for (Piece p : level1.getInitialPieces()) {
            board.addPiece(p);
        }

        // ── Layout ────────────────────────────────────────────────────────
        setLayout(new BorderLayout(0, 0));

        add(buildHeader(level1), BorderLayout.NORTH);

        boardPanel = new BoardPanel(board);
        add(boardPanel, BorderLayout.CENTER);

        add(buildLegend(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // centre on screen
    }

    // ── Header panel ──────────────────────────────────────────────────────

    /**
     * Builds the header bar showing level number and par move count.
     */
    private JPanel buildHeader(Level level) {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 10));
        header.setBackground(new Color(25, 90, 170));

        header.add(headerLabel("Level  " + level.getLevelNumber(), 22, Font.BOLD, Color.WHITE));
        header.add(headerLabel("|", 20, Font.PLAIN, new Color(100, 150, 210)));
        header.add(headerLabel("Par:  " + level.getMinMoves() + " moves", 16, Font.PLAIN,
                new Color(200, 230, 255)));
        header.add(headerLabel("|", 20, Font.PLAIN, new Color(100, 150, 210)));
        header.add(headerLabel("Difficulty:  STARTER", 14, Font.ITALIC,
                new Color(160, 255, 160)));

        return header;
    }

    private JLabel headerLabel(String text, int size, int style, Color fg) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", style, size));
        lbl.setForeground(fg);
        return lbl;
    }

    // ── Legend panel ──────────────────────────────────────────────────────

    /**
     * Builds the bottom legend bar explaining each piece type.
     */
    private JPanel buildLegend() {
        JPanel legend = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 8));
        legend.setBackground(new Color(200, 222, 245));
        legend.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(160, 200, 230)));

        legend.add(legendItem("🌲", "Tree (fixed)",         new Color(40, 130, 40)));
        legend.add(legendItem("⚪", "Large snowball",       new Color(100, 140, 200)));
        legend.add(legendItem("🔘", "Small snowball",       new Color(130, 160, 210)));
        legend.add(legendItem("🎩", "Snowman head",         new Color(50, 50, 50)));

        // Level 1 specific note
        JLabel note = new JLabel("  ·  Level 1 pieces: 1 × small snowball, 1 × head (blue), 1 × large snowball");
        note.setFont(new Font("SansSerif", Font.ITALIC, 11));
        note.setForeground(new Color(60, 80, 120));
        legend.add(note);

        return legend;
    }

    private JPanel legendItem(String icon, String label, Color colour) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        item.setOpaque(false);

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JLabel textLbl = new JLabel(label);
        textLbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        textLbl.setForeground(colour);

        item.add(iconLbl);
        item.add(textLbl);
        return item;
    }

    // ── Entry point ───────────────────────────────────────────────────────

    /**
     * Application entry point.
     * Launches the window on the Swing Event Dispatch Thread.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
