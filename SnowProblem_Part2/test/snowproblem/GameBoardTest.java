package snowproblem;

import java.util.List;

/**
 * Unit tests for the Snow Problem data model (Part 1).
 *
 * This file uses no external test framework — every test is a plain Java
 * method.  Run via:
 *
 *   javac -d build src/snowproblem/*.java test/snowproblem/GameBoardTest.java
 *   java  -cp build snowproblem.GameBoardTest
 *
 * A summary is printed and the JVM exits with code 0 (pass) or 1 (fail).
 */
public class GameBoardTest {

    // ── Simple assertion helpers ──────────────────────────────────────────

    private static int passed = 0;
    private static int failed = 0;

    private static void assertTrue(String name, boolean condition) {
        if (condition) {
            System.out.println("  PASS  " + name);
            passed++;
        } else {
            System.out.println("  FAIL  " + name);
            failed++;
        }
    }

    private static void assertEqual(String name, Object expected, Object actual) {
        boolean ok = expected == null ? actual == null : expected.equals(actual);
        if (ok) {
            System.out.println("  PASS  " + name);
            passed++;
        } else {
            System.out.println("  FAIL  " + name + "  (expected=" + expected + " actual=" + actual + ")");
            failed++;
        }
    }

    // ── Test suites ───────────────────────────────────────────────────────

    /** Tests for the Piece class. */
    static void testPiece() {
        System.out.println("\n[Piece]");

        Piece large = new Piece(PieceType.LARGE_SNOWBALL, 0, 0);
        Piece small = new Piece(PieceType.SMALL_SNOWBALL, 1, 2);
        Piece tree  = new Piece(PieceType.TREE, 2, 3);
        Piece headR = new Piece(PieceType.HEAD_RED, 3, 4);
        Piece stack = new Piece(PieceType.SNOWBALL_STACK, 0, 1);
        Piece snowR = new Piece(PieceType.SNOWMAN_RED, 0, 2);

        // isMovable
        assertTrue("large snowball is movable",      large.isMovable());
        assertTrue("small snowball is movable",      small.isMovable());
        assertTrue("tree is NOT movable",           !tree.isMovable());
        assertTrue("head is NOT movable",           !headR.isMovable());
        assertTrue("stack is NOT movable",          !stack.isMovable());
        assertTrue("complete snowman NOT movable",  !snowR.isMovable());

        // isSnowball
        assertTrue("large isSnowball",   large.isSnowball());
        assertTrue("small isSnowball",   small.isSnowball());
        assertTrue("tree NOT isSnowball",!tree.isSnowball());

        // isHead
        assertTrue("HEAD_RED isHead",    headR.isHead());
        assertTrue("large NOT isHead",  !large.isHead());
        Piece headB = new Piece(PieceType.HEAD_BLUE, 0, 0);
        Piece headY = new Piece(PieceType.HEAD_YELLOW, 0, 0);
        assertTrue("HEAD_BLUE isHead",   headB.isHead());
        assertTrue("HEAD_YELLOW isHead", headY.isHead());

        // isStack / isComplete
        assertTrue("SNOWBALL_STACK isStack",   stack.isStack());
        assertTrue("large NOT isStack",       !large.isStack());
        assertTrue("SNOWMAN_RED isComplete",   snowR.isComplete());
        assertTrue("stack NOT isComplete",    !stack.isComplete());

        // setType mutation
        Piece mutable = new Piece(PieceType.LARGE_SNOWBALL, 0, 0);
        mutable.setType(PieceType.SNOWBALL_STACK);
        assertTrue("setType works", mutable.isStack());

        // position setters
        Piece pos = new Piece(PieceType.SMALL_SNOWBALL, 1, 1);
        pos.setRow(3); pos.setCol(4);
        assertEqual("setRow", 3, pos.getRow());
        assertEqual("setCol", 4, pos.getCol());
    }

