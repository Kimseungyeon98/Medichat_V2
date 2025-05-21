<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div>
	<div class="p-3">
		<span class="text-lightgray fw-7 fs-13">홈 > 약국 찾기 > 검색 결과 > 약국 상세</span>
		<div id="detail_hosName">
			<h4 class="fw-7">${pharmacy.pha_name}</h4>
			<div class="d-flex">
				<c:if test="${pharmacy.pha_weekendAt=='Y'}">
					<div class="detail-hospital-info-item fs-14 fw-7">주말 운영</div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="line"></div>
	<div style="height:15px;" class="bg-gray-0"></div>
	
	
	<div id="detail_hosTime">
		<div class="d-flex align-items-center mb-3">
			<div class="fs-18 fw-7">운영 시간</div>
			<c:if test="${day==1}">
				<c:if test="${pharmacy.pha_time1S<=time and time<pharmacy.pha_time1C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">Open</div>
				</c:if>
				<c:if test="${pharmacy.pha_time1S>time or pharmacy.pha_time1C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">Close</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==2}">
				<c:if test="${pharmacy.pha_time2S<=time and time<pharmacy.pha_time2C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">Open</div>
				</c:if>
				<c:if test="${pharmacy.pha_time2S>time or pharmacy.pha_time2C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">Close</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==3}">
				<c:if test="${pharmacy.pha_time3S<=time and time<pharmacy.pha_time3C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">Open</div>
				</c:if>
				<c:if test="${pharmacy.pha_time3S>time or pharmacy.pha_time3C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">Close</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==4}">
				<c:if test="${pharmacy.pha_time4S<=time and time<pharmacy.pha_time4C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">Open</div>
				</c:if>
				<c:if test="${pharmacy.pha_time4S>time or pharmacy.pha_time4C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">Close</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==5}">
				<c:if test="${pharmacy.pha_time5S<=time and time<pharmacy.pha_time5C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">Open</div>
				</c:if>
				<c:if test="${pharmacy.pha_time5S>time or pharmacy.pha_time5C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">Close</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==6}">
				<c:if test="${pharmacy.pha_time6S<=time and time<pharmacy.pha_time6C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">Open</div>
				</c:if>
				<c:if test="${pharmacy.pha_time6S>time or pharmacy.pha_time6C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">Close</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==7}">
				<c:if test="${pharmacy.pha_time7S<=time and time<pharmacy.pha_time7C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">Open</div>
				</c:if>
				<c:if test="${pharmacy.pha_time7S>time or pharmacy.pha_time7C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">Close</div>
				</c:if>
		    </c:if>
		</div>
		<div class="bg-gray-0 p-3 d-flex rounded-3 row">
			<div id="detail_timeAndEtc" class="col-6">
				<div class="fs-15 fw-7 text-black-6">오늘</div>
				<div class="fs-15 fw-3">
					<c:if test="${day==1 and (pharmacy.pha_time1S!='null' or pharmacy.pha_time1C!='null')}">
						${fn:substring(pharmacy.pha_time1S,0,2)}:${fn:substring(pharmacy.pha_time1S,2,4)}
						-
						${fn:substring(pharmacy.pha_time1C,0,2)}:${fn:substring(pharmacy.pha_time1C,2,4)}
				    </c:if>
				    <c:if test="${day==2 and (pharmacy.pha_time2S!='null' or pharmacy.pha_time2C!='null')}">
						${fn:substring(pharmacy.pha_time2S,0,2)}:${fn:substring(pharmacy.pha_time2S,2,4)}
						-
						${fn:substring(pharmacy.pha_time2C,0,2)}:${fn:substring(pharmacy.pha_time2C,2,4)}
				    </c:if>
				    <c:if test="${day==3 and (pharmacy.pha_time3S!='null' or pharmacy.pha_time3C!='null')}">
						${fn:substring(pharmacy.pha_time3S,0,2)}:${fn:substring(pharmacy.pha_time3S,2,4)}
						-
						${fn:substring(pharmacy.pha_time3C,0,2)}:${fn:substring(pharmacy.pha_time3C,2,4)}
				    </c:if>
				    <c:if test="${day==4 and (pharmacy.pha_time4S!='null' or pharmacy.pha_time4C!='null')}">
						${fn:substring(pharmacy.pha_time4S,0,2)}:${fn:substring(pharmacy.pha_time4S,2,4)}
						-
						${fn:substring(pharmacy.pha_time4C,0,2)}:${fn:substring(pharmacy.pha_time4C,2,4)}
				    </c:if>
				    <c:if test="${day==5 and (pharmacy.pha_time5S!='null' or pharmacy.pha_time5C!='null')}">
						${fn:substring(pharmacy.pha_time5S,0,2)}:${fn:substring(pharmacy.pha_time5S,2,4)}
						-
						${fn:substring(pharmacy.pha_time5C,0,2)}:${fn:substring(pharmacy.pha_time5C,2,4)}
				    </c:if>
				    <c:if test="${day==6 and (pharmacy.pha_time6S!='null' or pharmacy.pha_time6C!='null')}">
						${fn:substring(pharmacy.pha_time6S,0,2)}:${fn:substring(pharmacy.pha_time6S,2,4)}
						-
						${fn:substring(pharmacy.pha_time6C,0,2)}:${fn:substring(pharmacy.pha_time6C,2,4)}
				    </c:if>
					<c:if test="${day==7 and (pharmacy.pha_time7S!='null' or pharmacy.pha_time7C!='null')}">
						${fn:substring(pharmacy.pha_time7S,0,2)}:${fn:substring(pharmacy.pha_time7S,2,4)}
						-
						${fn:substring(pharmacy.pha_time7C,0,2)}:${fn:substring(pharmacy.pha_time7C,2,4)}
				    </c:if>
		    	</div>
			</div>
			<div class="col-6">
				<div class="fs-15 fw-7 text-black-6">기타정보</div>
				<div id="detail_hospitalEtc" class="fs-15 fw-1">
					<c:if test="${pharmacy.pha_etc!='null'}">
						${pharmacy.pha_etc}
					</c:if>
					<c:if test="${pharmacy.pha_etc=='null'}">
						없음
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-gray-0"></div>
	
	<div id="detail_hosSub">
		<p class="fs-18 fw-7">약국 정보</p>
		<div class="text-black-5 fs-15 fw-6">
			<c:if test="${pharmacy.pha_info!='null'}">
				${pharmacy.pha_info}
			</c:if>
			<c:if test="${pharmacy.pha_info=='null'}">
				정보없음
			</c:if>
		</div>
	</div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-gray-0"></div>
	
	<div id="detail_hosPosition">
		<p class="fs-18 fw-7">약국 위치</p>
		<div class="fs-14 text-black-6 fw-7 mb-3">${pharmacy.pha_addr}</div>
		<div class="mb-3">
			<jsp:include page="/WEB-INF/views/pharmacy/staticPhaMap.jsp"/>
		</div>
		<c:if test="${pharmacy.pha_mapImg!='null'}">
			<div class="fs-15 text-black-7 fw-9 text-center">&lt;간이약도&gt;</div>
			<div class="fs-14 text-black-6 fw-7 text-center">${pharmacy.pha_mapImg}</div>
		</c:if>
	</div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-gray-0"></div>
	
	<div id="detail_hosTell" class="d-flex">
		<div class="col-6">
			<p class="fs-18 fw-7">약국 번호</p>
			<p>${pharmacy.pha_tell1}</p>
		</div>
	</div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-gray-0"></div>
	
	<div id="detail_btn_box">
		<div class="d-flex justify-content-center align-items-center">
			<div id="call_btn">
				전화하기
			</div>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
$('#call_btn').click(function() {
 	// 복사할 텍스트 지정
    var textToCopy = '${pharmacy.pha_tell1}';
    
    // 임시 텍스트 영역 생성
    var tempInput = $('<input>');
    $('body').append(tempInput);
    tempInput.val(textToCopy).select();
    
    // 클립보드에 텍스트 복사
    document.execCommand('copy');
    
    // 임시 텍스트 영역 제거
    tempInput.remove();
    
    // 알림 메시지
    alert('전화번호가 클립보드에 복사되었습니다: ' + textToCopy);
});
</script>