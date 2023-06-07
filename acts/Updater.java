package acts;

import java.sql.ResultSet;
import java.util.Base64;

// import javax.swing.text.StyledDocument;
// import javax.swing.text.Style;
// import javax.swing.text.StyleConstants;
// import java.awt.Color;

import src.*;
import util.*;

public class Updater implements Runnable {
    @Override
    public void run() {
        for (;;) {
            // make it finally show le mensajes
            StringBuilder strbr = new StringBuilder(""); // document format
            strbr.append("<!DOCTYPE html> <html> <head><style> body { font-family: 'Ubuntu'; font-size: 20; font-weight: bold; } .gray { color: gray; } </head></style> <body> ");
            
            if (Chatter.group_id == 0) 
                strbr.append("<i>Select chat<i>");
            
            try ( ResultSet rs = Database.query("SELECT users.username, messages.message FROM messages, members, users WHERE messages.member_id LIKE members.id AND members.user_id LIKE users.id AND members.group_id LIKE " + Chatter.group_id + " ORDER BY messages.id;") ) {
                while (rs.next()) {
                    strbr.append("<span class='gray'>" + rs.getString("username") + "</span> ");
                    String decoded = new String(Base64.getDecoder().decode(rs.getString("message")));
                    decoded = decoded.replaceAll("<", "&lt;");
                    strbr.append(Utility.format(decoded) + "<br/>"); 
                }
            } catch (Exception e) { e.printStackTrace(); }

            strbr.append("</body></html>");

            String str = strbr.toString();
            if (!str.equals(Chatter.prev_update)) 
                Chatter.text.setText(str);
            
            Chatter.prev_update = new String(str);
            try { Thread.sleep(500); } catch (Exception e) {} // updates every tenth of a second
        }
    }
}