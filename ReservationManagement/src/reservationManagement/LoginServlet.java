// LoginServlet.java
package reservationManagement;

import dbManagement.JDBCMySQLConnection;
import dbManagement.dbManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        JDBCMySQLConnection jdbcConnection = JDBCMySQLConnection.getInstance();
        dbManager manger = dbManager.getInstance();
        

        try (Connection connection = jdbcConnection.getConnection()) {
            // Check login credentials
            if (isValidUser(connection, username, password)) {
                // Redirect to reservation form
                response.sendRedirect("reservation.jsp");
            } else {
                // Redirect to login page with an error message
                response.sendRedirect("login.jsp?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidUser(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT * FROM user WHERE username=? AND password=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // If a record is found, credentials are valid
        }
    }
}
