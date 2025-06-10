$(function(){
    if(filter==null){
        initLocation();
    }
});

function initLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            pos => {
                $.ajax({
                    url:'/initLocation',
                    method:'post',
                    data:{user_lat:pos.coords.latitude,
                        user_lon:pos.coords.longitude},
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