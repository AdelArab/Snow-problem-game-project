# Snow Problem

A Java Swing puzzle game developed as part of my Year 1 coursework for the BSc (Hons) Data Science degree at Lancaster University.

The goal is to move and stack snowballs to build the required snowmen while avoiding invalid moves and losing pieces from the board.

## Features

- Multiple puzzle levels with increasing difficulty
- Snowball sliding and stacking mechanics
- Trees and other board obstacles
- Coloured snowman heads and completed snowmen
- Level selector and next-level controls
- Move counter
- Persistent best scores saved locally
- Desktop interface built with Java Swing

## How to Play

1. Select a level from the drop-down menu.
2. Click a movable snowball to select it.
3. Click an adjacent cell to attempt a stack, or click in a direction to slide the selected piece.
4. Build the required number of snowmen to complete the level.
5. Use **Reset** if you want to restart the current level.

## Technologies

- Java
- Java Swing
- Object-Oriented Programming
- Git and GitHub

## Project Structure

```text
.
├── src/
│   └── snowproblem/
│       ├── Main.java
│       ├── levels/
│       ├── model/
│       └── ui/
├── resources/
├── .gitignore
└── README.md
```

## Running the Game

### Requirements

Install a Java Development Kit (JDK). The project has been tested with JDK 25.

### Windows PowerShell

From the repository root:

```powershell
javac -d out src\snowproblem\*.java src\snowproblem\model\*.java src\snowproblem\levels\*.java src\snowproblem\ui\*.java
java -cp out snowproblem.Main
```

### macOS / Linux

From the repository root:

```bash
javac -d out $(find src -name "*.java")
java -cp out snowproblem.Main
```

Keep the `resources` folder in the repository root when running the game because the interface loads its images from that folder.

The game creates and updates `highscores.properties` locally to store the best score for each level.

## What I Learned

This project helped me practise object-oriented program design, separating game logic from the user interface, event-driven programming with Swing, file-based persistence, and managing a multi-file Java project with Git.
