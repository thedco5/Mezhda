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
        } catch (Exception e) { System.err.println(e.getLocalizedMessage()); }
    }

    public static ResultSet query(String sql) {
        try {
            if (stmt.execute(sql))
                return stmt.getResultSet();
        } catch (Exception e) { System.err.println(e.getLocalizedMessage()); }
        return null;
    }
    public static ResultSet selectFromUsers(String username) {
        return query("SELECT * FROM users WHERE username LIKE '" + username + "';");
    }
    public static ResultSet selectFromUsers(int id) {
        return query("SELECT * FROM users WHERE id LIKE " + id + ";");
    }
    public static int getUserID(String username) {
        ResultSet rs = selectFromUsers(username);
        try {
            rs.next();
            return rs.getInt("id");
        } catch (Exception e) { System.err.println(e.getLocalizedMessage()); }
        return 0;
    }
    public static String getUsername(int id) {
        ResultSet rs = query("SELECT * FROM users WHERE id LIKE " + id + ";");
        try {
            rs.next();
            return rs.getString("username");
        } catch (Exception e) { System.err.println(e.getLocalizedMessage()); }
        return null;
    }
}