<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="../include/header.jsp" %>
<%
	List<JobpostingVO> jList = (List<JobpostingVO>)request.getAttribute("jList");
	List<JobpostingVO> jlList = (List<JobpostingVO>)request.getAttribute("jlList");
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
              		int i = 0;
		          	for(JobpostingVO jpvo : jList)
		          	{
		          		i++;	
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
           			if(i%3 == 0) {
           		%>
        	  </div>
        	  <%
        	  if(i != 9) {
        		  
        	  
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
	                <a href="#">&laquo;</a> <!-- 이전 페이지 -->
	                <a href="#" class="active">1</a> <!-- 현재 페이지 -->
	                <a href="#">2</a>
	                <a href="#">3</a>
	                <a href="#">4</a>
	                <a href="#">5</a>
	                <a href="#">&raquo;</a> <!-- 다음 페이지 -->
            	</div>
    </div>
  </main>

<%@ include file="../include/footer.jsp" %>
