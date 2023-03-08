package acts;

import java.sql.ResultSet;

import src.*;
import util.*;

public class Updater implements Runnable {
    @Override
    public void run() {
        for (;;) {
            try ( ResultSet rs = Database.query("SELECT * FROM users;") ) {
                StringBuilder strbr = new StringBuilder(""); // Document format
                while (rs.next()) strbr.append("@" + rs.getString("username") + ": " + rs.getString("password") + "\n");
                String str = strbr.toString();
                if (!str.equals(Chatter.prev_update)) Chatter.text.setText(str);
                Chatter.prev_update = new String(str);
                // Thread.sleep(1000); // updates every tenth of a second
            } catch (Exception e) { e.printStackTrace(); }
            String username = Database.getUsername(Chatter.user_id);
            try ( ResultSet rs = Database.query("SELECT * FROM messages WHERE user_id LIKE " + Chatter.user_id + ";") ) {
                StringBuilder strbr = new StringBuilder(""); // Document format
                while (rs.next()) strbr.append("@" + username + ": " + rs.getString("message") + "\n");
                String str = strbr.toString();
                Chatter.text.setText(Chatter.prev_update + "\n\n" + str);
                Thread.sleep(1000); // updates every tenth of a second
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}