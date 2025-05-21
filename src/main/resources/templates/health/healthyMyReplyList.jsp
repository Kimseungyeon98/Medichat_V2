<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/review.css" type="text/css">
<div >
<div class="align-center" >
	<br><br><br><br>
	<h2 class="align-center"><b>건강 매거진 댓글 리스트</b></h2>
<br><br>
<c:if test="${count==0}">
	댓글 없습니다.
	<br><br><br><br>
</c:if>
<c:if test="${count > 0}">
<div >
	
		<table class="align-center" id="review-table" style="border:3px black; ">
			<tr style="width:100%; margin-bottom:20px;   background-color: #f8f8f8; height: 50px; border-top: 2px solid #000000; border-bottom: 1px solid #000000;">
			 	<th>번호</th>
			 	<th>댓글 내용</th>
			 	<th>작성일</th>
			</tr>
			<tr style=width:50%;">
				<td>&nbsp; </td>
				<td> </td>
				<td> </td>
				<td> </td>
			</tr>
	<c:forEach var="r" items="${list}">
			<tr id="myList" style="cursor:pointer; " onclick="location.href='${pageContext.request.contextPath}/health/healthDetail?healthy_num=${r.healthy_num} #replyList'">
				<th>${r.healthy_num}</th>
				<th style="padding-left:20%;" class="align-left col-9"><br>${r.hre_content}<br><a href="${pageContext.request.contextPath}/health/healthM" ><span  class="fs-12 fw-5 text-black-3"> HOME > 건강블로그 > 건강 매거진 </span></a></th>
				<th>${r.hre_reg_date}</th>
			</tr>
			<tr style="height:1px;"><td class="align-center" colspan="3" ><hr size="1" width="100%"></td></tr>
	</c:forEach>
	</table>
	
	<br><br>
	<div class="align-center">
		${page}
	</div>
	<br><br><br><br>
</div>
</c:if>
</div>

</div>