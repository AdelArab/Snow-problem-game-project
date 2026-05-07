package snowproblem.levels;

import java.util.ArrayList;
import java.util.List;

/**
 * Central registry of all game levels.
 *
 * To add a new level, call registerLevel() with the piece positions.
 * Grid positions: row 0 = top, col 0 = left. Board is 4 rows × 5 cols.
 *
 * Each piece position is {row, col}.
 * Up to 3 of each piece type per level.
 */
public class LevelRegistry {

    private static final List<Level> levels = new ArrayList<>();

    static {
        registerLevel(Level.fromMap(
            1, "Starter 1", 1, "blue",
            ".S...",
            ".....",
            ".....",
            "B...L"
        ));

        registerLevel(Level.fromMap(
            2, "Starter 2", 1, "yellow",
            ".....",
            "L..Y.",
            ".T..S",
            "....."
        ));

        registerLevel(Level.fromMap(
            3, "Starter 3", 1, "red",
            "T..L.",
            "...S.",
            "....R",
            ".T..."
        ));
    }

    private static void registerLevel(Level level) {
        levels.add(level);
    }

    /** Returns all registered levels in order. */
    public static List<Level> getAllLevels() {
        return levels;
    }

    /** Returns the level with the given number, or null if not found. */
    public static Level getLevel(int number) {
        for (Level l : levels) {
            if (l.getNumber() == number) return l;
        }
        return null;
    }

    /** Returns the total number of registered levels. */
    public static int count() {
        return levels.size();
    }
}
