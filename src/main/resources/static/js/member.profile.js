$(function(){
	/*----------------------------
		의사회원 프로필 사진 등록 및 수정
	-----------------------------*/
	//수정 버튼 이벤트 처리
	$('#photo_btn').click(function(){
		$('#photo_choice').show();
	});
	//처음 화면에 보여지는 이미지 읽기
	let photo_path = $('.my-photo').attr('src');
	let my_photo;//업로드 하고자 선택한 이미지 저장
	//파일 선택 이벤트 연결
	$('#upload').change(function(){
		my_photo = this.files[0];//선택한 이미지 저장
		if(!my_photo){//선택 후 취소하는 경우
			$('.my-photo').attr('src',photo_path);
			return;
		}
		if(my_photo.size > 1024*1024){//사진 용량 제한
			alert(Math.round(my_photo.size/1024)+'kbytes(1024kbytes까지만 업로드 가능)');
			$('.my-photo').attr('src',photo_path);
			$(this).val('');
			return;
		}
		//이미지 미리보기 처리
		const reader = new FileReader();//const 를 사용하면 객체 주소 변경 불가
		reader.readAsDataURL(my_photo);
		
		reader.onload=function(){
			$('.my-photo').attr('src',reader.result);
		};
	});//end of change
	
	//파일 업로드 처리
	$('#photo_submit').click(function(){
		if($('#upload').val()==''){
			Swal.fire({
               title:'파일을 선택하세요!',
               icon:'warning',
               confirmButtonText:'확인'
            }).then(() => {
               $('#upload').focus();
            });
            return;
		}
		//서버에 전송할 파일 선택
		const form_data = new FormData();
		form_data.append('upload',my_photo);
		
		//서버와 통신(경로에 el 사용 불가 > 상대적인 경로 입력)
		$.ajax({
			url:'../member/updateMemPhoto',
			type:'post',
			data:form_data,
			dataType:'json',
			contentType:false,
			processData:false,
			success:function(param){
				if(param.result == 'success'){
					Swal.fire({
                       title:'프로필 사진이 저장되었습니다.',
                       icon:'success',
                       confirmButtonText:'확인'
                    })
					//교체된 이미지 저장
					photo_path = $('.my-photo').attr('src');
					$('#upload').val('');
					$('#photo_choice').hide();
					$('#photo_btn').show();
				}else{
					Swal.fire({
                       title:'파일 전송 오류 발생',
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
	});
	//취소 버튼 처리
	$('#photo_reset').click(function(){
		$('.my-photo').attr('src',photo_path);
		$('#upload').val('');
		$('#photo_choice').hide();
		$('#photo_btn').show();
	});
});
