package comps;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import java.awt.Color;

import src.*;

public class GroupButton extends JButton {
    public GroupButton(String name) {
        setText(name);
        setHorizontalAlignment(LEFT);
        setFocusPainted(false);
        setFont(Chatter.font);
        setBackground(Color.WHITE);
        setMinimumSize(getMinimumSize());
        addActionListener(Chatter.group_listener);
        Border border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY);
        setBorder(BorderFactory.createCompoundBorder(border, Chatter.padding));
    }
}