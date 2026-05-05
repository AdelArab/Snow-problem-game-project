# ❄ Snow Problem — Parts 1 & 2

Java implementation of the Snow Problem puzzle game.  
This deliverable covers:

- **Part 1 – Core Data Model** (Task 1 foundation)
- **Part 2 – Basic GUI** (Task 1 completion — Level 1 displayed)

---

## Requirements

- **JDK 11+** — download from https://adoptium.net/

---

## Build & Run

### Linux / Mac
```bash
chmod +x build.sh
./build.sh
# Then:
java -jar SnowProblem_Part1_2.jar
```

### Windows
```
run.bat
```
This compiles, runs tests, packages the JAR, then launches the GUI.

---

## What You Will See (Part 2 GUI)

A 5×4 game board rendered with the provided PNG graphics showing **Level 1**:

```
 .  S  .  .  .     (S = small snowball, row 0 col 1)
 .  .  .  .  .
 B  .  .  .  .     (B = head blue,      row 2 col 0)
 .  .  .  L  .     (L = large snowball, row 3 col 3)
```

The window shows:
- Level number and par (minimum moves)
- Difficulty tier (STARTER)
- Piece legend at the bottom

---

## Running Tests Only (no GUI)

```bash
# After building:
java -cp build snowproblem.GameBoardTest
```

Expected output:
```
=== Snow Problem – Unit Tests ===

[Piece]
  PASS  large snowball is movable
  PASS  small snowball is movable
  ...

[GameBoard – movement]
  PASS  slide to right wall col
  ...

Results: 38 passed, 0 failed
STATUS: ALL PASS
```

---

## Project Structure

```
SnowProblem_Part1_2/
│
├── src/snowproblem/
│   ├── PieceType.java     Part 1 – enum of all piece types
│   ├── Piece.java         Part 1 – single board piece (type + position)
│   ├── GameBoard.java     Part 1 – 5×4 board, all game rules
│   ├── Level.java         Part 1 – level data structure (all 80 levels)
│   ├── BoardPanel.java    Part 2 – Swing panel that renders the board
│   ├── MainWindow.java    Part 2 – top-level JFrame, shows Level 1
│   └── resources/         PNG game assets (from provided zip)
│       ├── hole.png
│       ├── tree.png
│       ├── snowball_large.png
│       ├── snowball_small.png
│       ├── head_red/blue/yellow.png
│       ├── snowman_stack.png
│       └── snowman_red/blue/yellow.png
│
├── test/snowproblem/
│   └── GameBoardTest.java  Part 1 – unit tests (no external framework)
│
├── build.sh                Linux/Mac build + test + package
├── run.bat                 Windows  build + test + package + launch
└── README.md
```

---

## Class Responsibilities

| Class | Responsibility |
|---|---|
| `PieceType` | Enum listing every distinct piece type |
| `Piece` | Holds a piece's type and board position; exposes isMovable(), isHead(), etc. |
| `GameBoard` | 5×4 grid, enforces all sliding/stacking/win rules |
| `Level` | Immutable level config; `buildAllLevels()` returns all 80 |
| `BoardPanel` | Swing JPanel — paints the grid and pieces using PNG assets |
| `MainWindow` | JFrame — creates the window, builds Level 1, wires everything together |
| `GameBoardTest` | 38 unit tests covering Piece, GameBoard, and Level |

---

## Test Coverage (Part 1)

| Suite | Tests |
|---|---|
| Piece | isMovable, isSnowball, isHead, isStack, isComplete, setType, position setters |
| GameBoard add/get | addPiece, getPiece, inBounds, isOccupied, duplicate/OOB guards |
| GameBoard movement | slide to wall, blocked by tree, no-move at wall, tree immovable, move count |
| GameBoard game over | fly-off detected, piece removed, no moves after game over |
| GameBoard stacking | S+L→stack, head+stack→snowman (all 3 colours), adjacency guard |
| GameBoard win | win detected, not won prematurely |
| GameBoard reset | count reset, game flags cleared, piece repositioned |
| Level | 80 levels loaded, Level 1 data correct, deep-copy isolation |
