<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보</title>
</head>
<style>
.memberInfo {
        display: flex;
        margin-top:70px;
    }

    .leftInfo, .rightInfo {
        flex: 1;
        padding: 0 10px;
    }

    .leftInfo li, .rightInfo li {
        list-style-type: none;
        margin-bottom: 10px;
    }

    @media (max-width: 768px) {
        .memberInfo {
            flex-direction: column;
        }
    }
</style>
<body>
	<div class="memberInfo">
		<div class="leftInfo">
			<ul>
				<li style="font-size:25px; font-weight:bold;">이름</li>
				<li style=font-size:15px;>${member.mem_name}</li>
				<li style="font-size:25px; font-weight:bold;">전화번호</li>
				<li style=font-size:15px;>${member.mem_phone}</li>
				<li style="font-size:25px; font-weight:bold;">생년월일</li>
				<li style=font-size:15px;>${member.mem_birth}</li>
				<li style="font-size:25px; font-weight:bold;">이메일</li>
				<li style=font-size:15px;>${member.mem_email}</li>
			</ul>
			</div>
		<div class="rightInfo">
			<ul>	
				<li style="font-size:25px; font-weight:bold;">우편번호</li>
				<li style=font-size:15px;>${member.mem_zipcode}
				<li style="font-size:25px; font-weight:bold;">주소</li>
				<li style=font-size:15px;>${member.mem_address1} ${member.mem_address2}
				<li style="font-size:25px; font-weight:bold;">가입일</li>
				<li style=font-size:15px;>${member.mem_reg}</li>
				<c:if test="${!empty member.mem_modify}">
					<li style="font-size:25px; font-weight:bold;">정보 수정일</li>
					<li style=font-size:15px;>${member.mem_modify}</li>
				</c:if>
			</ul>
		</div>
	</div>
</body>
</html>