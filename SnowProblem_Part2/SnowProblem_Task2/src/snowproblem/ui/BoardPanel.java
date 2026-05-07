package snowproblem.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import snowproblem.model.GameBoard;
import snowproblem.model.Piece;
import snowproblem.model.PieceType;

/** Draws the board and handles mouse selection for Task 2 movement. */
public class BoardPanel extends JPanel {

    private static final int CELL_SIZE = 100;
    private static final int BOARD_W = GameBoard.COLS * CELL_SIZE;
    private static final int BOARD_H = GameBoard.ROWS * CELL_SIZE;
    private static final Color COLOR_BG = new Color(200, 225, 245);
    private static final Color COLOR_GRID = new Color(150, 180, 210);
    private static final Color COLOR_SELECT = new Color(255, 255, 80, 150);

    private GameBoard board;
    private final Map<String, BufferedImage> images = new HashMap<>();
    private int selectedRow = -1;
    private int selectedCol = -1;
    private BiConsumer<int[], int[]> moveCallback;

    public BoardPanel(GameBoard board) {
        this.board = board;
        loadImages();
        setPreferredSize(new Dimension(BOARD_W, BOARD_H));
        setBackground(COLOR_BG);
        setupMouse();
    }

    public void setBoard(GameBoard board) {
        this.board = board;
        selectedRow = -1;
        selectedCol = -1;
        repaint();
    }

    public void setMoveCallback(BiConsumer<int[], int[]> moveCallback) {
        this.moveCallback = moveCallback;
    }

    private void loadImages() {
        loadImage("tree", "tree.png");
        loadImage("large", "snowball_large.png");
        loadImage("small", "snowball_small.png");
        loadImage("head_blue", "head_blue.png");
        loadImage("head_red", "head_red.png");
        loadImage("head_yellow", "head_yellow.png");
        loadImage("hole", "hole.png");
    }

    private void loadImage(String key, String fileName) {
        try {
            File file = new File(System.getProperty("user.dir"), "resources/" + fileName);
            images.put(key, ImageIO.read(file));
        } catch (IOException e) {
            System.err.println("Could not load image: " + fileName);
        }
    }

    private void setupMouse() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    private void handleClick(int x, int y) {
        int row = y / CELL_SIZE;
        int col = x / CELL_SIZE;

        if (!board.inBounds(row, col) || board.isGameOver()) return;

        if (selectedRow == -1) {
            Piece piece = board.getPiece(row, col);
            if (piece != null && piece.canMove()) {
                selectedRow = row;
                selectedCol = col;
                repaint();
            }
            return;
        }

        if (moveCallback != null) {
            moveCallback.accept(new int[] {selectedRow, selectedCol}, new int[] {row, col});
        }

        selectedRow = -1;
        selectedCol = -1;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawHoles(g);
        drawGrid(g);
        drawSelection(g);
        drawPieces(g);
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
        BufferedImage hole = images.get("hole");
        int pad = 1;
        int size = CELL_SIZE - pad * 2;
        for (int r = 0; r < GameBoard.ROWS; r++) {
            for (int c = 0; c < GameBoard.COLS; c++) {
                if (hole != null) {
                    g.drawImage(hole, c * CELL_SIZE + pad, r * CELL_SIZE + pad, size, size, null);
                }
            }
        }
    }

    private void drawSelection(Graphics2D g) {
        if (selectedRow == -1) return;
        g.setColor(COLOR_SELECT);
        g.fillRect(selectedCol * CELL_SIZE + 2, selectedRow * CELL_SIZE + 2, CELL_SIZE - 4, CELL_SIZE - 4);
        g.setColor(Color.DARK_GRAY);
        g.setStroke(new BasicStroke(2));
        g.drawRect(selectedCol * CELL_SIZE + 2, selectedRow * CELL_SIZE + 2, CELL_SIZE - 4, CELL_SIZE - 4);
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
        if (piece.getType() == PieceType.TREE) {
            drawImage(g, "tree", x, y, size);
        } else if (piece.getType() == PieceType.LARGE_SNOWBALL) {
            drawImage(g, "large", x, y, size);
        } else if (piece.getType() == PieceType.SMALL_SNOWBALL) {
            drawImage(g, "small", x, y, size);
        } else if (piece.getType() == PieceType.SNOWMAN_HEAD) {
            drawImage(g, "head_" + piece.getColor(), x, y, size);
        }
    }

    private void drawImage(Graphics2D g, String key, int x, int y, int size) {
        BufferedImage image = images.get(key);
        if (image != null) {
            g.drawImage(image, x, y, size, size, null);
        }
    }
}
