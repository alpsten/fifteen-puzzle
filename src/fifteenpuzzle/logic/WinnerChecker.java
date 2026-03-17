package fifteenpuzzle.logic;

import fifteenpuzzle.model.GameTile;

import java.util.List;

public final class WinnerChecker {
    private WinnerChecker() {
    }

    public static boolean isSolved(List<GameTile> tiles) {
        for (int index = 0; index < tiles.size() - 1; index++) {
            if (tiles.get(index).getValue() != index + 1) {
                return false;
            }
        }

        return tiles.get(tiles.size() - 1).isEmpty();
    }
}

