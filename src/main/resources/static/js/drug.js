$(function(){
    const $drugEffectInfo = $('.drug-effect-info');
    const $headerContent = $('.drug-effect-header-content');
    const $content = $('.drug-effect-content');

    function drugEffectToggle(drgNum) {
        $.ajax({
            url: '/drug/drugEffect',
            type: 'GET',
            data: { drg_num: drgNum },
            dataType: 'json',
            success: function (param) {
                $headerContent.empty().append(param.drg_name);
                $content.empty().text(param.drg_effect);
                $drugEffectInfo.slideToggle('fast');
            },
            error: function () {
                alert('네트워크 오류 발생');
            }
        });
    }

    $('.drg-effect-icon').on('click', function () {
        const drgNum = $(this).attr('data-drg_num');
        const offset = $(this).offset();

        $drugEffectInfo.css({
            top: offset.top + $(this).outerWidth(),
            left: offset.left - 140
        });

        if ($drugEffectInfo.is(':visible')) {
            $drugEffectInfo.slideUp('fast', function () {
                drugEffectToggle(drgNum);
            });
        } else {
            drugEffectToggle(drgNum);
        }
    });

    $('.close').on('click', () => {
        $drugEffectInfo.slideToggle('fast');
    });

    $('#h-search-icon').on('click', () => {
        $('#search_form').trigger('submit');
    });
});