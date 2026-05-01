package snowproblem.model;

/**
 * Represents the 5-column x 4-row Snow Problem game board.
 * Part 1: stores pieces on a grid and provides access to them.
 *
 * Grid coordinates: row 0 = top, col 0 = left.
 */
public class GameBoard {

    public static final int COLS = 5;
    public static final int ROWS = 4;

    private final Piece[][] grid;

    public GameBoard() {
        this.grid = new Piece[ROWS][COLS];
        setupLevel1();
    }

    /**
     * Places the pieces for Level 1 onto the board.
     *
     * Layout:
     *   . . T . .
     *   . L . S .
     *   . . H . .
     *   . . . . .
     */
    private void setupLevel1() {
        grid[0][2] = new Piece(PieceType.TREE);
        grid[1][1] = new Piece(PieceType.LARGE_SNOWBALL);
        grid[1][3] = new Piece(PieceType.SMALL_SNOWBALL);
        grid[2][2] = new Piece(PieceType.SNOWMAN_HEAD);
    }

    /** Returns the piece at (row, col), or null if the cell is empty. */
    public Piece getPiece(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) return null;
        return grid[row][col];
    }
}
