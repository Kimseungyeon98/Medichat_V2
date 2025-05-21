<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<div class="page-main">
	<div class="page-one" style="padding-top: 16px;">
		<span class="text-lightgray fw-7 fs-13">홈 > 자주 묻는 질문(FAQ) >
			${faq.faq_title}</span>
		<h3 style="margin-top: 16px;">
			<img src="${pageContext.request.contextPath}/images/qna.png"
				width="45px;"> &nbsp; <b>${faq.faq_title}</b>
		</h3>
		<br> ${faq.f_reg_date} &nbsp;👁 ${faq.faq_hit} <br>
		<div class="line"></div>
		<br>
		<br> <img
			src="${pageContext.request.contextPath}/images/faqdoc.png"
			width="60px;"> &nbsp; ${faq.mem_id} 의사
		<br>
		<br> ${faq.faq_content} <br>
		<br>
		<br>
		<br> <img
			src="${pageContext.request.contextPath}/images/alertfaq.png"
			width="20px;"> 꼭 확인해주세요.<br> <span
			class=" fs-12 fw-4 text-black-3">본 답변은 의학적 판단이나 진료 행위로 해석 될 수
			없으며, 메디챗은 이로 인해 발생하는 어떠한 책임도 지지 않습니다.<br> 정확한 개인 증상 파악 및 진단은 의료
			기관 내방을 통해 진행하시기 바랍니다.<br> 고객님의 개인정보 보호를 위해 개인정보는 기재하지 않습니다.<br>
			서비스에 입력되는 데이터는 내부 정책에 따라 관리됩니다.
		</span><br>
		<br>
		<br>
		<c:if test="${user.mem_num == faq.mem_num}">
			<div class="align-center">
				<input type="button" class="default-btn" value="글 수정"
					onclick="location.href='faqUpdate?faq_num=${faq.faq_num}'">
				<input type="button" class="default-btn" value="글 삭제" id="f_delbtn">
			</div>
			<br>
			<script type="text/javascript">
				$(function() {
					$('#f_delbtn').click(function() {
						let choice = confirm('삭제하시겠습니까?');
						if (choice) {
							location.href = 'faqDelete?faq_num=${faq.faq_num}';
						}
					});
				});
			</script>
		</c:if>
	</div>
	<div class="align-center">
		<br>
		<br>
		<br> <input type="button" class="default-btn"
			onclick="location.href='${pageContext.request.contextPath}/hospitals'"
			style="width: 50% !important; height: 50px; font-size: 15pt;"
			value="비대면 진료 받으러 가기">
	</div>
</div>
