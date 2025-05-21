<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/kyj.css" type="text/css">
<style>
.align-center {/* 가운데 정렬 */
  text-align: center;
}
ul {
    list-style-type: none; /* 모든 ul 태그의 점 제거 */
}

ul li {
    list-style-type: none; /* 각각의 li 태그의 점 제거 */
     display: inline-block;
    margin-right: 10px; /* 각 항목 사이의 간격 조정 */
}
a {
    text-decoration: none;
    color: inherit; /* 기본 색상 상속 */
}	
</style>
<!-- 의약품 검색 시작 -->
<div class="page-main">
	<div class="page-one">
		<h5 class="text-lightgray fw-7 fs-13">홈 > 의약품 백과사전</h5>
		<p></p>
		<h1>
		<img src="/images/dictionary.png" width="45px;">
		<b>의약품 백과사전</b>
		</h1>
		<br>
		<!-- 의약품 검색 -->
		<div class="drug-search">
		<form action="search" id="search_form" method="get" class="align-center">
			<ul class="search">
				<li>
					<select name="keyfield" id="keyfield" class="form-control drug-keyfield">
						<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>제품명</option>
						<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>회사명</option>
						<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>효능</option>
					</select>
					<input type="text" name="keyword" id="keyword" class="form-control drug-keyword" value="${param.keyword}" placeholder="검색어를 입력하세요.">
					<i id="h-search-icon" class="bi bi-search" ></i>
					<script type="text/javascript">
					$('#h-search-icon').click(function(){
						$('#search_form').submit();
					});
				</script>
					<!-- <input type="submit" value="검색">-->
				</li>
				<%-- <li>
					<input type="search" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li> --%>
			</ul>
		</form>
		</div>
		<hr size="1" width="100%">
		<br>
		<c:if test="${count == 0}">
			<div class="result-display">표시할 게시물이 없습니다</div>
		</c:if>
		<c:if test="${count > 0}">
			<table class="drug-table">
				<tr class="table-light align-center bg-gray-1">
					<th class="fs-18 fw-4 drug-name-th">제품명</th>
					<th class="fs-18 fw-4 drug-company">회사명</th>
					<th class="fs-18 fw-4 drug-effect">효능</th>
				</tr>
				<c:forEach var="drug" items="${list}">
				<tr>
					<td class="fs-18 fw-4 drug-name-td"><a href="detail?drg_num=${drug.drg_num}" class="list-drug-name"> ${drug.drg_name}</a></td>
					<td class="align-center fs-18 fw-4 drug-company">${drug.drg_company}</td>
					<td class="align-center fs-18 fw-4 drug-effect"><img src="${pageContext.request.contextPath}/images/magnifier.png" width="20" class="drg-effect-icon" data-drg_num="${drug.drg_num}"></td>
				</tr>
				</c:forEach>
			</table><br>
		</c:if>
		<nav aria-label="Page navigation example">
			<div class="align-center">${page}</div>
		</nav>
	</div>

	<!-- 의약품 효능 상세보기 -->
	<div class="drug-effect-info">
		<div class="effect-info-header bg-green-6">
			<div class="drug-effect-header-content"></div>
			<div class="close" style="color:white;">&times;</div>
		</div>
		<div class="effect-info-body" style="background-color:#ebf5ec;">
			<div class="drug-effect-content"></div>
		</div>
	</div>
	<!-- 의약품 효능 상세보기 끝-->	
</div>
<!-- 의약품 검색 끝 -->

<script>
$(document).ready(function(){
    let drg_num = '';
    
    $('.drg-effect-icon').click(function(){
        drg_num = $(this).data('drg_num');
        
        let offset = $(this).offset();
        $('.drug-effect-info').css({
            top: offset.top+ $(this).outerWidth(),
            left: offset.left - 140
        });
        
        if ($('.drg-effect-info').is(':visible')) {
            $('.drg-effect-info').slideUp('fast', function() {
                // 정보창을 닫은 후 새로운 데이터를 가져와서 열기
                drugEffectToggle(drg_num);
            });
        } else {
            // 정보창이 닫혀 있는 경우 바로 새로운 데이터를 가져와서 열기
            drugEffectToggle(drg_num);
        }
    });
function drugEffectToggle(drg_num){
        $.ajax({
            url:'/drug/drugEffect',
            type:'get',
            data:{drg_num:drg_num},
            dataType:'JSON',
            success:function(param){
                // 이전 내용 초기화 
                $('.drug-effect-header-content').empty();
                $('.drug-effect-content').empty();
                
                // 새 내용 추가
                $('.drug-effect-header-content').append(param.drg_name);
                $('.drug-effect-content').text(param.drg_effect);
                
                // drug-effect-info 요소를 slideToggle로 표시
                $('.drug-effect-info').slideToggle('fast');
            },
            error:function(){
                alert('네트워크 오류 발생');
            }
        }); // end of ajax
}
    
    // 모달 닫기 기능
    $('.close').click(function(){
        $('.drug-effect-info').slideToggle('fast');
    });
	
}); //end of ready function
</script>