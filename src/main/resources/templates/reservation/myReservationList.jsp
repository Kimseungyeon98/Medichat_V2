<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/shg.css" type="text/css">
<p>
마이페이지>나의 예약 내역
<c:forEach var="reservation" items="${list}">
<div class="reservation-container">
    <input type="hidden" id="hos_num" value="${reservation.hos_num}">
    <div class="reservation-header">
        <img src="/images/hos_icon.png">
        <h4><a href="/hospitals/search/detail/${reservation.hos_num}">${reservation.hos_name}</a></h4>
        <p style="margin-top:20px;">예약등록일 : ${reservation.res_reg}</p>
        <div class="reservation-actions">
            <c:choose>
                <c:when test="${reservation.res_status == 0 || reservation.res_status == 1}">
                    <input type="button" class="cancel-button" data-resnum="${reservation.res_num}" value="예약 취소">
                </c:when>
                <c:when test="${reservation.res_status == 2}">
                    <input type="button" class="review-button" onclick="location.href='/review/writeReview?res_num=${reservation.res_num}&hos_num=${reservation.hos_num}'"  value="리뷰 쓰기">
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="reservation-body">
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
                    <c:when test="${reservation.res_status == 1}">예약 확정</c:when>
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
            <label>담당 의사 | </label>
            <span>${reservation.doc_name} 의사</span>
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
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
$(document).ready(function() {
	$(".cancel-button").click(function() {
		var resNum = $(this).data("resnum");
        if (confirm("예약을 취소하시겠습니까?")) {
            $.ajax({
            	type:'post',
            	url:'/reservation/cancelReservation',
            	data:{res_num:resNum},
            	dataType: "json",
            	success: function(param){
            		if(param.result == 'logout'){
            			alert('로그인 후 이용하세요');
            		}else if(param.result == 'success'){
            			alert('예약이 취소되었습니다.');
            			location.reload();
            		}else{
            			alert('예약취소에 실패했습니다.');
            		}
            	},
            	error:function(){
            		alert('에러가 발생했습니다.');
            	}
            });
         }
    });
});
</script>
