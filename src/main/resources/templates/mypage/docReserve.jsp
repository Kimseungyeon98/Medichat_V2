<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약내역</title>
</head>
<body>
	<div class="docReservce">
		<ul>
			<li>이름 : ${user.mem_name}</li>
			<li>이메일 : ${user.doc_email}</li>
			<li>병원정보 : ${user.hos_num}</li>
		</ul>
	</div>
</body>
</html>