package fifteenpuzzle.ui;

import fifteenpuzzle.controller.NewGameHandler;
import fifteenpuzzle.model.PuzzleBoard;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public final class PuzzleFrame extends JFrame {
    public PuzzleFrame() {
        super("15-Puzzle");

        PuzzleBoard board = new PuzzleBoard();
        GameGrid gameGrid = new GameGrid(board);
        NewGameHandler newGameHandler = new NewGameHandler(board, gameGrid);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 820);
        setMinimumSize(new java.awt.Dimension(520, 620));
        getContentPane().setBackground(PuzzleTheme.APP_BACKGROUND);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 20));

        add(gameGrid, BorderLayout.CENTER);
        add(new ControlPanel(newGameHandler), BorderLayout.SOUTH);

        newGameHandler.startNewGame();
    }
}
