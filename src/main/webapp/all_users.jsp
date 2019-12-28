<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="DAO.DAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Все пользователи</title>
</head>
<body>
    <%

        List<User> users = DAO.getAllObjects(User.class);
        PrintWriter writer = response.getWriter();
        for (User user : users){
            writer.println(user);
        }
    %>
</body>
</html>
