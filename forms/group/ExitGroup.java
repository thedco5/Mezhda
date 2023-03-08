package forms.group;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.border.TitledBorder;

import src.*;
import util.Database;
import comps.*;

public class ExitGroup extends JFrame {

    public static Panel main_panel, button_panel;
    public static Button confirm_button, cancel_button;
    public static boolean is_owner;

    public ExitGroup() {

        String sql = "SELECT * FROM chatter.groups WHERE owner_id LIKE " + Chatter.user_id + " AND id LIKE " + Chatter.group_id + ";";
        try ( ResultSet rs = Database.query(sql) ) {
            if (rs.next()) is_owner = true;
            else is_owner = false;
        } catch (Exception e) { e.printStackTrace(); }

        /* BUTTONS */
        confirm_button = new Button((is_owner ? "Delete" : "Exit") + " group!");
        confirm_button.setActionCommand("Remove chat");
        cancel_button = new Button("Cancel");
        cancel_button.removeActionListener(Chatter.button_listener);
        cancel_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        button_panel = new Panel(new FlowLayout(FlowLayout.CENTER));
        button_panel.add(confirm_button);
        button_panel.add(cancel_button);
        

        /* PANEL */
        main_panel = new Panel();
        TitledBorder titled_border = BorderFactory.createTitledBorder(" '" + Database.getGroupname(Chatter.group_id) + "'' ");
        titled_border.setTitleFont(Chatter.font);
        titled_border.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        main_panel.setBorder(BorderFactory.createCompoundBorder(Chatter.padding, titled_border));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        main_panel.add(button_panel);

        /* WINDOW */
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(Chatter.IMG_URL).getImage());
        setResizable(false);
        getRootPane().setDefaultButton(cancel_button);
        add(main_panel);
        pack();
        setLocationRelativeTo(null);
    }
}