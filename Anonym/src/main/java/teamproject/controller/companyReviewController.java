package teamproject.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.util.DBConn;
import teamproject.vo.*;
import teamproject.vo.PagingUtil;
import teamproject.vo.PostCommentVO;
import teamproject.vo.PostVO;
import teamproject.vo.UserVO;

public class companyReviewController 
{
	public companyReviewController(HttpServletRequest request, HttpServletResponse response, String[] comments) throws ServletException, IOException 
	{
		// System.out.println(comments[comments.length-1].equals("companyInfo.do"));
		// System.out.println(comments[comments.length-1]);
		
		// 기업리뷰 인덱스
		if(comments[comments.length-1].equals("companySearchIndex.do"))
		{
			Search(request, response);
			
		// 기업 검색
		}else if(comments[comments.length-1].equals("companySearch.do"))
		{
			CompanyList(request, response);
		
		// 기업 정보
		}else if(comments[comments.length-1].equals("companyInfo.do"))
		{
			companyInfo(request, response);
		
		// 기업 추천 등록
		}else if(comments[comments.length-1].equals("recommendOk.do"))
		{
			recommendOk(request, response);

		// 기업 리뷰 목록
		}else if(comments[comments.length-1].equals("reviewList.do")) 
		{
			LikeState(request, response);
			reviewList(request, response);

		// 기업 리뷰 조회
		}else if(comments[comments.length-1].equals("reviewView.do")) 
		{
			LikeState(request, response);
			LikeCount(request, response);
			reviewView(request, response);

		// 기업 리뷰 등록
		}else if(comments[comments.length-1].equals("reviewRegister.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				reviewRegister(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				reviewRegisterOk(request, response);
			}

		// 기업 리뷰 수정
		}else if(comments[comments.length-1].equals("reviewModify.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				reviewModify(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				reviewModifyOk(request, response);
			}
			
		// 기업 리뷰 삭제
		}else if(comments[comments.length-1].equals("reviewDelete.do")) 
		{
			reviewDelete(request, response);
			
		// 기업 커뮤니티 글 목록
		}else if(comments[comments.length-1].equals("communityList.do"))
		{
			communityList(request, response);
			
		// 기업 커뮤니티 글,댓글,좋아요(상태, 개수) 조회
		}else if(comments[comments.length-1].equals("communityView.do"))
		{
			LikeState(request, response);
			LikeCount(request, response);
			commentList(request, response);
			communityView(request, response);
			
		// 기업 커뮤니티 글 등록
		}else if(comments[comments.length-1].equals("communityRegister.do"))
		{
			if(request.getMethod().equals("GET"))
			{
				communityRegister(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				communityRegisterOk(request, response);
			}
			
		// 기업 커뮤니티 글 수정
		}else if(comments[comments.length-1].equals("communityModify.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				communityModify(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				communityModifyOk(request, response);
			}
			
		// 기업 커뮤니티 글 삭제
		}else if(comments[comments.length-1].equals("communityDelete.do"))
		{
			communityDelete(request, response);
			
		// 댓글 등록
		}else if(comments[comments.length-1].equals("commentRegister.do")) 
		{
			commentRegisterOk(request, response);
		
		// 댓글 수정
		}else if(comments[comments.length-1].equals("commentModify.do")) 
		{
			commentModifyOk(request, response);
			
		// 댓글 삭제
		}else if(comments[comments.length-1].equals("commentDelete.do")) 
		{
			commentDeleteOk(request, response);
			
		// 좋아요 등록
		}else if(comments[comments.length-1].equals("likeOk.do")) 
		{
			LikeOk(request, response);
		
		// 신고 등록
		}else if(comments[comments.length-1].equals("complaintOk.do")) 
		{
			complaintOk(request, response);
		}
	}
	
	// 기업 인덱스
	public void Search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<CompanyVO> List = new ArrayList<>();
		
		Connection conn = null;
	    PreparedStatement ptmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = DBConn.conn();
	        
	        String sql = "SELECT "
	        		+ "company_name, company_logo, c.company_no, "
	        		+ "SUM(CASE WHEN company_like_state = 'Y' THEN 1 ELSE 0 END) AS like_count, "
	        		+ "SUM(CASE WHEN company_like_state = 'N' THEN 1 ELSE 0 END) AS dislike_count, "
	        		+ "SUM(CASE WHEN company_like_state IN ('Y', 'N') THEN 1 ELSE 0 END) AS total_count, "
	        		+ "ROUND(SUM(CASE WHEN company_like_state = 'Y' THEN 1 ELSE 0 END) / NULLIF(SUM(CASE WHEN company_like_state IN ('Y', 'N') THEN 1 ELSE 0 END), 0) * 100, 0) AS like_percentage, "
	        		+ "ROUND(SUM(CASE WHEN company_like_state = 'N' THEN 1 ELSE 0 END) / NULLIF(SUM(CASE WHEN company_like_state IN ('Y', 'N') THEN 1 ELSE 0 END), 0) * 100, 0) AS dislike_percentage "
	        		+ "FROM company c "
	        		+ "INNER JOIN company_like cl "
	        		+ "ON c.company_no = cl.company_no "
	        		+ "WHERE company_state = 'E' "
	        		+ "GROUP BY c.company_no "
	        		+ "ORDER BY total_count desc, like_percentage desc "
	        		+ "LIMIT 0, 9";
	        
	        System.out.println(sql);
	        
	        ptmt = conn.prepareStatement(sql);
	        
	        rs = ptmt.executeQuery();
	        
			
			while(rs.next())
			{
				CompanyVO vo = new CompanyVO();
				
				vo.setCname(rs.getString("company_name"));
				vo.setClogo(rs.getString("company_logo"));
				vo.setCno(rs.getInt("company_no"));
				
				List.add(vo);
			}

		
	        // 항목별 평균 계산 (리뷰가 있는 경우에만)
//	        double avgCareer = reviewCount > 0 ? totalCareer / reviewCount : 0;
//	        double avgBalance = reviewCount > 0 ? totalBalance / reviewCount : 0;
//	        double avgCulture = reviewCount > 0 ? totalCulture / reviewCount : 0;
//	        double avgManagement = reviewCount > 0 ? totalManagement / reviewCount : 0;
//	        double avgSalary = reviewCount > 0 ? totalSalary / reviewCount : 0;
	        
	        // 요청에 데이터 설정
//	        request.setAttribute("avgCareer", avgCareer);
//	        request.setAttribute("avgBalance", avgBalance);
//	        request.setAttribute("avgCulture", avgCulture);
//	        request.setAttribute("avgManagement", avgManagement);
//	        request.setAttribute("avgSalary", avgSalary);
			
			request.setAttribute("List", List);
	        
	        request.getRequestDispatcher("/WEB-INF/companyReview/companySearch.jsp").forward(request, response);

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setContentType("application/json; charset=UTF-8");
	        response.getWriter().write("{\"error\":\"오류가 발생했습니다.\"}");
	    } finally {
	        try {
	            DBConn.close(rs, ptmt, conn);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		
	}
	
	// 기업 검색(회사 목록)
	public void CompanyList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection conn = null;
	    PreparedStatement ptmt = null;
	    ResultSet rs = null;

	    // 사용자가 입력한 searchValue 값으로 필터링
	    String searchValue = request.getParameter("searchValue");
	    // System.out.println("searchValue: " + searchValue);
	    
	    try {
	        conn = DBConn.conn();
	        
	        // 검색 값이 null이거나 비어 있는 경우 처리
	        if (searchValue == null || searchValue.trim().isEmpty()) {
	            response.setContentType("application/json; charset=UTF-8");
	            response.getWriter().write("{\"message\":\"검색어가 비어있습니다.\"}");
	            return;
	        }
	        
	        String sql = "SELECT company_name, company_no FROM company WHERE company_name LIKE CONCAT('%', ?, '%') AND company_state = 'E'";
	        
	        ptmt = conn.prepareStatement(sql);
	        ptmt.setString(1, searchValue);
	        
	        rs = ptmt.executeQuery();
	        
	        JSONArray jsonArray = new JSONArray();
	        
	        // 찾은 데이터 jsonArray에 담기
	        while (rs.next()) {
	            String company = rs.getString("company_name");
	            int companyNo = rs.getInt("company_no");
	            
	            JSONObject jsonObj = new JSONObject();
	            jsonObj.put("company", company);
	            jsonObj.put("cno", companyNo);
	            jsonArray.put(jsonObj);
	        }
	        
	        // System.out.println("JSON Array: " + jsonArray.toString());

	        // 결과에 따라 응답
	        response.setContentType("application/json; charset=UTF-8");
	        
	        // System.out.println(jsonArray.isEmpty());
	        
	        if (jsonArray.isEmpty()) 
	        {
	        	response.getWriter().write("{\"message\":\"결과가 없습니다.\"}");
	        }else 
	        {
	        	response.getWriter().write(jsonArray.toString());
	        }
	        

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setContentType("application/json; charset=UTF-8");
	        response.getWriter().write("{\"error\":\"오류가 발생했습니다.\"}");
	    } finally {
	        try {
	            DBConn.close(rs, ptmt, conn);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// 회사추천 등록
	public void recommendOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}
		String cno = request.getParameter("cno");
		String crstate = request.getParameter("crstate");
		
		Connection conn = null; 
		PreparedStatement ptmt = null;  
		ResultSet rs = null;
		
		try
		{
			conn = DBConn.conn();
			
			String sql = "select company_like_state from company_like where user_no = ? and company_no = ? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, uno);
			ptmt.setString(2, cno);
			
			rs = ptmt.executeQuery();
			
			if(rs.next())
			{
				sql = "update company_like set company_like_state = ? where user_no = ? and company_no = ? ";
				
				ptmt = conn.prepareStatement(sql);
				
				ptmt.setString(1, crstate);
				ptmt.setString(2, uno);
				ptmt.setString(3, cno);
				
				System.out.println("좋아요 상태 변경" + crstate);
				
			}else{
				sql = "insert into company_like (company_no, user_no, company_like_state) values (?, ?, ?)";

				ptmt = conn.prepareStatement(sql);
				
				ptmt.setString(1, cno);
				ptmt.setString(2, uno);
				ptmt.setString(3, crstate);

				System.out.println("좋아요 등록");
				
			}
			
			int result = ptmt.executeUpdate();
			
			PrintWriter writer = response.getWriter();
			if(result > 0)
			{
				String countSql = "SELECT "
						+ "	SUM(CASE WHEN company_like_state = 'Y' THEN 1 ELSE 0 END) AS like_count, "
						+ " SUM(CASE WHEN company_like_state = 'N' THEN 1 ELSE 0 END) AS dislike_count, "
						+ " SUM(CASE WHEN company_like_state IN ('Y', 'N') THEN 1 ELSE 0 END) AS total_count, "
						+ " ROUND(SUM(CASE WHEN company_like_state = 'Y' THEN 1 ELSE 0 END) / NULLIF(SUM(CASE WHEN company_like_state IN ('Y', 'N') THEN 1 ELSE 0 END), 0) * 100, 0) AS like_percentage, "
						+ " ROUND(SUM(CASE WHEN company_like_state = 'N' THEN 1 ELSE 0 END) / NULLIF(SUM(CASE WHEN company_like_state IN ('Y', 'N') THEN 1 ELSE 0 END), 0) * 100, 0) AS dislike_percentage "
						+ "FROM company_like WHERE company_no = ?;";
				
				System.out.println("회사 추천 상태 비율" + countSql);
				
				ptmt = conn.prepareStatement(countSql);
				ptmt.setString(1, cno);
				rs = ptmt.executeQuery();
				
				int like_count = 0;
				int dislike_count = 0;
				
				if (rs.next()) {
					like_count = rs.getInt("like_percentage");
					dislike_count = rs.getInt("dislike_percentage");
				}
				
				// System.out.println("좋아요 상태가 변경되었습니다.");
				writer.write("OK|" + like_count + "|" + dislike_count);
			}else {
				writer.write("ERROR");
			}
			
			/* response.sendRedirect("companyInfo.do?cno=" + cno + "&uno=" + uno); */
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
	}
	
	// 기업 정보 
	public CompanyVO GetCompanyInfo(HttpServletRequest request, String cno) throws ServletException, IOException 
	{
		String crstate = "";
		int like_count = 0;
		int dislike_count = 0;
		/* String name = ""; */
		
		if( cno == null || cno.equals("")) return null;
	    
	    HttpSession session = request.getSession();
	    UserVO loginUser = (UserVO) session.getAttribute("loginUser");
	    String uno = (loginUser != null) ? Integer.toString(loginUser.getUser_no()) : null;

	    if (uno == null || uno.equals("")) crstate = "D"; 
	    
	    System.out.println(cno);
	    System.out.println(uno);
		
		CompanyVO vo = null;

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			// 회사 정보 조회
			String sql = "SELECT * FROM company WHERE company_no = ? ";
			
			// System.out.println("회사 정보 조회" + sql);
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, cno);
			
			rs = ptmt.executeQuery();
			
			if (rs.next()) 
			{  
				/*
				 * String companyIndustry = rs.getString("company_industry");
				 * 
				 * switch(companyIndustry) { case "ci1": name = "제조업"; break; case "ci2": name =
				 * "건설업"; break; case "ci3": name = "도매 및 소매업"; break; case "ci4": name =
				 * "숙박 및 음식점업"; break; case "ci5": name = "운수업"; break; case "ci6": name =
				 * "통신업"; break; case "ci7": name = "금융 및 보험업"; break; case "ci8": name =
				 * "사업서비스업"; break; default: name = "기타"; }
				 */
			
			
			vo = new CompanyVO();
			
			vo.setCname(rs.getString("company_name"));;
			vo.setClogo(rs.getString("company_logo"));
			vo.setClocation(rs.getString("company_location"));
			vo.setCanniversary(rs.getString("company_anniversary"));
			vo.setCindustry(rs.getString("company_industry")); 
			vo.setCemployee(rs.getString("company_employee"));

			}
			
			// 회사 추천 상태 조회
			if (uno != null && !uno.equals("")) 
			{
				String stateSql = "select company_like_state from company_like where user_no = ? and company_no = ? ";

				// System.out.println("회사 추천 상태 조회" + stateSql);
				
				ptmt = conn.prepareStatement(stateSql);
				ptmt.setString(1, uno);
				ptmt.setString(2, cno);
	
				rs = ptmt.executeQuery();
			
				if(rs.next())
				{
					crstate = rs.getString("company_like_state");
					vo.setCrstate(crstate);
				}
				
				// 회사 추천 개수 조회 
				String countSql = "SELECT "
						+ "	SUM(CASE WHEN company_like_state = 'Y' THEN 1 ELSE 0 END) AS like_count, "
						+ " SUM(CASE WHEN company_like_state = 'N' THEN 1 ELSE 0 END) AS dislike_count, "
						+ " SUM(CASE WHEN company_like_state IN ('Y', 'N') THEN 1 ELSE 0 END) AS total_count, "
						+ " ROUND(SUM(CASE WHEN company_like_state = 'Y' THEN 1 ELSE 0 END) / NULLIF(SUM(CASE WHEN company_like_state IN ('Y', 'N') THEN 1 ELSE 0 END), 0) * 100, 0) AS like_percentage, "
						+ " ROUND(SUM(CASE WHEN company_like_state = 'N' THEN 1 ELSE 0 END) / NULLIF(SUM(CASE WHEN company_like_state IN ('Y', 'N') THEN 1 ELSE 0 END), 0) * 100, 0) AS dislike_percentage "
						+ "FROM company_like WHERE company_no = ?;";
				
				System.out.println("회사 추천 상태 비율" + countSql);
				
				ptmt = conn.prepareStatement(countSql);
				ptmt.setString(1, cno);
				rs = ptmt.executeQuery();
				
				if (rs.next()) {
					like_count = rs.getInt("like_percentage");
					dislike_count = rs.getInt("dislike_percentage");
					vo.setClcount(like_count);  
					vo.setCdlcount(dislike_count);  
					
					System.out.println("like_count" + like_count);
					System.out.println("dislike_count" + dislike_count);
				}
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
//		if(vo != null )
//		{
//			System.out.println(vo.toString());
//		}
		return vo;
	}
	
	// 기업 정보 
	public List<CompanyVO> GetCompany(String cno) throws ServletException, IOException 
	{
		if( cno == null || cno.equals("")) return null;
		
		System.out.println(cno);

		List<CompanyVO> cList = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
	    
	    try 
	    {
	    	conn = DBConn.conn();
		        
	        String sql = "SELECT company_name, company_logo, company_no, company_industry "
	        		+ "FROM company "
	        		+ "WHERE company_state = 'E' ";
//	        		+ "AND company_industry = ? "
//	        		+ "LIMIT 0, 5 ";
	        
	        ptmt = conn.prepareStatement(sql);
	        
	        rs = ptmt.executeQuery();
			
			while(rs.next())
			{
				CompanyVO vo = new CompanyVO();
				
				vo.setCname(rs.getString("company_name"));
				vo.setClogo(rs.getString("company_logo"));
				vo.setCno(rs.getInt("company_no"));
				vo.setCindustry(rs.getString("company_industry"));
				
				cList.add(vo);
			}
				
	    } catch (Exception e) 
	    {
	        e.printStackTrace();
	    } finally 
	    {
	        try {
	            DBConn.close(rs, ptmt, conn);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return cList;
	}
	
	// 기업 정보
	public void companyInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// 회사 인클루드 정보
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);

		request.setAttribute("vo", vo);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		//
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}

		Connection conn = null;

		// 글 목록
		List<PostVO> List = new ArrayList<>();

		PreparedStatement ptmtPost = null;
		ResultSet rsPost = null;
		
		// 리뷰 목록
		PreparedStatement ptmtReview = null;
		ResultSet rsReview = null;
		
	    // 항목별 총합을 저장할 변수 초기화
	    double totalCareer = 0;
	    double totalBalance = 0;
	    double totalCulture = 0;
	    double totalManagement = 0;
	    double totalSalary = 0;
	    double total = 0;
	    
	    int reviewCount = 0;
		
		try 
		{
			conn = DBConn.conn();
			
			// 글 목록
			String sqlPost = "SELECT post_hit, p.post_no, post_title, post_content, date_format(p.post_registration_date, '%Y-%m-%d') as post_registration_date, user_id, "
					+ "(select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount, p.company_no " 
					+ "FROM post p, user u, board b, company c  "
					+ "WHERE p.user_no = u.user_no "
					+ "AND p.board_no = b.board_no "
					+ "AND p.company_no = c.company_no "
					+ "AND post_state = 'E' "
					+ "AND b.board_no = 2 "
					+ "AND p.company_no = ? "
			        + "ORDER BY post_no DESC "
			        + "LIMIT 0, 5 ";
			
			ptmtPost = conn.prepareStatement(sqlPost);
			ptmtPost.setString(1, cno);
			
			rsPost = ptmtPost.executeQuery();
			
			while(rsPost.next())
			{
				PostVO pvo = new PostVO();
				
				pvo.setPost_hit(rsPost.getString("post_hit"));
				pvo.setPost_no(rsPost.getString("post_no"));
				pvo.setPost_title(rsPost.getString("post_title"));
				pvo.setPost_content(rsPost.getString("post_content"));
				pvo.setPost_registration_date(rsPost.getString("post_registration_date"));
				pvo.setUser_id(rsPost.getString("user_id"));
				pvo.setPccount(rsPost.getString("pccount"));
				
				List.add(pvo);
			}
			
			
			// 
			String sqlReview = "SELECT post_hit, p.post_no, post_title, post_content, post_content2, date_format(p.post_registration_date, '%Y.%m.%d') as post_registration_date, "
					+ "pr.*, user_id, u.user_no, "
					+ "(select count(*) from post_like pl WHERE pl.post_no = p.post_no AND post_like_state = 'E') as plcnt, "
					+ "(select post_like_state from post_like pl WHERE pl.post_no = p.post_no AND post_like_state = 'E') as post_like_state "
					+ "FROM post p "
					+ "INNER JOIN post_review pr ON p.post_no = pr.post_no "
					+ "INNER JOIN board b ON b.board_no = p.board_no "
					+ "INNER JOIN user u ON u.user_no = p.user_no "
					+ "WHERE post_state = 'E' "
					+ "AND b.board_no = 3 "
					+ "AND pr.company_no = ? ";
//			        + "ORDER BY p.post_no DESC "
//			 		+ "LIMIT 0, 1"; 
			
			ptmtReview = conn.prepareStatement(sqlReview);
			ptmtReview.setString(1, cno);
			
			rsReview = ptmtReview.executeQuery();

			while(rsReview.next())
			{
				PostVO pvo = new PostVO();
				
				pvo.setPost_hit(rsReview.getString("post_hit"));
				pvo.setPost_no(rsReview.getString("post_no"));
				pvo.setPost_title(rsReview.getString("post_title"));
				pvo.setPost_content(rsReview.getString("post_content"));
				pvo.setPost_content2(rsReview.getString("post_content2"));
				pvo.setPost_registration_date(rsReview.getString("post_registration_date"));
				pvo.setUser_id(rsReview.getString("user_id"));
				pvo.setUser_no(rsReview.getString("user_no"));
				pvo.setPost_like_state(rsReview.getString("post_like_state"));
				pvo.setPlcnt(rsReview.getString("plcnt"));
				
				pvo.setPost_review_career(rsReview.getInt("post_review_career"));
				pvo.setPost_review_balance(rsReview.getInt("post_review_balance"));
				pvo.setPost_review_culture(rsReview.getInt("post_review_culture"));
				pvo.setPost_review_management(rsReview.getInt("post_review_management"));
				pvo.setPost_review_salary(rsReview.getInt("post_review_salary"));
				pvo.setPost_review_starrating(rsReview.getInt("post_review_starrating"));
				
	            // 리스트에 추가하기 전에 총합 계산
	            totalCareer += pvo.getPost_review_career();
	            totalBalance += pvo.getPost_review_balance();
	            totalCulture += pvo.getPost_review_culture();
	            totalManagement += pvo.getPost_review_management();
	            totalSalary += pvo.getPost_review_salary();
	            total += pvo.getPost_review_starrating();
	            reviewCount++;

	            request.setAttribute("pvo", pvo);
			}
			
	        // 항목별 평균 계산 (리뷰가 있는 경우에만)
	        double avgCareer = reviewCount > 0 ? totalCareer / reviewCount : 0;
	        double avgBalance = reviewCount > 0 ? totalBalance / reviewCount : 0;
	        double avgCulture = reviewCount > 0 ? totalCulture / reviewCount : 0;
	        double avgManagement = reviewCount > 0 ? totalManagement / reviewCount : 0;
	        double avgSalary = reviewCount > 0 ? totalSalary / reviewCount : 0;
	        double avgtotal = reviewCount > 0 ?  Math.round(((total / 5 ) / reviewCount) * 10) / 10.0 : 0;
	        
	        // 요청에 데이터 설정
	        request.setAttribute("avgCareer", avgCareer);
	        request.setAttribute("avgBalance", avgBalance);
	        request.setAttribute("avgCulture", avgCulture);
	        request.setAttribute("avgManagement", avgManagement);
	        request.setAttribute("avgSalary", avgSalary);
	        request.setAttribute("avgtotal", avgtotal);
			
			request.setAttribute("vo", vo);
			request.setAttribute("List", List);
			
			request.getRequestDispatcher("/WEB-INF/companyReview/companyInfo.jsp").forward(request, response);
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rsPost, ptmtPost, conn);
				DBConn.close(rsReview, ptmtReview, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
			
	}
	
	// 기업 리뷰 목록
	public void reviewList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}

		List<PostVO> pList = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
	    // 항목별 총합을 저장할 변수 초기화
	    double totalCareer = 0;
	    double totalBalance = 0;
	    double totalCulture = 0;
	    double totalManagement = 0;
	    double totalSalary = 0;
	    double total = 0;
	    int reviewCount = 0;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "SELECT post_hit, p.post_no, post_title, post_content, post_content2, date_format(p.post_registration_date, '%Y.%m.%d') as post_registration_date, "
					+ "pr.*, user_id, u.user_no, "
					+ "(select count(*) from post_like pl WHERE pl.post_no = p.post_no AND post_like_state = 'E') as plcnt, "
					+ "(select post_like_state from post_like pl WHERE pl.post_no = p.post_no AND post_like_state = 'E') as post_like_state "
					+ "FROM post p "
					+ "INNER JOIN post_review pr ON p.post_no = pr.post_no "
					+ "INNER JOIN board b ON b.board_no = p.board_no "
					+ "INNER JOIN user u ON u.user_no = p.user_no "
					+ "WHERE post_state = 'E' "
					+ "AND b.board_no = 3 "
					+ "AND pr.company_no = ? "
			        + "ORDER BY p.post_no DESC ";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, cno);
			
