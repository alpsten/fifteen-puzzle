package fifteenpuzzle.logic;

import java.util.ArrayList;
import java.util.List;

public final class GameLogic {
    public static final int GRID_SIZE = 4;

    private GameLogic() {
    }

    public static boolean isAdjacent(int index1, int index2) {
        int row1 = index1 / GRID_SIZE;
        int column1 = index1 % GRID_SIZE;
        int row2 = index2 / GRID_SIZE;
        int column2 = index2 % GRID_SIZE;

        return (Math.abs(row1 - row2) == 1 && column1 == column2)
                || (Math.abs(column1 - column2) == 1 && row1 == row2);
    }

    public static List<Integer> getAdjacentIndices(int index) {
        List<Integer> adjacentIndices = new ArrayList<>();
        int row = index / GRID_SIZE;
        int column = index % GRID_SIZE;

        if (row > 0) {
            adjacentIndices.add(index - GRID_SIZE);
        }
        if (row < GRID_SIZE - 1) {
            adjacentIndices.add(index + GRID_SIZE);
        }
        if (column > 0) {
            adjacentIndices.add(index - 1);
        }
        if (column < GRID_SIZE - 1) {
            adjacentIndices.add(index + 1);
        }

        return adjacentIndices;
    }
}

