import javax.swing.JButton;
import java.awt.Color;

public class Button extends JButton {
    Button(String name) {
        setText(name);
        setFocusPainted(false);
        setFont(Chatter.font);
        setBackground(Color.LIGHT_GRAY);
        setBorderPainted(false);
        addActionListener(Chatter.button_listener);
    }
}