<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.all.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/kcy.css">
<!-- 비밀번호 변경 시작 -->
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
	<h2 class="title">비밀번호 변경(의사)</h2>
	<hr size="1" width="100%" noshade="noshade">
	<form:form action="changePasswd" id="doctor_changePasswd" modelAttribute="doctorVO">
		<form:errors element="div" cssClass="error-color"/>
		<div class="form-main">
		<ul>
			<li>
				<form:label path="now_passwd">현재 비밀번호</form:label>
				<form:password path="now_passwd"/>
				<form:errors path="now_passwd" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="doc_passwd">변경할 비밀번호</form:label>
				<form:password path="doc_passwd"/>
				<form:errors path="doc_passwd" cssClass="error-color"/>
			</li>
			<li>
				<label for="confirm_passwd">변경할 비밀번호 확인</label>
				<input type="password" id="confirm_passwd">
				<span id="message_password"></span>
			</li>
		</ul>
		</div>
		<!-- 캡챠 시작 -->
		<hr size="1" width="100%" noshade="noshade">
		<div class="captcha">
		<ul>
			<li>
				<span style="font-weight:bold;">인증문자 입력</span>
				<div id="captcha_div">
					<img src="getCaptcha" id="captcha_img" width="200" height="90">
					<button type="button" class="btn" id="reload_captcha">
					    <i class="fas fa-sync-alt"></i>
					</button>
				</div>
			</li>
			<li>
				<form:label path="captcha_chars" >인증문자 확인</form:label>
				<form:input path="captcha_chars" placeholder="인증문자를 입력하세요."/>
				<form:errors path="captcha_chars" cssClass="error-color"/>
			</li>
		</ul>
		</div>
		<div style="text-align:center">
			<form:button class="default-btn">비밀번호 수정</form:button>
		</div>
		<hr size="1" width="100%" noshade="noshade">
		<div style="text-align:right; margin-bottom:10px;">
			<input type="button" value="MY페이지" id="reload_btn" onclick="location.href='docPage'">
			<input type="button" value="홈으로" id="reload_btn" onclick="location.href='${pageContext.request.contextPath}/main/main'">
		</div>
	</form:form>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/doctor.passwd.js"></script>
	<script>
		$(function(){
			$('#reload_captcha').click(function(){
			 $.get('getCaptcha', function(data) {
		            $('#captcha_img').attr('src', 'getCaptcha?' + new Date().getTime());
		        }).fail(function() {
		            alert('네트워크 오류 발생');
		        });
			});
		});
	</script>
</div>
<!-- 비밀번호 변경  끝 -->