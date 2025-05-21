<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<style>
    .container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 700px;     
    }
    .result-display {
        background-color:rgb(251, 250, 250);
        padding: 20px;
        border:1px solid #ccc;
        border-radius: 8px;
        text-align: center;
        max-width: 600px;
        width: 100%;
    }
    .result-display b {
        color: #007bff;
        font-size: 35px;
    }
    .result-display p {
        font-size: 20px;
        color: #666;
    }
</style>
<jsp:include page="/WEB-INF/views/template/header.jsp"/>
<hr size="1" width="100%" noshade="noshade">
<div class="container">
    <div class="result-display">
        <div class="align-center">
            <img src="/images/loginLogo.png" width="150" height="100" style="margin-top: 20px;">
            <p style="margin-top: 20px;">아이디 찾기 결과는</p>
            <b>${mem_id}</b>
            <p>입니다.</p>
        </div>
        <hr size="1" width="100%" noshade="noshade">
        <div>
        	<input type="button" value="비밀번호 찾기" class="btn-green" onclick="location.href='sendMemPassword'">
        	<input type="button" value="로그인" class="btn-green" onclick="location.href='login'">
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"/>