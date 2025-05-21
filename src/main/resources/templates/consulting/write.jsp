<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style>
    .error { 
	    color: red; 
	    display: none; 
    }
</style>
<div class="p-3">
	<p class="text-lightgray fw-7 fs-13">홈 > 의료 상담 > 글 작성</p>
	<img src="/images/question-icon.gif" width="55px" height="55px" class="mb-3">
	<div class="fs-22 fw-7 text-black-6">
		간단하게 진단 받아 보세요
	</div>
	<div class="fs-17 fw-6 text-black-4 my-3">
		의사 선생님들이 대기 중입니다
	</div>
	
	
	<form id="con_form" action="/consultings/create" method="post">
		<div id="con_title_div">
			<div id="con_title_label">
				<label for="con_title">제목</label>
			</div>
			<input type="text" name="con_title" id="con_title" class="form-control">
			<span class="error" id="title_error">제목을 입력해 주세요.</span>
		</div>
		
		<div id="con_content_div">
			<div id="con_content_label" class="d-flex justify-content-between">
				<label for="con_content">내용</label>
				<span id="content_length">0 / 2000</span>
			</div>
			<textarea rows="6" id="con_content" name="con_content" class="form-control"></textarea>
			<span class="error" id="content_error">내용을 입력해 주세요.</span>
		</div>
		
		<div id="con_type_div">
			<div id="con_type_label"><label for="con_type">카테고리</label></div>
			<select id="con_type" name="con_type" class="form-control">
				<option selected>증상 선택</option>
				<option value="0">구분없음</option>
				<option value="1">만성질환</option>
				<option value="2">여성질환</option>
				<option value="3">소화기질환</option>
				<option value="4">영양제</option>
				<option value="5">정신건강</option>
				<option value="6">처방약</option>
				<option value="7">탈모</option>
				<option value="8">통증</option>
				<option value="9">여드름,피부염</option>
				<option value="10">임신,성고민</option>
			</select>
			<span class="error" id="type_error">카테고리를 선택해 주세요.</span>
		</div>
		
		<div id="con_info_div">
			<p>메디챗 의료 상담 유의사항</p>
			<ul>
				<li>폭력적/공격적인 글, 서비스 목적과 맞지 않는 질문, 도배성 질문은 예고 없이 삭제될 수 있습니다.</li>
				<li>서비스 운영을 방해하는 행위가 반복적으로 발생할 경우, 회원 및 의료진 보호를 위해 서비스 이용이 한시적 또는 영구적오로 사전 안내 없이 제한될 수 있습니다.</li>
				<li>질문한 내용은 공개되나 회원 정보는 비공개이니 안심하세요.</li>
				<li>답변은 의학적 판단이나 진료 행위로 해석될 수 없으며, 메디챗과 답변 의료인은 이로 인해 발생하는 어떠한 책임도 지지 않습니다.</li>
				<li>정확한 진단을 위해서는 메디챗 웹 내에서 비대면 진료를 신청하거나, 병원을 직접 방문해 주세요.</li>
				<li>등록하신 내용은 고객센터를 통한 삭제 요청이 없는 한 지속적으로 남습니다.</li>
			</ul>
		</div>
		
		<div class="text-center">
			<input type="submit" id="consulting-write-btn" value="질문하기">
		</div>
	</form>
</div>

<script>
    $(document).ready(function() {
      $('#con_content').on('input', function() {
          var content = $(this).val();
          var contentLength = content.length;
          if (contentLength > 2000) {
              $(this).val(content.substring(0, 2000)); // 2000자까지만 입력되도록 자르기
              contentLength = 2000;
          }
          $('#content_length').text(contentLength + ' / 2000');
      });
      
      function validateForm() {
          let isValid = true;

          if ($('#con_title').val().trim() === '') {
              $('#title_error').show();
              isValid = false;
          } else {
              $('#title_error').hide();
          }

          if ($('#con_content').val().trim() === '') {
              $('#content_error').show();
              isValid = false;
          } else {
              $('#content_error').hide();
          }

          if ($('#con_type').val() === '증상 선택') {
              $('#type_error').show();
              isValid = false;
          } else {
              $('#type_error').hide();
          }

          return isValid;
      }

      $('#consulting-write-btn').click(function(event){
          if (!validateForm()) {
              event.preventDefault();
          }
      });

      $('#con_form').on('submit', function(event) {
          if (!validateForm()) {
              event.preventDefault();
          }
      });
      
    });
  </script>