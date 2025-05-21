
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="page-main">
	<div class="page-one"  style="padding-top:16px;">
		<span class="text-lightgray fw-7 fs-13">홈 > 질병백과사전</span>	
		<h3   style="margin-top:16px;"><img src="${pageContext.request.contextPath}/images/dictionary.png" width="45px;"> <b>질병 백과사전</b></h3>
		<br>
	<form class="align-center" id="form-disease" action="${pageContext.request.contextPath}/disease/diseaseDictionary" method="get">
		
		<div class="container-input" style="width:500px; margin:0 auto;">
		<div class="d-flex justify-content-center align-items-center " >
			<select name="keyfield" id="selectinput" class="form-control">
				<option disabled="disabled"<c:if test="${empty keyfield}">selected</c:if> >선택</option>
				<option value="1" <c:if test="${keyfield ==1}">selected</c:if>>질병명</option>
				<option value="2"<c:if test="${keyfield ==2}">selected</c:if>>증상</option>
				<option value="3" <c:if test="${keyfield ==3}">selected</c:if>>질병명 또는 증상</option>
			</select>
		  	&nbsp;&nbsp;<input type="text" id="h-search" class="form-control" placeholder="검색어를 입력하세요." name="keyword" value="${keyword}">
			<i id="h-search-icon" class="bi bi-search" ></i>
				<script type="text/javascript">
					$('#h-search-icon').click(function(){
						$('#form-disease').submit();
					});
				</script>
		</div>
		</div>
		
	</form>
	<hr size="1" width="100%">
	<br>
		<c:if test="${count  == 0}">
				질병 사전 내역이 없습니다.
		</c:if>
		<c:if test="${count > 0}">
				<table style="width:90%; margin:0 auto;"class="fs-18 fw-4">
					<tr>
						<th>
						</th>
					</tr>
				
		<c:forEach var="d" items="${list}">
				<tr >
					<td ><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/disease/diseaseDictDetail?sickcd=${d.sickcd}">${d.dis_name}</a></td>
				</tr>
				<tr>
					<td ><div class="line"></div></td>
				</tr>
		</c:forEach>
				</table>
				<div class="align-center">
					<br>
					${page}
					<br>
				</div>
		</c:if>
	</div>
</div>