package fifteenpuzzle.ui;

import fifteenpuzzle.logic.GameLogic;
import fifteenpuzzle.model.GameTile;
import fifteenpuzzle.model.PuzzleMove;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class BoardPanel extends JPanel {
    private static final int GAP = 12;
    private static final int PADDING = 16;
    private static final int ANIMATION_DURATION_MS = 150;
    private static final int ANIMATION_STEP_MS = 15;

    private final Map<Integer, JButton> tileButtons;
    private final JButton emptyTileButton;

    private List<GameTile> currentTiles;
    private Timer animationTimer;
    private PuzzleMove activeMove;
    private long animationStartTime;

    public BoardPanel() {
        setLayout(null);
        setBackground(PuzzleTheme.BOARD_BACKGROUND);
        setBorder(PuzzleTheme.createOuterPadding(PADDING, PADDING, PADDING, PADDING));
        setPreferredSize(new Dimension(560, 560));

        tileButtons = new LinkedHashMap<>();
        for (int value = 1; value < GameLogic.GRID_SIZE * GameLogic.GRID_SIZE; value++) {
            JButton button = new JButton(String.valueOf(value));
            tileButtons.put(value, button);
            add(button);
        }

        emptyTileButton = new JButton();
        add(emptyTileButton);
    }

    public void bindTileActions(TileSelectionHandler selectionHandler) {
        for (Map.Entry<Integer, JButton> entry : tileButtons.entrySet()) {
            int tileValue = entry.getKey();
            JButton button = entry.getValue();
            button.addActionListener(event -> selectionHandler.handleTileSelection(tileValue));
        }
    }

    public boolean isAnimating() {
        return animationTimer != null && animationTimer.isRunning();
    }

    public void renderBoard(List<GameTile> tiles) {
        stopAnimation();
        currentTiles = tiles;
        activeMove = null;
        updateButtonBounds();
        repaint();
    }

    public void animateMove(List<GameTile> tiles, PuzzleMove move, Runnable onComplete) {
        stopAnimation();

        currentTiles = tiles;
        activeMove = move;
        animationStartTime = System.currentTimeMillis();
        updateButtonBounds();

        animationTimer = new Timer(ANIMATION_STEP_MS, event -> {
            long elapsed = System.currentTimeMillis() - animationStartTime;
            double progress = Math.min(1.0, (double) elapsed / ANIMATION_DURATION_MS);

            updateButtonBounds(progress);
            repaint();

            if (progress >= 1.0) {
                stopAnimation();
                activeMove = null;
                updateButtonBounds();
                onComplete.run();
            }
        });
        animationTimer.start();
    }

    @Override
    public void doLayout() {
        super.doLayout();

        if (currentTiles == null || currentTiles.isEmpty()) {
            return;
        }

        if (isAnimating()) {
            long elapsed = System.currentTimeMillis() - animationStartTime;
            double progress = Math.min(1.0, (double) elapsed / ANIMATION_DURATION_MS);
            updateButtonBounds(progress);
            return;
        }

        updateButtonBounds();
    }

    private void updateButtonBounds() {
        updateButtonBounds(1.0);
    }

    private void updateButtonBounds(double animationProgress) {
        if (currentTiles == null || currentTiles.isEmpty()) {
            return;
        }

        int fontSize = getBoardFontSize();

        for (int index = 0; index < currentTiles.size(); index++) {
            GameTile tile = currentTiles.get(index);
            JButton button = tile.isEmpty() ? emptyTileButton : tileButtons.get(tile.getValue());

            PuzzleTheme.styleBoardTile(button, tile.isEmpty(), fontSize);

            if (activeMove != null && tile.getValue() == activeMove.getTileValue()) {
                Rectangle startBounds = getCellBounds(activeMove.getFromIndex());
                Rectangle endBounds = getCellBounds(activeMove.getToIndex());
                button.setBounds(interpolate(startBounds, endBounds, animationProgress));
            } else {
                button.setBounds(getCellBounds(index));
            }
        }
    }

    private Rectangle getCellBounds(int index) {
        Insets insets = getInsets();
        int availableWidth = getWidth() - insets.left - insets.right;
        int availableHeight = getHeight() - insets.top - insets.bottom;
        int cellSize = (Math.min(availableWidth, availableHeight) - GAP * (GameLogic.GRID_SIZE - 1)) / GameLogic.GRID_SIZE;

        int row = index / GameLogic.GRID_SIZE;
        int column = index % GameLogic.GRID_SIZE;
        int x = insets.left + column * (cellSize + GAP);
        int y = insets.top + row * (cellSize + GAP);

        return new Rectangle(x, y, Math.max(cellSize, 0), Math.max(cellSize, 0));
    }

    private Rectangle interpolate(Rectangle startBounds, Rectangle endBounds, double progress) {
        int x = (int) Math.round(startBounds.x + (endBounds.x - startBounds.x) * progress);
        int y = (int) Math.round(startBounds.y + (endBounds.y - startBounds.y) * progress);
        int width = (int) Math.round(startBounds.width + (endBounds.width - startBounds.width) * progress);
        int height = (int) Math.round(startBounds.height + (endBounds.height - startBounds.height) * progress);
        return new Rectangle(x, y, width, height);
    }

    private int getBoardFontSize() {
        Rectangle firstCell = getCellBounds(0);
        return Math.max(18, Math.min(40, firstCell.width / 3));
    }

    private void stopAnimation() {
        if (animationTimer == null) {
            return;
        }

        animationTimer.stop();
        animationTimer = null;
    }
}
