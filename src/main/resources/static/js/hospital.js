// 공통


// hospitals
$(document).ready(function() {
    if (window.location.pathname === '/hospitals') {
        // 더보기 버튼 & 오버레이
        $('#more').on('click', function () {
            $('#more_box, #overlay').show();
            $('body').css('overflow', 'hidden');
        });
        $('#overlay').on('click', function () {
            $('#more_box, #overlay').hide();
            $('body').css('overflow', 'auto');
        });

        // 위치 정보 자동 전송
        const locationForm = $('#locationForm');
        if (locationForm.length && (!filter.user_lat || !filter.user_lon || (filter.user_lat === 37.4981646510326 && filter.user_lon === 127.028307900881))) {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(
                    pos => {
                        $('#lat').val(pos.coords.latitude);
                        $('#lon').val(pos.coords.longitude);
                        locationForm.submit();
                    },
                    err => console.error(`Error ${err.code}: ${err.message}`),
                    { enableHighAccuracy: true }
                );
            }
        }

        // 공통 이동 함수
        function bindRedirect(selector) {
            $(selector).on('click', function () {
                let keyword = $(this).attr('data-keyword');
                if (keyword === '마취통증학과') keyword = '마취통증';
                const params = $.param({
                    keyword,
                    sortType: filter.sortType,
                    user_lat: filter.user_lat,
                    user_lon: filter.user_lon,
                    around: filter.around
                });
                location.href = `/hospitals/search?${params}`;
            });
        }
        bindRedirect('.subject');
        bindRedirect('.sub-item');
        bindRedirect('.hs-item');
        bindRedirect('.hkw-item');

        // 검색 아이콘
        $('#h-search-icon').on('click', () => $('#search_form').submit());
    }

    if (window.location.pathname === '/hospitals/search') {
        // 더보기 버튼 & 오버레이
        $('#moreSortType').on('click', function () {
            $('#more_box, #overlay').show();
            $('body').css('overflow', 'hidden');
        });
        $('#overlay').on('click', function () {
            $('#more_box, #overlay').hide();
            $('body').css('overflow', 'auto');
        });
        // 검색 뒤로가기
        $('#search_back').on('click', () => location.href = '/hospitals');

        // 필터 클릭 이벤트 처리
        $('.filter-item').each(function (i) {
            if (i === 0) return;
            $(this).on('click', function () {
                $(this).toggleClass('filter-item-selected');

                const commonFilterNames = $('.filter-item-selected').map((j, el) => $(el).attr('data-commonFilter')).get().slice(1).join(',');
                $('#commonFilter').val(commonFilterNames);
                $('#search_form').submit();
            });
        });

        // 페이지 로드 시 선택된 필터 유지
        const commonFilter = filter.commonFilter;
        if(commonFilter){
            const selectedFilters = commonFilter.split(',');
            $('.filter-item').each(function (i) {
                if (i === 0) return;
                if (selectedFilters.includes($(this).attr('data-commonFilter'))) {
                    $(this).addClass('filter-item-selected');
                }
            });
        }

        // 정렬 항목 클릭 시 처리
        $('.sortType-item').on('click', function () {
            $('#sortType').val($(this).attr('data-sortType'));
            $('#search_form').submit();
        });


        const hospitalListBox = $('#hospitalListBox');
        const maxItems = 300;
        let currentPageNumber = 0; // 최초 0으로 시작
        let totalItemsLoaded = 0;
        let loading = false;

        function loadHospitals() {
            if (loading || totalItemsLoaded >= maxItems) return;

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
                    size: pageable.size,
                },
                success: function(data) {
                    if(data.length===0){
                        loading = false;
                        return;
                    }

                    let output = '';
                    data.forEach(hos => {
                        output += `<div class="hospital-box" data-hosNum="${hos.hosNum}">
                                    <div class="d-flex align-items-center">
                                         <div class="hospital-name fs-17 fw-8 text-black-6">${hos.hosName}</div>
                                    </div>
                                    <div class="hospital-open fs-13 fw-7 text-black-4 d-flex align-items-center">`;

                        const timeStart = hos['hosTime' + filter.day + 'S'];
                        const timeClose = hos['hosTime' + filter.day + 'C'];

                        if (timeStart !== 'null' || timeClose !== 'null') {
                            if (timeStart <= filter.time && filter.time < timeClose) {
                                output += `<div class="greenCircle"></div>진료중&nbsp;<div class="vr"></div>&nbsp;${timeClose.slice(0, 2)}:${timeClose.slice(2, 4)} 마감`;
                            } else {
                                output += `<div class="redCircle"></div>진료종료`;
                                const nextDay = (filter.day % 7) + 1;
                                const nextStart = hos['hosTime' + nextDay + 'S'];
                                if (nextStart !== 'null') {
                                    output += `&nbsp;<div class="vr"></div>&nbsp;내일${nextStart.slice(0, 2)}:${nextStart.slice(2, 4)} 오픈`;
                                }
                            }
                        } else {
                            output += '<div class="redCircle"></div>휴무';
                        }
                        output += `</div>
                                   <div class="hospital-address fs-12 fw-7 text-black-3">${hos.hosAddr}</div>
                                   </div><div class="line"></div>`;
                    });

                    hospitalListBox.append(output);
                    totalItemsLoaded += data.length;
                    currentPageNumber++;
                    loading = false;

                    $('.hospital-box').off('click').on('click', function () {
                            location.href = '/hospitals/search/' + $(this).attr('data-hosNum');
                        });
                },
                error: function(){
                    loading = false;
                }
            });
        }

        // 스크롤 시 자동 로딩
        $(window).on('scroll', function () {
            if ($(window).scrollTop() + $(window).height() >= $(document).height() - 10) {
                loadHospitals();
            }
        });

        // 초기 병원 리스트 클릭 이벤트 연결
        $('.hospital-box').on('click', function () {
            location.href = '/hospitals/search/' + $(this).attr('data-hosNum');
        });

        //맨 처음 초기화용
        loadHospitals();
    }
});
