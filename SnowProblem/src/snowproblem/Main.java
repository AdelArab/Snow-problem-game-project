package snowproblem;

import snowproblem.ui.GameWindow;
import javax.swing.SwingUtilities;

public class Main {

    // Starts the game by opening the main window.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameWindow();
            }
        });
    }
}