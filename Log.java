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

public class Login extends JFrame {

    JPanel mainPanel, usernamePanel, passwordPanel, buttonsPanel;
    JLabel usernameLabel, passwordLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, registerButton;

    Login() {
        
        int labelHeight;

        /* USERNAME */
        usernameLabel = new JLabel("username");
        usernameField = new JTextField();
        labelHeight = (int) usernameLabel.getPreferredSize().getHeight();
        usernameField.setPreferredSize(new Dimension(labelHeight * 5, labelHeight));
        usernamePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        /* PASSWORD */
        passwordLabel = new JLabel("password");
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(labelHeight * 5, labelHeight));
        passwordPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        /* BUTTONS */
        loginButton = new JButton("Log in");
        registerButton = new JButton("Register");
        loginButton.setFocusPainted(false);
        registerButton.setFocusPainted(false);
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(loginButton);
        buttonsPanel.add(registerButton);
        /* PANEL */
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Login / Registration"));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(usernamePanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(buttonsPanel);
        /* SETTING UP THE WINDOW */
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icon.png").getImage());
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
}