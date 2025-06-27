$(function(){
    // 공통
    const pathParts = window.location.pathname.split('/');
    const $body = $('body');
    const $overlay = $('#overlay');

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

    // pharmacies 페이지 처리
    if (pathParts.length === 2 && pathParts[1] === 'pharmacies') {
        bindMoreOverlay('#moreSortType', '#more_box');

        // 검색 뒤로가기
        $('#search_back').on('click', () => location.href = '/');

        // 필터 클릭 이벤트 처리
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

        function loadPharmacies() {
            if (loading || totalLoaded >= maxItems) return;

            loading = true;
            $.ajax({
                url: '/pharmacies/search-json',
                type: 'POST',
                dataType:'json',
                data: {
                    page: currentPage
                },
                success: function(data) {
                    if(data.length===0){
                        loading = false;
                        return;
                    }

                    let output = '';
                    data.forEach(pha => {
                        output += `<div class="hospital-box" data-hosnum="${pha.code}">
                                    <div class="d-flex align-items-center">
                                         <div class="hospital-name fs-17 fw-8 text-black-6">${pha.name}</div>
                                    </div>
                                    <div class="hospital-around fs-11 fw-9 text-gray-7">${pha.around}m</div>
                                    <div class="hospital-open fs-13 fw-7 text-black-4 d-flex align-items-center">`;

                        const timeStart = pha['time' + search.date.day + 'S'];
                        const timeClose = pha['time' + search.date.day + 'C'];

                        if (timeStart !== 'null' || timeClose !== 'null') {
                            if (timeStart <= search.date.time && search.date.time < timeClose) {
                                output += `<div class="greenCircle"></div>진료중&nbsp;<div class="vr"></div>&nbsp;${timeClose.slice(0, 2)}:${timeClose.slice(2, 4)} 마감`;
                            } else {
                                output += `<div class="redCircle"></div>진료종료`;
                                const nextDay = (search.date.day % 7) + 1;
                                const nextStart = pha['time' + nextDay + 'S'];
                                if (nextStart !== 'null') {
                                    output += `&nbsp;<div class="vr"></div>&nbsp;내일${nextStart.slice(0, 2)}:${nextStart.slice(2, 4)} 오픈`;
                                }
                            }
                        } else {
                            output += '<div class="redCircle"></div>휴무';
                        }
                        output += `</div>
                                   <div class="hospital-address fs-12 fw-7 text-black-3">${pha.address}</div>
                                   </div><div class="line"></div>`;
                    });

                    $hospitalListBox.append(output);
                    totalLoaded += data.length;
                    currentPage++;
                    loading = false;

                    $hospitalListBox.find('.hospital-box').off('click').on('click', function () {
                        location.href = '/pharmacies/' + $(this).attr('data-hosnum');
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
                loadPharmacies();
            }
        });

        // 초기 로드
        loadPharmacies();
    }

    // pharmacies/{hosNum} 페이지 처리
    if (pathParts.length === 3 && pathParts[1] === 'pharmacies' && !isNaN(pathParts[2])) {
        $('#call_btn').click(function() {
            // 복사할 텍스트 지정
            var phone = '${pharmacy.mainPhone}';

            const $tempInput = $('<input>').appendTo('body').val(phone).select();
            document.execCommand('copy');
            $tempInput.remove();

            alert('전화번호가 클립보드에 복사되었습니다: ' + phone);
        });
    }
});
