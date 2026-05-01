package snowproblem;

import snowproblem.ui.GameWindow;
import javax.swing.SwingUtilities;

/**
 * Entry point for Snow Problem — Part 1.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
