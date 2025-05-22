<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 상단 시작 -->
<style>
.logo-container {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 10px;
}

.logo img {
	max-height: 50px;
	vertical-align: middle;
}

.align-right {
	display: flex;
	align-items: center;
}

.align-right a {
	margin-left: 10px;
	text-decoration: none;
	color: #333;
}

.page-main {
	display: flex;
	flex-direction: column;
	align-items: center;
	text-align: center;
	margin-top: 20px;
}

.page-main>.align-center {
	margin-bottom: 20px;
}

.page-main>.align-right {
	align-self: flex-end;
	margin-bottom: 20px;
}

.page-main .align-center>a, .page-main .align-right>a {
	margin: 0 10px;
}

.search-bar {
	width: 100%;
	padding: 10px;
	box-sizing: border-box;

	margin-bottom: 10px;
}

.search-bar input[type="text"] {
	width: 70%;
	padding: 8px;

	border-radius: 5px;
	font-size: 16px;
}

.search-bar button {
	padding: 8px 15px;
	border: none;
	background-color: #333;
	color: #fff;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
}
</style>

<div class="logo-container">
	<div class="logo">
		<img src="${pageContext.request.contextPath}/images/logo.jpg">
	</div>
	<div class="align-right">
		<c:if test="${!empty user}">
			<a href="${pageContext.request.contextPath}/member/myPage">MY페이지</a>
			<img src="${pageContext.request.contextPath}/member/photoView"
				width="25" height="25" class="my-photo">
			<a href="${pageContext.request.contextPath}/member/logout">로그아웃</a>
		</c:if>
		<c:if test="${empty user}">
			<a href="${pageContext.request.contextPath}/member/login">로그인</a>
			<a href="${pageContext.request.contextPath}/member/registerUser">회원가입</a>
		</c:if>
	</div>
</div>
<!-- 상단 끝 -->





