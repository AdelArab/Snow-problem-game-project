package snowproblem.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class HighScoreManager {

    // Stores the best score for each level using the level number.
    private final Properties scores = new Properties();

    // The file where the scores are saved after the game closes.
    private final String fileName = "highscores.properties";

    // Loads the saved scores from the file if it already exists.
    public HighScoreManager() {
        try (FileInputStream input = new FileInputStream(fileName)) {
            scores.load(input);
        } catch (IOException e) {
            // If the file does not exist yet, the game starts with no saved scores.
        }
    }

    // Returns the best saved score for a level, or -1 if there is no score yet.
    public int getBestScore(int levelNumber) {
        String score = scores.getProperty(String.valueOf(levelNumber));

        if (score == null) {
            return -1;
        }

        return Integer.parseInt(score);
    }

    // Saves the score only if it is the first score or better than the old best score.
    public void saveScore(int levelNumber, int moves) {
        int currentBest = getBestScore(levelNumber);

        if (currentBest == -1 || moves < currentBest) {
            scores.setProperty(String.valueOf(levelNumber), String.valueOf(moves));

            try (FileOutputStream output = new FileOutputStream(fileName)) {
                scores.store(output, "Snow Problem High Scores");
            } catch (IOException e) {
                System.out.println("Could not save high score.");
            }
        }
    }
}