import java.sql.ResultSet;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.JComponent;

public class Chatter {

    // static Action keyboard;
    static JFrame chat_frame, form_frame;
    static JPanel main_panel;
    static JTextField field;
    static JTextPane text;

    static Label label;
    static MenuBar menu_bar;
    static Menu profile_menu, change_profile_sm;
    static MenuItem username_mi, change_username_mi, change_password_mi, sign_out_mi;

    static Font font;
    static Border padding;
    static ButtonListener button_listener;
    static Database database;

    static String prev_update;
    
    static int user_id;
    static boolean full_screen;
    static GraphicsDevice gr_dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    public static void main(String[] args) {

        /* CUSTOM FONT */
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/.ttf")));
            font = new Font("", Font.PLAIN, 20);
        } catch (Exception e) { font = new Font("Arial", Font.PLAIN, 20); }

        /* OTHER */
        padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        button_listener = new ButtonListener();
        database = new Database();

        /* TOP LABEL AND FIELD*/
        label = new Label(" label ");
        label.setLocation(0, 24);
        int label_height = (int) label.getPreferredSize().getHeight();
        label.setSize(new Dimension(label_height * 30, label_height));

        /* MESSAGES AREA */
        text = new JTextPane();
        text.setFont(font);
        text.setBounds(0, label_height * 2, label_height * 30, label_height * 18);
        text.setEditable(false);

        /* UPDATING THE MESSAGES */
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    ResultSet rs = Database.query("SELECT * FROM users;");
                    StringBuilder strbr = new StringBuilder(""); // Document format
                    try {
                        while (rs.next()) strbr.append(rs.getString("username") + ": " + rs.getString("password") + "\n");
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    String str = strbr.toString();
                    if (!str.equals(prev_update)) text.setText(str);
                    prev_update = new String(str);
                    try { Thread.sleep(100); } // updates every tenth of a second
                    catch (Exception e) { }
                }
            }
        }).start();

        /* TOP MENU */
        username_mi = new MenuItem("Logged in as: ");
        username_mi.setEnabled(false);
        change_username_mi = new MenuItem("Change username", KeyEvent.VK_U);
        change_password_mi = new MenuItem("Change password", KeyEvent.VK_P);
        change_profile_sm = new Menu("Change profile", KeyEvent.VK_C);
        change_profile_sm.add(change_username_mi);
        change_profile_sm.add(change_password_mi);
        sign_out_mi = new MenuItem("Sign out", KeyEvent.VK_S);

        profile_menu = new Menu("Profile", KeyEvent.VK_P);
        profile_menu.add(username_mi);
        profile_menu.add(change_profile_sm);
        profile_menu.add(sign_out_mi);
        Menu menu_pipe = new Menu("I");
        menu_pipe.setEnabled(false);

        menu_bar = new MenuBar();
        menu_bar.add(profile_menu);
        menu_bar.add(menu_pipe);
        menu_bar.setBounds(0, 0, label_height * 30, label_height);

        /* MAIN PANEL */
        main_panel = new JPanel();
        main_panel.setLayout(null);
        main_panel.setPreferredSize(new Dimension(label_height * 30, label_height * 20));
        main_panel.setFocusable(false);
        main_panel.add(menu_bar);
        main_panel.add(label);
        main_panel.add(text);

        /* FULL SCREEN */
        main_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F11"), "f11");
        main_panel.getActionMap().put("f11", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                full_screen = !full_screen;
                if (full_screen) gr_dev.setFullScreenWindow(chat_frame);
                else gr_dev.setFullScreenWindow(null);
            }
        });

        /*  SCREEN MINIMISING */
        main_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "esc");
        main_panel.getActionMap().put("esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("+ " + e.getSource());
                chat_frame.setState(JFrame.ICONIFIED);
            }
        });

        /* WINDOWS */
        form_frame = new Form();
        chat_frame = new JFrame("Chatter");
        // chat_frame.setVisible(true);
        chat_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chat_frame.setIconImage(new ImageIcon("icon.png").getImage());
        chat_frame.setResizable(false);
        chat_frame.add(main_panel);
        chat_frame.pack();
        chat_frame.setLocationRelativeTo(null);
    }

}