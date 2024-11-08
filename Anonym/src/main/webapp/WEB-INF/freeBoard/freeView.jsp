<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="teamproject.vo.*" %>
<%@ page import="java.util.*" %> 
<%@ include file="../include/header.jsp" %>
<% 
request.setCharacterEncoding("UTF-8");

// 글 내용 조회
PostVO vo = (PostVO)request.getAttribute("vo");

System.out.println(vo.getPost_no());

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
System.out.println("List.size()" + List.size());
%>  
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
                  	<h2><%= vo.getPost_title() %></h2>
                  	<div class="post-info">
						<div class="post-info1">
							<span>
							<%
                             if(vo.getUser_company() != null && !vo.getUser_company().equals(""))
                             {
                             	%>
                             		<%= vo.getUser_company() %>
                             	<%
                             }else
                             {
                             	%>
                              	무직
                             	<%
                             }
                             %>
							 · <%= vo.getUser_id() %></span>
							<span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc "> <%= vo.getPost_registration_date() %></span>
							<span><img src="https://img.icons8.com/?size=100&id=RzBtKwnyPvYk&format=png&color=cccccc "> <%= vo.getPost_hit()  %></span>
							<span><img src="https://img.icons8.com/?size=100&id=gFDEU6xka9fu&format=png&color=cccccc "> <%= pccount %></span>
	                   	</div>
						<div class="post-info2 dropdown">
						    <span class="dropdown_btn">
						        <img src="https://img.icons8.com/?size=100&id=SyMGbCSBNAUh&format=png&color=000000" alt="Dropdown Button">
						    </span>
						    <div class="dropdown_menu">
						    <%
						    
						    if(loginUser != null && vo.getUser_no().equals(Integer.toString(loginUser.getUser_no())))
						    {
						    %>
					        	<div class="dropdown_link">
						            <a href="freeModify.do?pno=<%= vo.getPost_no() %>">수정</a>
						        </div>
						        <div class="dropdown_link">
					            	<a href="freeDelete.do?pno=<%= vo.getPost_no() %>">삭제</a>
						        </div>
						        <%
					        }else if(loginUser != null && loginUser.getUser_type().equals("A"))
						    {
					        	%>
					        	<div class="dropdown_link">
					            	<a href="freeDelete.do?pno=<%= vo.getPost_no() %>">삭제</a>
						        </div>
					        	<%
						    }else
						    {
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
				</div>
			</div>
                 
			<!-- 게시글 본문 -->
			<div class="post-content">
				<%= vo.getPost_content() %>
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
						<a href="likeOk.do?pno=<%= vo.getPost_no() %>&uno=<%= loginUser.getUser_no() %>&plstate=E"><span><img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=000000 "> <%= lpcnt %></span></a>
						<% 
					}else if(plstate.equals("E"))
					{
						%>
						<a href="likeOk.do?pno=<%= vo.getPost_no() %>&uno=<%= loginUser.getUser_no() %>&plstate=D"><span><img src="https://img.icons8.com/?size=100&id=epmKwF1Z2gYn&format=png&color=000000"> <%= lpcnt %></span></a>
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
						<input type="hidden" name="pno" value="<%= vo.getPost_no() %>">
						<input type="hidden" name="uno" value="<%= loginUser.getUser_no() %>">
						<div><%= loginUser.getUser_id() %></div>
						<div class="comment-input">
					  		<textarea placeholder="댓글을 남겨주세요." name="content"></textarea>
					  		<button type="button" onclick="commentRegister()" class="rbtn">등록</button>
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
									<span class="comment-author">
									<%
		                             if(comment.getUser_company() != null && !comment.getUser_company().equals(""))
		                             {
		                             	%>
		                             		<%= comment.getUser_company() %>
		                             	<%
		                             }else
		                             {
		                             	%>
		                              	무직
		                             	<%
		                             }
		                             %>
									 - <%= comment.getUser_id() %></span>
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
									            <a onclick="commentModify(<%= comment.getPost_comment_no() %>, <%= comment.getUser_no() %>, '<%= comment.getUser_id() %>', '<%= comment.getUser_company() %>');">수정</a>
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
									<span id="crdate"><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc "> <%= comment.getPost_comment_registration_date() %></span>
								</div>
							</div>
							
							
							
							<%
						}
					}
					%>
					<form name="commentDeleteForm" action="commentDelete.do" method="post">
						<input type="hidden" name="cno">
						<input type="hidden" name="pno" value="<%= vo.getPost_no() %>">
					</form>
				</div>
			</div>
		</section>
	
	<!-- aside -->
		<aside class="sidebar">
	    	<section class="rcmd_list">
		        <h2>추천 글</h2>
	            <a href="freeView.do">
	                런닝 좋아하시는 분들
	            </a>
	            <a href="#">
	                런닝 좋아하시는 분들
	            </a>
	            <a href="#">
	                런닝 좋아하시는 분들
	            </a>
	            <a href="#">
	                런닝 좋아하시는 분들
	            </a>
	            <a href="#">
	                런닝 좋아하시는 분들
	            </a>
	            <a href="#">
	                런닝 좋아하시는 분들
	            </a>
	            <a href="#">
	                런닝 좋아하시는 분들
	            </a>
	            <a href="#">
	                런닝 좋아하시는 분들
	            </a>
	            <a href="#">
	                런닝 좋아하시는 분들
	            </a>
	            <a href="#">
	                런닝 좋아하시는 분들
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
            <form id="reportForm" action="complaintOk.do" method="POST">
            	<input type="hidden" name="uno" value="1">
				<input type="hidden" name="pno" value="<%= vo.getPost_no() %>">
                <select name="reason" id="reasonSelect" required>
                    <option value="" disabled selected>사유를 선택하세요</option>
                    <option value="abusive_language">욕설 / 혐오발언</option>
                    <option value="spam">스팸</option>
                    <option value="false_information">허위 정보</option>
                    <option value="other">기타</option>
                </select>
                <textarea name="reason_detail" id="detailsTextarea" placeholder="기타 사유를 입력해주세요" rows="3"></textarea><br><br>
                <button type="submit">신고하기</button>
            </form>
        </div>
    </div>
    