			rs = ptmt.executeQuery();

			while(rs.next())
			{
				PostVO pvo = new PostVO();
				
				pvo.setPost_hit(rs.getString("post_hit"));
				pvo.setPost_no(rs.getString("post_no"));
				pvo.setPost_title(rs.getString("post_title"));
				pvo.setPost_content(rs.getString("post_content"));
				pvo.setPost_content2(rs.getString("post_content2"));
				pvo.setPost_registration_date(rs.getString("post_registration_date"));
				pvo.setUser_id(rs.getString("user_id"));
				pvo.setUser_no(rs.getString("user_no"));
				pvo.setPost_like_state(rs.getString("post_like_state"));
				pvo.setPlcnt(rs.getString("plcnt"));
				
				pvo.setPost_review_career(rs.getInt("post_review_career"));
				pvo.setPost_review_balance(rs.getInt("post_review_balance"));
				pvo.setPost_review_culture(rs.getInt("post_review_culture"));
				pvo.setPost_review_management(rs.getInt("post_review_management"));
				pvo.setPost_review_salary(rs.getInt("post_review_salary"));
				pvo.setPost_review_starrating(rs.getInt("post_review_starrating"));
				
	            // 리스트에 추가하기 전에 총합 계산
	            totalCareer += pvo.getPost_review_career();
	            totalBalance += pvo.getPost_review_balance();
	            totalCulture += pvo.getPost_review_culture();
	            totalManagement += pvo.getPost_review_management();
	            totalSalary += pvo.getPost_review_salary();
	            total += pvo.getPost_review_starrating();
	            reviewCount++;

				pList.add(pvo);
			}
			
