package snowproblem.model;

/**
 * Represents one piece on the Snow Problem board.
 * In Task 2, only large and small snowballs are movable.
 */
public class Piece {

    private final PieceType type;
    private final String color;

    public Piece(PieceType type) {
        this(type, "blue");
    }

    public Piece(PieceType type, String color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public boolean canMove() {
        return type == PieceType.LARGE_SNOWBALL || type == PieceType.SMALL_SNOWBALL;
    }
}
