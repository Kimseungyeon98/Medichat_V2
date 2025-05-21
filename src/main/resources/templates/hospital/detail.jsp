<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/hos.css" type="text/css">
<div>
	<div class="p-3">
		<span class="text-lightgray fw-7 fs-13">홈 > 병원 찾기 > 검색 결과 > 병원 상세</span>
		<div id="detail_hosName">
			<h4 class="fw-7">${hospital.hos_name}</h4>
			<div class="d-flex">
				<div class="detail-hospital-info-item fs-14 fw-7">${hospital.hos_divName}</div>
				<c:if test="${hospital.hos_weekendAt=='Y'}">
					<div class="detail-hospital-info-item fs-14 fw-7">주말 운영</div>
				</c:if>
				<c:if test="${hospital.hos_eryn==1}">
					<div class="detail-hospital-info-item fs-14 fw-7">응급실 운영</div>
				</c:if>
			</div>
		</div>
	</div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-gray-0"></div>
	
	
	<div id="detail_hosTime" class="p-3">
		<div class="d-flex align-items-center mb-3">
			<div class="fs-18 fw-7">진료 시간</div>
			<c:if test="${day==1}">
				<c:if test="${hospital.hos_time1S<=time and time<hospital.hos_time1C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time1S>time or hospital.hos_time1C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==2}">
				<c:if test="${hospital.hos_time2S<=time and time<hospital.hos_time2C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time2S>time or hospital.hos_time2C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==3}">
				<c:if test="${hospital.hos_time3S<=time and time<hospital.hos_time3C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time3S>time or hospital.hos_time3C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==4}">
				<c:if test="${hospital.hos_time4S<=time and time<hospital.hos_time4C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time4S>time or hospital.hos_time4C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==5}">
				<c:if test="${hospital.hos_time5S<=time and time<hospital.hos_time5C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time5S>time or hospital.hos_time5C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==6}">
				<c:if test="${hospital.hos_time6S<=time and time<hospital.hos_time6C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time6S>time or hospital.hos_time6C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		    <c:if test="${day==7}">
				<c:if test="${hospital.hos_time7S<=time and time<hospital.hos_time7C}">
					<div class="greenCircle ms-2 mx-1"></div>
					<div class="greenText">진료중</div>
				</c:if>
				<c:if test="${hospital.hos_time7S>time or hospital.hos_time7C<=time}">
					<div class="redCircle ms-2 mx-1"></div>
					<div class="redText">진료종료</div>
				</c:if>
		    </c:if>
		</div>
		<div class="bg-gray-0 p-3 rounded-3 row">
			<div id="detail_timeAndEtc" class="col-6">
				<div class="fs-15 fw-7 text-black-6">오늘</div>
				<div class="fs-15 fw-3">
					<c:if test="${day==1 and (hospital.hos_time1S!='null' or hospital.hos_time1C!='null')}">
						${fn:substring(hospital.hos_time1S,0,2)}:${fn:substring(hospital.hos_time1S,2,4)}
						-
						${fn:substring(hospital.hos_time1C,0,2)}:${fn:substring(hospital.hos_time1C,2,4)}
				    </c:if>
				    <c:if test="${day==2 and (hospital.hos_time2S!='null' or hospital.hos_time2C!='null')}">
						${fn:substring(hospital.hos_time2S,0,2)}:${fn:substring(hospital.hos_time2S,2,4)}
						-
						${fn:substring(hospital.hos_time2C,0,2)}:${fn:substring(hospital.hos_time2C,2,4)}
				    </c:if>
				    <c:if test="${day==3 and (hospital.hos_time3S!='null' or hospital.hos_time3C!='null')}">
						${fn:substring(hospital.hos_time3S,0,2)}:${fn:substring(hospital.hos_time3S,2,4)}
						-
						${fn:substring(hospital.hos_time3C,0,2)}:${fn:substring(hospital.hos_time3C,2,4)}
				    </c:if>
				    <c:if test="${day==4 and (hospital.hos_time4S!='null' or hospital.hos_time4C!='null')}">
						${fn:substring(hospital.hos_time4S,0,2)}:${fn:substring(hospital.hos_time4S,2,4)}
						-
						${fn:substring(hospital.hos_time4C,0,2)}:${fn:substring(hospital.hos_time4C,2,4)}
				    </c:if>
				    <c:if test="${day==5 and (hospital.hos_time5S!='null' or hospital.hos_time5C!='null')}">
						${fn:substring(hospital.hos_time5S,0,2)}:${fn:substring(hospital.hos_time5S,2,4)}
						-
						${fn:substring(hospital.hos_time5C,0,2)}:${fn:substring(hospital.hos_time5C,2,4)}
				    </c:if>
				    <c:if test="${day==6 and (hospital.hos_time6S!='null' or hospital.hos_time6C!='null')}">
						${fn:substring(hospital.hos_time6S,0,2)}:${fn:substring(hospital.hos_time6S,2,4)}
						-
						${fn:substring(hospital.hos_time6C,0,2)}:${fn:substring(hospital.hos_time6C,2,4)}
				    </c:if>
					<c:if test="${day==7 and (hospital.hos_time7S!='null' or hospital.hos_time7C!='null')}">
						${fn:substring(hospital.hos_time7S,0,2)}:${fn:substring(hospital.hos_time7S,2,4)}
						-
						${fn:substring(hospital.hos_time7C,0,2)}:${fn:substring(hospital.hos_time7C,2,4)}
				    </c:if>
		    	</div>
			</div>
			<div class="col-6">
				<div class="fs-15 fw-7 text-black-6">기타정보</div>
				<div id="detail_hospitalEtc" class="fs-15 fw-1">
					<c:if test="${hospital.hos_etc!='null'}">
						${hospital.hos_etc}
					</c:if>
					<c:if test="${hospital.hos_etc=='null'}">
						없음
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
	<div class="line"></div>
	<div style="height:18px;" class="bg-gray-0"></div>
	
	<div id="detail_hosSub">
		<p class="fs-18 fw-7">병원 정보</p>
		<div class="text-black-5 fs-15 fw-6">
			<c:if test="${hospital.hos_info!='null'}">
				${hospital.hos_info}
			</c:if>
			<c:if test="${hospital.hos_info=='null'}">
				정보없음
			</c:if>
		</div>
	</div>
	
	<!-- 여기에 의사 연혁 -->
	<div id="doctor_history"></div>
	
	<div class="line"></div>
	<div style="height:15px;" class="bg-gray-0"></div>
	
	<div id="detail_hosPosition">
		<p class="fs-18 fw-7">병원 위치</p>
		<div class="fs-14 text-black-6 fw-7 mb-3">${hospital.hos_addr}</div>
		<div class="mb-3">
			<jsp:include page="/WEB-INF/views/hospital/staticHosMap.jsp"/>
		</div>
		<c:if test="${hospital.hos_mapImg!='null'}">
			<div class="fs-15 text-black-7 fw-9 text-center">&lt;간이약도&gt;</div>
			<div class="fs-14 text-black-6 fw-7 text-center">${hospital.hos_mapImg}</div>
		</c:if>
	</div>
	
	<div class="line"></div>
	<div style="height:20px;" class="bg-gray-0"></div>
	
	<div id="detail_hosTell" class="d-flex">
		<div class="col-6">
			<p class="fs-18 fw-7">병원 번호</p>
			<p>${hospital.hos_tell1}</p>
		</div>
		<c:if test="${hospital.hos_eryn==1}">
			<div class="col-6">
				<p class="fs-18 fw-7">응급실 번호</p>
				<p>${hospital.hos_tell3}</p>
			</div>
		</c:if>
	</div>

	<div class="line"></div>
	<div style="height:18px;" class="bg-gray-0"></div>
	
	<div id="detail_hosRev">
		<p class="fs-18 fw-7">진료 후기</p>
		<c:forEach items="${reviewList}" var="review">
			<div class="detail-hosRev-item d-flex justify-content-around align-items-center bg-black-1">
				<div class="detail_hosRev_profile">
					<img src="${PageContext.reqeust.contextPath }/image_bundle/face.png" width="35" height="35" class="rounded-circle">
					<div class="text-black-6 fw-7 fs-15 text-center">${fn:substring(review.mem_name,0,1)}OO</div>
				</div>
				<div class="d-flex justify-content-center align-items-center">
					<div>
						<div class="detail_hosRev_title fw-7">
							${review.rev_title}
						</div>
						<div class="detail_hosRev_content overflowText">
							${review.rev_content}
						</div>
					</div>
					<div>
						<img src="/images/down.png" width="20" height="20" class="hosRev_more_content_icon">
					</div>
				</div>
				<div class="detail_hosRev_score d-flex justify-content-center align-items-center">
					<img src="/images/star.png" width="15" height="15">
					<div>${review.rev_grade }</div>
				</div>
			</div>
		</c:forEach>
		<div class="text-center text-decoration-none text-black-6">${reviewPage }</div>
	</div>
	
	<div class="line"></div>
	<div style="height:20px;" class="bg-gray-0"></div>
	
	<div class="float-cleatr" id="detail_btn_box">
		<div class="d-flex justify-content-center align-items-center">
			<c:if test="${hospital.doc_cnt>0}">
			<button id="reservation_btn" data-hos-name="${hospital.hos_name}" data-hos-num="${hospital.hos_num}">
                진료 예약하기
            </button>
            </c:if>
			<div id="call_btn">
				전화하기
			</div>
		</div>
	</div>
