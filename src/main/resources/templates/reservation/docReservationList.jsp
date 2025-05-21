<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/shg.css" type="text/css">

<div class="container">
    <p>
	마이페이지>나의 예약 내역
    <c:forEach var="reservation" items="${list}">
        <div class="reservation-container">
            <div class="reservation-header">
                <img src="/images/hos_icon.png">
                <h4>${reservation.patient_name} 님</h4>
                <div class="reservation-actions">
                    <c:choose>
                        <c:when test="${reservation.res_status == 0}">
                            <input type="button" class="approve-button" data-resnum="${reservation.res_num}" data-action="1" value="승인">
                            <input type="button" class="reject-button" data-resnum="${reservation.res_num}" data-action="3" value="거절">
                        </c:when>
                        <c:when test="${reservation.res_status == 1}">
                            <input type="button" class="complete-button" data-resnum="${reservation.res_num}" data-action="2" value="진료완료">
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="docreservation-body">
                <div class="detail-info">
                    <label>비대면/진료 | </label>
                    <span>
                        <c:choose>
                            <c:when test="${reservation.res_type == 0}">비대면 진료</c:when>
                            <c:otherwise>방문진료</c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div class="detail-info">
                    <label>예약 상태 | </label>
                    <span>
                        <c:choose>
                            <c:when test="${reservation.res_status == 0}">예약 승인 대기</c:when>
                            <c:when test="${reservation.res_status == 1}">진료 예정</c:when>
                            <c:when test="${reservation.res_status == 2}">진료 완료</c:when>
                            <c:otherwise>예약 취소</c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div class="detail-info">
                    <label>진료일 | </label>
                    <span>${reservation.res_date} ${reservation.res_time}</span>
                </div>
                <div class="detail-info">
                    <label>상세증상 | </label>
                    <span>${reservation.res_content}</span>
                </div>
            </div>
        </div>
    </c:forEach>
    <div class="pagination">
        ${page}
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
$(document).ready(function() {
    $(".approve-button, .reject-button, .complete-button").click(function() {
        var resNum = $(this).data("resnum");
        var action = $(this).data("action");
        var confirmationMessage = "";
        if (action == 1) {
            confirmationMessage = "예약을 승인하시겠습니까?";
        } else if (action == 2) {
            confirmationMessage = "진료를 완료하시겠습니까?";
        } else if (action == 3) {
            confirmationMessage = "예약을 거절하시겠습니까?";
        }
        if (confirm(confirmationMessage)) {
            $.ajax({
                type: 'post',
                url: '/reservation/updateReservation',
                data: {
                    res_num: resNum,
                    res_status: action
                },
                dataType: "json",
                success: function(param) {
                    if (param.result == "success") {
                        alert("처리가 완료되었습니다.");
                        location.reload();
                    } else if (param.result == "logout") {
                        alert("로그인이 필요합니다.");
                    } else {
                        alert("처리에 실패했습니다.");
                    }
                },
                error: function() {
                    alert("네트워크 오류가 발생했습니다.");
                }
            });
        }
    });
});
</script>
