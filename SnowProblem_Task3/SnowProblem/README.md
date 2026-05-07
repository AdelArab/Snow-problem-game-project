# Snow Problem — Java Game

A digital implementation of the Snow Problem tile puzzle game.

## Requirements

- Java 17 or newer (JDK, not just JRE)
- VS Code with the **Extension Pack for Java** installed
  (search "Extension Pack for Java" by Microsoft in the Extensions panel)

## Running in VS Code

1. Open the `SnowProblem` folder in VS Code (`File → Open Folder`)
2. Wait for the Java extension to index the project (bottom status bar)
3. Press **F5** or click `Run → Start Debugging` → select "Run Snow Problem"
4. The game window will open

Alternatively, right-click `src/snowproblem/Main.java` → **Run Java**

## How to Play

1. **Click a snowball** to select it (yellow highlight appears)
2. **Click in the direction** you want it to move — the snowball slides until blocked
3. To **stack**: select a small snowball and click an adjacent large snowball
4. To **add a head**: select a snowman head and click an adjacent small+large stack
5. Build all snowmen to win!

### Rules
- Large and small snowballs slide until hitting another piece or the edge
- If a snowball slides off the board → **Game Over** (press Reset)
- Small snowball stacks onto adjacent large snowball → locked (can't move)
- Snowman head stacks onto adjacent [large + small] stack → snowman complete
- Trees cannot move

## Project Structure

```
SnowProblem/
├── src/
│   └── snowproblem/
│       ├── Main.java              ← Entry point
│       ├── model/
│       │   ├── Piece.java         ← Game piece with stacking logic
│       │   ├── PieceType.java     ← Enum: TREE, LARGE_SNOWBALL, etc.
│       │   ├── Direction.java     ← Enum: UP, DOWN, LEFT, RIGHT
│       │   └── GameBoard.java     ← Board logic, movement, win detection
│       ├── levels/
│       │   ├── Level.java         ← Level data (piece positions)
│       │   └── LevelRegistry.java ← All level definitions (add new ones here)
│       └── ui/
│           ├── BoardPanel.java    ← Swing rendering + mouse input
│           └── GameWindow.java    ← Main window, level selector, reset
└── .vscode/
    ├── launch.json               ← F5 run config
    └── settings.json             ← Java source path

```

## Adding New Levels

Open `src/snowproblem/levels/LevelRegistry.java` and add a `registerLevel()` call:

```java
registerLevel(new Level(
    /* level number */ 10,
    /* name         */ "My Level",
    /* snowmen req  */ 2,
    /* trees        */ new int[][]{{0, 2}},
    /* large balls  */ new int[][]{{1, 1}, {1, 3}},
    /* small balls  */ new int[][]{{2, 1}, {2, 3}},
    /* heads        */ new int[][]{{3, 1}, {3, 3}}
));
```

Grid positions: `{row, col}` — row 0 is top, col 0 is left. Board is 4 rows × 5 cols.

## Grade Coverage

| Task   | Grade | Status   |
|--------|-------|----------|
| Task 1 | D     | Complete |
| Task 2 | C     | Complete |
| Task 3 | B     | Complete |
| Task 4 | A     | Extend LevelRegistry with all 80 levels + save scores |
| Task 5 | A*    | Implement BFS solver in a new `solver/` package |
