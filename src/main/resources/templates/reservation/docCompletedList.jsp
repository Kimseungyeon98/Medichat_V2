<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/shg.css" type="text/css">

<div class="container">
    <p>
	마이페이지>나의 진료 내역
    <c:forEach var="reservation" items="${list}">
    <c:if test="${reservation.res_status == 2}">
        <div class="reservation-container">
            <div class="reservation-header">
                <img src="/images/hos_icon.png">
                <h4>${reservation.patient_name} 님</h4>
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
                    <span>진료 완료</span>
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
    </c:if>
    </c:forEach>
    <div class="pagination">
        ${page}
    </div>
</div>

