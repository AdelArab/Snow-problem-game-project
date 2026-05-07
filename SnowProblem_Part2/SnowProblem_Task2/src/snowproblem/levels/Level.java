package snowproblem.levels;

import snowproblem.model.GameBoard;
import snowproblem.model.Piece;
import snowproblem.model.PieceType;

/** Represents a single level layout. */
public class Level {

    private final int number;
    private final String name;
    private final String[] mapRows;

    public Level(int number, String name, String row0, String row1, String row2, String row3) {
        this.number = number;
        this.name = name;
        this.mapRows = new String[] {row0, row1, row2, row3};
    }

    public int getNumber() {
        return number;
    }

    public Piece[][] buildGrid() {
        Piece[][] grid = new Piece[GameBoard.ROWS][GameBoard.COLS];

        for (int r = 0; r < GameBoard.ROWS; r++) {
            if (mapRows[r].length() != GameBoard.COLS) {
                throw new IllegalArgumentException("Each row must contain exactly 5 characters.");
            }

            for (int c = 0; c < GameBoard.COLS; c++) {
                char ch = mapRows[r].charAt(c);

                if (ch == 'T') {
                    grid[r][c] = new Piece(PieceType.TREE);
                } else if (ch == 'L') {
                    grid[r][c] = new Piece(PieceType.LARGE_SNOWBALL);
                } else if (ch == 'S') {
                    grid[r][c] = new Piece(PieceType.SMALL_SNOWBALL);
                } else if (ch == 'B') {
                    grid[r][c] = new Piece(PieceType.SNOWMAN_HEAD, "blue");
                } else if (ch == 'R') {
                    grid[r][c] = new Piece(PieceType.SNOWMAN_HEAD, "red");
                } else if (ch == 'Y') {
                    grid[r][c] = new Piece(PieceType.SNOWMAN_HEAD, "yellow");
                }
            }
        }

        return grid;
    }

    @Override
    public String toString() {
        return "Level " + number + ": " + name;
    }
}
