package src;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Graphics;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import acts.*;
import forms.prof.*;
import menus.*;
import comps.*;
import comps.menus.*;
import comps.scrolls.*;
import util.*;

import javax.swing.JComponent;

public class Chatter { // ~ 1300 lines fully in this project

    // static Action keyboard;
    public static JFrame chat_frame;
    public static JFrame form_frame, change_username_frame, delete_account_frame, change_password_frame;
    public static JFrame new_group_frame, exit_group_frame, invite_frame, enter_chat_frame;
    public static JPanel main_panel, side_panel;
    public static JTextField field;
    public static JSplitPane split_pane;

    public static Label label;
    public static TextPane text;
    public static MenuBar menu_bar;
    public static SideMenu side_menu;
    public static Menu profile_menu, groups_menu, window_menu;
    public static ScrollBar group_scroll_bar, text_scroll_bar;
    public static ScrollPane group_scroll_pane, text_scroll_pane;

    public static Font font;
    public static Border padding;
    public static ButtonListener button_listener;
    public static MenuListener menu_listener;
    public static GroupListener group_listener;

    public static String prev_update;

    public static final String IMG_URL = "imgs/icon.png";
    public static int base_height;
    public static int user_id, group_id;
    public static boolean full_screen;
    public static GraphicsDevice gr_dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    public static void main(String[] args) {

        /* CUSTOM FONT */
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Lexend.ttf")));
            font = new Font("Lexend", Font.PLAIN, 20);
        } catch (Exception e) {
            font = new Font("Arial", Font.PLAIN, 20);
        }
        // font = new Font("Arial", Font.PLAIN, 20);

        /* OTHER */
        padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        button_listener = new ButtonListener();
        menu_listener = new MenuListener();
        group_listener = new GroupListener();
        new Database();

        /* TOP LABEL AND FIELD */
        label = new Label(" label ");
        // label.setLocation(0, 24);
        base_height = (int) (label.getPreferredSize().getHeight() * 1.20);
        label.setPreferredSize(new Dimension(base_height * 30, base_height));

        /* MESSAGES AREA */
        text = new TextPane(); 

        /* TOP MENU */
        menu_bar = new MenuBar();
        menu_bar.add(profile_menu = new ProfileMenu("Profile", KeyEvent.VK_P));
        menu_bar.add(new MenuPipe());
        menu_bar.add(groups_menu = new GroupMenu("Groups", KeyEvent.VK_G));
        menu_bar.add(new MenuPipe());
        menu_bar.add(window_menu = new WindowMenu("Window", KeyEvent.VK_W));
        menu_bar.setSize(base_height * 30, base_height);

        /* SIDE MENU */
        side_menu = new SideMenu(); 
        side_menu.refreshGroups();
        side_panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        side_panel.setBackground(new Color(0xeeeeee));
        side_panel.add(side_menu);

        /* GROUP SCROLL BAR */
        group_scroll_bar = new ScrollBar();
        text_scroll_bar = new ScrollBar();
        group_scroll_pane = new ScrollPane(side_panel, group_scroll_bar);
        group_scroll_pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        text_scroll_pane = new ScrollPane(text, text_scroll_bar) {
            public Dimension getMinimumSize() {
                return text.getMinimumSize();
            }
        };

        /* SPLIT PANE */
        split_pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, group_scroll_pane, text_scroll_pane);
        split_pane.setDividerSize((int) group_scroll_bar.getPreferredSize().getWidth() / 2);
        split_pane.setDividerLocation((int) group_scroll_pane.getPreferredSize().getWidth());
        split_pane.setContinuousLayout(true);
        split_pane.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    public void setBorder(Border b) {}
                    public void paint(Graphics g) {
                        g.setColor(new Color(0xeeeeee));
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });

        /* MAIN PANEL */
        main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        main_panel.setFocusable(false);
        main_panel.add(menu_bar, BorderLayout.NORTH);
        main_panel.add(label, BorderLayout.SOUTH);
        main_panel.add(split_pane);

        /* FULL SCREEN */
        main_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F11"), "f11");
        main_panel.getActionMap().put("f11", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                menu_listener.actionPerformed(new ActionEvent(this, 0, "Toggle fullscreen"));
            }
        });

        /* SCREEN MINIMISING */
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
        // chat_frame.setVisible(true);
        chat_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chat_frame.setIconImage(new ImageIcon(IMG_URL).getImage());
        // chat_frame.setResizable(false);
        chat_frame.add(main_panel);
        chat_frame.pack();
        chat_frame.setLocationRelativeTo(null);

        /* UPDATING THE MESSAGES */
        new Thread(new Updater()).start();
    }

}