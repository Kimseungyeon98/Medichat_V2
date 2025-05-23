<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<style>
img{
  width: 100%;
}
.login-container {
     width: 800px;
     margin-left: auto;
     margin-right: auto;
     margin-bottom: 100px;
     padding-left: 15px;
     padding-right: 15px;
     padding-bottom:15px;
     position: relative;
     top:60px;
}
</style>
<div class="login-container">
	<section class="login">
		<div class="login_box">
			<div class="left">
				<div class="contact">		
				<form:form action="login" id="member_login" modelAttribute="member">
					<ul>
						<li>
							<form:input path="mem_id" placeholder="아이디" autocomplete="off"/>
							<form:errors path="mem_id" cssClass="error-color"/>
						</li>
						<li>
							<form:password path="mem_passwd" placeholder="비밀번호"/>
							<form:errors path="mem_passwd" cssClass="error-color"/>
						</li>
					</ul>
					<div class="flame">
						<form:errors element="div" cssClass="error-color"/>
						<form:button style="display: block; margin: 0 auto;" class="submit custom-btn btn-15">로그인</form:button>
					</div>
					<div class="button-container">
						<label for="auto">
						<input type="checkbox" name="auto" id="auto">자동로그인</label>
						<input type="button" value="아이디 찾기" style="margin-right:10px; margin-left:80px;" onclick="location.href='memberFindId'">
						<input type="button" value="비밀번호 찾기" onclick="location.href='sendMemPassword'">
					</div>
					<hr class="hr-text" data-content="OR">
					<div class="social">
						<!--  카카오 로그인시작 -->
						<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&amp;client_id=c37546ec572f00c776f1b466369745f3&amp;redirect_uri=http://localhost:8000/member/kakaologin">
							<img src="${pageContext.request.contextPath}/images/kakao_login_medium_narrow.png">
						</a>
						<!--  카카오 로그인 끝 -->
						<!--  네이버 로그인 시작 -->
						<a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=RNP28hanNjPbrV17UuZb	&state=STATE_STRING&redirect_uri=http://localhost:8000/member/naverlogin">
							<img src="${pageContext.request.contextPath}/images/naver_login.png" width="200">		
						</a>
						<!--  네이버 로그인 끝 -->
					</div>
				</form:form>
				</div>
			</div>
			<div class="right">
				<div class="right-text">
					<h2>MediChat</h2>
		      		<h5>비대면진료</h5>
				</div>
			</div>
		</div>
	</section>
	
</div>
