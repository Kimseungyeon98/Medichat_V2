<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	$(function() {
		let pageNum = ${vpageNum};
		let count
		$('#rightVideo').click(
				function() {
					location.href = 'healthBlog?vpageNum=' + (pageNum + 1);

				})
		$('#leftVideo').click(
				function() {
					location.href = 'healthBlog?vpageNum=' + (pageNum - 1);

				})

	})
</script>
<div class="page-main">
	<div class="page-one" style="padding-top:16px;">
		<span class="text-lightgray fw-7 fs-13">홈 > 건강 블로그</span>
		<h3 style="margin-top:16px;">
			<img src="${pageContext.request.contextPath}/images/blogging.png" width="45px;"> <b>건강 블로그</b>
		</h3>
		<hr size="1" width="100%">
		<br>
		<div class="page-one" id="healthyVideo" style="margin: auto;">
			<c:if test="${!empty user && user.mem_auth > 2}">
				<div class="align-right hide">
					<button class="default-btn" type="button"
						onclick="location.href='${pageContext.request.contextPath}/video/videoWrite'">글쓰기</button>
				</div>
			</c:if>
			<h3>
			<img src="${pageContext.request.contextPath}/images/marketing.png" width="35px;"> <b>건강 비디오</b>
		   </h3>
			<c:if test="${vcount  == 0}">
				건강 비디오 내역이 없습니다.
			</c:if>
			<c:if test="${vcount > 0}">
				<c:if test="${vpageNum != 1}">
					<div style="float: left; width: 40px;" id="leftVideo">
						<label class="container"
							style="transform: rotate(180deg); margin: 100px 0;"> <input
							checked="checked" type="checkbox"> <svg
								viewBox="0 0 320 512" height="1em"
								xmlns="http://www.w3.org/2000/svg" class="chevron-right">
								<path
									d="M310.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-192 192c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L242.7 256 73.4 86.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l192 192z"></path></svg>
						</label>
					</div>
				</c:if>
				<c:forEach var="v" items="${vlist}">
					<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
					<div
						style="float:left; width:253px; margin:0 10px; <c:if test="${vpageNum == 1 && !(maxPage<=vpageNum)}">margin-left:20px;</c:if> <c:if test="${vpageNum == 1 && maxPage<=vpageNum}">margin-left:30px;</c:if>">
						<br> <br>
						${fn:substring(v.video_content,0,fn:indexOf(v.video_content, '</figure>')+9)}
						<a
							href="${pageContext.request.contextPath}/video/videoDetail?video_num=${v.video_num}">
							<ul class="align-center">
								<li style="font-size: 16pt;">${fn:substring(v.video_title,0,10)}<c:if
										test="${fn:length(v.video_title) >10}">...</c:if></li>
								<li class="fs-15 fw-5 text-black-3" >${v.v_reg_date} &nbsp;👁 ${v.video_hit} &nbsp;</li>
							</ul>
						</a>
					</div>
				</c:forEach>
				<c:if test="${vpageNum<maxPage}">
					<div style="width: 40px; float: left;" id="rightVideo">
						<label class="container" style="margin: 100px 0;"> <input
							checked="checked" type="checkbox"> <svg
								viewBox="0 0 320 512" height="1em"
								xmlns="http://www.w3.org/2000/svg" class="chevron-right">
								<path
									d="M310.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-192 192c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L242.7 256 73.4 86.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l192 192z"></path></svg>
						</label>
					</div>
				</c:if>
			</c:if>
			<div style="clear: both;"></div>
			<div class="align-right float-clear">
				<a href="${pageContext.request.contextPath}/video/videoList" style=" text-decoration: underline;">건강비디오 목록 보러가기>></a>
			</div>

		</div>
		<br><br>
		<h3>
			<img src="${pageContext.request.contextPath}/images/magazine.png" width="35px;"> <b>건강 매거진</b>
		</h3>
		<br><br>
		<c:if test="${count  == 0}">
			건강 블로그 내역이 없습니다.
		</c:if>
		<c:if test="${count > 0}">

			<c:forEach var="h" items="${list}">
			<a href="${pageContext.request.contextPath}/health/healthDetail?healthy_num=${h.healthy_num}" >
				<div class="healthy_list" style="width:400px; float:left; margin:25px; padding-bottom:20px; height:200px;">
					<div class="float-left">
							<ul style="height:200px">
								<li style="font-size: 16pt;">${h.healthy_title}</li>
								<li>${fn:substring(fn:substring(h.healthy_content,0,fn:indexOf(h.healthy_content, '.')+1),0,52)}<br><span class="fs-15 fw-5 text-black-3">${h.h_reg_date}</span><br></li>
								<li class="align-right fs-14 fw-5 text-black-3" style="padding-right:18px;">👁 ${h.healthy_hit}&nbsp;&nbsp;❤️ ${h.fav_cnt}&nbsp;&nbsp;💬 ${h.re_cnt}
								</li>
								<li><div class="line"></div></li>
							</ul>
					</div>
					<div class="healthy_image">
						<c:if test="${!empty h.h_filename}">
							<img width="120" style="border-radius: 10px;" height="120"
								src="${pageContext.request.contextPath}/upload/${h.h_filename}" />
						</c:if>
					</div>
				</div>
				</a>
			</c:forEach>
			<br><br>
		</c:if>
		<br>
		<c:if test="${!empty user && user.mem_auth > 2}">
			<div class="align-right">
				<button class="default-btn  hide" type="button"
					onclick="location.href='${pageContext.request.contextPath}/health/healWrite'">글쓰기</button>
			</div>
		</c:if>
		<br>
		<br>
		<div class="align-right float-clear">
			<a href="${pageContext.request.contextPath}/health/healthM" style=" text-decoration: underline;">건강매거진 목록 보러가기>></a>
		</div>
	</div>
</div>