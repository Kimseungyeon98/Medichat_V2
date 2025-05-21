$(function(){
	/*---------------------
		비밀번호 변경체크
	----------------------*/
	$('#mem_passwd').keyup(function(){
		if($('#confirm_passwd').val()!='' && $('#confirm_passwd').val()!=$(this).val() ){
			$('#message_password').text('비밀번호 불일치').css('color','red');
		}else if($('#confirm_passwd').val()!='' && $('#confirm_passwd').val()==$(this).val() ){
			$('#message_password').text('비밀번호 일치').css('color','#000');
		}
	});
	$('#confirm_passwd').keyup(function(){
		if($('#mem_passwd').val()!='' && $('#mem_passwd').val()!=$(this).val() ){
			$('#message_password').text('비밀번호 불일치').css('color','red');
		}else if($('#mem_passwd').val()!='' && $('#mem_passwd').val()==$(this).val() ){
			$('#message_password').text('비밀번호 일치').css('color','#000');
		}
	});
	
	$('#member_changePasswd').submit(function(){
		if($('#now_passwd').val().trim()==''){
			Swal.fire({
               title:'현재 비밀번호를 입력하세요.',
               icon:'warning',
               confirmButtonText:'확인'
            }).then(() => {
               $('#now_passwd').val('').focus();
            });
            return false;
		}
		if($('#confirm_passwd').val().trim()==''){
			Swal.fire({
               title:'새비밀번호 확인을 입력하세요.',
               icon:'warning',
               confirmButtonText:'확인'
            }).then(() => {
               $('#confirm_passwd').val('').focus();
            });
            return false;
		}
		if($('#mem_passwd').val()!=$('#confirm_passwd').val()){
			$('#message_password').text('비밀번호 불일치').css('color','red');
			return false;
		}
		
		$.ajax({
	        type:"POST",
	        url: "/member/changePasswd",
	        data: $('#member_changePasswd').serialize(),
	        success: function(response) {
	            Swal.fire({
                   title:'비밀번호가 성공적으로 변경되었습니다!',
                   icon:'success',
                   confirmButtonText:'확인'
                }).then(() => {
                   location.href = '/main/main';
                });
	        },
	        error: function() {
	            Swal.fire({
                   title:'네트워크 오류 발생',
                   icon:'error',
                   confirmButtonText:'확인'
                });
	        }
  		});
	});
});