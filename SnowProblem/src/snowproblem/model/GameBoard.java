package snowproblem.model;

public class GameBoard {

    // Sets the board size used by the whole game.
    public static final int COLS = 5;
    public static final int ROWS = 4;

    // Stores the pieces currently on the board.
    private final Piece[][] grid;

    // Stores the target number of snowmen needed to finish the level.
    private final int snowmenRequired;

    // Tracks whether the level is lost, won, and how many moves were made.
    private boolean gameOver;
    private boolean gameWon;
    private int moveCount;

    // Creates a new board from the level grid and resets the game state.
    public GameBoard(Piece[][] initialGrid, int snowmenRequired) {
        this.grid = new Piece[ROWS][COLS];
        this.snowmenRequired = snowmenRequired;
        this.gameOver = false;
        this.gameWon = false;
        this.moveCount = 0;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c] = initialGrid[r][c];
            }
        }
    }

    // Returns the piece in a cell, or null if the cell is empty.
    public Piece getPiece(int row, int col) {
        if (!inBounds(row, col)) {
            return null;
        }

        return grid[row][col];
    }

    // Checks if a row and column are inside the board.
    public boolean inBounds(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    // Allows other classes to check the game state.
    public boolean isGameOver() { return gameOver; }
    public boolean isGameWon()  { return gameWon; }
    public int getMoveCount()   { return moveCount; }

    // Moves a snowball in one direction until it hits something or falls off.
    public boolean move(int row, int col, Direction dir) {
        if (gameOver || gameWon) {
            return false;
        }

        Piece piece = grid[row][col];

        if (piece == null || !piece.canMove()) {
            return false;
        }

        int r = row;
        int c = col;

        while (true) {
            int nr = r + dir.dr();
            int nc = c + dir.dc();

            if (!inBounds(nr, nc)) {
                grid[r][c] = null;
                gameOver = true;
                moveCount++;
                return true;
            }

            if (grid[nr][nc] != null) {
                break;
            }

            grid[nr][nc] = piece;
            grid[r][c] = null;
            r = nr;
            c = nc;
        }

        if (r != row || c != col) {
            moveCount++;
        }

        checkWin();
        return (r != row || c != col);
    }

    // Tries to stack one piece onto another adjacent piece.
    public boolean stack(int fromRow, int fromCol, Direction dir) {
        if (gameOver || gameWon) {
            return false;
        }

        Piece from = grid[fromRow][fromCol];

        if (from == null) {
            return false;
        }

        int tr = fromRow + dir.dr();
        int tc = fromCol + dir.dc();

        if (!inBounds(tr, tc)) {
            return false;
        }

        Piece target = grid[tr][tc];

        if (target == null) {
            return false;
        }

        // Allows a small snowball to stack onto a large snowball.
        if (from.getType() == PieceType.SMALL_SNOWBALL
                && target.getType() == PieceType.LARGE_SNOWBALL
                && !target.hasStack()) {
            target.setStackedOn(from);
            grid[fromRow][fromCol] = null;
            moveCount++;
            checkWin();
            return true;
        }

        // Allows a head to stack onto a large and small snowball stack.
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

    // Checks if enough snowmen have been completed to win the level.
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
}