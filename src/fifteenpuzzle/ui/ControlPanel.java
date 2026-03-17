package fifteenpuzzle.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

public final class ControlPanel extends JPanel {
    public ControlPanel(ActionListener newGameAction) {
        super(new BorderLayout());

        setBackground(PuzzleTheme.APP_BACKGROUND);
        setBorder(PuzzleTheme.createOuterPadding(0, 24, 24, 24));

        JButton newGameButton = new JButton("New Solvable Game");
        PuzzleTheme.stylePrimaryButton(newGameButton);
        newGameButton.addActionListener(newGameAction);

        JPanel buttonWrapper = new JPanel(new BorderLayout());
        buttonWrapper.setBackground(PuzzleTheme.BUTTON_WRAPPER_BACKGROUND);
        buttonWrapper.setBorder(PuzzleTheme.createCardBorder(PuzzleTheme.PANEL_BORDER, 12));
        buttonWrapper.add(newGameButton, BorderLayout.CENTER);

        add(buttonWrapper, BorderLayout.CENTER);
    }
}

