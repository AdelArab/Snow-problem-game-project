package snowproblem;

/**
 * Enumerates every distinct piece type that can appear on the board.
 *
 * Simple types (placed at level start):
 *   TREE            – immovable obstacle
 *   LARGE_SNOWBALL  – slides horizontally / vertically
 *   SMALL_SNOWBALL  – slides horizontally / vertically
 *   HEAD_RED        – snowman head (red scarf variant)
 *   HEAD_BLUE       – snowman head (blue scarf variant)
 *   HEAD_YELLOW     – snowman head (yellow scarf variant)
 *
 * Composite types (created during play):
 *   SNOWBALL_STACK  – small snowball stacked on large snowball (immovable)
 *   SNOWMAN_RED     – complete snowman (head_red + stack)
 *   SNOWMAN_BLUE    – complete snowman (head_blue + stack)
 *   SNOWMAN_YELLOW  – complete snowman (head_yellow + stack)
 */
public enum PieceType {
    TREE,
    LARGE_SNOWBALL,
    SMALL_SNOWBALL,
    HEAD_RED,
    HEAD_BLUE,
    HEAD_YELLOW,
    SNOWBALL_STACK,
    SNOWMAN_RED,
    SNOWMAN_BLUE,
    SNOWMAN_YELLOW
}
