$(function(){
    initLocation();

    setInterval(() => {
        initLocation();
    }, 20000); // 20초마다 반복 호출
});

function initLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            pos => {
                $.ajax({
                    url:'/initLocation',
                    method:'post',
                    data:{
                        userLat:pos.coords.latitude,
                        userLng:pos.coords.longitude
                    },
                    dataType:'json',
                    success: function(){
                        console.log('위치 설정 성공');
                    }
                });
            },
            err => console.error(`Error ${err.code}: ${err.message}`),
            { enableHighAccuracy: true }
        );
    }
}