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
										<li>이름 : ${user.mem_name}</li>
										<li>이메일 : ${user.doc_email}</li>
										<li>병원정보 : ${user.hos_num}</li>
										<li>비대면 진료 여부 : ${user.doc_treat}</li>
										<li><a href="#" class="dropdown-toggle" data-toggle="dropdown">연혁 <span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li>${user.doc_history}</li>
											</ul>
										</li>
										<li>가입일 : ${user.doc_reg}</li>
									</ul>
									<div class="align-center">
										<input type="button" value="회원정보 수정"
											onclick="location.href='memberModify.jsp'">
										<input type="button" value="비밀번호 변경" onclick="location.href='#'">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div></li>
		</ul>
	</div>
</body>
<input type="button" value="예약내역">
<br>
<c:if test="${dmember.doc_treat == 1}">
    <input type="button" value="스케줄 관리">
<br>
</c:if>
<input type="button" value="비대면 진료"><br>
<input type="button" value="로그아웃">

</html>
<!--end 모달 팝업-->
<!-- 회원정보 끝 -->