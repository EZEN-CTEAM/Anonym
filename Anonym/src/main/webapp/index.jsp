<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/header.jsp" %>
<%@ page import="java.sql.*" %>
<%@ page import="teamproject.*" %>
<%@ page import="teamproject.util.*" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/index.css" />

<%
 	request.setCharacterEncoding("UTF-8");

	String searchValue = request.getParameter("index_search");
	if(searchValue == null || searchValue.equals("")) {
		searchValue = "";
	}
	
	int freeBoardPostNo = 0;
	int jobPostingNo = 0;
	String freeBoardPostTitle = null;
	String jobPostingTitle = null;
	int goodCnt = 0;
	int commentCnt = 0;
	String companyName = null;
	String companyLogo = null;
	int postReviewStarrating = 0;	
	
	Connection conn = null;
	PreparedStatement psmtFb = null;
	ResultSet rsFb = null;
	
	PreparedStatement psmtJp = null;
	ResultSet rsJp = null;
	
	try {
		conn = DBConn.conn();
		
		/* 자유게시판에서 조회수 순위 8개 */
		String sqlFb = "SELECT post_no"
					 + " , post_title"
					 + " , (SELECT count(*) FROM post_like pl INNER JOIN post p ON pl.post_no = p.post_no WHERE board_no = 1) as goodCnt"
					 + " , (SELECT count(*) FROM post_comment pc INNER JOIN post p ON pc.post_no = p.post_no WHERE board_no = 1) commentCnt"
					 + " FROM post p, board b"
					 + " WHERE p.board_no = b.board_no"
					 + " AND post_state = 'E'"
					 + " AND b.board_no = 1"
					 + " ORDER BY post_hit desc"
					 + " LIMIT 0, 8";
		
		psmtFb = conn.prepareStatement(sqlFb);
		rsFb = psmtFb.executeQuery();
		
		 String sqlJp = " SELECT j.job_posting_no"
				 	  + " , j.job_posting_title"
				 	  + " , c.company_name"
				 	  + " , c.company_logo"
				 	  + " , AVG(p.post_review_starrating) AS post_review_starrating"
				 	  + " , j.job_posting_hit"
				 	  + " FROM company c"
				 	  + " JOIN job_posting j ON c.company_no = j.company_no"
				 	  + " JOIN post_review p ON c.company_no = p.company_no"
				 	  + " WHERE j.job_posting_state = 'E'"
				 	  + " GROUP BY j.job_posting_no"
				 	  + " , j.job_posting_title"
				 	  + " , c.company_name"
				 	  + " ORDER BY j.job_posting_hit DESC"
				 	  + " LIMIT 0, 8";
					 
	 	psmtJp = conn.prepareStatement(sqlJp);
		rsJp = psmtJp.executeQuery(); 
%>
		  <!-- 메인 컨텐츠 -->
		  <main>
		    <div class="main_apply">
		      <img src="image/인덱스 가로 배너.PNG">
		    </div>
		    <div class="main-container">
		        <section class="intro">
		          <form action="index_search.jsp?searchValue=<%= searchValue %>" method="get">
		            <div>
		              <input onkeyup="enterkey();" type="text" name="index_search" size="80" placeholder="관심있는 내용을 검색해보세요 !">
		              <!-- <button onclick="href='index_search.jsp'">검색</button> -->
		            </div>
		          </form>
		        </section>
		
		        <!-- 사이드 배너 -->
		        <aside>
		          <div class="ad_banner" width="150px" height="300px">
		            <img src="<%= request.getContextPath() %>/image/인덱스 새로 배너.PNG" width="200px" height="400px">
		          </div>
		        </aside>
		
		        <!-- 인기 게시판 목록 -->
		        <section class="board-container">
			        <div class="free_title">
			        	<h3 class="img_area"><img src="https://img.icons8.com/?size=100&id=FR7Wf3HTQISd&format=png&color=000000" width="20px"> 인기글</h3>
			        	<a href="<%= request.getContextPath() %>/freeBoard/freeList.do">더보기 ></a>
			        </div>
		        <%
		        	 int num = 0;
			         while(rsFb.next()) {
			        	freeBoardPostNo = rsFb.getInt("post_no");
			        	freeBoardPostTitle = rsFb.getString("post_title");
						goodCnt = rsFb.getInt("goodCnt");
						commentCnt = rsFb.getInt("commentCnt");
						
						num++;
				%>
			          <div class="free_list">
			            <div class="list_title">
			              <span class="rank"><%= num %></span> 
			              <a href="<%= request.getContextPath() %>/freeBoard/freeView.do?postNo=<%= freeBoardPostNo %>"><%= freeBoardPostTitle %></a>
			            </div>
			            <div class="goodcomment_count">
			              <img src="https://img.icons8.com/?size=100&id=89385&format=png&color=000000" width="17px"><%= goodCnt %>
			              <span class="goodcomment_count"><img src="https://img.icons8.com/?size=100&id=22050&format=png&color=000000" width="17px"><%= commentCnt %></span>
			            </div>
			          </div>
				<%	
					} 
		        %>
		        </section>
		        
        <!-- 인기 채용 -->
        <section class="board-container">
          <div class="apply_title">
            <h3 class="img_area"><img src="https://img.icons8.com/?size=100&id=53426&format=png&color=000000" width="20px"> 채용 공고</h3>
            <a href="<%= request.getContextPath() %>/jobPosting/jobList.do">더보기 ></a>
          </div>
          <div class="apply_list">
          <%
          	int count = 0;
	        while(rsJp.next()) {
	        	count++;
	        	
	        	jobPostingNo  = rsJp.getInt("job_posting_no");
	        	jobPostingTitle  = rsJp.getString("job_posting_title");
				companyName = rsJp.getString("company_name");
				companyLogo = rsJp.getString("company_logo");
				postReviewStarrating  = rsJp.getInt("post_review_starrating");
           %>
            <div>
              <a href="<%= request.getContextPath() %>/jobPosting/jobView.do?job_posting_no=<%= jobPostingNo %>">
                <div class="company_logo">
                  <img src="<%= request.getContextPath() %>/upload/<%= companyLogo %>" width="164px" height="82px">
                </div>
                <div class="company_name">
                  <%= companyName %>
                </div>
                <div class="company_title">
                  <%= jobPostingTitle %>
                </div>
                <div class="company_score">
                  <%= postReviewStarrating %>
                </div>
              </a>
            </div>
              <%
              	if(count%4 == 0) {
              %>
	    	</div>
	    	<%
	    		if(count != 8) {
	    	%>
	    	<div class="apply_list">
           <%         
    			}
       		}
		}
           %>
  	</div>
  </main>

<%@ include file="/WEB-INF/include/footer.jsp" %>
<%		
	} catch(Exception e) {
		e.printStackTrace();
	} finally {
		DBConn.close(rsFb, psmtFb, null); 
		DBConn.close(rsJp, psmtJp, conn);
	}
%>