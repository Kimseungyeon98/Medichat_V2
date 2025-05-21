		$(function(){
			$('.chevron-down').click(
				function() {
					if ($('#' + $(this).attr('data-num')).hasClass('hide')) {
							$('#' + $(this).attr('data-num')).removeClass('hide');
					} else {
							$('#' + $(this).attr('data-num')).addClass('hide');
					}
					}
				);
		})
						