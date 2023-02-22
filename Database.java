import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

    static final String URL = "jdbc:mysql://127.0.0.1/chatter";
    static final String USER = "root";
    static final String PASS = "msqlroot";

    static Connection conn;
    static Statement stmt;

    Database() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    static ResultSet query(String sql) {
        try {
            return stmt.executeQuery(sql);
        } catch (Exception e) { 
            System.err.println(e.getMessage());
            return null;
        }
    }
}