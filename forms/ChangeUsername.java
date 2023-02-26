package forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import src.*;
import util.*;
import comps.*;

public class ChangeUsername extends JFrame {

    public static Panel main_panel, new_username_panel, confirm_username_panel, password_panel, button_panel;
    public static Label new_username, confirm_username, password_label;
    public static JTextField new_username_field, confirm_username_field;
    public static JPasswordField password_field;
    public static Button confirm_button;

    public ChangeUsername() {
        
        int label_height;
        String current_username = Database.getUsername(Chatter.user_id);

        /* NEW USERNAME */
        new_username = new Label("new username");
        new_username_field = new JTextField(current_username);
        new_username_field.setFont(Chatter.font);
        label_height = (int) new_username.getPreferredSize().getHeight();
        new_username_field.setPreferredSize(new Dimension(label_height * 5, label_height));
        new_username_panel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        new_username_panel.add(new_username);
        new_username_panel.add(new_username_field);

        /* CONFIRM USERNAME */
        confirm_username = new Label("repeat username");
        confirm_username_field = new JTextField(current_username);
        confirm_username_field.setFont(Chatter.font);
        label_height = (int) new_username.getPreferredSize().getHeight();
        confirm_username_field.setPreferredSize(new Dimension(label_height * 5, label_height));
        confirm_username_panel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        confirm_username_panel.add(confirm_username);
        confirm_username_panel.add(confirm_username_field);

        /* PASSWORD */
        password_label = new Label("password");
        password_field = new JPasswordField("");
        password_field.setFont(Chatter.font);
        password_field.setPreferredSize(new Dimension(label_height * 5, label_height));
        password_panel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        password_panel.add(password_label);
        password_panel.add(password_field);

        /* BUTTONS */
        confirm_button = new Button("Confirm");
        confirm_button.setActionCommand("Confirm username change");
        button_panel = new Panel(new FlowLayout(FlowLayout.CENTER));
        button_panel.add(confirm_button);

        /* PANEL */
        main_panel = new Panel();
        TitledBorder titled_border = BorderFactory.createTitledBorder(" Change username ");
        titled_border.setTitleFont(Chatter.font);
        titled_border.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        main_panel.setBorder(BorderFactory.createCompoundBorder(Chatter.padding, titled_border));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        main_panel.add(new_username_panel);
        main_panel.add(confirm_username_panel);
        main_panel.add(password_panel);
        main_panel.add(button_panel);

        /* WINDOW */
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowListener() {
            public void windowClosing(WindowEvent e) {
                Chatter.chat_frame.setVisible(true);
            }
            public void windowOpened(WindowEvent e) { }
            public void windowClosed(WindowEvent e) { }
            public void windowIconified(WindowEvent e) { }
            public void windowDeiconified(WindowEvent e) { }
            public void windowActivated(WindowEvent e) { }
            public void windowDeactivated(WindowEvent e) { }
        });
        setIconImage(new ImageIcon("imgs/icon.png").getImage());
        setResizable(false);
        getRootPane().setDefaultButton(confirm_button);
        add(main_panel);
        pack();
        setLocationRelativeTo(null);
    }
}