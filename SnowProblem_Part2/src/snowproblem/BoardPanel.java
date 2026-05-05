package snowproblem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * BoardPanel renders the 5×4 game board and all pieces using the provided PNG assets.
 *
 * Part 2 scope: display only — no click input yet (that comes in Part 3).
 * The panel is driven entirely by the GameBoard data model from Part 1.
 *
 * Visual design:
 *   - Light-blue grid with rounded cells
 *   - Each cell shows a subtle "hole" background image
 *   - Piece images drawn centred inside their cell
 *   - Board is surrounded by a snow-coloured border
 */
public class BoardPanel extends JPanel {

    // ── Layout constants ─────────────────────────────────────────────────
    /** Pixel size of each grid cell. */
    public static final int CELL = 100;

    /** Padding around the grid. */
    public static final int PADDING = 30;

    // ── State ─────────────────────────────────────────────────────────────
    private GameBoard board;
    private final Map<String, BufferedImage> images = new HashMap<>();

    // ── Construction ─────────────────────────────────────────────────────

    /**
     * Creates a BoardPanel backed by the given GameBoard.
     *
     * @param board the game model to render
     */
    public BoardPanel(GameBoard board) {
        this.board = board;
        setPreferredSize(new Dimension(
            GameBoard.COLS * CELL + PADDING * 2,
            GameBoard.ROWS * CELL + PADDING * 2
        ));
        setBackground(new Color(210, 230, 250));
        loadImages();
    }

    // ── Public API ────────────────────────────────────────────────────────

    /**
     * Replaces the current game model and repaints.
     * Call this when loading a new level.
     */
    public void setBoard(GameBoard board) {
        this.board = board;
        repaint();
    }

    // ── Image loading ─────────────────────────────────────────────────────

    /**
     * Loads all PNG assets from the resources directory on the classpath.
     * Missing images are silently skipped (the cell will appear empty).
     */
    private void loadImages() {
        String[] names = {
            "hole",
            "tree",
            "snowball_large",
            "snowball_small",
            "head_red",
            "head_blue",
            "head_yellow",
            "snowman_stack",
            "snowman_red",
            "snowman_blue",
            "snowman_yellow"
        };
        for (String name : names) {
            String path = "/snowproblem/resources/" + name + ".png";
            try (InputStream is = getClass().getResourceAsStream(path)) {
                if (is != null) {
                    images.put(name, ImageIO.read(is));
                } else {
                    System.err.println("BoardPanel: image not found on classpath: " + path);
                }
            } catch (Exception e) {
                System.err.println("BoardPanel: failed to load " + name + ": " + e.getMessage());
            }
        }
    }

    // ── Painting ──────────────────────────────────────────────────────────

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Enable smooth rendering
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,      RenderingHints.VALUE_RENDER_QUALITY);

        drawBackground(g2);
        drawGrid(g2);
        drawPieces(g2);
    }

    /**
     * Draws a subtle drop-shadow behind the board area.
     */
    private void drawBackground(Graphics2D g) {
        int bx = PADDING - 6;
        int by = PADDING - 6;
        int bw = GameBoard.COLS * CELL + 12;
        int bh = GameBoard.ROWS * CELL + 12;

        // Shadow
        g.setColor(new Color(0, 0, 0, 40));
        g.fillRoundRect(bx + 4, by + 4, bw, bh, 20, 20);

        // Board surface
        g.setColor(new Color(230, 242, 255));
        g.fillRoundRect(bx, by, bw, bh, 20, 20);
        g.setColor(new Color(160, 200, 230));
        g.setStroke(new BasicStroke(2));
        g.drawRoundRect(bx, by, bw, bh, 20, 20);
        g.setStroke(new BasicStroke(1));
    }

    /**
     * Draws each grid cell: a rounded rectangle with an optional hole image.
     */
    private void drawGrid(Graphics2D g) {
        for (int row = 0; row < GameBoard.ROWS; row++) {
            for (int col = 0; col < GameBoard.COLS; col++) {
                int x = PADDING + col * CELL;
                int y = PADDING + row * CELL;

                // Cell fill
                g.setColor(new Color(245, 252, 255));
                g.fillRoundRect(x + 5, y + 5, CELL - 10, CELL - 10, 18, 18);

                // Cell border
                g.setColor(new Color(190, 215, 240));
                g.drawRoundRect(x + 5, y + 5, CELL - 10, CELL - 10, 18, 18);

                // Hole image (subtle circular indent in centre)
                BufferedImage hole = images.get("hole");
                if (hole != null) {
                    int pad = 28;
                    g.drawImage(hole, x + pad, y + pad + 4, CELL - pad * 2, CELL - pad * 2 - 4, null);
                }
            }
        }
    }

    /**
     * Draws every piece currently on the board, centred within its cell.
     */
    private void drawPieces(Graphics2D g) {
        for (Piece piece : board.getPieces()) {
            int x = PADDING + piece.getCol() * CELL;
            int y = PADDING + piece.getRow() * CELL;

            BufferedImage img = imageForPiece(piece);
            if (img != null) {
                int pad = 10;
                g.drawImage(img, x + pad, y + pad, CELL - pad * 2, CELL - pad * 2, null);
            } else {
                // Fallback: draw a coloured circle with a label
                drawFallback(g, piece, x, y);
            }
        }
    }

    /**
     * Maps a Piece to its image key.
     */
    private BufferedImage imageForPiece(Piece p) {
        switch (p.getType()) {
            case TREE:           return images.get("tree");
            case LARGE_SNOWBALL: return images.get("snowball_large");
            case SMALL_SNOWBALL: return images.get("snowball_small");
            case HEAD_RED:       return images.get("head_red");
            case HEAD_BLUE:      return images.get("head_blue");
            case HEAD_YELLOW:    return images.get("head_yellow");
            case SNOWBALL_STACK: return images.get("snowman_stack");
            case SNOWMAN_RED:    return images.get("snowman_red");
            case SNOWMAN_BLUE:   return images.get("snowman_blue");
            case SNOWMAN_YELLOW: return images.get("snowman_yellow");
            default:             return null;
        }
    }

    /**
     * Fallback renderer used when a PNG asset is missing.
     * Draws a coloured circle with a short text label so the game is still usable.
     */
    private void drawFallback(Graphics2D g, Piece p, int x, int y) {
        Color fill;
        String label;
        switch (p.getType()) {
            case TREE:           fill = new Color(40, 140, 40);   label = "T";  break;
            case LARGE_SNOWBALL: fill = new Color(210, 230, 250); label = "LB"; break;
            case SMALL_SNOWBALL: fill = new Color(230, 240, 255); label = "SB"; break;
            case HEAD_RED:       fill = new Color(220, 80, 80);   label = "HR"; break;
            case HEAD_BLUE:      fill = new Color(80, 120, 220);  label = "HB"; break;
            case HEAD_YELLOW:    fill = new Color(220, 200, 40);  label = "HY"; break;
            case SNOWBALL_STACK: fill = new Color(200, 220, 240); label = "ST"; break;
            default:             fill = new Color(180, 180, 180); label = "?";  break;
        }
        int pad = 14;
        g.setColor(fill);
        g.fillOval(x + pad, y + pad, CELL - pad * 2, CELL - pad * 2);
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontMetrics fm = g.getFontMetrics();
        g.drawString(label, x + CELL / 2 - fm.stringWidth(label) / 2, y + CELL / 2 + fm.getAscent() / 2 - 2);
    }
}
