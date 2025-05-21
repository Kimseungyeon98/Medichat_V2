<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<head>
<link
	href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@600;900&display=swap"
	rel="stylesheet">
<script src="https://kit.fontawesome.com/4b9ba14b0f.js"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/ych.css" type="text/css">
</head>
<body>
<div class="error-body">
	<div class="mainbox">
		<div class="logo">
			<a href="${contextPath.request.contextPath}/main/main"><img src="${pageContext.request.contextPath}/images/logo.jpg" width="400"></a>
		</div>
		<div class="err">4</div>
		<i class="far fa-question-circle fa-spin"></i>
		<div class="err2">4</div>
		<div class="msg">
			<p><b>We looked everywhere for this page.</b>
			<p><b>Are you sure website URL is correct?</b>
			<p><b>Let's go <a href="${contextPath.request.contextPath}/main/main" class="msgh">home</a> and try from there.</b>
		</div>
	</div>
</div>