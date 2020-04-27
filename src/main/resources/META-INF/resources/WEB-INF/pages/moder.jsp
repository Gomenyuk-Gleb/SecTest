<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Prog.kiev.ua</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>
</head>
<body>
<div align="center">
    <h1>Read only!</h1>

    <p>Click to go back: <a href="/">back</a></p>

    <c:url value="/logout" var="logoutUrl" />
    <p>Click to logout: <a href="${logoutUrl}">LOGOUT</a></p>


    <table border="1">
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.role}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
