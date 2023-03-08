package acts;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import menus.*;
import src.*;
import util.*;

public class GroupListener implements ActionListener {

    public static int last_painted;

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
                    int id = Integer.parseInt(command.split(":")[0]);
                    Chatter.group_id = id;
                    GroupMenu.current_group_mi.setText("chat: " + Database.getGroupname(id));
                    GroupMenu.add_to_group_mi.setEnabled(true);
                    GroupMenu.edit_mi.setEnabled(true);
                    GroupMenu.delete_group_mi.setEnabled(true);
                    if (last_painted > 0)
                        Chatter.side_menu.getComponent(last_painted).setBackground(Color.WHITE);
                    last_painted = Integer.parseInt(command.split(":")[1]);
                    Chatter.side_menu.getComponent(last_painted).setBackground(Color.LIGHT_GRAY);
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }
    
}