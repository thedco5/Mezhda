
import java.sql.ResultSet;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
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

    static int FONT_SIZE = 20;

    // static Action keyboard;
    static JFrame chatFrame, formFrame;
    static JPanel mainPanel;
    static JTextField field;
    static JLabel label;
    static JTextPane text;

    static Button button;
    static Database database;
    static String previousUpdate;
    static int user_id;

    static boolean fullScreen;
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    public static void main(String[] args) {
        /* MISCELLANEOUS */
        
        button = new Button();
        database = new Database();
        /* NICKNAME LABEL AND FIELD*/
        label = new JLabel(" log in / register ");
        label.setLocation(0, 0);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        int labelHeight = (int) label.getPreferredSize().getHeight();
        label.setSize(new Dimension(labelHeight * 30, labelHeight));

        // field = new JTextField();
        //field.setBounds(labelWidth, 0, labelHeight * 30, labelHeight);
        /* MESSAGES AREA */
        text = new JTextPane();
        text.setFont(new Font("Arial", Font.PLAIN, 20));
        text.setBounds(0, labelHeight, labelHeight * 30, labelHeight * 18);
        text.setEditable(false);
        /* UPDATING THE MESSAGES */
        /* new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ResultSet rs = Database.executeQuery("SELECT * FROM users;");
                StringBuilder strbr = new StringBuilder(""); // Document format
                try {
                    while (rs.next()) strbr.append(rs.getString("username") + ": " + rs.getString("password") + "\n");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                text.setText(strbr.toString());
            }
        }, 100, 100); */
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    ResultSet rs = Database.executeQuery("SELECT * FROM users;");
                    StringBuilder strbr = new StringBuilder(""); // Document format
                    try {
                        while (rs.next()) strbr.append(rs.getString("username") + ": " + rs.getString("password") + "\n");
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    String str = strbr.toString();
                    if (!str.equals(previousUpdate)) text.setText(str);
                    previousUpdate = new String(str);
                    try { Thread.sleep(100); } // updates every tenth of a second
                    catch (Exception e) { }
                }
            }
        }).start();
        /* MAIN PANEL */
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(labelHeight * 30, labelHeight * 20));
        mainPanel.setFocusable(false);
        // mainPanel.add(field);
        mainPanel.add(label);
        mainPanel.add(text);
        /* FULL SCREEN */
        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F11"), "f11");
        mainPanel.getActionMap().put("f11", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fullScreen = !fullScreen;
                if (fullScreen) device.setFullScreenWindow(chatFrame);
                else device.setFullScreenWindow(null);
            }
        });
        /*  SCREEN MINIMISING */
        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "esc");
        mainPanel.getActionMap().put("esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatFrame.setState(JFrame.ICONIFIED);
            }
        });
        /*  SCREEN CLOSING */
        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("typed a"), "del");
        mainPanel.getActionMap().put("del", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatFrame.setVisible(false);
                chatFrame.dispose();
            }
        });
        /* WINDOW */
        chatFrame = new JFrame("Chatter");
        // chatFrame.setVisible(true);
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.setIconImage(new ImageIcon("icon.png").getImage());
        chatFrame.add(mainPanel);
        chatFrame.pack();
        chatFrame.setLocationRelativeTo(null);

        formFrame = new Form();
    }
}