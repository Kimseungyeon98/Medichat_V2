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
				<div style="text-align:right;">
				    <span style="display:block; font-weight:bold;">의사회원 로그인</span>
				</div>
				<div class="contact">	
				<form:form action="login" id="doctor_login" modelAttribute="doctorVO">
					<!-- 	<input type="hidden" id="lon" name="user_lon" value="" >
					<input type="hidden" id="lat" name="user_lat" value="" > -->
					<ul>
						<li>
							<form:input path="mem_id" placeholder="아이디" autocomplete="off"/>
							<form:errors path="mem_id" cssClass="error-color"/>
						</li>
						<li>
							<form:password path="doc_passwd" placeholder="비밀번호"/>
							<form:errors path="doc_passwd" cssClass="error-color"/>
						</li>
					</ul>
					<div class="flame">
						<form:errors element="div" cssClass="error-color"/>
						<form:button style="display: block; margin: 0 auto;" class="submit custom-btn btn-15">로그인</form:button>
					</div>
					<div class="button-container">
						<label for="auto">
						<input type="checkbox" name="auto" id="auto">자동로그인</label>
						<input type="button" value="아이디 찾기" style="margin-right:10px; margin-left:80px;" onclick="location.href='doctorFindId'">
						<input type="button" value="비밀번호 찾기" onclick="location.href='sendDocPassword'">
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
<!-- 위도 경도 보내기 -->
<!-- <script>
window.onload = function() {
    const log_btn = document.getElementById('login_btn');
    log_btn.onsubmit = function(event) {
        event.preventDefault(); // 폼 제출 기본 동작 방지

        // 기본값 설정
        const defaultLat = 37.4981646510326;
        const defaultLon = 127.028307900881;
        
        // 위치 정보 가져오기
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                function(position) {
                    const lat = position.coords.latitude;
                    const lon = position.coords.longitude;
                    document.getElementById('lat').value = lat;
                    document.getElementById('lon').value = lon;
                    log_btn.submit(); // 폼 제출
                },
                function(error) {
                    const lat = defaultLat;
                    const lon = defaultLon;
                    document.getElementById('lat').value = lat;
                    document.getElementById('lon').value = lon;
                    log_btn.submit(); // 폼 제출
                }
            );
        } else {
            alert('Geolocation is not supported by this browser.');
            const lat = defaultLat;
            const lon = defaultLon;
            document.getElementById('lat').value = lat;
            document.getElementById('lon').value = lon;
            log_btn.submit(); // 폼 제출
        }
    };
};
</script> -->
