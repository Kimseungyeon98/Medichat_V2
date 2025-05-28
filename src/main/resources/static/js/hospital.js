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

// 위치 정보 가져오기
const locationForm = document.getElementById('locationForm');

if ((userLat == null || userLon == null) || (userLat == 37.4981646510326 && userLon == 127.028307900881)) {
    if (navigator.geolocation) {
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
for (let i = 0; i < subject.length; i++) {
    subject[i].onclick = function() {
        location.href = '/hospitals/search?keyword=' + subject[i].getAttribute('data-keyword') + '&sortType=NEAR&user_lat=' + userLat + '&user_lon=' + userLon;
    };
}

const subItem = document.getElementsByClassName('sub-item');
for (let i = 0; i < subItem.length; i++) {
    subItem[i].onclick = function() {
        let keyword = subItem[i].getAttribute('data-keyword');
        if (keyword === '마취통증학과') {
            keyword = '마취통증';
        }
        location.href = '/hospitals/search?keyword=' + keyword + '&sortType=NEAR&user_lat=' + userLat + '&user_lon=' + userLon;
    };
}

const hsItem = document.getElementsByClassName('hs-item');
for (let i = 0; i < hsItem.length; i++) {
    hsItem[i].onclick = function() {
        location.href = '/hospitals/search?keyword=' + hsItem[i].getAttribute('data-keyword') + '&sortType=NEAR&user_lat=' + userLat + '&user_lon=' + userLon;
    };
}

const hSearchIcon = document.getElementById('h-search-icon');
const searchForm = document.getElementById('search_form');
hSearchIcon.onclick = function() {
    searchForm.submit();
};

const hkwItem = document.getElementsByClassName('hkw-item');
for (let i = 0; i < hkwItem.length; i++) {
    hkwItem[i].onclick = function() {
        location.href = '/hospitals/search?keyword=' + hkwItem[i].getAttribute('data-keyword') + '&sortType=NEAR&user_lat=' + userLat + '&user_lon=' + userLon;
    };
}
