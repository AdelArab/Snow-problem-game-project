# Snow Problem Game Project

A Java Swing puzzle game developed as part of my Year 1 coursework for BSc (Hons) Data Science at Lancaster University.

## Technologies Used

- Java
- Java Swing
- Git
- GitHub
- Object-Oriented Programming

# Snow Problem

This is a Java Swing puzzle game where the player moves and stacks snowballs to build snowmen.

## How to compile

From the main project folder, run:

```bash
javac -d out $(find src -name "*.java")
```

## How to run

After compiling, run:

```bash
java -cp out snowproblem.Main
```

## Required files

The `resources` folder must stay in the main project folder because it contains the images used by the game.

The game also uses `highscores.properties` to save the best score for each level.
