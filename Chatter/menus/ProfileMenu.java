package menus;

import java.awt.Color;
import java.awt.event.KeyEvent;

import comps.menus.*;

public class ProfileMenu extends Menu {

    public static Menu change_profile_sm;
    public static MenuItem username_mi, change_username_mi, change_password_mi, delete_profile_mi, sign_out_mi;

    public ProfileMenu(String name, int ke) {
        super(name, ke);

        username_mi = new MenuItem(" - ");
        username_mi.setEnabled(false);
        change_username_mi = new MenuItem("Change username", KeyEvent.VK_U);
        change_password_mi = new MenuItem("Change password", KeyEvent.VK_P);
        change_profile_sm = new Menu("Change profile", KeyEvent.VK_C);
        change_profile_sm.add(change_username_mi);
        change_profile_sm.add(change_password_mi);
        sign_out_mi = new MenuItem("Sign out", KeyEvent.VK_S);
        delete_profile_mi = new MenuItem("Delete account");
        delete_profile_mi.setForeground(Color.RED.darker()); 

        add(username_mi);
        add(change_profile_sm);
        add(sign_out_mi);
        add(delete_profile_mi);
    }
}