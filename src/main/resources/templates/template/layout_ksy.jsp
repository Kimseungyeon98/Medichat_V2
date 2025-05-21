<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:getAsString name="title"/></title>
<tiles:insertAttribute name="bootstrap" ignore="true"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ksy.css" type="text/css">
<tiles:insertAttribute name="css" ignore="true"/>
</head>
<body>
<div>
	<div>
		<tiles:insertAttribute name="header"/>
	</div>
	<div class="custom-container border">
		<div class="row">
			<div class="col-9 border-end" style="padding-right:0;">
				<tiles:insertAttribute name="body"/>
			</div>
			<div id="right_nav" class="col-3">
				<tiles:insertAttribute name="right_nav"/>
			</div>
		</div>
		<div>
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>
</div>
</body>
</html>