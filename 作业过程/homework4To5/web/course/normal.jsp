<%--
  Created by IntelliJ IDEA.
  User: kylin
  Date: 26/12/2016
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>

<%@ taglib prefix="kylin" uri="/WEB-INF/tlds/kylin.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title>standard information</title>
</head>

<%--<kylin:checkLogin/>--%>

<kylin:checkLogin>
    <c:redirect url="/Login"/>
</kylin:checkLogin>

<body>
<h1>welcome student: kylin2</h1>
<p>standard information page</p>

<table border='1'>
    <tr>
        <th>课程ID</th>
        <th>课程名称</th>
        <th>考试分数</th>
    </tr>
    <kylin:studentInfo/>
</table>

<form method='GET' action='${pageContext.request.contextPath}/Login'>
    </p>
    <input type='submit' name='Logout' value='Logout'>
</form>

<kylin:countNumber/>

</body>
</html>

