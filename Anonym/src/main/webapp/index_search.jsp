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
	
	int freeBoardPostNo = 0;
	String freeBoardPostTitle = null;
	String freeBoardPostContent = null;
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
					+ " , c.company_logo"
					+ " , AVG(p.post_review_starrating) AS post_review_starrating"
					+ "	FROM company c"
					+ "	JOIN job_posting j ON c.company_no = j.company_no"
					+ "	JOIN post_review p ON c.company_no = p.company_no"
					+ " WHERE company_name = ?"
					+ " GROUP BY c.company_no, c.company_name";
		
		psmtC = conn.prepareStatement(sqlC);
		psmtC.setString(1, searchValue);
		rsC = psmtC.executeQuery();
		
		String sqlCr = "SELECT post_review_starrating"
					 + " , p.post_no"
					 + " , post_title"
					 + " , post_content"
					 + " , user_nickname"
					 + " , c.company_no"
					 + " , company_name"
					 + " FROM post p, post_review r, company c, user u"
					 + " WHERE company_name like ?"
					 + " AND post_state = 'E'"
					 + " ORDER BY post_registration_date desc"
					 + " LIMIT 0, 1";
		
		psmtCr = conn.prepareStatement(sqlCr);
		psmtCr.setString(1, searchValue);
		rsCr = psmtCr.executeQuery();
		
		/* 채용공고에서 조회수 순위 4개 */
		 String sqlJp = "SELECT c.company_no"
				 	  + " , company_name"
				 	  + " , company_logo"
				 	  + " , job_posting_no"
				 	  + " , job_posting_title"
				 	  + " , AVG(p.post_review_starrating) AS post_review_starrating"
				 	  + " FROM company c, job_posting j, post_review p"
				 	  + " WHERE company_name LIKE '%?%'"
				 	  + " AND job_posting_state = 'E'"
				 	  + " GROUP BY company_no, company_name, company_logo, job_posting_no, job_posting_title"
				 	  + " ORDER BY job_posting_registration_date desc"
				 	  + " LIMIT 0, 4";
					 
	 	psmtJp = conn.prepareStatement(sqlJp);
	 	psmtJp.setString(1, searchValue);
		rsJp = psmtJp.executeQuery(); 
		
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
        <div class="company">
          <h4>회사</h4>
        </div>
        <%
        	if(rsC.next()){
        		companyNo = rsC.getInt("company_no");
        		companyName = rsC.getString("company_name");
        		companyLogo = rsC.getString("company_logo");
        		postReviewStarratingAvg = rsC.getInt("post_review_starrating");
        %>
        <div class="company_container">
          <div class="company_info">
          	<a href="#">
            <div><img src="<%= request.getContextPath() %>/upload/<%= companyLogo %>" width="164px" height="82px"></div>
            <div class="company_info_name">
              <%= companyName %>
              <div class="company_info_score">
                <img src="https://img.icons8.com/?size=100&id=87XmLcImcKSL&format=png&color=000000" width="17px"><%= postReviewStarratingAvg %>
              </div>
            </a>
            </div>
        <%
        	}
        %>
          </div>
          <div class="recommend_container">
            <div class="recommend_container_ment">
              이 회사는 일해보고 싶은 회사인가요 ?
              <div class="recommend_container_good">
                <div><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAdVBMVEX////oW2zoWWrpaHfoV2j86+3pZHTnT2LnUWTnVGboWGnnTmH+9/j//Pz3zNHsfIn409f98fLwnabtgo775Ob63eDvlqDrbn3vkJrrcH7yqbHrd4Txo6v2xcr52NzpX3D1vML0tbzzsLfuipXyqLDtgY31v8WrOXj9AAAG7ElEQVR4nO2d6XbiMAyFiQnYjtnLVgJlm/b9H3HSbTqlIZFsy4579P0P+J4scrTc9HoMwzAMwzAMwzAMwzAMwzAMwzAMw3hlNB90jfnIm7jJojwKkXcNIY7lYuIsczy5GqkL0U0KLc304CTytDF51m0KOTyPbfWtSiliCwAgZH9mJ3BnitiLByLMwkLf+MHEXjgCVaLvxlGpY68ahX6cIxVO0xL4KhH3vHlQsVeMRpcYgZeU7sFP5A4ucKlSiBK3CLUCK5x2PczXk4Ov00mK1+gr5gBUWKYS6W8pHmECV6mewuokPoEUrlMLhV/oNUihTvFB+o5QEIFLGXudDkhIwDilt535Qp0AChO+DYE34jTVWPFKMQUofEz3QVM9aiARsZ+0wj4rZIVdJ5RCIZC/gT7g7g+RKxS5lqba9glplIaEnbcDVAY/oOXnaBUKbcT1MlnOKwarl30pZfOaPw8YfBwwNS0HtC+BUqFQev30Pd81OkzN/X386wE3yerqAOm08SdUKFS/tnwwWBT1u0Chhue6LO5gIRy2jXQK8+JyL1052NZderrY3ctSD9b25RIyhXI6aPjFp83tkoW5NqWon462p5FIoTCXlt886/+XLNSmpVg0vlq+wtEoFLo9xTXffp1GLc7tq/hj9x5OolBoUPVu1n9fszDbpiv6H3ZpdxKFZgJZcHXl7YzKtenD0mHVWbSRSKHQnIArri7V83YNzdlWbC0eNwQKgfk7G8aP+A2Of4Wi762r5SdL/HXqX6G07BGA8QcdM7wrzK+UAnvjIzpy+VYol6QKeydsVPStEJS7c2GEPYm+FRrSu/CVPTJieFYoMmqBvRXyWeNZIWEstFgNhUKJ2KDYgqyieFaoQFtoNya4p6lfhaT7mU+Q+xq/CsljxRsxFeZbcnkVuIjoV2GIR2mvt4mp0KZtFQ2uuSdJhXyVEirMH8jlVWQRFYrSegoAQcxoITJsa7UFUSN+ZuAdq9YcYu7aMtWWzPdA3J13iG3bMOrbU2bIb8TIb8CZRnTH27GIm8WgT2OMkBep/2wiqNnRgTM2JexdoRiSvgSjk4kEWX21p1S4j5/VR82poBl0oTKTiSPd5rQT1bUqYpC9YSwsuhVIqtxUt+LZpleBphdDnigEzoxN2xBRtwlFgWZp1xdF1THkv4w4x25maBVmxdDzFnxsO2dN1teGm8Ft59qxvrYK5bWgv7AevSLsL5UeYwa6eh9EYWZefAmcOcxZUyoUylPMWLq0QdP2eRdeYgb+jSmYwipmeHhZtI4TIRR6iRlbt/lO6okS5VwztWoqDagQZU1Rx4vroDz9VJB0ihlPzn4cAeaeXGoZA1wlLY7CKmZYN9mM+p2f7Hqj2NjGDB++TUHmD7VlvWbtwwcgzISlXczYebFyCDRDKi3qis5x4p1QU7L4pkX3OPFOsDlgbCZ8XiQzB/z5RxKVuPERJz7+ONi0Oi5mPHszGwk4j49J9nuJE++EdBxQ4J4wn/Z3QT0VWgdLPzj4NN4K6xoBG0xc5T5dKsIqFDkgZsyd0jI//zOs84c4tsaM8dCvgWFob5N801YgfvBsShXcvaUtZtiUeRsJ70+jGjulrcq8jURw4DENs/cEHpsxPIbk3ZhB4QMbQ6FQd5L98yGBMVwUn6g7MWP8SGF0G8cJK6/1ofYdJ96J5PWlawrEexqHTZBC3AgHiJ8FYocybyNiA1BI4ZtobuoZL1btQABAvokPFE8Auf7/nX9PJRA2x4MdEIehh6cPjaPDhs4DVkMaJg40/y+UmO5Pp91DRmn4riB5TIu+VRii0EppX3nDegyoLuT3nTQo4ggR2NulayQMHAAhu0zpgV2kdgZNnUBDq17YYaOugCiXJGrpjRhMduu8ioU4ImolsxQfNrh28wQ/pXO7u2/Dvk03EhLtf7BI4qty/8ALrC7UhCS2O6jWMstSCRr6aNmfPF64GTOHQWi5sG/dXW5z1e3PXRSqWLt1Xw/OpZQ6t/sSKWCFLl8hzbWU5cWDHdd8ctmWm6EF7RqLo83vvrEpt5dJAGeORlqtyIBvrB3muSVvB/Ug7i4t3iv5c+wFutP4HiY0sf1pCEZND5swVlrUNBQoRBbAlZCeBstqmgnp8MzuJX0KSJkoCa53IoaE2s93nnl9xABn/hKg1qFE5AHsXUMxrputV+QGTCGp8VxLf0P6nfLHwwb85d5EWN2exDyIQW9IbranpM5EcZh/z/f8jg3pd771WYoi9qs5BZv/tqcS8DWk9Jh8hX1QZ1aCfDUi0X8zIg7/ugPq+vh+Bx/bU4dR6K7zUVim9SGMy8EUQqh+CAPpWMxKcVz/itzMfX7z+WMYhmEYhmEYhmEYhmEYhmEYhmGY9PkLNvOI/zzFkekAAAAASUVORK5CYII=" width="35px"></div>
              </div>
            </div>
          </div>
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
                <div class="commit_info_score"><%= postReviewStarratingCr %> <img src="https://img.icons8.com/?size=100&id=87XmLcImcKSL&format=png&color=000000" width="17px"></div>
                <div class="commit_info_nickname"><%= userNickname %></div>
              </div>
              <div><a href="<%= request.getContextPath() %>/companyReview/reviewList.do?post_no=<%= postNo %>" class="commit_info_detail">전체보기 ></a></div>
            </div>
            <div>
              <div class="commit_title"><%= postTitle %></div>
              <div class="commit_content"><%= postContent %></div>
            </div>
          </div>
          <%
          	}
          %>
        </div>
      </section>

        <!-- 인기 채용 -->
        <section class="board-container">
          <div class="apply_title">
            <h3><img src="https://img.icons8.com/?size=100&id=53426&format=png&color=000000" width="20px"> 채용 공고</h3>
            <a href="<%= request.getContextPath() %>/jobPosting/jobList.do">더보기 ></a>
          </div>
          <div class="apply_list">
          <%
          	
          %>
            <div class="apply_area">
              <a href="<%= request.getContextPath() %>/jobPosting/jobView.do">
                <div class="company_logo">
                  <img src="data:image/png;base64,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" width="164px" height="82px">
                </div>
                <div class="company_name">
                  회사명
                </div>
                <div class="company_title">
                  공고제목
                </div>
                <div class="company_score">
                  평점
                </div>
              </a>
            </div>
            <div class="apply_area">
              <a href="#">
                <div class="company_logo">
                  <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAATcAAACiCAMAAAATIHpEAAAAgVBMVEX0MUL////0JzrzGjH7vsH8ztH0IzfzFC7zHTP3hYz5naL3gYn94+X3fYX0L0D+7/DzACX7ycz82dv5o6j5n6T7xcj4kpjzACT5par3eYH6tLj93t/6rrL4jZP80NL82Nn1TVr1V2P1R1T+6uv2YGv0P07+9/j2b3jzABP0OUj6ur49H+PtAAADSklEQVR4nO3ca1vaMBiA4ZCWpiiEAqJ4dsPp5v//gUPYLElbkPZqXN4991cjl3mWNBw6lAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAyTKzk9imETb/MyTzf5CYI/Ke//gvY83derw1zBrCaT3bjVjfJe7vqsfxEalp/NeImrbzwV9p/eLInqYfQ671/k/y5eCoZSExXP7tpZziMKkZYc35XoWR0y2ZHe8mMZw1l/szrOtmzc3+kBbdBjNp4bS3z2q6aXXrDGnTTVq47PvanV+1W/L84g5p1W0wk3Q4mGt/en43bxu37yYo3ObpR2V2XjebVI/Llt3EhNN6Xp2c2y37sa4O8boNP9tNSDjn6Ud9N/NaN323m65s9QPh4j8cbFG5bvndbLGqHeJ2U9nzpNn1m/voV5GHq7tued109lA/xOu22e8HXN24Dx/3irP+049qN7v3yupwt8MK9+wZRn2Ny84ampTdsoumIad1U8bd7VEfDl63dXmtK7u5Q4Zp225+uJhXnBtlWUyOdVv9bN9NmYWUcE6U+0KXb3fUd3s1eYducsLtRZk+5epIt/EvrTp188M9xHqqllGWyabC4W4378ujWzc/3GOkK05fvky3RtsJ1HX7GDLZDunYTRn3eJ43f5DxT9PJzi5BXTdvSNduyntfZZ7HGc5R283VuZvEcEG6CQwXplslXKTXuFKgbsrcO+FuIz1VP4TqJi1csG6VcHFv1XDdVDJyw0V9OATsppKJnHAhu6nE/UBi7N/fFJGQ3axJnW4run2GNe4HGwvT4cG+WrhuthCULWA3L9tF1NmCdbOFe1PEWdzZQnXzN2nkqy1UN/9IiD5bmG7ysgXpJjBbiG7WyDoStvrvJu0k3em9m8xsvXcTmq3vbiKvbe/67SY2W7/d/Gxxv5R39NnNz7aSk63PbrYYis12Yrdzk33elZtN0CZVp3ZLz07g3pO+KMJOrGendWtP1moL1k1atkDdxGUL001etiDdJLzf5gvQTWK2AN1EZtvv9tBLNzkv5R26/P+5bw1fypMtDmT5T7NtVtNomW7dNd5WZS7Sti6lZnv/GpGdpq9H2sjytrrd9wUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAkfkNDKw2lxqcgM8AAAAASUVORK5CYII=" width="164px" height="82px">
                </div>
                <div class="company_name">
                  회사명
                </div>
                <div class="company_title">
                  공고제목
                </div>
                <div class="company_score">
                  평점
                </div>
              </a>
            </div>
            <div class="apply_area">
              <a href="#">
                <div class="company_logo">
                  <img src="data:image/jpeg;base64,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" width="164px" height="82px">
                </div>
                <div class="company_name">
                  회사명
                </div>
                <div class="company_title">
                  공고제목
                </div>
                <div class="company_score">
                  평점
                </div>
              </a>
            </div>
            <div class="apply_area">
              <a href="#">
                <div class="company_logo">
                  <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAATYAAACjCAMAAAA3vsLfAAAAhFBMVEX////p6ekDx1oAxVEAxE7D7M+e5bkAxlUAxleY5LXc9OOA3aIpzGsAxE/l+e6i5ruL4Kpy25kAw0hg1YlX1om/7tGt6cN42poAwkT0/fhs2JLD79Pu+/ST4q8AymDp+/LN8tv3/fnX9uRQ04Izz3LK8dkuzm+F36VU04Mby2U/0Xm168ie2eQ9AAAE80lEQVR4nO3ZUXuaShSF4SlQwaRoxKjRKAFrmmj///87zN4zQEzTnrOeZp+b9d3UAUM6bwUG674wIPfFsf8c2aDIBkU2KLJBkQ2KbFBkgyIbFNmgyAZFNiiyQZENimxQZIMiGxTZoMgGRTYoskGRDYpsUGSDIhsU2aDIBkU2KLJBkQ2KbFBkgyIbFNmgyAZFNiiyQZENimxQZIMiGxTZoMgGRTYoskGRDYpsUGSDIhsU2aDIBkU2KLJBkQ2KbFBkgyIbFNmgyAZFNiiyQZENimxQZIMiGxTZoMgGRTYoskHZsG2/+lZbHbUy+nrsd7cr3bL3b9XXTb/zRvc9xRd9/ueH0fPRGWbDdvOQdVXfdTSrZLTpd09kd/Zw5wdneZ23caeOH1p30HfFqmW3sx5vOc23BnORjNiyxJfNZTTL/SC/7Xd/K2R3cfaDaSlvXb350XzR7UiTcflu+EmtKJPV9W/+pEzZknTmR9dsxyrMO3vsRnt1ug877wQrO/4Ltg6u+mkwG2fNll/86JptkUcJf965pQyLve4UmOLkerY8VP6CLSnyvcF0rNmS7ODesW2HWWf+4vQoby7XsrORQerPbmUrXpfavT9UYKvrOlXUcmIwHXO2pGzesa31rBwmffYWud4/9JNYek5ly5/GBxa2VG4lh3T4wH561mz+sn/FdlGlPJ6M4XJWeZ+27i9jf2JzJzmdLwbTsWdLytsrtrBvLxuzptsy0xNz2r1cycvs2b8xnKSHuTbxG8dsCv/DYDrGbIUaPL1he0n13JLT0S80nHvtP3m7PIkLk3hLSEutenGRzV/lXKNHfTWYji1bcRGE+liM2Nraj7plml78M7/M/ak3hRvXDp+7dwsQ+Yzpffay2WyW+i9THgymY8uWruXDk1/qEZuehoWLnxy5gebBZS1L31IvZx+ydcuOrrD16aO/w1/Nkq2ct3Ke6qIhsMkVKfcPWnIn0JXdbSpvCzvDyjeu21JtOEmHimptMBtnzRbWZCO2WaqnYfPczPvngfj+tbwxPmeFW8LtnfbiN79ly8+PBpPxGbO5XfqW7RCu892TeDraLFfBpC7iCez7cAHiT1L542QwFc2aTdeyg0999XTUTd7fFPSiNlzDfB+yFafFQg5TTg3mIpmz9c/twjY6aWOlfxpvB7buhqqFk3S3iLnA5q+MczlQZfWlmzmbuytHbOGxPTyd68JEnqo2/eP993iQq0f5NHEjNnevK7z2l7//r2fP5k55z6YL3+LbvSaPR0nlv11q+of//qugqwVIUbsxmz5lpDuD2bj/hS08Mni2id4845eLTTVczH6Ei17aH+T3bGGNnNmsQKy+FJeVVvhSZ1LJqJtskflXWf++sx+XiT/T5pWuzobvgA9VOq4s4g9U8kTmlnKwB5PLmw3bfjrpmsYZzWX0GDcP38iudIO/WW7l5WQ663c2umWoP5J+WrfXR/vE+B9+UGSDIhsU2aDIBkU2KLJBkQ2KbFBkgyIbFNmgyAZFNiiyQZENimxQZIMiGxTZoMgGRTYoskGRDYpsUGSDIhsU2aDIBkU2KLJBkQ2KbFBkgyIbFNmgyAZFNiiyQZENimxQZIMiGxTZoMgGRTYoskGRDYpsUGSDIhsU2aDIBkU2KLJBkQ2KbFBkgyIbFNmgyAZFNiiyQZENimxQZIMiGxTZoMgGRTaojo0B/QMwCToRXJRgDwAAAABJRU5ErkJggg==" width="164px" height="82px">
                </div>
                <div class="company_name">
                  회사명
                </div>
                <div class="company_title">
                  공고제목
                </div>
                <div class="company_score">
                  평점
                </div>
              </a>
            </div>
          </div>
          
          <!-- 검색 글 -->
        <section class="board-container">
          <div class="free_title">
            <h3 class="img_area"><img src="https://img.icons8.com/?size=100&id=L1QiLIoHOOjJ&format=png&color=000000" width="20px"> 게시글</h3>
            <a href="<%= request.getContextPath() %>/freeBoard/freeList.do">더보기 ></a>
          </div>
          <div class="free_count">
            게시글 개수(개수)
          </div>
          <div>
            <div class="free_container">
              <div class="free_list_container">
                <a href="<%= request.getContextPath() %>/freeBoard/freeView.do">
                  <div class="free_list_title">제목</div>
                  <div class="free_list_content">내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용</div>
                  <div>
                    <div class="free_list_nickname">닉네임</div>
                  </div>
                  <div class="free_icons_count">
                    <div class="free_icons">
                      <div><img src="https://img.icons8.com/?size=100&id=85028&format=png&color=000000" width="17px">조회수</div>
                      <div><img src="https://img.icons8.com/?size=100&id=89385&format=png&color=000000" width="17px">좋아요</div>
                      <div><img src="https://img.icons8.com/?size=100&id=22050&format=png&color=000000" width="17px">댓글</div>
                    </div>
                    <div class="free_list_rdate">작성일</div>
                    <div><img scr="#"></div>
                  </div>
                </a>
              </div>
              <div class="free_list_container">
                <a href="#">
                  <div class="free_list_title">제목</div>
                  <div class="free_list_content">내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용</div>
                  <div>
                    <div class="free_list_nickname">닉네임</div>
                  </div>
                  <div class="free_icons_count">
                    <div class="free_icons">
                      <div><img src="https://img.icons8.com/?size=100&id=85028&format=png&color=000000" width="17px">조회수</div>
                      <div><img src="https://img.icons8.com/?size=100&id=89385&format=png&color=000000" width="17px">좋아요</div>
                      <div><img src="https://img.icons8.com/?size=100&id=22050&format=png&color=000000" width="17px">댓글</div>
                    </div>
                    <div class="free_list_rdate">작성일</div>
                    <div><img scr="#"></div>
                  </div>
                </a>
              </div>
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
