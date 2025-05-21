<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="page-main">
<div class="page-one" style="padding-top:16px;">
		<span class="text-lightgray fw-7 fs-13">홈 > 건강 블로그 > 건강 비디오</span>	
		<h3 style="margin-top:16px;"><img src="${pageContext.request.contextPath}/images/marketing.png" width="45px;"> <b>건강 비디오</b></h3>
		<br>
		<form action="videoList" id="form-video" method="get" class="align-center">
			<div class="container-input"  style="width:500px; margin:0 auto;">
			<div class="d-flex justify-content-center align-items-center " >
			<select name="keyfield"  class="form-control"  id="selectinput">
				<option disabled="disabled"  <c:if test="${empty keyfield}">selected</c:if>>선택</option>
				<option value="1" <c:if test="${keyfield ==1}">selected</c:if>>제목</option>
				<option value="2"<c:if test="${keyfield ==2}">selected</c:if>>내용</option>
				<option value="3" <c:if test="${keyfield ==3}">selected</c:if>>제목 또는 내용</option>
			</select>
				 &nbsp;&nbsp;<input type="text" id="h-search" class="form-control" placeholder="검색어를 입력하세요." name="keyword" value="${keyword}">
				<i id="h-search-icon" class="bi bi-search" ></i>
				<script type="text/javascript">
					$('#h-search-icon').click(function(){
						$('#form-video').submit();
					});
				</script>
			</div>
			</div>
		</form>
		<hr size="1" width="100%">
		<c:if test="${!empty user && user.mem_auth > 2}">
			<div class="align-right">
				<button  class="default-btn" type="button"  onclick="location.href='${pageContext.request.contextPath}/video/videoWrite'">글쓰기</button>
			</div>
		</c:if>
	<c:if test="${count  == 0}">
			건강 비디오 내역이 없습니다.
	</c:if>
	<c:if test="${count > 0}">
		<c:forEach var="v" items="${list}">
				<div style="width:400px; float:left; margin:20px;'">
					<br><br>
					${fn:substring(v.video_content,0,fn:indexOf(v.video_content, '</figure>')+9)}
					<a href="${pageContext.request.contextPath}/video/videoDetail?video_num=${v.video_num}">
							<ul class="align-center">
								<li style="font-size:16pt;">${v.video_title}</li>
								<li class="fs-16 fw-5 text-black-3"> ${v.v_reg_date} &nbsp;👁 ${v.video_hit} &nbsp;   </li>
							</ul>
						</a>
			</div>	
		</c:forEach>
		<div class="align-center float-clear">
			${page}
		</div>
	</c:if>
	</div>
</div>