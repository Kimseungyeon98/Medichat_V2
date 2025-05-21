<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>

<div class="page-main">
	<div class="page-one" style="padding-top:16px;">
		<span class="text-lightgray fw-7 fs-13">홈 > 자주 묻는 질문(FAQ) >  글수정</span>
		<h3 style="margin-top:16px;">&nbsp;&nbsp;&nbsp;자주 묻는 질문(FAQ) </h3>
		
		<hr size="1" width="80%">
		
		<form:form action="faqUpdate" id="register_form" method="post" modelAttribute="faqVO" enctype="multipart/form-data">
				<form:hidden path="faq_num"/>
				<ul>
					<li>
						<select name="f_category" id="selectinputw">
							<option value="a"<c:if test="${faqVO.f_category =='a'}">selected</c:if> >건강</option>
							<option value="b" <c:if test="${faqVO.f_category =='b'}">selected</c:if>>미용</option>
							<option value="c" <c:if test="${faqVO.f_category =='c'}">selected</c:if>>홍보</option>
						</select>
					</li>
					<li>
						<form:input path="faq_title" placeholder="제목을 입력하세요"/>
						<form:errors path="faq_title" cssClass="error-color"/>
					</li>
					<li>
						<form:textarea path="faq_content" placeholder="영상링크만 첨부해주세요"/>
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
				<form:button class="default-btn">글수정</form:button>
				<input type="button" class="default-btn" value="목록" onclick="location.href='${pageContext.request.contextPath}/faq/faqList'">
			</div>
		
			</form:form>
	</div>
</div>