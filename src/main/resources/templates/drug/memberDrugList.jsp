<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.global.min.js"></script>
<script src="${pageContext.request.contextPath}/js/drug.member.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {//'DOMContentLoaded'이벤트는 문서 내 DOM이 완전히 로드되고 파싱되었을 때 발생
        // FullCalendar 초기화
        var calendarEl = document.getElementById('calendar');
        window.calendar = new FullCalendar.Calendar(calendarEl, {
        	timeZone: 'Asia/Seoul',	//날짜를 한국 기준으로 설정
            selectable: true,	//날짜 선택가능
            height: 'auto',	//높이 자동 조절
            width: 'auto',	//너비 자동 조절
            headerToolbar: {
            	left: 'title',
                //right: 'today,dayGridMonth,multiMonthYear prev,next'
            	right: 'today prev,next'
            },
            locale: 'ko', // 로케일 설정
            initialDate: new Date().toISOString().slice(0, 10), // 오늘 날짜로 설정
            initialView: 'dayGridMonth', // 초기 보여줄 뷰 설정
            dayCellContent: function(e) {
                // 날짜 셀 내용 설정 (1일, 2일 -> '일' 빼고 1, 2만 보이게)
                var dayNumber = e.date.getDate();
                var html = '<div class="fc-daygrid-day-number">' + dayNumber + '</div>';
                return { html: html };
            },
            fixedWeekCount: false, // 다음 달의 날짜가 보여지지 않게 설정 
            //이벤트 적용
            events:[
            	<c:forEach var="memberDrug" items="${memberDrug}" varStatus="status">
            		<c:if test="${status.index>0}">,</c:if>
            		{
            			id: '${memberDrug.med_num}',
            			title: '${memberDrug.med_title}',
            			start: '${memberDrug.med_sdate}',
	           			end : (function() {//풀캘린더의 select end 특성으로 인해서 DB에 저장된 값 보다 하루 뒤의 값을 출력
	           	               var date = new Date('${memberDrug.med_edate}');
	           	               date.setDate(date.getDate() + 1);
	           	               return date.toISOString().slice(0, 10);
	           	            })(),
            			allDay: true,
            			med_name: '${memberDrug.med_name}',
            			med_time: '${memberDrug.med_time}',
            			med_dosage: '${memberDrug.med_dosage}',
            			med_note: '${memberDrug.med_note}'
            		}
            	</c:forEach>
            	
            ],
            eventColor: '#40916c',
            /*----------캘린더에서 드래그로 이벤트 생성----------*/
            select:function(arg){
            	var today = new Date(); //현재 날짜 및 시간
                var selectedSDate = new Date(arg.start); //선택한 시작 날짜
                var selectedEDate = new Date(arg.end); //선택한 끝 날짜
                
                //끝 날짜에서 하루 빼기(이게 없으면 오늘 날짜 선택 불가)
                selectedEDate.setDate(selectedEDate.getDate() - 1); //풀캘린더의 배타적 기능으로 인해 수동적으로 날짜 변경 

                selectedSDate.setHours(0, 0, 0, 0);	//선택한 날짜의 시간 초기화(한국 기준으로 날짜를 선택했기 때문에)
                selectedEDate.setHours(0, 0, 0, 0);
                
            	console.log('오늘날짜:',today);
            	console.log('시작시간:', selectedSDate);
            	console.log('끝시간:', selectedEDate);
            	console.log('end:', arg.end);
            	
            	if(selectedSDate > today || selectedEDate > today){//arg.start : 선택한 날짜의 시작 시간을 나타내는 Date 객체
            		alert('오늘 이후 날짜는 선택할 수 없습니다.');
            	}else{   		
            		console.log('변경 전 끝시간:', selectedEDate);
            		console.log('selectedEDate.toISOString().slice(0, 10):', selectedEDate.toISOString().slice(0, 10));
            		
            		selectedEDate.setDate(selectedEDate.getDate() + 1);//원상태로 되돌리기
            		
            		console.log('변경 후 끝시간:', selectedEDate);
            		console.log('selectedEDate.toISOString().slice(0, 10):', selectedEDate.toISOString().slice(0, 10));
            		
            		//선택한 날짜가 보여지도록 처리
            		$('#selectedSDate').val(arg.start.toISOString().slice(0, 10));
            		$('#selectedEDate').val(selectedEDate.toISOString().slice(0, 10));//끝날짜
            		$('#drugModal').css('display', 'block'); // 모달 표시
            	    $('.modal-bg').css('display', 'block'); // 모달 배경 표시
            	}
            },
          	/*----------생성된 이벤트 클릭(수정/삭제)----------*/
          	//이벤트 클릭
            eventClick:function(info){//이벤트 클릭 시 기존 데이터 출력
            	var event = info.event;//이벤트 객체 가져오기
                $('#updateDrug input[name="med_num"]').val(event.id);
                $('#updateDrug input[name="med_title"]').val(event.title);
                //의약품 목록
                var med_names = event.extendedProps.med_name;
                modifyDrugList(med_names);
                $('#updateDrug input[name="med_sdate"]').val(event.start.toISOString().slice(0, 10));
                
                // end 날짜에서 하루 빼기
                //FullCalendar의 Long Event end 특성으로 인해 하루를 빼고 출력해야 DB의 값과 동일한 값이 보여짐
			   	var endDate = new Date(event.end);
			   	endDate.setDate(endDate.getDate() - 1);
			   	$('#updateDrug input[name="med_edate"]').val(endDate.toISOString().slice(0, 10));
			   
                //복용시간 체크박스
                $('#updateDrug input[name="med_time"]').each(function() {
                   $(this).prop('checked', event.extendedProps.med_time.includes($(this).val()));
                });
                $('#updateDrug input[name="med_dosage"]').val(event.extendedProps.med_dosage);
                $('#updateDrug textarea[name="med_note"]').val(event.extendedProps.med_note);
                $('#updateDrug').css('display', 'block'); // 모달 표시
                $('.modal-bg').css('display', 'block'); // 모달 배경 표시
            },
            eventSourceFailure(error) {
                if (error instanceof JsonRequestError) {
                  console.log(`Request to ${error.response.url} failed`)
                }
              }
        });
        calendar.render();
    });//end of fullcalendar
    
    /*====================모달창====================*/
    $(document).ready(function(){
    	$('.close-button').click(function(){//모달 닫기 버튼
    		$(this).closest('.modal').css('display', 'none'); // 모달 숨기기
    	    $('.modal-bg').css('display', 'none'); // 모달 배경 숨기기
    	});
    });
    
    /*==========기존 의약품 데이터 수정폼에 출력==========*/
    let med_list = [];

    //기존 의약품 데이터를 수정 폼에 출력하는 함수
    function modifyDrugList(med_names) {
        //의약품 배열 초기화
        med_list = med_names.split(',');
        console.log(med_list);
        $('#moDrugSelect').empty(); // 이전에 추가된 의약품들을 모두 제거
        //의약품 데이터를 기반으로 <span> 요소 생성
        med_list.forEach(function(med_name) {
            let choice_med = '<span class="moDrugSelect-span" data-name="' + med_name + '">';
            choice_med += med_name + '<sup>&times;</sup></span>';
            $('#moDrugSelect').append(choice_med);
            console.log("의약품 기반 <span> 생성:" + med_name);
        });
    }

    //검색된 의약품 선택
    $(document).on('click', '#moSearchDrugList li', function() {
        let med_name = $(this).text(); //선택한 의약품
        //
        console.log("med_name:" + med_name);
        med_list.push(med_name);
        console.log(med_list);
        //선택한 의약품 화면에 표시
        let choice_med = '<span class="moDrugSelect-span" data-name="' + med_name + '">';
        choice_med += med_name + '<sup>&times;</sup></span>';
        $('#moDrugSelect').append(choice_med);
        $('#moDrug_search').val('');
        $('#moSearchDrugList').empty();
    });
    
    //검색된 의약품 삭제
    $(document).on('click','.moDrugSelect-span',function(){
		let med_name = $(this).attr('data-name');
		//의약품이 저장된 배열에서 의약품명 제거
		med_list.splice(med_list.indexOf(med_name),1);
		$(this).remove();//span 태그 삭제
		console.log("검색 의약품 삭제:" + med_name);
	});
