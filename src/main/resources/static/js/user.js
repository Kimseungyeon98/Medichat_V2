
$(function(){
    /*------------------
            회원가입
    --------------------*/
    //아이디 중복 여부 저장 변수 : 0은 아이디 중복 또는 중복 체크 미실행
    //						1은 아이디 미중복
    let checkId = 0;

    //아이디 중복 체크
    $('#confirmId').click(function(){
        if($('#memId').val().trim()==''){
            $('#messageId').css('color','red').text('아이디를 입력하세요!');
            $('#memId').val('').focus();
            return;
        }
        $('#messageId').text('');//메시지 초기화

        //서버와 통신
        $.ajax({
            url:'confirmId',
            type:'get',
            data:{mem_id:$('#memId').val()},
            dataType:'json',
            success:function(param){
                if(param.result == 'idNotFound'){
                    checkId=1;
                    $('#messageId').css('color','#000').text('등록 가능한 ID 입니다.');
                }else if(param.result == 'idDuplicated'){
                    checkId=0;
                    $('#messageId').css('color','red').text('중복된 ID 입니다.');
                    $('#memId').val('').focus();
                }else if(param.result == 'notMatchPattern'){
                    checkId=0;
                    $('#messageId').css('color','red').text('영문,숫자 4자~12자 입력');
                    $('#memId').val('').focus();
                }else{
                    checkId=0;
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

    //아이디 중복 안내 메시지 초기화 및 아이디 중복 값 초기화
    $('#member_register #memId').keydown(function(){
        checkId=0;
        $('#messageId').text('');
    });//end of keydown

    //submit 이벤트 발생시 아이디 중복 체크 여부 확인
    $('#member_register').submit(function(){
        if(checkId=0){
            $('#messageId').css('color','red').text('ID 중복 체크 필수!');
            if($('#memId').val().trim()==''){
                $('#memId').val('').focus();
            }
            return false;
        }
    });//end of submit

    $('#reload_captcha').click(function() {
        $('#captchaImg').attr('src', '[[@{/getCaptcha}]]?' + new Date().getTime());
    });

    $('#memBirth').datepicker({
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

    $('#calendarButton').click(function() {
        $('#memBirth').datepicker('show');
    });


    // 우편번호 찾기 화면을 넣을 element
    var $element_layer = $('#layer');

    function closeDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        $element_layer.hide();
    }

    $('#btnPostcode').on('click', function () {
        execDaumPostcode();
    });

    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function (data) {
                var addr = '';
                var extraAddr = '';

                if (data.userSelectedType === 'R') {
                    addr = data.roadAddress;
                } else {
                    addr = data.jibunAddress;
                }

                if (data.userSelectedType === 'R') {
                    if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                        extraAddr += data.bname;
                    }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
            }

            $('#memZipcode').val(data.zonecode);
            $('#memAddress1').val(addr + extraAddr);
            $('#memAddress2').focus();

                $element_layer.hide();
            },
            width: '100%',
            height: '100%',
            maxSuggestItems: 5
        }).embed($element_layer[0]);

        $element_layer.show();
        initLayerPosition();
    }

    function initLayerPosition() {
        var width = 300;
        var height = 400;
        var borderWidth = 5;

        $element_layer.css({
            'width': width + 'px',
            'height': height + 'px',
            'border': borderWidth + 'px solid',
            'left': ((($(window).width() - width) / 2) - borderWidth) + 'px',
            'top': ((($(window).height() - height) / 2) - borderWidth) + 'px'
        });
    }

    // 닫기 버튼 이벤트도 jQuery로 연결
    $('#btnCloseLayer').on('click', function () {
        closeDaumPostcode();
    });
});