	        // 항목별 평균 계산 (리뷰가 있는 경우에만)
	        double avgCareer = reviewCount > 0 ? totalCareer / reviewCount : 0;
	        double avgBalance = reviewCount > 0 ? totalBalance / reviewCount : 0;
	        double avgCulture = reviewCount > 0 ? totalCulture / reviewCount : 0;
	        double avgManagement = reviewCount > 0 ? totalManagement / reviewCount : 0;
	        double avgSalary = reviewCount > 0 ? totalSalary / reviewCount : 0;
	        double avgtotal = reviewCount > 0 ?  Math.round(((total / 5 ) / reviewCount) * 10) / 10.0 : 0;
	        
	        // 요청에 데이터 설정
	        request.setAttribute("avgCareer", avgCareer);
	        request.setAttribute("avgBalance", avgBalance);
	        request.setAttribute("avgCulture", avgCulture);
	        request.setAttribute("avgManagement", avgManagement);
	        request.setAttribute("avgSalary", avgSalary);
	        request.setAttribute("avgtotal", avgtotal);
			
			request.setAttribute("pList", pList);
			request.setAttribute("vo", vo);
			
			
			request.getRequestDispatcher("/WEB-INF/companyReview/reviewList.jsp").forward(request, response);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	// 기업 리뷰 조회
	public void reviewView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.getRequestDispatcher("/WEB-INF/companyReview/reviewView.jsp").forward(request, response);
	}
	
	// 기업 리뷰 등록
	public void reviewRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);

		request.setAttribute("vo", vo);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}
		
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "SELECT user_no FROM anonym.company_employee where company_no = ? ";
			
			ptmt = conn.prepareStatement(sql);
			
			ptmt.setString(1, cno);
			
			rs = ptmt.executeQuery();
			
			boolean isMatch = false;
			
			while(rs.next())
			{
				String Uno = rs.getString("user_no"); // company_employee에서 조회된 user_no

				System.out.println("uno : " + uno);
				System.out.println("Uno : " + Uno);
				
				
		        if (uno != null && uno.equals(Uno)) 
		        {
		        	isMatch = true; // 일치하면 true로 설정
		            break;
		        }
				
	        } 
	        	
        	if (isMatch) {
        	    request.getRequestDispatcher("/WEB-INF/companyReview/reviewRegister.jsp").forward(request, response);
        	} else {
        	    response.sendRedirect("companyInfo.do?cno=" + cno);
        	}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public void reviewRegisterOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	 
		String cno = request.getParameter("cno");
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}
		
		CompanyVO vo = GetCompanyInfo(request, cno);

		String title = request.getParameter("post_title");
		String good = request.getParameter("good");
		String bad = request.getParameter("bad");
		
		int career = Integer.parseInt(request.getParameter("career"));
		int balance = Integer.parseInt(request.getParameter("balance"));
		int salary = Integer.parseInt(request.getParameter("salary"));
		int culture = Integer.parseInt(request.getParameter("culture"));
		int management = Integer.parseInt(request.getParameter("management"));
		int sum =  career + balance + salary + culture + management;
		String pno = "";

		System.out.println("회사 리뷰");
		System.out.println(cno);
		System.out.println(title);
		System.out.println(good);
		System.out.println(bad);

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "insert into post (user_no, post_title, post_content, post_content2, board_no) values (?, ?, ?, ?, '3')";
			
			ptmt = conn.prepareStatement(sql);
			
			ptmt.setString(1, uno);
			ptmt.setString(2, title);
			ptmt.setString(3, good);
			ptmt.setString(4, bad);
			
			
			int result = ptmt.executeUpdate();
			
			if(result > 0)
			{
				rs = ptmt.executeQuery("SELECT LAST_INSERT_ID() as post_no");
				
				if( rs.next() )
				{
					pno = rs.getString("post_no");
					
					sql = "insert into post_review (post_no, company_no, post_review_career, post_review_balance, post_review_salary, post_review_culture, post_review_management, post_review_starrating) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
					
					ptmt = conn.prepareStatement(sql);
					
					ptmt.setString(1, pno);
					ptmt.setString(2, cno);
					ptmt.setInt(3, career);
					ptmt.setInt(4, balance);
					ptmt.setInt(5, salary);
					ptmt.setInt(6, culture);
					ptmt.setInt(7, management);
					ptmt.setInt(8, sum);
					
					ptmt.executeUpdate();
				}
			}
			
			response.sendRedirect("reviewList.do?cno=" + cno);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 기업 리뷰 수정
	public void reviewModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		 
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
	
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}

		
		String pno = request.getParameter("pno");

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "SELECT p.*,u.*, pr.* "
					+ "FROM post p, user u, post_review pr "
					+ "WHERE p.user_no = u.user_no "
					+ "AND p.post_no = pr.post_no "
					+ "AND p.post_no = ?";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, pno);
			
			rs = ptmt.executeQuery();
			
			if(rs.next())
			{
				PostVO pvo = new PostVO();
				
				pvo.setPost_title(rs.getString("post_title"));
				pvo.setPost_content(rs.getString("post_content"));
				pvo.setPost_content2(rs.getString("post_content2"));
				pvo.setPost_no(rs.getString("post_no"));
				pvo.setPost_review_balance(rs.getInt("post_review_balance"));
				pvo.setPost_review_career(rs.getInt("post_review_career"));
				pvo.setPost_review_culture(rs.getInt("post_review_culture"));
				pvo.setPost_review_management(rs.getInt("post_review_management"));
				pvo.setPost_review_salary(rs.getInt("post_review_salary"));
				
				request.setAttribute("pvo", pvo);
			}
			
			request.setAttribute("vo", vo);
			request.getRequestDispatcher("/WEB-INF/companyReview/reviewModify.jsp").forward(request, response);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void reviewModifyOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		System.out.println("cno" + cno);
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}

		String pno = request.getParameter("pno");
		String title = request.getParameter("post_title");
		String good = request.getParameter("good");
		String bad = request.getParameter("bad");
		int career = Integer.parseInt(request.getParameter("career"));
		int balance = Integer.parseInt(request.getParameter("balance"));
		int salary = Integer.parseInt(request.getParameter("salary"));
		int culture = Integer.parseInt(request.getParameter("culture"));
		int management = Integer.parseInt(request.getParameter("management"));
		int sum =  career + balance + salary + culture + management;

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "update post set post_title = ?, post_content = ?, post_content2 = ? where  post_no = ? ";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, title);
			ptmt.setString(2, good);
			ptmt.setString(3, bad);
			ptmt.setString(4, pno);
			
			int result = ptmt.executeUpdate();
			
			if(result > 0)
			{
					
				sql = "update post_review set post_review_starrating = ?, post_review_career = ?, post_review_balance = ?, post_review_salary = ?, post_review_culture = ?, post_review_management = ? where post_no = ? ";
				
				ptmt = conn.prepareStatement(sql);
				
				ptmt.setInt(1, sum);
				ptmt.setInt(2, career);
				ptmt.setInt(3, balance);
				ptmt.setInt(4, salary);
				ptmt.setInt(5, culture);
				ptmt.setInt(6, management);
				ptmt.setString(7, pno);
				
//				System.out.println(sum);
//				System.out.println(career);
//				System.out.println(balance);
//				System.out.println(salary);
//				System.out.println(culture);
//				System.out.println(management);
//				System.out.println(pno);

				ptmt.executeUpdate();
			}
			
			request.setAttribute("vo", vo);

			response.sendRedirect("reviewList.do?cno=" + cno);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 기업 리뷰 삭제
	public void reviewDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		String pno = request.getParameter("pno");

		Connection conn = null;
		PreparedStatement ptmt = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "update post set post_state = 'D' where post_no = ? ";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, pno);
			
			ptmt.executeUpdate();
			
			request.setAttribute("vo", vo);
			
			response.sendRedirect("reviewList.do?cno=" + cno);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	// 기업 커뮤니티 글 목록
	public void communityList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// 회사 include
		String cno = request.getParameter("cno");
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);

		// 글 목록
		List<PostVO> List = new ArrayList<>();
		
		// 검색어
		String searchValue = request.getParameter("searchValue");
	    if (searchValue == null) searchValue = "";
		
	    // 페이징
	    int total = 0;
	    int nowPage = 1;
		if(request.getParameter("nowPage") != null) nowPage = Integer.parseInt(request.getParameter("nowPage"));

		Connection conn = null;
		
		// 글 갯수
		PreparedStatement ptmtTotal = null;  
		ResultSet rsTotal = null; 
		
		// 글 목록
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			// 페이징
			String sqlTotal = "SELECT count(*) AS total FROM post p, board b, company c "
					+ "WHERE p.board_no = b.board_no "
					+ "AND p.company_no = c.company_no "
					+ "AND post_state = 'E' "
					+ "AND b.board_no = 2 "
					+ "AND c.company_no = ? ";
			
			if(searchValue.equals(""))
			{
				ptmtTotal = conn.prepareStatement(sqlTotal);
				ptmtTotal.setString(1, cno);
			}else
			{
				sqlTotal += "AND (post_title LIKE CONCAT('%', ?, '%') OR post_content LIKE CONCAT('%', ?, '%')) ";
				ptmtTotal = conn.prepareStatement(sqlTotal);
				ptmtTotal.setString(1, searchValue);
				ptmtTotal.setString(2, searchValue);
				ptmtTotal.setString(3, cno);
			}
				
			rsTotal = ptmtTotal.executeQuery();
			
			if(rsTotal.next())
			{
				total = rsTotal.getInt("total");
			}
			
			PagingUtil paging = new PagingUtil(nowPage, total, 10);
			
			// 글 목록
			String sql = "SELECT post_hit, p.post_no, post_title, post_content, date_format(p.post_registration_date, '%Y-%m-%d') as post_registration_date, user_id, "
					+ "(select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount, c.company_no " 
					+ "FROM post p, user u, board b, company c "
					+ "WHERE p.user_no = u.user_no "
					+ "AND p.board_no = b.board_no "
					+ "AND p.company_no = c.company_no "
					+ "AND post_state = 'E' "
					+ "AND b.board_no = 2 "
					+ "AND c.company_no = ? "
			        + "ORDER BY post_no DESC "
			        + "LIMIT ?, ? ";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, cno);
			ptmt.setInt(2, paging.getStart());
			ptmt.setInt(3, paging.getPerPage());
			
			rs = ptmt.executeQuery();
			
			// 검색어 있을 경우
			if(!searchValue.equals(""))
			{
				sql = "SELECT post_hit, p.post_no, post_title, post_content, date_format(p.post_registration_date, '%Y-%m-%d') as post_registration_date, user_id, "
						+ "(select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount, c.company_no " 
						+ "FROM post p, user u, board b, company c "
						+ "WHERE p.user_no = u.user_no "
						+ "AND p.board_no = b.board_no "
						+ "AND p.company_no = c.company_no "
						+ "AND b.board_no = 2 "
						+ "AND c.company_no = ? "
						+ "AND post_state = 'E' "
						+ "AND (post_title LIKE CONCAT('%', ?, '%') OR post_content LIKE CONCAT('%', ?, '%')) "
				        + "ORDER BY post_no DESC "
				        + "LIMIT ?, ? ";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1, cno);
				ptmt.setString(2, searchValue);
				ptmt.setString(3, searchValue);
				ptmt.setInt(4, paging.getStart());
				ptmt.setInt(5, paging.getPerPage());
				
				rs = ptmt.executeQuery();
			}
			
			while(rs.next())
			{
				PostVO pvo = new PostVO();
				
				pvo.setPost_hit(rs.getString("post_hit"));
				pvo.setPost_no(rs.getString("post_no"));
				pvo.setPost_title(rs.getString("post_title"));
				pvo.setPost_content(rs.getString("post_content"));
				pvo.setPost_registration_date(rs.getString("post_registration_date"));
				pvo.setUser_id(rs.getString("user_id"));
				pvo.setPccount(rs.getString("pccount"));
				pvo.setPost_no(rs.getString("post_no"));
				
				List.add(pvo);
			}
			
			request.setAttribute("vo", vo);
			request.setAttribute("List", List);
			request.setAttribute("paging", paging);
			
			request.getRequestDispatcher("/WEB-INF/companyReview/communityList.jsp").forward(request, response);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	// 기업 커뮤니티 글 조회
	public void communityView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		String pno = request.getParameter("pno");

		Connection conn = null;
		PreparedStatement ptmtHit = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			// 조회수 증가
			String sqlHit  = "update post set post_hit = post_hit + 1 where post_no = ?";
			ptmtHit = conn.prepareStatement(sqlHit);
			ptmtHit.setString(1, pno);
			ptmtHit.executeUpdate();
			
			// 상세페이지
			String sql = "SELECT p.*,u.* "
					+ "FROM post p, user u "
					+ "WHERE p.user_no = u.user_no "
					+ "AND post_no = ?";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, pno);
			
			rs = ptmt.executeQuery();
			
			// 찾은 데이터 request에 담기
			if(rs.next())
			{
				PostVO pvo = new PostVO();
				
				pvo.setPost_hit(rs.getString("post_hit"));
				pvo.setPost_no(rs.getString("post_no"));
				pvo.setPost_title(rs.getString("post_title"));
				pvo.setPost_content(rs.getString("post_content"));
				pvo.setPost_registration_date(rs.getString("post_registration_date"));
				pvo.setUser_no(rs.getString("user_no"));
				pvo.setUser_id(rs.getString("user_id"));
				
				request.setAttribute("pvo", pvo);
			}
			
			request.setAttribute("vo", vo);

			// 포워드
			request.getRequestDispatcher("/WEB-INF/companyReview/communityView.jsp").forward(request, response);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(ptmtHit, null);
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 기업 커뮤니티 글 등록
	public void communityRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		request.setAttribute("vo", vo);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		request.getRequestDispatcher("/WEB-INF/companyReview/communityRegister.jsp").forward(request, response);
	}

	public void communityRegisterOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");

		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
	    String uno = request.getParameter("user_no");
	    String bno = request.getParameter("board_no"); 
		String title = request.getParameter("post_title");
		String content = request.getParameter("post_content");
		String pno = null;
		
