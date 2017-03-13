<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alex
  Date: 11.03.17
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <link href="../../resources/css/bootstrap.css" rel="stylesheet"/>
    <link href="../../resources/css/style.css" rel="stylesheet"/>
  </head>
  <body>
  <form:form method="POST" modelAttribute="uploadedFile" action="uploadFile" enctype="multipart/form-data" cssStyle="float: left">
    <table>
      <tr>
        <td>Upload file:</td>
        <td><input type="file" name="file"></td>
        <td style="color:red; font-style: italic;"><form:errors path="file"/></td>
      </tr>

      <tr>
        <td></td>
        <td><input type="submit" value="Upload"></td>
        <td></td>
      </tr>
    </table>

    Press here to upload the file!
  </form:form>

  <a href="/downloadFile" style="float: right">Download File</a>



    <br>

    <table class='table table-margin'>
      <tr><th>Title</th><th>Artist</th><th>Company</th><th>Country</th><th>Price</th><th>Year</th></tr>
      <c:forEach items="${cdList}" var="cd">
        <tr>
          <td>${cd.title}</td>
          <td>${cd.artist}</td>
          <td>${cd.company}</td>
          <td>${cd.country}</td>
          <td>${cd.year}</td>
          <td>${cd.price}</td>
        </tr>
      </c:forEach>

    </table>
  </body>
</html>
