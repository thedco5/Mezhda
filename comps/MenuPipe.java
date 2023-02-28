package comps;

import util.*;

public class MenuPipe extends Menu {
    public MenuPipe() {
        super("|");
        setOpaque(true);
        setBorderPainted(false);
        Utility.makeDefaults(this);
        setEnabled(false);
    }
}