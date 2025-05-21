<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.all.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/kcy.css">
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
<!-- 의사회원정보 수정 시작 -->
<div class="container">
	<h2>회원정보수정(의사)</h2>
	<hr size="1" width="100%" noshade="noshade">
	<span style="font-weight:bold;">정보입력</span>
	<br>
	<span class="title2">회원정보수정에 필요한 정보를 입력합니다.</span>
	<form:form action="modifyDoctor" id="doctor_modify" modelAttribute="doctorVO">
		<form:hidden path="doc_num"/>
		<ul>
			<li>
				<form:label path="mem_name" style="margin-top:50px;">이름</form:label>
				<form:input path="mem_name" class="effect-1"/>
				<form:errors path="mem_name" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="doc_email">이메일</form:label>
				<form:input path="doc_email" class="effect-1"/>
				<form:errors path="doc_email" cssClass="error-color"/>
			</li>
			<li>
			    <!-- 병원 목록 검색 -->
			    <form:label path="hos_num">병원</form:label>
			    <form:hidden path="hos_num"/>
			    <input type="search" name="keyword" id="keyword" value="${hos_name}" class="effect-1">
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
				<form:textarea path="doc_history" placeholder="연혁을 입력해주세요." style="height:150px; vertical-align:top;"/>
			</li>
		</ul>
		<hr size="1" width="100%" noshade="noshade">
		<div style="text-align:left;">
			<input type="button" value="비밀번호 변경" id="reload_btn" style="width:150px;" onclick="location.href='changePasswd'">
		</div>
		<div style="text-align:right">
			<input type="button" value="MY페이지" id="reload_btn" onclick="location.href='docPage'">
			<form:button class="default-btn">수정</form:button>
		</div>
	</form:form>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
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

</div>
<!-- 회원정보 수정 끝 -->