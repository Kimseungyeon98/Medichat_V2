<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.all.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<!-- 비대면진료 채팅창 시작 -->
	<div class="chat-main col-9">
		<div class="chat-header bg-green-6" id="chat_header">
			<!-- 예약내역을 클릭한 경우에만 예약번호와 진료일시, 의사 표시 -->
		</div>
		<!-- 채팅방 표시 시작 -->
		<div class="chat-body" id="chat_body">
			<div class="chat-select-notice">
				<img src="/images/chatSelectNotice.png" width="55px" height="55px" style="margin-bottom:8px;">	
				<span class="fs-21 chat-notice fw-8">좌측 채팅방을 선택해주세요.</span>
			</div>
		</div>
		
		<!-- 입력창 표시 -->
		<div class="chat-input">
			<form action="#" id="chat_input">
				<input type="hidden" name="chat_num" id="chat_num" value="">
				<input type="hidden" name="res_date" id="res_date" value="">
				<input type="hidden" name="res_time" id="res_time" value="">
				<input type="hidden" name="res_num" id="res_num" value="">
				<c:if test="${user.mem_auth==1 || user.mem_auth==2}">
					<input type="hidden" name="msg_sender_type" value="0">
				</c:if>
				<c:if test="${user.mem_auth==3}">
					<input type="hidden" name="msg_sender_type" value="1">
				</c:if>
				<button class="chat-image bg-green-6" id="chat_image" disabled>
					<img src="${pageContext.request.contextPath}/images/chat_image.png" width="40px" height="40px">
				</button>
				<input type="text" name="msg_content" id="msg_content" disabled>
				<input type="submit" value="전송" id="message_btn" class="btn-chat" disabled>
			</form>
		</div>
	</div>
<!-- 비대면 진료 채팅창 끝 -->

<!-- 이미지 전송 시 모달 -->
	<div class="image-form">
		<div class="form-title bg-green-6 fs-20 fw-4">
		이미지 전송
			<button type="button" class="close-button">&times;</button>
		</div>
		<div class="form-body">
		<form action="image_input" id="image_input">
			<input type="hidden" name="chat_num" id="image_chat_num" value="">
			<input type="file" name="select_image" id="select_image" accept="image/gif,image/png,image/jpeg">
			<input type="submit" value="전송" class="btn-chat">
		</form>
		</div>
	</div>
<div class="image-form-bg"></div>
<!-- 모달 창 끝 -->

<!-- 이미지 크게 보기 모달 -->
<div class="msg-image-modal">
	<div class="form-title bg-green-6 fs-20 fw-4">
		이미지 크게 보기
		<button type="button" class="close-button">&times;</button>
	</div>
	<div id="msg_image"></div>
</div>
<div class="msg-image-modal-bg"></div>
<!-- 이미지 크게 보기 모달 끝 -->

<!-- 진료 종료 모달 창(파일 전송) 시작 -->
<jsp:include page="chatClose.jsp"/>
<!-- 진료 종료 모달 창(파일 전송) 끝 -->
<!-- 진료 종료 모달 창(진료비 청구) 시작 -->
<jsp:include page="chatPayment.jsp"/>
<!-- 진료 종료 모달 창(진료비 청구) 끝 -->