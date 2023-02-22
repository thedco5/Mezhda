import javax.swing.JLabel;

public class Label extends JLabel {
    Label (String str) {
        setText(str);
        setFont(Chatter.font);
    }
}