<%@ include file="../include/footer.jsp" %>

<script>

// 드롭다운
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


function commentRegister(){
	
	// 로그인시에만 submit 처리
	<%-- let loginUno = '<%= session.getAttribute("no") %>';  --%>
	
	// 'null'로 출력되기 때문에 'null'!
	/* if(loginUno != 'null')
	{
	}else
	{
		alert("로그인 후 이용할 수 있습니다.");		
	} */
	
	document.commentForm.action = "commentRegister.do";
	
	document.commentForm.submit();
	
}

let originHtml = "";

//댓글 수정 클릭시 html 변경
function commentModify(cno, uno, uid, cname){
	
	console.log($("#comment"+cno+" p").text());
	console.log(originHtml);

	originHtml = $("#comment" + cno).html();
	let content = $("#comment" + cno + " p").text();

	$("#comment" + cno).css("padding","10px 0 10px 0");
	$("#comment"+cno).html(`
			<div class="comment-Modify">
				<div>${uid}</div>
				<div class="comment-input">
		  		<textarea id="content" name="content" style="width:714px;">`+content+`</textarea><br>
		  		<div class="sc">
		  		<button type="button" onclick="commentSave(` + cno + `, ` + uno + `, '` + uid + `', '` + cname + `')" class="rbtn">등록</button>
		  		<button type="button" onclick="commentCancle(` + cno + `)" class="rbtn">취소</button>
				</div>
				</div>
			</div>
			`);
	
    // 동적으로 생성된 저장 버튼에 클릭 이벤트 추가
    $("#saveBtn" + cno).on('click', function() {
        commentSave(cno, uno, uid, cname);
    });
}

// 댓글 수정 후 취소
function commentCancle(cno){
	$("#comment"+cno).html(originHtml);
	
}

