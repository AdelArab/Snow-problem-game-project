package snowproblem.levels;

import java.util.ArrayList;
import java.util.List;

/** Stores the levels currently available in the game. */
public class LevelRegistry {
    private static final List<Level> levels = new ArrayList<>();

    static {
        // Level 1 only for Task 1: setting the scene.
        registerLevel(new Level(
            1, "Starter 1", 1,
            new int[][] {},          // trees
            new int[][] {{3, 4}},    // large snowball
            new int[][] {{0, 1}},    // small snowball
            new int[][] {{3, 0}},    // snowman head
            "blue"
        ));
    }

    private static void registerLevel(Level level) {
        levels.add(level);
    }

    public static List<Level> getAllLevels() {
        return levels;
    }
}
