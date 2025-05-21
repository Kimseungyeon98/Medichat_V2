<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<script src="${pageContext.request.contextPath}/js/faq.total.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="page-main">
	<div class="page-one" style="padding-top:16px;">
		<span class="text-lightgray fw-7 fs-13">홈 > 자주 묻는 질문(FAQ)</span>
		<h3 style="margin-top:16px;"><img
				src="${pageContext.request.contextPath}/images/communication.png"
				width="45px;"> <b>자주 묻는 질문(FAQ)</b></h3>
		<br>
		<form action="faqList" id="form-faq" method="get" class="align-center">
			<div class="container-input " style="width: 500px; margin: 0 auto;">
				<div class="d-flex justify-content-center align-items-center ">
					<select name="keyfield" class="form-control" id="selectinput">
						<option disabled="disabled"
							<c:if test="${empty keyfield}">selected</c:if>>선택</option>
						<option value="1" <c:if test="${keyfield ==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${keyfield ==2}">selected</c:if>>내용</option>
						<option value="3" <c:if test="${keyfield ==3}">selected</c:if>>제목
							또는 내용</option>
					</select> &nbsp;&nbsp;<input type="text" id="h-search" class="form-control"
						placeholder="검색어를 입력하세요." name="keyword" value="${keyword}">
					<i id="h-search-icon" class="bi bi-search"></i>
					<script type="text/javascript">
						$('#h-search-icon').click(function() {
							$('#form-faq').submit();
						});
					</script>
				</div>
			</div>
		</form>
		<hr size="1" width="100%">
		<c:if test="${!empty user && user.mem_auth > 2}">
			<div class="align-right">
				<button class="default-btn" type="button"
					onclick="location.href='${pageContext.request.contextPath}/faq/faqWrite'">글쓰기</button>
			</div>
		</c:if>
		<c:if test="${count  == 0}">
			자주 묻는 질문(FAQ) 내역이 없습니다.
	</c:if>
		<c:if test="${count > 0}">
			<c:forEach var="f" items="${list}">
				<br>
				<div style=" padding-bottom:10px;">
					<a
						href="${pageContext.request.contextPath}/faq/faqDetail?faq_num=${f.faq_num}">
						<ul  id="faqList" style="margin: 0 auto; float: left; width: 70%">
							<li style="font-size: 16pt;">Q. ${f.faq_title}</li>
							<li class="fs-16 fw-5 text-black-3">${f.f_reg_date}
								&nbsp;&nbsp;👁 ${f.faq_hit} &nbsp;</li>
						</ul>
					</a>
					<div style="float: right; margin-right: 30px; padding-top: 30px;">
						<label class="container" style="transform: rotate(180deg);">
							<input checked="checked" type="checkbox"> <svg
								viewBox="0 0 512 512" height="1em" data-num="${f.faq_num}"
								xmlns="http://www.w3.org/2000/svg" class="chevron-down toggle">
								<path
									d="M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z"></path></svg>
						</label>
					</div>
					<br>
					<div class="items hide" id="${f.faq_num}"
						style="margin: 0 auto; width: 80%; text-align: left;">
						<br> <br> <br>
						<br>
						<h4>
							<img src="${pageContext.request.contextPath}/images/qna.png"
								width="45px;"> &nbsp; ${f.faq_title}
						</h4>
						<hr width="100%" size="1">
						<br> <img
							src="${pageContext.request.contextPath}/images/faqdoc.png"
							width="50px;">&nbsp; ${f.mem_id} 의사 <br>
						<br>${f.faq_content} <br><br><br>
						<img src="${pageContext.request.contextPath}/images/alertfaq.png" width="20px;"> 꼭 확인해주세요.<br> 
						 <span class=" fs-12 fw-4 text-black-3">본 답변은 의학적 판단이나 진료 행위로 해석
							될 수 없으며, 메디챗은 이로 인해 발생하는 어떠한 책임도 지지 않습니다.<br> 정확한 개인 증상 파악 및
							진단은 의료 기관 내방을 통해 진행하시기 바랍니다.<br> 고객님의 개인정보 보호를 위해 개인정보는 기재하지
							않습니다.<br> 서비스에 입력되는 데이터는 내부 정책에 따라 관리됩니다.
						</span><br>
						<br>
						<br>
					</div>
				</div>

				<br>
				<br>
				<div class="line"></div>
			</c:forEach>
			<br>
			<br>
			<div class="align-center float-clear">${page}</div>
		</c:if>
		<br> <br>
	</div>
</div>