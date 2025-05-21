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
 				if($('#faq_title').val().trim() ==''){
 					alert('제목을 입력하세요.');
 					$('#faq_title').val('').focus();
 					return false;
 				}
 				if($('#faq_content').val().trim() ==''){
 					alert('내용을 입력하세요.');
 					$('#faq_content').val('').focus();
 					return false;
 				}
 			
 		})
 		
 	});
</script>
<div class="page-main">
	<div class="page-one" style="padding-top:16px;">
		<span class="text-lightgray fw-7 fs-13">홈 > 자주 묻는 질문(FAQ) > 글쓰기</span>	
		<h3 style="margin-top:16px;">&nbsp;&nbsp;&nbsp;자주 묻는 질문(FAQ) </h3>
		
		<hr size="1" width="80%">
		
		<form:form action="faqWrite" id="register_form" method="post" modelAttribute="faqVO" enctype="multipart/form-data">
				<ul>
					<li>
						<select name="f_category" id="selectinputw">
							<option value="a" >건강</option>
							<option value="b">미용</option>
							<option value="c" >홍보</option>
						</select>
		
						<form:input path="faq_title" placeholder="제목을 입력하세요"/>
						<form:errors path="faq_title" cssClass="error-color"/>
					</li>
					<li>
						<form:textarea path="faq_content" placeholder="자주 묻는 질문과 답변을 입력해주세요"/>
						<form:errors path="faq_content" cssClass="error-color"/>
						<script>
							 function MyCustomUploadAdapterPlugin(editor) {
								    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
								        return new UploadAdapter(loader);
								    }
								}
							 
							 ClassicEditor
					            .create( document.querySelector( '#faq_content' ),{
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
				<form:button class="default-btn">글쓰기</form:button>
				<input type="button" class="default-btn" value="목록" onclick="location.href='${pageContext.request.contextPath}/faq/faqList'">
			</div>
		
			</form:form>
	</div>
</div>