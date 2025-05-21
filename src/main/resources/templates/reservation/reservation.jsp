<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/shg.css" type="text/css">
<style>
#calendar {
    margin: 50px auto 0;
    width: 80%;
    max-width: 800px;
}
.fc .fc-button-primary{
    background-color: #fff;
    border-color: #fff;
    color: #000;
    width: 80px;
}
.fc .fc-button-primary:hover{
    background-color: #d8f3dc;
    border-color: #fff;
    color: #fff;
}
.fc .fc-button-primary:focus, .fc .fc-button-primary:active {
    outline: none;
    box-shadow: none;
}
.fc .fc-button-primary:active{
    background-color: transparent !important;
    outline: none !important;
    box-shadow: none !important;
    border-color: transparent !important;
}
.fc .fc-button-primary:disabled{
    background-color: #fff;
    border-color: #fff;
    color: #000;
}
.fc-day-sun a {
    color: red;
    text-decoration: none;
}
.fc-day-sat a {
    color: blue;
    text-decoration: none;
}
.fc-day-mon a, .fc-day-tue a, .fc-day-wed a, .fc-day-thu a, .fc-day-fri a {
    color: black;
    text-decoration: none;
}
.fc .fc-daygrid-day.fc-day-today {
    background-color: #f6fff7;
}
.fc .fc-highlight{
    background-color: #d8f3dc;
}
</style>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script src="${pageContext.request.contextPath}/js/reservation.reservation.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    const hos_num = '${hos_num}';
    initializeCalendar(hos_num);
    initializeNextButton(); // 페이지 로드 시 다음 버튼 초기화
});
</script>
<c:set var="user" value="${sessionScope.user}" />
<div class="reservation-container">
    <div id="calendar"></div>
    <div id="time-buttons"></div>
    <div id="doctor-info"></div>
    <div id="next-button-container" class="next-button-container"></div>