    /** Tests for GameBoard.addPiece() and getPiece(). */
    static void testBoardAddAndGet() {
        System.out.println("\n[GameBoard – add/get]");

        GameBoard board = new GameBoard();
        Piece p = new Piece(PieceType.TREE, 1, 2);
        board.addPiece(p);

        assertTrue("getPiece returns piece",          board.getPiece(1, 2) == p);
        assertTrue("empty cell returns null",         board.getPiece(0, 0) == null);
        assertTrue("out-of-bounds returns null",      board.getPiece(-1, 0) == null);
        assertTrue("inBounds valid",                  board.inBounds(0, 0));
        assertTrue("inBounds invalid row",           !board.inBounds(4, 0));
        assertTrue("inBounds invalid col",           !board.inBounds(0, 5));
        assertTrue("isOccupied at (1,2)",             board.isOccupied(1, 2));
        assertTrue("isOccupied at empty (0,0)",      !board.isOccupied(0, 0));

        // Duplicate placement should throw
        boolean threw = false;
        try { board.addPiece(new Piece(PieceType.SMALL_SNOWBALL, 1, 2)); }
        catch (IllegalArgumentException e) { threw = true; }
        assertTrue("duplicate addPiece throws", threw);

        // Out-of-bounds placement should throw
        threw = false;
        try { board.addPiece(new Piece(PieceType.SMALL_SNOWBALL, 10, 10)); }
        catch (IllegalArgumentException e) { threw = true; }
        assertTrue("out-of-bounds addPiece throws", threw);
    }

    /** Tests for snowball sliding movement. */
    static void testMovement() {
        System.out.println("\n[GameBoard – movement]");

        // ── Slide to wall ────────────────────────────────────────────────
        GameBoard board = new GameBoard();
        Piece ball = new Piece(PieceType.LARGE_SNOWBALL, 2, 2);
        board.addPiece(ball);

        board.movePiece(ball, 3); // RIGHT → should land at col 4
        assertEqual("slide to right wall col", 4, ball.getCol());
        assertEqual("slide to right wall row", 2, ball.getRow());
        assertEqual("move count = 1", 1, board.getMoveCount());

        // ── Slide stopped by tree ────────────────────────────────────────
        GameBoard board2 = new GameBoard();
        Piece ball2 = new Piece(PieceType.LARGE_SNOWBALL, 0, 0);
        Piece tree  = new Piece(PieceType.TREE, 0, 3);
        board2.addPiece(ball2);
        board2.addPiece(tree);

        board2.movePiece(ball2, 3); // RIGHT → blocked by tree at col 3, lands at col 2
        assertEqual("blocked by tree", 2, ball2.getCol());

        // ── Already at wall, no move ─────────────────────────────────────
        GameBoard board3 = new GameBoard();
        Piece corner = new Piece(PieceType.SMALL_SNOWBALL, 0, 0);
        board3.addPiece(corner);
        boolean moved = board3.movePiece(corner, 0); // UP – already on top row
        assertTrue("no move at wall returns false", !moved);
        assertEqual("position unchanged row", 0, corner.getRow());

        // ── Tree cannot be moved ─────────────────────────────────────────
        GameBoard board4 = new GameBoard();
        Piece t = new Piece(PieceType.TREE, 1, 1);
        board4.addPiece(t);
        moved = board4.movePiece(t, 1); // DOWN
        assertTrue("tree cannot be moved", !moved);

        // ── Move count increments ─────────────────────────────────────────
        GameBoard board5 = new GameBoard();
        Piece b = new Piece(PieceType.LARGE_SNOWBALL, 2, 0);
        board5.addPiece(b);
        board5.movePiece(b, 3); // RIGHT
        board5.movePiece(b, 0); // UP
        assertEqual("move count after 2 moves", 2, board5.getMoveCount());
    }

    /** Tests for the game-over condition (snowball leaves the board). */
    static void testGameOver() {
        System.out.println("\n[GameBoard – game over]");

        GameBoard board = new GameBoard();
        Piece ball = new Piece(PieceType.SMALL_SNOWBALL, 0, 0);
        board.addPiece(ball);

        board.movePiece(ball, 0); // UP – slides off the top
        assertTrue("game is over after flying off", board.isGameOver());
        assertTrue("piece removed from list", !board.getPieces().contains(ball));
        assertTrue("cell is now null", board.getPiece(0, 0) == null);

        // No further moves accepted after game over
        GameBoard board2 = new GameBoard();
        Piece b2 = new Piece(PieceType.LARGE_SNOWBALL, 1, 1);
        board2.addPiece(b2);
        board2.movePiece(b2, 0); // UP – flies off
        boolean moved = board2.movePiece(new Piece(PieceType.LARGE_SNOWBALL, 3, 3), 1);
        assertTrue("no move accepted after game over", !moved);
    }

