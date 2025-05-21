<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- image: Flaticon.com -->
<!-- 카테고리 시작 -->
<div>
	<div class="p-3">
		<p class="text-lightgray fw-7 fs-13">홈 > 병원 찾기</p>
		<h4 class="text-black fw-6 fs-21 mb-4">병원 찾기</h4>
		<p class="text-lightgray fw-6 fs-13 mb-1">병원 예약하고 편하게 방문해보세요</p>
		<h4 class="text-black-6 fw-7 fs-23">어떤 병원을 찾으세요?</h4>
	</div>
	<div class="row justify-content-between px-3">
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="피부과">
				<p class="sub-icon"><img src="/images/subIcon/skin.png" width="30" height="30"></p>
				<p class="sub-name">피부과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="산부인과">
				<p class="sub-icon"><img src="/images/subIcon/gynecology.png" width="30" height="30"></p>
				<p class="sub-name">산부인과</p>
			</div>                            
		</div>                                
		<div class="col-2">                   
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="이비인후과">
				<p class="sub-icon"><img src="/images/subIcon/ear.png" width="30" height="30"></p> 
				<p class="sub-name">이비인후과</p>
			</div>                            
		</div>                                
		<div class="col-2">                   
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="내과">
				<p class="sub-icon"><img src="/images/subIcon/physician.png" width="30" height="30"></p> 
				<p class="sub-name">내과</p>   
			</div>                             
		</div>                                 
		<div class="col-2">                    
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="안과">
				<p class="sub-icon"><img src="/images/subIcon/eye.png" width="30" height="30"></p> 
				<p class="sub-name">안과</p>   
			</div>                             
		</div>                                 
		<div class="col-2">                    
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="가정의학과">
				<p class="sub-icon"><img src="/images/subIcon/home.png" width="30" height="30"></p>
				<p class="sub-name">가정의학과</p>
			</div>
		</div>
	</div>
	<div class="row justify-content-between px-3">
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="소아과">
				<p class="sub-icon"><img src="/images/subIcon/child.png" width="30" height="30"></p>
				<p class="sub-name">소아과</p>
			</div>                            
		</div>                                
		<div class="col-2">                   
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="정형외과">
				<p class="sub-icon"><img src="/images/subIcon/bone.png" width="30" height="30"></p>
				<p class="sub-name">정형외과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="정신건강의학과">
				<p class="sub-icon"><img src="/images/subIcon/mental.png" width="30" height="30"></p>
				<p class="sub-name">정신건강의학과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="비뇨기과">
				<p class="sub-icon"><img src="/images/subIcon/urology.png" width="30" height="30"></p>
				<p class="sub-name">비뇨기과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="치과">
				<p class="sub-icon"><img src="/images/subIcon/tooth.png" width="30" height="30"></p>
				<p class="sub-name">치과</p>
			</div>
		</div>
		<div class="col-2">
			<div class="subject rounded-2 bg-gray-0 text-center" data-keyword="신경외과">
				<p class="sub-icon"><img src="/images/subIcon/headache.png" width="30" height="30"></p>
				<p class="sub-name">신경외과</p>
			</div>
		</div>
	</div>
	<!-- 더 보기 시작 -->
	<div class="line mt-3"></div>
	<div id="more">
		<p class="text-lightgray fw-7">더 보기</p>
	</div>
	<div class="line"></div>
	
	<div class="overlay" id="overlay"></div>
	<div id="more_box" class="p-5 rounded-top-3 border bg-white" style="display: none;">
		<h5 class="fw-8">진료과목 선택</h5>
		<div>
			<c:forEach items="${subList}" var="sub">
				<c:forEach items="${sub}" var="item" varStatus="loop">
					<c:if test="${loop.index%3==0}">
						<c:set var="sub_name" value="${item}"/>
					</c:if>
					<c:if test="${loop.index%3==1}">
						<c:set var="sub_content" value="${item}"/>
					</c:if>
					<c:if test="${loop.index%3==2}">
						<c:set var="sub_icon" value="${item}"/>
					</c:if>
				</c:forEach>
				<div class="sub-item d-flex justify-content-between rounded-3" data-keyword="${sub_name}">
					<div class="py-3">
						<h5 class="text-black-6 fw-8 fs-17">${sub_name}</h5>
						<h5 class="text-gray-6 fw-7 fs-14">${sub_content}</h5>
					</div>
					<div class="sub-img">
						<img width="50" height="50" src="/images/subIcon/${sub_icon}.png">
					</div>
				</div>
				<div class="mbline"></div>
			</c:forEach>
		</div>
	</div>
	<!-- 더 보기 끝 -->
