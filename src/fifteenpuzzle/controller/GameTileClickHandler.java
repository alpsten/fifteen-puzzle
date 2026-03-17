package fifteenpuzzle.controller;

import fifteenpuzzle.ui.GameGrid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class GameTileClickHandler implements ActionListener {
    private final GameGrid gameGrid;
    private final int tileValue;

    public GameTileClickHandler(GameGrid gameGrid, int tileValue) {
        this.gameGrid = gameGrid;
        this.tileValue = tileValue;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        gameGrid.handleTileSelection(tileValue);
    }
}
