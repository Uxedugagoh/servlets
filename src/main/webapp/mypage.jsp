<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Java Servlet App</title>
    </head>
    <body>
        <div>${date}</div>
        <h1>${currentPath}<h1>
        <hr>
        <div class="navigation">
            <c:if test="${not empty parentPath}">
                <a href="?path=${encodedParentPath}">↑ Вверх</a>
            </c:if>
        </div>
        <table class="file-list">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Type</th>
                    <th class="size">Size</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${fileList}">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${item.directory}">
                                    <a href="?path=${item.path}" class="directory">📁 ${item.name}</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="download?file=${item.path}" class="file">📄 ${item.name}</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${item.directory}">Directory</c:when>
                                <c:otherwise>File</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="size">${item.displaySize}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
             <c:if test="${empty fileList}">
                    <p>This directory is empty.</p>
            </c:if>
    </body>
</html>