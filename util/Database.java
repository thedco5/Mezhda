package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

    public static final String URL = "jdbc:mysql://127.0.0.1/chatter";
    public static final String USER = "root";
    public static final String PASS = "msqlroot";

    public static Connection conn;
    public static Statement stmt;

    public Database() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static synchronized ResultSet query(String sql) {
        // if (sql.matches(".*.;.*.;.*.")) return null;
        try {
            if (stmt.execute(sql))
                return stmt.getResultSet();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /* UTILS */
    
    public static ResultSet selectFromUsers(String username) {
        return query("SELECT * FROM users WHERE username LIKE '" + username + "';");
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