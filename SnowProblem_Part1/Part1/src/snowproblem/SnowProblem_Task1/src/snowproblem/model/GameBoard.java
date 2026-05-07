package snowproblem.model;

/** Holds the 5-column by 4-row board used by the game. */
public class GameBoard {
    public static final int ROWS = 4;
    public static final int COLS = 5;

    private final Piece[][] grid;
    private final int snowmenRequired;

    public GameBoard(Piece[][] grid, int snowmenRequired) {
        this.grid = grid;
        this.snowmenRequired = snowmenRequired;
    }

    public Piece getPiece(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            return null;
        }
        return grid[row][col];
    }

    public int getSnowmenRequired() {
        return snowmenRequired;
    }
}
