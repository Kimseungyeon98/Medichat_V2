$(function(){
	
	/*------------------------------------의약품 등록------------------------------------ */
	//의약품 검색
	let drug_list = [];
	
	$('#drug_search').keyup(function(){
		if($('#drug_search').val().trim() ==''){//빈문자열
			$('#searchDrugList').empty();//searchDrugList의 모든 자식 요소 제거
			return;
		}
		$.ajax({
			url:'memberDrugSearchAjax',
			type:'get',
			data:{drg_name:$('#drug_search').val()},
			dataType:'json',
			success:function(param){
				if(param.result == 'success'){
					let results = param.drugList.slice(0,10);//검색어 10개까지만 출력
					$('#searchDrugList').empty();
					$(results).each(function(index,item){
						//한 번 선택한 약은 다시 선택하지 않도록 처리
						if(!drug_list.includes(item.drg_name)){ 
							let output = '';
							output += '<li data-name="'+item.drg_name+'">';
							output += item.drg_name;
							output += '</li>';
							$('#searchDrugList').append(output);
						}
						
					});
				}else{
					alert('의약품 검색 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	//검색된 의약품 선택
	$(document).on('click','#searchDrugList li',function(){
		let drg_name = $(this).text(); //선택한 의약품
		drug_list.push(drg_name);
		//선택한 의약품 화면에 표시
		let choice_drug = '<span class="drugSelect-span data-name="'+drg_name+'">';
		choice_drug += drg_name+'<sup>&times;</sup><span>'
		$('#drugSelect').append(choice_drug);
		$('#drug_search').val('');
		$('#searchDrugList').empty();
	});
	
	//검색된 의약품 삭제
	$(document).on('click','.drugSelect-span',function(){
		let drug_name = $(this).attr('data-name');
		//의약품이 저장된 배열에서 의약품명 제거
		drug_list.splice(drug_list.indexOf(drug_name),1);
		$(this).remove();//span 태그 삭제
	});
	
	//유효성 체크(복용내용 외 전부) 및 등록
	$('#drugSearch').submit(function(event){
		event.preventDefault();
		if($('#title').val().trim()==''){
			alert('증상을 입력하세요');
			$('#title').val('').focus();
			return false;
		}
		if(drug_list.length == 0){
			alert('복용한 의약품을 입력하세요');
			$('#drugSelect').val('').focus();
			return false;
		}
		if($('#selectedSDate').val().trim()==''){
			alert('복용 일자를 입력하세요');
			$('#selectedSDate').val('').focus();
			return false;
		}
		
		//날짜 오늘 이후 선택 불가
		let selectedSDate = new Date($('#selectedSDate').val());
		selectedSDate.setHours(0, 0, 0, 0);
		let today = new Date();
		
		if(selectedSDate > today){
			alert('오늘 이후 날짜는 선택할 수 없습니다');
			$('#selectedSDate').val('').focus();
			return false;
		}
		
		var checkboxes = document.querySelectorAll('input[name="med_time"]');
		var isChecked = false;
		for(var i=0;i<checkboxes.length;i++){
			if(checkboxes[i].checked){
				isChecked = true;
				break;
			}
		}
		if(!isChecked){
			alert('적어도 하나의 복용 시간을 선택하세요.');
			return false;
		}
		if($('#memberDosage').val().trim()==''){
			alert('복용량을 입력하세요');
			$('#memberDosage').val('').focus();
			return false;
		}
		
		//선택한 의약품을 hidden input으로 추가
		let med_names = drug_list.join(','); //배열을 쉼표로 구분된 문자열로 반환
		console.log("수정, 선택한 의약품 : " + med_names);
	    $('<input>').attr({
	        type: 'hidden',
	        name: 'med_name',
	        value: med_names
	    }).appendTo('#drugSearch');

		let form_data = $(this).serialize();
		//
		console.log(form_data);
		//서버와 통신
		$.ajax({
			url:'/memberDrug/memberDrugInsertAjax',
			type:'get',
			data:form_data,
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 등록할 수 있습니다.');
				}else if(param.result=='success'){
					alert('의약품 복용 기록 등록이 완료되었습니다.');
					$('#drugModal').hide();
					//폼 초기화
					$('#drugSearch')[0].reset();
					$('#drugSelect').empty();
                    drug_list = []; //의약품 리스트 초기화
               		location.reload();
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});//end of 등록
	
	/*------------------------------------의약품 수정------------------------------------ */
	//의약품 검색
	$('#moDrug_search').keyup(function(){
		if($('#moDrug_search').val().trim() ==''){//빈문자열
			$('#moSearchDrugList').empty();//searchDrugList의 모든 자식 요소 제거
			return;
		}
		$.ajax({
			url:'memberDrugSearchAjax',
			type:'get',
			data:{drg_name:$('#moDrug_search').val()},
			dataType:'json',
			success:function(param){
				if(param.result == 'success'){
					let results = param.drugList.slice(0,10);//검색어 10개까지만 출력
					$('#moSearchDrugList').empty();
					$(results).each(function(index,item){
						//한 번 선택한 약은 다시 선택하지 않도록 처리
						if(!med_list.includes(item.drg_name)){
							let output = '';
							output += '<li data-name="'+item.drg_name+'">';
							output += item.drg_name;
							output += '</li>';
							$('#moSearchDrugList').append(output);
						}
					});
				}else{
					alert('의약품 검색 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	//의약품 수정 submit
	$('#modifyDrugSearch').submit(function(event){
		event.preventDefault();
		if($('#mo_title').val().trim()==''){
			alert('증상을 입력하세요');
			$('#mo_title').val('').focus();
			return false;
		}
		if(med_list.length == 0){
			alert('복용한 의약품을 입력하세요');
			$('#moDrugSelect').val('').focus();
			return false;
		}
		if($('#moSelectedSDate').val().trim()==''){
			alert('복용 일자를 입력하세요');
			$('#moSelectedSDate').val('').focus();
			return false;
		}
		
		let moSelectedSDate = new Date($('#moSelectedSDate').val());
		moSelectedSDate.setHours(0, 0, 0, 0);
		let today = new Date();
		
		if(moSelectedSDate > today){
			alert('오늘 이후 날짜는 선택할 수 없습니다');
			$('#moSelectedSDate').val('').focus();
			return false;
		}
		
		var checkboxes = document.querySelectorAll('input[name="med_time"]');
		var isChecked = false;
		for(var i=0;i<checkboxes.length;i++){
			if(checkboxes[i].checked){
				isChecked = true;
				break;
			}
		}
		if(!isChecked){
			alert('적어도 하나의 복용 시간을 선택하세요.');
			return false;
		}
		if($('#moMemberDosage').val().trim()==''){
			alert('복용량을 입력하세요');
			$('#moMemberDosage').val('').focus();
			return false;
		}
		
		//선택한 의약품을 hidden input으로 추가
		let med_names = med_list.join(','); //배열을 쉼표로 구분된 문자열로 반환
		console.log(med_names);
	    $('<input>').attr({
	        type: 'hidden',
	        name: 'med_name',
	        value: med_names
	    }).appendTo('#moDrugSelect');
	    
	    console.log("js파일 통신 전 med_names:" + med_names);

		let form_data = $(this).serialize();
		console.log(form_data);
		
		//서버와 통신
		$.ajax({
			url:'/MemberDrug/memberDrugUpdateAjax',
			type:'post',
			data:form_data,
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 수정할 수 있습니다.');
				}else if(param.result=='success'){
					alert('의약품 복용 기록 수정이 완료되었습니다.');
					console.log("통신성공 med_names:" + med_names);
					$('#updateDrug').hide();
					//폼 초기화
					$('#modifyDrugSearch')[0].reset();
					$('#moDrugSelect').empty();
					console.log("통신성공 폼초기화 med_names:" + med_names);
                    med_list = []; //의약품 리스트 초기화
					console.log("통신성공 의약품 리스트 초기화 med_names:" + med_names);
					location.reload();
				}else{
					alert('의약품 복용 내역 수정 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});//end of 수정
	
	/*------------------------------------의약품 삭제------------------------------------ */
	$('.delete-btn').click(function(){
		//med_num
		let med_num = $('#updateDrug input[name="med_num"]').val();
		//서버와 통신
		$.ajax({
			url:'/MemberDrug/memberDrugDeleteAjax',
			type:'get',
			data:{med_num:med_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 삭제할 수 있습니다')
				}else if(param.result == 'success'){
					alert('의약품 복용 기록이 삭제되었습니다.');
					console.log("의약품 복용기록 삭제 완료");
					$('#updateDrug').hide();
					location.reload();
				}else{
					alert('의약품 복용 내역 삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		
	});
});