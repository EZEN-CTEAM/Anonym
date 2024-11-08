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
import teamproject.vo.CompanyVO;
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
		
		// 기업리뷰 ?��?��?��
		if(comments[comments.length-1].equals("companySearchIndex.do"))
		{
			Search(request, response);
			
		// 기업 �??��
		}else if(comments[comments.length-1].equals("companySearch.do"))
		{
			CompanyList(request, response);
		
		// 기업 ?���?
		}else if(comments[comments.length-1].equals("companyInfo.do"))
		{
			companyInfo(request, response);
		
		// 기업 추천 ?���?
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
			reviewView(request, response);

		// 기업 리뷰 ?���?
		}else if(comments[comments.length-1].equals("reviewRegister.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				reviewRegister(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				reviewRegisterOk(request, response);
			}

		// 기업 리뷰 ?��?��
		}else if(comments[comments.length-1].equals("reviewModify.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				reviewModify(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				reviewModifyOk(request, response);
			}
			
		// 기업 리뷰 ?��?��
		}else if(comments[comments.length-1].equals("reviewDelete.do")) 
		{
			reviewDelete(request, response);
			
		// 기업 커�?�니?�� �? 목록
		}else if(comments[comments.length-1].equals("communityList.do"))
		{
			communityList(request, response);
			
		// 기업 커�?�니?�� �?,?���?,좋아?��(?��?��, 개수) 조회
		}else if(comments[comments.length-1].equals("communityView.do"))
		{
			LikeState(request, response);
			LikeCount(request, response);
			commentList(request, response);
			communityView(request, response);
			
		// 기업 커�?�니?�� �? ?���?
		}else if(comments[comments.length-1].equals("communityRegister.do"))
		{
			if(request.getMethod().equals("GET"))
			{
				communityRegister(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				communityRegisterOk(request, response);
			}
			
		// 기업 커�?�니?�� �? ?��?��
		}else if(comments[comments.length-1].equals("communityModify.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				communityModify(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				communityModifyOk(request, response);
			}
			
		// 기업 커�?�니?�� �? ?��?��
		}else if(comments[comments.length-1].equals("communityDelete.do"))
		{
			communityDelete(request, response);
			
		// ?���? ?���?
		}else if(comments[comments.length-1].equals("commentRegister.do")) 
		{
			commentRegisterOk(request, response);
		
		// ?���? ?��?��
		}else if(comments[comments.length-1].equals("commentModify.do")) 
		{
			commentModifyOk(request, response);
			
		// ?���? ?��?��
		}else if(comments[comments.length-1].equals("commentDelete.do")) 
		{
			commentDeleteOk(request, response);
			
		// 좋아?�� ?���?
		}else if(comments[comments.length-1].equals("likeOk.do")) 
		{
			LikeOk(request, response);
		
		// ?���? ?���?
		}else if(comments[comments.length-1].equals("complaintOk.do")) 
		{
			complaintOk(request, response);
		}
	}
	
	// 기업 ?��?��?��
	public void Search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<CompanyVO> List = new ArrayList<>();
		
		Connection conn = null;
	    PreparedStatement ptmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = DBConn.conn();
	        
	        String sql = "SELECT company_name, company_logo, company_no "
	        		+ "FROM company "
	        		+ "WHERE company_state = 'E' "
	        		+ "LIMIT 0, 9";
	        
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

		
	        // ?��목별 ?���? 계산 (리뷰�? ?��?�� 경우?���?)
