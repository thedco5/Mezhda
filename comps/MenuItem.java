package comps;

import javax.swing.JMenuItem;

import src.*;
import util.*;

public class MenuItem extends JMenuItem {
    public MenuItem (String str) {
        setText(str);
        setBorderPainted(false);
        Utility.makeDefaults(this);
        addActionListener(Chatter.menu_listener);
    }
    public MenuItem (String str, int ke) {
        this(str);
        setMnemonic(ke);
    }
}