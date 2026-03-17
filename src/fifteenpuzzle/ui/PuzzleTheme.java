package fifteenpuzzle.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public final class PuzzleTheme {
    public static final Color APP_BACKGROUND = new Color(0xF3, 0xF4, 0xF6);
    public static final Color BOARD_BACKGROUND = new Color(0xD9, 0xE3, 0xE8);
    public static final Color PANEL_BACKGROUND = Color.WHITE;
    public static final Color PANEL_BORDER = new Color(0xD1, 0xD5, 0xDB);
    public static final Color TITLE_TEXT = new Color(0x11, 0x18, 0x27);
    public static final Color SUBTITLE_TEXT = new Color(0x6B, 0x72, 0x80);
    public static final Color STATUS_TEXT = new Color(0x4B, 0x55, 0x63);
    public static final Color BUTTON_WRAPPER_BACKGROUND = new Color(0xE5, 0xE7, 0xEB);
    public static final Color PRIMARY = new Color(0x11, 0x18, 0x27);
    public static final Color PRIMARY_BORDER = new Color(0x03, 0x07, 0x12);
    public static final Color TILE_BACKGROUND = new Color(0x0F, 0x76, 0x8A);
    public static final Color TILE_BORDER = new Color(0x0B, 0x4F, 0x5C);
    public static final Color EMPTY_TILE_BACKGROUND = new Color(0xE5, 0xE7, 0xEB);

    private static final String FONT_FAMILY = "Avenir Next";

    private PuzzleTheme() {
    }

    public static Font font(int style, int size) {
        return new Font(FONT_FAMILY, style, size);
    }

    public static Border createCardBorder(Color borderColor, int padding) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1, true),
                new EmptyBorder(padding, padding, padding, padding)
        );
    }

    public static Border createOuterPadding(int top, int left, int bottom, int right) {
        return new EmptyBorder(top, left, bottom, right);
    }

    public static void stylePrimaryButton(JButton button) {
        button.setFont(font(Font.BOLD, 16));
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_BORDER, 1, true),
                new EmptyBorder(14, 18, 14, 18)
        ));
    }

    public static void styleBoardTile(JButton button, boolean empty, int fontSize) {
        button.setFont(font(Font.BOLD, fontSize));
        button.setFocusPainted(false);
        button.setOpaque(true);

        if (empty) {
            button.setBackground(EMPTY_TILE_BACKGROUND);
            button.setBorder(BorderFactory.createLineBorder(PANEL_BORDER, 1, true));
            button.setEnabled(false);
            button.setCursor(Cursor.getDefaultCursor());
            button.setText("");
            return;
        }

        button.setBackground(TILE_BACKGROUND);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TILE_BORDER, 1, true),
                new EmptyBorder(14, 14, 14, 14)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleGoalTile(JLabel label, boolean empty) {
        label.setOpaque(true);
        label.setFont(font(Font.BOLD, 16));

        if (empty) {
            label.setBackground(EMPTY_TILE_BACKGROUND);
            label.setBorder(BorderFactory.createLineBorder(PANEL_BORDER, 1, true));
            return;
        }

        label.setBackground(TILE_BACKGROUND);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(TILE_BORDER, 1, true));
    }
}
