package fifteenpuzzle.ui;

import fifteenpuzzle.controller.GameTileClickHandler;
import fifteenpuzzle.model.PuzzleBoard;
import fifteenpuzzle.model.PuzzleMove;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public final class GameGrid extends JPanel {
    private final PuzzleBoard board;
    private final JLabel statusLabel;
    private final BoardPanel boardPanel;

    public GameGrid(PuzzleBoard board) {
        super(new BorderLayout(0, 18));
        this.board = board;

        setBackground(PuzzleTheme.APP_BACKGROUND);
        setBorder(PuzzleTheme.createOuterPadding(24, 24, 24, 24));

        statusLabel = new JLabel();
        statusLabel.setFont(PuzzleTheme.font(java.awt.Font.PLAIN, 16));
        statusLabel.setForeground(PuzzleTheme.STATUS_TEXT);

        boardPanel = new BoardPanel();
        boardPanel.bindTileActions(tileValue -> {
            new GameTileClickHandler(this, tileValue).actionPerformed(null);
        });

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
    }

    public void render() {
        boardPanel.renderBoard(board.getTiles());
        statusLabel.setText("Moves: " + board.getMoveCount());
    }

    public boolean handleTileSelection(int tileValue) {
        if (boardPanel.isAnimating()) {
            return false;
        }

        PuzzleMove move = board.moveTileValue(tileValue);
        if (move == null) {
            return false;
        }

        statusLabel.setText("Moves: " + board.getMoveCount());
        boardPanel.animateMove(board.getTiles(), move, () -> {
            render();

            if (board.isSolved()) {
                JOptionPane.showMessageDialog(this, "Congratulations! You won the game!");
            }
        });
        return true;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(18, 0));
        headerPanel.setOpaque(false);
        headerPanel.add(new GoalPreviewPanel(), BorderLayout.WEST);

        JPanel titlePanel = new JPanel(new GridLayout(0, 1, 0, 4));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("15 Puzzle");
        titleLabel.setFont(PuzzleTheme.font(java.awt.Font.BOLD, 32));
        titleLabel.setForeground(PuzzleTheme.TITLE_TEXT);

        JLabel subtitleLabel = new JLabel("Slide tiles into place. Every shuffle is solvable.");
        subtitleLabel.setFont(PuzzleTheme.font(java.awt.Font.PLAIN, 15));
        subtitleLabel.setForeground(PuzzleTheme.SUBTITLE_TEXT);

        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(statusLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        JPanel gridWrapper = new SquarePanel(boardPanel);
        gridWrapper.setOpaque(false);

        contentPanel.add(gridWrapper, BorderLayout.CENTER);
        return contentPanel;
    }
}
