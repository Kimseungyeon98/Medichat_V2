//hospital 시작
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


// hospital/search 시작
/*
const searchBack = document.getElementById('search_back');
searchBack.onclick = function(){
    location.href='/hospitals';
};

const filterItem = document.getElementsByClassName('filter-item');
const commonFilter = document.getElementById('commonFilter');
for(let i=1; i < filterItem.length; i++){
    filterItem[i].onclick = function(){
        // 클래스가 있는지 확인
        if (this.classList.contains('filter-item-selected')) {
            // 클래스가 있으면 제거
            this.classList.remove('filter-item-selected');
        } else {
            // 클래스가 없으면 추가
            this.classList.add('filter-item-selected');
        }

        var commonFilterNames = '';
        var filterItemSelected = document.getElementsByClassName('filter-item-selected');
        for(let j=1; j < filterItemSelected.length; j++){
            commonFilterNames += filterItemSelected[j].getAttribute('data-commonFilter')+',';
        }
        commonFilter.value = commonFilterNames;
        searchForm.submit();
    };
}

if(typeof '${commonFilter}' !== 'undefined' && '${commonFilter}' !== null && '${commonFilter}' !== ''){
    const commonFilterArray = '${commonFilter}'.split(',');
    for(let i=1; i<filterItem.length; i++){
        for(let j=0; j<commonFilterArray.length; j++){
            if(filterItem[i].getAttribute('data-commonFilter') == commonFilterArray[j]){
                filterItem[i].classList.add('filter-item-selected');
            }
        }
    }
}

const sortTypeItem = document.getElementsByClassName('sortType-item');
const sortType = document.getElementById('sortType');
for(let i=0; i<sortTypeItem.length; i++){
    sortTypeItem[i].onclick = function(){
        sortType.value = sortTypeItem[i].getAttribute('data-sortType');
        searchForm.submit();
    };
}
$(document).ready(function() {
    const hospitalListBox = $('#hospitalListBox');
    let pageNum = 2;
    const pageItemNum = 15;
    const maxItems = 120;
    const keyword = '${keyword}';
    const sortType = '${sortType}';
    const commonFilter = '${commonFilter}';
    const user_lat = '${user_lat}';
    const user_lon = '${user_lon}';

    let totalItemsLoaded = 0;
    let loading = false;

    function loadHospitals() {
        if (totalItemsLoaded >= maxItems || loading) {
            return;
        }
        loading = true;
        $.ajax({
            url: '/hospitals/search-json',
            type: 'GET',
            dataType:'json',
            data: {
                pageNum: pageNum,
                pageItemNum: pageItemNum,
                keyword: keyword,
                sortType: sortType,
                commonFilter: commonFilter,
                user_lat: user_lat,
                user_lon: user_lon
            },
            success: function(param) {
                if(param.length==0){
                    return;
                }
                pageNum++;
                let output = '';
                for(let i=0; i<param.length; i++){
                    output += '<div class="hospital-box" data-hosNum="'+param[i].hos_num+'">';
                    output += '<div class="d-flex align-itmes-center">';
                    output += '<div class="hospital-name fs-17 fw-8 text-black-6">'+param[i].hos_name+'</div>';
                    if(param[i].rev_avg != '0.0'){
                        output += '<div class="ms-3 d-flex align-items-center">';
                        ouput += '<img src="/images/star.png" width="15" height="15">';
                        output += '<span class="fs-14 fw-7 ms-1">'
                        output += param[i].rev_avg;
                        output += '</span>';
                    }
                    output += '</div>';
                    output += '<div class="hospital-around fs-11 fw-9 text-gray-7">'+param[i].around+'m</div>';
                    output += '<div class="hospital-open fs-13 fw-7 text-black-4 d-flex align-items-center">';
                    if(param[i].hos_time${day}S!='null' || param[i].hos_time${day}C!='null'){
                        if(param[i].hos_time${day}S<=${time} && ${time}<param[i].hos_time${day}C){
                            output += '<div class="greenCircle"></div>'+'진료중';
                        } else {
                            output += '<div class="redCircle"></div>'+'진료종료';
                        }
                        if(param[i].hos_time${day}S!='null' || param[i].hos_time${day}C!='null'){

                            if(param[i].hos_time${day}S<=${time} && ${time}<param[i].hos_time${day}C){
                                output += '&nbsp;<div class="vr"></div>&nbsp;';
                                output += param[i].hos_time${day}C.substr(0,2)+':'+param[i].hos_time${day}C.substr(2,4)+' 마감';
                            } else { //진료종료
                                if(${day}<7 && param[i].hos_time${day+1}S!='null'){
                                    output += '&nbsp;<div class="vr"></div>&nbsp;';
                                    output += '내일'+param[i].hos_time${day+1}S.substr(0,2)+':'+param[i].hos_time${day+1}S.substr(2,4)+' 오픈';
                                }else if(${day}==1 && param[i].hos_time1S!='null') {
                                    output += '&nbsp;<div class="vr"></div>&nbsp;';
                                    output += '내일'+param[i].hos_time1S.substr(0,2)+':'+param[i].hos_time1S.substr(2,4)+' 오픈';
                                }
                            }
                        }
                    }
                    output += '</div>';
                    output += '<div class="hospital-address fs-12 fw-7 text-black-3">'+param[i].hos_addr+'</div>';
                    output += '<div class="hospital-docCnt fs-11 fw-8 text-black-3">'+'전문의'+param[i].doc_cnt+'명'+'</div>';
                    output += '</div>';
                    output += '<div class="line"></div>';
                }
                hospitalListBox.append(output);
                totalItemsLoaded += param.length;
                loading = false;
                hosBox = document.getElementsByClassName('hospital-box');
                for(let i=0; i<hosBox.length; i++){
                    hosBox[i].onclick = function(){
                        let hosNum = hosBox[i].getAttribute('data-hosNum');
                        location.href = '/hospitals/search/detail/'+hosNum;
                    }
                }
            },
            error: function(){
                loading = false;
            }
        });
    }

    let hosBox = document.getElementsByClassName('hospital-box');
    function onScroll() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 10) {
            loadHospitals();
        }
    }
    for(let i=0; i<hosBox.length; i++){
        hosBox[i].onclick = function(){
            let hosNum = hosBox[i].getAttribute('data-hosNum');
            location.href = '/hospitals/search/detail/'+hosNum;
        }
    }
    $(window).on('scroll', onScroll);

});*/
