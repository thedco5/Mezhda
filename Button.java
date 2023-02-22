import javax.swing.JButton;
import java.awt.Color;

public class Button extends JButton {
    Button(String name) {
        setText(name);
        setFocusPainted(false);
        setBackground(Color.LIGHT_GRAY);
        setBorderPainted(false);
        addActionListener(Chatter.button_listener);
    }
}