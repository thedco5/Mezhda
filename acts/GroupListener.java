package acts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import menus.*;
import src.*;
import util.*;

public class GroupListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        switch (command) {
            case "refresh" -> { 
                Chatter.side_menu.refreshGroups(); 
                Chatter.chat_frame.pack(); 
            }
            default -> {
                try {
                    int id = Integer.parseInt(command);
                    Chatter.group_id = id;
                    GroupMenu.current_group_mi.setText("chat: " + Database.getGroupname(id));
                    GroupMenu.add_to_group_mi.setEnabled(true);
                    GroupMenu.edit_mi.setEnabled(true);
                    GroupMenu.delete_group_mi.setEnabled(true);
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }
    
}