<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<div>
	<div class="p-3">
		<p class="text-lightgray fw-7 fs-13">홈 > 의료 상담</p>
		<form id="typeForm" action="/consultings" method="get">
			<select id="con_type" name="con_type" class="form-control">
				<option <c:if test="${con_type==0}">selected</c:if> value="0">구분없음</option>
				<option <c:if test="${con_type==1}">selected</c:if> value="1">만성질환</option>
				<option <c:if test="${con_type==2}">selected</c:if> value="2">여성질환</option>
				<option <c:if test="${con_type==3}">selected</c:if> value="3">소화기질환</option>
				<option <c:if test="${con_type==4}">selected</c:if> value="4">영양제</option>
				<option <c:if test="${con_type==5}">selected</c:if> value="5">정신건강</option>
				<option <c:if test="${con_type==6}">selected</c:if> value="6">처방약</option>
				<option <c:if test="${con_type==7}">selected</c:if> value="7">탈모</option>
				<option <c:if test="${con_type==8}">selected</c:if> value="8">통증</option>
				<option <c:if test="${con_type==9}">selected</c:if> value="9">여드름,피부염</option>
				<option <c:if test="${con_type==10}">selected</c:if> value="10">임신,성고민</option>
			</select>
		</form>
		<script>
			$('select').change(function(){
				$('#typeForm').submit();
			})
		</script>
	</div>
	<div id="consulting-div">
		<c:if test="${empty consultingList}">
			<div class="text-black-5 text-center fs-17 fw-7">등록된 의료 상담 글이 없습니다.</div>
		</c:if>
		<c:forEach items="${consultingList}" var="consulting">
			<div class="consulting-item-div" data-conNum="${consulting.con_num}">
				<div class="consulting-item-title">${consulting.con_title}</div>
				<div class="consulting-item-content">${consulting.con_content}</div>
				<div class="consulting-item-type"><span>${consulting.con_type_name}</span></div>
				<div class="consulting-item-reCnt">
					<div>
						<div><img src="/images/doctor.png" width="23px" height="23px"></div>
						<div>${consulting.con_re_cnt}개의 답변</div>
					</div>
					<div>
						<div>${consulting.con_rDate}</div>
					</div>
				</div>
			</div>
			<div class="line"></div>
		</c:forEach>
	</div>
	<div class="sticky-bottom-container">
		<button id="consulting-btn" onclick="location.href='/consultings/create'">질문하기</button>
	</div>
</div>


<script>
	const consultingItems = document.getElementsByClassName('consulting-item-div');
	for(let i=0; i<consultingItems.length; i++){
		consultingItems[i].onclick = function(){
			location.href="/consultings/detail/"+consultingItems[i].getAttribute('data-conNum');
		}
	}
	
	
$(document).ready(function() {
    const consultingDiv = $('#consulting-div');
    let pageNum = 2;
    const pageItemNum = 5;
    const maxItems = 40;
    const con_type = '${con_type}';
    
    let totalItemsLoaded = 0;
    let loading = false;
	function loadConsultings(){
		if(totalItemsLoaded >= maxItems || loading){
			return;
		}
		loading = true;
		$.ajax({
			url: '/consultings-ajax',
			type: 'GET',
			data: {
					pageNum:pageNum,
					pageItemNum:pageItemNum,
					con_type:con_type
			},
			dataType:'json',
			success: function(param){
				if(param.length==0){
                	return;
                }
				pageNum++;
                let output = '';
                for(let i=0; i<param.length; i++){
                	output += '<div class="consulting-item-div" data-conNum="'+param[i].con_num+'">';
    				output += '<div class="consulting-item-title">'+param[i].con_title+'</div>';
    				output += '<div class="consulting-item-content">'+param[i].con_content+'</div>';
    				output += '<div class="consulting-item-type"><span>'+param[i].con_type_name+'</span></div>';
    				output += '<div class="consulting-item-reCnt">';
    				output += '<div><div><img src="/images/doctor.png" width="23px" height="23px"></div>'
    				output += '<div>'+param[i].con_re_cnt+'개의 답변</div>';
    				output += '</div>';
    				output += '<div><div>'+param[i].con_rDate+'</div></div>'
    				output += '</div>';
    				output += '</div>';
    				output += '<div class="line"></div>';
                }
                consultingDiv.append(output);
                totalItemsLoaded += param.length;
                loading = false;
                $('.consulting-item-div').click(function(){
                	location.href = "/consultings/detail/"+$(this).attr('data-conNum');
                });
			},
			error: function(){
				loading = false;
				console.log('ajax 에러~');
			}
		});
	}
	function onScroll() {
	    if ($(window).scrollTop() + $(window).height() >= $(document).height() - 10) {
	    	loadConsultings();
	    }
	}
	$(window).on('scroll', onScroll);
});
	
</script>