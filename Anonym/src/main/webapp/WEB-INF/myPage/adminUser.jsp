<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%
List<UserVO> ulist = (List<UserVO>)request.getAttribute("ulist");
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminReport.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage_styles.css" />

<%-- 
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
    </main> --%>

	        <!-- 메인 컨텐츠 -->
        <main>
            <div class="container">
		        <h1>비활성 회원</h1>
		        
                <!-- 검색 섹션 -->
		        <div class="search-container">
		            <input type="text" id="contentInput" class="input" placeholder="아이디 검색">
		            <button onclick="filterReports()">검색</button>
		        </div>
		        
		        <table class="table">
		            <thead>
		                <tr>
		                    <th>회원 번호</th>
		                    <th>작성자 ID</th>
		                    <th>닉네임</th>
		                    <th>가입일</th>
		                </tr>
		            </thead>
		            <tbody id="reportTableBody">
						<%
		                   for(UserVO uvo : ulist){
						%>
						<tr>
		                    <td><%= uvo.getUser_no() %></td>
		                    <td><%= uvo.getUser_id() %></td>
		                    <td><%= uvo.getUser_nickname() %></td>
		                    <td><%= uvo.getUser_registration_date() %></td>
		                </tr>
						<%	
						}
						%>		                
		                
		            </tbody>
		        </table>
		    </div>
        </main>

<%@ include file="../include/footer.jsp" %>

<script>
/* function toggleDetails(element) {
    const details = element.nextElementSibling; // 다음 요소인 company-details를 선택
    if (details.style.display === "none" || details.style.display === "") {
        details.style.display = "block"; // 보이게
    } else {
        details.style.display = "none"; // 숨기기
    }
} */

function filterReports() {
    // 셀렉트 및 입력 값 가져오기
    const userId = document.getElementById("contentInput").value.toLowerCase();

    // 테이블의 행들을 가져와서 필터링
    const rows = document.getElementById("reportTableBody").getElementsByTagName("tr");
    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName("td");
        const userIdCell = cells[1].textContent.toLowerCase();

        // 조건에 맞는지 확인하여 표시 여부 결정
        if (userIdCell.includes(userId)) {
            rows[i].style.display = ""; // 조건이 맞으면 표시
        } else {
            rows[i].style.display = "none"; // 조건이 맞지 않으면 숨기기
        }
    }
}
</script>