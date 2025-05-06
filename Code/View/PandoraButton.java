package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PandoraButton extends JButton {

    private final Color defaultBackground = Color.BLACK;
    private final Color defaultForeground = Color.WHITE;
    private final Color defaultBorder = Color.WHITE;

    private final Color clickedColor = Color.BLUE;

    private final Color disabledBackground = new Color(30, 30, 30);     // very dark grey
    private final Color disabledForeground = new Color(200, 200, 200); // very light grey

    public PandoraButton(String text) {
        super(text);
        setContentAreaFilled(false);  // no 3D effect
        setFocusPainted(false);
        setOpaque(true);
        setFont(new Font("Arial", Font.PLAIN, 20));

        updateStyle(isEnabled());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isEnabled()) {
                    setForeground(clickedColor);
                    setBorder(BorderFactory.createLineBorder(clickedColor));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isEnabled()) {
                    setForeground(defaultForeground);
                    setBorder(BorderFactory.createLineBorder(defaultBorder));
                }
            }
        });
    }

    private void updateStyle(boolean enabled) {
        if (enabled) {
            setBackground(defaultBackground);
            setForeground(defaultForeground);
            setBorder(BorderFactory.createLineBorder(defaultBorder));
        } else {
            setBackground(disabledBackground);
            setForeground(disabledForeground);
            setBorder(BorderFactory.createLineBorder(disabledForeground));
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        updateStyle(enabled);
    }
}
