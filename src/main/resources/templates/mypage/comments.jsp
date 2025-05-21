<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>댓글 목록</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <h1>댓글 목록</h1>
    <c:choose>
        <c:when test="${!empty comments}">
            <table>
                <thead>
                    <tr>
                        <th>댓글 내용</th>
                        <th>게시글 제목</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="comment" items="${comments}">
                        <tr>
                            <td>${comment.cre_content}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/medichatCommunity/detail?cbo_num=${comment.cbo_num}">
                                    ${comment.postTitle}
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>댓글이 없습니다.</p>
        </c:otherwise>
    </c:choose>
</body>
</html>
