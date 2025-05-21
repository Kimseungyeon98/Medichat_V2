<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.all.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.12.3/dist/sweetalert2.min.css" rel="stylesheet">
<!DOCTYPE html>
<div class="container">
	<h2 class="title">회원정보 수정(관리자)</h2>
	<hr size="1" width="80%" noshade="noshade">
	<form action="changeAuth" id="search_form" method="get">
	    <ul style="list-style-type: none; padding: 0; text-align:center;">
	        <li style="display: inline-block; margin-right: 10px;">
	            <input type="search" name="keyword" id="keyword" value="${param.keyword}">
	        </li>
	        <li style="display: inline-block; margin-right: 10px;">
	            <input type="submit" value="검색">
	        </li>
	        <li style="display: inline-block;">
	            <button type="button" onclick="window.location.href=window.location.pathname;">새로고침</button>
	        </li>
	    </ul>
	</form>
	<c:if test="${count == 0}">
		<div>
			검색된 회원이 없습니다.
		</div>
	</c:if>
	<c:if test="${count > 0}">
		<table>
	        <thead>
	            <tr>
	                <th>아이디</th>
	                <th>이름</th>
	                <th>가입일</th>
	                <th>권한등급</th>
	                <th>회원정지</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:forEach var="mem" items="${memList}">
	                <tr>
	                    <td>${mem.mem_id}</td>
	                    <td>${mem.mem_name}</td>
	                    <td>${mem.mem_reg}</td>
	                    <td class="auth" data-auth="${mem.mem_auth}">
	                        <c:choose>
	                            <c:when test="${mem.mem_auth == 0}">탈퇴</c:when>
	                            <c:when test="${mem.mem_auth == 1}">정지</c:when>
	                            <c:when test="${mem.mem_auth == 2}">일반회원</c:when>
	                            <c:when test="${mem.mem_auth == 9}">관리자</c:when>
	                        </c:choose>
	                    </td>
	                    <td>
	                    	<c:if test="${mem.mem_auth != 1}">
		                   		<input type="button" value="정지" class="ben-btn" data-mem="${mem.mem_num}">                	
	                    	</c:if>
	                    	<c:if test="${mem.mem_auth == 1}">
		                   		<input type="button" value="취소" class="cancel-btn" data-mem="${mem.mem_num}">                	
	                    	</c:if>
	                    </td>
	                </tr>
	            </c:forEach>
	        </tbody>
	    </table>
	    <div style="text-align:center;">${page}</div>
	</c:if>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
	    // 정지 버튼 클릭 시
	    $('.ben-btn').click(function(){
	        var mem_num = $(this).data('mem');
	        $.ajax({
	            url:'/updateAuth',
	            type:'post',
	            data:{mem_num:mem_num},
	            dataType:'json',
	            success:function(param) {
	                if(param.result == 'logout') {
	                   Swal.fire({
                          title:'로그인이 필요합니다.',
                          icon:'warning',
                          confirmButtonText:'확인'
                       });
	                }else if(param.result == 'success') {
	                	Swal.fire({
                           title:'회원이 정지되었습니다.',
                           icon:'success',
                           confirmButtonText:'확인'
                        }).then(() => {
                           location.reload();
                        });
	                }else {
	                	Swal.fire({
                           title:'회원정보 변경 오류',
                           icon:'error',
                           confirmButtonText:'확인'
                        });
	                }
	            },
	            error:function() {
	            	Swal.fire({
                       title:'네트워크 오류 발생',
                       icon:'error',
                       confirmButtonText:'확인'
                    });
	            }
	        });
	    });

	    // 취소 버튼 클릭 시
	    $('.cancel-btn').click(function(){
	        var mem_num = $(this).data('mem');
	        $.ajax({
	            url:'/cancelAuth',
	            type:'post',
	            data:{mem_num:mem_num},
	            dataType:'json',
	            success:function(param) {
	                if(param.result == 'logout') {
	                	Swal.fire({
                           title:'로그인이 필요합니다.',
                           icon:'warning',
                           confirmButtonText:'확인'
                        });
	                }else if(param.result == 'success') {
	                	Swal.fire({
                           title:'회원 정지가 취소되었습니다.',
                           icon:'success',
                           confirmButtonText:'확인'
                        }).then(() => {
                           location.reload();
                        });
	                }else {
	                	Swal.fire({
                           title:'회원정보 변경 오류',
                           icon:'error',
                           confirmButtonText:'확인'
                        });
	                }
	            },
	            error:function() {
	            	Swal.fire({
                       title:'네트워크 오류 발생',
                       icon:'error',
                       confirmButtonText:'확인'
                    });
	            }
	        });
	    });
	});
    </script>
</div>