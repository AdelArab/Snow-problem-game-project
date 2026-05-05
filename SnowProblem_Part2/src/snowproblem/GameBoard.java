package snowproblem;

import java.util.ArrayList;
import java.util.List;

/**
 * Models the Snow Problem game board (5 columns × 4 rows) and enforces all game rules.
 *
 * Coordinate system:
 *   row 0 = top row,   row 3 = bottom row
 *   col 0 = left col,  col 4 = right col
 *
 * The board is represented as both:
 *   - a 2-D array (grid) for O(1) position look-ups
 *   - a List<Piece>       for easy iteration
 *
 * Game rules enforced here:
 *   1. Snowballs slide until blocked by another piece or the board edge.
 *   2. A snowball that would slide off the board is removed and the game ends.
 *   3. A small snowball colliding with a large snowball forms a SNOWBALL_STACK.
 *   4. A head colliding with a SNOWBALL_STACK forms a complete SNOWMAN_*.
 *   5. A head can also be placed onto an adjacent stack via stackHead().
 *   6. Stacks and complete snowmen cannot move.
 *   7. Trees cannot move.
 *   8. The game is won when no loose snowballs, stacks, or heads remain.
 */
public class GameBoard {

    /** Width of the board (columns). */
    public static final int COLS = 5;

    /** Height of the board (rows). */
    public static final int ROWS = 4;

    // Internal state
    private Piece[][]   grid;     // grid[row][col] – null if empty
    private List<Piece> pieces;   // all pieces currently on the board
    private boolean     gameOver; // true if a snowball flew off
    private boolean     gameWon;  // true if all snowmen are complete
    private int         moveCount;

    // ── Construction ──────────────────────────────────────────────────────

    /** Creates an empty board. */
    public GameBoard() {
        grid      = new Piece[ROWS][COLS];
        pieces    = new ArrayList<>();
        gameOver  = false;
        gameWon   = false;
        moveCount = 0;
    }

    // ── Board population ──────────────────────────────────────────────────

    /**
     * Places a piece onto the board at its current (row, col) position.
     * Throws IllegalArgumentException if the position is out-of-bounds or already occupied.
     *
     * @param p the piece to add
     */
    public void addPiece(Piece p) {
        if (!inBounds(p.getRow(), p.getCol())) {
            throw new IllegalArgumentException(
                "Position out of bounds: (" + p.getRow() + "," + p.getCol() + ")");
        }
        if (grid[p.getRow()][p.getCol()] != null) {
            throw new IllegalArgumentException(
                "Cell already occupied: (" + p.getRow() + "," + p.getCol() + ")");
        }
        grid[p.getRow()][p.getCol()] = p;
        pieces.add(p);
    }

    // ── Queries ───────────────────────────────────────────────────────────

    /**
     * Returns the piece at (row, col), or null if the cell is empty.
     * Returns null for out-of-bounds coordinates.
     */
    public Piece getPiece(int row, int col) {
        if (!inBounds(row, col)) return null;
        return grid[row][col];
    }

