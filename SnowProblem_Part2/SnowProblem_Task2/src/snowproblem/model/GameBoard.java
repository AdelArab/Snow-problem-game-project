package snowproblem.model;

/**
 * Stores the 5-column by 4-row board and implements Task 2 sliding movement.
 * A snowball slides in a chosen direction until it hits another piece or leaves
 * the board. If it leaves the board, the game ends.
 */
public class GameBoard {

    public static final int ROWS = 4;
    public static final int COLS = 5;

    private final Piece[][] grid;
    private int moveCount;
    private boolean gameOver;

    public GameBoard(Piece[][] startingGrid) {
        grid = startingGrid;
        moveCount = 0;
        gameOver = false;
    }

    public Piece getPiece(int row, int col) {
        if (!inBounds(row, col)) return null;
        return grid[row][col];
    }

    public boolean inBounds(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Moves a large or small snowball in the given direction.
     * Returns true if a legal move was attempted and the board changed/end state changed.
     */
    public boolean move(int row, int col, Direction direction) {
        if (gameOver || !inBounds(row, col)) return false;

        Piece piece = grid[row][col];
        if (piece == null || !piece.canMove()) return false;

        int currentRow = row;
        int currentCol = col;
        int nextRow = currentRow + direction.dr;
        int nextCol = currentCol + direction.dc;

        // If the first step is blocked, the snowball cannot move.
        if (inBounds(nextRow, nextCol) && grid[nextRow][nextCol] != null) {
            return false;
        }

        // Keep sliding through empty cells.
        while (inBounds(nextRow, nextCol) && grid[nextRow][nextCol] == null) {
            currentRow = nextRow;
            currentCol = nextCol;
            nextRow = currentRow + direction.dr;
            nextCol = currentCol + direction.dc;
        }

        grid[row][col] = null;
        moveCount++;

        // If the next square is outside the board, the snowball falls off and the game ends.
        if (!inBounds(nextRow, nextCol)) {
            gameOver = true;
            return true;
        }

        // Otherwise, place the snowball in the last empty square before the obstacle.
        grid[currentRow][currentCol] = piece;
        return true;
    }
}
