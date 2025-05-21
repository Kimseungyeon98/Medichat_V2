<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<script type="text/javascript">
 	$(function(){
 		$('#register_form').submit(function(){
 				if($('#healthy_title').val().trim() ==''){
 					alert('제목을 입력하세요.');
 					$('#healthy_title').val('').focus();
 					return false;
 				}
 				if($('#healthy_content').val().trim() ==''){
 					alert('내용을 입력하세요.');
 					$('#healthy_content').val('').focus();
 					return false;
 				}
 			
 		})
 		
 	});
</script>
<div class="page-main">
	<div class="page-one" style="padding-top: 16px;">
		<span class="text-lightgray fw-7 fs-13">홈 > 건강 블로그 > 건강매거진 > 글
			수정</span>
		<h2 style="margin-top: 16px;">&nbsp;&nbsp;&nbsp;건강 블로그</h2>

		<hr size="1" width="80%">

		<form:form action="healthUpdate" id="register_form" method="post"
			modelAttribute="healthyBlogVO" enctype="multipart/form-data">
			<form:hidden path="healthy_num" />
			<ul>
				<li><form:input path="healthy_title" placeholder="제목을 입력하세요" />
					<form:errors path="healthy_title" cssClass="error-color" /></li>
				<li><form:textarea path="healthy_content"
						placeholder="내용을 입력하세요" /> <form:errors path="healthy_content"
						cssClass="error-color" /> <script>
						 function MyCustomUploadAdapterPlugin(editor) {
							    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
							        return new UploadAdapter(loader);
							    }
							}
						 
						 ClassicEditor
				            .create( document.querySelector( '#healthy_content' ),{
				            	extraPlugins: [MyCustomUploadAdapterPlugin]
				            })
				            .then( editor => {
								window.editor = editor;
							} )
				            .catch( error => {
				                console.error( error );
				            } );
			    </script></li>
				<li><form:label path="upload">파일업로드</form:label> <input
					type="file" id="upload" name="upload"></li>

			</ul>
			<div class="align-center">
				<form:button class="default-btn">수정</form:button>
				<input type="button" class="default-btn" value="목록"
					onclick="location.href='${pageContext.request.contextPath}/health/healthBlog'">
			</div>

		</form:form>
	</div>
</div>