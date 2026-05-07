package snowproblem;

import javax.swing.SwingUtilities;
import snowproblem.ui.GameWindow;

/** Starts the Snow Problem application. */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow().setVisible(true));
    }
}
