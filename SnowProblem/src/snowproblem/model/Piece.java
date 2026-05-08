package snowproblem.model;

public class Piece {

    // Stores what kind of piece this is and the colour if it is a snowman head.
    private final PieceType type;
    private final String color;

    // Stores the piece placed on top of this piece during stacking.
    private Piece stackedOn;
    private boolean hasStack;

    // Creates a normal piece with no colour.
    public Piece(PieceType type) {
        this(type, null);
    }

    // Creates a piece with a colour, mainly used for snowman heads.
    public Piece(PieceType type, String color) {
        this.type = type;
        this.color = color;
        this.stackedOn = null;
        this.hasStack = false;
    }

    // Allows other classes to read the piece type and colour.
    public PieceType getType() {
        return type;
    }

    public String getColor() {
        if (color == null) {
            return "blue";
        } else {
            return color;
        }
    }

    // Checks if this piece is allowed to slide around the board.
    public boolean canMove() {
        return (type == PieceType.LARGE_SNOWBALL || type == PieceType.SMALL_SNOWBALL)
                && !hasStack;
    }

    // Checks if another piece is stacked on top of this one.
    public boolean hasStack() {
        return hasStack;
    }

    public Piece getStackedOn() {
        return stackedOn;
    }

    // Places another piece on top of this piece.
    public void setStackedOn(Piece piece) {
        this.stackedOn = piece;
        this.hasStack = true;
    }

    // Checks if this piece forms a full snowman stack.
    public boolean isCompleteSnowman() {
        return type == PieceType.LARGE_SNOWBALL
                && stackedOn != null
                && stackedOn.getType() == PieceType.SMALL_SNOWBALL
                && stackedOn.getStackedOn() != null
                && stackedOn.getStackedOn().getType() == PieceType.SNOWMAN_HEAD;
    }

    // Shows the piece name when printed, and marks it if it has a stack.
    @Override
    public String toString() {
        if (hasStack) {
            return type.name() + "[STACKED]";
        } else {
            return type.name();
        }
    }
}