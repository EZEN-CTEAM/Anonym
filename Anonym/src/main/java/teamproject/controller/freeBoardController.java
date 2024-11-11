package teamproject.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import teamproject.util.DBConn;
import teamproject.vo.CompanyVO;
import teamproject.vo.PagingUtil;
import teamproject.vo.PostCommentVO;
import teamproject.vo.PostVO;
import teamproject.vo.UserVO;

public class freeBoardController 
{
	public freeBoardController(HttpServletRequest request, HttpServletResponse response, String[] comments) throws ServletException, IOException 
	{
		System.out.println(comments[comments.length-1].equals("freeView.do"));
		System.out.println(comments[comments.length-1]);
		
		// 자유게시판 목록
		if(comments[comments.length-1].equals("freeList.do"))
		{
			freeList(request, response);
		// 자유게시판,댓글,좋아요(상태, 개수) 조회
		}else if(comments[comments.length-1].equals("freeView.do")) 
		{
			LikeState(request, response);
			LikeCount(request, response);
			commentList(request, response);
			freeView(request, response);
		// 자유게시판 등록
		}else if(comments[comments.length-1].equals("freeRegister.do"))
		{
			if(request.getMethod().equals("GET"))
			{
				freeRegister(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				freeRegisterOk(request, response);
			}
		// 자유게시판 수정
		}else if(comments[comments.length-1].equals("freeModify.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				freeModify(request, response);
			}
			else if(request.getMethod().equals("POST"))
			{
				freeModifyOk(request, response);
			}
		// 자유게시판 삭제
		}else if(comments[comments.length-1].equals("freeDelete.do")) 
		{
			freeDelete(request, response);
		// 댓글 등록
		}else if(comments[comments.length-1].equals("commentRegister.do")) 
		{
			commentRegisterOk(request, response);
		// 대댓글 등록
		}else if(comments[comments.length-1].equals("subcommentRegister.do")) 
		{
			subcommentRegisterOk(request, response);
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

	public List<PostVO> GetfreeList() throws ServletException, IOException 
	{
		List<PostVO> List = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			// 글 목록
			String sql = "SELECT post_hit, p.post_no, post_title, post_content, date_format(p.post_registration_date, '%Y-%m-%d') as post_registration_date, user_id, user_company, "
					+ "(select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount " 
					+ "FROM post p, user u, board b "
					+ "WHERE p.user_no = u.user_no "
					+ "AND p.board_no = b.board_no "
					+ "AND post_state = 'E' "
					+ "AND b.board_no = 1 "
			        + "ORDER BY post_hit DESC "
			        + "LIMIT 0, 8 ";
			
			ptmt = conn.prepareStatement(sql);
			
			rs = ptmt.executeQuery();
			
			while(rs.next())
			{
				PostVO vo = new PostVO();
				
				vo.setPost_hit(rs.getString("post_hit"));
				vo.setPost_no(rs.getString("post_no"));
				vo.setPost_title(rs.getString("post_title"));
				vo.setPost_content(rs.getString("post_content"));
				vo.setPost_registration_date(rs.getString("post_registration_date"));
				vo.setUser_id(rs.getString("user_id"));
				vo.setUser_company(rs.getString("user_company"));
				vo.setPccount(rs.getString("pccount"));
				
				List.add(vo);
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

		return List;
	}
	
	// 자유게시판 목록
	public void freeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
			String sqlTotal = "SELECT count(*) AS total FROM post p, board b "
					+ "WHERE p.board_no = b.board_no "
					+ "AND post_state = 'E' "
					+ "AND b.board_no = 1 ";
			
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
			
			// 글 목록
			String sql = "SELECT post_hit, p.post_no, post_title, post_content, date_format(p.post_registration_date, '%Y-%m-%d') as post_registration_date, user_id, user_company, "
					+ "(select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount " 
					+ "FROM post p, user u, board b "
					+ "WHERE p.user_no = u.user_no "
					+ "AND p.board_no = b.board_no "
					+ "AND post_state = 'E' "
					+ "AND b.board_no = 1 "
			        + "ORDER BY post_no DESC "
			        + "LIMIT ?, ? ";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, paging.getStart());
			ptmt.setInt(2, paging.getPerPage());
			
			rs = ptmt.executeQuery();
			
			// 검색어 있을 경우
			if(!searchValue.equals(""))
			{
				sql = "SELECT post_hit, p.post_no, post_title, post_content, date_format(p.post_registration_date, '%Y-%m-%d') as post_registration_date, user_id, user_company, "
						+ "(select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount " 
						+ "FROM post p, user u, board b "
						+ "WHERE p.user_no = u.user_no "
						+ "AND p.board_no = b.board_no "
						+ "AND b.board_no = 1 "
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
				PostVO vo = new PostVO();
				
				vo.setPost_hit(rs.getString("post_hit"));
				vo.setPost_no(rs.getString("post_no"));
				vo.setPost_title(rs.getString("post_title"));
				vo.setPost_content(rs.getString("post_content"));
				vo.setPost_registration_date(rs.getString("post_registration_date"));
				vo.setUser_id(rs.getString("user_id"));
				vo.setUser_company(rs.getString("user_company"));
				vo.setPccount(rs.getString("pccount"));
				
				List.add(vo);
			}
			request.setAttribute("List", List);
			request.setAttribute("paging", paging);
			
			request.getRequestDispatcher("/WEB-INF/freeBoard/freeList.jsp").forward(request, response);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, ptmt, conn);
				DBConn.close(rsTotal, ptmtTotal, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 자유게시판 조회
	public void freeView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<PostVO> List = GetfreeList();
		
		request.setAttribute("fList", List);
		
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
			String sql = "SELECT post_hit, p.post_no, post_title, post_content, date_format(p.post_registration_date, '%Y-%m-%d') as post_registration_date, user_id, u.user_no, user_company "
					+ "FROM post p, user u "
					+ "WHERE p.user_no = u.user_no "
					+ "AND post_no = ?";
			
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, pno);
			
			rs = ptmt.executeQuery();
			
			// 3. 찾은 상세 데이터 request에 담기
			if(rs.next())
			{
				PostVO vo = new PostVO();
				
				vo.setPost_hit(rs.getString("post_hit"));
				vo.setPost_no(rs.getString("post_no"));
				vo.setPost_title(rs.getString("post_title"));
				vo.setPost_content(rs.getString("post_content"));
				vo.setPost_registration_date(rs.getString("post_registration_date"));
				vo.setUser_id(rs.getString("user_id"));;
				vo.setUser_no(rs.getString("user_no"));;
				vo.setUser_company(rs.getString("user_company"));;
				
				request.setAttribute("vo", vo);
			}

			// 4. WEB-INF/notice/view.jsp 포워드
			request.getRequestDispatcher("/WEB-INF/freeBoard/freeView.jsp").forward(request, response);
			
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
	
	// 자유게시판 등록
	public void freeRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.getRequestDispatcher("/WEB-INF/freeBoard/freeRegister.jsp").forward(request, response);
	}

	public void freeRegisterOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}
		
