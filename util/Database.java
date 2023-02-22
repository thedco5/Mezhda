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
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static ResultSet query(String sql) {
        try {
            return stmt.executeQuery(sql);
        } catch (Exception e) { 
            System.err.println(e.getMessage());
            return null;
        }
    }
    public static ResultSet selectFromUsers(String username) {
        return query("SELECT * FROM users WHERE username LIKE '" + username + "';");
    }
    public static int getUserID(String username) {
        ResultSet rs = selectFromUsers(username);
        try {
            rs.next();
            return rs.getInt("id");
        } catch (Exception e) { }
        return 0;
    }
}