    /** Returns true if (row, col) is a valid board position. */
    public boolean inBounds(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    /** Returns true if (row, col) is within bounds and holds a piece. */
    public boolean isOccupied(int row, int col) {
        return inBounds(row, col) && grid[row][col] != null;
    }

    /** Returns an unmodifiable view of all pieces currently on the board. */
    public List<Piece> getPieces() { return pieces; }

    /** Returns the raw 2-D grid array (may contain nulls). */
    public Piece[][] getGrid()     { return grid; }

    public int     getMoveCount() { return moveCount; }
    public boolean isGameOver()   { return gameOver;  }
    public boolean isGameWon()    { return gameWon;   }

    // ── Movement ──────────────────────────────────────────────────────────

    /**
     * Attempts to slide the given piece in the requested direction.
     *
     * Direction constants:
     *   0 = UP    (decreasing row)
     *   1 = DOWN  (increasing row)
     *   2 = LEFT  (decreasing col)
     *   3 = RIGHT (increasing col)
     *
     * @param piece     the piece to move (must be a movable snowball)
     * @param direction 0-3 as above
     * @return true if any change to the board state occurred
     */
    public boolean movePiece(Piece piece, int direction) {
        if (gameOver || gameWon)   return false;
        if (!piece.isMovable())    return false;

        int dr = 0, dc = 0;
        switch (direction) {
            case 0: dr = -1; break; // UP
            case 1: dr =  1; break; // DOWN
            case 2: dc = -1; break; // LEFT
            case 3: dc =  1; break; // RIGHT
            default: return false;
        }

        int startRow = piece.getRow();
        int startCol = piece.getCol();

        // Slide until we hit a piece or leave the board
        int r = startRow + dr;
        int c = startCol + dc;
        while (inBounds(r, c) && !isOccupied(r, c)) {
            r += dr;
            c += dc;
        }

        // Last empty cell the piece can reach
        int landRow = r - dr;
        int landCol = c - dc;

        // Piece didn't move at all (immediately blocked)
        if (landRow == startRow && landCol == startCol) return false;

        // ── Case 1: piece slid off the board ─────────────────────────────
        if (!inBounds(r, c)) {
            grid[startRow][startCol] = null;
            pieces.remove(piece);
            gameOver = true;
            return true;
        }

        // ── Case 2: piece collided with an obstacle ───────────────────────
        Piece obstacle = grid[r][c];
        if (tryStack(piece, obstacle)) {
            // Mover was absorbed into the obstacle cell
            grid[startRow][startCol] = null;
            pieces.remove(piece);
            moveCount++;
            checkWin();
            return true;
        }

        // ── Case 3: normal slide, land one cell before obstacle ───────────
        grid[startRow][startCol] = null;
        piece.setRow(landRow);
        piece.setCol(landCol);
        grid[landRow][landCol] = piece;
        moveCount++;
        checkWin();
        return true;
    }

    /**
     * Attempts to place a head piece directly onto an adjacent snowball stack.
     * The head must be orthogonally adjacent (distance 1) to the stack.
     *
     * @param head  the head piece to place
     * @param stack the target SNOWBALL_STACK piece
     * @return true if the stack was completed successfully
     */
    public boolean stackHead(Piece head, Piece stack) {
        if (gameOver || gameWon)  return false;
        if (!head.isHead())       return false;
        if (!stack.isStack())     return false;

        // Must be orthogonally adjacent
        int dr = Math.abs(head.getRow() - stack.getRow());
        int dc = Math.abs(head.getCol() - stack.getCol());
        if (dr + dc != 1)         return false;

        // Upgrade the stack to a complete snowman
        grid[head.getRow()][head.getCol()] = null;
        pieces.remove(head);
        promoteStack(stack, head.getType());
        moveCount++;
        checkWin();
        return true;
    }

    /**
     * Resets the board to the supplied initial piece configuration.
     * A fresh copy of each piece is made so the original Level data is not mutated.
     *
     * @param initialPieces the starting pieces for a level
     */
    public void reset(List<Piece> initialPieces) {
        grid      = new Piece[ROWS][COLS];
        pieces    = new ArrayList<>();
        gameOver  = false;
        gameWon   = false;
        moveCount = 0;
        for (Piece p : initialPieces) {
            addPiece(new Piece(p.getType(), p.getRow(), p.getCol()));
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────

    /**
     * Tries to stack 'mover' onto 'target'.
     * Updates target's type in-place if successful.
     *
     * @return true if a stacking occurred
     */
    private boolean tryStack(Piece mover, Piece target) {
        if (target == null) return false;

        // Small snowball lands on large snowball → stack
        if (mover.getType() == PieceType.SMALL_SNOWBALL
                && target.getType() == PieceType.LARGE_SNOWBALL) {
            target.setType(PieceType.SNOWBALL_STACK);
            return true;
        }

        // Head lands on stack → complete snowman
        if (mover.isHead() && target.isStack()) {
            promoteStack(target, mover.getType());
            return true;
        }

        return false;
    }

    /** Converts a SNOWBALL_STACK to the appropriate complete snowman type. */
    private void promoteStack(Piece stack, PieceType headType) {
        switch (headType) {
            case HEAD_RED:    stack.setType(PieceType.SNOWMAN_RED);    break;
            case HEAD_BLUE:   stack.setType(PieceType.SNOWMAN_BLUE);   break;
            case HEAD_YELLOW: stack.setType(PieceType.SNOWMAN_YELLOW); break;
            default: break;
        }
    }

    /**
     * Checks whether all snowball/head/stack pieces have been resolved into
     * complete snowmen. If so, sets gameWon = true.
     */
    private void checkWin() {
        for (Piece p : pieces) {
            if (p.isMovable() || p.isStack() || p.isHead()) return;
        }
        gameWon = true;
    }
}
