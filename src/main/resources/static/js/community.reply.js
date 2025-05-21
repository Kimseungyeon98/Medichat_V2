$(function(){
	let rowCount = 20;
	let currentPage;
	let count;
	
	/*==============================댓글==============================*/
	/*-----댓글 목록-----*/
	function selectList(pageNum){
		currentPage = pageNum;
		console.log("<js ajax 호출 전>")
		$.ajax({
			url:'/medichatCommunity/listComment',
			type:'get',
			data:{cbo_num:$('#cbo_num').val(),pageNum:pageNum,rowCount:rowCount},
			dataType:'json',
			success:function(param){
				count = param.count;
				console.log("<<댓글목록>>")
				
				if(pageNum == 1){//처음호출시 
					$('#output').empty();
				}
				console.log("<<댓글 목록 데이터>>", param.list); //서버로부터 받은 댓글 목록 배열
				console.log("<<댓글 목록 데이터 - user_num>>", param.user_num);

				//댓글수 읽어 오기
				displayReplyCount(param.count);
				console.log("댓글수"+param.count);
				
				$(param.list).each(function(index,item){
					console.log("<<댓글 목록 mem_num>>"+item.mem_num);
					console.log("<<댓글 목록 item>>"+item.med_id);
					console.log("<<댓글 목록 level>> : " + item.cre_level);
					
					//처음에는 보여지지 않고 다음 댓글부터 수평선에 보이게 처리
					if(index>0) $('#output').append('<hr size="1" width="100%">'); 
					
					//댓글 좋아요
					let fav_cnt = 0;
					if(item.refav_cnt!=0) fav_cnt = item.refav_cnt;
					//댓글 좋아요
					
					let output = '<div class="item cboard-comment">';
					output += '     <ul class="detail-info cboard-info">';
					output +='        <li>';
					output += '         <img src="../member/memViewProfile?mem_num='+item.mem_num+'" width="40" height="40" class="profile">';
					output +='        </li>';
					output +='        <li class="cboard-profile">';
					output += item.mem_id + '<br>';
					output += '<span class="modify-date">' + item.cre_rdate + '</span>';
					output +='        </li>';
					
					output += '<li id="cbo_reply_info">'
					output +='     <div class="cboard-sub-item">';
					//신고기능을 추가할 경우 dropdown div를 밖으로 빼내기
					if(param.user_num==item.mem_num){
						//로그인 한 회원번호와 댓글 작성자 회원번호가 같으면
						
						output += '<div class="dropdown" id="re-dropdown">'
						output += '<img src="../images/icon/dots.png" width="20" id="dropdownToggle_re">'
						output += '<ul class="dropdown-menu">'
						output += '<li><a class="dropdown-btn modify-btn" data-num="'+item.cre_num+'">수정</a></li>'
						output += '<li><hr></li>'
						output += '<li><a class="dropdown-btn delete-btn" data-num="'+item.cre_num+'">삭제</a></li>'
						output += '</ul>'
						output += '</div>'
						output += '</li>'
					}
					output +='     </ul>';
					output += '    <p>' + item.cre_content.replace(/\r\n/g,'<br>') + '</p>';
					
					/*---좋아요 시작---*/
					/*if(item.click_num==0 || param.user_num!==item.click_num){
						output += ' <img class="output_rfav" src="../images/cboard-fav01.png" width="15" data-num="'+item.cre_num+'"> <span class="output_rfcount">'+item.refav_cnt+'</span>';
					}else{
						output += ' <img class="output_rfav" src="../images/cboard-fav02.png" width="15" data-num="'+item.cre_num+'"> <span class="output_rfcount">'+item.refav_cnt+'</span>';
					}*/
					/*---좋아요 끝---*/
					
					//답글이 있을 경우에만 버튼이 보여지도록 처리 변경 필요
					//output += '  <div><input type="button" data-level="1" data-ref="'+item.cre_num+'" value="답글목록" class="rescontent-btn"></div>';
					output += '<a class="rescontent-btn" data-status="0" data-level="1" data-ref="'+item.cre_num+'">💬답글목록</a>&nbsp;&nbsp;';
					/*---답글 시작---*/
					console.log("답글수 : "+param.resp_cnt)
					if(param.user_num){
						//output += '<input type="button" data-parent="'+item.cbo_num+'"data-level="1" data-ref="'+item.cre_num+'" value="답글" class="reply-btn">'
						output += '<a class="reply-btn text-black-6" data-parent="'+item.cbo_num+'"data-level="'+(item.cre_level+1)+'" data-ref="'+item.cre_num+'">답글작성</a><br>'
					}
					/*---답글 끝---*/
					
					output += '  </div>';
					output += '  <div class="replies"></div>'; // 답글이 추가될 부분
					output += '</div>';
					
					//문서 객체에 추가
					$('#output').append(output);
				});
				
				console.log("currentPage:"+currentPage);
				console.log("count:"+count);
				console.log("rowCount:"+rowCount);
				
				//paging button 처리
				if(currentPage>=Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				alert('목록 네트워크 오류');
			}
		});//end of ajax
	}
	
	//수정/삭제 드롭다운
	$(document).on('click','#dropdownToggle_re',function(event){
		  event.stopPropagation();
        $(this).siblings('.dropdown-menu').toggle();
	});
	
	
	//다음 댓글 보기 버튼 클릭시 데이터 추가
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	
	/*----------댓글 등록----------*/
	$('#comment_form').submit(function(event){
		event.preventDefault();//ajax 통신위해 기본이벤트 제거
		
		if($('#cre_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#cre_content').val('').focus();
			return false;
		}
		
		let form_data = $(this).serialize();
		console.log("<<댓글등록 form_data>>:"+ form_data);
		
		$.ajax({
			url:'writeComment',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('댓글을 작성하려면 로그인 해주세요');
					//로그인창 이동
				}else if(param.result == 'success'){
					initForm(); //폼 초기화
					selectList(1);
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error:function(){
				alert('댓글 등록 네트워크 오류 발생');
			}
		});
	});
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('0/300');
	}
	
	/*----------댓글 수정----------*/
	$(document).on('click','.modify-btn',function(){
		let cre_num = $(this).attr('data-num');
		console.log("댓글번호:"+cre_num);
		let cre_content = $(this).parents('.item').find('p').html().replace(/<br>/gi, '\r\n');

		console.log("댓글내용:"+cre_content);
		
		//댓글 수정폼 UI
		let modifyUI = '<form id="mcomment_form">';
		modifyUI += '   <input type="hidden" name="cre_num" id="mcre_num" value="'+cre_num+'">';
		modifyUI += '   <textarea rows="7" cols="110" name="cre_content" id="mcre_content" class="reply-textarea">'+cre_content+'</textarea>';
		
		modifyUI += '   <div id="mcre_first"><span class="letter-count">0/300</span></div>';      
		modifyUI += '   <div id="mcre_second">';
		modifyUI += '      <input type="submit" class="modify-submit-btn" value="수정">';
		modifyUI += '      <input type="button" value="취소" class="cre-reset modify-default-btn">';
		modifyUI += '   </div>';
		modifyUI += '</form>';
		
		//열어둔 수정댓글 존재시 숨김처리
		initModifyForm(); //폼초기화
		
		$(this).parents('.item').find('p').hide(); //p 태그 숨기기
    	$(this).parents('.item').find('.cboard-sub-item').hide(); //댓글 수정 버튼 및 기타 정보를 숨김
		
		//수정폼 노출
		 $(this).closest('.item').append(modifyUI);
		
		//입력한 글자수 셋팅
		let inputLength = $('#mcre_content').val().length;
		let remain = inputLength + '/300';
		
		//문서 객체에 반영
		$('#mre_first .letter-count').text(remain);
	});
	
	//수정폼에서 취소 버튼 클릭시 수정폼 초기화
	$(document).on('click','.cre-reset',function(){
		initModifyForm();
	});
	
	//댓글 수정 폼 초기화
	function initModifyForm(){
		$('.cboard-sub-item').show();	//댓글 보여지게 처리
		$('.item p').show();//숨긴 p태그 보여지게 처리
		$('#mcomment_form').remove(); //수정폼 숨기기
		$('.dropdown-menu').hide(); //드롭다운 메뉴 숨기기
	}
	
	//댓글 수정 전송
	$(document).on('submit','#mcomment_form',function(event){
		event.preventDefault();
		
		if($('#mcre_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#mcre_content').val('').focus();
			return false;
		}
		
		let form_data = $(this).serialize(); //폼에 입력한 데이터 반환
		
		$.ajax({
			url:'updateComment',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 수정할 수 있습니다')
				}else if(param.result == 'success'){
					//수정 데이터 표시
					$('#mcomment_form').parent().find('p').html($('#mcre_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\r\n/g,'<br>').replace(/\r/g,'<br>').replace(/\n/g,'<br>'));
					
					initModifyForm();
				}else if(param.result == 'wrongAccess'){
					alert('타인의 글은 수정할 수 없습니다');
				}else{
					alert('댓글 수정 오류 발생');
				}
			},
			error:function(){
				alert('댓글 수정 네트워크 오류 발생');
			}
		});
	});	
	
	/*----------댓글 삭제----------*/
	$(document).on('click','.delete-btn',function(){
		let cre_num = $(this).attr('data-num');
		
		if (confirm('댓글을 삭제하시겠습니까?')) {
			$.ajax({
				url:'deleteComment',
				type:'post',
				data:{cre_num:cre_num},
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인해야 삭제할 수 있습니다')
					}else if(param.result == 'success'){
						selectList(1);
					}else if(param.result == 'wrongAccess'){
						alert('타인의 글은 삭제할 수 없습니다');
					}else{
						alert('댓글 삭제 오류 발생');
					}
				},
				error:function(){
					alert('댓글 삭제 네트워크 오류 발생');
				}
			});
		}
	});
	
	/*---------------댓글수 표시---------------*/
	function displayReplyCount(count){
		let output;
		if(count>0){
			output = '💬 '+count;
		}else{
			output = '💬 0';
		}			
		//문서 객체에 추가
		$('#output_rcount').text(output);
	}
	
	/*---------------댓글 좋아요---------------*/
	;
	/*---------------댓글/답글 등록, 수정 공통---------------*/
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 체크
		let inputLength = $(this).val().length;
		
		if(inputLength > 300){
			$(this).val($(this).val().substring(0,300));
		}else{//300자 미만
			let remain = inputLength + '/300';
			
			if($(this).attr('id')=='cre_content'){
				$('#re_first .letter-count').text(remain);
			}else if($(this).attr('id')=='mcre_content'){
				$('#mcre_first .letter-count').text(remain)
			}else if($(this).attr('id')=='mrcre_content'){
				$('#resp_first .letter-count').text(remain)
			}else{
				$('#mresp_first .letter-count').text(remain);
			}
			//답글 등록,수정 추가 필요
		}
	});
	
	
	/*==============================답글==============================*/
	/*----------답글 목록----------*/
	function getListReply(cre_num, replyUI) {
        // 서버와 통신하여 답글 목록을 가져옵니다.
        $.ajax({
            url: 'listReply',
            type: 'get',
            data: {cre_num: cre_num},
            dataType: 'json',
            success: function(param) {
                replyUI.find('.respitem').remove();	//기존 답글 제거
                
                let output = '';
                $(param.list).each(function(index, item) {
					console.log("답글 깊이 : " + item.cre_level);
					console.log("답글 부모 id : " + item.parent_id);
					
					//처음에는 보여지지 않고 다음 답글부터 수평선에 보이게 처리
					output += '<hr size="1" width="100%">';
					
					let sign_depth = '@';
										
					output += '<div class="respitem">';
					output += ' <ul class="detail-info cboard-info">';
					output +='    <li>';
					
					output += '<img src="../member/memViewProfile?mem_num='+item.mem_num+'" width="40" height="40" class="reply-profile">';
					output += '<input type="hidden" name="cre_num" id="mcre_num" value="'+item.cre_num+'">';
					output += '</li>';
					output += '<li>';
					
					output += item.mem_id+'<br>'
					if(item.cre_mdate){
						output += '<span class="modify-date">'+item.cre_mdate+'</span>';
					}
					output += '<span class="modify-date">'+item.cre_rdate+'</span>';
					output += '</li>';
					
					if(param.user_num==item.mem_num){//로그인 한 회원번호와 답글 작성자 회원번호가 일치할 경우
						output += '<div class="dropdown resp-dropdown">'
						output += '<img src="../images/icon/dots.png" width="20" id="dropdownToggle_re">'
						output += '<ul class="dropdown-menu">'
						output += '<li><a class="dropdown-btn remodify-btn" data-num="'+item.cre_num+'">답글수정</a></li>'
						output += '<li><hr></li>'
						output += '<li><a class="dropdown-btn redelete-btn" data-num="'+item.cre_num+'">답글삭제</a></li>'
						output += '</ul>'
						output += '</div>'
					}
					
					
					output += '</ul>';
					output += '<div class="resp-sub-item">';
					
					output += '<p>';
					if(item.cre_level > 1){
						output += '<b> '+sign_depth+item.parent_id+'</b> ';
					}
					output += item.cre_content.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\r\n/g,'<br>').replace(/\r/g,'<br>').replace(/\n/g,'<br>') + '</p>';
					if(param.user_num){
						output += ' <a class="reply2-btn text-black-6" data-ref="'+item.cre_num+'" data-parent="'+item.cbo_num+'" data-level="'+(item.cre_level+1)+'">답글작성</a>';
					}
					output += '</div>';
					output += '</div>';
					
                });
                
                //답글노출
                replyUI.append(output);
                
            },
            error: function() {
                alert('답글 목록 네트워크 오류 발생');
            }
        });
    }//end of getListReply
	
	//답글 노출/숨김 버튼 이벤트 처리
	$(document).on('click','.rescontent-btn',function(){
		let cre_num = $(this).attr('data-ref');
		console.log("rescontent-btn 클릭됨. cre_num:", cre_num);
		if($(this).attr('data-status')==0){
			$(this).attr('data-status',1);
			getListReply(cre_num,$(this).parent());//.cboard-sub-item	
		}else{
			$(this).attr('data-status',0);
			$(this).parents('.item').find('.respitem').remove();
			$(this).parents('.item').find('hr').remove(); // hr 태그 제거
		}
	});
	
	/*----------답글 등록----------*/
	//output += '<input type="button data-num"'+item.cre_num+'"data-parent="'+item.cbo_num+'"data-level="1" data-ref="" value="답글" class="reply-btn">'
	$(document).on('click','.reply-btn,.reply2-btn',function(){
		initReplyForm(); //모든 폼 초기화
		$(this).hide();
		
		let cbo_num = $(this).attr('data-parent');
		let cre_level = $(this).attr('data-level');
		let cre_ref = $(this).attr('data-ref');
		console.log('<<답글등록>> cbo_num:'+cbo_num+','+'cre_level:'+cre_level+','+'cre_ref:'+cre_ref);
		
		//답글 작성 폼 UI
		let replyUI = '<form id="reply_form">'
		replyUI += '<input type="hidden" name="cbo_num"  value="'+cbo_num+'">';
		replyUI += '<input type="hidden" name="cre_level" value="'+cre_level+'">';
		replyUI += '<input type="hidden" name="cre_ref" id="resp_num" value="'+cre_ref+'">';
		replyUI += '<textarea rows="7" cols="110" name="cre_content" id="mrcre_content" class="reply-textarea"></textarea>';
		replyUI += '<div id="resp_first"><span class="letter-count">0/300</span></div>';
		replyUI += '<div id="resp_second">';
		replyUI += '<input type="submit" class="reply-submit-btn" value="등록">';
		replyUI += '<input type="button" value="취소" class="resp-reset rely-default-btn">';
		replyUI += '</div>';
		replyUI += '</form>';
		
		//답글 작성폼을 답글을 작성하고자는 데이터가 있는 div에 노출
		$(this).after(replyUI);
	});
	
	//답글에서 취소 버튼 클릭시 답글 폼 초기화
	$(document).on('click','.resp-reset',function(){
		initReplyForm();
	});
	
	//답글 작성 폼 초기화
	function initReplyForm(){
		$('.reply-btn, .reply2-btn').show();
		$('#reply_form').remove();
		$('.dropdown-menu').hide(); //드롭다운 메뉴 숨기기
	}
	
	//답글 등록
	$(document).on('submit','#reply_form',function(event){
		let resp_form = $(this);
		event.preventDefault();
		
		if($('#mrcre_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#mrcre_content').val('').focus();
			return false;
		}
		
		let form_data = $(this).serialize();
		let cre_num = $(this).find('#resp_num').val();
		$.ajax({
			url:'writeReply',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				
				if(param.result == 'logout'){
					alert('로그인해야 답글을 작성할 수 있습니다');
				}else if(param.result == 'success'){
					resp_form.parent().find('a.rescontent-btn').attr('data-status',1);//토글을 강제로 열면서 status를 1로 변경
					getListReply(cre_num,resp_form.parent());//.item에 접근
					initReplyForm();
				}else{
					alert('답글 작성 오류 발생');
				}
			},
			error:function(){
				alert('답글 등록 네트워크 오류 발생');
			}
		});
	});
	/*----------답글 수정----------*/
	$(document).on('click','.remodify-btn',function(){
		let cre_num = $(this).attr('data-num');
		//let cre_content = $(this).parent().find('p').html().replace(/<br>/gi,'\r\n');
		let cre_content = $(this).closest('.respitem').find('p').html().replace(/<br>/gi, '\r\n');
		console.log("답글수정내용 : "+cre_content);
		
		let replyUI = '<form id="mresp_form">'
			replyUI += '   <input type="hidden" name="cre_num" id="mresp_num" value="'+cre_num+'">';
			replyUI += '   <textarea rows="7" cols="110" name="cre_content" id="mresp_content" class="rep-content reply-textarea">'+cre_content+'</textarea>';			
			replyUI += '   <div id="mresp_first"><span class="letter-count">300/300</span></div>';      
			replyUI += '   <div id="mresp_second" class="align-right">';
			replyUI += '      <input type="submit" class="morely-submit-btn" value="수정">';
			replyUI += '      <input type="button" value="취소" class="mresp-reset moreply-default-btn">';
			replyUI += '   </div>';
			replyUI += '</form>';
			
		
		//이전에이미 수정하는 답글이 있을 경우 수정버튼을 클릭하면
		//숨김 resp-sub-item를 환원시키고 수정폼을 초기화함
		initReplyModifyForm();
		//지금 클릭해서 수정하고 하는 데이터는 감추기
		$(this).parents('.respitem').find('.resp-sub-item').hide();
		
		//수정폼을 수정하고자하는 데이터가 있는 div에 노출
		$(this).parents('.respitem').append(replyUI);
		
		
		//입력한 글자수 셋팅
		let inputLength = $('#mresp_content').val().length;
		let remain = inputLength + '/300';
		
		//문서 객체에 반영
		$('#mresp_first .letter-count').text(remain);
		
	});
	
	//답글 수정 폼 초기화
	function initReplyModifyForm(){
		$('.resp-sub-item').show();
		$('#mresp_form').remove();
	}
	//답글 수정 취소 버튼 클릭시 답글 폼 초기화
	$(document).on('click','.mresp-reset',function(){
		initReplyModifyForm();
	});
	
	//답글수정
	$(document).on('submit','#mresp_form',function(){
		event.preventDefault();
		if($('#mresp_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#mresp_content').val('').focus();
			return false;
		}
		//폼에 입력한 데이터 반환
		let form_data = $(this).serialize();
		$.ajax({
			url:'updateComment',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 수정할 수 있습니다.');
				}else if(param.result=='success'){
                    $('#mresp_form').parent().find('p')
				      .html($('#mresp_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\r\n/g,'<br>').replace(/\r/g,'<br>').replace(/\n/g,'<br>'));
					
					//최근 수정일 처리
					//$('#mresp_form').parent().find('.modify-date') .text('최근 수정일 : 5초미만');
					
					//수정 폼 초기화
					initReplyModifyForm();                        
				}else if(param.result=='wrongAccess'){
					alert('타인의 글은 수정할 수 없습니다.');
				}else{
					alert('답글 수정 오류 발생');
				}
			},
			error:function(){
				alert('답글 수정 네트워크 오류 발생');
			}
		});
	});
	
	/*----------답글 삭제----------*/
	$(document).on('click','.redelete-btn',function(){
		let cre_num = $(this).attr('data-num');
		
		if (confirm('댓글을 삭제하시겠습니까?')) {
			$.ajax({
				url:'deleteComment',
				type:'post',
				data:{cre_num:cre_num},
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인해야 삭제할 수 있습니다')
					}else if(param.result == 'success'){
						selectList(1);
					}else if(param.result == 'wrongAccess'){
						alert('타인의 글은 삭제할 수 없습니다');
					}else{
						alert('댓글 삭제 오류 발생');
					}
				},
				error:function(){
					alert('댓글 삭제 네트워크 오류 발생');
				}
			});
		}
	});
	
	/*==============================초기 데이터(목록) 호출==============================*/
	selectList(1);
});
	
