package menu;

import javax.swing.JMenu;

import util.*;

public class Menu extends JMenu {
    public Menu (String str) {
        setText(str);
        setOpaque(true);
        setBorderPainted(false);
        Utility.makeDefaults(this);
    }
    public Menu (String str, int ke) {
        this(str);
        setMnemonic(ke);
    }
}