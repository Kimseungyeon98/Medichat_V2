<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<div class="container">
	<h2 class="title">나의 의료상담 내역</h2>
	<hr size="1" width="80%" noshade="noshade">
	<div id="consulting-div">
		<c:forEach items="${consultList}" var="consult">
				<div style="margin:10px 10px; font-size:15pt;"><b>${consult.con_title}</b></div>
				<div><a href="${pageContext.request.contextPath}/consultings/detail/${consult.con_num}">${consult.con_content}</a></div>
				<div>
					<div>
						<div>${consult.con_rDate}</div>
					</div>
				</div>
			<div class="line"></div>
		</c:forEach>
	</div>
</div>