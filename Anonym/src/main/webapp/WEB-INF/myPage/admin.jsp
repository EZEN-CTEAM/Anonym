<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%
List<CompanyVO> clist = (List<CompanyVO>)request.getAttribute("clist");
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminReport.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage_styles.css" />

        <!-- 메인 컨텐츠 -->
         <main>
            <div class="main-container9">
                <!-- 기업 승인 관리 -->
                <section class="approval-section">
                <div class="header-row">
                    <div class="company-header">
                        <h3>기업 승인 관리</h3>
                    </div>
                </div>
                        
                <form action="admin.do" method="post">
                <% 
                for(CompanyVO cvo : clist){
                %>
                	<div class="company-item">
	                    <div class="company-name" onclick="toggleDetails(this)">
	                        <img src="https://img.icons8.com/?size=100&id=99634&format=png&color=ff5252">
	                        <%= cvo.getCname() %>
	                    </div>
	                    <div class="company-details">
	                    	<input type="hidden" name="company_no" value=<%= cvo.getCno() %>>
	                        <div>
	                            <div>위치</div>
	                            <div class="company-value"><%= cvo.getClocation() %></div>
	                        </div>
	                        <div>
	                            <div>직원수</div>
	                            <div class="company-value"><%= cvo.getCemployee() %></div>
	                        </div>
	                        <div>
	                            <div>업계</div>
	                            <div class="company-value"><%= cvo.getCindustry() %></div>
	                        </div>
	                        <div>
	                            <div>설립일</div>
	                            <div class="company-value"><%= cvo.getCanniversary() %></div>
	                        </div>
	                        <div>
	                            <div>사업자등록번호</div>
	                            <div class="company-value"><%= cvo.getCbrcnum() %></div>
	                        </div>
	                        <div>
	                            <div>사업자등록증</div>
	                            <div class="company-value">
	                                <img src=<%= cvo.getCbrc() %>>
	                            </div>
	                        </div>
	                        <div>
	                            <div>로고</div>
	                            <div class="company-value">
	                                <img src=<%= cvo.getClogo() %>>
	                            </div>
	                        </div>
	                        
	                    </div>
	                    <div class="radioButtonStyle">
	                        <label class="radioStyle">
	                            <input type="radio" name="company_state" value="E">
	                            <span>승인</span>
	                        </label>
	                        <label class="radioStyle">
	                            <input type="radio" name="company_state" value="D">
	                            <span>부결</span>
	                        </label>
	                    </div>
	                </div>
                <%	
                }
                %>
	                <button class="cta-button">저장</button>
                </form>
                </section>
            </div>
        </main> 
        
        <main>
            <div class="container">
		        <h1>기업 승인 관리</h1>
		        
		        <table class="table">
			        <thead>
			            <tr>
			                <th>회사명</th>
			                <th>사업자등록번호</th>
			                <th>가입일</th>
			                <th>승인</th>
			            </tr>
			        </thead>
			        <tbody>
                <% 
                for(CompanyVO cvo : clist)
                {
                %>
			            <tr>
			                <td onclick="showModal('<%= cvo.getCname() %>', '<%= cvo.getCbrc() %>')"><%= cvo.getCname() %></td>
			                <td><%= cvo.getCbrcnum() %></td>
			                <td><%= cvo.getCrdate() %></td>
			                <td>
			                    <form action="admin.do" method="post" onsubmit="submitForm(this)">
			                        <input type="hidden" name="company_no" value=<%= cvo.getCno() %>>
			                        <div class="radioButtonStyle2">
				                        <label class="radioStyle2">
				                            <input type="radio" name="company_state" value="E" onchange="this.form.submit()">
				                            <span>승인</span>
				                        </label>
				                        <label class="radioStyle2">
				                            <input type="radio" name="company_state" value="D" onchange="this.form.submit()">
				                            <span>부결</span>
				                        </label>
				                    </div>
			                    </form>
			                </td>
			            </tr>
			       <%	
	                }
	                %>
			        </tbody>
		        </table>
		    </div>
		    
		        <!-- Modal Structure -->
		    <div id="companyModal" class="modal">
		        <div class="modal-content">
		            <span class="close" onclick="closeModal()">&times;</span>
		            <h2 id="companyName"></h2>
		            <img id="companyImage" src="" alt="Company Image" style="max-width: 100%; height: auto;">
		        </div>
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
    function submitForm(form) {
        form.submit();  // 라디오 버튼을 선택하면 폼을 제출합니다.
    }
    
    function showModal(companyName, imageUrl) {
        // 모달을 표시하고 회사명과 이미지를 설정
        document.getElementById("companyName").innerText = companyName;
        document.getElementById("companyImage").src = imageUrl;

        console.log(imageUrl);
        var modal = document.getElementById("companyModal");
        modal.style.display = "block";
    }

    // 모달 닫기
    function closeModal() {
        var modal = document.getElementById("companyModal");
        modal.style.display = "none";
    }

    // 모달 바깥 영역 클릭 시 모달 닫기
    window.onclick = function(event) {
        var modal = document.getElementById("companyModal");
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

</script>
