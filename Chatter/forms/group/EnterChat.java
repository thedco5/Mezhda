package forms.group;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import src.*;
import comps.*;

public class EnterChat extends JFrame {

    public static Panel main_panel, groupname_panel, button_panel;
    public static Label groupname_label;
    public static JTextField groupname_field;
    public static Button confirm_button;

    public EnterChat() {

        /* NEW GROUP */
        groupname_label = new Label("groupname: ");
        groupname_field = new JTextField();
        groupname_field.setFont(Chatter.font);
        groupname_field.setPreferredSize(new Dimension(Chatter.base_height * 5, Chatter.base_height));
        groupname_panel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        groupname_panel.add(groupname_label);
        groupname_panel.add(groupname_field);

        /* BUTTONS */
        confirm_button = new Button("Join");
        button_panel = new Panel(new FlowLayout(FlowLayout.CENTER));
        button_panel.add(confirm_button);

        /* PANEL */
        main_panel = new Panel();
        TitledBorder titled_border = BorderFactory.createTitledBorder(" Enter group ");
        titled_border.setTitleFont(Chatter.font);
        titled_border.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        main_panel.setBorder(BorderFactory.createCompoundBorder(Chatter.padding, titled_border));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        main_panel.add(groupname_panel);
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