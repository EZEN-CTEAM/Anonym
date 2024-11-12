<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="teamproject.*" %>
<%@ page import="teamproject.util.*" %>
<%@ include file="/WEB-INF/include/header.jsp" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/index_search.css" />
<%
	request.setCharacterEncoding("UTF-8");

	String searchValue = request.getParameter("index_search");
	
	int companyNo = 0;
	String companyName = null;
	String companyLogo = null;
	int postReviewStarratingAvg = 0;
	
	int postReviewStarratingCr = 0;
	int postNo = 0;
	String userNickname = null;
	String postTitle = null;
	String postContent = null;
	
	String companyLogoJp = null;
	String companyNameJp = null;
	int jobPostingNo = 0;
	String jobPostingTitle = null;
	int postReviewStarratingJp = 0;
	
	int count = 0;
	int freeBoardPostNo = 0;
	String freeBoardPostTitle = null;
	String freeBoardPostContent = null;
	String userNicknameFb = null;
	int postHit = 0;
	int goodCnt = 0;
	int commentCnt = 0;
	String postRegistrationDate = null;
	
	Connection conn = null;
	PreparedStatement psmtC = null;
	ResultSet rsC = null;
	
	PreparedStatement psmtCr = null;
	ResultSet rsCr = null;
	
	PreparedStatement psmtJp = null;
	ResultSet rsJp = null;
	
	PreparedStatement psmtFb = null;
	ResultSet rsFb = null;
	
	try {
		conn = DBConn.conn();
		
		/* searchValue와 회사명이 일치하는 회사 */
		String sqlC = " SELECT c.company_no"
					+ " , c.company_name"
					+ " , (select a.company_attach_physics_file_name from anonym.company_attach a where a.company_no = c.company_no and a.company_attach_sequence = 2 ) as company_logo"
					+ " , AVG(p.post_review_starrating) AS post_review_starrating"
					+ "	FROM company c"					
					+ "	JOIN post_review p ON c.company_no = p.company_no"
					+ " WHERE company_name = ?"
					+ " GROUP BY c.company_no, c.company_logo";
		
		psmtC = conn.prepareStatement(sqlC);
		psmtC.setString(1, searchValue);
		rsC = psmtC.executeQuery();
		
		String sqlCr = "SELECT p.post_no"
	             	 + " , p.post_title"
	            	 + " , p.post_content"
	            	 + " , r.post_review_starrating"
	             	 + " , u.user_nickname"
	             	 + " , c.company_no"
	           		 + " FROM post p"
	            	 + " JOIN post_review r ON p.post_no = r.post_no"
	             	 + " JOIN company c ON r.company_no = c.company_no"
	             	 + " JOIN user u ON p.user_no = u.user_no"
	             	 + " JOIN board b ON p.board_no = b.board_no"
	              	 + " WHERE company_name = ?"
	             	 + " AND p.post_state = 'E'"
	             	 + " AND b.board_no = 2"
	             	 + " ORDER BY p.post_registration_date DESC"
	             	 + " LIMIT 0, 1";
		
		psmtCr = conn.prepareStatement(sqlCr);
		psmtCr.setString(1, searchValue);
		rsCr = psmtCr.executeQuery();
		
		/* 채용공고에서 조회수 순위 4개 */
		String sqlJp = "SELECT c.company_no"
             		 + " , c.company_name"
             		 + " , (select a.company_attach_physics_file_name from anonym.company_attach a where a.company_no = c.company_no and a.company_attach_sequence = 2 ) as company_logo"
            		 + " , j.job_posting_no"
             	 	 + " , j.job_posting_title"
             	 	 + " , AVG(p.post_review_starrating) AS post_review_starrating"
             		 + " FROM company c"
             		 + " JOIN job_posting j ON c.company_no = j.company_no"
             		 + " LEFT JOIN post_review p ON c.company_no = p.company_no"
             		 + " WHERE j.job_posting_state = 'E'"
             		 + " GROUP BY c.company_no, c.company_name, c.company_logo, j.job_posting_no, j.job_posting_title"
             		 + " ORDER BY j.job_posting_registration_date DESC"
             		 + " LIMIT 0, 4";
					 
	 	psmtJp = conn.prepareStatement(sqlJp);
		rsJp = psmtJp.executeQuery();
		
		if(!searchValue.equals("")) {
			sqlJp = "SELECT c.company_no"
            	  + " , c.company_name"
            	  + " , (select a.company_attach_physics_file_name from anonym.company_attach a where a.company_no = c.company_no and a.company_attach_sequence = 2 ) as company_logo"
           		  + " , j.job_posting_no"
            	  + " , j.job_posting_title"
            	  + " , AVG(p.post_review_starrating) AS post_review_starrating"
            	  + " FROM company c"
            	  + " JOIN job_posting j ON c.company_no = j.company_no"
            	  + " LEFT JOIN post_review p ON c.company_no = p.company_no"
            	  + " WHERE j.job_posting_state = 'E'"
            	  + " AND c.company_name LIKE CONCAT('%', ?, '%')"
            	  + " GROUP BY c.company_no, c.company_name, c.company_logo, j.job_posting_no, j.job_posting_title"
            	  + " ORDER BY j.job_posting_registration_date DESC"
            	  + " LIMIT 0, 4";
					 
		 	psmtJp = conn.prepareStatement(sqlJp);
		 	psmtJp.setString(1, searchValue);
			rsJp = psmtJp.executeQuery();
		}
		
		/* 자유게시판에서 조회수 순위 8개 */
		String sqlFb = "SELECT p.post_no"
				 	 + " , post_title"
				 	 + " , post_content"
				 	 + " , post_hit"
				 	 + " , user_nickname"
				 	 + " , COUNT(DISTINCT pl.user_no) AS goodCnt"
				 	 + " , (SELECT COUNT(*) FROM post_comment pc WHERE pc.post_no = p.post_no AND pc.post_comment_state = 'E' ) AS commentCnt"
				 	 + " , post_registration_date"
				 	 + " FROM post p"
				 	 + " LEFT JOIN post_like pl ON p.post_no = pl.post_no"
				 	 + " LEFT JOIN post_comment pc ON p.post_no = pc.post_no"
				 	 + " LEFT JOIN user u ON u.user_no = p.user_no"
				 	 + " WHERE p.board_no = 1"
				 	 + " AND post_state = 'E'"
				 	 + " GROUP BY p.post_no, p.post_title"
				 	 + " ORDER BY post_hit desc"
				 	 + " LIMIT 0, 8";
		
		psmtFb = conn.prepareStatement(sqlFb);
		rsFb = psmtFb.executeQuery();
		
		if(!searchValue.equals("")) {
			sqlFb = "SELECT p.post_no"
			  	  + " , post_title"
			  	  + " , post_content"
				  + " , post_hit"
				  + " , user_nickname"
				  + " , COUNT(DISTINCT pl.user_no) AS goodCnt"
				  + " , (SELECT COUNT(*) FROM post_comment pc WHERE pc.post_no = p.post_no AND pc.post_comment_state = 'E' ) AS commentCnt"
				  + " , post_registration_date"
				  + " FROM post p"
				  + " LEFT JOIN post_like pl ON p.post_no = pl.post_no"
				  + " LEFT JOIN post_comment pc ON p.post_no = pc.post_no"
				  + " LEFT JOIN user u ON u.user_no = p.user_no"
				  + " WHERE p.board_no = 1"
				  + " AND post_state = 'E'"
				  + " AND (p.post_title LIKE CONCAT('%', ?, '%') OR p.post_content LIKE CONCAT('%', ?, '%'))"
				  + " GROUP BY p.post_no, p.post_title"
				  + " ORDER BY post_hit desc"
				  + " LIMIT 0, 8";
		
		psmtFb = conn.prepareStatement(sqlFb);
		psmtFb.setString(1, searchValue);
		psmtFb.setString(2, searchValue);
		rsFb = psmtFb.executeQuery();
		}
	
%>

  <!-- 메인 컨텐츠 -->
  <main>
    <div class="main-container">
      <section class="search_contianer">
        <div class="search_result">
          <div style="font-weight: bold;">(<%= searchValue %>)</div> 
          <div>검색결과</div>
        </div>
      </section>

      <!-- 회사 정보 -->
      <section class="board-container">
        <%
        if(rsC != null) {
        	if(rsC.next()){
        		companyNo = rsC.getInt("company_no");
        		companyName = rsC.getString("company_name");
        		companyLogo = rsC.getString("company_logo");
        		postReviewStarratingAvg = rsC.getInt("post_review_starrating");
        %>
        <div class="company">
          <h4>회사</h4>
        </div>
        <div class="company_container">
          <div class="company_info">
          	<a href="<%= request.getContextPath() %>/companyReview/companyInfo.do?company_no=<%= companyNo %>">
            <div><img src="<%= request.getContextPath() %>/user/down.do?fileName=<%= companyLogo %>" width="164px" height="82px"></div>
            <div class="company_info_name">
              <%= companyName %>
              <div class="company_info_score">
                <img src="https://img.icons8.com/?size=100&id=87XmLcImcKSL&format=png&color=000000" width="17px"><%= postReviewStarratingAvg %>
              </div>
            </a>
            </div>
          </div>
          <div class="recommend_container">
            <div class="recommend_container_ment">
              이 회사는 일해보고 싶은 회사인가요 ?
              <div class="recommend_container_good">
                <div><img src="data:image/png;base64,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" width="35px"></div>
              </div>
            </div>
          </div>
        <%
        	}
        %>
          <%
          	if(rsCr.next()) {
          		postReviewStarratingCr = rsCr.getInt("post_review_starrating");
          		userNickname = rsCr.getString("user_nickname");
          		postTitle = rsCr.getString("post_title");
          		postContent = rsCr.getString("post_content");
          		postNo = rsCr.getInt("post_no");
   		  %>
          <div class="commit_container">
            <div class="commit_info">
              <div>
                <div class="commit_info_score">별점<img src="https://img.icons8.com/?size=100&id=87XmLcImcKSL&format=png&color=000000" width="17px"></div>
                <div class="commit_info_nickname">닉네임</div>
              </div>
              <div><a href="<%= request.getContextPath() %>/companyReview/reviewList.do" class="commit_info_detail">전체보기 ></a></div>
            </div>
            <div>
              <div class="commit_title">제목</div>
              <div class="commit_content">내용</div>
            </div>
          </div>
        </div>
          <%
          	}
        }
          %>
      </section>

        <!-- 인기 채용 -->
        <section class="board-container">
          <div class="apply_title">
            <h3><img src="https://img.icons8.com/?size=100&id=53426&format=png&color=000000" width="20px"> 채용 공고</h3>
            <a href="<%= request.getContextPath() %>/jobPosting/jobList.do">더보기 ></a>
          </div>
          <div class="apply_list">
          <%
          	while(rsJp.next()){
          		companyLogoJp = rsJp.getString("company_logo");
          		companyNameJp = rsJp.getString("company_name");
          		jobPostingNo = rsJp.getInt("job_posting_no");
          		jobPostingTitle = rsJp.getString("job_posting_title");
          		postReviewStarratingJp = rsJp.getInt("post_review_starrating");
          %>
            <div class="apply_area">
              <a href="<%= request.getContextPath() %>/jobPosting/jobView.do?job_posting_no=<%= jobPostingNo %>">
                <div class="company_logo">
                  <img src="<%= request.getContextPath() %>/user/down.do?fileName=<%= companyLogoJp %>" width="164px" height="82px">
                </div>
                <div class="company_name">
                  <%= companyNameJp %>
                </div>
                <div class="company_title">
                  <%= jobPostingTitle %>
                </div>
                <div class="company_score">
                  <%= postReviewStarratingJp %>
                </div>
              </a>
            </div>
            <%
          	}
            %>
          </div>
          
          <!-- 검색 글 -->
        <section class="board-container">
          <div class="free_title">
            <h3 class="img_area"><img src="https://img.icons8.com/?size=100&id=L1QiLIoHOOjJ&format=png&color=000000" width="20px"> 게시글</h3>
            <a href="<%= request.getContextPath() %>/freeBoard/freeList.do">더보기 ></a>
          </div>
          <div class="free_count">
            게시글 개수(<%= count %>)
          </div>
          <div>
            <div class="free_container">
            <%
            while(rsFb.next()){
            	count++;
            	
            	freeBoardPostNo = rsFb.getInt("post_no");
            	freeBoardPostTitle = rsFb.getString("post_title");
            	freeBoardPostContent = rsFb.getString("post_content");
            	userNicknameFb = rsFb.getString("user_nickname");
            	postHit = rsFb.getInt("post_hit");
            	goodCnt = rsFb.getInt("goodCnt");
            	commentCnt = rsFb.getInt("commentCnt");
            	postRegistrationDate = rsFb.getString("post_registration_date");
            %>
              <div class="free_list_container">
                <a href="<%= request.getContextPath() %>/freeBoard/freeView.do?pno=<%= freeBoardPostNo %>">
                  <div class="free_list_title"><%= freeBoardPostTitle %></div>
                  <div class="free_list_content"><%= freeBoardPostContent %></div>
                  <div>
                    <div class="free_list_nickname"><%= userNicknameFb %></div>
                  </div>
                  <div class="free_icons_count">
                    <div class="free_icons">
                      <div><img src="https://img.icons8.com/?size=100&id=85028&format=png&color=000000" width="17px"><%= postHit %></div>
                      <div><img src="https://img.icons8.com/?size=100&id=89385&format=png&color=000000" width="17px"><%= goodCnt %></div>
                      <div><img src="https://img.icons8.com/?size=100&id=22050&format=png&color=000000" width="17px"><%= commentCnt %></div>
                    </div>
                    <div class="free_list_rdate"><%= postRegistrationDate %></div>
                    <div><img scr="#"></div>
                  </div>
                </a>
              </div>
              <%
            	if(count%2 == 0) {
              %>
          </div>
          <div class="free_container">
          
          <%
          	}
		}
          %>
          </div>
        </section>
    </div>
  </main>

<%@ include file="/WEB-INF/include/footer.jsp" %>
	<%
	} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close(rsC, psmtC, conn);
			DBConn.close(rsCr, psmtCr, null);
			DBConn.close(rsJp, psmtJp, null);
			DBConn.close(rsFb, psmtFb, null);
		}
	%>
