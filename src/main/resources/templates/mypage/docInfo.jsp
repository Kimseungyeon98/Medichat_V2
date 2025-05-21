<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보</title>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<style>
ul{
	margin-top:10%;
}
ul li{
	margin-top:15px;
}
</style>
<body>
	<div class="docInfo">
		<ul>
			<li>이름 : ${doctor.mem_name}</li>
			<li>이메일 : ${doctor.doc_email}</li>
			<li>병원정보 : ${hospital.hos_name}</li>
			<c:if test="${doctor.doc_treat==0}">
				<li>비대면 진료 여부 : 불가능</li>
			</c:if>
			<c:if test="${doctor.doc_treat==1}">
				<li>비대면 진료 여부 : 가능</li>
			</c:if>
			<li>
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">연혁<span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li>
						${doctor.doc_history}
					</li>
				</ul>
			</li>
			<li>가입일 : ${doctor.doc_reg}</li>
		</ul>
		<div class="align-center" style="margin-top:77px;">
			<input type="button" value="회원정보 수정"
				onclick="location.href='${pageContext.request.contextPath}/doctor/modifyDoctor'"> <input
				type="button" value="비밀번호 변경" onclick="location.href='#'">
		</div>
	</div>
</body>
</html>