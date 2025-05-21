<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 회원정보 시작 -->
<style>
ul {
	list-style-type: none;
	padding: 0;
}
ul li {
	margin-bottom: 10px;
	font-size: 16px;
}
ul li img {
	max-width: 100%;
	height: auto;
	display: block;
	margin-top: 10px;
}
</style>
<div class="container">
	<h2 class="title">의사정보상세 조회(관리자)</h2>
	<hr size="1" width="80%" noshade="noshade">
	<div>
	<ul>
		<li>이름 : ${doctor.mem_name}</li>
		<li>이메일 : ${doctor.doc_email}</li>
		<li>병원 이름 : ${hospital.hos_name}</li>
		<li>병원 주소 : ${hospital.hos_addr}</li>
		<c:if test="${empty hospital.hos_tell1}">
			<li>병원 전화번호 : 정보없음</li>
		</c:if>
		<c:if test="${!empty hospital.hos_tell1}">
			<li>병원 전화번호 : ${hospital.hos_tell1}</li>
		</c:if>
		<c:if test="${empty doctor.doc_history}">
			<li>연혁 : 정보없음</li>
		</c:if>
		<c:if test="${!empty doctor.doc_history}">
			<li>연혁 : ${doctor.doc_history}</li>	
		</c:if>
		<li>의사 면허증 : <img src="${pageContext.request.contextPath}/upload/${doctor.doc_license}"></li>
		<li>가입 신청일 : ${doctor.doc_reg}</li>
	</ul>
	</div>
	<div style="text-align:center;">
		<input type="button" value="목록" onclick="location.href='agree'">
	</div>
</div>
<!-- 회원정보 끝 -->