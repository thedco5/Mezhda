import javax.swing.JMenuItem;

public class MenuItem extends JMenuItem {
    MenuItem (String str) {
        setText(str);
        setBorderPainted(false);
        Utility.makeDefaults(this);
        addActionListener(Chatter.button_listener);
    }
    MenuItem (String str, int ke) {
        this(str);
        setMnemonic(ke);
    }
}