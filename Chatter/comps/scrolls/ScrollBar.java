package comps.scrolls;

import javax.swing.JScrollBar;
import java.awt.Color;
import javax.swing.BorderFactory;

import src.*;

public class ScrollBar extends JScrollBar {
    public ScrollBar() {
        setUI(new Scroll());
        setUnitIncrement((int) Chatter.groups_menu.getComponent().getPreferredSize().getHeight());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.LIGHT_GRAY));
        setPreferredSize(getPreferredSize());
    }
}