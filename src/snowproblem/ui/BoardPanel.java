package snowproblem.ui;

import snowproblem.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import java.util.function.BiConsumer;

// Draws the game board, pieces, selection box, and messages.
public class BoardPanel extends JPanel {

    // Sets the board size and the colours used in the display.
    private static final int CELL_SIZE = 100;
    private static final int BOARD_W = GameBoard.COLS * CELL_SIZE;
    private static final int BOARD_H = GameBoard.ROWS * CELL_SIZE;

    private static final Color COLOR_BG = new Color(200, 225, 245);
    private static final Color COLOR_GRID = new Color(150, 180, 210);
    private static final Color COLOR_SELECT = new Color(255, 220, 60, 160);
    private static final Color COLOR_HOVER = new Color(255, 255, 255, 80);
    private static final Color COLOR_GAME_OVER = new Color(200, 50, 50, 200);
    private static final Color COLOR_GAME_WON = new Color(50, 180, 50, 200);

    // Stores the board, loaded images, selected cell, and mouse hover cell.
    private GameBoard board;
    private final Map<String, BufferedImage> images = new HashMap<>();
    private String currentSnowmanKey = "snowman_blue";
    private int selectedRow = -1;
    private int selectedCol = -1;
    private int hoverRow = -1;
    private int hoverCol = -1;

    // Sends the selected cell and clicked cell to GameWindow.
    private BiConsumer<int[], int[]> moveCallback;

    // Creates the board panel and prepares the images and mouse controls.
    public BoardPanel(GameBoard board) {
        this.board = board;
        loadImages();
        setPreferredSize(new Dimension(BOARD_W, BOARD_H));
        setBackground(COLOR_BG);
        setupMouseListeners();
    }

    // Loads all the images used for pieces, snowmen, and holes.
    private void loadImages() {
        loadImage("tree", "tree.png");
        loadImage("large", "snowball_large.png");
        loadImage("small", "snowball_small.png");

        loadImage("head_blue", "head_blue.png");
        loadImage("head_red", "head_red.png");
        loadImage("head_yellow", "head_yellow.png");

        loadImage("snowman_blue", "snowman_blue.png");
        loadImage("snowman_red", "snowman_red.png");
        loadImage("snowman_yellow", "snowman_yellow.png");

        loadImage("stack", "snowman_stack.png");
        loadImage("hole", "hole.png");
    }

    // Loads one image from the resources folder and stores it using a short name.
    private void loadImage(String key, String fileName) {
        try {
            File file = new File(System.getProperty("user.dir"), "resources/" + fileName);
            BufferedImage image = ImageIO.read(file);

            if (image == null) {
                throw new IOException("Could not read image: " + file.getAbsolutePath());
            }

            images.put(key, image);
        } catch (IOException e) {
            System.err.println("Could not load image: resources/" + fileName);
            e.printStackTrace();
        }
    }

