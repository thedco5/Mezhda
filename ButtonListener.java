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
                ResultSet rs = Database.selectFromUsers(username);
                try {
                    if (!Utility.checkRegex(username, password)) break;
                    if (rs.next()) {
                        if (password.equals(rs.getString("password"))) {
                            Chatter.user_id = rs.getInt("id");
                            Utility.setUser(username);
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
                ResultSet rs = Database.selectFromUsers(username);
                try {
                    if (!Utility.checkRegex(username, password)) break;
                    if (rs.next()) {
                        if (password.equals(rs.getString("password"))) {
                            Chatter.user_id = rs.getInt("id");
                            Utility.setUser(username);
                        } else { 
                            JOptionPane.showMessageDialog(null, "Wrong password! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Such user doesn't exist! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }  
                } catch (Exception e) { }
            }
            case "Sign out" -> {
                Chatter.user_id = 0;
                Chatter.chat_frame.dispose();
                Chatter.form_frame = new Form();
            }
            default -> System.out.println(ae.getActionCommand());
        }
    }
}