//		System.out.println(title);
//		System.out.println(content);

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "insert into post (user_no, post_title, post_content, board_no, company_no) values (?, ?, ?, ?, ?)";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, uno);
			ptmt.setString(2, title);
			ptmt.setString(3, content);
			ptmt.setString(4, bno);
			ptmt.setString(5, cno);
			
			int result = ptmt.executeUpdate();
			
			if(result > 0)
			{
				rs = ptmt.executeQuery("SELECT LAST_INSERT_ID() as post_no");
				
				if( rs.next() )
				{
					pno = rs.getString("post_no");
				}
			}
			
			request.setAttribute("vo", vo);
			
			response.sendRedirect("communityView.do?pno="+pno+"&cno="+cno);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 기업 커뮤니티 글 수정
	public void communityModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		String pno = request.getParameter("pno");

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "SELECT p.*,u.* "
					+ "FROM post p, user u "
					+ "WHERE p.user_no = u.user_no "
					+ "AND post_no = ?";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, pno);
			
			rs = ptmt.executeQuery();
			
			if(rs.next())
			{
				PostVO pvo = new PostVO();
				
				pvo.setPost_title(rs.getString("post_title"));
				pvo.setPost_content(rs.getString("post_content"));
				pvo.setPost_no(rs.getString("post_no"));

				request.setAttribute("pvo", pvo);
			}
			
			request.setAttribute("vo", vo);
			
			request.getRequestDispatcher("/WEB-INF/companyReview/communityModify.jsp").forward(request, response);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void communityModifyOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// notice_board 테이블에 수정데이터 update 처리
		request.setCharacterEncoding("UTF-8");
		
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		String pno = request.getParameter("pno");
		String title = request.getParameter("post_title");
		String content = request.getParameter("post_content");

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "update post set post_title = ?, post_content = ? where post_no = ? ";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, title);
			ptmt.setString(2, content);
			ptmt.setString(3, pno);
			
			ptmt.executeUpdate();
			
			request.setAttribute("vo", vo);
			
			response.sendRedirect("communityView.do?pno=" + pno + "&cno=" + cno);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 기업 커뮤니티 글 삭제
	public void communityDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		String pno = request.getParameter("pno");

		Connection conn = null;
		PreparedStatement ptmt = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "update post set post_state = 'D' where post_no = ? ";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, pno);
			
			ptmt.executeUpdate();
			
			request.setAttribute("vo", vo);
			
			response.sendRedirect("communityList.do?cno=" + cno);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 댓글 조회
	public void commentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		String pno = request.getParameter("pno");
		List<PostCommentVO> List = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		int pccount = 0;
		
		try 
		{
			conn = DBConn.conn();
			
//			String sql = "SELECT post_comment_no, post_comment_content, post_comment_registration_date "
//					+ "(SELECT count(*) FROM post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount "
//					+ "FROM post_comment pc "
//					+ "INNER JOIN post p "
//					+ "ON pc.post_no = p.post_no "
//					+ "WHERE post_comment_state = 'E' "
//					+ "AND p.post_no = ? ";
			String sql = "SELECT post_comment_no, post_comment_content, date_format(pc.post_comment_registration_date, '%Y-%m-%d') as post_comment_registration_date, p.post_no, pc.user_no, "
					+ "(select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount, user_id "
					+ "from post_comment pc "
					+ "inner join post p "
					+ "on pc.post_no = p.post_no "
					+ "inner join user u "
					+ "on pc.user_no = u.user_no "
					+ "WHERE post_comment_state = 'E' "
					+ "AND p.post_no = ? ";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, pno);
			
			rs = ptmt.executeQuery();
			
			while(rs.next())
			{
				if (pccount == 0) 
				{
					pccount = rs.getInt("pccount"); 
			    }
				
				PostCommentVO pvo = new PostCommentVO();
				
				pvo.setPost_comment_no(rs.getString("post_comment_no"));
				pvo.setPost_comment_content(rs.getString("post_comment_content"));
				pvo.setPost_comment_registration_date(rs.getString("post_comment_registration_date"));
				pvo.setPost_no(rs.getString("post_no"));
				pvo.setUser_id(rs.getString("user_id"));
				pvo.setUser_no(rs.getString("user_no"));
				
				pno = rs.getString("post_no");
				
				List.add(pvo);
			}
			
