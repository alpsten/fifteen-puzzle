package fifteenpuzzle.model;

public final class GameTile {
    private static final int EMPTY_VALUE = 0;

    private final int value;

    public GameTile(int value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == EMPTY_VALUE;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return isEmpty() ? "" : String.valueOf(value);
    }

    public int getSortOrder() {
        return isEmpty() ? 16 : value;
    }
}