</script>
<br>
<!-- 등록 모달 시작 -->
<div class="modal" id="drugModal">
	<div class="modal-header">
		<div>의약품 복용 기록 등록</div>
		<div class="close-button">&times;</div>
	</div>
	<div class="modal-body">
	<form action="memberDrugSearch" method="post" id="drugSearch">
		<ul>
			<li>
				<label>증상</label> 
				<input type="text" id="title" name="med_title">
			</li>
			<li>
				<label>의약품명</label> 
				<input type="text" id="drug_search" autocomplete="off">
				<ul id="searchDrugList"></ul>
				<div id="drugSelect"></div>
			</li>
			<li>
				<label>복용일자</label>
				<input type="date" id="selectedSDate" name="med_sdate">
				<label>~</label>
				<input type="date" id="selectedEDate" name="med_edate">
			</li>
			<li>
				<label>복용시간</label> 
				<input type="checkbox" name="med_time" value="아침" class="drug">아침
				<input type="checkbox" name="med_time" value="점심">점심
				<input type="checkbox" name="med_time" value="저녁">저녁
				<input type="checkbox" name="med_time" value="자기 전">자기 전
			</li>	
			<li>
				<label>복용량</label>
				<input type="text" id="memberDosage" name="med_dosage"><br>
			</li>
			<li>
				<label>메모</label>
				<textarea name="med_note"></textarea>
			</li>	
		</ul>
		<div>
			<input type="submit" value="전송" class="submit-btn">
		</div>
	</form>
	</div>
