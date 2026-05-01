package snowproblem.ui;

import snowproblem.model.*;

import javax.swing.*;
import java.awt.*;

/**
 * Renders the 5x4 Snow Problem game board.
 * Part 1: display only, no interaction.
 */
public class BoardPanel extends JPanel {

    private static final int CELL_SIZE = 110;
    private static final int BOARD_W   = GameBoard.COLS * CELL_SIZE;
    private static final int BOARD_H   = GameBoard.ROWS * CELL_SIZE;

    private static final Color COLOR_BG   = new Color(200, 225, 245);
    private static final Color COLOR_GRID = new Color(150, 180, 210);

    private final GameBoard board;

    public BoardPanel(GameBoard board) {
        this.board = board;
        setPreferredSize(new Dimension(BOARD_W, BOARD_H));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2);
        drawGrid(g2);
        drawPieces(g2);
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(COLOR_BG);
        g.fillRect(0, 0, BOARD_W, BOARD_H);
    }

    private void drawGrid(Graphics2D g) {
        g.setColor(COLOR_GRID);
        g.setStroke(new BasicStroke(1.5f));
        for (int r = 0; r <= GameBoard.ROWS; r++) {
            g.drawLine(0, r * CELL_SIZE, BOARD_W, r * CELL_SIZE);
        }
        for (int c = 0; c <= GameBoard.COLS; c++) {
            g.drawLine(c * CELL_SIZE, 0, c * CELL_SIZE, BOARD_H);
        }
    }

    private void drawPieces(Graphics2D g) {
        int pad = 12;
        int sz  = CELL_SIZE - pad * 2;

        for (int r = 0; r < GameBoard.ROWS; r++) {
            for (int c = 0; c < GameBoard.COLS; c++) {
                Piece piece = board.getPiece(r, c);
                if (piece == null) continue;

                int x = c * CELL_SIZE + pad;
                int y = r * CELL_SIZE + pad;

                switch (piece.getType()) {
                    case TREE            -> drawTree(g, x, y, sz);
                    case LARGE_SNOWBALL  -> drawLargeSnowball(g, x, y, sz);
                    case SMALL_SNOWBALL  -> drawSmallSnowball(g, x, y, sz);
                    case SNOWMAN_HEAD    -> drawHead(g, x, y, sz);
                }
            }
        }
    }

    // ── Piece drawing methods ─────────────────────────────────────────────────

    private void drawTree(Graphics2D g, int x, int y, int sz) {
        // Trunk
        g.setColor(new Color(100, 60, 20));
        g.fillRect(x + sz / 3, y + sz * 2 / 3, sz / 3, sz / 3);
        // Canopy
        g.setColor(new Color(34, 120, 34));
        int[] xs = {x + sz / 2, x, x + sz};
        int[] ys = {y, y + sz * 2 / 3, y + sz * 2 / 3};
        g.fillPolygon(xs, ys, 3);
        g.setColor(new Color(20, 80, 20));
        g.setStroke(new BasicStroke(1.5f));
        g.drawPolygon(xs, ys, 3);
    }

    private void drawLargeSnowball(Graphics2D g, int x, int y, int sz) {
        g.setColor(new Color(180, 210, 240));
        g.fillOval(x, y, sz, sz);
        g.setColor(new Color(130, 170, 210));
        g.setStroke(new BasicStroke(2f));
        g.drawOval(x, y, sz, sz);
        // Shine highlight
        g.setColor(new Color(255, 255, 255, 140));
        g.fillOval(x + sz / 5, y + sz / 8, sz / 4, sz / 5);
        // Label
        drawLabel(g, "Large", x + sz / 2, y + sz + 2);
    }

    private void drawSmallSnowball(Graphics2D g, int x, int y, int sz) {
        int half = sz * 3 / 4;
        int ox = x + (sz - half) / 2;
        int oy = y + (sz - half) / 2;
        g.setColor(new Color(210, 232, 252));
        g.fillOval(ox, oy, half, half);
        g.setColor(new Color(150, 190, 220));
        g.setStroke(new BasicStroke(1.5f));
        g.drawOval(ox, oy, half, half);
        g.setColor(new Color(255, 255, 255, 120));
        g.fillOval(ox + half / 5, oy + half / 8, half / 4, half / 5);
        drawLabel(g, "Small", x + sz / 2, y + sz + 2);
    }

    private void drawHead(Graphics2D g, int x, int y, int sz) {
        int half = sz * 2 / 3;
        int ox = x + (sz - half) / 2;
        int oy = y + (sz - half) / 2;
        g.setColor(new Color(240, 240, 255));
        g.fillOval(ox, oy, half, half);
        g.setColor(new Color(180, 180, 210));
        g.setStroke(new BasicStroke(1.5f));
        g.drawOval(ox, oy, half, half);
        // Eyes
        g.setColor(Color.DARK_GRAY);
        g.fillOval(ox + half / 4 - 2,     oy + half / 3, 4, 4);
        g.fillOval(ox + half * 3 / 4 - 2, oy + half / 3, 4, 4);
        // Smile
        g.drawArc(ox + half / 4, oy + half / 2, half / 2, half / 4, 0, -180);
        drawLabel(g, "Head", x + sz / 2, y + sz + 2);
    }

    /** Draws a small label below a piece for clarity. */
    private void drawLabel(Graphics2D g, String text, int cx, int y) {
        g.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g.setColor(new Color(60, 80, 120));
        FontMetrics fm = g.getFontMetrics();
        g.drawString(text, cx - fm.stringWidth(text) / 2, y + fm.getAscent());
    }
}
