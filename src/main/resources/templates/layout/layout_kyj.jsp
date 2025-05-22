<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:getAsString name="title"/></title>

<tiles:insertAttribute name="font" ignore="true"/>
<tiles:insertAttribute name="bootstrap" ignore="true"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/chat.js"></script>

</head>
<body>
<div id="main">
	<div id="main_header">
		<tiles:insertAttribute name="header"/>
	</div>
	<div class="custom-container row d-flex justify-content-center pt-4 display-body">
		<div id="main_leftNav" class="col-3 display-body" style="margin-top:55px	;">
			<tiles:insertAttribute name="nav"/>
		</div>
		<div id="main_body" class="col-9 display">
				<tiles:insertAttribute name="chatNav"/>
				<tiles:insertAttribute name="chatRoom"/>
		</div>
	</div>
	<div id="main_footer">
		<tiles:insertAttribute name="footer"/>
	</div>
</div>
</body>
</html>