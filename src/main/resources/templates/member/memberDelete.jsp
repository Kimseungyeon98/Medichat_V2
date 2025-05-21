<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 회원탈퇴 시작 -->
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
	<h2 class="title">회원탈퇴</h2>
	<hr size="1" width="100%" noshade="noshade">
	<form:form action="deleteUser" id="member_delete" modelAttribute="memberVO">
		<div class="form-main">
		<form:hidden path="mem_num"/>
		<ul>
			<li>
				<form:label path="mem_id">아이디</form:label>
				<form:input path="mem_id" placeholder="아이디" autocomplete="off"/>
				<form:errors path="mem_id" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="mem_passwd">비밀번호</form:label>
				<form:password path="mem_passwd" placeholder="비밀번호"/>
				<form:errors path="mem_passwd" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="mem_email">이메일</form:label>
				<form:input path="mem_email" placeholder="test@test.com 형식으로 입력"/>
				<form:errors path="mem_email" cssClass="error-color"/>
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
			<form:button class="default-btn">회원탈퇴</form:button>
		</div>
		<hr size="1" width="100%" noshade="noshade">
		<div style="text-align:right; margin-bottom:10px;">
			<input type="button" value="MY페이지" id="reload_btn" onclick="location.href='myPage'">
			<input type="button" value="홈으로" id="reload_btn" onclick="location.href='${pageContext.request.contextPath}/main/main'">
		</div>
	</form:form>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.passwd.js"></script>
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
<!-- 회원탈퇴 끝 -->