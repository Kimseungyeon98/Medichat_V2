<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.all.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.min.css" rel="stylesheet">
<!-- 의사회원가입 시작 -->
<style>
.custom-select {
    position: relative;
    padding-right: 35px;
}
.custom-select::after {
    content: "\f078"; /* Font Awesome 화살표 아이콘 코드 */
    font-family: "Font Awesome 5 Free";
    position: absolute;
    top: 50%;
    right: 10px;
    transform: translateY(-50%);
    pointer-events: none;
}
</style>
<div class="container">
	<h2 class="title">회원가입(의사)</h2>
	<hr size="1" width="100%" noshade="noshade">
	<span style="font-weight:bold;">정보입력</span>
	<br>
	<span class="title2">회원가입에 필요한 정보를 입력합니다.</span>
	<form:form action="registerDoc" id="doctor_register" enctype="multipart/form-data" modelAttribute="doctorVO">
		<div class="form-main">
		<ul>
			<li style="margin-top:20px;">
				<form:label path="mem_id">아이디</form:label>
				<form:input path="mem_id" placeholder="영문or숫자 사용하여 4~12자 입력" autocomplete="off" class="effect-1" style="width:300px;"/>
				<input type="button" id="confirmId" value="중복확인" class="default-btn" style="margin-left:0px; background-color:#40916C;">
				<form:errors path="mem_id" cssClass="error-color" style="display:inline;"/><br>
				<span id="message_id" style=""></span>
			</li>
			<li>
				<form:label path="doc_passwd">비밀번호</form:label>
				<form:password path="doc_passwd" placeholder="영문,숫자 사용하여 4~12자 입력" class="effect-1"/>
				<form:errors path="doc_passwd" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="mem_name">이름</form:label>
				<form:input path="mem_name" placeholder="이름" class="effect-1"/>
				<form:errors path="mem_name" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="doc_email">이메일</form:label>
				<form:input path="doc_email" placeholder="test@test.com 형식으로 입력" class="effect-1"/>
				<form:errors path="doc_email" cssClass="error-color"/>
			</li>
			<li>
			    <!-- 병원 목록 검색 -->
			    <form:label path="hos_num">병원</form:label>
			    <form:hidden path="hos_num"/>
			    <input type="search" name="keyword" id="keyword" value="${keyword}" class="effect-1" placeholder="병원 이름 검색 후 선택">
			    <button type="button" id="search_button" class="btn">
			        <i class="fas fa-search"></i>
			    </button>
			    <form:errors path="hos_num" cssClass="error-color"/>
			</li>
			<li style="margin-left:205px;">
			    <form:select path="hos_num" class="custom-select" style="width:400px;">
			        <c:forEach var="hos" items="${hosList}">
			            <option value="${hos.hos_num}">${hos.hos_name}/${hos.hos_addr}</option>
			        </c:forEach>
			    </form:select>
			</li>
			<li>
				<form:label path="doc_history">연혁</form:label>
				<form:textarea path="doc_history" placeholder="연혁을 입력해주세요." class="msg" style="height:150px; vertical-align:top;"/>
			</li>
		</ul>
		</div>
		<br>
		<hr size="1" width="100%" noshade="noshade">
		<div class="upload-form">
		<ul>
			<li>
				<form:label path="doc_upload">의사 면허증</form:label>
				<input type="file" name="doc_upload" id="doc_upload">
				<form:errors path="doc_upload" cssClass="error-color"/>
			</li>
		</ul>
		</div>
		<!-- 캡챠 시작 -->
		<hr size="1" width="100%" noshade="noshade">
		<div class="captcha">
		<ul>
			<li>
				<span style="font-weight:bold;">인증문자 입력</span>
				<div id="captcha_div">
					<img src="getCaptcha" id="captcha_img" width="200" height="90">
					<button type="button" class="btn" id="reload_captcha">
					    <i class="fas fa-sync-alt"></i>
					</button>
				</div>
			</li>
			<li>
				<form:label path="captcha_chars" >인증문자 확인</form:label>
				<form:input path="captcha_chars" placeholder="인증문자를 입력하세요." class="effect-1"/>
				<form:errors path="captcha_chars" cssClass="error-color"/>
			</li>
		</ul>
		</div>
		<!-- 캡챠 끝 -->
		<hr size="1" width="100%" noshade="noshade">
		<div style="text-align:right;">
			<input type="button" value="홈으로" id="reload_btn" onclick="location.href='${pageContext.request.contextPath}/main/main'">
			<form:button class="default-btn">가입신청</form:button>
		</div>
	</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/doctor.register.js"></script>
<script src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        function handleEnter(event) {
            if (event.key === 'Enter') {
                performSearch();
                return false;
            }
            return true;
        }
        function performSearch() {
            var keyword = $('#keyword').val();

            $.ajax({
                url:'${pageContext.request.contextPath}/doctor/hosList',
                type:'post',
                dataType:'json',
                data:{keyword:keyword},
                success:function(data){
                    if (data.success){
                        var hosList = data.hosList;
                        var options = '';
                        
                        var $select = $('form').find('select[name="hos_num"]');
                        $select.empty();

                        $.each(hosList,function(index,hospital) {
                            options += '<option value="' + hospital.hos_num + '">' 
                                    + hospital.hos_name + ' / ' + hospital.hos_addr + '</option>';
                        });
                        $select.append(options);
                        
                        var selectedValue = $('form').find('input[name="hos_num"]').val();
                        if(selectedValue) {
                            $select.val(selectedValue);
                        }
                    }else {
                        alert('병원 목록을 가져오는 중에 오류가 발생하였습니다.');
                    }
                },
                error:function() {
                    alert('서버 통신 중 오류가 발생하였습니다.');
                }
            });
        }

        $('#search_button').click(function() {
            performSearch();
        });

        $('#keyword').keypress(handleEnter);

        $('form').on('submit',function() {
            var selectedValue = $('form').find('select[name="hos_num"]').val();
            $('form').find('input[name="hos_num"]').val(selectedValue);
        });
    });
</script>
<script>
	$(function(){
		$('#reload_captcha').click(function(){
		 $.get('getCaptcha', function(data) {
	            $('#captcha_img').attr('src', 'getCaptcha?' + new Date().getTime());
	        }).fail(function() {
	            alert('네트워크 오류 발생');
	        });
		});
	});
</script>
</div>
<!-- 의사회원가입 끝 -->