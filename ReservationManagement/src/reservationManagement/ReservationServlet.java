package reservationManagement;

import dbManagement.JDBCMySQLConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String trainNumber = request.getParameter("trainNumber");
        String trainName = request.getParameter("trainName");
        String classType = request.getParameter("classType");
        String dateOfJourney = request.getParameter("dateOfJourney");
        String fromPlace = request.getParameter("fromPlace");
        String toDestination = request.getParameter("toDestination");

        JDBCMySQLConnection jdbcConnection = JDBCMySQLConnection.getInstance();

        try (Connection connection = jdbcConnection.getConnection()) {
            insertReservation(connection, username, trainNumber, trainName, classType, dateOfJourney, fromPlace, toDestination);
            response.sendRedirect("reservationSuccess.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertReservation(Connection connection, String username, String trainNumber, String trainName,
            String classType, String dateOfJourney, String fromPlace, String toDestination) throws SQLException {
        String query = "INSERT INTO reservations (user_id, train_number, train_name, class_type, date_of_journey, from_place, to_destination) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, trainNumber);
            preparedStatement.setString(3, trainName);
            preparedStatement.setString(4, classType);
            preparedStatement.setString(5, dateOfJourney);
            preparedStatement.setString(6, fromPlace);
            preparedStatement.setString(7, toDestination);

            preparedStatement.executeUpdate();
        }
    }
}
