<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- CK 에디터 -->
<script src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<!-- 커뮤니티 글쓰기 시작 -->
<div class="page-main">
	<div class="cboard-write-body">
		<p class="text-lightgray fw-7 fs-13">홈 > 커뮤니티 > 글작성</p>
		<form:form action="write" modelAttribute="communityVO">
			<ul class="cboard-write">
				<li>
					<form:select path="cbo_type" class="form-control-sm cboard-select">
						<option disabled="disabled" selected>-카테고리-</option>
						<form:option value="1">질환고민</form:option>
						<form:option value="2">다이어트·헬스</form:option>
						<form:option value="3">피부고민</form:option>
						<form:option value="4">임신·성고민</form:option>
						<form:option value="5">탈모고민</form:option>
						<form:option value="6">마음건강</form:option>
						<form:option value="7">뼈와관절</form:option>
						<form:option value="8">영앙제</form:option>
						<form:option value="9">자유게시판</form:option>
					</form:select>
				</li>
				<li>
					<form:input path="cbo_title" class="form-control" placeholder="제목" maxlength="50"/>
				</li>
				<li>
					<form:textarea path="cbo_content"/>
					<script>
					 function MyCustomUploadAdapterPlugin(editor) {
						    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
						        return new UploadAdapter(loader);
						    }
						}
					 
					 ClassicEditor
			            .create( document.querySelector( '#cbo_content' ),{
			            	extraPlugins: [MyCustomUploadAdapterPlugin]
			            })
			            .then( editor => {
							window.editor = editor;
							editor.ui.view.editable.element.style.height = '500px'; // CKEditor 높이 설정
						} )
			            .catch( error => {
			                console.error( error );
			            } );
				    </script>
				</li>
			</ul>
			<div>
				<form:button class="write-btn" style="margin-bottom:50px;">등록</form:button>
			</div>
		</form:form>
	</div>
</div>
<!-- 커뮤니티 글쓰기 끝 -->
<style>
    .ck-editor__editable {
        height: 500px !important;
        overflow: auto;
    }
</style>
