<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<% 
request.setCharacterEncoding("UTF-8");
PostVO pvo = (PostVO)request.getAttribute("pvo");
%>  
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/c_review_2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
<%@ include file="../include/companyInfo.jsp" %>

            <div class="main-container">
                <section class="review-form-section">
                    <div class="review-form">
                        <h2>글 수정</h2>
                    
                        <form action="communityModify.do" method="POST" onsubmit="return submitForm(event)">
                        	<input type="hidden" name="user_no" value="<%= loginUser.getUser_no() %>">
                        	<input type="hidden" name="board_no" value="2">
							<input type="hidden" name="cno" value="<%= cno %>">
							<input type="hidden" name="pno" value="<%= pvo.getPost_no() %>">
                    
                            <!-- 제목 입력 -->
                            <div class="form-group">
                                <label for="review-title">제목</label>
                                <input type="text" id="review-title" name="post_title" value="<%= pvo.getPost_title() %>"/>
                            </div>
                            
							<!-- 내용 입력 -->
							<div class="form-group">
							    <label for="editor-content">내용</label>
							    <div id="editor"></div>
							    <textarea id="editor-content" name="post_content" style="display:none;"></textarea>
							</div>
						
							<button type="submit" class="submit-btn">수정하기</button>
						
						  	<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
							<script>
							    // 에디터 초기화
							    const editor = new toastui.Editor({
							        el: document.querySelector('#editor'),
							        initialEditType: 'wysiwyg',
							        previewStyle: 'vertical',
							        height: '500px',
							        initialValue: '<%=  pvo.getPost_content() %>',
							    });
							    
							
							    // 폼 제출 시 호출될 함수
							    function submitForm(event) {
							        event.preventDefault();  // 기본 제출 동작 방지
							
							        // const content = editor.getMarkdown();  
							        const content = editor.getHTML();  // HTML 형식으로 가져오기
							        document.getElementById('editor-content').value = content;
							
							        // 에러가 없으면 폼을 실제로 제출
							        event.target.submit();
							    }
                                <!-- 에디터 추가 -->
							</script>
                        </form>
                    </div>
                </section>
        
<%@ include file="../include/aside.jsp" %>
<%@ include file="../include/footer.jsp" %>
