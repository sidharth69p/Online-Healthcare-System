package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = Config.get("db.url", "jdbc:mysql://localhost:3306/online_healthcare?serverTimezone=UTC");
        String user = Config.get("db.username", "root");
        String pass = Config.get("db.password", "");
        return DriverManager.getConnection(url, user, pass);
    }
}
