<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="teamproject.vo.*" %>
<%@ page import="java.util.*" %>
<%

List<ApplicantVO> alist = (List<ApplicantVO>)request.getAttribute("alist");

%> 
<%@ include file="../include/header.jsp" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage_styles.css" />

        <!-- 메인 컨텐츠 -->
        <main>
            <div class="main-container">
                <!-- 지원 현황 목록 -->
                <section class="apply-count">
                    <div class="apply-count-div">
                        <div>미열람</div>
                        <div class="a-count">0건</div>
                    </div>
                    <div class="apply-count-div">
                        <div>합격</div>
                        <div class="a-count">0건</div>
                    </div>
                    <div class="apply-count-div">
                        <div>불합격</div>
                        <div class="a-count">0건</div>
                    </div>
                    <div class="apply-count-div" id="showAll">
					    <div>전체 보기</div>
					    <div class="a-count">0건</div>
					</div>
                </section>
                <br><br><br>
                <section class="apply-list">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>회사명</th>
                                <th>제목</th>
                                <th>신청일</th>
                                <th>심사 상태</th>
                            </tr>
                        </thead>
                        <tbody>
	                        <%
	                        for(ApplicantVO avo : alist){
	                        %>
	                        <tr data-status="<%= avo.getApplicant_state().equals("E") ? "합격" : avo.getApplicant_state().equals("D") ? "불합격" : "미열람" %>">
	                                <td>
	                                	<!-- <img src="https://img.icons8.com/?size=100&id=99634&format=png&color=ff5252"> -->
	                                	<%= avo.getCompany_name() %>
	                                </td>
	                                <td><%= avo. getResume_title() %></td>
	                                <td><%= avo.getApplicant_registration_date() %></td>
	                                <td>
		                                <%
		                                if(avo.getApplicant_state().equals("E")){
			                                %>
			                                <span>합격</span>
			                                <%	
		                                }else if(avo.getApplicant_state().equals("D")){
			                                %>
			                                <span>불합격</span>
			                                <%	
		                                }else{
		                                	 %>
		 	                                <span>미열람</span>
		 	                                <%	
		                                }
		                                %>
	                                </td>
	                            </tr>
	                        <%	
	                        }
	                        %>
                        </tbody>
                    </table>
                </section>
            </div>
        </main>

		<script>
		document.addEventListener("DOMContentLoaded", () => {
		    const applyListRows = document.querySelectorAll(".apply-list tbody tr");
		    const statusCounts = {
		        "미열람": 0,
		        "합격": 0,
		        "불합격": 0
		    };

		    // 각 상태별로 행 개수를 계산
		    applyListRows.forEach(row => {
		        const status = row.dataset.status;
		        if (statusCounts[status] !== undefined) {
		            statusCounts[status]++;
		        }
		    });

		    // 각 상태별 건수를 apply-count-div에 업데이트
		    document.querySelectorAll(".apply-count-div").forEach(div => {
		        const statusText = div.querySelector("div:first-child").textContent.trim();
		        const countElement = div.querySelector(".a-count");

		        if (statusCounts[statusText] !== undefined) {
		            countElement.textContent = `${statusCounts[statusText]}건`;
		        } else if (statusText === "전체 보기") {
		            // 전체 보기에는 모든 건수를 합산하여 표시
		            const totalCount = Object.values(statusCounts).reduce((sum, count) => sum + count, 0);
		            countElement.textContent = `${totalCount}건`;
		        }
		    });

		    // apply-count-div 클릭 시 필터링
		    document.querySelectorAll(".apply-count-div").forEach(div => {
		        div.addEventListener("click", () => {
		            const selectedStatus = div.querySelector("div:first-child").textContent.trim();

		            // 전체 보기일 경우 모든 행을 표시
		            if (selectedStatus === "전체 보기") {
		                applyListRows.forEach(row => {
		                    row.style.display = "";
		                });
		            } else {
		                // 선택한 상태에 맞는 행만 표시하고 나머지는 숨김
		                applyListRows.forEach(row => {
		                    if (row.dataset.status === selectedStatus) {
		                        row.style.display = "";
		                    } else {
		                        row.style.display = "none";
		                    }
		                });
		            }
		        });
		    });
		});
			
		</script>

<%@ include file="../include/footer.jsp" %>