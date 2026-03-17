package fifteenpuzzle.model;

public final class PuzzleMove {
    private final int tileValue;
    private final int fromIndex;
    private final int toIndex;

    public PuzzleMove(int tileValue, int fromIndex, int toIndex) {
        this.tileValue = tileValue;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    public int getTileValue() {
        return tileValue;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }
}