    // Draws a hole image in every board cell before the pieces are drawn.
    private void drawAllHoles(Graphics2D g) {
        int pad = 1;
        int size = CELL_SIZE - pad * 2;

        for (int row = 0; row < GameBoard.ROWS; row++) {
            for (int col = 0; col < GameBoard.COLS; col++) {
                int x = col * CELL_SIZE + pad;
                int y = row * CELL_SIZE + pad;

                drawImageOrFallback(g, "hole", x, y, size, size, new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        }
    }

    // Allows GameWindow to receive move requests from this panel.
    public void setMoveCallback(BiConsumer<int[], int[]> callback) {
        this.moveCallback = callback;
    }

    // Changes the board when a new level is loaded or reset.
    public void setBoard(GameBoard board) {
        this.board = board;
        selectedRow = -1;
        selectedCol = -1;
        repaint();
    }

    // Sets which completed snowman colour should be drawn.
    public void setHeadColor(String color) {
        currentSnowmanKey = "snowman_" + color;
        repaint();
    }
        // Sets up clicking and mouse movement on the board.
    private void setupMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int r = e.getY() / CELL_SIZE;
                int c = e.getX() / CELL_SIZE;

                if (r != hoverRow || c != hoverCol) {
                    hoverRow = r;
                    hoverCol = c;
                    repaint();
                }
            }
        });
    }

    // Handles the first click to select a piece and the second click to choose where to move.
    private void handleClick(int px, int py) {
        int row = py / CELL_SIZE;
        int col = px / CELL_SIZE;

        if (!board.inBounds(row, col)) {
            return;
        }

        if (board.isGameOver() || board.isGameWon()) {
            return;
        }

        if (selectedRow == -1) {
            Piece p = board.getPiece(row, col);

            // First click selects a movable piece or a snowman head.
            if (p != null && (p.canMove() || p.getType() == PieceType.SNOWMAN_HEAD)) {
                selectedRow = row;
                selectedCol = col;
                repaint();
            }
        } else {
            if (row == selectedRow && col == selectedCol) {
                // Clicking the same cell again cancels the selection.
                selectedRow = -1;
                selectedCol = -1;
            } else if (moveCallback != null) {
                // Second click sends the selected cell and target cell to GameWindow.
                moveCallback.accept(new int[]{selectedRow, selectedCol}, new int[]{row, col});
                selectedRow = -1;
                selectedCol = -1;
            }

            repaint();
        }
    }

    // Draws the full board every time the panel refreshes.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2);
        drawGrid(g2);
        drawAllHoles(g2);
        drawPieces(g2);
        drawHover(g2);
        drawSelection(g2);
        drawOverlay(g2);
    }

    // Draws the background colour behind the board.
    private void drawBackground(Graphics2D g) {
        g.setColor(COLOR_BG);
        g.fillRect(0, 0, BOARD_W, BOARD_H);
    }

    // Draws the grid lines for the 5 by 4 board.
    private void drawGrid(Graphics2D g) {
        g.setColor(COLOR_GRID);
        g.setStroke(new BasicStroke(1f));

        for (int r = 0; r <= GameBoard.ROWS; r++) {
            g.drawLine(0, r * CELL_SIZE, BOARD_W, r * CELL_SIZE);
        }

        for (int c = 0; c <= GameBoard.COLS; c++) {
            g.drawLine(c * CELL_SIZE, 0, c * CELL_SIZE, BOARD_H);
        }
    }

    // Goes through every board cell and draws any piece found there.
    private void drawPieces(Graphics2D g) {
        int pad = 1;
        int sz = CELL_SIZE - pad * 2;

        for (int r = 0; r < GameBoard.ROWS; r++) {
            for (int c = 0; c < GameBoard.COLS; c++) {
                Piece piece = board.getPiece(r, c);

                if (piece == null) {
                    continue;
                }

                int x = c * CELL_SIZE + pad;
                int y = r * CELL_SIZE + pad;

                drawPieceAt(g, piece, x, y, sz);
            }
        }
    }

    // Draws one piece, including stacked pieces if the piece has a stack.
    private void drawPieceAt(Graphics2D g, Piece piece, int x, int y, int sz) {
        if (piece.isCompleteSnowman()) {
            drawSnowman(g, x, y, sz);
            return;
        }

            switch (piece.getType()) {
            case TREE:
                drawTree(g, x, y, sz);
                break;

            case LARGE_SNOWBALL:
                if (piece.hasStack()) {
                    drawImageOrFallback(g, "stack", x, y, sz, sz, new Runnable() {
                        @Override
                        public void run() {
                            int offset = sz / 4;

                            drawLargeSnowball(g, x, y, sz);
                            drawSmallSnowball(g, x + offset / 2, y - offset, sz - offset);

                            if (piece.getStackedOn() != null && piece.getStackedOn().hasStack()) {
                                drawHead(g, x + offset, y - offset * 2, sz - offset * 2,
                                        piece.getStackedOn().getStackedOn().getColor());
                            }
                        }
                    });
                } else {
                    drawLargeSnowball(g, x, y, sz);
                }
                break;

            case SMALL_SNOWBALL:
                drawSmallSnowball(g, x, y, sz);
                break;

            case SNOWMAN_HEAD:
                drawHead(g, x, y, sz, piece.getColor());
                break;
        }
    }

    // Draws a tree image.
    private void drawTree(Graphics2D g, int x, int y, int sz) {
        drawImageOrFallback(g, "tree", x, y, sz, sz, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    // Draws a large snowball image.
    private void drawLargeSnowball(Graphics2D g, int x, int y, int sz) {
        drawImageOrFallback(g, "large", x, y, sz, sz, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    // Draws a small snowball image.
    private void drawSmallSnowball(Graphics2D g, int x, int y, int sz) {
        drawImageOrFallback(g, "small", x, y, sz, sz, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    // Draws a snowman head image using the correct colour.
    private void drawHead(Graphics2D g, int x, int y, int sz, String color) {
        drawImageOrFallback(g, "head_" + color, x, y, sz, sz, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    // Draws a completed snowman image.
    private void drawSnowman(Graphics2D g, int x, int y, int sz) {
        drawImageOrFallback(g, currentSnowmanKey, x, y, sz, sz, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    // Draws the image if it loaded correctly.
    private void drawImageOrFallback(Graphics2D g, String key, int x, int y, int w, int h, Runnable fallback) {
        BufferedImage image = images.get(key);

        if (image != null) {
            g.drawImage(image, x, y, w, h, null);
        } else {
            fallback.run();
        }
    }

    // Highlights the cell that the mouse is currently over.
    private void drawHover(Graphics2D g) {
        if (!board.inBounds(hoverRow, hoverCol) || board.isGameOver() || board.isGameWon()) {
            return;
        }

        g.setColor(COLOR_HOVER);
        g.fillRect(hoverCol * CELL_SIZE + 1, hoverRow * CELL_SIZE + 1,
                CELL_SIZE - 2, CELL_SIZE - 2);
    }

    // Highlights the piece selected by the player.
    private void drawSelection(Graphics2D g) {
        if (selectedRow == -1) {
            return;
        }

        g.setColor(COLOR_SELECT);
        g.fillRoundRect(selectedCol * CELL_SIZE + 3, selectedRow * CELL_SIZE + 3,
                CELL_SIZE - 6, CELL_SIZE - 6, 12, 12);

        g.setColor(COLOR_SELECT.darker());
        g.setStroke(new BasicStroke(2f));
        g.drawRoundRect(selectedCol * CELL_SIZE + 3, selectedRow * CELL_SIZE + 3,
                CELL_SIZE - 6, CELL_SIZE - 6, 12, 12);
    }

    // Shows the win or game over message on the board.
    private void drawOverlay(Graphics2D g) {
        if (board.isGameOver()) {
            drawCentreMessage(g, "Game Over — snowball fell off!", COLOR_GAME_OVER);
        } else if (board.isGameWon()) {
            drawCentreMessage(g, "You Win! All snowmen built!", COLOR_GAME_WON);
        }
    }

    // Draws a message box in the middle of the board.
    private void drawCentreMessage(Graphics2D g, String msg, Color bg) {
        g.setFont(new Font("SansSerif", Font.BOLD, 22));

        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(msg) + 40;
        int h = fm.getHeight() + 20;
        int x = (BOARD_W - w) / 2;
        int y = (BOARD_H - h) / 2;

        g.setColor(bg);
        g.fillRoundRect(x, y, w, h, 16, 16);

        g.setColor(Color.WHITE);
        g.drawString(msg, x + 20, y + fm.getAscent() + 10);
    }
}