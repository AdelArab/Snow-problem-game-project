package snowproblem.model;

/**
 * Represents a single piece on the Snow Problem board.
 * Pieces can be trees, large snowballs, small snowballs, or snowman heads.
 * Pieces may be stacked: a small snowball on a large snowball, or a head on that stack.
 */
public class Piece {

    private final PieceType type;
    private final String color;

    /** The piece resting on top of this one (null if nothing stacked). */
    private Piece stackedOn;

    /** Whether this piece is the bottom of a stack (i.e. something is on it). */
    private boolean hasStack;

    public Piece(PieceType type) {
        this(type, null);
}

public Piece(PieceType type, String color) {
    this.type = type;
    this.color = color;
    this.stackedOn = null;
    this.hasStack = false;
}
    

    public PieceType getType() {
        return type;
    }
    public String getColor() {
        return color == null ? "blue" : color;
}

    /**
     * Returns true if this piece can slide around the board.
     * Only un-stacked large and small snowballs can move.
     */
    public boolean canMove() {
        return (type == PieceType.LARGE_SNOWBALL || type == PieceType.SMALL_SNOWBALL)
                && !hasStack;
    }

    /** Returns true if another piece is resting on top of this one. */
    public boolean hasStack() {
        return hasStack;
    }

    public void setHasStack(boolean hasStack) {
        this.hasStack = hasStack;
    }

    public Piece getStackedOn() {
        return stackedOn;
    }

    public void setStackedOn(Piece piece) {
        this.stackedOn = piece;
        this.hasStack = true;
    }

    /**
     * Returns true if this piece is a complete snowman
     * (large snowball → small snowball → head).
     */
    public boolean isCompleteSnowman() {
        return type == PieceType.LARGE_SNOWBALL
                && stackedOn != null
                && stackedOn.getType() == PieceType.SMALL_SNOWBALL
                && stackedOn.getStackedOn() != null
                && stackedOn.getStackedOn().getType() == PieceType.SNOWMAN_HEAD;
    }

    @Override
    public String toString() {
        return type.name() + (hasStack ? "[STACKED]" : "");
    }
}
