package snowproblem.model;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    // Gives the column change for left and right movement.
    public int dc() {
        if (this == LEFT) {
            return -1;
        } else if (this == RIGHT) {
            return 1;
        } else {
            return 0;
        }
    }

    // Gives the row change for up and down movement.
    public int dr() {
        if (this == UP) {
            return -1;
        } else if (this == DOWN) {
            return 1;
        } else {
            return 0;
        }
    }
}