<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form action="/consultings/modify/${consulting.con_num }" method="post">
	<label>구분</label>
	<select name="con_type">
		<option value="0" <c:if test="${consulting.con_type==0}">selected</c:if>>구분없음</option>
		<option value="1" <c:if test="${consulting.con_type==1}">selected</c:if>>만성질환</option>
		<option value="2" <c:if test="${consulting.con_type==2}">selected</c:if>>여성질환</option>
		<option value="3" <c:if test="${consulting.con_type==3}">selected</c:if>>소화기질환</option>
		<option value="4" <c:if test="${consulting.con_type==4}">selected</c:if>>영양제</option>
		<option value="5" <c:if test="${consulting.con_type==5}">selected</c:if>>정신건강</option>
		<option value="6" <c:if test="${consulting.con_type==6}">selected</c:if>>처방약</option>
		<option value="7" <c:if test="${consulting.con_type==7}">selected</c:if>>탈모</option>
		<option value="8" <c:if test="${consulting.con_type==8}">selected</c:if>>통증</option>
		<option value="9" <c:if test="${consulting.con_type==9}">selected</c:if>>여드름,피부염</option>
		<option value="10" <c:if test="${consulting.con_type==10}">selected</c:if>>임신,성고민</option>
	</select>
	
	<label id="con_title">제목</label>
	<input type="text" name="con_title" id="con_title" value="${consulting.con_title}">
	
	<label id="con_content">내용</label>
	<textarea rows="5" cols="15" id="con_content" name="con_content">${consulting.con_content}</textarea>
	
	<input type="submit" value="수정">
</form>