$(function(){
    initLocation();

    setInterval(() => {
        if(search.location===null){
            initLocation();
        }
    }, 20000); // 20초마다 반복 호출 -> 위치정보 없다면 재요청
});

function initLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            pos => {
                $.ajax({
                    url:'/initLocation',
                    method:'post',
                    data:{
                        userLat : pos.coords.latitude,
                        userLng : pos.coords.longitude
                    },
                    dataType:'json'
                });
            },
            err => console.error(`Error ${err.code}: ${err.message}`),
            { enableHighAccuracy: true }
        );
    }
}