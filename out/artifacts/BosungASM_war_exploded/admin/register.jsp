<%--
  Created by IntelliJ IDEA.
  User: minhnt-mac
  Date: 2019-08-03
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/account/create" method="post">
    <div>
        Email <input type="email" name="email">
    </div>
    <div>
        Link avatar <input type="text" name="avatar">
    </div>
    <div>
        <input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </div>
</form>

</body>
</html>
