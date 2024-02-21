package comps;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import src.Chatter;
import util.*;

public class SideMenu extends JPanel {
    public SideMenu () {
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // setLayout(new GridLayout(10, 1));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.LIGHT_GRAY));
    }
    public void refreshGroups() {
        removeAll();
        GroupButton group_button = new GroupButton("chats");
        group_button.setActionCommand("refresh");
        group_button.setForeground(Color.GRAY);
        group_button.setEnabled(false);

        add(group_button);
        int counter = 1;
        try ( ResultSet rs = Database.query("SELECT * FROM members WHERE user_id LIKE " + Chatter.user_id + ";") ) {
            while (rs.next()) {
                int group_id = rs.getInt("group_id");
                GroupButton button = new GroupButton(Database.getGroupname(group_id));
                button.setActionCommand(String.valueOf(group_id) + ":" + String.valueOf(counter));
                add(button);
                counter++;
            }
        } catch (Exception e) { e.printStackTrace(); }
        setLayout(new GridLayout(counter, 1));
        if (Chatter.split_pane != null)
        Chatter.split_pane.setDividerLocation((int) (Chatter.group_scroll_pane.getPreferredSize().getWidth()));
    }
}