    /** Tests for stacking (small on large, and head on stack). */
    static void testStacking() {
        System.out.println("\n[GameBoard – stacking]");

        // ── Small slides onto Large → SNOWBALL_STACK ──────────────────────
        GameBoard board = new GameBoard();
        Piece large = new Piece(PieceType.LARGE_SNOWBALL, 0, 3);
        Piece small = new Piece(PieceType.SMALL_SNOWBALL, 0, 0);
        board.addPiece(large);
        board.addPiece(small);

        board.movePiece(small, 3); // RIGHT → collides with large
        assertEqual("large becomes stack", PieceType.SNOWBALL_STACK, large.getType());
        assertTrue("small removed from pieces", !board.getPieces().contains(small));
        assertTrue("stack is at (0,3)",         board.getPiece(0, 3) == large);

        // ── Head slides onto Stack → SNOWMAN ─────────────────────────────
        GameBoard board2 = new GameBoard();
        Piece stack = new Piece(PieceType.SNOWBALL_STACK, 2, 4);
        Piece headB = new Piece(PieceType.HEAD_BLUE, 2, 1);
        // Can't slide heads – use stackHead (adjacent placement)
        // First test via movePiece (head sliding)
        // We need head to be movable via slide — heads are treated like movable in movePiece
        // Actually per GameBoard.movePiece, only isMovable() pieces can slide.
        // For heads we use stackHead(). Test that here:
        board2.addPiece(stack);
        Piece headR = new Piece(PieceType.HEAD_RED, 2, 3); // adjacent to stack at col 4
        board2.addPiece(headR);
        boolean result = board2.stackHead(headR, stack);
        assertTrue("stackHead returns true",             result);
        assertEqual("stack becomes SNOWMAN_RED",         PieceType.SNOWMAN_RED, stack.getType());
        assertTrue("head removed from pieces",           !board2.getPieces().contains(headR));

        // ── stackHead requires adjacency ──────────────────────────────────
        GameBoard board3 = new GameBoard();
        Piece st2  = new Piece(PieceType.SNOWBALL_STACK, 0, 0);
        Piece head2 = new Piece(PieceType.HEAD_YELLOW, 3, 4); // far away
        board3.addPiece(st2);
        board3.addPiece(head2);
        result = board3.stackHead(head2, st2);
        assertTrue("stackHead fails when not adjacent", !result);

        // ── HEAD_YELLOW → SNOWMAN_YELLOW ─────────────────────────────────
        GameBoard board4 = new GameBoard();
        Piece st3   = new Piece(PieceType.SNOWBALL_STACK, 1, 1);
        Piece headY = new Piece(PieceType.HEAD_YELLOW, 1, 2);
        board4.addPiece(st3);
        board4.addPiece(headY);
        board4.stackHead(headY, st3);
        assertEqual("HEAD_YELLOW gives SNOWMAN_YELLOW", PieceType.SNOWMAN_YELLOW, st3.getType());

        // ── HEAD_BLUE → SNOWMAN_BLUE ──────────────────────────────────────
        GameBoard board5 = new GameBoard();
        Piece st4   = new Piece(PieceType.SNOWBALL_STACK, 2, 2);
        Piece headBl = new Piece(PieceType.HEAD_BLUE, 2, 3);
        board5.addPiece(st4);
        board5.addPiece(headBl);
        board5.stackHead(headBl, st4);
        assertEqual("HEAD_BLUE gives SNOWMAN_BLUE", PieceType.SNOWMAN_BLUE, st4.getType());
    }

