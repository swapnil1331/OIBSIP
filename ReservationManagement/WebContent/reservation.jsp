<!-- reservation.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Reservation Form</title>
</head>
<body>
    <form action="ReservationServlet" method="post">
        <!-- Add reservation form fields here -->
        Train Number: <input type="text" name="trainNumber" required><br>
        Train Name: <input type="text" name="trainName" required><br>
        Class Type: <input type="text" name="classType" required><br>
        Date of Journey: <input type="text" name="dateOfJourney" required><br>
        From Place: <input type="text" name="fromPlace" required><br>
        To Destination: <input type="text" name="toDestination" required><br>
        <input type="submit" value="Reserve">
    </form>
</body>
</html>