</div>
<!-- 카테고리 끝 -->
<div style="height:17px;" class="bg-gray-0"></div>
<!-- 어떻게 아프신가요 시작 -->
<div class="p-4">
	<h4 class="text-black-7 fw-6 mb-4" style="font-size: 20px;">어떻게 아프신가요?</h4>
	<div class="row justify-content-between">
		<c:forEach items="${howSick}" var="hs">
			<div class="col-2">
				<div class="hs-item rounded-2 bg-green-6" data-keyword="${hs}">
					<p class="text-center">${hs}</p>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<!-- 어떻게 아프신가요 끝 -->

<div class="line"></div>
<div style="height:14px;" class="bg-gray-0"></div>

<!-- 검색창 + 인기 검색어 시작 -->
<div class="p-5">
	<form id="search_form" class="d-flex" method="get" action="/hospitals/search">
		<input type="text" id="h-search" name="keyword" class="form-control" placeholder="병원 이름, 지역 + 과목, 증상">
		<img id="h-search-icon" src="/images/search.png" width="30" height="30">
	</form>
	<p class="text-lightgray fw-7 mt-3">인기 검색어</p>
	<div class="d-inline">
		<c:forEach items="${hotKeyWord}" var="hkw">
			<div class="hkw-item border text-center rounded-5 bg-gray-1 px-2" data-keyword="${hkw}">
				<p class="hkw fs-14 fw-7 text-gray-7">#${hkw}</p>
			</div>
		</c:forEach>
	</div>
</div>

<div class="line"></div>
<div style="height:20px;" class="bg-gray-0"></div>

<div>
	<!-- 지도 시작 -->
	<div id="mapDiv">
		<h4 class="fw-7 my-4 fs-22">내 주변 병원</h4>
		<jsp:include page="/WEB-INF/views/hospital/map.jsp"/>
	</div>
	<!-- 지도 끝 -->
</div>
<!-- 검색창 + 인기 검색어 끝 -->

<form id="locationForm" action="/hospitals" method="get">
	<input type="hidden" id="lat" value="${user_lat}" name="user_lat">
	<input type="hidden" id="lon" value="${user_lon}" name="user_lon">
</form>
<script>
	$('#more').click(function() {
	    $('#more_box').show();
	    $('#overlay').show();
	    $('body').css("overflow","hidden");
	});
	
	$('#overlay').click(function() {
		$('#more_box').hide();
		$('#overlay').hide();
		$('body').css("overflow","auto");
	});
	//위치 정보 가져오기
	const locationForm = document.getElementById('locationForm');
	if((${empty user_lat} && ${empty user_lon}) || ('${user_lat}'=='37.4981646510326' && '${user_lon}'=='127.028307900881')){
		if(navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(
				function(position) {
					const lat = position.coords.latitude;
				    const lon = position.coords.longitude;
				    document.getElementById('lat').value = lat;
				    document.getElementById('lon').value = lon;
				    locationForm.submit();
				},
				function(error) {
					console.error("Error Code = " + error.code + " - " + error.message);
				},
				{
					enableHighAccuracy: true
				}
			);
		}
	}
	const subject = document.getElementsByClassName('subject');
	for(let i=0; i<subject.length; i++){
		subject[i].onclick = function(){
			location.href = '/hospitals/search?keyword='+subject[i].getAttribute('data-keyword')+'&sortType=NEAR&user_lat=${user_lat}&user_lon=${user_lon}';
		};
	}
	const subItem = document.getElementsByClassName('sub-item');
	for(let i=0; i<subItem.length; i++){
		subItem[i].onclick = function(){
			if(subItem[i].getAttribute('data-keyword')=='마취통증학과'){
				subItem[i].setAttribute('data-keyword','마취통증');
			}
			location.href = '/hospitals/search?keyword=' + subItem[i].getAttribute('data-keyword')+'&sortType=NEAR&user_lat=${user_lat}&user_lon=${user_lon}';
		};
	}
	const hsItem = document.getElementsByClassName('hs-item');
	for(let i=0; i<hsItem.length; i++){
		hsItem[i].onclick = function(){
			location.href = '/hospitals/search?keyword='+hsItem[i].getAttribute('data-keyword')+'&sortType=NEAR&user_lat=${user_lat}&user_lon=${user_lon}';
		};
	}
	const hSearchIcon = document.getElementById('h-search-icon');
	const searchForm = document.getElementById('search_form');
	hSearchIcon.onclick = function(){
		searchForm.submit();
	};
	const hkwItem = document.getElementsByClassName('hkw-item');
	for(let i=0; i<hkwItem.length; i++){
		hkwItem[i].onclick = function(){
			location.href = '/hospitals/search?keyword='+hkwItem[i].getAttribute('data-keyword')+'&sortType=NEAR&user_lat=${user_lat}&user_lon=${user_lon}';
		};
	}
</script>