    /** Tests for the win condition. */
    static void testWinCondition() {
        System.out.println("\n[GameBoard – win condition]");

        // Minimal level: one large + one small + one head, arranged to win in two moves
        //   Board (4 rows × 5 cols):
        //   row 0: . L . . .
        //   row 1: . . . . .
        //   row 2: . S . . .
        //   row 3: . B . . .   (B = HEAD_BLUE adjacent below S after S slides up onto L)
        // Sequence:
        //   1. Move S up  → collides with L → becomes SNOWBALL_STACK at (0,1)
        //   2. stackHead(B, stack) → B is at (3,1), stack at (0,1) → NOT adjacent → won't work
        //
        // Use simpler layout where all are adjacent after one move:
        //   row 0: . . . . .
        //   row 1: S L . . .     S at (1,0), L at (1,1)
        //   row 2: B . . . .     B at (2,0) – will be adjacent to stack at (1,1)? No…
        //
        // Easiest: place S directly left of L and B directly below resulting stack.
        //   S at (1,0), L at (1,1)   → move S right → stack at (1,1)
        //   B at (2,1)               → stackHead → SNOWMAN_BLUE at (1,1) → win

        GameBoard board = new GameBoard();
        Piece L = new Piece(PieceType.LARGE_SNOWBALL, 1, 1);
        Piece S = new Piece(PieceType.SMALL_SNOWBALL, 1, 0);
        Piece B = new Piece(PieceType.HEAD_BLUE, 2, 1);
        board.addPiece(L);
        board.addPiece(S);
        board.addPiece(B);

        assertTrue("not won before any moves", !board.isGameWon());

        board.movePiece(S, 3); // RIGHT → S collides with L → stack at (1,1)
        assertTrue("not won after stack", !board.isGameWon());

        board.stackHead(B, L); // L is now a SNOWBALL_STACK; B is adjacent below
        assertTrue("game won after completing snowman", board.isGameWon());
        assertTrue("game not over (no fly-off)",       !board.isGameOver());
    }

    /** Tests for board reset. */
    static void testReset() {
        System.out.println("\n[GameBoard – reset]");

        GameBoard board = new GameBoard();
        Piece ballOrig = new Piece(PieceType.LARGE_SNOWBALL, 0, 0);
        board.addPiece(ballOrig);
        board.movePiece(ballOrig, 3); // moves to col 4

        List<Piece> initial = new java.util.ArrayList<>();
        initial.add(new Piece(PieceType.LARGE_SNOWBALL, 0, 0));
        board.reset(initial);

        assertEqual("move count reset to 0", 0, board.getMoveCount());
        assertTrue("game not over after reset",  !board.isGameOver());
        assertTrue("game not won after reset",   !board.isGameWon());
        assertTrue("piece at original position", board.getPiece(0, 0) != null);
        assertTrue("old position empty after reset", board.getPiece(0, 4) == null);
    }

    /** Tests that Level 1 data is correct. */
    static void testLevelOne() {
        System.out.println("\n[Level – Level 1 data]");

        List<Level> levels = Level.buildAllLevels();
        assertTrue("80 levels exist", levels.size() == 80);

        Level lvl1 = levels.get(0);
        assertEqual("Level 1 number", 1, lvl1.getLevelNumber());
        assertEqual("Level 1 par",    4, lvl1.getMinMoves());

        List<Piece> pieces = lvl1.getInitialPieces();
        assertEqual("Level 1 piece count", 3, pieces.size());

        // Verify deep copy – mutating returned list should not affect Level
        pieces.get(0).setRow(3);
        List<Piece> pieces2 = lvl1.getInitialPieces();
        assertEqual("Level deep copy not mutated", 0, pieces2.get(0).getRow());
    }

    // ── Entry point ───────────────────────────────────────────────────────

    public static void main(String[] args) {
        System.out.println("=== Snow Problem – Unit Tests ===");

        testPiece();
        testBoardAddAndGet();
        testMovement();
        testGameOver();
        testStacking();
        testWinCondition();
        testReset();
        testLevelOne();

        System.out.println("\n─────────────────────────────────");
        System.out.println("Results: " + passed + " passed, " + failed + " failed");

        if (failed > 0) {
            System.out.println("STATUS: FAIL");
            System.exit(1);
        } else {
            System.out.println("STATUS: ALL PASS");
            System.exit(0);
        }
    }
}
