package menu;

import javax.swing.JMenuItem;

import src.Chatter;
import util.Utility;

public class MenuItem extends JMenuItem {
    public MenuItem (String str) {
        setText(str);
        setBorderPainted(false);
        Utility.makeDefaults(this);
        addActionListener(Chatter.button_listener);
    }
    public MenuItem (String str, int ke) {
        this(str);
        setMnemonic(ke);
    }
}