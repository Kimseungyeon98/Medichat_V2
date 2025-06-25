$(function(){
    /*------------------
            회원가입
    --------------------*/
    //아이디 중복 여부 저장 변수 : 0은 아이디 중복 또는 중복 체크 미실행
    //						1은 아이디 미중복
    let checkId = 0;

    const $memId = $('#memId');
    const $messageId = $('#messageId');
    const $registerForm = $('#member_register');
    const $captchaImg = $('#captchaImg');
    const $birthInput = $('#memBirth');
    const $calendarBtn = $('#calendarButton');
    const $zipcodeBtn = $('#btnPostcode');
    const $zipcodeInput = $('#memZipcode');
    const $addr1Input = $('#memAddress1');
    const $addr2Input = $('#memAddress2');
    const $elementLayer = $('#layer');
    const $closeLayerBtn = $('#btnCloseLayer');

    //아이디 중복 체크
    $('#confirmId').on('click', function () {
        const memIdVal = $memId.val().trim();

        if (!memIdVal) {
            $messageId.css('color', 'red').text('아이디를 입력하세요!');
            $memId.focus();
            return;
        }

        $messageId.text('');//메시지 초기화

        //서버와 통신
        $.ajax({
            url:'confirmId',
            type:'get',
            data: { mem_id: memIdVal },
            dataType:'json',
            success:function(res){
                switch (res.result) {
                    case 'idNotFound':
                        checkId = 1;
                        $messageId.css('color', '#000').text('등록 가능한 ID 입니다.');
                        break;
                    case 'idDuplicated':
                        checkId = 0;
                        $messageId.css('color', 'red').text('중복된 ID 입니다.');
                        $memId.val('').focus();
                        break;
                    case 'notMatchPattern':
                        checkId = 0;
                        $messageId.css('color', 'red').text('영문, 숫자 4자~12자 입력');
                        $memId.val('').focus();
                        break;
                    default:
                        checkId = 0;
                        Swal.fire({
                            title: 'ID 중복 체크 오류',
                            icon: 'error',
                            confirmButtonText: '확인'
                        });
                }
            },
            error:function(){
                checkId=0;
                Swal.fire({
                    title: '네트워크 오류 발생',
                    icon: 'error',
                    confirmButtonText: '확인'
                });
            }
        });
    });//end of click

    // 아이디 입력 시 중복 확인 초기화
    $memId.on('keydown', function () {
        checkId = 0;
        $messageId.text('');
    });

    // 제출 전 중복 확인 여부 체크
    $registerForm.on('submit', function () {
        if (checkId === 0) {
            $messageId.css('color', 'red').text('ID 중복 체크 필수!');
            if (!$memId.val().trim()) {
                $memId.val('').focus();
            }
            return false;
        }
    });

    // 캡차 새로고침
    $('#reload_captcha').on('click', function () {
        $captchaImg.attr('src', '[[@{/getCaptcha}]]?' + new Date().getTime());
    });

    // 생년월일 datepicker
    $birthInput.datepicker({
        dateFormat: 'yy-mm-dd',
        changeYear: true,
        changeMonth: true,
        yearRange: 'c-100:c+0',
        maxDate: '0',
        prevText: '이전 달',
        nextText: '다음 달',
        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        dayNames: ['일','월','화','수','목','금','토'],
        dayNamesShort: ['일','월','화','수','목','금','토'],
        dayNamesMin: ['일','월','화','수','목','금','토'],
        showMonthAfterYear: true,
        yearSuffix: '년'
    });

    // 달력 버튼 클릭 시 datepicker 표시
    $calendarBtn.on('click', function () {
        $birthInput.datepicker('show');
    });

    // 우편번호 찾기
    $zipcodeBtn.on('click', function () {
        execDaumPostcode();
    });

    // 우편번호 찾기 창 닫기
    $closeLayerBtn.on('click', function () {
        $elementLayer.hide();
    });

    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function (data) {
                let addr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
                let extraAddr = '';

                if (data.userSelectedType === 'R') {
                    if (data.bname && /[동|로|가]$/g.test(data.bname)) {
                        extraAddr += data.bname;
                    }
                if (data.buildingName && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr) {
                    extraAddr = ' (' + extraAddr + ')';
                }
            }

                $zipcodeInput.val(data.zonecode);
                $addr1Input.val(addr + extraAddr);
                $addr2Input.focus();
                $elementLayer.hide();
            },
            width: '100%',
            height: '100%',
            maxSuggestItems: 5
        }).embed($elementLayer[0]);

        $elementLayer.show();
        centerLayer($elementLayer, 300, 400);
    }

    function centerLayer($layer, width, height) {
        const border = 5;
        $layer.css({
            width: width + 'px',
            height: height + 'px',
            border: border + 'px solid',
            left: ($(window).width() - width) / 2 - border + 'px',
            top: ($(window).height() - height) / 2 - border + 'px'
        });
    }
});