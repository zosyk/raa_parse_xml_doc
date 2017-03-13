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
    <link href="../../resources/css/custom_style.css" rel="stylesheet"/>
    <link href="../../resources/css/simplePagination.css" rel="stylesheet"/>
    <script src="../../resources/js/jquery-3.1.1.min.js"></script>
    <script src="../../resources/js/jquery.simplePagination.js"></script>
    <script src="../../resources/js/upload_file.js"></script>
</head>
<body>
<div class="custom-container">
    <form:form method="POST" modelAttribute="uploadedFile" action="uploadFile" enctype="multipart/form-data"
               cssStyle="float: left">
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

    <a href="/downloadFile" style="float: right" class="btn btn-danger">Download File</a>


    <br style="clear: both">
    <hr/>

    <table class='table table-margin' id="cdTable">
    </table>

    <div class="pagination-wrapper">
        <div class="custom-pagination"></div>
    </div>
</div>


<script>
    $(function () {
        $(".custom-pagination").pagination({
            items: ${cdList},
            itemsOnPage: 5,
            cssStyle: 'light-theme'
        });
    });

    var schoolList =
    ${cdList}
    for (var i = 0; i < schoolList.length; i++) {
        console.info(schoolList[i].title);
    }
</script>
</body>
</html>
