<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<!-- 비대면 진료 파일 목록 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/kyj.css" type="text/css">
<div class="nav-body bg-gray-1">
	<table class="nav-item">
		<tr class="nav-title">
			<th class="fs-24 text-black-7">나의 서류함</th>
		</tr>
		<c:forEach var="item" items="${chat}">
				<tr>
					<td><a href="/chat/myFiles" class="file-room fs-18 text-black-4" data-chat-num="${item.chat_num}" data-res-date="${item.res_date}" data-res-time="${item.res_time}" data-res-num="${item.res_num}">
					${item.res_date} ${item.res_time}
					<br>${item.mem_name} 의사</a></td>
				</tr>
		</c:forEach>
	</table>
</div>
<!-- 비대면 진료 파일 목록 끝 -->