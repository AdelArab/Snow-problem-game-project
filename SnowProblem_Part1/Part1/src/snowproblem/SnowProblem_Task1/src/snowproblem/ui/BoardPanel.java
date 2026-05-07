package snowproblem.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import snowproblem.model.GameBoard;
import snowproblem.model.Piece;
import snowproblem.model.PieceType;

/** Draws the board and pieces for Task 1. */
public class BoardPanel extends JPanel {
    private static final int CELL_SIZE = 100;
    private static final int BOARD_W = GameBoard.COLS * CELL_SIZE;
    private static final int BOARD_H = GameBoard.ROWS * CELL_SIZE;

    private static final Color COLOR_BG = new Color(200, 225, 245);
    private static final Color COLOR_GRID = new Color(150, 180, 210);

    private final Map<String, BufferedImage> images = new HashMap<>();
    private GameBoard board;

    public BoardPanel(GameBoard board) {
        this.board = board;
        loadImages();
        setPreferredSize(new Dimension(BOARD_W, BOARD_H));
        setBackground(COLOR_BG);
    }

    private void loadImages() {
        loadImage("tree", "tree.png");
        loadImage("large", "snowball_large.png");
        loadImage("small", "snowball_small.png");
        loadImage("head_blue", "head_blue.png");
        loadImage("hole", "hole.png");
    }

    private void loadImage(String key, String fileName) {
        try {
            File file = new File(System.getProperty("user.dir"), "resources/" + fileName);
            images.put(key, ImageIO.read(file));
        } catch (IOException e) {
            System.out.println("Could not load image: " + fileName);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawBackground(g2);
        drawHoles(g2);
        drawGrid(g2);
        drawPieces(g2);
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(COLOR_BG);
        g.fillRect(0, 0, BOARD_W, BOARD_H);
    }

    private void drawGrid(Graphics2D g) {
        g.setColor(COLOR_GRID);
        for (int r = 0; r <= GameBoard.ROWS; r++) {
            g.drawLine(0, r * CELL_SIZE, BOARD_W, r * CELL_SIZE);
        }
        for (int c = 0; c <= GameBoard.COLS; c++) {
            g.drawLine(c * CELL_SIZE, 0, c * CELL_SIZE, BOARD_H);
        }
    }

    private void drawHoles(Graphics2D g) {
        int pad = 1;
        int size = CELL_SIZE - pad * 2;
        for (int r = 0; r < GameBoard.ROWS; r++) {
            for (int c = 0; c < GameBoard.COLS; c++) {
                drawImage(g, "hole", c * CELL_SIZE + pad, r * CELL_SIZE + pad, size, size);
            }
        }
    }

    private void drawPieces(Graphics2D g) {
        int pad = 1;
        int size = CELL_SIZE - pad * 2;
        for (int r = 0; r < GameBoard.ROWS; r++) {
            for (int c = 0; c < GameBoard.COLS; c++) {
                Piece piece = board.getPiece(r, c);
                if (piece != null) {
                    drawPiece(g, piece, c * CELL_SIZE + pad, r * CELL_SIZE + pad, size);
                }
            }
        }
    }

    private void drawPiece(Graphics2D g, Piece piece, int x, int y, int size) {
        PieceType type = piece.getType();
        if (type == PieceType.TREE) {
            drawImage(g, "tree", x, y, size, size);
        } else if (type == PieceType.LARGE_SNOWBALL) {
            drawImage(g, "large", x, y, size, size);
        } else if (type == PieceType.SMALL_SNOWBALL) {
            drawImage(g, "small", x, y, size, size);
        } else if (type == PieceType.SNOWMAN_HEAD) {
            drawImage(g, "head_" + piece.getColor(), x, y, size, size);
        }
    }

    private void drawImage(Graphics2D g, String key, int x, int y, int w, int h) {
        BufferedImage image = images.get(key);
        if (image != null) {
            g.drawImage(image, x, y, w, h, null);
        }
    }
}
