package snowproblem.model;

/** Represents one item placed on the game board. */
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
}
