package reservationManagement;

import dbManagement.JDBCMySQLConnection;

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

@WebServlet("/CancellationServlet")
public class CancellationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pnrNumber = request.getParameter("pnrNumber");

        JDBCMySQLConnection jdbcConnection = JDBCMySQLConnection.getInstance();

        try (Connection connection = jdbcConnection.getConnection()) {
            ReservationDetails reservationDetails = getReservationDetails(connection, pnrNumber);

            if (reservationDetails != null) {
                request.setAttribute("reservationDetails", reservationDetails);
                request.getRequestDispatcher("cancellationDetails.jsp").forward(request, response);
            } else {
                response.sendRedirect("cancellation.jsp?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ReservationDetails getReservationDetails(Connection connection, String pnrNumber) throws SQLException {
        String query = "SELECT * FROM reservations WHERE pnr_number=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pnrNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ReservationDetails reservationDetails = new ReservationDetails();
                reservationDetails.setTrainNumber(resultSet.getString("train_number"));
                reservationDetails.setTrainName(resultSet.getString("train_name"));
                reservationDetails.setClassType(resultSet.getString("class_type"));
                reservationDetails.setDateOfJourney(resultSet.getString("date_of_journey"));
                reservationDetails.setFromPlace(resultSet.getString("from_place"));
                reservationDetails.setToDestination(resultSet.getString("to_destination"));
                // Add more fields as needed

                return reservationDetails;
            } else {
                return null;
            }
        }
    }
}
