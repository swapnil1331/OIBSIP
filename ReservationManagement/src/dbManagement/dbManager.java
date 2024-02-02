package dbManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class dbManager {

    private static final String DATABASE_NAME = "reservation_system";
    private static final String USER_TABLE_NAME = "user";
    private static final String RESERVATIONS_TABLE_NAME = "reservations";

    private static dbManager instance;

    private JDBCMySQLConnection jdbcConnection = JDBCMySQLConnection.getInstance();

    private dbManager() {
    	super();
        this.createDatabaseAndTables();
    }

    public static synchronized dbManager getInstance() {
        if (instance == null) {
            instance = new dbManager();
        }
        return instance;
    }

    private void createDatabaseAndTables() {
        createDatabase();
        createReservationsTable();
        createUserTable();
        insertDefaultUser();
    }

    private void createDatabase() {
        Connection connection = null;
        Statement statement = null;

        try {
            // Connect to MySQL server
            connection = jdbcConnection.getConnection();
            System.out.println("Connected to MySQL server");

            // Create the database if not exists
            statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
            System.out.println("Database created or exists");

            // Switch to the reservation_system database
            statement.executeUpdate("USE " + DATABASE_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcConnection.closeConnection(connection);
            closeStatement(statement);
        }
    }

    private void createReservationsTable() {
        Connection connection = null;
        Statement statement = null;

        try {
            // Connect to MySQL server
            connection = jdbcConnection.getConnection();
            System.out.println("Connected to MySQL server");

            // Switch to the reservation_system database
            statement = connection.createStatement();
            statement.executeUpdate("USE " + DATABASE_NAME);

            // Create the reservations table if not exists
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + RESERVATIONS_TABLE_NAME + " ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "user_id INT,"
                    + "train_number INT,"
                    + "train_name VARCHAR(255),"
                    + "class_type VARCHAR(50),"
                    + "date_of_journey DATE,"
                    + "from_place VARCHAR(100),"
                    + "to_destination VARCHAR(100)"
                    + ")");
            System.out.println("Table 'reservations' created or exists");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcConnection.closeConnection(connection);
            closeStatement(statement);
        }
    }

    private void createUserTable() {
        Connection connection = null;
        Statement statement = null;

        try {
            // Connect to MySQL server
            connection = jdbcConnection.getConnection();
            System.out.println("Connected to MySQL server");

            // Switch to the reservation_system database
            statement = connection.createStatement();
            statement.executeUpdate("USE " + DATABASE_NAME);

            // Create the user table if not exists
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + " ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "username VARCHAR(50),"
                    + "password VARCHAR(50)"
                    + ")");
            System.out.println("Table 'user' created or exists");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcConnection.closeConnection(connection);
            closeStatement(statement);
        }
    }

    private void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insertDefaultUser() {
        Connection connection = null;
        Statement statement = null;

        try {
            // Connect to MySQL server
            connection = jdbcConnection.getConnection();
            System.out.println("Connected to MySQL server");

            // Switch to the reservation_system database
            statement = connection.createStatement();
            statement.executeUpdate("USE " + DATABASE_NAME);

            // Insert default user if not exists
            String defaultUsername = "admin";
            String defaultPassword = "admin@123";
            statement.executeUpdate("INSERT INTO " + USER_TABLE_NAME + " (username, password) VALUES ('"
                    + defaultUsername + "', '" + defaultPassword + "')");
            System.out.println("Default user inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcConnection.closeConnection(connection);
            closeStatement(statement);
        }
    }

    public static void main(String[] args) {
        // Get the singleton instance, and both tables will be created during instantiation
        dbManager manager = dbManager.getInstance();
    }
}
