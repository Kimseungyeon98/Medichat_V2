<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- 비대면 진료 신청 시작 -->
<style>
.container {
	text-align: center;
}

#reload_btn {
	margin-top: 75px;
}

#register_treat ul li {
	margin-top: 15px;
}

label li {
	postion: fixed;
}
#mem_name{
	width:200px;
}
#doc_email{
	width:200px;
}
#doc_stime{
	width:100px;
}
#doc_etime{
	width:100px;
}
#now_passwd{
	width:200px;
}
</style>
<div class="container">
	<h2>비대면 진료 신청</h2>
	<hr size="1" width="95%" noshade="noshade">
	<span style="font-weight: bold; font-size: 30px;">정보입력</span> <br>
	<span>신청에 필요한 정보를 입력합니다.</span>
	<form:form
		action="${pageContext.request.contextPath}/doctor/registerTreat"
		method="post" id="register_treat" enctype="multipart/form-data"
		modelAttribute="doctorVO" onsubmit="return validateForm()">
		<ul>
			<li><form:label path="mem_name" style="width:56px;">이름</form:label>
				<form:input path="mem_name" value="${doctor.mem_name}" readonly="true" /> <form:errors path="mem_name"
					cssClass="error-color" /></li>
			<li><form:label path="doc_email" style="width:56px;">이메일</form:label>
				<form:input path="doc_email" value="${doctor.doc_email}" readonly="true" /> <form:errors path="doc_email"
					cssClass="error-color" /></li>
			<li>
				<label for="doc_off">휴무일</label>
					<div id="day_checkboxes">
					    <input type="checkbox" id="doc_off_0" name="doc_off[]" value="0" />
					    <label for="doc_off_0">월</label>
					    <input type="checkbox" id="doc_off_1" name="doc_off[]" value="1" />
					    <label for="doc_off_1">화</label>
					    <input type="checkbox" id="doc_off_2" name="doc_off[]" value="2" />
					    <label for="doc_off_2">수</label>
					    <input type="checkbox" id="doc_off_3" name="doc_off[]" value="3" />
					    <label for="doc_off_3">목</label>
					    <input type="checkbox" id="doc_off_4" name="doc_off[]" value="4" />
					    <label for="doc_off_4">금</label>
					    <input type="checkbox" id="doc_off_5" name="doc_off[]" value="5" />
					    <label for="doc_off_5">토</label>
					    <input type="checkbox" id="doc_off_6" name="doc_off[]" value="6" />
					    <label for="doc_off_6">일</label>
					</div>
			</li>
			<li><form:label path="doc_time">업무시간</form:label>
				<div>
					<form:select path="doc_stime">
						<% for (int hour = 0; hour <= 23; hour++) { %>
						<% for (int minute = 0; minute <= 45; minute += 15) { %>
						<form:option
							value='<%= String.format("%02d:%02d", hour, minute) %>'>
							<%= String.format("%02d:%02d", hour, minute) %>
						</form:option>
						<% } %>
						<% } %>
					</form:select>
					부터
					<form:select path="doc_etime">
						<% for (int hour = 0; hour <= 23; hour++) { %>
						<% for (int minute = 0; minute <= 45; minute += 15) { %>
						<form:option
							value='<%= String.format("%02d:%02d", hour, minute) %>'>
							<%= String.format("%02d:%02d", hour, minute) %>
						</form:option>
						<% } %>
						<% } %>
					</form:select>
					까지
				</div> <form:errors path="doc_time" cssClass="error-color" /></li>

			<li><form:label path="now_passwd" style="width:56px;">비밀번호</form:label>
				<form:password path="now_passwd" id="now_passwd" /> <form:errors path="now_passwd" cssClass="error-color" /></li>
			<li><input type="button" value="홈으로" id="reload_btn" onclick="location.href='${pageContext.request.contextPath}/main/main'">
				<form:button class="default-btn">비대면 진료 신청</form:button></li>
		</ul>
	</form:form>
</div>
<!-- 비대면 진료 신청 끝 -->
<script>
function validateForm() {
    var checkboxes = document.querySelectorAll('input[name="doc_off[]"]');
    var isChecked = false;

    checkboxes.forEach(function(checkbox) {
        if (checkbox.checked) {
            isChecked = true;
        }
    });

    if (!isChecked) {
        alert('하나 이상의 휴무일을 선택해주세요.');
        event.preventDefault();
        return false;
    } else {
        return true;
    }
}
</script>

