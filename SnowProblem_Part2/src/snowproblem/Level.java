package snowproblem;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the immutable starting configuration for a single game level.
 *
 * Each level stores:
 *  - its number (1-based)
 *  - the par (minimum moves to solve)
 *  - the list of pieces at their starting positions
 *
 * getInitialPieces() always returns deep copies so the GameBoard cannot
 * corrupt the original level data.
 *
 * Static factory buildAllLevels() returns all 80 levels in order.
 * For Parts 1 & 2 only Level 1 is needed, but the full list is available
 * for later tasks.
 */
public class Level {

    private final int         levelNumber;
    private final int         minMoves;
    private final List<Piece> initialPieces;

    /**
     * @param levelNumber  1-based level index
     * @param minMoves     par (fewest moves needed to solve)
     * @param initialPieces starting pieces (this constructor copies them)
     */
    public Level(int levelNumber, int minMoves, List<Piece> initialPieces) {
        this.levelNumber   = levelNumber;
        this.minMoves      = minMoves;
        this.initialPieces = deepCopy(initialPieces);
    }

    // ── Getters ───────────────────────────────────────────────────────────

    public int getLevelNumber() { return levelNumber; }
    public int getMinMoves()    { return minMoves;    }

    /**
     * Returns a fresh deep copy of the initial piece list.
     * Callers can freely mutate the returned list without affecting the Level.
     */
    public List<Piece> getInitialPieces() {
        return deepCopy(initialPieces);
    }

    // ── Static factory ────────────────────────────────────────────────────

