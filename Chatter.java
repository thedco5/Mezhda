import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Timer;
import java.util.TimerTask;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.BorderFactory;

public class Chatter {

    static final String URL = "jdbc:mysql://127.0.0.1/chatter";
    static final String USER = "root";
    static final String PASS = "msqlroot";

    static Action keyboard;
    static JFrame frame;
    static JPanel mainPanel;
    static JTextField field;
    static JLabel label;
    static JTextPane text;

    static boolean fullScreen;
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    public static void main(String[] args) {
        /* NICKNAME LABEL AND FIELD*/
        label = new JLabel(" nickname: ");
        label.setLocation(0, 0);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setSize(label.getPreferredSize());
        field = new JTextField();
        int labelWidth = (int) label.getPreferredSize().getWidth();
        int labelHeight = (int) label.getPreferredSize().getHeight();
        field.setBounds(labelWidth, 0, labelHeight * 5, labelHeight);
        /* MESSAGES AREA */
        text = new JTextPane();
        text.setFont(new Font("Arial", Font.PLAIN, 20));
        text.setBounds(0, labelHeight, labelHeight * 30, labelHeight * 18);
        text.setEditable(false);
        /* UPDATING THE MESSAGES */
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try (
                    Connection conn = DriverManager.getConnection(URL, USER, PASS);
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
                ) {
                    StringBuilder strbr = new StringBuilder(""); // Document format
                    while (rs.next()) strbr.append(rs.getString("username") + ": " + rs.getString("password") + "\n");
                    text.setText(strbr.toString());
                } catch (Exception e) { e.printStackTrace(); }
            }
        }, 50, 50);
        /* MAIN PANEL */
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(labelHeight * 30, labelHeight * 20));
        mainPanel.setFocusable(false);
        mainPanel.add(field);
        mainPanel.add(label);
        mainPanel.add(text);


        // mainPanel.setBorder(BorderFactory.createTitledBorder("Messages"));


        /* FULL SCREEN */
        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F11"), "f11");
        mainPanel.getActionMap().put("f11", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fullScreen = !fullScreen;
                if (fullScreen) device.setFullScreenWindow(frame);
                else device.setFullScreenWindow(null);
            }
        });
        /*  SCREEN MINIMISING */
        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "esc");
        mainPanel.getActionMap().put("esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }
        });
        /*  SCREEN CLOSING */
        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("typed a"), "del");
        mainPanel.getActionMap().put("del", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
            }
        });
        /* WINDOW */
        frame = new JFrame("Chatter");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon("icon.png").getImage());
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}