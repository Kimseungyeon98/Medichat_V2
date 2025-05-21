<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<div class="page-main">
	<div class="page-one" style="padding-top:16px;">
		<span class="text-lightgray fw-7 fs-13">홈 > 건강 블로그 > 건강 비디오 > ${video.video_title}</span>	
		<h3 style="margin-top:16px;">&nbsp;&nbsp;&nbsp;<b>${video.video_title}</b></h3>
		<ul>
			<li><img
				src="${pageContext.request.contextPath}/member/memViewProfile?mem_num=${video.mem_num}"
				width="35" height="35" class="rounded-circle"> &nbsp;&nbsp;
				${video.mem_id}</li>
				<li>
					${video.v_reg_date}  &nbsp;👁  ${video.video_hit} 
				</li>
		</ul>
		<br>
		<div class="line"></div>
		<br><br>
				${video.video_content}

	
		
        &nbsp; &nbsp; &nbsp;<span id="hfav_cnt"></span><br>

<c:if test="${user.mem_num == video.mem_num}">
<div class="align-center">
	<input type="button" class="default-btn" value="글 수정" onclick="location.href='videoUpdate?video_num=${video.video_num}'">
	<input type="button" class="default-btn" value="글 삭제" id="v_delbtn" >
</div>
	<br>
	<script type="text/javascript">
		$(function(){
				$('#v_delbtn').click(function(){
					let choice  =confirm('삭제하시겠습니까?');
					if(choice){
						location.href='videoDelete?video_num=${video.video_num}';
					}
			});
		});
	</script>
</c:if>
		<div class="line"></div>
		</div>
		
		<br><br>
		<div class="align-center">
			<br><br><br>
			<input type="button" class="default-btn" onclick="location.href='${pageContext.request.contextPath}/hospitals'" style="width:50% !important; height:50px; font-size:15pt;" value="비대면 진료 받으러 가기">
		</div>
	</div>
