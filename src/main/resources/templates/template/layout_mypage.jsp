<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:getAsString name="title"/></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ych.css" type="text/css">
<tiles:insertAttribute name="font" ignore="true"/>
<tiles:insertAttribute name="css" ignore="true"/>
<tiles:insertAttribute name="chatCss" ignore="true"/>
<tiles:insertAttribute name="bootstrap" ignore="true"/>
<style>
.container{
	margin-top:30px;
	margin-bottom: 10px;
	border:none;
}
#main_body{
	border-radius: 10px;
	background-color: #fff;
}
</style>
</head>
<body>
<div id="main">
	<div id="main_header">
		<tiles:insertAttribute name="header"/>
	</div>
	<div id="background">
	<div class="custom-container row d-flex justify-content-center pt-4">
		<div id="main_leftNav" class="col-3">
			<tiles:insertAttribute name="nav"/>
		</div>
		<div id="main_body" class="col-9">
			<tiles:insertAttribute name="body"/>
		</div>
	</div>
		<div id="main_footer" style="margin-top:70px;">
			<tiles:insertAttribute name="footer"/>
		</div>
</div>
</div>
</body>
</html>