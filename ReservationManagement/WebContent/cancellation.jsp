<!-- cancellation.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Cancellation Form</title>
</head>
<body>
    <form action="CancellationServlet" method="post">
        PNR Number: <input type="text" name="pnrNumber" required><br>
        <input type="submit" value="Submit">
    </form>

    <c:if test="${param.error eq '1'}">
        <p style="color: red;">Invalid PNR number. Please try again.</p>
    </c:if>
</body>
</html>
