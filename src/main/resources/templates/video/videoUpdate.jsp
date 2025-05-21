<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<script  type="text/javascript">
 	$(function(){
 		$('#register_form').submit(function(){
 				if($('#video_title').val().trim() ==''){
 					alert('제목을 입력하세요.');
 					$('#video_title').val('').focus();
 					return false;
 				}
 				if($('#video_content').val().trim() ==''){
 					alert('내용을 입력하세요.');
 					$('#video_content').val('').focus();
 					return false;
 				}
 			
 		})
 		
 	});
</script>
<div class="page-main">
	<div class="page-one" style="padding-top:16px;">
		<span class="text-lightgray fw-7 fs-13">홈 > 건강 블로그 > 건강 비디오 >  글수정</span>	
		<h3 style="margin-top:16px;">&nbsp;&nbsp;&nbsp;건강 비디오 </h3>
		
		<hr size="1" width="80%">
		
		<form:form action="videoUpdate" id="register_form" method="post" modelAttribute="videoVO" enctype="multipart/form-data">
				<form:hidden path="video_num"/>
				<ul>
					<li>
						<select name="v_category" id="selectinputw">
							<option value="a"<c:if test="${videoVO.v_category =='a'}">selected</c:if> >건강</option>
							<option value="b" <c:if test="${videoVO.v_category =='b'}">selected</c:if>>미용</option>
							<option value="c" <c:if test="${videoVO.v_category =='c'}">selected</c:if>>홍보</option>
						</select>
						<form:input path="video_title" placeholder="제목을 입력하세요"/>
						<form:errors path="video_title" cssClass="error-color"/>
					</li>
					<li>
						<form:textarea path="video_content" placeholder="영상링크만 첨부해주세요"/>
						<form:errors path="video_content" cssClass="error-color"/>
						<script>
							 function MyCustomUploadAdapterPlugin(editor) {
								    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
								        return new UploadAdapter(loader);
								    }
								}
							 
							 ClassicEditor
					            .create( document.querySelector( '#video_content' ),{
					            	extraPlugins: [MyCustomUploadAdapterPlugin]
					            })
					            .then( editor => {
									window.editor = editor;
								} )
					            .catch( error => {
					                console.error( error );
					            } );
				    </script> 
					</li>
			</ul>
			<div class="align-center">
				<form:button class="default-btn">글수정</form:button>
				<input type="button" class="default-btn" value="목록" onclick="location.href='${pageContext.request.contextPath}/video/videoList'">
			</div>
		
			</form:form>
	</div>
</div>