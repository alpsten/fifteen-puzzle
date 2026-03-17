package fifteenpuzzle.model;

import fifteenpuzzle.logic.GameLogic;
import fifteenpuzzle.logic.GameTileShuffler;
import fifteenpuzzle.logic.WinnerChecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PuzzleBoard {
    private final List<GameTile> tiles;
    private int moveCount;

    public PuzzleBoard() {
        tiles = createSolvedTiles();
    }

    public List<GameTile> getTiles() {
        return Collections.unmodifiableList(tiles);
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void startNewGame() {
        GameTileShuffler.shuffleTiles(tiles);
        moveCount = 0;
    }

    public PuzzleMove moveTileValue(int tileValue) {
        int tileIndex = getTileIndex(tileValue);
        int emptyIndex = getEmptyIndex();

        if (!GameLogic.isAdjacent(tileIndex, emptyIndex)) {
            return null;
        }

        Collections.swap(tiles, tileIndex, emptyIndex);
        moveCount++;
        return new PuzzleMove(tileValue, tileIndex, emptyIndex);
    }

    public boolean isSolved() {
        return WinnerChecker.isSolved(tiles);
    }

    private int getEmptyIndex() {
        for (int index = 0; index < tiles.size(); index++) {
            if (tiles.get(index).isEmpty()) {
                return index;
            }
        }

        throw new IllegalStateException("Board must contain an empty tile.");
    }

    private int getTileIndex(int tileValue) {
        for (int index = 0; index < tiles.size(); index++) {
            if (tiles.get(index).getValue() == tileValue) {
                return index;
            }
        }

        throw new IllegalArgumentException("Board does not contain tile " + tileValue + ".");
    }

    private List<GameTile> createSolvedTiles() {
        List<GameTile> solvedTiles = new ArrayList<>();

        for (int value = 1; value < GameLogic.GRID_SIZE * GameLogic.GRID_SIZE; value++) {
            solvedTiles.add(new GameTile(value));
        }

        solvedTiles.add(new GameTile(0));
        return solvedTiles;
    }
}
