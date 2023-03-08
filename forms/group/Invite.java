package forms.group;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import src.*;
import util.*;
import comps.*;

public class Invite extends JFrame {

    public static Panel main_panel, username_panel, button_panel;
    public static Label username_label;
    public static JTextField username_field;
    public static Button confirm_button;
    public static boolean is_owner = true;

    public Invite() {

        String sql = "SELECT * FROM chatter.groups WHERE owner_id LIKE " + Chatter.user_id + " AND id LIKE " + Chatter.group_id + ";";
        try ( ResultSet rs = Database.query(sql) ) {
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "You must be the owner to invite others!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception e) { e.printStackTrace(); }

        /* NEW GROUP */
        username_label = new Label("username: ");
        username_field = new JTextField();
        username_field.setFont(Chatter.font);
        username_field.setPreferredSize(new Dimension(Chatter.base_height * 5, Chatter.base_height));
        username_panel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        username_panel.add(username_label);
        username_panel.add(username_field);

        /* BUTTONS */
        confirm_button = new Button("Invite");
        button_panel = new Panel(new FlowLayout(FlowLayout.CENTER));
        button_panel.add(confirm_button);

        /* PANEL */
        main_panel = new Panel();
        TitledBorder titled_border = BorderFactory.createTitledBorder(" Invitation ");
        titled_border.setTitleFont(Chatter.font);
        titled_border.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        main_panel.setBorder(BorderFactory.createCompoundBorder(Chatter.padding, titled_border));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        main_panel.add(username_panel);
        main_panel.add(button_panel);

        /* WINDOW */
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(Chatter.IMG_URL).getImage());
        setResizable(false);
        getRootPane().setDefaultButton(confirm_button);
        add(main_panel);
        pack();
        setLocationRelativeTo(null);
    }
}