</div>
<!-- 등록 모달 끝 -->
<div class="modal-bg"></div>
<!-- 수정 모달 시작 -->
<div class="modal" id="updateDrug">
	<div class="modal-header">
		<div>의약품 복용 기록 수정</div>
		<div class="close-button">&times;</div>
	</div>
	<div class="modal-body">
	<form action="memberDrugSearch" method="post" id="modifyDrugSearch">
		<input type="hidden" name="med_num">
		<ul>
			<li>
				<label>증상</label>
				<input type="text" id="mo_title" name="med_title">
			</li>
			<li>
				<label>의약품명</label> 
				<input type="text" id="moDrug_search" autocomplete="off">
				<ul id="moSearchDrugList"></ul>
				<div id="moDrugSelect"></div>
			</li>
			<li>
				<label>복용일자</label>
				<input type="date" id="moSelectedSDate" name="med_sdate">
				<label>~</label>
				<input type="date" id="moSelectedEDate" name="med_edate">
			</li>
			<li>
				<label>복용시간</label> 
				<input type="checkbox" name="med_time" value="아침">아침
				<input type="checkbox" name="med_time" value="점심">점심
				<input type="checkbox" name="med_time" value="저녁">저녁
				<input type="checkbox" name="med_time" value="자기 전">자기 전
			</li>	
			<li>
				<label>복용량</label>
				<input type="text" id="moMemberDosage" name="med_dosage"><br>
			</li>
			<li>
				<label>메모</label>
				<textarea name="med_note"></textarea>
			</li>
		</ul>
		<div class="modal-btn">
			<input type="submit" value="수정" class="submit-btn">
			<input type="button" value="삭제" class="delete-btn">
		</div>
	</form>
	</div>
</div>
<!-- 수정 모달 끝 -->
<br>
<!-- 캘린더 -->
<h4 class="drug-title"><b>약 복용 내역</b></h4>
<div id='calendar'></div>
<br>
<!-- 스타일 적용 -->
<style>
/*----------FullCalendar----------*/
.fc-day-sun a {
    color: #ff4242;
    text-decoration: none;
}
.fc-day-sat a {
    color: #2027f7;
    text-decoration: none;
}
.fc-day-mon a, .fc-day-tue a, .fc-day-wed a, .fc-day-thu a, .fc-day-fri a {
    color: #4a4a4a;
    text-decoration: none;
}
.fc .fc-daygrid-day.fc-day-today {
    background-color: #fafffb;
}
.fc .fc-highlight{
    background-color: #e4f8e5;
}
.fc-daygrid-day-selected {
    background-color: #e4f8e5;
}
.fc-daygrid-day-selected {
    background-color: #e4f8e5;
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
#calendar {
    margin: 0 auto;
    width: 90%;
    max-width: 800px;
}
.drug-title{
	margin-bottom:50px;
}
/*----------모달 스타일----------*/
.modal {
    display: none;
    position: fixed;
    z-index: 1000; /* 다른 요소들보다 위에 위치하도록 설정 */
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%); /* 화면 가운데 정렬 */
    background-color: white;
    width: 500px; /* 모달의 고정 너비 */
    height:620px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); /* 그림자 효과 */
    border-radius: 8px; /* 모든 모서리를 둥글게 설정 */
}

