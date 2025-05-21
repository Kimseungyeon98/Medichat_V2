<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

<div class="sticky-top-container p-3">
	<a id="right-nav-img" href="#"><img src="/images/right_nav_img.png" width="290" height="420"></a>
	<div style="height: 50px;"></div>
	<div>
		<h5 class="fw-9 fs-18 my-4">실시간 상담 베스트</h5>
		<div id="right_nav_items">
			
		</div>
	</div>
</div>

<script>
	let output = '';
	$.ajax({
		url:'/getRightNavData',
		type:'GET',
		success:function(param){
			if(param.length==0){
				output+= '<div class="text-center fw-7 fs-15 text-black-5">아직 등록된 글이 없습니다.</div>';
			}
			
			for(let i=0; i<param.length; i++){
				output += '<div class="d-flex justify-content-start align-items-center mb-3 px-2"  style="height:24px;">';
				output += '<img src="/images/letter-q.png" width="24px" height="24px" class="me-2">';
				output += '<a class="text-gray-7 right-nav-q" href="/consultings/detail/'+param[i].con_num+'">'+param[i].con_title+'</a>';
				output += '</div>';
			}
			$('#right_nav_items').append(output);
		},
		error:function(){
			
		}
	});
</script>