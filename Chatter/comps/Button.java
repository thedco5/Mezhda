package comps;

import javax.swing.JButton;
import java.awt.Color;

import src.*;

public class Button extends JButton {
    public Button(String name) {
        setText(name);
        setFocusPainted(false);
        setFont(Chatter.font);
        setBackground(Color.LIGHT_GRAY);
        setBorderPainted(false);
        addActionListener(Chatter.button_listener); // check whether a button is pressed
    }
}