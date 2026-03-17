package fifteenpuzzle.logic;

import fifteenpuzzle.model.GameTile;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public final class GameTileShuffler {
    private static final int SHUFFLE_MOVES = 400;
    private static final Random RANDOM = new Random();

    private GameTileShuffler() {
    }

    public static void shuffleTiles(List<GameTile> tiles) {
        tiles.sort(Comparator.comparingInt(GameTile::getSortOrder));

        int emptyIndex = tiles.size() - 1;
        int previousEmptyIndex = -1;

        for (int i = 0; i < SHUFFLE_MOVES; i++) {
            List<Integer> candidates = GameLogic.getAdjacentIndices(emptyIndex);

            if (candidates.size() > 1 && previousEmptyIndex >= 0) {
                candidates.remove(Integer.valueOf(previousEmptyIndex));
            }

            int swapIndex = candidates.get(RANDOM.nextInt(candidates.size()));
            Collections.swap(tiles, emptyIndex, swapIndex);
            previousEmptyIndex = emptyIndex;
            emptyIndex = swapIndex;
        }

        if (WinnerChecker.isSolved(tiles)) {
            List<Integer> candidates = GameLogic.getAdjacentIndices(emptyIndex);
            int swapIndex = candidates.get(RANDOM.nextInt(candidates.size()));
            Collections.swap(tiles, emptyIndex, swapIndex);
        }
    }
}

