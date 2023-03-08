package forms.prof;

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
import javax.swing.border.TitledBorder;

import src.*;
import comps.*;

public class DeleteAccount extends JFrame {

    public static Panel main_panel, password_panel, button_panel;
    public static Label password_label;
    public static JPasswordField password_field;
    public static Button confirm_button;

    public DeleteAccount() {
        
        int label_height;

        /* PASSWORD */
        password_label = new Label("password");
        label_height = (int) password_label.getPreferredSize().getHeight();
        password_field = new JPasswordField("");
        password_field.setFont(Chatter.font);
        password_field.setPreferredSize(new Dimension(label_height * 5, label_height));
        password_panel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        password_panel.add(password_label);
        password_panel.add(password_field);

        /* BUTTONS */
        confirm_button = new Button("Confirm");
        confirm_button.setActionCommand("Delete account");
        button_panel = new Panel(new FlowLayout(FlowLayout.CENTER));
        button_panel.add(confirm_button);

        /* PANEL */
        main_panel = new Panel();
        TitledBorder titled_border = BorderFactory.createTitledBorder(" Delete account ");
        titled_border.setTitleFont(Chatter.font);
        titled_border.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        main_panel.setBorder(BorderFactory.createCompoundBorder(Chatter.padding, titled_border));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
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
        setIconImage(new ImageIcon(Chatter.IMG_URL).getImage());
        setResizable(false);
        getRootPane().setDefaultButton(confirm_button);
        add(main_panel);
        pack();
        setLocationRelativeTo(null);
    }
}