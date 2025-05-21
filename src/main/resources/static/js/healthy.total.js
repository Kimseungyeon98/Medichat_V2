$(function(){
let pageNum;
let count;
let rowCount=5;
//댓글 목록처리랑 답글보기해야됨...


//글쓰기 글수정 공통
$('#register_form').submit(function(){
	if($('#healthy_title').val().trim()==''){
		alert('제목을 입력하세요');
		$('#healthy_title').val('').focus();
		return false;
		
	}
	if($('#healthy_content').val().trim()==''){
		alert('내용을 입력하세요');
		$('#healthy_content').val('').focus();
		return false;
		
	}
});


	$('#hFav').click(function(){
		let healthy_num = $(this).attr('data-num');
			$.ajax({
				url:'clickHFav',
				type:'post',
				data:{healthy_num:healthy_num},
				dataType:'json',
				success:function(param){
					if(param.result =='logout'){
						$('#Give-It-An-Id').prop('checked',false);
						alert('로그인 후 좋아요 가능');

					}else if(param.result=='success'){
						if(param.count == null){
							count=0;
						}else{
							count = param.count
						}

						if(param.status =='noFav'){
							$('#Give-It-An-Id').attr('checked',false);
							$('#hfav_cnt').text(count);

						}else {
							$('#Give-It-An-Id').attr('checked',true);
							$('#hfav_cnt').text(count);
						}
					}else{
						alert('좋아요 클릭 오류');

					}

				},
				error:function(){
					alert('네트워크 오류');
				}
			});		

	});//게시글 좋아요 끝


//댓글 입력
$('#hreWrite').submit(function(event){

	if($('#hre_content').val().trim()==''){
		alert('내용을 입력해주세요');
		$('#hre_content').val('').focus();
		return false;
	}

		if($('#hre_content').val().length >300){
		alert('300자까지 입력가능합니다.');
		$('#hre_content').val($('#hre_content').val().substring(0,300)).focus();
		return false;
	}

	let form_data = $(this).serialize();

	$.ajax({
		url:'insertHreply',
		type:'post',
		data:form_data,
		dataType:'json',
		success:function(param){
			if(param.result=='logout'){
				alert('로그인 후 댓글 작성 가능');
			}else if(param.result=='success'){
				 selectReply(1);	
				$('#hre_content').val('');
			}else{
				alert('댓글 쓰기 오류');
			}
		},
		error:function(){
			alert('네트워크 오류');
		}
	})
	event.preventDefault();
});


//댓글 더불러오기해야됨
function selectReply(currentNum){
	pageNum= currentNum;
	$.ajax({
		url:'selectHReply',
		type:'post',
		data:{healthy_num:$('#replyList').attr('data-num'),pageNum:pageNum},
		dataType:'json',
		success:function(param){
				if(param.count==null){
					count =0;
				}else{
					count = param.count;
				}
				if(pageNum == 1){
					//처음 호출시는 해당 ID의 div의 내부 내용물을 제거
					$('#replyList').empty();
				}
				
				$(param.list).each(function(index,item){
					let output ='';
					output +='<div class="items">';
					output +='<ul>';
					output+='<li><img src="../member/memViewProfile?mem_num='+item.mem_num+'" width="35" height="35" class="rounded-circle"> &nbsp;&nbsp; '+item.id+'</li>';
					if(item.hre_modify_date){
						output+='<li> 수정일 : '+item.hre_modify_date+'</li>';
					}else{
						output+='<li> 등록일 : '+item.hre_reg_date+'</li>';
					}
					output +='</ul>';
					output+='<p>'+item.hre_content+'<p><br>';
							output +='<div class="hrefav" style="width:20px; margin-bottom:20px;" data-num="'+item.hre_num+'">';
					output+='<div title="Like" class="heart-container" >';
					if(item.click_num == $('#user_num').val()&& item.click_num !=0&& $('#user_num').val()!=0){
					output+='<input id="'+item.hre_num+'" data-id="'+item.id+'"  class="checkbox" type="checkbox" checked="checked">';}

					else{
					output+='<input  id="'+item.hre_num+'" data-id="'+item.id+'"  class="checkbox" type="checkbox">';
					}
					output+='<div class="svg-container" style="width:25px;  float:left;">';
					output+='<svg style="width:20px; margin-bottom:25px;" xmlns="http://www.w3.org/2000/svg" class="svg-outline" viewBox="0 0 24 24">';
					output+='<path  d="M17.5,1.917a6.4,6.4,0,0,0-5.5,3.3,6.4,6.4,0,0,0-5.5-3.3A6.8,6.8,0,0,0,0,8.967c0,4.547,4.786,9.513,8.8,12.88a4.974,4.974,0,0,0,6.4,0C19.214,18.48,24,13.514,24,8.967A6.8,6.8,0,0,0,17.5,1.917Zm-3.585,18.4a2.973,2.973,0,0,1-3.83,0C4.947,16.006,2,11.87,2,8.967a4.8,4.8,0,0,1,4.5-5.05A4.8,4.8,0,0,1,11,8.967a1,1,0,0,0,2,0,4.8,4.8,0,0,1,4.5-5.05A4.8,4.8,0,0,1,22,8.967C22,11.87,19.053,16.006,13.915,20.313Z">';
					output+='</path></svg><svg style="width:20px; margin-bottom:25px;" xmlns="http://www.w3.org/2000/svg" class="svg-filled" viewBox="0 0 24 24"><path d="M17.5,1.917a6.4,6.4,0,0,0-5.5,3.3,6.4,6.4,0,0,0-5.5-3.3A6.8,6.8,0,0,0,0,8.967c0,4.547,4.786,9.513,8.8,12.88a4.974,4.974,0,0,0,6.4,0C19.214,18.48,24,13.514,24,8.967A6.8,6.8,0,0,0,17.5,1.917Z"> </path>';
					output+='</svg><svg  style="width:10px; " xmlns="http://www.w3.org/2000/svg"  class="svg-celebrate"><polygon points="10,10 20,20"></polygon><polygon style="width:20px; margin-bottom:20px;" points="10,50 20,50"></polygon>';
					output+='<polygon style="width:20px; margin-bottom:20px;" points="20,80 30,70"></polygon><polygon  style="width:20px; margin-bottom:20px;" points="90,10 80,20"></polygon><polygon style="width:20px; margin-bottom:20px;"  points="90,50 80,50"></polygon><polygon style="width:20px; margin-bottom:20px;" points="80,80 70,70"></polygon> </svg></div>';
					output +='&nbsp; &nbsp;<span  style="width:10px;  text-align:center; margin-top:4px; padding-top:4px !important;" class="'+item.hre_num+'">'+item.refav_cnt+'</span></div>';
					output +='</div>';
					output+='<div class="align-right btnspace">';
					if(param.user_num == item.mem_num){
						output+=' <input type="button"  data-content="'+item.hre_content+'" data-num="'+item.hre_num+'" data-mem="'+item.mem_num+'" value="수정" class="modify-btn default-btn"> <input type="button"  value="삭제" data-num="'+item.hre_num+'" data-mem="'+item.mem_num+'" class="delete-btn default-btn">';	
					}
					if(param.user_num != 0)
						output+=' <input type="button"  class="re-wirte-btn default-btn" data-id="'+item.id+'" data-renum="'+item.hre_num+'" data-healnum="'+item.healthy_num+'" data-level="1"  value="답글 쓰기">';
					if(item.rereply_cnt !=0){
						output+=' <input type="button"  class="re-view-btn default-btn"data-renum="'+item.hre_num+'"  value="'+item.rereply_cnt +'개의 답글 보기">';
						output+=' <input type="button"  class="re-cancle-btn default-btn hide" data-renum="'+item.hre_num+'"  value="답글 닫기">';
					}
					output +='</div>';
					output +='</div><div id="subitems'+item.hre_num+'"></div><br><br><div class="line"></div><br>';
					$('#replyList').append(output);
				})
				
				if(pageNum>=Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
		},
		error:function(){
			alert('네트워크 오류');
		}
	})
};

	$('.paging-button').click(function(){
		selectReply(pageNum + 1);
		$(this).hide();
		$('#reply1page').show();
	});
	$('#reply1page').click(function(){
		pageNum =1;
		selectReply(pageNum);
		$(this).hide();
	});
	
	
$(document).on('click','.modify-btn',function(){
	$('#rehreWriteform').remove();
	$('#modifyform').remove();

	let output = '<div id="modifyform">';

	output+='<form  id="hreModify">';
	
	output += '<img src="../images/icon/replymodify.png" width="20px"> '+'댓글 수정중<br>';
	
	output+='<input type="hidden"  name ="hre_num" value="'+$(this).attr('data-num')+'">';

	output +='<input type="hidden" name="mem_num" value="'+$(this).attr('data-mem')+'">';

	output+='<textarea rows="5" cols="55" name="hre_content"  style="resize:none;" placeholder="300자까지 입력가능" >'+$(this).attr('data-content')+'</textarea>';

	output+=' <input type="submit" class=" default-btn" value="댓글수정"> <input type="button"  class="default-btn" id="resetbtn" value="취소"></form>';

	output+='</div>';

	$(this).parent().parent().append(output);

	$(this).parent().hide();

	$(this).parent().parent().find('p').hide();

});


$(document).on('click','#resetbtn',function(){
	$('.btnspace').show();
	$(this).parents().find('p').show();
	$(this).parents().find('#modifyform').remove();
	$('#rehreWriteform').remove();
});

$(document).on('submit','#hreModify',function(event){

	if($(this).find('textarea').val().trim()==''){
		alert('내용을 입력해주세요.');+
		$(this).find('textarea').val('').focus();
		return false;
	}

	let formdata = $(this).serialize();

	$.ajax({
		url:'updateHReply',
		type:'post',
		data:formdata,
		dataType:'json',
		success:function(param){
			if(param.result=='logout'){

				alert('로그인 후 이용해주세요');

			}else if(param.result=='success'){
				selectReply(pageNum);

			}else if(param.result=='notWriter'){
				alert('본인 댓글만 수정 가능합니다.');
			}else{
				alert('댓글 수정 오류');
			}

		},

		error:function(){
			alert('네트워크 오류');
		}
	});	

	event.preventDefault();

});

$(document).on('click','.delete-btn',function(){
	
	let choice = confirm('삭제하시겠습니까?');
	
	if(choice){
		$.ajax({
			url:'deleteHreply',
			type:'post',
			data:{hre_num:$(this).attr('data-num'),mem_num:$(this).attr('data-mem')},
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인 후 이용해주세요');

				}else if(param.result=='success'){
					selectReply(pageNum);
				}else if(param.result=='notWriter'){
					alert('본인 댓글만 삭제 가능합니다.');
				}else{
					alert('댓글 수정 오류');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		})
	}
});


//댓글 좋아요
$(document).on('click','.hrefav',function(event){
			let hre_num = $(this).attr('data-num');
				$.ajax({
					url:'clickHreFav',
					type:'post',
					data:{hre_num:hre_num},
					dataType:'json',
					success:function(param){

						if(param.result =='logout'){
							alert('로그인 후 좋아요 가능');

							$('#'+hre_num).prop('checked',false);

						}else if(param.result=='success'){

							if(param.count ==0){
								$('.'+hre_num).text(0);

							}else{

								$('.'+hre_num).text(param.count);

							}

							if(param.status =='noFav'){
								$('#'+hre_num).prop('checked',false);
							}else {
								$('#'+hre_num).prop('checked',true);
							}

						}else{
							alert('좋아요 클릭 오류');
							$('#'+hre_num).prop('checked',false);
						}
					},

					error:function(){
						alert('네트워크 오류');
						$('#'+hre_num).prop('checked',false);
					}
				});		
		});//댓글 좋아요 끝

		//댓글 목록 처리 해야됨

		//답글 쓰기
	
		$(document).on('click','.re-wirte-btn',function(){
				$('#rehreWriteform').remove();
				$('#modifyform').remove();
				let output = '<div id="rehreWriteform">';
				output += '<img src="../images/icon/replymodify.png" width="20px"> '+$(this).attr('data-id')+'님 답글 작성중';
				output+='<form  id="rehreWrite">';
				output+='<input type="hidden"  name ="healthy_num" value="'+$(this).attr('data-healnum')+'">';
				output+='<input type="hidden"  name ="hre_renum" value="'+$(this).attr('data-renum')+'">';
				output +='<input type="hidden" name="hre_level" value="'+$(this).attr('data-level')+'">';
				output+='<textarea rows="5" cols="55" name="hre_content"  style="resize:none;" placeholder="300자까지 입력가능" ></textarea>';
				output+=' <input type="submit" class="default-btn" value="답글달기"> <input type="button"  class="default-btn" id="resetbtn" value="취소"></form>';
				output+='</div>';
				$(this).parent().parent().append(output);

		});
		
		$(document).on('submit','#rehreWrite',function(event){
				
				if($(this).find('textarea').val().trim()==''){
					alert('내용을 입력해주세요.');+
					$(this).find('textarea').val('').focus();
					return false;
				}
			
				let formdata = $(this).serialize();
			
				$.ajax({
					url:'writereHReply',
					type:'post',
					data:formdata,
					dataType:'json',
					success:function(param){
						if(param.result=='logout'){
			
							alert('로그인 후 이용해주세요');
			
						}else if(param.result=='success'){
							selectReply(pageNum);
							let relist = $(this).parent().parent('.items').find('.re-view-btn');
							let relist2= $(this).closest('.btnspace').find('.re-view-btn');
							$('#rehreWriteform').remove();
							relist.click();
							relist2.click();
							//목록작업 해야됨
						}else{
							alert('답글 달기 오류');
						}
			
					},
			
					error:function(){
						alert('네트워크 오류');
					}
				});	
			
				event.preventDefault();
			
			});

$(document).on('click','.re-cancle-btn',function(){
	let re_num = $(this).attr('data-renum');
	$('#subitems'+re_num).empty();
	$(this).prev().removeClass('hide');
	$(this).addClass('hide');
})
//답글 보기
$(document).on('click','.re-view-btn',function(){
	let re_num = $(this).attr('data-renum');
	$(this).next().removeClass('hide');
	$(this).addClass('hide');
	$.ajax({
		url:'selectReHreply',
		type:'post',
		data:{hre_renum:$(this).attr('data-renum')},
		dataType:'json',
		success:function(param){
			$('#subitems'+re_num).empty();
				$(param.list).each(function(index,item){
					let output ='';
					output +='<div class="subitems">';
					output +='<ul>';
					output+='<li><img src="../member/memViewProfile?mem_num='+item.mem_num+'" width="35" height="35" class="rounded-circle"> &nbsp;&nbsp; '+item.id+'</li>';
					if(item.hre_modify_date){
						output+='<li> 수정일 : '+item.hre_modify_date+'</li>';
					}else{
						output+='<li> 등록일 : '+item.hre_reg_date+'</li>';
					}
					output +='</ul>';
					output += '<img src="../images/icon/replymodify.png" width="20px"> '+$('#'+item.hre_renum).attr('data-id');
					output+='<br><br><br><p>'+item.hre_content+'<p><br>';
							output +='<div class="hrefav" style="width:20px; margin-bottom:20px;" data-num="'+item.hre_num+'">';
					output+='<div title="Like" class="heart-container" >';
					if(item.click_num == $('#user_num').val()&& item.click_num !=0&& $('#user_num').val()!=0){
					output+='<input id="'+item.hre_num+'" data-id="'+item.id+'"  class="checkbox" type="checkbox" checked="checked">';}

					else{
					output+='<input  id="'+item.hre_num+'"  data-id="'+item.id+'" class="checkbox" type="checkbox">';
					}
					output+='<div class="svg-container" style="width:25px;  float:left;">';
					output+='<svg style="width:20px; margin-bottom:25px;" xmlns="http://www.w3.org/2000/svg" class="svg-outline" viewBox="0 0 24 24">';
					output+='<path  d="M17.5,1.917a6.4,6.4,0,0,0-5.5,3.3,6.4,6.4,0,0,0-5.5-3.3A6.8,6.8,0,0,0,0,8.967c0,4.547,4.786,9.513,8.8,12.88a4.974,4.974,0,0,0,6.4,0C19.214,18.48,24,13.514,24,8.967A6.8,6.8,0,0,0,17.5,1.917Zm-3.585,18.4a2.973,2.973,0,0,1-3.83,0C4.947,16.006,2,11.87,2,8.967a4.8,4.8,0,0,1,4.5-5.05A4.8,4.8,0,0,1,11,8.967a1,1,0,0,0,2,0,4.8,4.8,0,0,1,4.5-5.05A4.8,4.8,0,0,1,22,8.967C22,11.87,19.053,16.006,13.915,20.313Z">';
					output+='</path></svg><svg style="width:20px; margin-bottom:25px;" xmlns="http://www.w3.org/2000/svg" class="svg-filled" viewBox="0 0 24 24"><path d="M17.5,1.917a6.4,6.4,0,0,0-5.5,3.3,6.4,6.4,0,0,0-5.5-3.3A6.8,6.8,0,0,0,0,8.967c0,4.547,4.786,9.513,8.8,12.88a4.974,4.974,0,0,0,6.4,0C19.214,18.48,24,13.514,24,8.967A6.8,6.8,0,0,0,17.5,1.917Z"> </path>';
					output+='</svg><svg  style="width:10px; " xmlns="http://www.w3.org/2000/svg"  class="svg-celebrate"><polygon points="10,10 20,20"></polygon><polygon style="width:20px; margin-bottom:20px;" points="10,50 20,50"></polygon>';
					output+='<polygon style="width:20px; margin-bottom:20px;" points="20,80 30,70"></polygon><polygon  style="width:20px; margin-bottom:20px;" points="90,10 80,20"></polygon><polygon style="width:20px; margin-bottom:20px;"  points="90,50 80,50"></polygon><polygon style="width:20px; margin-bottom:20px;" points="80,80 70,70"></polygon> </svg></div>';
					output +='&nbsp; &nbsp;<span  style="width:10px;  text-align:center; margin-top:4px; padding-top:4px !important;" class="'+item.hre_num+'">'+item.refav_cnt+'</span></div>';
					output +='</div>';
					output+='<div class="align-right btnspace">';
					if(param.user_num == item.mem_num){
						output+=' <input type="button"  data-content="'+item.hre_content+'" data-num="'+item.hre_num+'" data-mem="'+item.mem_num+'" value="수정" class="modify-btn default-btn"> <input type="button"  value="삭제" data-num="'+item.hre_num+'" data-mem="'+item.mem_num+'" class="delete-btn default-btn">';	
					}
					if(param.user_num != 0)
						output+=' <input type="button"  class="re-wirte-btn default-btn" data-id="'+item.id+'" data-renum="'+item.hre_num+'" data-healnum="'+item.healthy_num+'" data-level="1"  value="답글 쓰기">';
					if(item.rere_cnt !=0){
						output+=' <input type="button"  id="rereList" class="re-view-btn default-btn"data-renum="'+item.hre_num+'"  value="답글 보기">';
					}
					output +='</div>';
					output +='</div><br><br><div class="line"></div><br></div><div id="subitems'+item.hre_num+'">';
					 $('#subitems'+re_num).append(output);
					 $('#rereList').click();
					 $('#rereList').remove();
				})
		},
		error:function(){
			alert('네트워크 오류');
		}
	})
});


 selectReply(1);



});