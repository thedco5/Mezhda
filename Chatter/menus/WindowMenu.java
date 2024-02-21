package menus;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import comps.menus.*;

public class WindowMenu extends Menu {

    public static MenuItem screen_size_mi, fullscreen_mi, minimize_mi;

    public WindowMenu(String name, int ke) {
        super(name, ke);

        screen_size_mi = new MenuItem("0×0 px", KeyEvent.VK_X);
        screen_size_mi.setActionCommand("Screen size");
        screen_size_mi.setForeground(Color.GRAY);
        fullscreen_mi = new MenuItem("Toggle fullscreen", KeyEvent.VK_T);
        fullscreen_mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
        minimize_mi = new MenuItem("Minimise window", KeyEvent.VK_M);
        minimize_mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));

        add(screen_size_mi);
        add(fullscreen_mi);
        add(minimize_mi);
    }
}
