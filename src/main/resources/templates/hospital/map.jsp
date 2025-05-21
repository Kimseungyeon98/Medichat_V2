<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${apiKey}&libraries=services"></script>
<div class="d-flex justify-content-center">
	<div id="map" style="width:700px; height:500px;" class="rounded-4"></div>
</div>
<script>
kakao.maps.load(function(){
	/* 지도 기본 설정 */
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
			center: new kakao.maps.LatLng('${user_lat}','${user_lon}'), //중심 좌표 (필수)
			level: 2 //확대 수준 (기본값: 3)			
	};
	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
	// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
	var mapTypeControl = new kakao.maps.MapTypeControl();
	// 지도 타입 컨트롤을 지도에 표시합니다
	map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
	// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
	var zoomControl = new kakao.maps.ZoomControl();
	// 지도 타입 컨트롤을 지도에 표시합니다
	map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
	
	
	/* 마커 표시 로직 */
	//내 위치 마커
	var marker = new kakao.maps.Marker({
		map: map,
		position: new kakao.maps.LatLng('${user_lat}','${user_lon}')
	});
	
	//병원 위치 받아오기
	const positions = new Array();
	<c:forEach items="${hosList}" var="hos">
		positions.push({
			title:'${hos.hos_name}',
			latlng:new kakao.maps.LatLng('${hos.hos_lat}','${hos.hos_lon}'),
			hosNum: '${hos.hos_num}'
		});
	</c:forEach>
	
	//병원 위치 마커
	var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
	for (let i = 0; i < positions.length; i ++) {
	    var imageSize = new kakao.maps.Size(24, 35); 
	    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
	    var marker = new kakao.maps.Marker({
	        map: map,
	        position: positions[i].latlng,
	        title : positions[i].title,
	        image : markerImage
	    });
	    kakao.maps.event.addListener(marker, 'click', function(){
	    	location.href='/hospitals/search/detail/' + positions[i].hosNum;
	    });
	    // 병원 이름 인포윈도우
	    var iwContent = '<div class="ch rounded-5 fs-12 fw-7 text-black-5" style="width:150px; padding:5px;">' + positions[i].title + '</div>';
	    var infowindow = new kakao.maps.InfoWindow({
	    	position: positions[i].latlng,
	    	content: iwContent
	    });
	    infowindow.open(map,marker);
	}
	// 지도 중심 좌표를 강제로 다시 설정
	map.setCenter(new kakao.maps.LatLng('${user_lat}', '${user_lon}'));
});
</script>
