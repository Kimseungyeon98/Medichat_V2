$(function(){
	let chat_num;
	let res_date;
	let res_time;
	let res_num;
	let doc_name = '';	//담당 의사 이름
	let pay_amount = ''; //결제 금액
	let mem_num = '';	//결제 회원 번호
	let mem_phone =''; //결제 회원 휴대폰 번호(iamport 실행 시 필요)
	
	
	let message_socket; //웹소켓 식별자
	console.log('페이지 로딩 완료');
	
	/*=======================
	  		 웹소켓 연결
	=========================*/
	function connectWebSocket(){
		message_socket = new WebSocket('ws://localhost:8000/message-ws');
		
		//채팅 페이지 접속 시
		message_socket.onopen=function(evt){
			console.log('채팅페이지 접속 : ' + $('#chat_body').length);
			if($('#chat_body').length==1){
				message_socket.send('msg');
			}
		};
		//서버로부터 메시지를 받으면 호출되는 함수 지정
		message_socket.onmessage=function(evt){
			//메시지 읽기
			let data = evt.data;
			if($('#chat_body').length==1 && (data.substring(0,3)=='msg')){
				selectChat();
			}
		};
		message_socket.onclose=function(evt){
			//소켓이 종료된 후 부과적인 작성이 있을 경우 명시
			console.log('chat close');
		};
	}//end of websocket connect
	
	/*=======================
	  스크롤 채팅 최하단으로 내리기
	=========================*/
	function scrollToBottom() {
            const chatBody = document.getElementById('chat_body');
            chatBody.scrollTop = chatBody.scrollHeight;
    }; //end of scroll
	
	/*=======================
		    메시지 불러오기
	=========================*/
	function selectChat(){
        
        $('.chat-select-notice').css('display','none');
        
		//서버와 통신
		$.ajax({
			url:'/chat/chatDetail',
			type:'get',
			data:{chat_num:chat_num, res_date:res_date, res_time:res_time, res_num:res_num},
			dataType:'json',
			success:function(param){
				if(param.userCheck=='logout'){
					//로그아웃 상태인 경우, 메인으로 이동
					alert('로그인 후 이용해주십시오');
					message_socket.close();
					window.location.href='/main/main';
				}else{
					//로그인 상태인 경우
					$('#chat_num').val(chat_num);
					$('#res_date').val(res_date);
					$('#res_time').val(res_time);
					$('#res_num').val(res_num);
					let res_title = '';
					res_title += '			<div class="res-title">'
					res_title += '			<div class="chat-title fs-25 fw-8" id="chat_title">';
					res_title += '				예약번호: '+res_num;
					res_title += '			</div>';
					res_title += '			<div class="chat-date fs-20">';
					res_title += '				'+res_date+'  '+res_time;
					res_title += '			</div>';
					res_title += '			</div>';
					if(param.type=='3'){
						res_title += '			<button type="button" class="chat-close-btn" id="chat_close" disabled>진료 종료</button>';
					};

					$('#chat_header').html(res_title);
					
					let message = '';
					message += '		<ul>';
					
					if(param.patient=='withdrawal'){
						message += '<div class="close-room">';
						message += '<img src="../images/icon/suspanded.png" width="40px" height="40px">';
						message += '<span class="fs-21 chat-notice fw-8">해당 환자는 탈퇴한 회원입니다.</span>';
						message += '</div>';
					}else if(param.patient=='suspended'){
						message += '<div class="close-room">';
						message += '<img src="../images/icon/suspanded.png" width="40px" height="40px">';
						message += '<span class="fs-21 chat-notice fw-8">해당 환자는 정지된 회원입니다.</span>';
						message += '</div>';
					}
					
					if(param.chat=='open'){
						if(param.status=='completed'){
							$('.chat-input input').prop('disabled', true);
							$('.chat-input input').attr('placeholder','진료가 끝난 채팅방입니다.');
                    		$('.chat-input button').prop('disabled', true);
                    		$('#chat_close').prop('disabled',true);
						}else{
							$('.chat-input input').prop('disabled', false);
	                    	$('.chat-input button').prop('disabled', false);
	                    	$('#chat_close').prop('disabled',false);
	                    	$('.chat-input input').attr('placeholder','');
                    	}
                    	$(param.list).each(function(index, item) {
                        	if (param.type === '1' || param.type === '2') {
                            	if (item.msg_sender_type === 0) { // 일반 회원이 0
                                	// 유저가 일반 회원이면서 일반 회원의 입력인 경우(본인이 보낸 메시지인 경우)
                                	message += ' <li class="my-message bg-green-7 fs-17">';
                            	} else if (item.msg_sender_type === 1) {
                                	// 유저가 일반 회원이면서 의사 회원의 입력인 경우(상대방이 보낸 메시지인 경우)
                                	message += ' <li class="other-message bg-green-5 fs-17">';
                            	}
                        	} else if (param.type === '3') {
                            	if (item.msg_sender_type === 1) { // 의사 회원이 1
                                	// 유저가 의사 회원이면서 일반 회원의 입력인 경우(본인이 보낸 메시지인 경우)
                                	message += ' <li class="my-message bg-green-7 fs-17">';
                            	} else if (item.msg_sender_type === 0) {
                                	// 유저가 의사 회원이면서 의사 회원의 입력인 경우(상대방이 보낸 메시지인 경우)
                                	message += ' <li class="other-message bg-green-5 fs-17">';
                            	}
                        	}

                        // 메시지 내용 추가
                        if (item.msg_content) {
                            message += item.msg_content;
                        }

                        // 이미지 추가
                        if (item.msg_image) {
                            message += '<img src="data:image/jpeg;base64,' + item.msg_image + '" class="msg-image"/>';
                        }
                        message += '</li>';
                        
                    }); // end of message list

						
						message += '	</ul>'
						
					}else if(param.chat=='close'){
						//예약시간이 되지 않아서 채팅방을 사용할 수 없는 상태
						message_socket.close();
						message += '		<div class="close-room">';
						message += '			<img src="../images/icon/chat_bubble.png" width="60px" height="60px" class="chat-bubble">';
						message += '			<span class="fs-21 chat-notice fw-8">아직 진료가 시작되지 않은 채팅방입니다</span>'
						message += '		</div>'
						$('.chat-input input').attr('disabled','disabled');
						$('.chat-input button').attr('disabled','disabled');
						$('.chat-input input').attr('placeholder','진료가 시작되지 않은 채팅방입니다.');
					}
					
					$('#chat_body').html(message);
					scrollToBottom();
				}//end of user login
			},
			error:function(){
				alert('네트워크 오류 발생');
				message_socket.close();
			}
		}); //end of ajax
	}; //end of selectChat
		
	/*=======================
	  채팅방 선택 시 채팅방 불러오기
	=========================*/	
	$('.chat-room').click(function(event){
		event.preventDefault();
		
		connectWebSocket();
		console.log('채팅방 선택 이벤트 발생');
		
		chat_num = $(this).data('chat-num');
        res_date = $(this).data('res-date');
        res_time = $(this).data('res-time');
        res_num = $(this).data('res-num');
        
        
        const selected = $(this).parent().parent();
        const not_selected = $('.chat-room').parent().parent();
        console.log(selected);
        
        not_selected.removeClass('selected-chat chat-nav-selected-bg');
        selected.addClass('selected-chat chat-nav-selected-bg');
        
        selectChat();
	}); //end of chat-room click
	
	/*=======================
		    메시지 입력하기
	=========================*/
	//엔터 입력 시 폼 제출되게 하기
	$('#msg_content').on('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault(); // 기본 Enter 키 동작 막기
            $('#chat_input').submit(); // 폼 제출 트리거
        }
    });
	
	$('#chat_input').submit(function insertMsg(event){
		
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('전송 이벤트 발생');
		if($('#msg_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#msg_content').val('').focus();
			return false;
		}
		
		
		//서버와 통신
		$.ajax({
			url:'/chat/chatRoom',
			type:'post',
			data:$(this).serialize(), //form 제출 데이터를 문자열로 직렬화
									  //메시지 입력창에서 메시지에 해당하는 부분
			dataType:'json',
			success:function(param){
				if(param.userCheck=='logout'){
					//로그아웃 상태인 경우, 메인으로 이동
					alert('로그인 후 이용해주십시오');
					message_socket.close();
					window.location.href='/main/main';
				}else if(param.userCheck=='login'){
					//로그인 상태인 경우
					message_socket.send('msg');
					initForm();	
					console.log('메시지 입력');
				}
			},
			error:function(){
				message_socket.close();
				alert('네트워크 오류 발생');
			}
			
		}); //end of ajax
	});//end of insertMsg
	
	//채팅 입력 폼 초기화
	function initForm(){
		$('#msg_content').val('');
	} //end of init form
	
	//300자 이하로 입력
	$(document).on('keydown','#msg_content',function(){
		let inputLength = $(this).val().length;
		
		if(inputLength>300){
			$(this).val($(this).val().substring(0,300));
		}
	}); //end of keydown
	
	/*=======================
		 이미지 전송 폼 노출
	=========================*/
	$('#chat_image').click(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		$('#image_chat_num').val(chat_num);
		$('.image-form-bg').show();
		$('.image-form').show();
		
	}); //end of modal show
	
	//모달 창 닫기 버튼 클릭
	$('.image-form .close-button').click(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		$('.image-form-bg').hide();
		$('.image-form').hide();
	});
	
	//모달 창 생성 시 외부 클릭 이벤트 삭제
	$('.image-form').click(function(event) {
		event.stopPropagation();
	});
	
	/*=======================
		 	이미지 전송
	=========================*/
	$('#image_input').submit(function(event){
		//기본 이벤트 제거	
		event.preventDefault();
		
		if($('#select_image').val().trim()==''){
			alert('첨부파일을 선택하세요');
			$('#select_image').val('').focus();
			return false;
		}
		
		let formData = new FormData(this);
		
		console.log('이벤트 전송 이벤트 진입, formData:'+formData);
		//서버와 통신
		$.ajax({
			url:'/chat/image_input',
			type:'post',
			data:formData,
			dataType:'json',
			processData: false, // 데이터를 처리하지 않음
            contentType: false, // 콘텐츠 타입을 설정하지 않음
			success:function(param){
				if(param.userCheck=='logout'){
					//로그아웃 상태인 경우, 메인으로 이동
					alert('로그인 후 이용해주십시오');
					message_socket.close();
					window.location.href='/main/main';
				}
				//로그인 상태인 경우
				message_socket.send('msg');
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		}); //end of ajax
		
		$('.image-form-bg').hide();
		$('.image-form').hide();
		
	}); //end of submit image
	
	/*=======================
		 이미지 크게 보기
	=========================*/
	$('.chat-body').on('click','.msg-image',function(){
		
		let image = '<img src="';
		let image_src = $(this).attr('src');
		
		image += image_src;
		image += '">';
		
		$('#msg_image').html(image);
		
		$('.msg-image-modal').show();
		$('.msg-image-modal-bg').show();
		
	});
	
	$('.msg-image-modal .close-button').click(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('닫기 버튼 클릭 이벤트 발생');
		
		
		$('.msg-image-modal-bg').hide();
		$('.msg-image-modal').hide();
	});
	/*=======================
		  채팅 종료 폼 노출
	=========================*/
	$(document).on('click','#chat_close',function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('버튼 클릭 이벤트 발생');
		
		$('#close_chat_num').val(chat_num);
		$('.close-file-form-bg').show();
		$('.close-file-form').css('display','block');
		
	});
	
	//모달 취소 버튼 클릭
	$('.close-file-form').on('click','#close_cancel',function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('취소 버튼 클릭 이벤트 발생');
		
		
		$('.close-file-form-bg').hide();
		$('.close-file-form').hide();
	});
	
	//모달 창 생성 시 외부 클릭 이벤트 삭제
	$('.close-form').click(function(event) {
		event.stopPropagation();
	});
	
	/*=======================
		 채팅 종료 폼 파일 전송
	=========================*/
	$('#file_input').submit(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		
		let form_data = new FormData(this);
		
		let form = this;
		
		if($('#select_file').val().trim()==''){
			//파일을 등록하지 않은 경우
			alert('파일을 등록해주세요.');
			return false;
		}else if($('#file_valid_date').val().trim()==''){
			//유효기간을 등록하지 않은 경우
			alert('유효기간을 등록해주세요.');
			return false;
		}
		
		$.ajax({
			url:'/chat/chatClose',
			type:'post',
			data:form_data,
			contentType: false,  // 파일 업로드시 필요
        	processData: false,
			dataType:'json',
			success:function(param){
				if(param.valid_date=='pastDate'){
					//유효기간을 지난 날짜로 설정한 경우
					alert('유효기간은 당일 이전으로 설정할 수 없습니다.');
					$('#file_valid_date').focus();
					return false;
				}else{
					let file = '<tr>';
					file += '	<td>'+param.file_name+'</td>';
					file += '	<td>'+param.file_type+'</td>';
					file += '	<td>'+param.valid_date+'</td>';	
					file += '	<td><button type="button" class="list-delete" data-file_num='+param.file_num+'>&times;</button></td>'
					file += '</tr>';

				$('#file_table').append(file);
				}
				
				console.log('파일 요소 추가 후, 폼 초기화 전'); 
								
				form.reset();
				// 파일 입력 초기화
       			$('#file_input').val('');
	           },
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	
	/*=======================
		채팅 종료 폼 파일 삭제
	=========================*/
	$('#file_table').on('click', '.list-delete', function(){
		//기본 이벤트 제거
		//event.preventDefault();
		
		console.log('파일 삭제 클릭 이벤트 발생');
        //x 표시가 속하는 file_num 가져오기
        let file_num = $(this).data('file_num');
        
        console.log('file_num :'+file_num);
		
		$.ajax({
			url:'/chat/deleteFile',
			type:'post',
			data:{'file_num':file_num},
			dataType:'json',
			success:function(param){
				if(param.result=='success'){
					console.log(file_num+'번 파일 삭제 성공');
				    let deleteRow = $('button[data-file_num="' + file_num + '"]').parent().parent();
				    console.log('삭제 후 파일번호 확인: '+file_num);
				    console.log('삭제 대상 행: '+deleteRow.length); 
				   
				    deleteRow.remove();
				}else{
					console.log(file_num+'번 파일 삭제 실패');
					alert('파일 삭제에 실패했습니다.');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	
	
	});
	
	/*=======================
		 진료비 청구 폼 호출
	=========================*/
	$(document).on('click','#close_file_next',function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('버튼 클릭 이벤트 발생');
		
		$('.close-file-form-bg').hide();
		$('.close-file-form').hide();
		
		$('#close_chat_num').val(chat_num);
		$('.close-payment-form-bg').show();
		$('.close-payment-form').css('display','block');
		
	}); //end of click
	
	//모달 창 닫기 버튼 클릭
	$('.close-payment-form .close-button').click(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('닫기 버튼 클릭 이벤트 발생');
		
		
		$('.close-payment-form-bg').hide();
		$('.close-payment-form').hide();
	});
	
	//모달 창 생성 시 외부 클릭 이벤트 삭제
	$('.close-form').click(function(event) {
		event.stopPropagation();
	});
	
	/*=======================
		 진료비 청구 폼 제출
	=========================*/
	$('.close-payment-form').on('click','#close_submit',function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		console.log('종료 폼 전송 이벤트 발생');
		
		if($('#pay_amount').val()==''){
			alert('진료비를 입력하세요.');
			return false;
		}
		
		chat_num = $('#close_chat_num').val();
    	pay_amount = $('#pay_amount').val();
    
    	const form_data = {
        	chat_num: chat_num,
        	pay_amount: pay_amount
    	};
    	
		$.ajax({
			url:'/chat/requestPayment',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'success'){				
					selectChat();
					console.log('전송된 메시지:', param.paymentNotice);
					$('.close-payment-form-bg').hide();
					$('.close-payment-form').hide();
					message_socket.send('msg');
				}else if(param.result == 'fail'){
					alert('채팅 정보 로드 오류 발생');
				}else{
					alert('종료 처리 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		}); //end of ajax
	}); // end of close_submit
	
	//이전으로 버튼 클릭
	$('.close-payment-form').on('click','#close_previous',function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		$('.close-payment-form-bg').hide();
		$('.close-payment-form').hide();
		$('#close_chat_num').val(chat_num);
		$('.close-file-form-bg').show();
		$('.close-file-form').css('display','block');
	});
	
	
	/*=======================
		 	나의 서류함
	=========================*/
	$('.file-room').click(function(event){
		event.preventDefault();
		
		console.log('채팅방 선택 이벤트 발생');
		
		chat_num = $(this).data('chat-num');
        res_date = $(this).data('res-date');
        res_time = $(this).data('res-time');
        res_num = $(this).data('res-num');
        
        console.log(chat_num+','+res_date+','+res_time+','+res_num);
        
        const selected = $(this).parent().parent();
        const not_selected = $('.file-room').parent().parent();
        console.log(selected);
        
        not_selected.removeClass('selected-chat chat-nav-selected-bg');
        selected.addClass('selected-chat chat-nav-selected-bg');
        
		$('#chat_num').val(chat_num);
		$('#res_date').val(res_date);
		$('#res_time').val(res_time);
		$('#res_num').val(res_num);
		
		let res_title = '';
		
		res_title += '			<div class="res-title">'
		res_title += '			<div class="chat-title fs-25 fw-8" id="chat_title">';
		res_title += '				예약번호: '+res_num;
		res_title += '			</div>';
		res_title += '			<div class="chat-date fs-20">';
		res_title += '				'+res_date+'  '+res_time;
		res_title += '			</div>';
		res_title += '		</div>';

		$('#file_header').html(res_title); 

		let fileList = '';
		
		$.ajax({
			url:'/chat/fileDetail',
			type:'GET',
			data:{chat_num:chat_num},
			dataType:'json',
			success:function(param){
				if(param.userCheck=='logout'){
					//로그아웃 상태인 경우, 메인으로 이동
					alert('로그인 후 이용해주십시오');
					window.location.href='/main/main';
				}
				
				if(param.list == 'null'){
					//해당 채팅방 파일 목록이 없는 경우
					fileList+='<div class="close-room">';
					fileList+='		<img src="../images/icon/my_files.png" width="60px" height="60px" class="chat-bubble">';
					fileList+='		<span class="fs-21 chat-notice fw-8">해당 채팅방에 열람할 자료가 없습니다.</span>';
					fileList+='</div>';
				}else{
					//해당 채팅방 파일 목록이 있는 경우
					$(param.list).each(function(index,item){
						fileList+='<table class="file-table">';
						fileList+='	<tr class="list-head bg-gray-3">';
						fileList+='					<th>진료의사</th>';
						fileList+='					<th>서류유형</th>';
						fileList+='					<th>발급일자</th>';
						fileList+='					<th>유효일자</th>';
						fileList+='	</tr>';
						fileList+='	<tr>';
						fileList+='					<td>'+item.mem_name+'</td>';
						if(item.file_type == 0){ //파일 유형이 처방전
							fileList+='					<td>처방전</td>';
						}else if(item.file_type == 1){ //파일 유형이 소견서
							fileList+='					<td>소견서</td>';
						}else if(item.file_type == 2){ //파일 유형이 진단서
							fileList+='					<td>진단서</td>';
						}else if(item.file_type == 3){ //파일 유형이 진료비 세부내역서
							fileList+='					<td>진료비 세부내역서</td>';
						}
						fileList+='					<td>'+item.file_reg_date+'</td>';
						fileList+='					<td>'+item.file_valid_date+'</td>';
						fileList+='	</tr>';
						fileList+='	<tr>';
						fileList+='		<td class="btn-td" colspan="4">';
						fileList+='			<button type="button" class="btn-chat" onclick="location.href=\'/chat/downloadFile?file_num='+item.file_num+'\'">다운로드</button>';
						fileList+='		</td>';
						fileList+='	</tr>';
						fileList+='</table>';
					}); //end of list each
				}//end of else
				$('.file-body').html(fileList);
			}, // end of success
			error:function(){
				alert('네트워크 오류 발생');
			}
		}); //end of ajax
		
	});//end of click file-room
	
	$(document).on('click','#chat_payment',function requestPay(event){
	//기본 이벤트 제거
	event.preventDefault();
	
	var IMP = window.IMP;
	IMP.init("imp21212228"); //고객사 식별코드로 SDK 초기화
	
	chat_num = $(this).data('chat_num');
	pay_amount = $(this).data('pay_amount');
	
	console.log('결제 대금: '+pay_amount);
	console.log('결제 채팅방 번호: '+chat_num);
	
	//서버와 통신
	$.ajax({
		url:'/chat/chatPayment',
		type:'get',
		data:{chat_num:chat_num},
		dataType:'json',
		success:function(param){
			mem_phone = param.payment.mem_phone;
			mem_num = param.payment.mem_num;
			doc_name = param.doc_name;
				
			console.log('mem_phone:'+mem_phone);
			console.log('chat_num:'+chat_num);
			console.log('mem_num:'+mem_num);
			console.log('doc_name:'+doc_name);
                   
			IMP.request_pay(
			{
				pg: 'html5_inicis',		//KG이니시스 pg파라미터 값
				pay_method: 'card',		//결제 방법
				merchant_uid: chat_num,//주문번호
				name: doc_name,				//상품 명
				amount: pay_amount,			//금액
				buyer_name: mem_num,
				buyer_tel: mem_phone,
			},
			function (rsp) { // 결제 시 콜백 함수
				if (rsp.success) {
               		// 결제 성공 시 서버에 추가 요청
               		console.log('결제 성공 후 chat_num:'+chat_num);
               		console.log('결제 성공 후 doc_name:'+doc_name);
               		console.log('결제 성공 후 pay_amount:'+pay_amount);
               		console.log('결제 성공 후 mem_num:'+mem_num);
               		
               		$.ajax({
                   		url:'/chat/paymentConfirmation',
                   		type:'post',
                   		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
                   		data:{
                       		chat_num:chat_num,
                       		doc_name:doc_name,
                       		pay_amount:pay_amount,
                       		mem_num:mem_num
                   		},
                   		dataType: 'json',
                   		success: function(param) {
							if(param.result == 'paySuccess'){
                       			 var alertType = 'success';

						        Swal.fire({
						        	title: '<div style="font-weight:700; font-size: 17px; color: #4a4a4a;">결제가 완료되었습니다.</div>',
						            icon: alertType,// 알림 아이콘 (success, error, warning, info)
						            confirmButtonText: '확인',
						            confirmButtonColor: "#41A652",
						            cancelButtonColor: "#E60634"
						        }).then((result) => {
						            if (result.isConfirmed) {
						                // 확인 버튼 클릭 시 리다이렉트
						            }
						        });
						        
                       			$('#chat_close').css('display','none');
                       			$('.chat-input input').prop('disabled', true);
								$('.chat-input button').prop('disabled', true);
								
                       			console.log('서버 응답:', param);
                       			message_socket.send('msg');      
                       		}else if(param.result == 'fail'){
								alert("결제에 실패했습니다. 예외 발생");
							}else{
								alert("결제에 실패했습니다. 알 수 없는 예외 발생");
							}
                   		},
                       	error: function(xhr, status, error) {
                           	alert('결제 완료 후 서버 통신 오류');
                           	console.log(xhr.responseText);
                           	console.log('Ajax Error:', status, error);
                       	}
                   }); //end of callback ajax
               } else {
                   alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
               }
           }); //end of callback > end of request_pay
			}, //end of success
			error:function(xhr, status, error){
				alert('네트워크 오류 발생');
				console.log(xhr.responseText);
				console.log('Ajax Error:', status, error)
			}
		}); //end of click event ajax

	}); //end of click event	
});