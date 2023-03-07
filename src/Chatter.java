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
import forms.*;
import menus.*;
import comps.*;
import util.*;

import javax.swing.JComponent;

public class Chatter {

    // static Action keyboard;
    public static JFrame chat_frame, form_frame, change_username_frame, delete_account_frame, change_password_frame;
    public static JPanel main_panel, side_panel;
    public static JTextField field;
    public static JSplitPane split_pane;

    public static Label label;
    public static TextPane text;
    public static MenuBar menu_bar;
    public static SideMenu side_menu;
    public static ProfileMenu profile_menu;
    public static Menu groups_menu, window_menu;
    public static MenuItem fullscreen_mi, minimize_mi, screen_size_mi;
    public static ScrollBar group_scroll_bar, text_scroll_bar;
    public static ScrollPane group_scroll_pane, text_scroll_pane;

    public static Font font;
    public static Border padding;
    public static ButtonListener button_listener;
    public static MenuListener menu_listener;
    public static GroupListener group_listener;

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
        label.setLocation(0, 24);
        int label_height = (int) label.getPreferredSize().getHeight();
        label.setPreferredSize(new Dimension(label_height * 30, label_height));

        /* MESSAGES AREA */
        text = new TextPane();

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

        /* TOP MENU */
        menu_bar = new MenuBar();
        menu_bar.add(profile_menu = new ProfileMenu("Profile", KeyEvent.VK_P));
        menu_bar.add(new MenuPipe());
        menu_bar.add(groups_menu = new Menu("Groups", KeyEvent.VK_G));
        menu_bar.add(new MenuPipe());
        menu_bar.add(window_menu);
        menu_bar.setSize(label_height * 30, label_height);

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
                    public void setBorder(Border b) {
                    }

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
        chat_frame.setVisible(true);
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