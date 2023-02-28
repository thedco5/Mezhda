package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.ComponentOrientation;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import acts.*;
import forms.*;
import comps.*;
import util.*;

import javax.swing.JComponent;

public class Chatter {

    // static Action keyboard;
    public static JFrame chat_frame, form_frame, change_username_frame, delete_account_frame, change_password_frame;
    public static JPanel main_panel, side_panel;
    public static JTextField field;
    public static JTextPane text;

    public static Label label;
    public static MenuBar menu_bar;
    public static SideMenu side_menu;
    public static Menu profile_menu, change_profile_sm, groups_menu, window_menu;
    public static MenuItem username_mi, change_username_mi, change_password_mi, delete_profile_mi, sign_out_mi, fullscreen_mi, minimize_mi, screen_size_mi;

    public static Font font;
    public static Border padding;
    public static ButtonListener button_listener;
    public static MenuListener menu_listener;

    public static String prev_update;
    
    public static final String IMG_URL = "imgs/icon.png";
    public static int user_id;
    public static boolean full_screen;
    public static GraphicsDevice gr_dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    public static void main(String[] args) {

        /* CUSTOM FONT */
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Lexend.ttf")));
            font = new Font("Lexend", Font.PLAIN, 20);
        } catch (Exception e) { font = new Font("Arial", Font.PLAIN, 20); } 
        // font = new Font("Arial", Font.PLAIN, 20);

        /* OTHER */
        padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        button_listener = new ButtonListener();
        menu_listener = new MenuListener();
        new Database();

        /* TOP LABEL AND FIELD*/
        label = new Label(" label ");
        label.setLocation(0, 24);
        int label_height = (int) label.getPreferredSize().getHeight();
        label.setSize(new Dimension(label_height * 30, label_height));

        /* MESSAGES AREA */
        text = new JTextPane();
        text.setFont(font);
        text.setSize(label_height * 30, label_height * 18);
        text.setEditable(false);

        /* UPDATING THE MESSAGES */
        new Thread(new Updater()).start();

        /* PROFILE MENU */
        username_mi = new MenuItem(" - ");
        username_mi.setEnabled(false);
        change_username_mi = new MenuItem("Change username", KeyEvent.VK_U);
        change_password_mi = new MenuItem("Change password", KeyEvent.VK_P);
        change_profile_sm = new Menu("Change profile", KeyEvent.VK_C);
        change_profile_sm.add(change_username_mi);
        change_profile_sm.add(change_password_mi);
        sign_out_mi = new MenuItem("Sign out", KeyEvent.VK_S);
        delete_profile_mi = new MenuItem("Delete account");
        delete_profile_mi.setForeground(Color.RED.darker()); 

        /* GROUPS MENU */
        groups_menu = new Menu("Groups", KeyEvent.VK_G);

        /* WINDOW MENU */
        screen_size_mi = new MenuItem("0×0 px", KeyEvent.VK_X);
        screen_size_mi.setActionCommand("Screen size");
        screen_size_mi.setForeground(Color.GRAY);
        fullscreen_mi = new MenuItem("Toggle fullscreen", KeyEvent.VK_T);
        fullscreen_mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
        minimize_mi = new MenuItem("Minimise window", KeyEvent.VK_M);
        minimize_mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        window_menu = new Menu("Window", KeyEvent.VK_W);
        window_menu.add(screen_size_mi);
        window_menu.add(fullscreen_mi);
        window_menu.add(minimize_mi);

        /* MENU TABS */
        profile_menu = new Menu("Profile", KeyEvent.VK_P);
        profile_menu.add(username_mi);
        profile_menu.add(change_profile_sm);
        profile_menu.add(sign_out_mi);
        profile_menu.add(delete_profile_mi);

        /* TOP MENU */
        menu_bar = new MenuBar();
        menu_bar.add(profile_menu);
        menu_bar.add(new MenuPipe());
        menu_bar.add(groups_menu);
        menu_bar.add(new MenuPipe());
        menu_bar.add(window_menu);
        menu_bar.setSize(label_height * 30, label_height);

        /* SIDE MENU */
        side_menu = new SideMenu();
        GroupButton group_button = new GroupButton("chats");
        group_button.setEnabled(false);
        // group_button.setBorder(padding);
        side_menu.add(group_button);
        side_menu.add(new GroupButton("group01"));
        side_menu.add(new GroupButton("hej!"));
        side_menu.add(new GroupButton("group02"));
        side_menu.add(new GroupButton("group12"));
        side_menu.add(new GroupButton("notes"));
        side_menu.add(new GroupButton("test"));
        side_menu.add(new GroupButton("test1"));
        side_menu.add(new GroupButton("test_A"));
        side_menu.add(new GroupButton("c'mon"));
        side_panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        side_panel.setBackground(new Color(0xeeeeee));
        side_panel.add(side_menu);

        /* GROUP SCROLL BAR */
        JScrollBar scroll_bar = new JScrollBar();
        scroll_bar.setUI(new Scroll());
        scroll_bar.setUnitIncrement((int) groups_menu.getComponent().getPreferredSize().getHeight());
        scroll_bar.setBackground(Color.WHITE);
        scroll_bar.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.LIGHT_GRAY));
        JScrollPane scroll_pane = new JScrollPane();
        scroll_pane.setViewportView(side_panel);
        scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        scroll_pane.setVerticalScrollBar(scroll_bar);
        scroll_pane.setBorder(BorderFactory.createEmptyBorder());

        /* MAIN PANEL */
        main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        main_panel.setPreferredSize(new Dimension(label_height * 30, label_height * 20));
        main_panel.setFocusable(false);
        main_panel.add(menu_bar, BorderLayout.NORTH);
        main_panel.add(scroll_pane, BorderLayout.WEST);
        main_panel.add(label, BorderLayout.SOUTH);
        main_panel.add(text, BorderLayout.CENTER);

        /* FULL SCREEN */
        main_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F11"), "f11");
        main_panel.getActionMap().put("f11", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                menu_listener.actionPerformed(new ActionEvent(this, 0, "Toggle fullscreen"));
            }
        });

        /*  SCREEN MINIMISING */
        main_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "esc");
        main_panel.getActionMap().put("esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat_frame.setState(JFrame.ICONIFIED);
            }
        });

        /* WINDOWS */
        form_frame = new LogForm();
        chat_frame = new JFrame("Chatter");
        chat_frame.setVisible(true);
        chat_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chat_frame.setIconImage(new ImageIcon(IMG_URL).getImage());
        // chat_frame.setResizable(false);
        chat_frame.add(main_panel);
        chat_frame.pack();
        chat_frame.setLocationRelativeTo(null);
    }

}