</div>
<input type="hidden" id="user_mem_num" value="${user.mem_num}" />
<div class="confirm-container" style="display:none;">
	<div class="reservation-header">
		<!-- <h4 id="confirm-resType"></h4> -->
        <b>예약 정보를 다시 한 번 확인해 주세요</b>
	</div>
	<div class="reservation-details">
		<div class="detail-item">
            <label>병원 | </label>
            <span id="confirm-hospital"></span>
        </div>
        <div class="detail-item">
            <label>의사 | </label>
            <span id="confirm-doctor"></span>
        </div>
        <div class="detail-item">
            <label>날짜 | </label>
            <span id="confirm-date"></span>
        </div>
        <div class="detail-item">
            <label>시간 | </label>
            <span id="confirm-time"></span>
        </div>
        <div class="detail-item">
        	<label>비대면/방문 | </label>
        	<span id="confirm-resType"></span>
        </div>
	</div>
	<div class="reservation-userInfo">
	<h5><b>예약자 정보</b></h5>
	<p>
		<div class="detail-item">
            <label>이름 </label>
            <span id="confirm-name">${user.mem_name}</span>
        </div>
        <c:if test="${user_type != 'doctor'}">
        <div class="detail-item">
            <label>전화번호 </label>
            <span id="confirm-phone">${user.mem_phone}</span>
        </div>
        </c:if>
        <div class="detail-item">
            <label>상세 증상 </label>
            <textarea id="confirm-symptoms"></textarea>
        </div>
		<div class="outer-line">
        <div class="privacy-info">
            <span class="privacy-info-title">개인정보 수집, 제공</span>
            <svg viewBox="0 0 512 512" height="1em" xmlns="http://www.w3.org/2000/svg" class="chevron-down toggle">
            <path d="M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z"></path></svg>
        </div>
        <div id="privacy_notice" class="hide collapsible-content">
            <div class="privacy-info privacy-collection">
                <span class="privacy-info-title">개인정보 수집 동의</span>
                <svg viewBox="0 0 512 512" height="1em" xmlns="http://www.w3.org/2000/svg" class="chevron-down toggle">
                <path d="M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z"></path></svg>
            </div>
            <div id="privacy_collect" class="hide collapsible-content">
				1. 기본수집항목: [필수] 이름, (휴대)전화번호, 상세증상<br>
				※ 추가 수집하는 필수항목<br>
				- 예약자와 실제 방문자가 다른 경우 : 방문자의 이름 및 휴대전화번호<br>
				- 병원을 이용하는 경우: 생년월일 (병원 재진 시 이전 진료기록 조회를 위해 예약자명, 생년월일, 전화번호가 수집될 수 있습니다.)
				<p>
				2. 수집 및 이용목적 : 사업자회원과 예약이용자의 원활한 거래 진행, 고객상담, 불만처리 등 민원 처리, 분쟁조정 해결을 위한 기록보존
				<p>
				3. 보관기간<br>
				- 회원탈퇴 시 지체없이 파기<br>
				- 단, 관련 법령에 의하여 일정 기간 보관이 필요한 경우에는 해당 기간 동안 보관함
				<p>
				4. 동의 거부권 등에 대한 고지: 정보주체는 개인정보의 수집 및 이용 동의를 거부할 권리가 있으나, 이 경우 상품 및 서비스 예약이 제한될 수 있습니다. 그 밖의 내용은 메디챗 개인정보 처리방침을 따릅니다.<br>
            </div>
            <div class="privacy-info privacy-provision">
                <span class="privacy-info-title">개인정보 제공 동의</span>
                <svg viewBox="0 0 512 512" height="1em" xmlns="http://www.w3.org/2000/svg" class="chevron-down toggle">
                <path d="M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z"></path></svg>
            </div>
            <div id="privacy_provide" class="hide collapsible-content">
                1. 개인정보를 제공받는 자 : 메디챗
				<p>
				2. 제공하는 기본 개인정보 항목: [필수] 예약자의 마스킹 처리된 아이디, 이름, (휴대)전화번호<br>
				※ 추가 제공하는 필수항목<br>
				- 예약자와 실제 방문자가 다른 경우 : 방문자 이름 및 휴대전화번호<br>
				- 병원을 이용하는 경우: 생년월일 (병원 재진 시 이전 진료기록 조회를 위해 예약자명, 생년월일, 전화번호가 수집될 수 있습니다.)
				<p>
				3. 개인정보를 제공받는 자의 이용목적 : 사업자회원과 예약이용자의 원활한 거래 진행, 서비스 분석과 통계에 따른 혜택 및 맞춤 서비스 제공, 민원처리 등 고객상담, 고객관리, 서비스 이용에 따른 설문조사 및 혜택 제공, 분쟁조정을 위한 기록보존
				<p>
				4. 개인정보를 제공받는 자의 개인정보 보유 및 이용기간 : 메디챗 회원탈퇴 시 또는 위 개인정보 이용목적 달성 시 까지 이용합니다. (단, 관련 법령에 따른 보관의무가 있는 경우에 그 기간동안 보관)
				<p>
				5. 동의 거부권 등에 대한 고지 : 정보주체는 개인정보 제공 동의를 거부할 권리가 있으나, 이 경우 상품 및 서비스 예약이 제한될 수 있습니다.<br>
            </div>
        </div>
		</div>
    </div>
    <div class="reservation-footer">
        <input type="button" class="btn prev-btn" value="이전" onclick="previousStep()">
        <input type="button" class="btn agree-btn" style="width:140px;" value="동의하고 예약하기" onclick="submitReservation()">
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        $('.privacy-info').click(function(){
            var nextElement = $(this).next('.collapsible-content');
            if (nextElement.hasClass('hide')) {
                nextElement.removeClass('hide');
            } else {
                nextElement.addClass('hide');
            }
            $(this).find('svg').toggleClass('chevron-down chevron-up');
        });

        $('.privacy-info-sub').click(function(){
            var nextElement = $(this).next('.collapsible-content');
            if (nextElement.hasClass('hide')) {
                nextElement.removeClass('hide');
            } else {
                nextElement.addClass('hide');
            }
            $(this).find('svg').toggleClass('chevron-down chevron-up');
        });
    });
</script>