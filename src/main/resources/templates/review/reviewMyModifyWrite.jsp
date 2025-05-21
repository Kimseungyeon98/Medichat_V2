<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/review.css"
	type="text/css">
	<script type="text/javascript">
		$(function(){
			$('#wrev').submit(function(event){
				if($('#rev_title').val().length>30){
					alert('30자 미만으로 작성해주세요');
					$('#rev_title').focus();
					return false;
				}
				if($('#rev_title').val().trim()==''){
					alert('리뷰 제목 필수 입력');
					$('#rev_title').val('').focus();
					return false;
				}
				if($('#rev_content').val().length>300){
					alert('300자 미만으로 작성해주세요');
					$('#rev_content').focus();
					return false;
				}
				if($('#rev_content').val().trim()==''){
					alert('리뷰 내용 필수 입력');
					$('#rev_content').val('').focus();
					return false;
				}
				
			})
		})	
	</script>
<div class="align-center" style="padding: 0 auto;">
	<div class="align-left" style="margin: 0 auto; width: 60%;">
		<form:form action="updateReview" method="post"  id="wrev" modelAttribute="reviewVO">
		<br><br>
		<h4>후기 수정</h4>
		<br>
		<h2><b>&nbsp;&nbsp;&nbsp;&nbsp;병원명 : ${hos_name}</b></h2>
		<br>
		<div class="align-center float-clear" style="position:relative;">
		<div class="align-center" style="width: 330px; margin: 0 auto;">
			<div class="rating" style="zoom: 2; position:absolute; margin: 0 auto;">
				<input type="radio" class="star" id="star5" name="rev_grade" value="5" <c:if test="${reviewVO.rev_grade == 5.0}">checked</c:if> />
				<label title="Excellent!" for="star5" class="rating__label  half">
					<svg xmlns="http://www.w3.org/2000/svg" height="1em"
						viewBox="0 0 576 512">
								      <path
							d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"></path>
								    </svg>
				</label> <input type="radio" class="star" id="star4-5" name="rev_grade"
					value="4.5" <c:if test="${reviewVO.rev_grade == 4.5}">checked</c:if> /> <label title="Excellent!" for="star4-5"
					class="rating__label"> <svg
						xmlns="http://www.w3.org/2000/svg" height="1em"
						viewBox="0 0 576 512">
								      <path
							d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"></path>
								    </svg>
				</label> <input type="radio" class="star" id="star4" name="rev_grade" value="4" <c:if test="${reviewVO.rev_grade == 4.0}">checked</c:if> />
				<label title="Excellent!" for="star4" class="rating__label half">
					<svg xmlns="http://www.w3.org/2000/svg" height="1em"
						viewBox="0 0 576 512">
								      <path
							d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"></path>
								    </svg>
				</label> <input type="radio" class="star" id="star3-5" name="rev_grade"
					value="3.5" <c:if test="${reviewVO.rev_grade == 3.5}">checked</c:if> /> <label title="Excellent!" for="star3-5"
					class="rating__label"> <svg
						xmlns="http://www.w3.org/2000/svg" height="1em"
						viewBox="0 0 576 512">
								      <path
							d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"></path>
								    </svg>
				</label> <input type="radio" class="star" id="star3" name="rev_grade" value="3" <c:if test="${reviewVO.rev_grade == 3.0}">checked</c:if> />
				<label title="Excellent!" for="star3" class="rating__label half">
					<svg xmlns="http://www.w3.org/2000/svg" height="1em"
						viewBox="0 0 576 512">
								      <path
							d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"></path>
								    </svg>
				</label> <input type="radio" class="star" id="star2-5" name="rev_grade"
					value="2.5"  <c:if test="${reviewVO.rev_grade == 2.5}">checked</c:if>/> <label title="Excellent!" for="star2-5"
					class="rating__label"> <svg
						xmlns="http://www.w3.org/2000/svg" height="1em"
						viewBox="0 0 576 512">
								      <path
							d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"></path>
								    </svg>
				</label> <input type="radio" class="star" id="star2" name="rev_grade" value="2"  <c:if test="${reviewVO.rev_grade == 2.0}">checked</c:if> />
				<label title="Excellent!" for="star2" class="rating__label half">
					<svg xmlns="http://www.w3.org/2000/svg" height="1em"
						viewBox="0 0 576 512">
								      <path
							d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"></path>
								    </svg>
				</label> <input type="radio" class="star" id="star1-5" name="rev_grade"
					value="1.5" <c:if test="${reviewVO.rev_grade == 1.5}">checked</c:if> /> <label title="Excellent!" for="star1-5"
					class="rating__label"> <svg
						xmlns="http://www.w3.org/2000/svg" height="1em"
						viewBox="0 0 576 512">
								      <path
							d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"></path>
								    </svg>
				</label> <input type="radio" class="star" id="star1" name="rev_grade" value="1" <c:if test="${reviewVO.rev_grade == 1.0}">checked</c:if>/>
				<label title="Excellent!" for="star1" class="rating__label half">
					<svg xmlns="http://www.w3.org/2000/svg" height="1em"
						viewBox="0 0 576 512">
								      <path
							d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"></path>
								    </svg>
				</label> <input type="radio" class="star" id="star0-5" name="rev_grade"
					value="0.5" <c:if test="${reviewVO.rev_grade == 0.5}">checked</c:if> /> <label title="Excellent!" for="star0-5"
					class="rating__label"> <svg
						xmlns="http://www.w3.org/2000/svg" height="1em"
						viewBox="0 0 576 512">
								      <path
							d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"></path>
								    </svg>
				</label>
			</div>
		</div>
		<br><br><br><br><span style="position:relative;">별점을 클릭하여 선택</span><br><br><br>
				<form:hidden path="rev_num"/>
				<form:input  path="rev_title" placeholder="리뷰 제목을 입력해주세요" style="width:95%; height: 40px; border-radius: 5px;" /><br><br>
				<form:textarea rows="8"  path="rev_content"  style="resize:none; width:95% !important; border-radius:5px;" placeholder="리뷰 내용은 300자까지 입력가능합니다." ></form:textarea>
				<br><br>
				<input type="submit" value="후기수정" class="default-btn">
		</div>
		</form:form>
		<br><br><br><br>
	</div>
</div>