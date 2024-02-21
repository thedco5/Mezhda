package forms.prof;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import src.*;
import comps.*;

public class LogForm extends JFrame {

    public static Panel main_panel, username_panel, password_panel, button_panel;
    public static Label username_label, password_label;
    public static JTextField username_field;
    public static JPasswordField password_field;
    public static Button login_button, register_button;

    public LogForm() {

        /* USERNAME */
        username_label = new Label("username");
        username_field = new JTextField("");
        username_field.setFont(Chatter.font);
        username_field.setPreferredSize(new Dimension(Chatter.base_height * 5, Chatter.base_height));
        username_panel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        username_panel.add(username_label);
        username_panel.add(username_field);

        /* PASSWORD */
        password_label = new Label("password");
        password_field = new JPasswordField("");
        password_field.setFont(Chatter.font);
        password_field.setPreferredSize(new Dimension(Chatter.base_height * 5, Chatter.base_height));
        password_panel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        password_panel.add(password_label);
        password_panel.add(password_field);

        /* BUTTONS */
        login_button = new Button("Log in");
        register_button = new Button("Register");
        button_panel = new Panel(new FlowLayout(FlowLayout.CENTER));
        button_panel.add(login_button);
        button_panel.add(register_button);

        /* PANEL */
        main_panel = new Panel();
        TitledBorder titled_border = BorderFactory.createTitledBorder(" Login / Registration ");
        titled_border.setTitleFont(Chatter.font);
        titled_border.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        main_panel.setBorder(BorderFactory.createCompoundBorder(Chatter.padding, titled_border));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        main_panel.add(username_panel);
        main_panel.add(password_panel);
        main_panel.add(button_panel);

        /* WINDOW */
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(Chatter.IMG_URL).getImage());
        setResizable(false);
        getRootPane().setDefaultButton(login_button);
        add(main_panel);
        pack();
        setLocationRelativeTo(null); // centers window
    }
}