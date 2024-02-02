<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Cancellation Details</title>
</head>
<body>
    <h2>Reservation Details</h2>
    <c:if test="${reservationDetails ne null}">
        <p>Train Number: ${reservationDetails.trainNumber}</p>
        <p>Train Name: ${reservationDetails.trainName}</p>
        <p>Class Type: ${reservationDetails.classType}</p>
        <p>Date of Journey: ${reservationDetails.dateOfJourney}</p>
        <p>From Place: ${reservationDetails.fromPlace}</p>
        <p>To Destination: ${reservationDetails.toDestination}</p>
        <!-- Add more fields as needed -->
    </c:if>
    <c:if test="${reservationDetails eq null}">
        <p style="color: red;">Invalid PNR number. Please try again.</p>
    </c:if>
</body>
</html>