</div>

<div id="reservation_page" style="display:none;">
 <jsp:include page="/WEB-INF/views/reservation/reservation.jsp"/>
</div>   
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
$(function(){
	$('#call_btn').click(function() {
     	// 복사할 텍스트 지정
        var textToCopy = '${hospital.hos_tell1}';
        
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
	
	$('.hosRev_more_content_icon').on('click',function(event){
		const content = $(event.target).closest('.d-flex').find('.detail_hosRev_content');
        content.toggleClass('overflowText');
        content.toggleClass('heigtAuto');
	})
	
    $('#reservation_btn').click(function(event){
        $.ajax({
            url: '/reservation/reservation',
            method: 'get',
            data: {hos_num: '${hospital.hos_num}'},
            dataType: 'json',
            success: function(param) {
                if(param.result == 'success'){
                	$('#reservation_page').show();
                    initializeCalendar('${hospital.hos_num}');
                } else if(param.result == 'logout'){
                    alert('로그인 후 이용해주세요');
                    location.href = "${pageContext.request.contextPath}/member/login";
                } else if(param.result == 'doctor') {
                    alert('의사회원은 이용할 수 없습니다.');
                } else if(param.result == 'suspended') {
                    alert('정지회원입니다. 일반회원의 경우에만 이용할 수 있습니다.');
                } else if(param.result == 'unauthorized') {
                    alert('해당 서비스는 이용할 수 없습니다.');
                } else {
                    alert('예약 신청 페이지 오류 발생');
                }
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
        });
    }); //end of click event 
    
    
    let doctor_history_content = '';
    
    $.ajax({
    	url:'/doctor/doctorHistory',
    	method:'get',
    	data:{hos_num:'${hospital.hos_num}'},
    	dataType:'json',
    	success: function(param) {
    		if(param.doctor == 'empty'){
    			doctor_history_content += ''; //근무 의사가 없는 경우 공간 만들지 않기
    		}else{
	    		doctor_history_content += '<div class="line"></div><div style="height:15px;" class="bg-gray-0"></div>';
	    		doctor_history_content += '<p class="fs-18 fw-7" style="padding:0 20px;">의사 소개</p>';
	    		
	    		param.doctor.forEach(function(doctor) {
	    			doctor_history_content += '<div style="padding:20px 30px; display:flex; align-items:center;" >'
            doctor_history_content += '<div><img src="/doctor/docViewProfile?mem_num=' + doctor.doc_num + '" alt="' + doctor.mem_name + '" class="doctor-image" style="width: 80px; height: 80px; margin-right:10px;"></div>';
            doctor_history_content += '<div><span class="fs-15 fw-7">' + doctor.mem_name + ' 의사</span>';
            if (doctor.doc_history) {
                doctor_history_content += '<br><span class="fs-14">' + doctor.doc_history + '</span>';
            }
            doctor_history_content += '</div>';
            doctor_history_content += '</div>';
          });
    		}
    		$('#doctor_history').append(doctor_history_content);
    	},
    	error: function(){
    		alert('의사 연혁 출력 오류');
    	}
    });
});
</script>
