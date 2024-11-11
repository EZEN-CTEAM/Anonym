<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%
List<ComplaintVO> cplist = (List<ComplaintVO>)request.getAttribute("cplist");
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminReport.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />

        <!-- 메인 컨텐츠 -->
        <main>
            <div class="container">
		        <h1>신고글 관리 페이지</h1>
		        
                <!-- 검색 섹션 -->
		        <div class="search-container">
		            <label>게시판 종류: </label>
		            <select id="boardTypeSelect" class="select">
		                <option value="">전체</option>
		                <option value="자유 게시판">자유 게시판</option>
		                <option value="사내 게시판">사내 게시판</option>
		            </select>
		            <label>신고 사유: </label>
		            <select id="reasonSelect" class="select">
		                <option value="">전체</option>
		                <option value="욕설 / 혐오 발언">욕설 / 혐오 발언</option>
		                <option value="스팸">스팸</option>
		                <option value="허위 정보">허위 정보</option>
		                <option value="기타">기타</option>
		            </select>
		            <label>신고 내용: </label>
		            <input type="text" id="contentInput" class="input" placeholder="신고 내용 검색">
		            <button onclick="filterReports()">검색</button>
		        </div>
		        
        		<!-- 신고글 목록 테이블 -->
		        <table class="table">
		            <thead>
		                <tr>
		                    <th>게시판</th>
		                    <th>작성자 ID</th>
		                    <th>신고 사유</th>
		                    <th>신고 날짜</th>
		                    <th>글 내용</th>
		                    <th>처리 상태</th>
		                    <th>비활성화</th>
		                </tr>
		            </thead>
		            <tbody id="reportTableBody">
						<%
						for(ComplaintVO cpvo : cplist){
						%>
						<tr>
		                    <td><%= cpvo.getPost_no() %></td>
		                    <td><%= cpvo.getUser_id() %></td>
		                    <td><%= cpvo.getPost_complaint_reason() %></td>
		                    <td><%= cpvo.getPost_complaint_registration_date() %></td>
		                    <td><%= cpvo.getPost_content() %></td>
		                    <td class="status">
		                    	<%
		                    	if("U".equals(cpvo.getUser_state()) || "E".equals(cpvo.getUser_state()) ){
		                    		%>
		                    		<span>미처리</span>
		                    		<%
		                    	}else {
		                    		%>
		                    		<span>처리 완료</span>
		                    		<%
		                    	}
		                    	%>
		                    </td>
		                    <td>
		                        <button class="btn" onclick="location.href='<%= request.getContextPath() %>/myPage/adminReportOk.do?user_id=<%= cpvo.getUser_id() %>'">회원 비활성</button>
		                    </td>
		                </tr>
						<%	
						}
						%>		                
		                
		            </tbody>
		        </table>
		    </div>
        </main>
        
<script>
/* function processReport(reportId) {
    alert(`신고 처리가 완료되었습니다.`);
    document.querySelector(`tr[data-id="${reportId}"] .status`).textContent = '처리 완료';
} */
    
function filterReports() {
    // 셀렉트 및 입력 값 가져오기
    const boardType = document.getElementById("boardTypeSelect").value.toLowerCase();
    const reason = document.getElementById("reasonSelect").value.toLowerCase();
    const content = document.getElementById("contentInput").value.toLowerCase();

    // 테이블의 행들을 가져와서 필터링
    const rows = document.getElementById("reportTableBody").getElementsByTagName("tr");
    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName("td");
        const boardTypeCell = cells[0].textContent.toLowerCase();
        const reasonCell = cells[2].textContent.toLowerCase();
        const contentCell = cells[4].textContent.toLowerCase();

        // 조건에 맞는지 확인하여 표시 여부 결정
        if ((boardType === "" || boardTypeCell.includes(boardType)) &&
            (reason === "" || reasonCell.includes(reason)) &&
            contentCell.includes(content)) {
            rows[i].style.display = ""; // 조건이 맞으면 표시
        } else {
            rows[i].style.display = "none"; // 조건이 맞지 않으면 숨기기
        }
    }
}
</script>

<%@ include file="../include/footer.jsp" %>