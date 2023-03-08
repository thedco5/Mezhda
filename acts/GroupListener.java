package acts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.*;

public class GroupListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        switch (command) {
            case "refresh" -> { 
                Chatter.side_menu.refreshGroups(); 
                Chatter.chat_frame.pack(); 
            }
            // case NUMBER -> GROUP ID
            default -> System.out.println(command);
        }
    }
    
}