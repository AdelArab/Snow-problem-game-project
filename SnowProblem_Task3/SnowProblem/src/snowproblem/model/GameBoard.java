package snowproblem.model;

/**
 * Represents the 5-column × 4-row Snow Problem game board.
 * Handles piece placement, movement rules, stacking, and win detection.
 *
 * Grid coordinates: row 0 = top, col 0 = left.
 */
public class GameBoard {

    public static final int COLS = 5;
    public static final int ROWS = 4;

    /** The grid. null means an empty cell. */
    private final Piece[][] grid;

    /** Total number of snowmen that need to be completed to win. */
    private final int snowmenRequired;

    /** Whether the game is over (a snowball fell off the board). */
    private boolean gameOver;

    /** Whether the player has won. */
    private boolean gameWon;

    /** Number of moves made so far. */
    private int moveCount;

    public GameBoard(Piece[][] initialGrid, int snowmenRequired) {
        this.grid = new Piece[ROWS][COLS];
        this.snowmenRequired = snowmenRequired;
        this.gameOver = false;
        this.gameWon = false;
        this.moveCount = 0;

        // Deep copy the initial grid
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c] = initialGrid[r][c];
            }
        }
    }

    /** Returns the piece at (row, col), or null if the cell is empty. */
    public Piece getPiece(int row, int col) {
        if (!inBounds(row, col)) return null;
        return grid[row][col];
    }

    /** Returns true if (row, col) is within the board. */
    public boolean inBounds(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public boolean isGameOver() { return gameOver; }
    public boolean isGameWon()  { return gameWon; }
    public int getMoveCount()   { return moveCount; }

    /**
     * Attempts to move the piece at (row, col) in the given direction.
     * Snowballs slide until blocked by another piece or the board edge.
     * Returns true if a move was made (even if it results in game-over).
     */
    public boolean move(int row, int col, Direction dir) {
        if (gameOver || gameWon) return false;

        Piece piece = grid[row][col];
        if (piece == null || !piece.canMove()) return false;

        int r = row, c = col;

        // Slide until the next cell is blocked or out of bounds
        while (true) {
            int nr = r + dir.dr();
            int nc = c + dir.dc();

            if (!inBounds(nr, nc)) {
                // Snowball falls off — game over
                grid[r][c] = null;
                gameOver = true;
                moveCount++;
                return true;
            }

            if (grid[nr][nc] != null) {
                // Blocked by another piece — stop here
                break;
            }

            // Move one step
            grid[nr][nc] = piece;
            grid[r][c] = null;
            r = nr;
            c = nc;
        }

        // The piece moved at least somewhere (or stayed put if immediately blocked)
        if (r != row || c != col) {
            moveCount++;
        }

        checkWin();
        return (r != row || c != col);
    }

    /**
     * Attempts to stack the piece at (fromRow, fromCol) onto an adjacent piece.
     * Rules:
     *   - A small snowball can stack onto an adjacent large snowball (no stack yet).
     *   - A snowman head can stack onto an adjacent snowball stack
     *     (large + small already stacked).
     * Returns true if stacking succeeded.
     */
    public boolean stack(int fromRow, int fromCol, Direction dir) {
        if (gameOver || gameWon) return false;

        Piece from = grid[fromRow][fromCol];
        if (from == null) return false;

        int tr = fromRow + dir.dr();
        int tc = fromCol + dir.dc();
        if (!inBounds(tr, tc)) return false;

        Piece target = grid[tr][tc];
        if (target == null) return false;

        // Small snowball stacks onto large snowball (that has no stack yet)
        if (from.getType() == PieceType.SMALL_SNOWBALL
                && target.getType() == PieceType.LARGE_SNOWBALL
                && !target.hasStack()) {
            target.setStackedOn(from);
            grid[fromRow][fromCol] = null;
            moveCount++;
            checkWin();
            return true;
        }

        // Snowman head stacks onto a large snowball that already has a small snowball
        if (from.getType() == PieceType.SNOWMAN_HEAD
                && target.getType() == PieceType.LARGE_SNOWBALL
                && target.hasStack()
                && target.getStackedOn().getType() == PieceType.SMALL_SNOWBALL
                && !target.getStackedOn().hasStack()) {
            target.getStackedOn().setStackedOn(from);
            grid[fromRow][fromCol] = null;
            moveCount++;
            checkWin();
            return true;
        }

        return false;
    }

    /** Checks whether all snowmen are complete and sets gameWon accordingly. */
    private void checkWin() {
        int complete = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] != null && grid[r][c].isCompleteSnowman()) {
                    complete++;
                }
            }
        }
        if (complete >= snowmenRequired) {
            gameWon = true;
        }
    }

    /** Returns the number of complete snowmen currently on the board. */
    public int countCompleteSnowmen() {
        int count = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] != null && grid[r][c].isCompleteSnowman()) count++;
            }
        }
        return count;
    }

    /** Returns a deep copy of this board (used for reset/undo). */
    public Piece[][] getGridCopy() {
        Piece[][] copy = new Piece[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                copy[r][c] = grid[r][c]; // shallow reference — sufficient for reset
            }
        }
        return copy;
    }
}
