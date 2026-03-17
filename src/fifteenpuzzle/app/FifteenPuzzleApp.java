package fifteenpuzzle.app;

import fifteenpuzzle.ui.PuzzleFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class FifteenPuzzleApp {
    private FifteenPuzzleApp() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            new PuzzleFrame().setVisible(true);
        });
    }
}

