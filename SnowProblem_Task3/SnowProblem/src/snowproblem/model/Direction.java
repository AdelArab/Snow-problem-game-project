package snowproblem.model;

/**
 * The four cardinal directions a snowball can be moved.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /** Returns the column delta for this direction. */
    public int dc() {
        return this == LEFT ? -1 : this == RIGHT ? 1 : 0;
    }

    /** Returns the row delta for this direction. */
    public int dr() {
        return this == UP ? -1 : this == DOWN ? 1 : 0;
    }
}
