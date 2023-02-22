import javax.swing.JMenu;

public class Menu extends JMenu {
    Menu (String str) {
        setText(str);
        setOpaque(true);
        setBorderPainted(false);
        Utility.makeDefaults(this);
    }
    Menu (String str, int ke) {
        this(str);
        setMnemonic(ke);
    }
}