    /**
     * Builds and returns the complete list of all 80 game levels.
     *
     * Piece descriptor format used by the private make() helper:
     *   "X,row,col"  where X is one of:
     *     T = Tree
     *     L = Large snowball
     *     S = Small snowball
     *     R = Head (red)
     *     B = Head (blue)
     *     Y = Head (yellow)
     *
     * Board coordinates: row 0 = top, col 0 = left.
     */
    public static List<Level> buildAllLevels() {
        List<Level> levels = new ArrayList<>();

        // ── STARTER (1-16) ────────────────────────────────────────────────

        // Level 1 »4
        levels.add(make(1, 4,
            "S,0,1",
            "B,2,0",
            "L,3,3"
        ));

        // Level 2 »4
        levels.add(make(2, 4,
            "L,1,0",
            "T,2,1",
            "Y,1,3",
            "S,2,3"
        ));

        // Level 3 »5
        levels.add(make(3, 5,
            "T,0,0",
            "S,0,2",
            "S,1,2",
            "R,2,0",
            "T,3,1"
        ));

        // Level 4 »5
        levels.add(make(4, 5,
            "T,0,0",
            "S,0,4",
            "Y,3,1",
            "L,3,4"
        ));

        // Level 5 »6
        levels.add(make(5, 6,
            "T,0,0", "T,0,2", "T,0,3",
            "B,1,0",
            "S,3,0", "L,3,3"
        ));

        // Level 6 »6
        levels.add(make(6, 6,
            "T,0,2",
            "B,1,0", "S,1,2", "L,1,3",
            "T,2,3"
        ));

        // Level 7 »7
        levels.add(make(7, 7,
            "T,1,0", "T,1,1",
            "R,2,3",
            "S,3,0", "L,3,1"
        ));

        // Level 8 »7
        levels.add(make(8, 7,
            "T,0,0", "S,0,2", "L,0,3",
            "T,1,4",
            "B,3,3"
        ));

        // Level 9 »7
        levels.add(make(9, 7,
            "T,0,0", "S,0,2",
            "R,1,3",
            "T,2,3", "S,2,0", "L,2,4"
        ));

        // Level 10 »7
        levels.add(make(10, 7,
            "T,0,0", "T,0,1", "T,0,2",
            "R,2,3",
            "S,3,1", "L,3,3"
        ));

        // Level 11 »7
        levels.add(make(11, 7,
            "T,0,0", "T,0,1", "Y,0,2",
            "T,2,1",
            "S,2,0", "L,3,0", "S,3,2"
        ));

        // Level 12 »7
        levels.add(make(12, 7,
            "T,0,0", "S,0,1", "T,0,3",
            "B,2,1", "S,2,3", "L,3,4"
        ));

        // Level 13 »9
        levels.add(make(13, 9,
            "T,0,1", "S,0,3",
            "T,1,0", "R,1,3",
            "L,2,3",
            "S,3,4"
        ));

        // Level 14 »9
        levels.add(make(14, 9,
            "T,0,1", "S,0,4",
            "T,1,2",
            "R,2,1", "L,2,3", "S,2,4"
        ));

        // Level 15 »9
        levels.add(make(15, 9,
            "T,0,0", "T,0,1",
            "B,1,3",
            "T,3,1", "S,2,0", "L,3,4"
        ));

        // Level 16 »10
        levels.add(make(16, 10,
            "T,0,0", "T,0,1",
            "B,1,3",
            "T,2,2", "S,2,0", "L,3,1", "S,3,3"
        ));

        // ── JUNIOR (17-28) ────────────────────────────────────────────────

        levels.add(make(17, 12, "T,0,1","T,1,0","T,1,3","S,2,1","R,2,2","L,2,3"));
        levels.add(make(18, 10, "T,0,2","S,0,3","L,0,4","T,2,2","B,3,1","S,3,4"));
        levels.add(make(19, 12, "T,0,1","T,1,0","T,1,2","S,0,3","Y,2,0","L,2,2","S,3,2"));
        levels.add(make(20, 11, "T,1,0","Y,1,2","T,3,2","S,2,0","L,3,0","S,3,4"));
        levels.add(make(21, 11, "T,0,0","T,1,2","B,0,2","S,2,0","L,2,2","S,3,4"));
        levels.add(make(22, 11, "T,0,1","T,1,2","S,0,3","B,1,0","R,2,1","L,2,4","S,3,4"));
        levels.add(make(23, 13, "T,0,2","T,1,0","T,2,0","B,1,2","S,3,0","L,3,2","S,3,4"));
        levels.add(make(24, 12, "T,0,0","T,1,3","B,0,2","S,2,0","L,3,0","S,3,3"));
        levels.add(make(25, 12, "T,0,0","T,0,1","S,0,3","T,2,1","B,2,2","L,3,0","S,3,4"));
        levels.add(make(26, 12, "T,0,0","S,0,2","T,1,2","T,2,4","R,3,2","L,3,4","S,2,0"));
        levels.add(make(27, 18, "T,0,1","T,1,2","R,2,0","T,3,2","S,0,3","L,2,3","S,3,3"));
        levels.add(make(28, 11, "T,0,0","T,1,2","T,2,2","B,3,2","S,2,0","L,3,0","S,3,4"));

        // ── EXPERT (29-48) ────────────────────────────────────────────────

        levels.add(make(29,  8, "Y,1,1","B,1,2","S,2,0","L,2,2","S,2,3","L,2,4"));
        levels.add(make(30, 10, "R,0,0","T,0,2","S,0,4","L,1,4","Y,2,1","T,2,2","S,3,0","L,3,2"));
        levels.add(make(31, 11, "T,0,1","B,0,2","R,0,3","S,0,0","L,0,4","T,1,0","S,3,1","L,3,4"));
        levels.add(make(32, 12, "T,0,2","R,0,1","S,0,3","L,0,4","B,1,1","T,1,2","Y,2,1","S,3,1","L,3,3","S,3,4"));
        levels.add(make(33,  9, "S,0,0","R,0,1","Y,0,2","L,0,4","S,2,1","L,2,3","S,3,4"));
        levels.add(make(34, 13, "S,0,0","B,0,2","S,0,3","L,0,4","T,1,1","Y,2,2","T,3,1","S,3,3","L,3,4"));
        levels.add(make(35, 10, "T,0,0","T,0,1","S,0,2","Y,0,3","L,1,4","R,3,1","S,3,0","L,3,2"));
        levels.add(make(36, 11, "S,0,0","Y,0,1","T,1,1","T,2,2","R,3,0","S,3,1","L,3,3","S,3,4"));
        levels.add(make(37, 11, "Y,0,0","S,0,2","L,0,3","T,1,2","B,1,3","S,2,0","L,2,2","S,3,4"));
        levels.add(make(38, 10, "S,0,0","L,0,1","Y,0,2","B,0,3","T,0,4","T,1,1","S,2,1","L,2,3","S,3,4"));
        levels.add(make(39, 10, "B,0,0","T,1,2","S,1,0","L,2,0","R,3,0","S,3,2","L,3,4"));
        levels.add(make(40,  9, "T,0,0","B,0,1","S,0,2","L,0,3","R,3,1","S,2,0","L,3,4","S,3,2"));
        levels.add(make(41, 11, "S,0,0","L,0,4","B,1,1","Y,2,1","S,2,0","S,3,0","L,3,2"));
        levels.add(make(42, 12, "S,0,0","L,0,4","R,1,1","Y,2,1","S,2,0","L,3,2","S,3,4"));
        levels.add(make(43, 12, "S,0,0","B,0,1","T,0,2","L,0,4","R,3,0","S,3,2","L,3,4"));
        levels.add(make(44, 10, "T,1,1","T,1,2","R,2,2","S,2,0","L,3,0","S,3,2","L,3,4"));
        levels.add(make(45, 14, "T,0,1","B,1,0","T,2,1","Y,2,2","S,2,0","L,3,0","S,3,2","L,3,4"));
        levels.add(make(46, 10, "S,0,0","L,0,1","T,0,2","S,0,3","B,0,4","T,1,1","T,1,2","Y,2,1","L,2,4","S,3,4"));
        levels.add(make(47, 15, "S,0,0","R,0,2","L,0,3","T,1,1","T,1,2","B,3,0","S,3,2","L,3,4"));
        levels.add(make(48, 15, "T,0,0","S,0,2","L,0,4","T,1,2","B,3,0","Y,3,1","R,3,2","S,3,3","L,3,4"));

        // ── MASTER (49-64) ────────────────────────────────────────────────

        levels.add(make(49, 15, "S,0,0","L,0,1","S,0,2","T,0,3","T,1,1","Y,1,2","R,2,2","B,2,3","S,3,0","L,3,4"));
        levels.add(make(50, 18, "Y,0,0","S,0,1","L,0,2","T,0,4","T,1,2","R,3,1","S,2,0","L,3,0","S,3,4","B,0,3"));
        levels.add(make(51, 24, "S,0,0","L,0,1","R,0,2","T,1,0","T,2,1","B,2,2","Y,3,1","S,2,0","L,3,0","S,3,2","L,3,4"));
        levels.add(make(52, 17, "T,0,0","S,0,1","L,0,3","B,0,4","S,2,0","R,2,3","Y,3,2","L,3,3","S,3,4"));
        levels.add(make(53, 17, "S,0,0","T,0,2","Y,1,1","B,1,2","T,2,0","S,3,0","L,3,2","S,3,4"));
        levels.add(make(54, 13, "S,0,0","L,0,2","T,0,3","B,0,4","T,1,2","R,1,4","S,2,0","L,2,2","S,2,4"));
        levels.add(make(55, 13, "S,0,0","T,0,1","R,0,2","B,0,3","L,0,4","T,2,2","S,3,0","L,3,2","S,3,4"));
        levels.add(make(56, 14, "T,0,1","T,0,2","S,0,3","Y,1,2","R,2,2","S,3,0","L,3,2","S,3,4"));
        levels.add(make(57, 22, "T,0,1","T,0,2","T,1,0","S,1,2","R,1,3","T,2,2","S,3,0","L,3,2","S,3,4"));
        levels.add(make(58, 27, "T,0,1","S,0,2","Y,0,3","T,2,1","T,3,1","S,2,3","R,3,3","L,3,4","S,3,0"));
        levels.add(make(59, 15, "S,0,0","L,0,2","T,1,1","T,1,2","B,2,0","R,2,2","S,3,0","L,3,4"));
        levels.add(make(60, 14, "S,0,0","L,0,1","B,0,4","T,1,2","T,2,2","R,2,3","S,3,0","L,3,4"));
        levels.add(make(61, 21, "Y,0,0","R,0,1","L,0,3","S,0,4","B,1,0","S,2,0","L,2,2","S,3,0","L,3,4"));
        levels.add(make(62, 31, "S,0,0","L,0,1","L,0,2","S,0,3","B,0,4","T,1,0","T,1,1","Y,1,4","R,2,3","S,3,0","L,3,4"));
        levels.add(make(63, 17, "S,0,1","T,0,2","L,0,4","T,1,2","R,2,0","Y,2,1","S,3,0","L,3,2","S,3,4"));
        levels.add(make(64, 17, "S,0,0","B,0,1","Y,0,2","T,1,3","R,2,1","T,2,3","S,3,0","L,3,2","S,3,4"));

        // ── WIZARD (65-80) ────────────────────────────────────────────────

        levels.add(make(65, 23, "Y,0,0","B,0,1","T,1,0","S,1,1","L,1,2","T,2,3","R,3,2","S,3,0","L,3,4"));
        levels.add(make(66, 22, "Y,0,0","S,0,3","L,0,4","R,2,2","S,3,2","L,3,3","S,3,4"));
        levels.add(make(67, 21, "T,0,0","S,0,2","L,0,4","T,1,4","R,2,0","B,3,1","S,3,0","L,3,2","S,3,4"));
        levels.add(make(68, 16, "T,0,0","T,0,1","Y,0,2","T,1,0","S,1,3","L,1,4","R,3,2","S,3,0","L,3,4"));
        levels.add(make(69, 25, "S,0,0","R,0,2","B,0,3","L,0,4","T,1,0","T,2,1","Y,2,2","S,3,0","L,3,2","S,3,4"));
        levels.add(make(70, 20, "T,0,0","S,0,2","L,0,3","L,0,4","Y,2,2","R,3,1","B,3,3","S,3,0","S,3,4"));
        levels.add(make(71, 19, "S,0,0","T,0,2","B,1,0","T,1,2","R,2,0","S,3,0","L,3,2","S,3,4"));
        levels.add(make(72, 20, "S,0,0","L,0,1","S,0,3","L,0,4","T,1,0","R,1,2","B,2,2","S,3,0","L,3,4"));
        levels.add(make(73, 18, "Y,0,0","S,0,2","L,0,4","S,2,0","L,2,3","B,3,2","S,3,4"));
        levels.add(make(74, 21, "T,0,0","S,0,1","L,0,2","S,1,1","L,1,2","R,2,2","T,3,4","S,3,0","L,3,2"));
        levels.add(make(75, 24, "S,0,0","T,0,1","L,0,4","R,2,2","T,3,1","S,3,0","L,3,2","S,3,4"));
        levels.add(make(76, 27, "T,0,2","S,0,3","B,0,0","L,1,3","T,2,2","R,3,2","S,3,0","L,3,4"));
        levels.add(make(77, 25, "T,0,0","S,0,1","L,0,2","L,0,3","R,1,0","T,2,1","B,2,2","Y,3,2","S,3,0","S,3,4"));
        levels.add(make(78, 26, "B,0,0","Y,0,1","R,0,2","S,0,3","L,0,4","S,2,0","L,2,2","S,3,0","L,3,4"));
        levels.add(make(79, 31, "T,0,0","R,0,2","S,0,3","B,1,0","Y,2,0","S,1,2","L,2,2","S,3,0","L,3,2","S,3,4"));
        levels.add(make(80, 44, "S,0,0","L,0,1","T,0,2","B,0,4","T,1,2","Y,1,4","R,3,0","T,3,2","S,3,3","L,3,4"));

        return levels;
    }

    // ── Private helpers ───────────────────────────────────────────────────

    /** Creates a Level from varargs piece descriptors "TYPE,row,col". */
    private static Level make(int num, int minMoves, String... specs) {
        List<Piece> pieces = new ArrayList<>();
        for (String spec : specs) {
            String[]  parts = spec.split(",");
            PieceType type;
            switch (parts[0]) {
                case "T": type = PieceType.TREE;           break;
                case "L": type = PieceType.LARGE_SNOWBALL; break;
                case "S": type = PieceType.SMALL_SNOWBALL; break;
                case "R": type = PieceType.HEAD_RED;       break;
                case "B": type = PieceType.HEAD_BLUE;      break;
                case "Y": type = PieceType.HEAD_YELLOW;    break;
                default: continue;
            }
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);
            pieces.add(new Piece(type, row, col));
        }
        return new Level(num, minMoves, pieces);
    }

    /** Deep-copies a list of pieces. */
    private static List<Piece> deepCopy(List<Piece> src) {
        List<Piece> copy = new ArrayList<>(src.size());
        for (Piece p : src) {
            copy.add(new Piece(p.getType(), p.getRow(), p.getCol()));
        }
        return copy;
    }
}
