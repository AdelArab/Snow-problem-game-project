package snowproblem;

/**
 * Represents a single game piece on the board.
 *
 * Each piece has:
 *  - a type  (what kind of piece it is)
 *  - a row   (0 = top row)
 *  - a col   (0 = left column)
 *
 * Convenience query methods avoid scattering type-check logic across the codebase.
 */
public class Piece {

    private PieceType type;
    private int row;
    private int col;

    /**
     * Constructs a piece of the given type at board position (row, col).
     *
     * @param type  the type of this piece
     * @param row   row index (0-based, 0 = top)
     * @param col   column index (0-based, 0 = left)
     */
    public Piece(PieceType type, int row, int col) {
        this.type = type;
        this.row  = row;
        this.col  = col;
    }

    // ── Getters / setters ─────────────────────────────────────────────────

    public PieceType getType()          { return type; }
    public void      setType(PieceType t) { this.type = t; }

    public int  getRow()        { return row; }
    public void setRow(int row) { this.row = row; }

    public int  getCol()        { return col; }
    public void setCol(int col) { this.col = col; }

    // ── Convenience queries ───────────────────────────────────────────────

    /**
     * Returns true if this piece can be slid by the player (large or small snowball).
     * Stacked snowballs, trees, heads, and complete snowmen are NOT movable.
     */
    public boolean isMovable() {
        return type == PieceType.LARGE_SNOWBALL || type == PieceType.SMALL_SNOWBALL;
    }

    /** Returns true for a plain (unstacked) snowball of either size. */
    public boolean isSnowball() {
        return type == PieceType.LARGE_SNOWBALL || type == PieceType.SMALL_SNOWBALL;
    }

    /** Returns true for any of the three head variants. */
    public boolean isHead() {
        return type == PieceType.HEAD_RED
            || type == PieceType.HEAD_BLUE
            || type == PieceType.HEAD_YELLOW;
    }

    /** Returns true when a small snowball has been stacked onto a large one. */
    public boolean isStack() {
        return type == PieceType.SNOWBALL_STACK;
    }

    /** Returns true when the snowman is fully assembled (head + stack). */
    public boolean isComplete() {
        return type == PieceType.SNOWMAN_RED
            || type == PieceType.SNOWMAN_BLUE
            || type == PieceType.SNOWMAN_YELLOW;
    }

    // ── Object overrides ──────────────────────────────────────────────────

    @Override
    public String toString() {
        return type + "@(" + row + "," + col + ")";
    }
}
