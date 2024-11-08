<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="teamproject.vo.*"%>
<%@ include file="../include/header.jsp" %>
<%
	List<JobpostingVO> jpList = (List<JobpostingVO>)request.getAttribute("jpList");
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/company_service_list.css" />

  <!-- 메인 컨텐츠 -->
  <main>
    <div class="main-container">
    <!-- 현재 진행 중인 공고 -->
        <div class="div_container">
          <section class="board-container">
            <div class="apply_title">
              <h3><img src="https://img.icons8.com/?size=100&id=53426&format=png&color=000000" width="20px"> 마감된 공고</h3>
              <a href="company_service_write(에디터 추가).do" class="implyeement_write">글작성</a>
            </div>
            <div class="apply-container">
                <div class="apply_list">
			<%
			int i = 0;
          	for(JobpostingVO jpvo : jpList)
          		{
          		i++;
         	%>
	            <div class="company_apply">
	              <a href="cjobView.do?job_posting_no=<%= jpvo.getJob_posting_no() %>">
	                <div class="company_logo">
	                  <img src="<%= request.getContextPath() %>/upload/<%= jpvo.getCompany_logo() %>" width="164px" height="110px">
	                </div>
	                <div class="company_name">
	                  <%= jpvo.getCompany_name() %>
	                </div>
	                <div class="company_title">
	                  <%= jpvo.getJob_posting_title() %>
	                </div>
	                <div>
	                  <img src="https://img.icons8.com/?size=100&id=26089&format=png&color=#FCFF7B" width="17px">
	                </div>
	              </a>
	            </div>
            <%
           	if(i%3 == 0) {
           	%>
          		</div>
            <%
           		}
      		}
      		%>
            </div>
        </div>
	        <div class="pagination">
	          <a href="#">&laquo;</a> <!-- 이전 페이지 -->
	          <a href="#" class="active">1</a> <!-- 현재 페이지 -->
	          <a href="#">2</a>
	          <a href="#">3</a>
	          <a href="#">4</a>
	          <a href="#">5</a>
	          <a href="#">&raquo;</a> <!-- 다음 페이지 -->
	        </div>
        </section>
      </div>
    </div>
  </main>

<%@ include file="../include/footer.jsp" %>
