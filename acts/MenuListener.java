package acts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import forms.*;
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
                
            }
            default -> System.out.println(ae.getActionCommand());
        }
    }
}