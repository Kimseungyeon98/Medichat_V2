$(function(){
	//비밀번호 찾기
	$('#doctor_findPasswd').submit(function(event){
		if($('#mem_id').val().trim()==''){
			Swal.fire({
               title:'아이디를 입력하세요.',
               icon:'warning',
               confirmButtonText:'확인'
            }).then(() => {
               $('#mem_id').val('').focus();
            });
            return false;
		}
		if($('#doc_email').val().trim()==''){
			Swal.fire({
               title:'이메일을 입력하세요.',
               icon:'warning',
               confirmButtonText:'확인'
            }).then(() => {
               $('#doc_email').val('').focus();
            });
            return false;
		}
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'/doctor/getPasswordInfo',
			type:'post',
			data:form_data,
			dataType:'json',
			beforeSend:function(){
				$('#loading').show();//표시
			},
			complete:function(){
				$('#loading').hide();//숨김
			},
			success:function(param){
				if(param.result == 'invalidInfo'){
					Swal.fire({
                       title:'아이디 또는 이메일 불일치',
                       icon:'error',
                       confirmButtonText:'확인'
                    });
				}else if(param.result == 'noAuthority'){
					Swal.fire({
                       title:'정지회원 입니다.',
                       icon:'error',
                       confirmButtonText:'확인'
                    });
				}else if(param.result == 'success'){
					Swal.fire({
                       title:'이메일로 임시비밀번호가 발송되었습니다.',
                       icon:'success',
                       confirmButtonText:'확인'
                    }).then(() => {
                       location.href = 'login';
                    });
				}else{
					Swal.fire({
                       title:'비밀번호 찾기 오류 발생',
                       icon:'error',
                       confirmButtonText:'확인'
                    });
				}
			},
			error:function(){
				Swal.fire({
                   title:'네트워크 오류 발생',
                   icon:'error',
                   confirmButtonText:'확인'
                });
			}
		});
		//기본 이벤트 제거
		event.preventDefault();
	});
});