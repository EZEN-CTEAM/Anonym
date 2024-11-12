<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="teamproject.vo.*"%>
<%@ include file="../include/header.jsp" %>
<%
	List<JobpostingVO> jpIpList = (List<JobpostingVO>)request.getAttribute("jpIpList");
	List<JobpostingVO> jpCList = (List<JobpostingVO>)request.getAttribute("jpCList");
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/company_service.css" />

  <!-- 메인 컨텐츠 -->
  <main>
    <div class="main_apply">
      <img src="<%= request.getContextPath() %>/image/인덱스 가로 배너.PNG">
    </div>
    <div class="main-container">
    <!-- 사이드 배너 -->
    <aside>
      <div class="ad_banner" width="150px" height="300px">
        <img src="<%= request.getContextPath() %>/image/인덱스 새로 배너.PNG" width="200px" height="400px">
      </div>
    </aside>

    <!-- 현재 진행 중인 공고 -->
    <section class="board-container">
      <div class="implyeement_area">
        <a href="cjobRegister.do" class="implyeement_write">글작성</a>
      </div>
      <div class="apply_title">
        <h3><img src="https://img.icons8.com/?size=100&id=39372&format=png&color=000000" width="17px"> 현재 진행 중인 공고</h3>
        <a href="<%= request.getContextPath() %>/companyServices/cjobListInProgress.do">더보기 ></a>
      </div>
      <div class="apply_list">
          <%
          	for(JobpostingVO jpIpvo : jpIpList)
          	{
          %>
			<div class="apply_area">
          	<a href="cjobView.do?job_posting_no=<%= jpIpvo.getJob_posting_no() %>">
          		<input type="hidden" name="job_posting_no" value="<%= jpIpvo.getJob_posting_no() %>"> 
            <div class="company_logo">
              <img src="<%= request.getContextPath() %>/user/down.do?fileName=<%= jpIpvo.getCompany_logo() %>" width="164px" height="82px">
            </div>
            <div class="company_name">
              <%= jpIpvo.getCompany_name() %>
            </div>
            <div class="company_title">
              <%= jpIpvo.getJob_posting_title() %>
            </div>
        	</a>
        <div class="company_button">
          <a href="cjobModify.do?job_posting_no=<%= jpIpvo.getJob_posting_no() %>" class="button_area">수정
          	<input type="hidden" name="job_posting_no" value="<%= jpIpvo.getJob_posting_no() %>">
          </a>
          <a href="cjobDelete.do?job_posting_no=<%= jpIpvo.getJob_posting_no() %>" class="button_area">마감
          	<input type="hidden" name="job_posting_no" value="<%= jpIpvo.getJob_posting_no() %>">
       	  </a>
        </div>
          
        </div>          
          <%
          	}
          %>
      </div>
    </section>

    <!-- 마감된 공고 -->
      <section class="board-container">
        <div class="apply_title">
          <h3><img src="https://img.icons8.com/?size=100&id=39372&format=png&color=000000" width="17px"> 마감된 공고</h3>
          <a href="cjobListClosed.do">더보기 ></a>
        </div>
        <div class="apply_list">
        <%
        	for(JobpostingVO jpCvo : jpCList)
        	{
        %>
        	<div class="apply_area">
            <a href="cjobView.do?job_posting_no=<%= jpCvo.getJob_posting_no() %>">
            	<input type="hidden" name="job_posting_no" value="<%= jpCvo.getJob_posting_no() %>">
              <div class="company_logo">
                <img src="<%= request.getContextPath() %>/upload/<%= jpCvo.getCompany_logo() %>" width="164px" height="82px">
              </div>
              <div class="company_name">
                <%= jpCvo.getCompany_name() %>
              </div>
              <div class="company_title">
                <%= jpCvo.getJob_posting_title() %>
              </div>
            </a>
          </div>
        <%		
        	}
        %>
        </div>
      </section>
    </div>
  </main>

<%@ include file="../include/footer.jsp" %>
