package acts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import forms.prof.*;
import src.*;
import util.*;

public class ButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Register" -> {
                String username = LogForm.username_field.getText();
                String password = new String(LogForm.password_field.getPassword());
                try (ResultSet rs = Database.selectFromUsers(username)) {
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
                } catch (Exception e) { e.printStackTrace(); }
            }
            case "Log in" -> {
                String username = LogForm.username_field.getText();
                String password = new String(LogForm.password_field.getPassword());
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
                } catch (Exception e) { e.printStackTrace(); }
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
                        try ( ResultSet rs = Database.selectFromUsers(Chatter.user_id) ) {
                            if (rs.next()) {
                                if (Password.compare(password, rs.getString("password"))) {
                                    Database.query("UPDATE users SET username = '" + username + "' WHERE id LIKE '" + Chatter.user_id + "';");
                                    Utility.setUser(username);
                                    Chatter.change_username_frame.dispose();
                                } else { 
                                    JOptionPane.showMessageDialog(null, "Wrong password! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } catch (Exception e) { e.printStackTrace(); }
                    } else JOptionPane.showMessageDialog(null, "Usernames don't match", "Error!", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) { e.printStackTrace(); }
            }
            case "Change password" -> {
                String password = new String(ChangePassword.password_field.getPassword());
                String new_password = new String(ChangePassword.new_password_field.getPassword());
                if (!Utility.checkRegex(password, new_password)) break;
                try {
                    if (new_password.equals(new String(ChangePassword.confirm_password_field.getPassword()))) {
                        ResultSet rs = Database.selectFromUsers(Chatter.user_id);
                        if (rs.next()) {
                            if (Password.compare(password, rs.getString("password"))) {
                                Database.query("UPDATE users SET password = '" + Password.encode(new_password) + "' WHERE id LIKE '" + Chatter.user_id + "';");
                                Chatter.change_password_frame.dispose();
                                JOptionPane.showMessageDialog(null, "Successfully changed the password.", "Success!", JOptionPane.INFORMATION_MESSAGE);
                                Chatter.form_frame.setVisible(true);
                            } else { 
                                JOptionPane.showMessageDialog(null, "Wrong old password! \nTry again!", "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else JOptionPane.showMessageDialog(null, "New password doesn't match", "Error!", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) { e.printStackTrace(); }
            }
            case "Delete account" -> {
                String password = new String(DeleteAccount.password_field.getPassword());
                if (!Utility.checkRegex(password, password)) break;
                try ( ResultSet rs = Database.selectFromUsers(Chatter.user_id) ) {
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
                } catch (Exception e) { e.printStackTrace(); }
            }
            default -> System.out.println(ae.getActionCommand());
        }
    }
}