package dbManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCMySQLConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbcdatabase";
    private static final String USER = "root";
    private static final String PASSWORD = "swapnil@7316";

    // Private static instance variable
    private static JDBCMySQLConnection instance =null;

    // Private constructor to prevent instantiation outside the class
    private JDBCMySQLConnection() {
        // Optional: You can add initialization code here
    }

    // Public method to get the singleton instance
    public static synchronized JDBCMySQLConnection getInstance() {
        if (instance == null) {
            instance = new JDBCMySQLConnection();
        }
        return instance;
    }

    // Public method to get a database connection
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    // Public method to close a database connection
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection Closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }   
}
