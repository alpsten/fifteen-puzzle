package fifteenpuzzle.controller;

import fifteenpuzzle.model.PuzzleBoard;
import fifteenpuzzle.ui.GameGrid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class NewGameHandler implements ActionListener {
    private final PuzzleBoard board;
    private final GameGrid gameGrid;

    public NewGameHandler(PuzzleBoard board, GameGrid gameGrid) {
        this.board = board;
        this.gameGrid = gameGrid;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        startNewGame();
    }

    public void startNewGame() {
        board.startNewGame();
        gameGrid.render();
    }
}

