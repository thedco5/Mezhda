package comps;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import src.*;

import java.awt.Color;

public class GroupButton extends JButton {
    public GroupButton(String name) {
        setText(name);
        setHorizontalAlignment(LEFT);
        setFocusPainted(false);
        setFont(Chatter.font);
        setBackground(Color.WHITE);
        setMinimumSize(getMinimumSize());
        Border border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY);
        setBorder(BorderFactory.createCompoundBorder(border, Chatter.padding));
        // setBorder(Chatter.padding);
        // addActionListener(Chatter.button_listener);
    }
}