	    String bno = request.getParameter("board_no"); 
		String title = request.getParameter("post_title");
		String content = request.getParameter("post_content");
		String pno = null;
		
		System.out.println(title);
		System.out.println(content);

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
				System.out.println(pno); 
			}
			
			response.sendRedirect("freeView.do?pno="+pno);
			
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
	
	// 자유게시판 수정
	public void freeModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
				PostVO vo = new PostVO();
				
				vo.setPost_title(rs.getString("post_title"));
				vo.setPost_content(rs.getString("post_content"));
				vo.setPost_no(rs.getString("post_no"));

				request.setAttribute("vo", vo);
			}
			
			request.getRequestDispatcher("/WEB-INF/freeBoard/freeModify.jsp").forward(request, response);
			
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
	
	public void freeModifyOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		
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
			
			response.sendRedirect("freeView.do?pno="+pno);
			
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
	
	// 자유게시판 삭제
	public void freeDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
			
			response.sendRedirect("freeList.do");
			
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
		String pno = request.getParameter("pno");
		List<PostCommentVO> List = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		int pccount = 0;
		
		try 
		{
			conn = DBConn.conn();
			
			// String sql = "SELECT pc.*, (select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount from post_comment pc inner join post p on pc.post_no = p.post_no WHERE post_comment_state = 'E' AND p.post_no = ? ";
			String sql = "SELECT post_comment_no, post_comment_content, date_format(pc.post_comment_registration_date, '%Y-%m-%d') as post_comment_registration_date, p.post_no, pc.user_no, "
					+ "(select count(*) from post_comment pc WHERE pc.post_no = p.post_no AND post_comment_state = 'E') as pccount, user_id, user_company "
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
				
				PostCommentVO vo = new PostCommentVO();
				
				vo.setPost_comment_no(rs.getString("post_comment_no"));
				vo.setPost_comment_content(rs.getString("post_comment_content"));
				vo.setPost_comment_registration_date(rs.getString("post_comment_registration_date"));
				vo.setPost_no(rs.getString("post_no"));
				vo.setUser_id(rs.getString("user_id"));
				vo.setUser_no(rs.getString("user_no"));
				vo.setUser_company(rs.getString("user_company"));
				
				pno = rs.getString("post_no");
				
				List.add(vo);
			}
			
			System.out.println("댓글" + List.size());
			
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
		
		String pno = request.getParameter("pno");
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}
		String content = request.getParameter("content");
		
		System.out.println(pno);
		System.out.println(uno);
		
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
				System.out.println(content);
			}
			
			response.sendRedirect("freeView.do?pno=" + pno);
			
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
	
	// 대댓글 등록
	public void subcommentRegisterOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		String pno = request.getParameter("pno");
		String pcpno = request.getParameter("pcpno");
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}
		String content = request.getParameter("content");
		
		System.out.println(pno);
		System.out.println(uno);
		
		// db에 free_board에 데이터 추가하기
		Connection conn = null; 
		PreparedStatement ptmt = null; 
		
		try
		{
			conn = DBConn.conn();
			
			String sql = "insert into post_comment (post_no, user_no, post_comment_content, post_comment_parent_no) values (?, ?, ?, ?)";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, pno);
			ptmt.setString(2, uno);
			ptmt.setString(3, content);
			ptmt.setString(4, pcpno);
			
			int result = ptmt.executeUpdate();
			
			if(result > 0)
			{
				System.out.println(content);
			}
			
			response.sendRedirect("freeView.do?pno=" + pno);
			
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
		request.setCharacterEncoding("UTF-8");
		String content = request.getParameter("content");
		String cno = request.getParameter("cno");
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
				System.out.println(content);
			}
			
			response.sendRedirect("freeView.do?pno=" + pno);
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
		String pno = request.getParameter("pno");
		
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
			ptmt.setString(1, cno);
			
			int result = ptmt.executeUpdate();
			
			if(result>0)
			{
				System.out.println(cno);
			}
			
			response.sendRedirect("freeView.do?pno=" + pno);
			
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
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		String uno = null;
		
		if (loginUser != null) 
		{
			uno = Integer.toString(loginUser.getUser_no());  // user_no 값 가져오기
		}
		
		String pno = request.getParameter("pno");
		String plstate = request.getParameter("plstate");
		
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
			}else{
				sql = "insert into post_like (post_no, user_no, post_like_state) values (?, ?, ?)";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1, pno);
				ptmt.setString(2, uno);
				ptmt.setString(3, plstate);
			}
			
			int result = ptmt.executeUpdate();
			
			if(result > 0)
			{
				System.out.println("좋아요 상태가 변경되었습니다.");
			}
			
			response.sendRedirect("freeView.do?pno=" + pno);
			
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
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		
	    String uno = (loginUser != null) ? Integer.toString(loginUser.getUser_no()) : null;
		
		String pno = request.getParameter("pno");
		String reason = request.getParameter("reason");
		String reason_detail = request.getParameter("reason_detail");
		
		System.out.println(uno);
		System.out.println(pno);
		System.out.println(reason);
		System.out.println(reason_detail);
		
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
				sql = "update post_complaint set post_complaint_reason = ?, post_complaint_reason_other = ? where user_no = ? and post_no = ? ";
				
				ptmt = conn.prepareStatement(sql);
				
				ptmt.setString(1, reason);
				ptmt.setString(2, reason_detail);
				ptmt.setString(3, uno);
				ptmt.setString(4, pno);

			}else{
				sql = "insert into post_complaint (post_no, user_no, post_complaint_state, post_complaint_reason, post_complaint_reason_other) values (?, ?, ?, ?, ?)";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1, pno);
				ptmt.setString(2, uno);
				ptmt.setString(3, "E");
				ptmt.setString(4, reason);
				ptmt.setString(5, reason_detail);
			}
			
			int result = ptmt.executeUpdate();
			
			if(result > 0)
			{
				System.out.println("신고 되었습니다.");
			}
			
			response.sendRedirect("freeView.do?pno=" + pno);
			
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
