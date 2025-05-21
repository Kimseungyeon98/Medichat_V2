<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.find.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.all.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.min.css" rel="stylesheet">
<style>
input[type="password"],
input[type="text"]{
	width:320px;
	border-bottom:1px solid #000;
}
input[type="password"]:focus,
input[type="text"]:focus{
	outline:none;
	border-bottom:2px solid #000;
}
</style>  
<div class="container">
    <h2 class="title">비밀번호 찾기</h2>
    <hr size="1" width="100%" noshade="noshade">
    <span class="fs-15">
		가입할 때 사용한 아이디와 이메일를 입력하시면 이메일로 임시비밀번호를 보내드립니다.<br>		
	</span>
    <form:form action="memberFindPassword" id="member_findPasswd" modelAttribute="memberVO">
        <ul>
            <li>
                <form:input path="mem_id" placeholder="아이디" style="margin-top:50px;"/>
            </li>
            <li>
                <form:input path="mem_email" placeholder="test@test.com 형식으로 입력"/>
            </li>
        </ul>
        <div>
            <form:button class="submit fw-7 fs-17">이메일 전송</form:button>
        </div>
        <hr size="1" width="100%" noshade="noshade">
        <div class="button-container">
            <input type="button" value="홈으로" style="margin-right:20px;" onclick="location.href='${pageContext.request.contextPath}/main/main'">
            <input type="button" value="로그인" onclick="location.href='login'">
        </div>
    </form:form>
</div>