<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<style>
input[type="password"],
input[type="text"]{
	width:350px;
	border-bottom:1px solid #000;
}
input[type="password"]:focus,
input[type="text"]:focus{
	outline:none;
	border-bottom:2px solid #000;
}
</style>
<div class="container">
    <h2 class="title">아이디 찾기</h2>
    <hr size="1" width="100%" noshade="noshade">
    <form:form action="memberFindId" id="member_findId" modelAttribute="memberVO">
        <ul>
            <li>
                <form:input path="mem_name" placeholder="이름" style="margin-top:50px;" />
                <form:errors path="mem_name" cssClass="error-color"/>
            </li>
            <li>
                <form:input path="mem_email" placeholder="test@test.com 형식으로 입력"/>
                <form:errors path="mem_email" cssClass="error-color"/>
            </li>
        </ul>
        <div>
            <form:errors element="div" cssClass="error-color"/>
            <form:button class="submit fw-7 fs-17">아이디 찾기</form:button>
        </div>
        <hr size="1" width="100%" noshade="noshade">
        <div class="button-container">
            <input type="button" value="홈으로" style="margin-right:20px;" onclick="location.href='${pageContext.request.contextPath}/main/main'">
            <input type="button" value="로그인" onclick="location.href='login'">
        </div>
    </form:form>
</div>