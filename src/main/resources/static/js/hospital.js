$(function(){
    // 공통
    const pathParts = window.location.pathname.split('/');
    const $body = $('body');
    const $overlay = $('#overlay');

    // 빈 hidden input 제거 후 submit
    function submitWithoutEmpty(formSelector) {
        const $form = $(formSelector);

        $form.find('input[type="hidden"]').filter(function () {
            return !$(this).val().trim();
        }).remove();
        $form.submit();
    }

    // 공통: 더보기 & 오버레이 show/hide 처리
    function bindMoreOverlay(showSelector, hideSelector, overlaySelector = '#overlay') {
        $(showSelector).on('click', () => {
            $(hideSelector + ', ' + overlaySelector).show();
            $body.css('overflow', 'hidden');
        });
        $(overlaySelector).on('click', () => {
            $(hideSelector + ', ' + overlaySelector).hide();
            $body.css('overflow', 'auto');
        });
    }

    // hospitals 페이지 처리
    if (pathParts.length === 2 && pathParts[1] === 'hospitals') {
        bindMoreOverlay('#more', '#more_box');

        // 공통 이동 함수
        function bindRedirect(selector) {
            $(selector).on('click', function () {
                let keyword = $(this).attr('data-keyword');
                if (keyword === '마취통증학과') keyword = '마취통증';

                const queryParams = [];
                if (keyword) queryParams.push(`keyword=${encodeURIComponent(keyword)}`);
                if (search.sortType) queryParams.push(`sortType=${encodeURIComponent(search.sortType)}`);
                if (search.commonFilter) queryParams.push(`commonFilter=${encodeURIComponent(search.commonFilter)}`);
                if (search.maxDistance) queryParams.push(`maxDistance=${encodeURIComponent(search.maxDistance)}`);

                location.href = `/hospitals/search?${queryParams.join('&')}`;
            });
        }
        bindRedirect('.subject, .sub-item, .hs-item, .hkw-item');

        // 검색 아이콘
        $('#h-search-icon').on('click', () => submitWithoutEmpty('#search_form'));
    }

    // hospitals/search 페이지 처리
    if (pathParts.length === 3 && pathParts[1] === 'hospitals' && pathParts[2] === 'search') {
        bindMoreOverlay('#moreSortType', '#more_box');

        // 검색 뒤로가기
        $('#search_back').on('click', () => location.href = '/hospitals');

        // 필터 클릭 이벤트 (첫번째 제외)
        $('.filter-item').slice(1).on('click', function () {
            $(this).toggleClass('filter-item-selected');

            const selectedFilters = $('.filter-item-selected').map((j, el) => $(el).attr('data-commonFilter')).get();
            $('#commonFilter').val(selectedFilters.join(','));
            submitWithoutEmpty('#search_form');
        });

        // 선택된 필터 유지
        if (search.commonFilter) {
            const selectedFilters = search.commonFilter.split(',');
            $('.filter-item').slice(1).each(function () {
                if (selectedFilters.includes($(this).attr('data-commonFilter'))) {
                    $(this).addClass('filter-item-selected');
                }
            });
        }

        // 정렬 항목 클릭 시 처리
        $('.sortType-item').on('click', function () {
            $('#sortType').val($(this).attr('data-sortType'));
            submitWithoutEmpty('#search_form');
        });

        // 무한 스크롤 병원 로딩
        const $hospitalListBox = $('#hospitalListBox');
        const maxItems = 300;
        let currentPage  = 0; // 최초 0으로 시작
        let totalLoaded  = 0;
        let loading = false;

        function loadHospitals() {
            if (loading || totalLoaded >= maxItems) return;

            loading = true;
            $.ajax({
                url: '/hospitals/search-json',
                type: 'POST',
                dataType:'json',
                data: {
                    page: currentPage
                },
                success: function(data) {
                    if(!data.length){
                        loading = false;
                        return;
                    }

                    let output = '';
                    data.forEach(hos => {
                        output += `<div class="hospital-box" data-hosnum="${hos.code}">
                                    <div class="d-flex align-items-center">
                                         <div class="hospital-name fs-17 fw-8 text-black-6">${hos.name}</div>
                                    </div>
                                    <div class="hospital-around fs-11 fw-9 text-gray-7">${hos.around}m</div>
                                    <div class="hospital-open fs-13 fw-7 text-black-4 d-flex align-items-center">`;

                        const timeStart = hos['time' + search.date.day + 'S'];
                        const timeClose = hos['time' + search.date.day + 'C'];

                        if (timeStart !== 'null' || timeClose !== 'null') {
                            if (timeStart <= search.date.time && search.date.time < timeClose) {
                                output += `<div class="greenCircle"></div>진료중&nbsp;<div class="vr"></div>&nbsp;${timeClose.slice(0, 2)}:${timeClose.slice(2, 4)} 마감`;
                            } else {
                                output += `<div class="redCircle"></div>진료종료`;
                                const nextDay = (search.date.day % 7) + 1;
                                const nextStart = hos['time' + nextDay + 'S'];
                                if (nextStart !== 'null') {
                                    output += `&nbsp;<div class="vr"></div>&nbsp;내일${nextStart.slice(0, 2)}:${nextStart.slice(2, 4)} 오픈`;
                                }
                            }
                        } else {
                            output += '<div class="redCircle"></div>휴무';
                        }
                        output += `</div>
                                   <div class="hospital-address fs-12 fw-7 text-black-3">${hos.address}</div>
                                   </div><div class="line"></div>`;
                    });

                    $hospitalListBox.append(output);
                    totalLoaded += data.length;
                    currentPage++;
                    loading = false;

                    $hospitalListBox.find('.hospital-box').off('click').on('click', function () {
                        location.href = '/hospitals/search/' + $(this).data('hosnum');
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

        // 초기 로드
        loadHospitals();
    }

    // hospitals/search/{hosNum} 페이지 처리
    if (pathParts.length === 4 && pathParts[1] === 'hospitals' && pathParts[2] === 'search' && !isNaN(pathParts[3])) {
        $('#call_btn').on('click', function () {
            const phone = '${hospital.mainPhone}'; // 서버에서 Thymeleaf 치환 가능

            const $tempInput = $('<input>').appendTo('body').val(phone).select();
            document.execCommand('copy');
            $tempInput.remove();

            alert('전화번호가 클립보드에 복사되었습니다: ' + phone);
        });

        /*$('.hosRev_more_content_icon').on('click',function(event){
            const content = $(event.target).closest('.d-flex').find('.detail_hosRev_content');
            content.toggleClass('overflowText','heightAuto');
        })*/

        /*
        // 예약 버튼 클릭 이벤트
        $('#reservation_btn').on('click', function () {
            $.ajax({
                url: '/reservation/reservation',
                method: 'GET',
                data: { hos_num: '${hospital.hos_num}' },
                dataType: 'json',
                success: function (res) {
                    switch (res.result) {
                        case 'success':
                            $('#reservation_page').show();
                            initializeCalendar('${hospital.hos_num}');
                            break;
                        case 'logout':
                            alert('로그인 후 이용해주세요');
                            location.href = "${pageContext.request.contextPath}/users/login";
                            break;
                        case 'doctor':
                            alert('의사회원은 이용할 수 없습니다.');
                            break;
                        case 'suspended':
                            alert('정지회원입니다. 일반회원의 경우에만 이용할 수 있습니다.');
                            break;
                        case 'unauthorized':
                            alert('해당 서비스는 이용할 수 없습니다.');
                            break;
                        default:
                            alert('예약 신청 페이지 오류 발생');
                    }
                },
                error: function () {
                    alert('네트워크 오류 발생');
                }
            });
        });
        */

        /*
        // 의사 이력 AJAX 호출 및 화면 구성
        $.ajax({
            url: '/doctor/doctorHistory',
            method: 'GET',
            data: { hos_num: '${hospital.hos_num}' },
            dataType: 'json',
            success: function (param) {
                let content = '';

                if (param.doctor === 'empty') {
                    content = ''; // 의사 정보 없으면 내용 없음
                } else {
                    content += '<div class="line"></div><div style="height:15px;" class="bg-gray-0"></div>';
                    content += '<p class="fs-18 fw-7" style="padding:0 20px;">의사 소개</p>';

                    param.doctor.forEach(function (doctor) {
                        content += `
                            <div style="padding:20px 30px; display:flex; align-items:center;">
                                <div>
                                    <img src="/doctor/docViewProfile?mem_num=${doctor.doc_num}" alt="${doctor.mem_name}" class="doctor-image" style="width: 80px; height: 80px; margin-right:10px;">
                                </div>
                                <div>
                                    <span class="fs-15 fw-7">${doctor.mem_name} 의사</span>
                                    ${doctor.doc_history ? `<br><span class="fs-14">${doctor.doc_history}</span>` : ''}
                                </div>
                            </div>`;
                    });
                }

                $('#doctor_history').append(content);
            },
            error: function () {
                alert('의사 연혁 출력 오류');
            }
        });
        */
    }
});
