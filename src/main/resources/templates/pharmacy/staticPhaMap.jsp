<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="d-flex justify-content-center">
    <div id="staticMap" style="width:700px; height:500px;" class="rounded-4"></div>
</div>
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=${apiKey}&libraries=services"></script>
<script>
kakao.maps.load(function() {
    // 이미지 지도에서 마커가 표시될 위치입니다 
    var markerPosition  = new kakao.maps.LatLng(${pharmacy.pha_lat}, ${pharmacy.pha_lon}); 

    // 마커 이미지
    var imageSrc = '/images/marker.png' // 마커이미지의 주소입니다    

    var staticMapContainer  = document.getElementById('staticMap'); // 이미지 지도를 표시할 div  
    var staticMapOption = { 
        center: new kakao.maps.LatLng(${pharmacy.pha_lat}, ${pharmacy.pha_lon}), // 이미지 지도의 중심좌표
        level: 3, // 이미지 지도의 확대 레벨
        marker:  {
            position: markerPosition, // 마커 위치
            src: imageSrc, // 마커 이미지 URL
            size: new kakao.maps.Size(64, 69), // 마커 이미지 크기
            options: {offset: new kakao.maps.Point(27, 69)}, // 마커 이미지의 옵션
            text: '${pharmacy.pha_name}'
        } 
    };    

    // 이미지 지도를 생성합니다
    var staticMap = new kakao.maps.StaticMap(staticMapContainer, staticMapOption);
});
</script>