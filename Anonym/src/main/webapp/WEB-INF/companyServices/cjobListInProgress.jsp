<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="teamproject.vo.*"%>
<%@ include file="../include/header.jsp" %>
<%
	List<JobpostingVO> jpList = (List<JobpostingVO>)request.getAttribute("jpList");

	PagingUtil paging = (PagingUtil)request.getAttribute("paging");
	int nowPage = paging.getNowPage();
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/company_service_list.css" />

<!-- 메인 컨텐츠 -->
<main>
  <div class="main-container">
    <!-- 현재 진행 중인 공고 -->
    <div class="div_container">
      <section class="board-container">
      <div>
        <div class="apply_title">
          <h3><img src="https://img.icons8.com/?size=100&id=53426&format=png&color=000000" width="20px"> 현재 진행 중인 공고</h3>
        </div>
        <div class="apply-container">
          <!-- 공고 리스트 -->
            <%
            int cnt = 0;
            for (JobpostingVO jpvo : jpList) {
              cnt++;
              if(cnt%3 == 1){
            %>
            <div class="apply_list">
            <%
              }
            %>
              <div class="company_apply">
                <a href="cjobView.do?job_posting_no=<%= jpvo.getJob_posting_no() %>">
                  <div class="company_logo">
                    <img src="<%= request.getContextPath() %>/user/down.do?fileName=<%= jpvo.getCompany_logo() %>" width="164px" height="110px">
                  </div>
                  <div class="company_name">
                    <%= jpvo.getCompany_name() %>
                  </div>
                  <div class="company_title">
                    <%= jpvo.getJob_posting_title() %>
                  </div>
                </a>
              </div>
              <% 
              // 공고가 3개씩 표시될 때마다 새로운 apply_list 열기 및 닫기
              if (cnt % 3 == 0) {
           	  %>
                </div>
              <%
             		 }
            	}
              if (cnt != 9) {
              %>
                <div class="apply_list">
             <% 
          	} 
           	%>
          	</div>
          </div>
        </div>
      </section>
    </div>
        
    <!-- 페이지네이션 -->
    <div class="pagination">
      <%
        if (paging.getStartPage() > 1) { 
      %>
        <!-- 이전 페이지 링크 -->
        <a href="cjobListInprogress.do?nowPage=<%= paging.getStartPage() - 1 %>">&lt;</a>
      <%
        }
        for (int i = paging.getStartPage(); i <= paging.getEndPage(); i++) {
          if (i == nowPage) {
      %>
        <a class="active"><%= i %></a>
      <%
          } else {
      %>
        <a href="cjobListInprogress.do?nowPage=<%= i %>"><%= i %></a>
      <%
          }
        }
        if (paging.getLastPage() > paging.getEndPage()) {
      %>
        <!-- 다음 페이지 링크 -->
        <a href="cjobListInprogress.do?nowPage=<%= paging.getEndPage() + 1 %>">&gt;</a>
      <%
        }
      %>
    </div>
  </div>
</main>

<%@ include file="../include/footer.jsp" %>