//			System.out.println("댓글" + List.size());
			
			request.setAttribute("vo", vo);
			request.setAttribute("pccount", pccount);
			request.setAttribute("List", List);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 댓글 등록
	public void commentRegisterOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");

		String cno = request.getParameter("cno");
		
		System.out.println("cno : " + cno);
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		String pno = request.getParameter("pno");
		String uno = request.getParameter("uno");
		String content = request.getParameter("content");
		
//		System.out.println(pno);
//		System.out.println(uno);
		
		// db에 free_board에 데이터 추가하기
		Connection conn = null; 
		PreparedStatement ptmt = null; 
		
		try
		{
			conn = DBConn.conn();
			
			String sql = "insert into post_comment (post_no, user_no, post_comment_content) values (?, ?, ?)";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, pno);
			ptmt.setString(2, uno);
			ptmt.setString(3, content);
			
			int result = ptmt.executeUpdate();
			
			if(result > 0)
			{
//				System.out.println(content);
			}
			
			request.setAttribute("vo", vo);
			
			response.sendRedirect("communityView.do?pno=" + pno + "&cno=" + cno);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}	
	}
	
	// 댓글 수정
	public void commentModifyOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		request.setCharacterEncoding("UTF-8");
		String content = request.getParameter("content");
		String pno = request.getParameter("pno");
			
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;  

		try
		{
			conn = DBConn.conn();
			
			String sql = "update post_comment set post_comment_content = ? where post_comment_no = ?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, content);
			ptmt.setString(2, cno);
			
			int result = ptmt.executeUpdate();
			
			if(result > 0)
			{
//				System.out.println(content);
			}
			
			request.setAttribute("vo", vo);
			
			response.sendRedirect("communityView.do?pno=" + pno);
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 댓글 삭제
	public void commentDeleteOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		String pno = request.getParameter("pno");
		String c_no = request.getParameter("c_no");
		
		Connection conn = null; 
		PreparedStatement ptmt = null;  
		
		try
		{
			conn = DBConn.conn();
			
			// DB에서 실제 삭제 
			// String sql = "delete from comment where cno = ?";
			
			// DB에서 비활성화
			String sql = "update post_comment set post_comment_state = 'D' where post_comment_no = ?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, c_no);
			
			int result = ptmt.executeUpdate();
			
			if(result>0)
			{
//				System.out.println(cno);
			}
			
			request.setAttribute("vo", vo);
			
			response.sendRedirect("communityView.do?pno=" + pno + "&cno=" + cno);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 좋아요 등록
	public void LikeOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}
		
		String pno = request.getParameter("pno");
		String plstate = request.getParameter("plstate");
		
//		System.out.println("좋아요 등록");
//		System.out.println("좋아요 cno" + cno);
//		System.out.println(uno);
//		System.out.println(pno);
//		System.out.println("상태" + plstate);
		
		Connection conn = null; 
		PreparedStatement ptmt = null;  
		ResultSet rs = null;
		
		try
		{
			conn = DBConn.conn();
			
			String sql = "select post_like_state from post_like where user_no = ? and post_no = ? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, uno);
			ptmt.setString(2, pno);
			
			rs = ptmt.executeQuery();
			
			if(rs.next())
			{
				sql = "update post_like set post_like_state = ? where user_no = ? and post_no = ? ";
				
				ptmt = conn.prepareStatement(sql);
				
				ptmt.setString(1, plstate);
				ptmt.setString(2, uno);
				ptmt.setString(3, pno);
				
//				System.out.println("좋아요 상태 변경");
				
			}else{
				sql = "insert into post_like (post_no, user_no, post_like_state) values (?, ?, ?)";

				ptmt = conn.prepareStatement(sql);
				
				ptmt.setString(1, pno);
				ptmt.setString(2, uno);
				ptmt.setString(3, plstate);

//				System.out.println("좋아요 등록");
				
			}
			
			int result = ptmt.executeUpdate();
			
			if(result > 0)
			{
				// System.out.println("좋아요 상태가 변경되었습니다.");
			}
			
//			System.out.println("vo" + vo);
			
			request.setAttribute("vo", vo);
			
			response.sendRedirect("communityView.do?pno=" + pno + "&cno=" + cno);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 좋아요 상태 조회
	public void LikeState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}
		
		String pno = request.getParameter("pno");
		
		System.out.println("User No (uno): " + uno);
		System.out.println("Post No (pno): " + pno);
		
		Connection conn = null; 
		PreparedStatement ptmt = null;  
		ResultSet rs = null;
		
		String plstate = "";
		
		try
		{
			conn = DBConn.conn();
			
			String sql = "select post_like_state from post_like where user_no = ? and post_no = ? ";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, uno);
			ptmt.setString(2, pno);

			rs = ptmt.executeQuery();
			
			if(rs.next())
			{
				plstate = rs.getString("post_like_state");
				
				request.setAttribute("plstate", plstate);
			}
			
			request.setAttribute("vo", vo);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 좋아요 개수
	public void LikeCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String pno = request.getParameter("pno");
//		System.out.println("Post No (pno): " + pno);
		
		Connection conn = null; 
		PreparedStatement ptmt = null;  
		ResultSet rs = null;
		int lpcnt = 0;
		
		try
		{
			conn = DBConn.conn();
			
			String sql = "select count(*) as lpcnt from post_like where post_no = ? and post_like_state = 'E' ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, pno);
			
			rs = ptmt.executeQuery();
			
			if(rs.next())
			{
				lpcnt = rs.getInt("lpcnt");     
				
				request.setAttribute("lpcnt", lpcnt);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 신고 등록
	public void complaintOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		List<CompanyVO> cList = GetCompany(cno);
		
		request.setAttribute("cList", cList);
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}
		
		String pno = request.getParameter("pno");
		String reason = request.getParameter("reason");
		String reason_detail = request.getParameter("reason_detail");
		
//		System.out.println("신고 정보");
//		System.out.println(uno);
//		System.out.println(pno);
//		System.out.println(reason);
//		System.out.println(reason_detail);
		
		Connection conn = null; 
		PreparedStatement ptmt = null;  
		ResultSet rs = null;
		
		try
		{
			conn = DBConn.conn();
			
			String sql = "select post_complaint_state from post_complaint where user_no = ? and post_no = ? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, uno);
			ptmt.setString(2, pno);
			
			rs = ptmt.executeQuery();
			
			if(rs.next())
			{
//				System.out.println("신고 수정");
				
				sql = "update post_complaint set post_complaint_reason = ?, post_complaint_reason_other = ? where user_no = ? and post_no = ? ";
				
				ptmt = conn.prepareStatement(sql);
				
				ptmt.setString(1, reason);
				ptmt.setString(2, reason_detail);
				ptmt.setString(3, uno);
				ptmt.setString(4, pno);

//				System.out.println("신고 수정 완료");
			}else
			{
//				System.out.println("신고 등록");

				sql = "insert into post_complaint (post_no, user_no, post_complaint_state, post_complaint_reason, post_complaint_reason_other) values (?, ?, ?, ?, ?)";
				
				ptmt = conn.prepareStatement(sql);
				
				ptmt.setString(1, pno);
				ptmt.setString(2, uno);
				ptmt.setString(3, "E");
				ptmt.setString(4, reason);
				ptmt.setString(5, reason_detail);
			}
			
			int result = ptmt.executeUpdate();
			
//			System.out.println(result);
			
			if(result > 0)
			{
				System.out.println("신고 되었습니다.");
			}
			
			response.sendRedirect("communityView.do?pno=" + pno + "&cno=" + cno);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(ptmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
}
