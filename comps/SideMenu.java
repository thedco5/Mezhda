package comps;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import util.*;

public class SideMenu extends JPanel {
    public SideMenu () {
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new GridLayout(10, 1));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.LIGHT_GRAY));

        
    }
    public void refreshGroups() {
        System.out.println("refreshing");
        removeAll();
        GroupButton group_button = new GroupButton("chats");
        group_button.setActionCommand("refresh");
        group_button.setForeground(Color.GRAY);
        add(group_button);
        try ( ResultSet rs = Database.query("SELECT * FROM chatter.groups;") ) {
            while (rs.next()) {
                GroupButton button = new GroupButton(rs.getString("groupname"));
                button.setActionCommand(String.valueOf(rs.getInt("id")));
                add(button);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}