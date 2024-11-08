<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/freeboard_view.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/freeRefort.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />

        <!-- 메인 -->
        <main>
            <div class="main-container">
                <section class="free_view-section">
                    <div class="post-container">
                        <!-- 게시글 정보 -->
                        <div class="post-header">
                          <h2>내장산 등산 갈건데 정읍 맛집 추천좀!!</h2>
                          <div class="post-info">
                          	<div class="post-info1">
								<span>공무원 starryfox12</span>
								<span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc "> 2024.10.31</span>
								<span><img src="https://img.icons8.com/?size=100&id=RzBtKwnyPvYk&format=png&color=cccccc "> 132</span>
								<span><img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=cccccc "> 4</span>
		                   	</div>
							<div class="post-info2 dropdown">
						    	<span class="dropdown_btn">
						        <img src="https://img.icons8.com/?size=100&id=SyMGbCSBNAUh&format=png&color=000000" alt="Dropdown Button">
						    	</span>
						    	<div class="dropdown_menu">
						        	<div class="dropdown_link">
							            <a href="freeModify.jsp">수정</a>
							        </div>
							        <div class="dropdown_link">
						            	<a href="#">삭제</a>
							        </div>
							        <div class="dropdown_link">
						            	<a id="reportBtn">신고</a>
							        </div>
						    	</div>
							</div>
                        </div>
                      
                        <!-- 게시글 본문 -->
                        <div class="post-content">
                          <p>내돈내산 키워드 넣어서 검색했는데도 뭔가 그렇다할 맛집이 안보이네ㅜㅜ</p>
                          <span><img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=000000 "> 1</span>
                          <span><img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=000000 "> 4</span>
                        </div>

                        <!-- 댓글 섹션 -->
                        <div class="comments-section">
                          <h3>댓글 4</h3>
                          <div class="comment-input">
                            <input type="text" placeholder="댓글을 남겨주세요." />
                          </div>
                      
                          <!-- 댓글 목록 -->
                          <div class="comment-list">
                            <div class="comment">
                              	<div style="display: flex; justify-content: space-between;">
	                              	<span class="comment-author">인천환경공단 - djakljs</span>
	                              	<div class="dropdown">
									    <span class="dropdown_btn">
									        <img src="https://img.icons8.com/?size=100&id=SyMGbCSBNAUh&format=png&color=000000" alt="Dropdown Button">
									    </span>
								    <div class="dropdown_menu" style="left: 50%;">
							        	<div class="dropdown_link">
								            <a>수정</a>
								        </div>
								        <div class="dropdown_link">
							            	<a>삭제</a>
								        </div>
								    </div>
                              		</div>
								</div>
                              <p>솜씨만두</p>
                              <div class="comment-footer">
                                <span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc "> 2022.11.21</span>
                                <span><img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=cccccc "> 4</span>
                                <span><img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=cccccc "></span>
                              </div>
                            </div>
                      
                            <div class="comment">
                              <span class="comment-author">새회사 - iiliiii</span>
                              <p>본가 갈비탕, 돗가비 갈비찜, 진고개 김치찌개, 단풍미인 한우</p>
                              <div class="comment-footer">
                                <span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc "> 2022.11.21</span>
                                <span><img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=cccccc "> 9</span>
                                <span><img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=cccccc "></span>
                              </div>
                            </div>
                      
                            <div class="comment">
                              <span class="comment-author">한국기술공사 - zz</span>
                              <p>다이네막창</p>
                              <div class="comment-footer">
                                <span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc "> 2022.11.21</span>
                                <span><img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=cccccc "> 좋아요</span>
                                <span><img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=cccccc "></span>
                              </div>
                            </div>

                            <div class="comment">
                              <span class="comment-author">현대중공업 - qwer</span>
                              <p>형연락행 나 정읍이얌 ㅎ</p>
                              <div class="comment-footer">
                                <span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc "> 2022.11.21</span>
                                <span><img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=cccccc "> 좋아요</span>
                                <span><img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=cccccc "></span>
                              </div>
                            </div>
                          </div>
                        </div>
                    </div>
                </section>
                <!-- aside -->
                <aside class="sidebar">
                    <section class="rcmd_list">
                        <h2>추천 글</h2>
                        <a href="freeView.jsp">
                            런닝 좋아하시는 분들
                        </a>
                        <a href="#">
                            2010년생 요크셔테리어인데 현재..
                        </a>
                        <a href="#">
                            4박 5일 해외여행지 추천
                        </a>
                        <a href="#">
                            후쿠오카 숙소
                        </a>
                        <a href="#">
                            면세찬스 이정도면 잘사는거지?
                        </a>
                        <a href="#">
                            60대 남자 트레킹화 추천 좀 해줘!
                        </a>
                        <a href="#">
                            아이패드 미니 vs ebook
                        </a>
                        <a href="#">
                            이직횟수
                        </a>
                        <a href="#">
                            기아형들 도움!!! 헬프!!!
                        </a>
                        <a href="#">
                            현대카드 신입 면접관련 질문
                        </a>
                            
                    </section>
                </aside>
            </div>
        </main>
<!-- 모달 창 -->
    <div id="reportModal" class="modal" style="display: none;">
        <div class="modal-content">
            <!-- <span class="close"><a href="freeView.jsp">&times;</a></span> -->
            <span class="close" onclick="closeModal()">&times;</span>
            <h2>게시글 신고하기</h2>
            <p>신고 사유를 선택해주세요:</p>
            <form id="reportForm" action="report.jsp" method="POST">
                <select name="reason" id="reasonSelect" required>
                    <option value="" disabled selected>사유를 선택하세요</option>
                    <option value="욕설 / 혐오발언">욕설 / 혐오발언</option>
                    <option value="스팸">스팸</option>
                    <option value="허위 정보">허위 정보</option>
                    <option value="기타">기타</option>
                </select>
                <textarea name="details" placeholder="기타 사유를 입력해주세요" rows="3"></textarea><br><br>
                <button type="button">신고하기</button>
            </form>
        </div>
    </div>

<%@ include file="../include/footer.jsp" %>

<script>
document.addEventListener('DOMContentLoaded', function () {
    const dropdownButtons = document.querySelectorAll('.dropdown_btn');
    const dropdownMenus = document.querySelectorAll('.dropdown_menu');

    // 각각의 드롭다운에 이벤트 바인딩
    dropdownButtons.forEach((btn, index) => {
        btn.addEventListener('click', function (event) {
            event.stopPropagation(); // 이벤트 전파 방지
            closeAllDropdowns(); // 다른 드롭다운 닫기
            dropdownMenus[index].classList.toggle('show');
        });
    });

    // 외부 클릭 시 모든 드롭다운 닫기
    window.addEventListener('click', function () {
        closeAllDropdowns();
    });

    // 모든 드롭다운 닫기 함수
    function closeAllDropdowns() {
        dropdownMenus.forEach(menu => menu.classList.remove('show'));
    }
});
</script>