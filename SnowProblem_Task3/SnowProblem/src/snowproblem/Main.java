package snowproblem;

import snowproblem.ui.GameWindow;
import javax.swing.SwingUtilities;

/**
 * Entry point for the Snow Problem game.
 * Launches the Swing UI on the Event Dispatch Thread.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
