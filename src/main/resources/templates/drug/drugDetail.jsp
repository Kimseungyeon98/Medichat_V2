<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/kyj.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/kyj.css" type="text/css">
<style>
ul li {
    margin-bottom: 50px; /* 리스트 아이템들 간의 간격을 넓힘 */
}
.drug-btn {
    display: flex;
    justify-content: center; /* 버튼들을 가운데 정렬 */
    flex-wrap: nowrap; /* 버튼들을 한 줄에 나열 */
}

.drug-btn .button {
    border-radius: 0; /* 버튼의 모서리를 둥글지 않게 함 */
    margin: 0; /* 버튼들 간의 간격을 없앰 */
    flex: 1; /* 버튼을 균등하게 배치 */
    text-align: center; /* 버튼 텍스트 가운데 정렬 */
}
</style>
<!-- 의약품 목록 상세 시작 -->
<div class="page-main main-margin" style="margin:40px 40px 0px 40px;">
	<div class="body-header">
		<p class="text-lightgray fw-7 fs-13" style="margin-top:10px;">홈 > 의약품 백과사전</p>
		<h1>의약품 상세정보</h1>
	</div><br>
	<div class="drug-body">
		<h4>제품 기본 정보</h4>
		<table class="table table-bordered">
			<tr>
				<td class="drug-table-title fw-6">제품명</td>
				<td>${drug.drg_name}</td>
				<td class="drug-table-image" rowspan="3">
					<c:if test="${empty drug.drg_img}">
						<img src="${pageContext.request.contextPath}/images/noIMG.png"width="100">
					</c:if>
					<c:if test="${!empty drug.drg_img}">
						<img class="drg-img" src="${drug.drg_img}" width="200">
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="drug-table-title fw-6">업체명</td>
				<td>${drug.drg_company}</td>
			</tr>
			<tr>
				<td class="drug-table-title fw-6">품목 기준 코드</td>
				<td>${drug.drg_code}</td>
			</tr>
		</table>
	<br>
		<h4>허가정보 · 복약정보</h4>
		<!-- 모든 데이터가 존재하지 않을 경우 -->
		<c:if test="${empty drug.drg_dosage && empty drug.drg_effect && empty drug.drg_warning && empty drug.drg_precaution && empty drug.drg_interaction && empty drug.drg_seffect && empty drug.drg_storage}">
			<div class="warin fs-24">
				제공되는 데이터가 없습니다
			</div>
		</c:if>
		<!-- 데이터가 하나 이상 존재할 경우 -->
		<c:if test="${!empty drug.drg_dosage || !empty drug.drg_effect || !empty drug.drg_warning || !empty drug.drg_precaution || !empty drug.drg_interaction || !empty drug.drg_seffect || !empty drug.drg_storage}">
		<div class="drug-btn align-center drug-btn-space">
			<c:if test="${!empty drug.drg_dosage}">
				<button class="button" onclick="scrollToSection('dosage')">복용법</button>
			</c:if>
			<c:if test="${!empty drug.drg_effect}">
				<button class="button" onclick="scrollToSection('effect')">효능·효과</button>
			</c:if>
			<c:if test="${!empty drug.drg_warning || !empty drug.drg_precaution || !empty drug.drg_interaction}">
				<button class="button" onclick="scrollToSection('warning')">주의사항</button>
			</c:if>
			<c:if test="${!empty drug.drg_seffect}">
				<button class="button" onclick="scrollToSection('seffect')">부작용</button>
			</c:if>
			<c:if test="${!empty drug.drg_storage}">
				<button class="button" onclick="scrollToSection('storage')">보관방법</button>
			</c:if>
		</div>
		<br>
		<ul class="list-margin">
			<c:if test="${!empty drug.drg_dosage}">
				<li id="dosage">
					<h5>복용법</h5>
					<div>${drug.drg_dosage}</div>
				</li>
			</c:if>
			<c:if test="${!empty drug.drg_effect}">
				<li id="effect">
					<h5>효능·효과</h5>
					<div>${drug.drg_effect}</div>
				</li>	
			</c:if>
			<c:if test="${!empty drug.drg_warning || !empty drug.drg_precaution || !empty drug.drg_interaction}">
				<li id="warning">
					<h5>주의사항</h5>
					<c:if test="${!empty drug.drg_warning}">
						<div>${drug.drg_warning}</div><br>
					</c:if>
					<c:if test="${!empty drug.drg_precaution}">
						<div>${drug.drg_precaution}</div><br>
					</c:if>
					<c:if test="${!empty drug.drg_interaction}">
						<div>${drug.drg_interaction}</div>
					</c:if>
				</li>
			</c:if>
			<c:if test="${!empty drug.drg_seffect}">
				<li id="seffect">
					<h5>부작용</h5>
					<div>${drug.drg_seffect}</div>
				</li>
			</c:if>
			<c:if test="${!empty drug.drg_storage}">
				<li id="storage">
					<h5>보관방법</h5>
					<div>${drug.drg_storage}</div>
				</li>
			</c:if>
		</ul>
		</c:if>
	</div>
</div>
<!-- 의약품 목록 상세 끝 -->
<!-- 모달창 시작 -->
<div class="modal">
	<div class="drugImg-modal">
		<div class="modal-header bg-green-5">
			<div class="fw-5 fs-20">의약품 이미지 확대보기</div>
			<div class="close">&times;</div>
		</div>
		<div class="modal-body">
			<img class="drugModal-img" src="${drug.drg_img}" width="300">
		</div>
		<br>
	</div>
</div>
<!-- 모달창 끝 -->
<!-- 스크립트 시작 -->
<script>
window.onload=function(){
	const modal = document.querySelector(".modal");
	const drgImg = document.querySelector(".drg-img");
	const close = document.querySelector(".close");
	
	//모달창 열기
	drgImg.onclick = function(){
		modal.style.display = "flex";
	};
	
	//모달창 닫기
	close.onclick = function(){
		modal.style.display = "none";
	};
	
	// 모달 창 외부를 클릭하면 모달 창 닫기
	if (event.target == modal) {
		modal.style.display = "none";
	}

};

//섹션으로 스크롤하는 함수
function scrollToSection(sectionId) {
	const element = document.getElementById(sectionId);
	element.scrollIntoView({ behavior: 'smooth' });
}
</script>
<!-- 스크립트 끝 -->