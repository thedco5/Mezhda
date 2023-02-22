package src;

import javax.swing.JLabel;

public class Label extends JLabel {
    public Label (String str) {
        setText(str);
        setFont(Chatter.font);
    }
}