<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 커뮤니티 목록 시작 -->
<div class="cboard-main">	
	<p class="text-lightgray fw-7 fs-13" style="padding:16px;">홈 > 커뮤니티</p>
	<h4 style="padding:10px;">커뮤니티</h4>
	<div class="page-body">
		<div id="cboard_category bg-gray-1 px-2" style="padding:16px;">
			<a href="list" class="cboard-category text-gray-7">전체</a>
			<a href="list?cbo_type=1" class="cboard-category text-gray-7">질환고민</a>
			<a href="list?cbo_type=2" class="cboard-category text-gray-7">다이어트·헬스</a>
			<a href="list?cbo_type=3" class="cboard-category text-gray-7">피부고민</a>
			<a href="list?cbo_type=4" class="cboard-category text-gray-7">임신·성고민</a>
			<a href="list?cbo_type=5" class="cboard-category text-gray-7">탈모고민</a>
			<a href="list?cbo_type=6" class="cboard-category text-gray-7">마음건강</a>
			<a href="list?cbo_type=7" class="cboard-category text-gray-7">뼈와관절</a>
			<a href="list?cbo_type=8" class="cboard-category text-gray-7">영앙제</a>
			<a href="list?cbo_type=9" class="cboard-category text-gray-7">자유게시판</a>
		</div><br>
		<form action="list" method="get" id="search_form">
			<input type="hidden" name="cbo_type" value="${param.cbo_type}">
			<ul class="cboard-search align-center">
				<li>
					<select name="keyfield" id="keyfield" class="form-control drug-keyfield">
						<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>내용</option>
						<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>제목+내용</option>
					</select>
				</li>
				<li class="search-container">
					<input type="text" class="form-control" name="keyword" id="keyword" value="${param.keyword}">
					<i id="h-search-icon" class="bi bi-search search-icon" style="top:5px;"></i>
					<script type="text/javascript">
					$('#h-search-icon').click(function(){
						$('#search_form').submit();
					});
				</script>
				</li>
			</ul>
			<%-- <div class="align-right" style="padding:16px;">
				<select id="order" name="order">
					<option value="1" <c:if test="${param.order == 1}">selected</c:if>>최신순</option>
					<option value="2" <c:if test="${param.order == 1}">selected</c:if>>조회수순</option>
					<option value="3" <c:if test="${param.order == 1}">selected</c:if>>공감순</option>
					<option value="4" <c:if test="${param.order == 1}">selected</c:if>>댓글순</option>
				</select>
			</div> --%>
		</form><br>
		<c:if test="${count == 0}">
			<div class="text-black-5 text-center fs-17 fw-7">표시할 게시물이 없습니다</div>
		</c:if>
		<c:if test="${count > 0}">
			<c:forEach var="cboard" items="${list}">
				<div class="cboard-list hover-gray" onclick="location.href='detail?cbo_num=${cboard.cbo_num}'" style="cursor: pointer;">
					<span class="cboard-category-box">
						<c:if test="${cboard.cbo_type == 1}">질환고민</c:if>
						<c:if test="${cboard.cbo_type == 2}">다이어트·헬스</c:if>
						<c:if test="${cboard.cbo_type == 3}">피부고민</c:if>
						<c:if test="${cboard.cbo_type == 4}">임신·성고민</c:if>
						<c:if test="${cboard.cbo_type == 5}">탈모고민</c:if>
						<c:if test="${cboard.cbo_type == 6}">마음건강</c:if>
						<c:if test="${cboard.cbo_type == 7}">뼈와관절</c:if>
						<c:if test="${cboard.cbo_type == 8}">영앙제</c:if>
						<c:if test="${cboard.cbo_type == 9}">자유게시판</c:if>
					</span>
					<div class="cboard-title">${cboard.cbo_title}</div>
					<div class="cboard-content-wrapper">
				        <div class="cboard-content">${cboard.cbo_content}</div>
				        <div class="cboard-image">
				        </div>
				    </div>
					<script>
						window.onload = function(){
							 var content = document.querySelectorAll('.cboard-content');
							 
							 content.forEach(function(element){
								//HTML을 파싱하기 위해 임시 div를 생성합니다.
						        var tempDiv = document.createElement('div');
						        tempDiv.innerHTML = element.innerHTML;
						        
						     	//첫 번째 이미지 URL을 추출
						        var firstImage = tempDiv.querySelector('img');
						        var imageUrl = null;
						        if (firstImage) {
						            imageUrl = firstImage.src;
						        }
						        
						    	//모든 이미지 태그를 제거
					            var images = tempDiv.getElementsByTagName('img');//모든 이미지 태그 선택
					            while (images.length > 0) {//이미지가 있을 경우 제거
					                images[0].parentNode.removeChild(images[0]);
					            }
					            
					        	//텍스트를 추출 및 150자로 제한
					            var text = tempDiv.textContent || tempDiv.innerText;
					            if (text.length > 150) {
					                text = text.substring(0, 150) + '...';
					            }
					            element.innerText = text;
					            
					         //첫 번째 이미지를 image-cboard-image 컨테이너에 삽입
					            if (imageUrl) {
					                var imageContainer = element.parentNode.querySelector('.cboard-image');
					                if (imageContainer) {
					                    var imgElement = document.createElement('img');
					                    imgElement.src = imageUrl;
					                    imgElement.alt = 'Content Image';
					                    imageContainer.appendChild(imgElement);
					                }
					            }
					            
							 });
						};
					</script>
					<div class="cboard-info" id="cboard_list_info">
				        <img src="${pageContext.request.contextPath}/member/memViewProfile?mem_num=${cboard.mem_num}" width="40" height="40">
				        <div class="cboard-profile">
				            <span>${cboard.mem_id}</span>
				            <span>${cboard.cbo_rdate}</span>
				        </div>
				        <div class="cboard-likes-views">
				            <span>👁 ${cboard.cbo_hit}</span>&nbsp;
				            <span id="output_fcount">❤️ ${cboard.fav_cnt}</span>&nbsp;
				            <span>💬 ${cboard.re_cnt}</span>
				        </div>
				    </div>
				</div>
				<hr style="margin: 0; padding: 0;">
			</c:forEach>
			
			<div class="align-center">${page}</div>
		</c:if>
		<c:if test="${!empty user}">
			<div class="sticky-bottom-container"><input type="button" value="글쓰기" onclick="location.href='write'" class="cboard_write_btn"></div>
		</c:if>
	</div>
</div>
<!-- 커뮤니티 목록 끝 -->