//댓글 수정 후 저장
function commentSave(cno, uno, uid, cname){
	let rdate = $("#crdate").val();
	$.ajax({
		url: "<%= request.getContextPath() %>/freeBoard/commentModify.do",
		type: "post",
		data: {
			cno : cno,
			uno : uno,
			content : $("#content").val(),
			pno : <%= vo.getPost_no() %>
		},
		success: function(data){
			console.log($("#content").val());
			$("#comment" + cno).css("padding","20px 10px 20px 10px");
			$("#comment"+cno).html(`
					<div class="md">
						<span class="comment-author">` + cname + ` - ` + uid + `</span>
						<div class="dropdown">
					    	<span class="dropdown_btn">
					        	<img src="https://img.icons8.com/?size=100&id=SyMGbCSBNAUh&format=png&color=000000" alt="Dropdown Button">
					    	</span>
					    	<div class="dropdown_menu" style="left: 50%;">
				        		<div class="dropdown_link">
					            	<a onclick="commentModify(` + cno + `, ` + uno + `, ` + uid + `);">수정</a>
					        	</div>
						        <div class="dropdown_link">
					            	<a href="freeDelete.do?pno=<%= vo.getPost_no() %>">삭제</a>
						        </div>
							</div>
						</div>
					</div>	
					<p>`+ $("#content").val() +`</p>
					<div class="comment-footer">
						<span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc ">`+rdate+`</span>
					</div>
					`); 
			/* $("#comment" + cno).html(originHtml);
            $("#comment" + cno + " p").html($("#content" + cno).val());
            originHtml = $("#comment" + cno).html(); */
		}
	});
}

// 댓글 삭제
function commentDelete(cno)
{
	
	/* alert(cno); */
	
	// 삭제버튼 클릭시 받은 pk 값 cno를 입력양식 cno의 값으로 대입
	document.commentDeleteForm.cno.value = cno;
	document.commentDeleteForm.submit();
}
<%-- 
// 대댓글 작성
function subcommentRegister(pcpno, pno, uid){
	
	$("#comment-footer"+pcpno).css("padding-bottom","20px");
	$("#comment-footer"+pcpno).css("border-bottom","1px solid #ddd");
	
	$("#subcomment"+pcpno).html(`
			<div class="Subcomment-Register">
				<div>`+uid+`</div>
				<div class="comment-input">
		  		<textarea id="content" name="content" style="width:714px;"></textarea><br>
		  		<div class="sc">
		  		<button type="button" onclick="subcommentSave(`+pcpno+`, '${uid}')" class="rbtn">등록</button>
		  		<button type="button" onclick="subcommentCancle(`+pcpno+`)" class="rbtn">취소</button>
		  		</div>
				</div>
			</div>
			`);
}

// 대댓글 작성 후 취소
function subcommentCancle(pcpno){
	
	$("#comment-footer"+pcpno).css("padding-bottom","0");
	$("#comment-footer"+pcpno).css("border-bottom","none");
	
	$("#subcomment"+pcpno).empty();;
}

// 대댓글 작성 후 저장
function subcommentSave(pcpno, pno, uid){
	let rdate = $("#crdate").val();
	$.ajax({
		url: "<%= request.getContextPath() %>/freeBoard/subcommentRegister.do",
		type: "post",
		data: {
			uno : <%= loginUser.getUser_no() %>,
			content : $("#content").val(),
			pno : <%= vo.getPost_no() %>
		},
		success: function(data){
			console.log($("#content").val());
			//$("#comment" + cno).css("padding","10px");
			$("#comment" + pcpno).css("backgound-color","#eee");
			$("#comment" + pcpno).html(`
					<div class="md">
					<span class="comment-author">인천환경공단 - '${uid}'</span>
					<div class="dropdown">
				    	<span class="dropdown_btn">
				        	<img src="https://img.icons8.com/?size=100&id=SyMGbCSBNAUh&format=png&color=000000" alt="Dropdown Button">
				    	</span>
				    	<div class="dropdown_menu" style="left: 50%;">
			        		<div class="dropdown_link">
				            	<a onclick="commentModify(` + pcpno + `, ` + uno + `, ` + uid + `);">수정</a>
				        	</div>
					        <div class="dropdown_link">
				            	<a href="freeDelete.do?pno=<%= vo.getPost_no() %>">삭제</a>
					        </div>
						</div>
					</div>
				</div>	
				<p>`+ $("#content").val() +`</p>
				<div class="comment-footer">
					<span><img src="https://img.icons8.com/?size=100&id=IETmQUFWcgXs&format=png&color=cccccc ">`+rdate+`</span>
				</div>
					`); 
			/* $("#comment" + cno).html(originHtml);
            $("#comment" + cno + " p").html($("#content" + cno).val());
            originHtml = $("#comment" + cno).html(); */
		}
	});
} --%>


</script>
