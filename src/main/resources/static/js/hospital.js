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
const locationForm = $('#locationForm');

if (locationForm && (filter.user_lat == null || filter.user_lon == null || (filter.user_lat === 37.4981646510326 && filter.user_lon === 127.028307900881))) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            function(position) {
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;
                if (locationForm) {
                    $('#lat').val(lat);
                    $('#lon').val(lon);
                    //alert("user_lat = " + userLat + ", user_lon = " + userLon);
                    locationForm.submit();
                }
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

const subject = $('.subject');
for (let i = 0; i < subject.length; i++) {
    subject[i].onclick = function() {
        location.href = '/hospitals/search?keyword=' + subject[i].getAttribute('data-keyword') + '&sortType=NEAR&user_lat=' + filter.user_lat + '&user_lon=' + filter.user_lon + '&around=' + filter.around;
    };
}

const subItem = $('.sub-item');
for (let i = 0; i < subItem.length; i++) {
    subItem[i].onclick = function() {
        let keyword = subItem[i].getAttribute('data-keyword');
        if (keyword === '마취통증학과') {
            keyword = '마취통증';
        }
        location.href = '/hospitals/search?keyword=' + keyword + '&sortType=NEAR&user_lat=' + filter.user_lat + '&user_lon=' + filter.user_lon + '&around=' + filter.around;
    };
}

const hsItem = $('.hs-item');
for (let i = 0; i < hsItem.length; i++) {
    hsItem[i].onclick = function() {
        location.href = '/hospitals/search?keyword=' + hsItem[i].getAttribute('data-keyword') + '&sortType=NEAR&user_lat=' + filter.user_lat + '&user_lon=' + filter.user_lon + '&around=' + filter.around;
    };
}

const hSearchIcon = $('#h-search-icon');
const searchForm = $('#search_form');
hSearchIcon.onclick = function() {
    searchForm.submit();
};

const hkwItem = $('.hkw-item');
for (let i = 0; i < hkwItem.length; i++) {
    hkwItem[i].onclick = function() {
        location.href = '/hospitals/search?keyword=' + hkwItem[i].getAttribute('data-keyword') + '&sortType=NEAR&user_lat=' + filter.user_lat + '&user_lon=' + filter.user_lon + '&around=' + filter.around;
    };
}


// hospital/search 시작
const searchBack = $('#search_back');
if(searchBack!=null){
    searchBack.onclick = function(){
        location.href='/hospitals';
    };
}

const filterItem = $('.filter-item');
const commonFilter = $('#commonFilter');
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
        var filterItemSelected = $('.filter-item-selected');
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

const sortTypeItem = $('.sortType-item');
const sortType = $('#sortType');
for(let i=0; i<sortTypeItem.length; i++){
    sortTypeItem[i].onclick = function(){
        sortType.value = sortTypeItem[i].getAttribute('data-sortType');
        searchForm.submit();
    };
}


$(document).ready(function() {
    const hospitalListBox = $('#hospitalListBox');
    const maxItems = 300;
    let currentPageNumber = 0; // 최초 0으로 시작

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
                user_lat: filter.user_lat,
                user_lon: filter.user_lon,
                keyword: filter.keyword,
                commonFilter: filter.commonFilter,
                sortType: filter.sortType,
                around: filter.around,
                day: filter.day,
                time: filter.time,
                page: currentPageNumber,
                size: pageable.size
            },
            success: function(param) {
                if(param.length===0){
                    loading = false;
                    return;
                }

                let output = '';

                for(let i=0; i<param.length; i++){
                    output += '<div class="hospital-box" data-hosNum="'+param[i].hosNum+'">';
                    output += '<div class="d-flex align-items-center">';
                    output += '<div class="hospital-name fs-17 fw-8 text-black-6">'+param[i].hosName+'</div>';
                    /*if(param[i].revAvg != '0.0'){
                        output += '<div class="ms-3 d-flex align-items-center">';
                        output += '<img src="/images/icon/star.png" width="15" height="15">';
                        output += '<span class="fs-14 fw-7 ms-1">'
                        output += param[i].revAvg;
                        output += '</span>';
                    }*/
                    output += '</div>';
                    /*output += '<div class="hospital-around fs-11 fw-9 text-gray-7">'+param[i].around+'m</div>';*/
                    output += '<div class="hospital-open fs-13 fw-7 text-black-4 d-flex align-items-center">';

                    const timeStart = param[i]['hosTime' + filter.day + 'S'];
                    const timeClose = param[i]['hosTime' + filter.day + 'C'];

                    if (timeStart !== 'null' || timeClose !== 'null') {
                        if (timeStart <= filter.time && filter.time < timeClose) {
                            output += '<div class="greenCircle"></div>' + '진료중';
                            output += '&nbsp;<div class="vr"></div>&nbsp;';
                            output += timeClose.substring(0, 2) + ':' + timeClose.substring(2, 4) + ' 마감';
                        } else {
                            output += '<div class="redCircle"></div>' + '진료종료';
                            const nextDay = (filter.day % 7) + 1; // day가 7이면 1로 순환
                            const nextStart = param[i]['hosTime' + nextDay + 'S'];

                            if (nextStart !== 'null') {
                                output += '&nbsp;<div class="vr"></div>&nbsp;';
                                output += '내일' + nextStart.substring(0, 2) + ':' + nextStart.substring(2, 4) + ' 오픈';
                            }
                        }
                    } else {
                        output += '<div class="redCircle"></div>' + '휴무';
                    }
                    output += '</div>';
                    output += '<div class="hospital-address fs-12 fw-7 text-black-3">'+param[i].hosAddr+'</div>';
                    /*output += '<div class="hospital-docCnt fs-11 fw-8 text-black-3">'+'전문의'+param[i].docCnt+'명'+'</div>';*/
                    output += '</div>';
                    output += '<div class="line"></div>';
                }
                hospitalListBox.append(output);
                totalItemsLoaded += param.length;
                currentPageNumber++;
                loading = false;
                hosBox = $('.hospital-box');
                for(let i=0; i<hosBox.length; i++){
                    hosBox[i].onclick = function(){
                        let hosNum = hosBox[i].getAttribute('data-hosNum');
                        location.href = '/hospitals/search/'+hosNum;
                    }
                }
            },
            error: function(){
                loading = false;
            }
        });
    }

    let hosBox = $('.hospital-box');
    function onScroll() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 10) {
            loadHospitals();
        }
    }
    for(let i=0; i<hosBox.length; i++){
        hosBox[i].onclick = function(){
            let hosNum = hosBox[i].getAttribute('data-hosNum');
            location.href = '/hospitals/search/'+hosNum;
        }
    }
    $(window).on('scroll', onScroll);
    loadHospitals();
});
