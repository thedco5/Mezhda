
import java.sql.ResultSet;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

public class Chatter {

    // static Action keyboard;
    static JFrame chat_frame, form_frame;
    static JPanel main_panel;
    static JTextField field;
    static JLabel label;
    static JTextPane text;

    static Font font;
    static Button button;
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
        button = new Button();
        database = new Database();

        /* TOP LABEL AND FIELD*/
        label = new JLabel(" label ");
        label.setLocation(0, 0);
        label.setFont(font);
        int label_height = (int) label.getPreferredSize().getHeight();
        label.setSize(new Dimension(label_height * 30, label_height));

        /* MESSAGES AREA */
        text = new JTextPane();
        text.setFont(font);
        text.setBounds(0, label_height, label_height * 30, label_height * 18);
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

        /* MAIN PANEL */
        main_panel = new JPanel();
        main_panel.setLayout(null);
        main_panel.setPreferredSize(new Dimension(label_height * 30, label_height * 20));
        main_panel.setFocusable(false);
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
                chat_frame.setState(JFrame.ICONIFIED);
            }
        });

        /*  SCREEN CLOSING */
        main_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("typed a"), "del");
        main_panel.getActionMap().put("del", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat_frame.setVisible(false);
                chat_frame.dispose();
            }
        });

        /* WINDOW */
        chat_frame = new JFrame("Chatter");
        chat_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chat_frame.setIconImage(new ImageIcon("icon.png").getImage());
        chat_frame.add(main_panel);
        chat_frame.pack();
        chat_frame.setLocationRelativeTo(null);

        form_frame = new Form();
    }
}