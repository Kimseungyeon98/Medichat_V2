$(function(){
    /*------------------
            회원가입
    --------------------*/
    //아이디 중복 여부 저장 변수 : 0은 아이디 중복 또는 중복 체크 미실행
    //						1은 아이디 미중복
    let checkId = 0;

    //아이디 중복 체크
    $('#confirmId').click(function(){
        if($('#mem_id').val().trim()==''){
            $('#message_id').css('color','red').text('아이디를 입력하세요!');
            $('#mem_id').val('').focus();
            return;
        }
        $('#message_id').text('');//메시지 초기화

        //서버와 통신
        $.ajax({
            url:'confirmId',
            type:'get',
            data:{mem_id:$('#mem_id').val()},
            dataType:'json',
            success:function(param){
                if(param.result == 'idNotFound'){
                    checkId=1;
                    $('#message_id').css('color','#000').text('등록 가능한 ID 입니다.');
                }else if(param.result == 'idDuplicated'){
                    checkId=0;
                    $('#message_id').css('color','red').text('중복된 ID 입니다.');
                    $('#mem_id').val('').focus();
                }else if(param.result == 'notMatchPattern'){
                    checkId=0;
                    $('#message_id').css('color','red').text('영문,숫자 4자~12자 입력');
                    $('#mem_id').val('').focus();
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
    $('#member_register #mem_id').keydown(function(){
        checkId=0;
        $('#message_id').text('');
    });//end of keydown

    //submit 이벤트 발생시 아이디 중복 체크 여부 확인
    $('#member_register').submit(function(){
        if(checkId=0){
            $('#message_id').css('color','red').text('ID 중복 체크 필수!');
            if($('#mem_id').val().trim()==''){
                $('#mem_id').val('').focus();
            }
            return false;
        }
    });//end of submit

});

$('#reload_captcha').click(function() {
    $('#captcha_img').attr('src', '[[@{/getCaptcha}]]?' + new Date().getTime());
});

$('#mem_birth').datepicker({
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
    $('#mem_birth').datepicker('show');
});
// 우편번호 찾기 화면을 넣을 element
var element_layer = document.getElementById('layer');

function closeDaumPostcode() {
    // iframe을 넣은 element를 안보이게 한다.
    element_layer.style.display = 'none';
}

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                //(주의)address1에 참고항목이 보여지도록 수정
                // 조합된 참고항목을 해당 필드에 넣는다.
                //(수정) document.getElementById("address2").value = extraAddr;

            }
            //(수정) else {
            //(수정)    document.getElementById("address2").value = '';
            //(수정) }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('mem_zipcode').value = data.zonecode;
            //(수정) + extraAddr를 추가해서 address1에 참고항목이 보여지도록 수정
            document.getElementById("mem_address1").value = addr + extraAddr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("mem_address2").focus();

            // iframe을 넣은 element를 안보이게 한다.
            // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
            element_layer.style.display = 'none';
        },
        width : '100%',
        height : '100%',
        maxSuggestItems : 5
    }).embed(element_layer);

    // iframe을 넣은 element를 보이게 한다.
    element_layer.style.display = 'block';

    // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
    initLayerPosition();
}

// 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
// resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
// 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
function initLayerPosition(){
    var width = 300; //우편번호서비스가 들어갈 element의 width
    var height = 400; //우편번호서비스가 들어갈 element의 height
    var borderWidth = 5; //샘플에서 사용하는 border의 두께

    // 위에서 선언한 값들을 실제 element에 넣는다.
    element_layer.style.width = width + 'px';
    element_layer.style.height = height + 'px';
    element_layer.style.border = borderWidth + 'px solid';
    // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
    element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
    element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
}