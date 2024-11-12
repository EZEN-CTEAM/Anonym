<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<% 
request.setCharacterEncoding("UTF-8");

// 글 내용 조회
PostVO pvo = (PostVO)request.getAttribute("pvo");

// 좋아요 상태
String plstate = "D";
if(request.getAttribute("plstate") != null) plstate = (String)request.getAttribute("plstate");

// 좋아요 개수
int lpcnt = 0;
if(request.getAttribute("lpcnt") != null) lpcnt = (Integer)request.getAttribute("lpcnt");

// 댓글 개수
int pccount = 0;
if(request.getAttribute("pccount") != null) pccount = (Integer)request.getAttribute("pccount");

// 댓글 목록
List<PostCommentVO> List = null;
if(request.getAttribute("List") != null) List = (List<PostCommentVO>)request.getAttribute("List");
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/c_review_2.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/freeRefort.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
<%@ include file="../include/companyInfo.jsp" %>   </div>

            <div class="main-container">
                <section class="community-view-section">
                    <div class="post-container">
                        <!-- 게시글 정보 -->
                        <div class="post-header">
                          <h2><%= pvo.getPost_title() %></h2>
                          <div class="post-info">
                          <div class="post-info1">
                            <span><%= pvo.getUser_id() %></span>
                            <span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc "> <%= pvo.getPost_registration_date() %></span>
                            <span><img src="https://img.icons8.com/?size=100&id=RzBtKwnyPvYk&format=png&color=cccccc "> <%= pvo.getPost_hit()  %></span>
                            <span><img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=cccccc "> <%= pccount  %></span>
                          </div>
                          <div class="post-info2 dropdown">
						    <span class="dropdown_btn">
						        <img src="https://img.icons8.com/?size=100&id=SyMGbCSBNAUh&format=png&color=000000" alt="Dropdown Button">
						    </span>
						    <div class="dropdown_menu">
						    <%
						    if(loginUser != null && pvo.getUser_no().equals(Integer.toString(loginUser.getUser_no())))
						    {
						    %>
					        	<div class="dropdown_link">
						            <a href="communityModify.do?pno=<%= pvo.getPost_no() %>&cno=<%= cno %>">수정</a>
						        </div>
						        <div class="dropdown_link">
					            	<a href="communityDelete.do?pno=<%= pvo.getPost_no() %>&cno=<%= cno %>">삭제</a>
						        </div>
						        <%
					        }else if(loginUser != null && loginUser.getUser_type().equals("A"))
						    {
					        	%>
					        	<div class="dropdown_link">
					            	<a href="communityDelete.do?pno=<%= pvo.getPost_no() %>&cno=<%= cno %>">삭제</a>
						        </div>
					        	<%
						    }else{
						    	%>
						        <div class="dropdown_link">
					            	<a id="reportBtn">신고</a>
						        </div>
						    	<%
						    }
						        %>
						    </div>
						</div>
                        </div>
                      
                        <!-- 게시글 본문 -->
                        <div class="post-content">
                        	<%= pvo.getPost_content() %>
				<div class="post-content2">
				<%
				if(session.getAttribute("loginUser") == null)
				{
                    %>
					<a href="<%= request.getContextPath() %>/user/login_p.do"><span><img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=000000 "> <%= lpcnt %></span></a>
					<% 
				}else
				{
	                if(plstate.equals("D"))
			      	{
	                    %>
						<a href="likeOk.do?pno=<%= pvo.getPost_no() %>&uno=<%= loginUser.getUser_no() %>&plstate=E&cno=<%= cno %>"><span><img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=000000 "> <%= lpcnt %></span></a>
						<% 
					}else if(plstate.equals("E"))
					{
						%>
						<a href="likeOk.do?pno=<%= pvo.getPost_no() %>&uno=<%= loginUser.getUser_no() %>&plstate=D&cno=<%= cno %>"><span><img src="https://img.icons8.com/?size=100&id=epmKwF1Z2gYn&format=png&color=000000"> <%= lpcnt %></span></a>
	   					<%
					}
				}
				%>
					<span><img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=000000 "> <%= pccount %></span>
				</div>
            </div>

                      
			<!-- 댓글 섹션 -->
			<div class="comments-section">
				<h3>댓글 <%= pccount %></h3>
				<%
				if(loginUser != null)
				{
					%>
					<!-- 댓글 등록 -->
					<form name="commentForm" action="" method="post" >
					<div class="comment-Register">
						<input type="hidden" name="pno" value="<%= pvo.getPost_no() %>">
						<input type="hidden" name="uno" value="<%= loginUser.getUser_no() %>">
						<div><%= loginUser.getUser_id() %></div>
						<div class="comment-input">
					  		<textarea placeholder="댓글을 남겨주세요." name="content"></textarea>
					  		<button type="button" onclick="commentRegister(<%= cno %>)" class="crbtn">등록</button>
							<!-- <input type="text" placeholder="댓글을 남겨주세요." /> -->
						</div>
					</div>
					</form>
					<%
				}
				if (session.getAttribute("loginUserc") != null) 
				{
					%>
					<!-- 댓글 등록 -->
					<div class="comment-Register">
						<div>
							<span><a href="<%= request.getContextPath() %>/user/login_p.do" style="font-size: 13px;">개인 회원 가입 후 댓글에 참여해보세요! ></a></span>
						</div>
					</div>
					<%
				}
				if (!isUserLoggedIn) 
				{
					%>
					<!-- 댓글 등록 -->
					<div class="comment-Register">
						<div>
							<span><a href="<%= request.getContextPath() %>/user/login_p.do" style="font-size: 13px;">지금 가입하고 댓글에 참여해보세요! ></a></span>
						</div>
					</div>
					<%
				}
				%>

				<!-- 댓글 목록 -->
				<div class="comment-list">
					<%
					if(List != null)
					{
						for(PostCommentVO comment : List)
						{
							%>
							<div class="comment" id="comment<%= comment.getPost_comment_no() %>">
								<div class="md">
									<span class="comment-author"><%= comment.getUser_id() %></span>
									<%
								    if(loginUser != null && comment.getUser_no().equals(Integer.toString(loginUser.getUser_no())))
								    {
									%>
									<div class="dropdown">
									    <span class="dropdown_btn">
									        <img src="https://img.icons8.com/?size=100&id=SyMGbCSBNAUh&format=png&color=000000" alt="Dropdown Button">
									    </span>
									    <div class="dropdown_menu" style="left: 50%;">
								        	<div class="dropdown_link">
									            <a onclick="commentModify(<%= comment.getPost_comment_no() %>, <%= comment.getUser_no() %>, '<%= comment.getUser_id() %>');">수정</a>
									        </div>
									        <div class="dropdown_link">
								            	<a onclick="commentDelete(<%= comment.getPost_comment_no() %>);">삭제</a>
									        </div>
									    </div>
									</div>
									<%
								    }
									%>
								</div>	
								<p><%= comment.getPost_comment_content() %></p>
								<div class="comment-footer" id="comment-footer<%= comment.getPost_comment_no() %>">
									<img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc "> 
									<span id="crdate<%= comment.getPost_comment_no() %>"><%= comment.getPost_comment_registration_date() %></span>
									<!-- <span><img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=cccccc "> 4</span> -->
<%-- 									<span>
										<a onclick="subcommentRegister(<%= comment.getPost_comment_no() %>, <%= pvo.getPost_no() %>, '<%= loginUser.getUser_id() %>');">
											<!-- <img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=cccccc "> 3 -->
											답글쓰기
										</a>
									</span> --%>
								</div>
								<div id="subcomment<%= comment.getPost_comment_no() %>" class="subcomment">
									<!-- 답글추가되는곳 -->
								</div>
							</div>
							<%
						}
					}
					%>
					<form name="commentDeleteForm" action="commentDelete.do" method="post">
						<input type="hidden" name="c_no">
						<input type="hidden" name="pno" value="<%= pvo.getPost_no() %>">
						<input type="hidden" name="cno" value="<%= cno %>">
					</form>
					<!-- <div class="comment">
						<span class="comment-author">새회사 - *****</span>
						<p>감사합니다</p>
						<div class="comment-footer">
							<span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc "> 2022.11.21</span>
							<span><img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=cccccc "> 9</span>
							<span><img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=cccccc "> 1</span>
						</div>
					</div>
	
					<div class="comment">
						<span class="comment-author">한국기술공사 - *****</span>
						<p>NCS는 어떤 식으로 나오나요? 후기가 없네요..ㅠ</p>
						<div class="comment-footer">
							<span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc "> 2022.11.21</span>
							<span><img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=cccccc "> 좋아요</span>
							<span><img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=cccccc "></span>
						</div>
					</div> -->
				</div>
			</div>
		</section>