//	        double avgCareer = reviewCount > 0 ? totalCareer / reviewCount : 0;
//	        double avgBalance = reviewCount > 0 ? totalBalance / reviewCount : 0;
//	        double avgCulture = reviewCount > 0 ? totalCulture / reviewCount : 0;
//	        double avgManagement = reviewCount > 0 ? totalManagement / reviewCount : 0;
//	        double avgSalary = reviewCount > 0 ? totalSalary / reviewCount : 0;
	        
	        // ?���??�� ?��?��?�� ?��?��
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
	        response.getWriter().write("{\"error\":\"?��류�? 발생?��?��?��?��.\"}");
	    } finally {
	        try {
	            DBConn.close(rs, ptmt, conn);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		
	}
	
	// 기업 �??��(?��?�� 목록)
	public void CompanyList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection conn = null;
	    PreparedStatement ptmt = null;
	    ResultSet rs = null;

	    // ?��?��?���? ?��?��?�� searchValue 값으�? ?��?���?
	    String searchValue = request.getParameter("searchValue");
	    // System.out.println("searchValue: " + searchValue);
	    
	    try {
	        conn = DBConn.conn();
	        
	        // �??�� 값이 null?��거나 비어 ?��?�� 경우 처리
	        if (searchValue == null || searchValue.trim().isEmpty()) {
	            response.setContentType("application/json; charset=UTF-8");
	            response.getWriter().write("{\"message\":\"�??��?���? 비어?��?��?��?��.\"}");
	            return;
	        }
	        
	        String sql = "SELECT company_name, company_no FROM company WHERE company_name LIKE CONCAT('%', ?, '%') AND company_state = 'E'";
	        
	        ptmt = conn.prepareStatement(sql);
	        ptmt.setString(1, searchValue);
	        
	        rs = ptmt.executeQuery();
	        
	        JSONArray jsonArray = new JSONArray();
	        
	        // 찾�? ?��?��?�� jsonArray?�� ?���?
	        while (rs.next()) {
	            String company = rs.getString("company_name");
	            int companyNo = rs.getInt("company_no");
	            
	            JSONObject jsonObj = new JSONObject();
	            jsonObj.put("company", company);
	            jsonObj.put("cno", companyNo);
	            jsonArray.put(jsonObj);
	        }
	        
	        // System.out.println("JSON Array: " + jsonArray.toString());

	        // 결과?�� ?��?�� ?��?��
	        response.setContentType("application/json; charset=UTF-8");
	        
	        // System.out.println(jsonArray.isEmpty());
	        
	        if (jsonArray.isEmpty()) 
	        {
	        	response.getWriter().write("{\"message\":\"결과�? ?��?��?��?��.\"}");
	        }else 
	        {
	        	response.getWriter().write(jsonArray.toString());
	        }
	        

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setContentType("application/json; charset=UTF-8");
	        response.getWriter().write("{\"error\":\"?��류�? 발생?��?��?��?��.\"}");
	    } finally {
	        try {
	            DBConn.close(rs, ptmt, conn);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// ?��?��추천 ?���?
	public void recommendOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no �? �??��?���?
		}
		String cno = request.getParameter("cno");
		String crstate = request.getParameter("crstate");
		
//		System.out.println("좋아?�� ?���?");
//		System.out.println(uno);
//		System.out.println(cno);
//		System.out.println("?��?��  :" + crstate);
		
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
				
				System.out.println("좋아?�� ?��?�� �?�?" + crstate);
				
			}else{
				sql = "insert into company_like (company_no, user_no, company_like_state) values (?, ?, ?)";

				ptmt = conn.prepareStatement(sql);
				
				ptmt.setString(1, cno);
				ptmt.setString(2, uno);
				ptmt.setString(3, crstate);

				System.out.println("좋아?�� ?���?");
				
			}
			
			int result = ptmt.executeUpdate();
			
			PrintWriter writer = response.getWriter();
			if(result > 0)
			{
				// System.out.println("좋아?�� ?��?���? �?경되?��?��?��?��.");
				writer.write("OK");
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
	
	// 기업 ?���? 
	public CompanyVO GetCompanyInfo(HttpServletRequest request, String cno) throws ServletException, IOException 
	{
		String crstate = "";
		int crcnt = 0;
		String name = "";
		
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
			
			// ?��?�� ?���? 조회
			String sql = "SELECT * FROM company WHERE company_no = ? ";
			
			// System.out.println("?��?�� ?���? 조회" + sql);
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, cno);
			
			rs = ptmt.executeQuery();
			
			if (rs.next()) 
			{  
		        String companyIndustry = rs.getString("company_industry");
			
				switch(companyIndustry)
				{
					case "ci1":	name = "?��조업"; break;
					case "ci2":	name = "건설?��"; break;
					case "ci3":	name = "?���? �? ?��매업"; break;
					case "ci4":	name = "?���? �? ?��?��?��?��"; break;
					case "ci5":	name = "?��?��?��"; break;
					case "ci6":	name = "?��?��?��"; break;
					case "ci7":	name = "금융 �? 보험?��"; break;
					case "ci8":	name = "?��?��?��비스?��"; break;
					default: name = "기�?";
				}
			}
			
			
			vo = new CompanyVO();
			
			vo.setCname(rs.getString("company_name"));;
			vo.setClogo(rs.getString("company_logo"));
			vo.setClocation(rs.getString("company_location"));
			vo.setCanniversary(rs.getString("company_anniversary"));
			vo.setCindustry(name);
			vo.setCemployee(rs.getString("company_employee"));
			
			// ?��?�� 추천 ?��?�� 조회
			if (uno != null && !uno.equals("")) 
			{
				String stateSql = "select company_like_state from company_like where user_no = ? and company_no = ? ";

				// System.out.println("?��?�� 추천 ?��?�� 조회" + stateSql);
				
				ptmt = conn.prepareStatement(stateSql);
				ptmt.setString(1, uno);
				ptmt.setString(2, cno);
	
				rs = ptmt.executeQuery();
			
				if(rs.next())
				{
					crstate = rs.getString("company_like_state");
					vo.setCrstate(crstate);
				}
				
				// ?��?�� 추천 개수 조회 
//				String countSql = "SELECT count(*) AS crcnt FROM company_like WHERE company_no = ? AND company_like_state = 'Y'";
//				
//				System.out.println("?��?�� 추천 ?��?�� 조회" + countSql);
//				
//				ptmt = conn.prepareStatement(countSql);
//				ptmt.setString(1, cno);
//				rs = ptmt.executeQuery();
//				
//				if (rs.next()) {
//					crcnt = rs.getInt("crcnt");
//					vo.setCrcount(crcnt);  
//				}
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
		if(vo != null )
		{
			System.out.println(vo.toString());
		}
		return vo;
	}
	
	// 기업 ?���?
	public void companyInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// ?��?�� ?��?��루드 ?���?
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);

		request.setAttribute("vo", vo);
		
		//
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no �? �??��?���?
		}

		Connection conn = null;

		// �? 목록
		List<PostVO> List = new ArrayList<>();

		PreparedStatement ptmtPost = null;
		ResultSet rsPost = null;
		
		// 리뷰 목록
		PreparedStatement ptmtReview = null;
		ResultSet rsReview = null;
		
	    // ?��목별 총합?�� ???��?�� �??�� 초기?��
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
			
			// �? 목록
			String sqlPost = "SELECT post_hit, p.post_no, post_title, post_content, date_format(p.post_registration_date, '%Y-%m-%d') as post_registration_date, user_id, "
					+ "(select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount " 
					+ "FROM post p, user u, board b "
					+ "WHERE p.user_no = u.user_no "
					+ "AND p.board_no = b.board_no "
					+ "AND post_state = 'E' "
					+ "AND b.board_no = 2 "
			        + "ORDER BY post_no DESC "
			        + "LIMIT 0, 5 ";
			
			ptmtPost = conn.prepareStatement(sqlPost);
			
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
				
	            // 리스?��?�� 추�??���? ?��?�� 총합 계산
	            totalCareer += pvo.getPost_review_career();
	            totalBalance += pvo.getPost_review_balance();
	            totalCulture += pvo.getPost_review_culture();
	            totalManagement += pvo.getPost_review_management();
	            totalSalary += pvo.getPost_review_salary();
	            total += pvo.getPost_review_starrating();
	            reviewCount++;

	            request.setAttribute("pvo", pvo);
			}
			
	        // ?��목별 ?���? 계산 (리뷰�? ?��?�� 경우?���?)
	        double avgCareer = reviewCount > 0 ? totalCareer / reviewCount : 0;
	        double avgBalance = reviewCount > 0 ? totalBalance / reviewCount : 0;
	        double avgCulture = reviewCount > 0 ? totalCulture / reviewCount : 0;
	        double avgManagement = reviewCount > 0 ? totalManagement / reviewCount : 0;
	        double avgSalary = reviewCount > 0 ? totalSalary / reviewCount : 0;
	        double avgtotal = reviewCount > 0 ?  Math.round(((total / 5 ) / reviewCount) * 10) / 10.0 : 0;
	        
	        // ?���??�� ?��?��?�� ?��?��
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
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no �? �??��?���?
		}

		List<PostVO> pList = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
	    // ?��목별 총합?�� ???��?�� �??�� 초기?��
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
				
	            // 리스?��?�� 추�??���? ?��?�� 총합 계산
	            totalCareer += pvo.getPost_review_career();
	            totalBalance += pvo.getPost_review_balance();
	            totalCulture += pvo.getPost_review_culture();
	            totalManagement += pvo.getPost_review_management();
	            totalSalary += pvo.getPost_review_salary();
	            total += pvo.getPost_review_starrating();
	            reviewCount++;

				pList.add(pvo);
			}
			
	        // ?��목별 ?���? 계산 (리뷰�? ?��?�� 경우?���?)
	        double avgCareer = reviewCount > 0 ? totalCareer / reviewCount : 0;
	        double avgBalance = reviewCount > 0 ? totalBalance / reviewCount : 0;
	        double avgCulture = reviewCount > 0 ? totalCulture / reviewCount : 0;
	        double avgManagement = reviewCount > 0 ? totalManagement / reviewCount : 0;
	        double avgSalary = reviewCount > 0 ? totalSalary / reviewCount : 0;
	        double avgtotal = reviewCount > 0 ?  Math.round(((total / 5 ) / reviewCount) * 10) / 10.0 : 0;
	        
	        // ?���??�� ?��?��?�� ?��?��
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
	
	// 기업 리뷰 ?���?
	public void reviewRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);

		request.setAttribute("vo", vo);
		
		request.getRequestDispatcher("/WEB-INF/companyReview/reviewRegister.jsp").forward(request, response);
	}
	
	public void reviewRegisterOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	 
		String cno = request.getParameter("cno");
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no �? �??��?���?
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

		System.out.println("?��?�� 리뷰");
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
	
	// 기업 리뷰 ?��?��
	public void reviewModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		 
		String cno = request.getParameter("cno");
		String uno = request.getParameter("uno");

		CompanyVO vo = GetCompanyInfo(request, cno);
		
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
		String uno = request.getParameter("uno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);

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

			response.sendRedirect("reviewList.do");
			
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
	
	// 기업 리뷰 ?��?��
	public void reviewDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.getRequestDispatcher("/WEB-INF/companyReview/.jsp").forward(request, response);
	}
	
	// 기업 커�?�니?�� �? 목록
	public void communityList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// ?��?�� include
		String cno = request.getParameter("cno");
		CompanyVO vo = GetCompanyInfo(request, cno);

		// �? 목록
		List<PostVO> List = new ArrayList<>();
		
		// �??��?��
		String searchValue = request.getParameter("searchValue");
	    if (searchValue == null) searchValue = "";
		
	    // ?��?���?
	    int total = 0;
	    int nowPage = 1;
		if(request.getParameter("nowPage") != null) nowPage = Integer.parseInt(request.getParameter("nowPage"));

		Connection conn = null;
		
		// �? �??��
		PreparedStatement ptmtTotal = null;  
		ResultSet rsTotal = null; 
		
		// �? 목록
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			// ?��?���?
			String sqlTotal = "SELECT count(*) AS total FROM post p, board b "
					+ "WHERE p.board_no = b.board_no "
					+ "AND post_state = 'E' "
					+ "AND b.board_no = 2 ";
			
			if(searchValue.equals(""))
			{
				ptmtTotal = conn.prepareStatement(sqlTotal);
			}else
			{
				sqlTotal += "AND (post_title LIKE CONCAT('%', ?, '%') OR post_content LIKE CONCAT('%', ?, '%')) ";
				ptmtTotal = conn.prepareStatement(sqlTotal);
				ptmtTotal.setString(1, searchValue);
				ptmtTotal.setString(2, searchValue);
			}
				
			rsTotal = ptmtTotal.executeQuery();
			
			if(rsTotal.next())
			{
				total = rsTotal.getInt("total");
			}
			
			PagingUtil paging = new PagingUtil(nowPage, total, 10);
			
			// �? 목록
			String sql = "SELECT post_hit, p.post_no, post_title, post_content, date_format(p.post_registration_date, '%Y-%m-%d') as post_registration_date, user_id, "
					+ "(select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount " 
					+ "FROM post p, user u, board b "
					+ "WHERE p.user_no = u.user_no "
					+ "AND p.board_no = b.board_no "
					+ "AND post_state = 'E' "
					+ "AND b.board_no = 2 "
			        + "ORDER BY post_no DESC "
			        + "LIMIT ?, ? ";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, paging.getStart());
			ptmt.setInt(2, paging.getPerPage());
			
			rs = ptmt.executeQuery();
			
			// �??��?�� ?��?�� 경우
			if(!searchValue.equals(""))
			{
				sql = "SELECT post_hit, p.post_no, post_title, post_content, date_format(p.post_registration_date, '%Y-%m-%d') as post_registration_date, user_id, "
						+ "(select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount " 
						+ "FROM post p, user u, board b "
						+ "WHERE p.user_no = u.user_no "
						+ "AND p.board_no = b.board_no "
						+ "AND b.board_no = 2 "
						+ "AND post_state = 'E' "
						+ "AND (post_title LIKE CONCAT('%', ?, '%') OR post_content LIKE CONCAT('%', ?, '%')) "
				        + "ORDER BY post_no DESC "
				        + "LIMIT ?, ? ";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1, searchValue);
				ptmt.setString(2, searchValue);
				ptmt.setInt(3, paging.getStart());
				ptmt.setInt(4, paging.getPerPage());
				
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
	
	// 기업 커�?�니?�� �? 조회
	public void communityView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		String pno = request.getParameter("pno");

		Connection conn = null;
		PreparedStatement ptmtHit = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			// 조회?�� 증�?
			String sqlHit  = "update post set post_hit = post_hit + 1 where post_no = ?";
			ptmtHit = conn.prepareStatement(sqlHit);
			ptmtHit.setString(1, pno);
			ptmtHit.executeUpdate();
			
			// ?��?��?��?���?
			String sql = "SELECT p.*,u.* "
					+ "FROM post p, user u "
					+ "WHERE p.user_no = u.user_no "
					+ "AND post_no = ?";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, pno);
			
			rs = ptmt.executeQuery();
			
			// 찾�? ?��?��?�� request?�� ?���?
			if(rs.next())
			{
				PostVO pvo = new PostVO();
				
				pvo.setPost_hit(rs.getString("post_hit"));
				pvo.setPost_no(rs.getString("post_no"));
				pvo.setPost_title(rs.getString("post_title"));
				pvo.setPost_content(rs.getString("post_content"));
				pvo.setPost_registration_date(rs.getString("post_registration_date"));
				pvo.setUser_no(rs.getString("user_no"));
				
				request.setAttribute("pvo", pvo);
			}
			
			request.setAttribute("vo", vo);

			// ?��?��?��
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
	
	// 기업 커�?�니?�� �? ?���?
	public void communityRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		request.setAttribute("vo", vo);
		
		request.getRequestDispatcher("/WEB-INF/companyReview/communityRegister.jsp").forward(request, response);
	}

	public void communityRegisterOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");

		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
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
			
			String sql = "insert into post (user_no, post_title, post_content, board_no) values (?, ?, ?, ?)";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, uno);
			ptmt.setString(2, title);
			ptmt.setString(3, content);
			ptmt.setString(4, bno);
			
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
	
	// 기업 커�?�니?�� �? ?��?��
	public void communityModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
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
		// notice_board ?��?��블에 ?��?��?��?��?�� update 처리
		request.setCharacterEncoding("UTF-8");
		
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
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
	
	// 기업 커�?�니?�� �? ?��?��
	public void communityDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
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
	
	// ?���? 조회
	public void commentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
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
			
