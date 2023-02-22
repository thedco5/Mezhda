import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Form extends JFrame {

    static JPanel main_panel, username_panel, password_panel, button_panel;
    static JLabel username_label, password_label;
    static JTextField username_field;
    static JPasswordField password_field;
    static JButton login_button, register_button;

    Form() {
        
        int label_height;

        /* USERNAME */
        username_label = new JLabel("username");
        username_label.setFont(Chatter.font);
        username_field = new JTextField("");
        label_height = (int) username_label.getPreferredSize().getHeight();
        System.out.println(username_label.getPreferredSize().getHeight());
        username_field.setPreferredSize(new Dimension(label_height * 5, label_height));
        username_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        username_panel.add(username_label);
        username_panel.add(username_field);
        /* PASSWORD */
        password_label = new JLabel("password");
        password_field = new JPasswordField("");
        password_field.setPreferredSize(new Dimension(label_height * 5, label_height));
        password_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        password_panel.add(password_label);
        password_panel.add(password_field);
        /* BUTTONS */
        login_button = new JButton("Sign in");
        login_button.setFocusPainted(false);
        login_button.addActionListener(Chatter.button);
        /* register_button = new JButton("Register");
        register_button.setFocusPainted(false);
        register_button.addActionListener(Chatter.button); */
        button_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        button_panel.add(login_button);
        // button_panel.add(register_button);
        /* PANEL */
        main_panel = new JPanel();
        main_panel.setBorder(BorderFactory.createTitledBorder("Login / Registration"));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        main_panel.add(username_panel);
        main_panel.add(password_panel);
        main_panel.add(button_panel);
        /* SETTING UP THE WINDOW */
        // setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icon.png").getImage());
        setResizable(false);
        getRootPane().setDefaultButton(login_button);
        add(main_panel);
        pack();
        setLocationRelativeTo(null);
    }
}