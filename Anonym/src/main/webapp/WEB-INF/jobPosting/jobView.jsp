<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="../include/header.jsp" %>
<%
	List<JobpostingVO> jpList = (List<JobpostingVO>)request.getAttribute("jpList");
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/recruitment_information_view.css" />

  <!-- 메인 컨텐츠 -->
  <main>
    <div class="main-container">
      <div>
      <!-- 기업정보 요약 -->
      <section class="board-container">
        <div class="summation_title">
          <h3>기업 요약 정보</h3>
        </div>
        <div>
        <%
	       	for(JobpostingVO jpvo : jpList)
	       	{
       	%>
          <div class="summation_container">
            <div class="summation_list_container">
              <div class="summation_list_itembox">
                <div class="summation_list_item">회사명</div>
                <div class="summation_list_item">위치</div>
                <div class="summation_list_item">직원수</div>
              </div>
              <div class="summation_list_resultbox">
                <div class="summation_list_item"><%= jpvo.getCompany_no() %></div>
                <div class="summation_list_item"><%= jpvo.getCompany_location() %></div>
                <div class="summation_list_item"><%= jpvo.getCompany_employee() %></div>
              </div>
            </div>
            <div class="summation_list_container">
              <div class="summation_list_itembox">
                <div class="summation_list_item">업계</div>
                <div class="summation_list_item">설립</div>
                <div class="summation_list_item">마감일</div>
              </div>
              <div class="summation_list_resultbox">
                <div class="summation_list_item"><%= jpvo.getCompany_industry() %></div>
                <div class="summation_list_item"><%= jpvo.getCompany_location() %></div>
                <div class="summation_list_item"><%= jpvo.getJob_posting_period() %></div>
              </div>
            </div>
          </div>
      </section>

      <!-- 채용 정보 -->
      <section class="board-container">
        <div class="apply">
          <h3>채용 정보</h3>
        </div>
        <div class="apply_title_container">
          <div class="apply_title"><%= jpvo.getJob_posting_title() %></div>
          <div class="apply_addr"><%= jpvo.getCompany_location() %></div>
        </div>
        <div class="apply_content_container">
          <%= jpvo.getJob_posting_content().replace("\n","<br>") %>
        </div>
      </section>
      <section>
          <a href="jobApply.do?job_posting_no=<%= jpvo.getJob_posting_no() %>" class="apply_button">지원하기</a>
      </section>
    </div>
    </div>
    <%
		}
    %>
  </main>

<%@ include file="../include/footer.jsp" %>
