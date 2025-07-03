
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBOperation {

    private static Connection conn;

    // Initialize connection (call once at start)
    public static void init() {
        conn = DBConnection.getConnection();
    }

    public static void main(String[] args) {
        init(); // call for db connection
        Scanner sc = new Scanner(System.in);
        System.out.println("Employee Database App");
        while (true) {
            System.out.print("1. Add \t 2. View \t 3. Update \t 4. Delete \t 5. Exit\n");
            System.out.print("Enter your Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Adding to Database!");
                    System.out.print("Enter ID: ");
                    int eid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String ename = sc.nextLine();
                    System.out.print("Enter Position: ");
                    String epos = sc.nextLine();
                    System.out.print("Enter Salaray: ");
                    Double esal = sc.nextDouble();
                    addValues(eid, ename, epos, esal);
                    break;

                case 2:
                    System.out.println("Reading from Database!");
                    viewDatabase();
                    break;

                case 3:
                    System.out.println("Updating to Database!");
                    System.out.print("Enter ID: ");
                    int Eid = sc.nextInt();
                    sc.nextLine();
                    if (exists(Eid)) {
                        System.out.print("Enter New Name: ");
                        String newEname = sc.nextLine();
                        System.out.print("Enter New Position: ");
                        String newEpos = sc.nextLine();
                        System.out.print("Enter New Salaray: ");
                        Double newEsal = sc.nextDouble();
                        updateValues(Eid, newEname, newEpos, newEsal);
                    }
                    break;

                case 4:
                    System.out.println("Deleted from Database!");
                    System.out.print("Enter ID: ");
                    int empid = sc.nextInt();
                    sc.nextLine();
                    deleteValues(empid);
                    break;

                case 5:
                    System.out.println("Exiting......");
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                            System.out.println("Database connection closed.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error closing connection: " + e.getMessage());
                    }
                    System.exit(0);
                default:
                    System.out.println("Invalid!!!!!!!");
            }
        }
    }

    // Add Values -- insert query
    public static void addValues(int e_id, String ename, String eposition, double esalary) {
        String insertQuery = "insert into employee(ID,Name,Position,Salary) Values (?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setInt(1, e_id);
            pstmt.setString(2, ename);
            pstmt.setString(3, eposition);
            pstmt.setDouble(4, esalary);
            pstmt.executeUpdate();
            System.out.println("Values Inserted!!!\n");
        } catch (SQLException e) {
            System.out.println("Error! Adding Employee Details " + e.getMessage());
        }
    }

    //Read Values -- select query
    public static void viewDatabase() {
        String selectQuery = ("select * from employee");
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectQuery)) {
            System.out.println("Employee Details");
            System.out.println("ID \t\t Name \t\t\t\t Position \t\t\t Salary \t\t");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t\t" + rs.getString(3) + "\t\t" + rs.getDouble(4));
            }
        } catch (SQLException e) {
            System.out.println("Error! Displaying Records " + e.getMessage());
        }
    }

    // id check
    public static boolean exists(int Eid) {
        String idCheck = "SELECT COUNT(*) FROM employee WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(idCheck)) {
            pstmt.setInt(1, Eid);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Return true if count is greater than 0
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking ID existence: " + e.getMessage());
        }
        return false; // Return false if an error occurs or no rows found
    }

    //Update values -- update query
    public static void updateValues(int Eid, String newEname, String newEposition, double newEsalary) {
        String updateQuery = "Update employee set name=?, position=?, salary=? where id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newEname);
            pstmt.setString(2, newEposition);
            pstmt.setDouble(3, newEsalary);
            pstmt.setInt(4, Eid);
            int updateRows = pstmt.executeUpdate();
            if (updateRows > 0) {
                System.out.println("Row Updated!!!\n");
            } else {
                System.out.println("ID Not Found!!!");
            }
        } catch (SQLException e) {
            System.out.println("Error! Updating Employee Details " + e.getMessage());
        }
    }

    //Delete Values -- delete query
    public static void deleteValues(int eid) {
        String deleteQuery = "Delete from employee where ID=?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, eid);
            int deletedRows = pstmt.executeUpdate();
            if (deletedRows > 0) {
                System.out.println("Row Deleted!!!\n");
            } else {
                System.out.println("ID Not Found!!!");
            }
        } catch (SQLException e) {
            System.out.println("Error! Deleting Employee Details " + e.getMessage());
        }
    }
}
