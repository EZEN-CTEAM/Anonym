<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="teamproject.vo.*"%>
<%@ include file="../include/header.jsp" %>
<%
	JobpostingVO jpList = (JobpostingVO)request.getAttribute("jpvo");
%>
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/company_service_write.css" />

  <!-- 메인 컨텐츠 -->
  <main>
    <div class="main-container">
      <section class="company_service_container">
        <div class="company_service_title">
          <h2>기업서비스</h2>
        </div>
        <form action="cjobModify.do" method="post" onsubmit="return submitForm(event)">
        	<input type="hidden" name="job_posting_no" value="<%= jpList.getJob_posting_no() %>">
          <div class="write_container">
            <div>
              <div class="write_item">제목</div>
              <input type="text" name="job_posting_title" class="input_area" value="<%= jpList.getJob_posting_title() %>">
              <!-- 채용 유형 선택 -->
			  <div class="write_item">채용 유형</div>
				<div class="radioButtonStyle">
					<label class="radioStyle">
						<input type="radio" name="job_posting_kind" value="C" <% if ("C".equals(jpList.getJob_posting_kind())) { %> checked <% } %>>
						<span>계약직</span>
					</label>
					<label class="radioStyle">
						<input type="radio" name="job_posting_kind" value="F" <% if ("F".equals(jpList.getJob_posting_kind())) { %> checked <% } %>>
						<span>정규직</span>
					</label>
			  </div>
              <div class="write_item">마감일</div>
              <input type="date" name="job_posting_period" class="input_area" value="<%= jpList.getJob_posting_period() %>">
              <div class="write_item">내용</div>
              <!-- 에디터 추가 -->
              <div id="editor"></div>
              <textarea id="post_content" name="job_posting_content" style="display: none;"></textarea>
            </div>
          </div>
          <div>
            <button type="submit" class="save_button">저장하기</button>
            <button class="cancle_button">취소하기</button>
          </div>
              <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
              <script>
                  const Editor = toastui.Editor;

                  const editor = new Editor({
                      el: document.querySelector('#editor'),
                      height: '500px',
                      initialEditType: 'wysiwyg',
                      previewStyle: 'vertical',
                      initialValue: '<%= jpList.getJob_posting_content() %>'
                      });

                  function submitForm(event) {
				        event.preventDefault();  // 기본 제출 동작 방지
				        
				        // 에디터 내용을 숨겨진 필드에 복사
				        const content = editor.getMarkdown();
				        document.getElementById('post_content').value = content;

				        // 폼 제출
				        event.target.submit();
				    }
              </script>
              <!-- 에디터 추가 -->
        </form>
      </section>
    </div>
  </main>

<%@ include file="../include/footer.jsp" %>
