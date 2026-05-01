package snowproblem.model;

/**
 * Represents a single piece on the Snow Problem board.
 */
public class Piece {

    private final PieceType type;

    public Piece(PieceType type) {
        this.type = type;
    }

    public PieceType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.name();
    }
}
