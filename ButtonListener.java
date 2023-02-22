import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class ButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Register" -> {
                String username = Form.username_field.getText();
                String password = new String(Form.password_field.getPassword());
                ResultSet rs = Database.query("SELECT * FROM users WHERE username LIKE '" + username + "';");
                try {
                    if (!username.matches("^[A-Za-z0-9_]{4,16}") || !password.matches("^[A-Za-z0-9_]{4,16}")) {
                        JOptionPane.showMessageDialog(null, "Only 4-16 characters A-Z, \na-z, 0-9 and _ are allowed!", "Error!", JOptionPane.ERROR_MESSAGE);
                    } else if (rs.next()) {
                        if (password.equals(rs.getString("password"))) {
                            Chatter.label.setText(" logged in as " + username);
                            Chatter.form_frame.dispose();
                            Chatter.chat_frame.setVisible(true);
                        } else { 
                            JOptionPane.showMessageDialog(null, "This username already exists! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        Database.stmt.execute("INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')");
                        JOptionPane.showMessageDialog(null, "Successfully added new user.", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }  
                } catch (Exception e) { }
            }
            case "Log in" -> {
                String username = Form.username_field.getText();
                String password = new String(Form.password_field.getPassword());
                ResultSet rs = Database.query("SELECT * FROM users WHERE username LIKE '" + username + "';");
                try {
                    if (rs.next()) {
                        if (password.equals(rs.getString("password"))) {
                            Chatter.label.setText(" logged in as " + username);
                            Chatter.form_frame.dispose();
                            Chatter.chat_frame.setVisible(true);
                        } else { 
                            JOptionPane.showMessageDialog(null, "Wrong password! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Such user doesn't exist! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }  
                } catch (Exception e) { }
            }
        }
    }
}