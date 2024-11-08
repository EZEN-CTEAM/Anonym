<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="teamproject.vo.*"%>
<%@ include file="../include/header.jsp" %>
<%
	/* CompanyVO loginUserc = (CompanyVO)session.getAttribute("loginUserc"); */
%>

<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/company_service_write.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

  <!-- 메인 컨텐츠 -->
<main>
	<div class="main-container">
		<section class="company_service_container">
			<div class="company_service_title">
				<h2>기업서비스</h2>
			</div>
				<form action="cjobRegister.do" method="post" onsubmit="return submitForm(event)">
				<!-- <input type="hidden" name="user_no" value="1"> -->
				<!-- <input type="hidden" name="board_no" value="3"> -->
				<%-- <input type="hidden" name="company_no" value="<%= loginUserc.getCno() %>"> --%>
				<div class="write_container">
					<div>
						<!-- 제목 입력 -->
						<div class="write_item">제목</div>
							<input type="text" name="job_posting_title" class="input_area">

						<!-- 채용 유형 선택 -->
						<div class="write_item">채용 유형</div>
							<div class="radioButtonStyle">
								<label class="radioStyle">
									<input type="radio" name="job_posting_kind" value="C">
									<span>계약직</span>
								</label>
								<label class="radioStyle">
									<input type="radio" name="job_posting_kind" value="F">
									<span>정규직</span>
								</label>
							</div>
 
						<!-- 마감일 입력 -->
						<div class="write_item">마감일</div>
							<input type="date" name="job_posting_period" class="input_area">

						<!-- 내용 입력(toast 에디터) -->
						<div class="write_item">내용</div>
							<div id="editor"></div>

							<!-- 숨겨진 필드 추가 -->
							<textarea id="post_content" name="job_posting_content" style="display: none;"></textarea>
	
						<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
						<script>
							const editor = new toastui.Editor({
							    el: document.querySelector('#editor'),
							    height: '500px',
							    initialEditType: 'wysiwyg',
							    previewStyle: 'vertical',
							    placeholder: '내용을 입력해주세요.'
							    });
							
							// submitForm 함수: 폼 제출 시 호출됨
						    function submitForm(event) {
						        event.preventDefault();  // 기본 제출 동작 방지
						        
						        // 에디터 내용을 숨겨진 필드에 복사
						        const content = editor.getMarkdown();
						        document.getElementById('post_content').value = content;

						        // 폼 제출
						        event.target.submit();
						    }
						</script>
					</div>
				</div>
				<div>
					<input class="save_button" type="submit" value="저장하기">
					<input class="cancle_button" type="reset" value="취소하기" onclick="location.href='company_cjobList.do';">
				</div>
			</form>
		</section>
	</div>
</main>

<%@ include file="../include/footer.jsp" %>
