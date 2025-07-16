import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/java_quizdb"; // your database name
    private static final String USER = "root"; // your MySQL username
    private static final String PASSWORD = "AkshataBhoi@25"; // replace with your MySQL password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed:");
            e.printStackTrace();
            return null;
        }
    }
}
