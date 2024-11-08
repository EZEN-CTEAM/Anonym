<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%
List<UserVO> ulist = (List<UserVO>)request.getAttribute("ulist");
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage_styles.css" />

    <!-- 메인 컨텐츠 -->
    <main>
        <div class="main-container10">
            <!-- 회원 관리 -->
            <section class="approval-section">
                
                    <div class="header-row">
                        <div class="company-header">
                            <h3>비활성 회원</h3>
                        </div>
                    </div>
                   <%
                   for(UserVO uvo : ulist){
					%>
					<div class="company-item">
                        <div class="company-name" onclick="toggleDetails(this)">
                            <%= uvo.getUser_id() %>
                        </div>
                        <div class="company-details">
	                        <div>
	                            <div>닉네임</div>
	                            <div class="company-value"><%= uvo.getUser_nickname() %></div>
	                        </div>
	                        <div>
	                            <div>가입 일자</div>
	                            <div class="company-value"><%= uvo.getUser_registration_date() %></div>
	                        </div>
	                        <div>
	                            <div>재직 상태</div>
	                            <div class="company-value">
	                            <%
	                            if(uvo.getUser_employment().equals("I")){
	                            	%><div>무직</div><%	
	                            }else{
	                            	%><div>재직 중</div><%	
	                            }
	                            %>
	                            </div>
	                        </div>
	                        <div>
	                            <div>회사명</div>
	                            <div class="company-value">.</div>
	                        </div>
                        </div>
                    </div>
					<%                	   
                   }
                   %> 
            </section>
        </div>
    </main>

<%@ include file="../include/footer.jsp" %>

<script>
function toggleDetails(element) {
    const details = element.nextElementSibling; // 다음 요소인 company-details를 선택
    if (details.style.display === "none" || details.style.display === "") {
        details.style.display = "block"; // 보이게
    } else {
        details.style.display = "none"; // 숨기기
    }
}
</script>