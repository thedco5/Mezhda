package acts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import forms.*;
import menus.*;
import src.*;

public class MenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Sign out" -> {
                Chatter.user_id = 0;
                Chatter.chat_frame.dispose();
                Chatter.form_frame.setVisible(true);
            }
            case "Delete account" -> {
                Chatter.chat_frame.dispose();
                Chatter.delete_account_frame = new DeleteAccount();
            }
            case "Change username" -> {
                Chatter.chat_frame.setVisible(false);
                Chatter.change_username_frame = new ChangeUsername();
            }
            case "Change password" -> {
                Chatter.chat_frame.setVisible(false);
                Chatter.change_password_frame = new ChangePassword();
            }
            case "Toggle fullscreen" -> {
                Chatter.full_screen = !Chatter.full_screen;
                if (Chatter.full_screen) Chatter.gr_dev.setFullScreenWindow(Chatter.chat_frame);
                else Chatter.gr_dev.setFullScreenWindow(null);
            }
            case "Screen size" -> {
                int width = (int) Chatter.main_panel.getSize().getWidth();
                int height = (int) Chatter.main_panel.getSize().getHeight();
                WindowMenu.screen_size_mi.setText(width + "×" + height + "px");
                Chatter.window_menu.doClick();
            }
            case "Minimise window" -> Chatter.chat_frame.setState(JFrame.ICONIFIED);
            default -> System.out.println(ae.getActionCommand());
        }
    }
}