$(document).ready(function() {
    // 공통
    const pathParts = window.location.pathname.split('/');

    // hospitals
    if (
        pathParts.length === 2 &&
        pathParts[1] === 'hospitals') {
        // 더보기 버튼 & 오버레이
        $('#more').on('click', function () {
            $('#more_box, #overlay').show();
            $('body').css('overflow', 'hidden');
        });
        $('#overlay').on('click', function () {
            $('#more_box, #overlay').hide();
            $('body').css('overflow', 'auto');
        });

        // 공통 이동 함수
        function bindRedirect(selector) {
            $(selector).on('click', function () {
                let keyword = $(this).attr('data-keyword');
                if (keyword === '마취통증학과') keyword = '마취통증';
                const params = $.param({
                    keyword,
                    sortType: filter.sortType,
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

    if (
        pathParts.length === 3 &&
        pathParts[1] === 'hospitals' &&
        pathParts[2] === 'search') {
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
                                    <div class="hospital-around fs-11 fw-9 text-gray-7">${hos.around}m</div>
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





    if (
        pathParts.length === 4 &&
        pathParts[1] === 'hospitals' &&
        pathParts[2] === 'search' &&
        !isNaN(pathParts[3])
    ) {
        $('#call_btn').click(function() {
            // 복사할 텍스트 지정
            var textToCopy = '${hospital.hos_tell1}';

            // 임시 텍스트 영역 생성
            var tempInput = $('<input>');
            $('body').append(tempInput);
            tempInput.val(textToCopy).select();

            // 클립보드에 텍스트 복사
            document.execCommand('copy');

            // 임시 텍스트 영역 제거
            tempInput.remove();

            // 알림 메시지
            alert('전화번호가 클립보드에 복사되었습니다: ' + textToCopy);
        });

        /*$('.hosRev_more_content_icon').on('click',function(event){
            const content = $(event.target).closest('.d-flex').find('.detail_hosRev_content');
            content.toggleClass('overflowText','heightAuto');
        })*/

        /*$('#reservation_btn').click(function(event){
            $.ajax({
                url: '/reservation/reservation',
                method: 'get',
                data: {hos_num: '${hospital.hos_num}'},
                dataType: 'json',
                success: function(param) {
                    if(param.result == 'success'){
                        $('#reservation_page').show();
                        initializeCalendar('${hospital.hos_num}');
                    } else if(param.result == 'logout'){
                        alert('로그인 후 이용해주세요');
                        location.href = "${pageContext.request.contextPath}/member/login";
                    } else if(param.result == 'doctor') {
                        alert('의사회원은 이용할 수 없습니다.');
                    } else if(param.result == 'suspended') {
                        alert('정지회원입니다. 일반회원의 경우에만 이용할 수 있습니다.');
                    } else if(param.result == 'unauthorized') {
                        alert('해당 서비스는 이용할 수 없습니다.');
                    } else {
                        alert('예약 신청 페이지 오류 발생');
                    }
                },
                error: function() {
                    alert('네트워크 오류 발생');
                }
            });
        }); //end of click event*/


        /*let doctor_history_content = '';
        $.ajax({
            url:'/doctor/doctorHistory',
            method:'get',
            data:{hos_num:'${hospital.hos_num}'},
            dataType:'json',
            success: function(param) {
                if(param.doctor == 'empty'){
                    doctor_history_content += ''; //근무 의사가 없는 경우 공간 만들지 않기
                }else{
                    doctor_history_content += '<div class="line"></div><div style="height:15px;" class="bg-gray-0"></div>';
                    doctor_history_content += '<p class="fs-18 fw-7" style="padding:0 20px;">의사 소개</p>';

                    param.doctor.forEach(function(doctor) {
                        doctor_history_content += '<div style="padding:20px 30px; display:flex; align-items:center;" >'
                        doctor_history_content += '<div><img src="/doctor/docViewProfile?mem_num=' + doctor.doc_num + '" alt="' + doctor.mem_name + '" class="doctor-image" style="width: 80px; height: 80px; margin-right:10px;"></div>';
                        doctor_history_content += '<div><span class="fs-15 fw-7">' + doctor.mem_name + ' 의사</span>';
                        if (doctor.doc_history) {
                            doctor_history_content += '<br><span class="fs-14">' + doctor.doc_history + '</span>';
                        }
                        doctor_history_content += '</div>';
                        doctor_history_content += '</div>';
                    });
                }
                $('#doctor_history').append(doctor_history_content);
            },
            error: function(){
                alert('의사 연혁 출력 오류');
            }
        });*/
    }
});
