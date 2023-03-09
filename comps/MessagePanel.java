package comps;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;

import src.*;

public class MessagePanel extends JPanel {
    public static JTextField message_field;
    public static Button send_button;
    
    public MessagePanel() {
        setLayout(new BorderLayout());
        message_field = new JTextField("");
        message_field.setFont(Chatter.font);

        send_button = new Button("Send");
        send_button.setEnabled(false);

        add(message_field);
        add(send_button, BorderLayout.EAST);
    }
}