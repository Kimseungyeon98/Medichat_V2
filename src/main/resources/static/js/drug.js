$(document).ready(function(){
    let drg_num = '';

    $('.drg-effect-icon').click(function(){
        drg_num = $(this).data('drg_num');

        let offset = $(this).offset();
        $('.drug-effect-info').css({
            top: offset.top+ $(this).outerWidth(),
            left: offset.left - 140
        });

        if ($('.drg-effect-info').is(':visible')) {
            $('.drg-effect-info').slideUp('fast', function() {
                // 정보창을 닫은 후 새로운 데이터를 가져와서 열기
                drugEffectToggle(drg_num);
            });
        } else {
            // 정보창이 닫혀 있는 경우 바로 새로운 데이터를 가져와서 열기
            drugEffectToggle(drg_num);
        }
    });
    function drugEffectToggle(drg_num){
        $.ajax({
            url:'/drug/drugEffect',
            type:'get',
            data:{drg_num:drg_num},
            dataType:'JSON',
            success:function(param){
                // 이전 내용 초기화
                $('.drug-effect-header-content').empty();
                $('.drug-effect-content').empty();

                // 새 내용 추가
                $('.drug-effect-header-content').append(param.drg_name);
                $('.drug-effect-content').text(param.drg_effect);

                // drug-effect-info 요소를 slideToggle로 표시
                $('.drug-effect-info').slideToggle('fast');
            },
            error:function(){
                alert('네트워크 오류 발생');
            }
        }); // end of ajax
    }

    // 모달 닫기 기능
    $('.close').click(function(){
        $('.drug-effect-info').slideToggle('fast');
    });

    $('#h-search-icon').click(function(){
        $('#search_form').submit();
    });
}); //end of ready function