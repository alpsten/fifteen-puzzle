package fifteenpuzzle.ui;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.LayoutManager;

public final class SquarePanel extends JPanel {
    private final JComponent content;

    public SquarePanel(JComponent content) {
        super((LayoutManager) null);
        this.content = content;
        setOpaque(false);
        add(content);
    }

    @Override
    public void doLayout() {
        int size = Math.min(getWidth(), getHeight());
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;
        content.setBounds(x, y, size, size);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension preferredSize = content.getPreferredSize();
        int size = Math.max(preferredSize.width, preferredSize.height);
        return new Dimension(size, size);
    }
}

