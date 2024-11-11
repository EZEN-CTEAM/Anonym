<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="teamproject.*" %>
<%@ include file="../include/header.jsp" %>
<%
	List<JobpostingVO> jList = (List<JobpostingVO>)request.getAttribute("jList");
	List<JobpostingVO> jlList = (List<JobpostingVO>)request.getAttribute("jlList");
	
	String searchValue = (String)request.getParameter("index_search");
	if(searchValue == null || searchValue.equals("null")) searchValue = "";
	
	PagingUtil paging = (PagingUtil)request.getAttribute("paging");
	int nowPage = paging.getNowPage();
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/recruitment_information.css" />

  <!-- 메인 컨텐츠 -->
  <main>
    <div class="main-container">
        <section class="intro">
        	<form action="jobList.do" method="get">
          		<div>
            		<input onkeyup="enterkey();" type="text" name="index_search" size="80" placeholder="관심있는 회사를 검색해보세요 !">
          		</div>
       		</form>
        </section>

        <!-- 인기 채용 -->
        <div class="div_container">
          <section class="board-container">
          <div>
            <div class="apply_title">
              <h3><img src="https://img.icons8.com/?size=100&id=53426&format=png&color=000000" width="20px"> 채용 공고</h3>
            </div>
            <div class="apply-container">
            <div>
              <div class="apply_list">
              	<%
              		int cnt = 0;
		          	for(JobpostingVO jpvo : jList)
		          	{
		          		cnt++;	
          		%>
                <div class="company_apply">
                  <a href="jobView.do?job_posting_no=<%= jpvo.getJob_posting_no() %>">
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
           			if(cnt%3 == 0) {
           		%>
        	  </div>
        	  <%
        	  if(cnt != 9) {
        		  
        	  
        		%>
        	  <div class="apply_list">
            	<%
        	  			}
           			}
      			}
      			%>
            </div>
            </div>
            <div>
	            <aside class="aside_container">
	            <div>
	              <div class="new_apply_list_title">
	                새로 업데이트 된 공고
	              </div>
	              <%
		          	for(JobpostingVO jplvo : jlList)
		          	{	
	        	  %>
	        	  <a href="<%= request.getContextPath() %>/jobPosting/jobView.do?job_posting_no=<%= jplvo.getJob_posting_no() %>">
		              <div class="new_apply_list">
		                <div>
		                  <img src="<%= request.getContextPath() %>/user/down.do?fileName=<%= jplvo.getCompany_logo() %>" width="75px">
		                </div>
		                <div class="new_apply_company_name">
		                  <%= jplvo.getCompany_name() %>
		                  <div class="new_apply_title">
		                  <%= jplvo.getJob_posting_title() %>
		                  </div>
		                </div>
		              </div>
	              </a>
	              <%
		          	}
	              %>
	            </div>
	          </aside>
	          </div>
            </div>
            </div>
          </section>
        </div>
		<div class="pagination">
		<%
		if(paging.getStartPage() > 1)
		{
			// 시작페이지가 1보다 큰 경우 이전 페이지 존재
			%>
			<!-- 클릭시 현재 페이지의 시작 페이지 번호 이전 페이지로 이동(13->10)-->
			<a href="jobList.do?nowPage=<%= paging.getStartPage() - 1 %>&searchValue=<%= searchValue %>">&lt;</a>
			<%
		}
		for(int i = paging.getStartPage(); i <= paging.getEndPage(); i++)
		{
			if(i == nowPage)
			{
				%>
				<a class="active"><%= i %></a>
				<%
			}else
			{
				%>
				<a href="jobList.do?nowPage=<%= i %>&searchValue=<%= searchValue %>"><%= i %></a>
				<%
			}
		}
		if(paging.getLastPage() > paging.getEndPage())
		{
			%>
			<!-- 클릭시 현재 페이지의 마지막 페이지 번호 다음 페이지로 이동(13->20)-->
			<a href="jobList.do?nowPage=<%= paging.getEndPage() + 1 %>&searchValue=<%= searchValue %>">&gt;</a>
			<%
		}
		%>
       </div>
    </div>
  </main>

<%@ include file="../include/footer.jsp" %>
