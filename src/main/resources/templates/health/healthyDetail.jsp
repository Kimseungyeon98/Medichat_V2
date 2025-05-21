<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/healthy.total.js"></script>
<div class="page-main">
	<div class="page-one" style="padding-top: 16px;">
		<span class="text-lightgray fw-7 fs-13">홈 > 건강 블로그 > 건강매거진 >
			${healthy.healthy_title}</span>
		<h3 style="margin-top: 16px;">
			&nbsp;&nbsp;&nbsp;<b>${healthy.healthy_title}</b>
		</h3>
		<ul>
			<li><img
				src="${pageContext.request.contextPath}/member/memViewProfile?mem_num=${healthy.mem_num}"
				width="35" height="35" class="rounded-circle"> &nbsp;&nbsp;
				${healthy.id}</li>
				<li>
					👁 ${healthy.healthy_hit}&nbsp;&nbsp;❤️ ${healthy.fav_cnt}&nbsp;&nbsp;💬 ${healthy.re_cnt}
				</li>
		</ul>
		
		<c:if test="${!empty healthy.h_filename}">
			<br>
			<br>
			<div class="align-center">
				<img width="480" height="480"
					src="${pageContext.request.contextPath}/upload/${healthy.h_filename}" />
			</div>
		</c:if>
		<br> <br>
		<div>
			<p>${healthy.healthy_content}</p>
			<br>
			<br> <img
				src="${pageContext.request.contextPath}/images/alertfaq.png"
				width="20px;"> 꼭 확인해주세요.<br> <span
				class=" fs-12 fw-4 text-black-3"> 메디챗는 특정 약품 추천 및 권유를 위해 콘텐츠를
				제작하지 않으며 메디챗회원의 건강한 생활을 돕는 것을 주 목적으로 합니다. <br>콘텐츠의 내용은 의학적 지식을
				자문 받아 활용했습니다.<br> 그 외 출처 : 디지털타임스, 메디컬투데이
			</span>
		</div>
		<br> <br> <br>
		<div title="Like" class="heart-container" id="hFav"
			data-num="${healthy.healthy_num}">
			<input id="Give-It-An-Id" class="checkbox" type="checkbox"
				<c:if test="${healthy.click_num == user_num}" >checked="checked"</c:if>>
			<div class="svg-container">
				<svg xmlns="http://www.w3.org/2000/svg" class="svg-outline"
					viewBox="0 0 24 24">
	                    <path
						d="M17.5,1.917a6.4,6.4,0,0,0-5.5,3.3,6.4,6.4,0,0,0-5.5-3.3A6.8,6.8,0,0,0,0,8.967c0,4.547,4.786,9.513,8.8,12.88a4.974,4.974,0,0,0,6.4,0C19.214,18.48,24,13.514,24,8.967A6.8,6.8,0,0,0,17.5,1.917Zm-3.585,18.4a2.973,2.973,0,0,1-3.83,0C4.947,16.006,2,11.87,2,8.967a4.8,4.8,0,0,1,4.5-5.05A4.8,4.8,0,0,1,11,8.967a1,1,0,0,0,2,0,4.8,4.8,0,0,1,4.5-5.05A4.8,4.8,0,0,1,22,8.967C22,11.87,19.053,16.006,13.915,20.313Z">
	                    </path>
	                </svg>
				<svg xmlns="http://www.w3.org/2000/svg" class="svg-filled"
					viewBox="0 0 24 24">
	                    <path
						d="M17.5,1.917a6.4,6.4,0,0,0-5.5,3.3,6.4,6.4,0,0,0-5.5-3.3A6.8,6.8,0,0,0,0,8.967c0,4.547,4.786,9.513,8.8,12.88a4.974,4.974,0,0,0,6.4,0C19.214,18.48,24,13.514,24,8.967A6.8,6.8,0,0,0,17.5,1.917Z">
	                    </path>
	                </svg>
				<svg xmlns="http://www.w3.org/2000/svg" height="100" width="100"
					class="svg-celebrate">
	                    <polygon points="10,10 20,20"></polygon>
	                    <polygon points="10,50 20,50"></polygon>
	                    <polygon points="20,80 30,70"></polygon>
	                    <polygon points="90,10 80,20"></polygon>
	                    <polygon points="90,50 80,50"></polygon>
	                    <polygon points="80,80 70,70"></polygon>
	                </svg>
			</div>
		</div>
		&nbsp; &nbsp; &nbsp;<span id="hfav_cnt">${healthy.fav_cnt}</span><br>
		<c:if test="${user.mem_num == healthy.mem_num}">
			<div class="align-center">
				<input type="button" class="default-btn" value="글 수정"
					onclick="location.href='healthUpdate?healthy_num=${healthy.healthy_num}'">
				<input type="button" class="default-btn" value="글 삭제" id="h_delbtn">
			</div>
			<br>
			<script type="text/javascript">
				$(function() {
					$('#h_delbtn')
							.click(
									function() {
										let choice = confirm('삭제하시겠습니까?');
										if (choice) {
											location.href = 'healthDelete?healthy_num=${healthy.healthy_num}';
										}
									})
				})
			</script>
		</c:if>
		<div class="align-center">
			<c:if test="${empty user}">
				<form>
					<input type="hidden" name="healthy_num"
						value="${healthy.healthy_num}"> <input type="hidden"
						name="hre_renum" value="0"> <input type="hidden"
						name="hre_level" value="0">
					<textarea rows="5" cols="55" name="hre_content"
						style="resize: none;" disabled="disabled">로그인 후 작성가능합니다.</textarea>
				</form>
				<br>
			</c:if>
			<c:if test="${!empty user}">
				<form id="hreWrite">
					<input type="hidden" id="healthy_num" name="healthy_num"
						value="${healthy.healthy_num}"> <input type="hidden"
						name="hre_renum" value="0"> <input type="hidden"
						name="hre_level" value="0">
					<textarea rows="5" cols="75" name="hre_content" id="hre_content"
						style="resize: none; border-radius: 5px;"
						placeholder="300자까지 입력가능"></textarea>
					<br>
					<div class="align-right">
						<input type="submit" class="default-btn" value="댓글쓰기">
					</div>
				</form>
			</c:if>
			<div class="line"></div>
			<br>
		</div>
		<input type="hidden" id="user_num" value="${user.mem_num}">
		<div id="replyList" data-num="${healthy.healthy_num}"></div>
		<div class="align-center">
			<img src="../images/replymore.png" width="30px" id="replypaging"
				class="paging-button" style="display: none;"> <img
				src="../images/reply1page.png" width="30px" id="reply1page"
				style="display: none;">
		</div>
		<p>
		<p>
		<div class="align-center">
			<br> <br> <br> <br>
		</div>
	</div>
</div>