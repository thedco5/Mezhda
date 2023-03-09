package acts;

import java.sql.ResultSet;
import java.util.Base64;

import src.*;
import util.*;

public class Updater implements Runnable {
    @Override
    public void run() {
        for (;;) {
            // make it finally show le mensajes
            StringBuilder strbr = new StringBuilder(""); // document format
            if (Chatter.group_id == 0) strbr.append("Select chat");
            try ( ResultSet rs = Database.query("SELECT users.username, messages.message FROM messages, members, users WHERE messages.member_id LIKE members.id AND members.user_id LIKE users.id AND members.group_id LIKE " + Chatter.group_id + " ORDER BY messages.id;") ) {
                while (rs.next()) {
                    strbr.append("@" + rs.getString("username") + ": ");
                    String decoded = new String(Base64.getDecoder().decode(rs.getString("message")));
                    strbr.append(decoded + "\n"); 
                }
            } catch (Exception e) { e.printStackTrace(); }
            String str = strbr.toString();
            if (!str.equals(Chatter.prev_update)) Chatter.text.setText(str);
            Chatter.prev_update = new String(str);
            try { Thread.sleep(1000); } catch (Exception e) {} // updates every tenth of a second

            /*String username = Database.getUsername(Chatter.user_id);
            try ( ResultSet rs = Database.query("SELECT * FROM messages WHERE member_id LIKE " + Chatter.user_id + ";") ) {
                StringBuilder strbr = new StringBuilder(""); // Document format
                while (rs.next()) strbr.append("@" + username + ": " + rs.getString("message") + "\n");
                String str = strbr.toString();
                Chatter.text.setText(Chatter.prev_update + "\n\n" + str);
                // Chatter.text.setCaretPosition(Chatter.text.getDocument().getLength());
                Thread.sleep(1000); // updates every tenth of a second
            } catch (Exception e) { e.printStackTrace(); }*/
        }
    }
}