
import java.sql.*;
import java.sql.DriverManager;

public class DBConnection {

    public static String url = "jdbc:mysql://localhost:3306/jdbcpractice";
    public static String user = "root";
    public static String password = "AkshataBhoi@25";

    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Successful!!!");
        } catch (ClassNotFoundException ce) {
            System.out.println("Error Occured! :- JDBC Driver not found " + ce.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection Failed! " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error Occured!");
        }
        return c;
    }

}
