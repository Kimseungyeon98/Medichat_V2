<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

<div class="container">
	<div class="d-flex">
		<div>
			<img id="header-notification" src="/images/notification.png" width="35" height="35">
		</div>
		<div>
			<p class="text-danger">${notiCnt}</p>
		</div>
		<div>
			<button class="btn btn-primary" onclick="showNoti()">알림보기</button>
		</div>
		<div id="notiBox">
			<c:forEach var="noti" items="${notiList}">
				<div class="noti">
					${noti.noti_message}
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="text-center" style="height:300px;">
	</div>
	<span id="messages"></span>
	<button onclick="ring()">흔들어</button>
	<div><p id="shakeCnt">0</p></div>
</div>


<script>
const cnt_text = document.getElementById('shakeCnt');

function showNoti(){
	if($('#notiBox').css("display")=='none'){
		$('#notiBox').show();
	} else {
		$('#notiBox').hide();
	}
}

function ring() {
	var img = document.getElementById('header-notification');
	img.src = "/images/notification-bell.png";
	img.classList.add('bell-shake');

	// 애니메이션이 끝난 후 클래스 제거 (애니메이션을 다시 트리거하기 위해)
	img.addEventListener('animationend', function() {
  		img.classList.remove('bell-shake');
  		img.src="/images/notification.png";
	}, { once: true });
	let cnt = Math.floor($('#shakeCnt').text());
	$('#shakeCnt').text('');
	$('#shakeCnt').text(cnt+1);
}

	
	//서버에서 /sse 엔드포인트로부터 SSE를 수신
	var eventSource = new EventSource('/sse/subscribe');
	
	// 메시지를 수신할 때마다 호출되는 이벤트 리스너
	eventSource.onmessage = function(event) {
		const notification = JSON.parse(event.data);
	    $('#notiBox').append('<div class="noti">')
	    			.append(event.data.noti_message)
	    			.append('</div>');
	    ring();
	};
	
	// 연결이 열렸을 때 호출되는 이벤트 리스너
	eventSource.onopen = function(event) {
	    console.log("Connection to server opened.");
	};
	
	// 에러가 발생했을 때 호출되는 이벤트 리스너
	eventSource.onerror = function(event) {
	    console.log("Error occurred: ", event);
	    if (event.eventPhase == EventSource.CLOSED) {
	        console.log("Connection was closed.");
	    }
	};
</script>
<style>
@keyframes bell-shake {
  0%, 100% {
    transform: rotate(0deg);
  }
  25% {
    transform: rotate(-45deg);
  }
  50% {
    transform: rotate(45deg);
  }
  75% {
    transform: rotate(-45deg);
  }
}
.bell-shake {
  animation: bell-shake 0.5s;
  animation-iteration-count: 2;
  transform-origin: top center; /* 중심 축을 이미지의 위쪽 중앙으로 설정 */
}
</style>