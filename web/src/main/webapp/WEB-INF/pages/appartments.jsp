
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach var="app" items="${appartments}">
    <p> ${app.name} </p>
    </value>
</c:forEach>
</body>
</html>
