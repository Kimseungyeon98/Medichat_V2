<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="close-file-form">
	<div class="form-title bg-green-7 fs-20 fw-4">
		진료 종료
	</div>
	<div class="close-form-body">
		<div class="form-notice margin-left-15">
			<span class="notice-title fw-8 fs-24">환자 열람 파일 등록</span>
			<span class="notice-sub fs-16">등록된 진료 파일은 환자의 진료비 결제 후에 자동으로 전송됩니다.</span>
			<span class="notice-sub fs-16">전송하지 않고 취소하는 경우, 등록된 파일이 저장되지 않습니다.</span>
		</div>
		<div class="form-list">
			<table id="file_table">
				<tr class="list-head bg-gray-3">
					<th class="list-name">파일명</th>
					<th class="list-type">파일 유형</th>
					<th class="list-valid-date">유효기간</th>
					<th class="list-delete"></th>
				</tr>
			</table>
		</div>
		<div class="form-body">
		<form action="file_input" method="post" id="file_input" enctype="multipart/form-data">
			<input type="hidden" name="chat_num" id="close_chat_num" value="">
			<label for="file_type">파일 유형</label>
			<select name="file_type" id="file_type">
				<option value="0">처방전</option>
				<option value="1">소견서</option>
				<option value="2">진단서</option>
				<option value="3">진료비 세부내역서</option>
			</select>
			<label for="file_valid_date">유효기간</label>
			<input type="date" name="file_valid_date" id="file_valid_date"><br>
			<div class="margin-top-5">
			<label for="select_file" class="">파일 선택</label>
			<input type="file" name="select_file" id="select_file" accept=".pdf,.doc,.docx">
			<input type="submit" value="등록" class="btn-chat">
			</div>
		</form>
		<div class="fs-16">&#8251;파일은 word(.doc, .docx)와 PDF 형식 파일만 전송 가능합니다.</div>
		</div>
		<div class="form-button">
		<button type="button" class="close-form-btn btn-chat fs-20" id="close_cancel">취소</button>
		<button type="button" class="close-form-btn btn-chat fs-20" id="close_file_next">다음으로</button>
		</div>
	</div>
</div>
<div class="close-file-form-bg"></div>	