.modal-bg {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: none;
    z-index: 998;
    
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #40916c; /* 헤더 배경색 */
    color: white; /* 헤더 글자색 */
    height: 50px; /* 헤더 높이 */
}

.modal-body {
    background-color: white; /* 모달 본문 배경색 */
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center; /* 수평 가운데 정렬 */
    justify-content: center; /* 수직 가운데 정렬 */
    padding: 20px;
    height: calc(100% - 50px); /* 모달 헤더를 제외한 높이 */
    box-sizing: border-box; /* 패딩을 높이 계산에 포함 */
     overflow-y: auto; /* 내용이 넘칠 경우 스크롤 표시 */
}

.close-button {
    cursor: pointer;
    font-size: 24px;
    font-weight: bold;
    margin-right: 10px;
    color: white;
    background: none;
    border: none;
}

.modal-body ul {
    list-style-type: none;
    padding: 0;
}

.modal-body li {
    margin-bottom: 10px;
}

input[type="text"],
input[type="date"],
textarea {
    width: calc(100% - 10px);
    padding: 5px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
}
.modal-btn{
	float: right; /* 버튼 오른쪽 정렬 */
}
/*전송 버튼*/
.submit-btn {
    background-color: #40916c; /* 버튼 배경색 */
    color: white; /* 버튼 글자색 */
    border: none;
    padding: 8px 18px;
    cursor: pointer;
    border-radius: 4px;
    margin-top: 10px;
    font-size: 16px; /* 폰트 크기 조절 */
}
.delete-btn{
	background-color: white; /* 버튼 배경색 */
    color: #40916c; /* 버튼 글자색 */
     border: 2px solid rgb(76, 165, 165); /* 버튼 테두리 추가 */
   padding: 6px 16px;
    cursor: pointer;
    border-radius: 4px;
    margin-top: 10px;
     font-size: 16px; /* 폰트 크기 조절 */
}

input[type="submit"]:hover,
input[type="button"]:hover {
    background-color: #006666; /* 버튼 호버 배경색 */
}

label {
    display: inline-block;
    margin-top: 10px;
}

.modal-body {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.modal-body label,
.modal-body input {
    display: inline-block;
    margin-right: 10px;
}

.modal-body input[type="date"] {
    width: auto;
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
}
/* 의약품 검색 */
.moDrugSelect-span, .drugSelect-span {
    display: inline-block;
    background: #e9ecef;
    border-radius: 4px;
    padding: 2px 8px;
    margin-right: 5px;
    margin-bottom: 5px;
    margin-top: 10px;
    font-size: 14px;
}

.moDrugSelect-span sup, .drugSelect-span sup {
    margin-left: 4px;
    cursor: pointer;
    font-size: 12px;
    color: red;
}
#searchDrugList, #moSearchDrugList {
    list-style-type: none; /* 기본 불릿 제거 */
    padding: 0;
    margin: 0;
    max-height: 200px; /* 목록의 최대 높이 설정 */
    overflow-y: auto; /* 내용이 넘칠 경우 스크롤 표시 */
    border: 1px solid #ccc; /* 경계선 추가 */
    background: white; /* 배경색 */
    position: absolute; /* 위치 조정 */
    width:403px;
    z-index: 1000; /* 다른 요소들 위에 표시되도록 */
}
#searchDrugList li,, #moSearchDrugList li {
    padding: 5px; /* 패딩 추가 */
    cursor: pointer; /* 포인터 커서 적용 */
    border-bottom: 1px solid #ddd; /* 목록 아이템의 하단 경계선 */
}
#searchDrugList li:hover,, #moSearchDrugList li:hover {
    background-color: #f0f0f0; /* 마우스를 올렸을 때 배경색 변경 */
}
</style>