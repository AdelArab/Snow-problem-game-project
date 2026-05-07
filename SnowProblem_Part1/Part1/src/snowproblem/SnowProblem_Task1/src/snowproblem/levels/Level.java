package snowproblem.levels;

import snowproblem.model.GameBoard;
import snowproblem.model.Piece;
import snowproblem.model.PieceType;

/** Stores the starting layout for a level. */
public class Level {
    private final int number;
    private final String name;
    private final int snowmenRequired;
    private final int[][] trees;
    private final int[][] largeSnowballs;
    private final int[][] smallSnowballs;
    private final int[][] snowmanHeads;
    private final String headColor;

    public Level(int number, String name, int snowmenRequired,
                 int[][] trees, int[][] largeSnowballs,
                 int[][] smallSnowballs, int[][] snowmanHeads,
                 String headColor) {
        this.number = number;
        this.name = name;
        this.snowmenRequired = snowmenRequired;
        this.trees = trees;
        this.largeSnowballs = largeSnowballs;
        this.smallSnowballs = smallSnowballs;
        this.snowmanHeads = snowmanHeads;
        this.headColor = headColor;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getSnowmenRequired() {
        return snowmenRequired;
    }

    /** Builds a fresh board from the level's starting positions. */
    public GameBoard buildBoard() {
        Piece[][] grid = new Piece[GameBoard.ROWS][GameBoard.COLS];
        place(grid, trees, PieceType.TREE);
        place(grid, largeSnowballs, PieceType.LARGE_SNOWBALL);
        place(grid, smallSnowballs, PieceType.SMALL_SNOWBALL);
        placeHeads(grid);
        return new GameBoard(grid, snowmenRequired);
    }

    private void place(Piece[][] grid, int[][] positions, PieceType type) {
        if (positions == null) return;
        for (int[] pos : positions) {
            grid[pos[0]][pos[1]] = new Piece(type);
        }
    }

    private void placeHeads(Piece[][] grid) {
        if (snowmanHeads == null) return;
        for (int[] pos : snowmanHeads) {
            grid[pos[0]][pos[1]] = new Piece(PieceType.SNOWMAN_HEAD, headColor);
        }
    }

    @Override
    public String toString() {
        return "Level " + number + ": " + name;
    }
}