//			System.out.println("?���?" + List.size());
			
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
	
	// ?���? ?���?
	public void commentRegisterOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");

		String cno = request.getParameter("cno");
		
		System.out.println("cno : " + cno);
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		String pno = request.getParameter("pno");
		String uno = request.getParameter("uno");
		String content = request.getParameter("content");
		
//		System.out.println(pno);
//		System.out.println(uno);
		
		// db?�� free_board?�� ?��?��?�� 추�??���?
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
	
	// ?���? ?��?��
	public void commentModifyOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
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
	
	// ?���? ?��?��
	public void commentDeleteOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		String pno = request.getParameter("pno");
		String c_no = request.getParameter("c_no");
		
		Connection conn = null; 
		PreparedStatement ptmt = null;  
		
		try
		{
			conn = DBConn.conn();
			
			// DB?��?�� ?��?�� ?��?�� 
			// String sql = "delete from comment where cno = ?";
			
			// DB?��?�� 비활?��?��
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
	
	// 좋아?�� ?���?
	public void LikeOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no �? �??��?���?
		}
		
		String pno = request.getParameter("pno");
		String plstate = request.getParameter("plstate");
		
//		System.out.println("좋아?�� ?���?");
//		System.out.println("좋아?�� cno" + cno);
//		System.out.println(uno);
//		System.out.println(pno);
//		System.out.println("?��?��" + plstate);
		
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
				
