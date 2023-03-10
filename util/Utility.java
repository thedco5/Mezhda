package util;

import src.*;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import menus.ProfileMenu;

import java.awt.Color;


public class Utility {
    public static void makeDefaults(JComponent component) {
        component.setBackground(Color.WHITE);
        component.setFont(Chatter.font);
    }
    public static void setUser(String username) {
        ProfileMenu.username_mi.setText(username);
        Chatter.chat_frame.setVisible(true);
    }
    public static boolean checkRegex(String username, String password) {
        if (!(checkRegex(username) && checkRegex(password))) {
            // System.out.println(username + "::" + password);
            JOptionPane.showMessageDialog(null, "Only 4-16 characters A-Z, \na-z, 0-9 and _ are allowed!", "Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    public static boolean checkRegex(String str) {
        return str.matches("^[A-Za-z0-9_]{4,16}");
    }
    public static String format(String str) {
        String res = new String(str);
        String[] array = res.split("(?<!!)(?=!/)|(?=/!)|(?<!!..)(?<=!/)|(?<=/!)");
        int counter = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals("!/")) {
                array[i] = "<i>";
                counter++;
            }
            else if (array[i].equals("/!")) { 
                array[i] = "</i>";
                if (counter > 0) counter--;
            }
        }
        res = String.join("", array);
        for (int i = 0; i < counter; i++)
            res += "</i>";
        return res;
    }
}