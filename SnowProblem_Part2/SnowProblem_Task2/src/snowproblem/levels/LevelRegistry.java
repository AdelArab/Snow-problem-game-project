package snowproblem.levels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Stores the levels available in the Task 2 snapshot. */
public class LevelRegistry {

    private static final List<Level> levels = new ArrayList<>();

    static {
        levels.add(new Level(
            1, "Starter 1",
            ".S...",
            ".....",
            ".....",
            "B...L"
        ));
    }

    public static List<Level> getAllLevels() {
        return Collections.unmodifiableList(levels);
    }
}