<!-- 모달 창 -->
    <div id="reportModal" class="modal" style="display: none;">
        <div class="modal-content">
            <!-- <span class="close"><a href="freeView.jsp">&times;</a></span> -->
            <span class="close" onclick="closeModal()">&times;</span>
            <h2>게시글 신고하기</h2>
            <p>신고 사유를 선택해주세요:</p>
            <form id="reportForm" action="complaintOk.do" method="POST">
            	<input type="hidden" name="cno" value="<%= cno %>">
				<input type="hidden" name="pno" value="<%= pvo.getPost_no() %>">
                <select name="reason" id="reasonSelect" required>
                    <option value="" disabled selected>사유를 선택하세요</option>
                    <option value="욕설 / 혐오발언">욕설 / 혐오발언</option>
                    <option value="스팸">스팸</option>
                    <option value="허위 정보">허위 정보</option>
                    <option value="기타">기타</option>
                </select>
                <textarea name="reason_detail" id="detailsTextarea" placeholder="기타 사유를 입력해주세요" rows="3"></textarea><br><br>
                <button type="submit">신고하기</button>
            </form>
        </div>
    </div>
    
<script>

//드롭다운
document.addEventListener('DOMContentLoaded', function () {
    const dropdownButtons = document.querySelectorAll('.dropdown_btn');
    const dropdownMenus = document.querySelectorAll('.dropdown_menu');

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

// 모달
// 모달 열기
document.getElementById("reportBtn").onclick = function() {
    document.getElementById("reportModal").style.display = "block";
};

// 모달 닫기
function closeModal() {
    document.getElementById("reportModal").style.display = "none";
}

// 모달 바깥을 클릭했을 때 닫기
window.onclick = function(event) {
    const modal = document.getElementById("reportModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
};

// 기타 선택시 
document.getElementById("reasonSelect").addEventListener("change", function() {
    var detailsTextarea = document.getElementById("detailsTextarea");
    if (this.value === "other") {
        detailsTextarea.disabled = false;
        detailsTextarea.focus();
    } else {
        detailsTextarea.disabled = true;
        detailsTextarea.value = ""; // 기타가 아닌 경우 내용 초기화
    }
});



function commentRegister(cno){
	
/* 	alert(cno); */
	
	document.commentForm.action = "commentRegister.do?cno=<%= cno %>";
	
	document.commentForm.submit(cno);
	
}

let originHtml = "";

//댓글 수정 클릭시 html 변경
function commentModify(cno, uno, uid){
	
	originHtml = $("#comment" + cno).html();
	/* console.log(originHtml); */
	let content = $("#comment" + cno + " p").text();
	/* console.log($("#comment"+cno+" p").text()); */
	$("#comment" + cno).css("padding","10px 0 10px 0");
	$("#comment"+cno).html(`
			<div class="comment-Modify">
				<div>${uid}</div>
				<div class="comment-input">
		  		<textarea id="content" name="content" style="width:714px;">`+content+`</textarea><br>
		  		<div class="sc">
		  		<button type="button" onclick="commentSave(` + cno + `, ` + uno + `, '` + uid + `')" class="rbtn">등록</button>
		  		<button type="button" onclick="commentCancle(` + cno + `)" class="rbtn">취소</button>
				</div>
				</div>
			</div>
			`);
}
//댓글 수정 후 취소
function commentCancle(cno){
	$("#comment"+cno).html(originHtml);
	
}

//댓글 수정 후 저장
function commentSave(cno, uno, uid){
	console.log($("#crdate"+cno).text());
	let rdate = $("#crdate" + cno).text();
	console.log("crdate:"+rdate);
	$.ajax({
		url: "<%= request.getContextPath() %>/freeBoard/commentModify.do",
		type: "post",
		data: {
			cno : cno,
			uno : uno,
			content : $("#content" + cno).val(),
			pno : <%= pvo.getPost_no() %>
		},
		success: function(data){
			console.log($("#content").val());
			$("#comment" + cno).css("padding","20px 10px 20px 10px");
			$("#comment"+cno).html(`
					<div class="md">
						<span class="comment-author">` + uid + `</span>
						<div class="dropdown">
					    	<span class="dropdown_btn">
					        	<img src="https://img.icons8.com/?size=100&id=SyMGbCSBNAUh&format=png&color=000000" alt="Dropdown Button">
					    	</span>
					    	<div class="dropdown_menu" style="left: 50%;">
				        		<div class="dropdown_link">
					            	<a onclick="commentModify(` + cno + `, ` + uno + `, ` + uid + `);">수정</a>
					        	</div>
						        <div class="dropdown_link">
					            	<a href="communityDelete.do?pno=<%= pvo.getPost_no() %>">삭제</a>
						        </div>
							</div>
						</div>
					</div>	
					<p>`+ $("#content").val() +`</p>
					<div class="comment-footer">
						<span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc ">${rdate}</span>
					</div>
					`); 
			/* $("#comment" + cno).html(originHtml);
          $("#comment" + cno + " p").html($("#content" + cno).val());
          originHtml = $("#comment" + cno).html(); */
		}
	});
}
//댓글 삭제
function commentDelete(c_no)
{
	
	/* alert(c_no); */
	
	// 삭제버튼 클릭시 받은 pk 값 cno를 입력양식 cno의 값으로 대입
	document.commentDeleteForm.c_no.value = c_no;
	document.commentDeleteForm.submit();
}
</script>

<%@ include file="../include/aside.jsp" %>
<%@ include file="../include/footer.jsp" %>
