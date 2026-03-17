package fifteenpuzzle.ui;

import fifteenpuzzle.logic.GameLogic;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

public final class GoalPreviewPanel extends JPanel {
    public GoalPreviewPanel() {
        super(new BorderLayout(0, 12));

        setBackground(PuzzleTheme.PANEL_BACKGROUND);
        setBorder(PuzzleTheme.createCardBorder(PuzzleTheme.PANEL_BORDER, 16));
        setPreferredSize(new Dimension(170, 170));

        JLabel previewTitle = new JLabel("Goal");
        previewTitle.setFont(PuzzleTheme.font(java.awt.Font.BOLD, 18));
        previewTitle.setForeground(PuzzleTheme.TITLE_TEXT);

        JPanel previewHeader = new JPanel(new GridLayout(0, 1, 0, 4));
        previewHeader.setOpaque(false);
        previewHeader.add(previewTitle);

        JPanel solvedGridPreview = new JPanel(new GridLayout(GameLogic.GRID_SIZE, GameLogic.GRID_SIZE, 6, 6));
        solvedGridPreview.setOpaque(false);

        int lastValue = GameLogic.GRID_SIZE * GameLogic.GRID_SIZE;
        for (int value = 1; value < lastValue; value++) {
            solvedGridPreview.add(createPreviewTile(String.valueOf(value), false));
        }
        solvedGridPreview.add(createPreviewTile("", true));

        add(previewHeader, BorderLayout.NORTH);
        add(solvedGridPreview, BorderLayout.CENTER);
    }

    private JComponent createPreviewTile(String label, boolean empty) {
        JLabel previewTile = new JLabel(label, SwingConstants.CENTER);
        previewTile.setPreferredSize(new Dimension(30, 30));
        PuzzleTheme.styleGoalTile(previewTile, empty);
        return previewTile;
    }
}

