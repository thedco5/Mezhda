package util;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import src.Chatter;

public class Database {

    public static String URL = "jdbc:mysql://127.0.0.1/chatter";
    public static final String USER = "root";
    public static final String PASS = "msqlroot";

    public static Connection conn;
    public static Statement stmt;

    public Database() {
        BufferedReader reader = new BufferedReader(new FileReader("dbaddr"));
        URL = "jdbc:mysql://" + reader.readLine() + "/chatter";
        for (;;) try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.createStatement();
            break;
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static synchronized ResultSet query(String sql) {
        // if (sql.matches(".*.;.*.;.*.")) return null;
        for (;;) try {
            Statement stmt = conn.createStatement();
            if (stmt.execute(sql)) return stmt.getResultSet();
            break;
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /* UTILS */
    
    public static ResultSet selectFromUsers(String username) {
        return query("SELECT * FROM users WHERE username LIKE '" + username + "';");
    }
    public static ResultSet selectFromGroups(String groupname) {
        return query("SELECT * FROM chatter.groups WHERE groupname LIKE '" + groupname + "';");
    }
    public static ResultSet selectFromUsers(int id) {
        return query("SELECT * FROM users WHERE id LIKE " + id + ";");
    }
    public static int getUserID(String username) {
        try ( ResultSet rs = selectFromUsers(username) ) {
            rs.next();
            return rs.getInt("id");
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    public static int getGroupID(String groupname) {
        try ( ResultSet rs = selectFromGroups(groupname) ) {
            rs.next();
            return rs.getInt("id");
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    public static int currentMemberID() {
        try ( ResultSet rs = query("SELECT * from members WHERE user_id LIKE " + Chatter.user_id + " AND group_id LIKE " + Chatter.group_id + ";")) {
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    public static String getUsername(int id) {
        try ( ResultSet rs = query("SELECT username FROM users WHERE id LIKE " + id + ";") ) {
            if (rs.next()) return rs.getString("username");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    public static String getGroupname(int id) {
        try ( ResultSet rs = query("SELECT groupname FROM chatter.groups WHERE id LIKE " + id + ";") ) {
            if (rs.next()) return rs.getString("groupname");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}