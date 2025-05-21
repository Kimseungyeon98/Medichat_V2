<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- 회원정보 시작 -->
<div class="page-main">
	<ul>
		<li>
		<li>이름 : ${member.mem_name}</li>
	</ul>
</div>
<!--모달 팝업-->
<body>
	<div class="page-main">
		<ul>
			<li><a data-toggle="modal" href="#detailModal">회원정보</a>
				<div class="modal fade" id="detailModal" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">닫기</button>
								<h1 class="modal-title" align="center">회원정보</h1>
							</div>
							<div class="modal-body">
								<div class="member_detail">
									<ul>
										<li>이름 : ${member.mem_name}</li>
										<li>생년월일 : ${member.mem_birth}</li>
										<li>전화번호 : ${member.mem_phone}</li>
										<li>이메일 : ${member.mem_email}</li>
										<li>우편번호 : ${member.mem_zipcode}</li>
										<li>주소 : ${member.mem_address1} ${member.mem_address2}</li>
										<li>가입일 : ${member.mem_reg}</li>
										<c:if test="${!empty member.mem_modify}">
											<li>정보 수정일 : ${member.mem_modify}</li>
										</c:if>
									</ul>
									<div class="align-center">
									<input type="button" value="회원정보 수정" onclick="location.href='modifyUser'">
									<input type="button" value="비밀번호 변경" onclick="location.href='memberModify.jsp'">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<input type="button" value="약 복용 내역"><br>
	<input type="button" value="후기내역"><br>
	<input type="button" value="비대면 진료"><br>
	<input type="button" value="로그아웃">
</body>
</html>
<!--end 모달 팝업-->
<!-- 회원정보 끝 -->