//				System.out.println("좋아?�� ?��?�� �?�?");
				
			}else{
				sql = "insert into post_like (post_no, user_no, post_like_state) values (?, ?, ?)";

				ptmt = conn.prepareStatement(sql);
				
				ptmt.setString(1, pno);
				ptmt.setString(2, uno);
				ptmt.setString(3, plstate);

//				System.out.println("좋아?�� ?���?");
				
			}
			
			int result = ptmt.executeUpdate();
			
			if(result > 0)
			{
				// System.out.println("좋아?�� ?��?���? �?경되?��?��?��?��.");
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
	
	// 좋아?�� ?��?�� 조회
	public void LikeState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no �? �??��?���?
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
	
	// 좋아?�� 개수
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
	
	// ?���? ?���?
	public void complaintOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		
		String cno = request.getParameter("cno");
		
		CompanyVO vo = GetCompanyInfo(request, cno);
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no �? �??��?���?
		}
		
		String pno = request.getParameter("pno");
		String reason = request.getParameter("reason");
		String reason_detail = request.getParameter("reason_detail");
		
//		System.out.println("?���? ?���?");
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
//				System.out.println("?���? ?��?��");
				
				sql = "update post_complaint set post_complaint_reason = ?, post_complaint_reason_other = ? where user_no = ? and post_no = ? ";
				
				ptmt = conn.prepareStatement(sql);
				
				ptmt.setString(1, reason);
				ptmt.setString(2, reason_detail);
				ptmt.setString(3, uno);
				ptmt.setString(4, pno);

//				System.out.println("?���? ?��?�� ?���?");
			}else
			{
//				System.out.println("?���? ?���?");

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
				System.out.println("?���? ?��?��?��?��?��.");
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
