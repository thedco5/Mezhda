package menus;

import java.awt.Color;
import java.awt.event.KeyEvent;

import comps.menus.*;

public class GroupMenu extends Menu {

    public static MenuItem current_group_mi, new_group_mi, add_to_group_mi, edit_mi, delete_group_mi;

    public GroupMenu(String name, int ke) {
        super(name, ke);

        current_group_mi = new MenuItem("current: ∅");
        current_group_mi.setEnabled(false);
        new_group_mi = new MenuItem("Create new", KeyEvent.VK_N);
        add_to_group_mi = new MenuItem("Send request", KeyEvent.VK_S);
        edit_mi = new MenuItem("Edit group", KeyEvent.VK_E);
        delete_group_mi = new MenuItem("Exit / Delete");
        delete_group_mi.setForeground(Color.RED.darker()); 

        add(current_group_mi);
        add(new_group_mi);
        add(add_to_group_mi);
        add(edit_mi);
        add(delete_group_mi);
    }
}
