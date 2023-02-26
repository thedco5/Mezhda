package acts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import forms.*;
import src.*;
import util.*;

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
                        if (Password.compare(password, rs.getString("password"))) {
                            Chatter.user_id = rs.getInt("id");
                            Utility.setUser(username);
                            Chatter.form_frame.setVisible(false);
                        } else { 
                            JOptionPane.showMessageDialog(null, "This username already exists! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        Database.stmt.execute("INSERT INTO users (username, password) VALUES ('" + username + "', '" + Password.encode(password) + "');");
                        JOptionPane.showMessageDialog(null, "Successfully added new user.", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }  
                } catch (Exception e) { System.err.println(e.getLocalizedMessage()); }
            }
            case "Log in" -> {
                String username = Form.username_field.getText();
                String password = new String(Form.password_field.getPassword());
                ResultSet rs = Database.selectFromUsers(username);
                try {
                    if (!Utility.checkRegex(username, password)) break;
                    if (rs.next()) {
                        if (Password.compare(password, rs.getString("password"))) {
                            Chatter.user_id = rs.getInt("id");
                            Utility.setUser(username);
                            Chatter.form_frame.setVisible(false);
                        } else { 
                            JOptionPane.showMessageDialog(null, "Wrong password! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Such user doesn't exist! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }  
                } catch (Exception e) { System.err.println(e.getLocalizedMessage()); }
            }
            case "Change username" -> {
                String username = ChangeUsername.new_username_field.getText();
                String password = new String(ChangeUsername.password_field.getPassword());
                if (!Utility.checkRegex(username, password)) break;
                try {
                    if (username.equals(ChangeUsername.confirm_username_field.getText())) {
                        if (Database.selectFromUsers(username).next()) {
                            JOptionPane.showMessageDialog(null, "Username already exists! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        ResultSet rs = Database.selectFromUsers(Chatter.user_id);
                        if (rs.next()) {
                            if (Password.compare(password, rs.getString("password"))) {
                                Database.query("UPDATE users SET username = '" + username + "' WHERE username LIKE '" + Database.getUsername(Chatter.user_id) + "';");
                                Utility.setUser(username);
                                Chatter.change_username_frame.dispose();
                            } else { 
                                JOptionPane.showMessageDialog(null, "Wrong password! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else JOptionPane.showMessageDialog(null, "Usernames don't match", "Error!", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) { System.err.println(e.getLocalizedMessage()); }
            }
            case "Delete account" -> {
                String password = new String(DeleteAccount.password_field.getPassword());
                if (!Utility.checkRegex(password, password)) break;
                try {
                    ResultSet rs = Database.selectFromUsers(Chatter.user_id);
                    if (rs.next()) {
                        if (Password.compare(password, rs.getString("password"))) {
                            Database.query("DELETE FROM users WHERE id LIKE " + Chatter.user_id + ";");
                            Chatter.user_id = 0;
                            Chatter.delete_account_frame.dispose();
                            JOptionPane.showMessageDialog(null, "Successfully deleted the account.", "Success!", JOptionPane.INFORMATION_MESSAGE);
                            Chatter.form_frame.setVisible(true);
                        } else { 
                            JOptionPane.showMessageDialog(null, "Wrong password! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception e) { System.err.println(e.getLocalizedMessage()); }
            }
            default -> System.out.println(ae.getActionCommand());
        }
    }
}