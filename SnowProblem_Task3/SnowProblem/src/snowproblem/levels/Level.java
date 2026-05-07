package snowproblem.levels;

import snowproblem.model.Piece;
import snowproblem.model.PieceType;

/**
 * A Level stores the initial configuration of pieces for a single puzzle.
 * Each level is defined by its number, name, snowmen required to win,
 * and the starting positions of all pieces on the 5×4 grid.
 *
 * Positions are given as {row, col} pairs (0-indexed, row 0 = top).
 */
public class Level {

    private final int number;
    private final String name;
    private final int snowmenRequired;

    // Piece starting positions: each int[] is {row, col}
    private final int[][] trees;
    private final int[][] largeSnowballs;
    private final int[][] smallSnowballs;
    private final int[][] snowmanHeads;
    private final String headColor;

    public Level(int number, String name, int snowmenRequired,
             int[][] trees, int[][] largeSnowballs,
             int[][] smallSnowballs, int[][] snowmanHeads,
             String headColor) {
        this.number         = number;
        this.name           = name;
        this.snowmenRequired = snowmenRequired;
        this.trees          = trees;
        this.largeSnowballs = largeSnowballs;
        this.smallSnowballs = smallSnowballs;
        this.snowmanHeads   = snowmanHeads;
        this.headColor = headColor;
    }

    public int getNumber()          { return number; }
    public String getName()         { return name; }
    public int getSnowmenRequired() { return snowmenRequired; }
    public String getHeadColor()    { return headColor; }

    /**
     * Builds and returns a fresh grid for this level.
     * Called each time the level starts or resets.
     */
    public Piece[][] buildGrid() {
        Piece[][] grid = new Piece[4][5];

        place(grid, trees,          PieceType.TREE);
        place(grid, largeSnowballs, PieceType.LARGE_SNOWBALL);
        place(grid, smallSnowballs, PieceType.SMALL_SNOWBALL);
        placeHeads(grid);

        return grid;
    }

    private void placeHeads(Piece[][] grid) {
    if (snowmanHeads == null) return;

    for (int[] pos : snowmanHeads) {
        int row = pos[0];
        int col = pos[1];

        String color = headColor;

        if (pos.length >= 3) {
            if (pos[2] == 0) {
                color = "blue";
            } else if (pos[2] == 1) {
                color = "red";
            } else if (pos[2] == 2) {
                color = "yellow";
            }
        }

        grid[row][col] = new Piece(PieceType.SNOWMAN_HEAD, color);
    }
}

    private void place(Piece[][] grid, int[][] positions, PieceType type) {
        if (positions == null) return;
        for (int[] pos : positions) {
            grid[pos[0]][pos[1]] = new Piece(type);
        }
    }

    public static Level fromMap(int number, String name, int snowmenRequired, String headColor,
                            String row0, String row1, String row2, String row3) {
        java.util.List<int[]> trees = new java.util.ArrayList<>();
        java.util.List<int[]> largeSnowballs = new java.util.ArrayList<>();
        java.util.List<int[]> smallSnowballs = new java.util.ArrayList<>();
        java.util.List<int[]> snowmanHeads = new java.util.ArrayList<>();

        String[] rows = {row0, row1, row2, row3};

        for (int r = 0; r < rows.length; r++) {
            if (rows[r].length() != 5) {
                throw new IllegalArgumentException("Each level row must have exactly 5 characters.");
            }

            for (int c = 0; c < rows[r].length(); c++) {
                char ch = rows[r].charAt(c);

                if (ch == 'T') {
                    trees.add(new int[]{r, c});
                } else if (ch == 'L') {
                    largeSnowballs.add(new int[]{r, c});
                } else if (ch == 'S') {
                    smallSnowballs.add(new int[]{r, c});
                } else if (ch == 'H') {
                    snowmanHeads.add(new int[]{r, c});
                } else if (ch == 'B') {
                    snowmanHeads.add(new int[]{r, c, 0});
                } else if (ch == 'R') {
                    snowmanHeads.add(new int[]{r, c, 1});
                } else if (ch == 'Y') {
                    snowmanHeads.add(new int[]{r, c, 2});
                } else if (ch == '.') {
    // empty cell
                } else {
                    throw new IllegalArgumentException("Unknown level symbol: " + ch);
                    }
                }
            }

        return new Level(
            number,
            name,
            snowmenRequired,
            toArray(trees),
            toArray(largeSnowballs),
            toArray(smallSnowballs),
            toArray(snowmanHeads),
            headColor
        );
    }

private static int[][] toArray(java.util.List<int[]> list) {
    return list.toArray(new int[list.size()][]);
}

    @Override
    public String toString() {
        return "Level " + number + ": " + name;
    }
}
