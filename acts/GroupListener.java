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
                    GroupMenu.current_group_mi.setText("current: " + Database.getGroupname(id));
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }
    
}