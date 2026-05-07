package snowproblem;

import javax.swing.SwingUtilities;
import snowproblem.ui.GameWindow;

/** Entry point for the Snow Problem game. */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
