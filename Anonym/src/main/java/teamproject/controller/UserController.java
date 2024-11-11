package teamproject.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import teamproject.util.DBConn;
import teamproject.vo.Company;
import teamproject.vo.CompanyVO;
import teamproject.vo.UserVO;


public class UserController 
{
	public UserController(HttpServletRequest request, HttpServletResponse response, String[] comments) throws ServletException, IOException 
	{
		// 개인회원 로그인
		if(comments[comments.length-1].equals("login_p.do"))
		{
			if(request.getMethod().equals("GET"))
			{
				login_p(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				login_pOk(request, response);
			}
		// 기업회원 로그인
		}else if(comments[comments.length-1].equals("login_c.do"))
		{
			if(request.getMethod().equals("GET"))
			{
				login_c(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				login_cOk(request, response);
			}
		// 개인 회원가입	
		}else if(comments[comments.length-1].equals("join_p.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				join_p(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				join_pOk(request, response);
			}
		// 기업 회원가입
		}else if(comments[comments.length-1].equals("join_c.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				join_c(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				join_cOk(request, response);
			}
		}
		
		// 개인 아이디 중복 체크
		else if(comments[comments.length-1].equals("checkID.do")) 
		{
		
			if(request.getMethod().equals("POST"))
			{
				checkID(request, response);
			}
		}
		
		// 개인 닉네임 중복 체크
		else if(comments[comments.length-1].equals("checkNickname.do")) 
		{
		
			if(request.getMethod().equals("POST"))
			{
				checkNickname(request, response);
			}
		}

		// 기업 아이디 중복 체크
		else if(comments[comments.length-1].equals("IDCheck.do")) 
		{
		
			if(request.getMethod().equals("POST"))
			{
				IDCheck(request, response);
			}
		}
		
		// 기업 사업자등록번호 중복 체크
		else if(comments[comments.length-1].equals("BrcCheck.do"))
		{
		
			if(request.getMethod().equals("POST"))
			{
				BrcCheck(request, response);
			}
		}
		
		// 로그아웃
		else if(comments[comments.length-1].equals("logout.do")) {
			if(request.getMethod().equals("POST"))
			{
				logout(request, response);
			}
		}
		
		// 첨부파일
		else if(comments[comments.length-1].equals("down.do")) {
			if(request.getMethod().equals("GET"))
			{
				down(request, response);
			}
		}
		
	}
	
	// 개인회원 로그인
	public void login_p(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.getRequestDispatcher("/WEB-INF/user/login_p.jsp").forward(request, response);
	}
	
	public void login_pOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "SELECT "
					+ "* "
					+ "FROM user "
					+ "WHERE user_id = ? AND user_pw = ? ";
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user_id);
			psmt.setString(2, user_pw);
			
			rs = psmt.executeQuery();
			
			if(rs.next())
			{
				UserVO loginUser = new UserVO();
				
				loginUser.setUser_no(rs.getInt("user_no"));
				loginUser.setUser_id(user_id);
				loginUser.setUser_nickname(rs.getString("user_nickname"));
				loginUser.setUser_type(rs.getString("user_type"));
				loginUser.setUser_employment(rs.getString("user_employment"));
				loginUser.setUser_company(rs.getString("user_company"));
				loginUser.setUser_cno(rs.getString("cno"));
				
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);
				
				response.sendRedirect(request.getContextPath());
			}else
			{	
		 		String errorMessage = "아이디 또는 비밀번호가 잘못되었습니다.";
	            HttpSession session = request.getSession();
	            session.setAttribute("errorMessage", errorMessage);
				response.sendRedirect(request.getContextPath()+"/user/login_p.do");
			}
			
		}catch(Exception e)
		{
			HttpSession session = request.getSession();
	        session.setAttribute("errorMessage", "오류가 발생했습니다");
	        response.sendRedirect(request.getContextPath());
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, psmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	//기업회원 로그인
	public void login_c(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.getRequestDispatcher("/WEB-INF/user/login_c.jsp").forward(request, response);
	}
	
	public void login_cOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cid = request.getParameter("cid");
		String cpw = request.getParameter("cpw");
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "SELECT (select a.company_attach_physics_file_name from anonym.company_attach a where a.company_no = c.company_no and a.company_attach_sequence = 2 ) as company_logo "
					+ "		   , (select a.company_attach_physics_file_name from anonym.company_attach a where a.company_no = c.company_no and a.company_attach_sequence = 1 ) as company_brc "
					+ "		   ,c.* "
					+ "		 from anonym.company c "
					+ "		WHERE company_id = ? AND company_pw = ? ";
			
			
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, cid);
			psmt.setString(2, cpw);
			
			rs = psmt.executeQuery();
			
			if(rs.next())
			{
				CompanyVO loginUserc = new CompanyVO();
				
				 loginUserc.setCno(rs.getInt("company_no")); 
				 loginUserc.setCid(cid);
				 loginUserc.setCname(rs.getString("company_name"));
				 loginUserc.setCpw(rs.getString("company_pw"));
				 loginUserc.setClogo(rs.getString("company_logo"));
				 loginUserc.setClocation(rs.getString("company_location"));
				 loginUserc.setCemployee(rs.getString("company_employee"));
				 loginUserc.setCindustry(rs.getString("company_industry"));
				 loginUserc.setCanniversary(rs.getString("company_anniversary"));
				 loginUserc.setCbrcnum(rs.getString("company_brc_num"));
				 loginUserc.setCbrc(rs.getString("company_brc"));
				 loginUserc.setCstate(rs.getString("company_state"));
				 
				
				HttpSession session = request.getSession();
				session.setAttribute("loginUserc", loginUserc);
				
				response.sendRedirect(request.getContextPath());
			}else
			{
				// 로그인 실패 시, 메시지를 session에 저장하고 리다이렉트
	            String errorMessage = "아이디 또는 비밀번호가 잘못되었습니다.";
	            HttpSession session = request.getSession();
	            session.setAttribute("errorMessage", errorMessage);
				response.sendRedirect(request.getContextPath()+"/user/login_c.do");
			}
			
		}catch(Exception e)
		{
			HttpSession session = request.getSession();
	        session.setAttribute("errorMessage", "오류가 발생했습니다");
	        response.sendRedirect(request.getContextPath());
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, psmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 개인 회원가입
	public void join_p(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<Company> companies = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			// 회사목록
			String sql = "SELECT company_name FROM company WHERE company_state = 'E'";
			
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();
			
			// 찾은 상세 데이터 request에 담기
			while(rs.next())
			{
				companies.add(new Company(rs.getString("company_name")));
			}
			
			System.out.println(companies.size());
			request.setAttribute("companies", companies);
			
			// /WEB-INF/user/join_p.jsp 포워드
			request.getRequestDispatcher("/WEB-INF/user/join_p.jsp").forward(request, response);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, psmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void join_pOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    String user_id = request.getParameter("user_id");
	    String user_pw = request.getParameter("user_pw");
	    String user_nickname = request.getParameter("user_nickname");
	    String user_employment = request.getParameter("user_employment");
	    String user_company = request.getParameter("user_company");

		System.out.println("아이디" + user_id);
		System.out.println("비밀번호" + user_pw);
		System.out.println("닉네임" + user_pw);
		System.out.println("재직여부" + user_employment);
		System.out.println("회사명" + user_company);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try 
		{
			conn = DBConn.conn();
			
			String sql = "insert into user (user_id, user_pw, user_nickname, user_employment, user_company) values (?, ?, ?, ?, ?)";
			
			psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psmt.setString(1, user_id);
			psmt.setString(2, user_pw);
			psmt.setString(3, user_nickname);
			psmt.setString(4, user_employment);
			psmt.setString(5, user_company);
			
			int result = psmt.executeUpdate();
			
			if(result > 0)
			{
		        // 생성된 user_no 가져오기
		        rs = psmt.getGeneratedKeys();
		        int user_no = 0;
		        if (rs.next()) {
		            user_no = rs.getInt(1);
		        }

		        // user_company가 company 테이블의 company_name과 일치하는지 확인
		        String companyCheckSql = "SELECT company_no FROM company WHERE company_name = ?";
		        psmt = conn.prepareStatement(companyCheckSql);
		        psmt.setString(1, user_company);

		        ResultSet companyRs = psmt.executeQuery();
		        if (companyRs.next()) {
		            int company_no = companyRs.getInt("company_no");

		            // company_employee 테이블에 user_no와 company_no 삽입
		            String insertEmployeeSql = "INSERT INTO company_employee (user_no, company_no) VALUES (?, ?)";
		            psmt = conn.prepareStatement(insertEmployeeSql);
		            psmt.setInt(1, user_no);
		            psmt.setInt(2, company_no);
		            psmt.executeUpdate();
		            
		            String updateUserSql = "UPDATE user SET cno = ? WHERE user_no = ?";
		            psmt = conn.prepareStatement(updateUserSql);
		            psmt.setInt(1, company_no);
		            psmt.setInt(2, user_no);
		            psmt.executeUpdate();
		        }
				
				response.sendRedirect(request.getContextPath()+"/user/login_p.do");
			} else {
	            // 회원가입 실패 시 세션에 메시지 저장
	            HttpSession session = request.getSession();
	            session.setAttribute("errorMessage", "회원가입에 실패했습니다. 다시 시도해주세요.");
	            response.sendRedirect(request.getContextPath() + "/user/join_p.do");
	        }
			
			
		}catch(Exception e)
		{
			HttpSession session = request.getSession();
	        session.setAttribute("errorMessage", "오류가 발생했습니다");
	        response.sendRedirect(request.getContextPath() + "/user/join_p.do");
			e.printStackTrace();
		}finally
		{
			try 
			{
				DBConn.close(rs, psmt, conn);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	// 기업 회원가입
	public void join_c(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.getRequestDispatcher("/WEB-INF/user/join_c.jsp").forward(request, response);
	}
	
	public void join_cOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		
		
		  int size = 10*1024*1024; 
		  String uploadPath = request.getContextPath()+ "\\upload";
//		  String uploadPath = "D:\\KGW\\teamproject\\test\\src\\main\\webapp\\upload";
		  uploadPath = request.getServletContext().getRealPath("/upload");
		  
		  MultipartRequest multi = null;
		  
		try {
			multi = new MultipartRequest(request, uploadPath, size, "UTF-8");

	        // 텍스트 파라미터 처리
	        String cid = multi.getParameter("cid");
	        String cpw = multi.getParameter("cpw");
	        String clogo = multi.getParameter("clogo");
	        String cname = multi.getParameter("cname");
	        String cemployee = multi.getParameter("cemployee");
	        String cindustry = multi.getParameter("cindustry");
	        String canniversary = multi.getParameter("canniversary");
	        String cbrcnum = multi.getParameter("cbrcnum");
	        String cbrc = multi.getParameter("cbrc");
			String[] addr = multi.getParameterValues("clocation");
	        
	        String clocation = "";
	        
	        String firstItem = addr[0];
	         
	        //나머지 요소들을 result에 추가 
	        for (int i = 1; i < addr.length; i++) { clocation += addr[i] + " "; } // 공백 추가
	        clocation += "(" + firstItem + ")";
	        
	        clogo = null;
	        File logoFile = multi.getFile("clogo");  // "clogo"는 form에서 파일 input의 name 속성
	        if (logoFile != null) {
	            clogo = logoFile.getName();  // 파일 이름을 DB에 저장
	        }
	        
	        cbrc = null;
	        File cbrcFile = multi.getFile("cbrc");  // "clogo"는 form에서 파일 input의 name 속성
	        if (cbrcFile != null) {
	        	cbrc = cbrcFile.getName();  // 파일 이름을 DB에 저장
	        }
	        
	        Connection conn = null;
	        PreparedStatement psmt = null;
	        ResultSet rs = null;
	        
	        
	        try 
			{
				conn = DBConn.conn();
				
				String sql = "insert into "
						+ "company "
						+ "(company_id, company_pw, company_logo, company_name, company_location, company_employee, company_industry, company_anniversary, company_brc_num, company_brc) "
						+ "values "
						+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
				
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, cid);
				psmt.setString(2, cpw);
				psmt.setString(3, clogo);
				psmt.setString(4, cname);
				psmt.setString(5, clocation);
				psmt.setString(6, cemployee);
				psmt.setString(7, cindustry);
				psmt.setString(8, canniversary);
				psmt.setString(9, cbrcnum);
				psmt.setString(10, cbrc);
				
				int result = psmt.executeUpdate();
				
				// select last_insert_id() as id;
				// 방금 등록된 회사 정보가 새로 받은 company_no를 받아옴
				
				// 첨부파일 등록하는 sql을 작성
				// 첨부파일 정보에 참조 해야 하는 company_no는 바로 위에서 받아옴
				//sql실행
				if (result > 0) {	
					String sqlPK = "SELECT last_insert_id() AS id"; // 방금 삽입된 회사 번호 가져오기
		            psmt = conn.prepareStatement(sqlPK);
		            rs = psmt.executeQuery();
		            
		            int companyNo = 0;
		            if (rs.next()) {
		                companyNo = rs.getInt("id"); // 방금 삽입된 회사 번호
		            }
		            
		            // 멀티파트를 이용해 파일을 받아오고 파일의 원래 이름과 파일의 새로운 이름을 가져 옴    
		            Enumeration<String> fileNames = multi.getFileNames(); // 폼에서 파일 이름을 가져옴
		            
		            while (fileNames.hasMoreElements()) {
		                String fileField = fileNames.nextElement(); // 파일 input name
		                File file = multi.getFile(fileField); // 실제 파일
	
		                if (file != null) {
		                    // 파일 정보 가져오기
		                    String fileName = file.getName(); // 원본 파일명
		                    long fileSize = file.length(); // 파일 크기
		                    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1); // 파일 확장자
	
		                    String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
		                    
		                    File newFile = new File(uploadPath + "/" + newFileName);
		                    boolean fileRenamed = file.renameTo(newFile);
		                    
		                    // 해당 회사의 기존 첨부파일 개수를 조회
		                    String countSql = "SELECT COUNT(*) AS file_count FROM company_attach WHERE company_no = ?";
		                    psmt = conn.prepareStatement(countSql);
		                    psmt.setInt(1, companyNo); // 회사 번호
		                    rs = psmt.executeQuery();
		                    int fileCount = 0;
		                    if (rs.next()) {
		                        fileCount = rs.getInt("file_count");
		                    }
	
		                    // 새로운 첨부파일에 대해 company_attach_sequence를 기존 파일 개수 + 1로 설정
		                    int sequence = fileCount + 1;
	
		                    if (fileRenamed) {
		                    // 첨부파일 DB에 삽입
		                    String sqlFile = "INSERT INTO company_attach "
		                            + " 				  (company_attach_sequence,"
		                            + "					   company_attach_physics_file_name, "
		                            + " 				   company_attach_logic_file_name,"
		                            + " 				   company_attach_file_size, "
		                            + " 				   company_attach_extension,"
		                            + "   				   company_attach_registration_date,"
		                            + " 				   company_no) "
		                            + "			VALUES (?, ?, ?, ?, ?, NOW(), ?)";
	
		                    psmt = conn.prepareStatement(sqlFile);
		                    psmt.setInt(1, sequence); // 순차적으로 증가하는 시퀀스 번호
		                    psmt.setString(2, newFileName); // 물리 파일명
		                    psmt.setString(3, fileName); // 논리 파일명
		                    psmt.setLong(4, fileSize); // 파일 크기
		                    psmt.setString(5, fileExtension); // 파일 확장자
		                    psmt.setInt(6, companyNo); // 회사 번호
	
		                    psmt.executeUpdate(); // 첨부파일 DB에 삽입
		                    
		                    }
		                }
		            }
		            response.sendRedirect(request.getContextPath() + "/user/login_c.do");
				} else {
	                // 회원가입 실패 시 세션에 오류 메시지 저장
	                HttpSession session = request.getSession();
	                session.setAttribute("errorMessage", "회원가입에 실패했습니다. 다시 시도해주세요.");
	                response.sendRedirect(request.getContextPath() + "/user/join_c.do");
	            }
	            
			}catch(Exception e)
			{
				HttpSession session = request.getSession();
		        session.setAttribute("errorMessage", "오류가 발생했습니다");
		        response.sendRedirect(request.getContextPath() + "/user/join_c.do");
				e.printStackTrace();
			}finally
			{
				try 
				{
					DBConn.close(rs, psmt, conn);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			
			
		}  catch(Exception e) {
			e.printStackTrace();
		}
	 	
		
	
	}

	//개인 아이디 중복 체크
	public void checkID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String user_id = request.getParameter("user_id");
		
		Connection conn = null; //DB 연결
		PreparedStatement psmt = null; //SQL 등록 및 실행
		ResultSet rs = null; // 조회 결과를 담음
		
		try
		{
			conn = DBConn.conn();
			
			String sql = "SELECT COUNT(*) AS cnt FROM user WHERE user_id=?";
			
			psmt = conn.prepareStatement(sql); // 사용할 쿼리 등록
			psmt.setString(1,user_id); // 쿼리 변수 값 등록
			
			rs = psmt.executeQuery();
			
			request.setCharacterEncoding("utf-8");
	        response.setContentType("text/html;charset=utf-8");
	        PrintWriter pw = response.getWriter();
	        
			if(rs.next()){
				int result = rs.getInt("cnt");
				if(result > 0){
				// System.out.print("isId"); // 아이디 존재 시 응답 데이터
					pw.append("isId");
				}else{
				// System.out.print("isNotId"); // 아이디 존재 않을 시 응답 데이터
					pw.append("isNotId");
				}
			}
			pw.flush();
			
		}catch(Exception e) 
		{
			e.printStackTrace();
			System.out.print(e.getMessage());
		}finally 
		{
			try
			{
				if(rs != null) rs.close();
				if(psmt != null) psmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) 
			{
				e.printStackTrace();
				System.out.print(e.getMessage());
			}
		}
		
		
	}
	
	//개인 닉네임 중복 확인
	public void checkNickname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String user_nickname = request.getParameter("user_nickname");
		
		Connection conn = null; //DB 연결
		PreparedStatement psmt = null; //SQL 등록 및 실행
		ResultSet rs = null; // 조회 결과를 담음
		
		try
		{
			conn = DBConn.conn();
			
			String sql = "SELECT COUNT(*) AS cnt FROM user WHERE user_nickname = ?";
			
			psmt = conn.prepareStatement(sql); // 사용할 쿼리 등록
			psmt.setString(1,user_nickname); // 쿼리 변수 값 등록
			
			rs = psmt.executeQuery();
			
			request.setCharacterEncoding("utf-8");
	        response.setContentType("text/html;charset=utf-8");
	        PrintWriter pw = response.getWriter();
			
			if(rs.next()){
				int result = rs.getInt("cnt");
				if(result > 0){
				// System.out.print("isNickname"); // 아이디 존재 시 응답 데이터
					pw.append("isNickname");
				}else{
				// System.out.print("isNotNickname"); // 아이디 존재 않을 시 응답 데이터
					pw.append("isNotNickname");
				}
			}
			
			
		}catch(Exception e) 
		{
			e.printStackTrace();
			System.out.print(e.getMessage());
		}finally 
		{
			try
			{
				if(rs != null) rs.close();
				if(psmt != null) psmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) 
			{
				e.printStackTrace();
				System.out.print(e.getMessage());
			}
		}
		
		
	}
	
	
	//기업 아이디 중복 확인
	public void IDCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cid = request.getParameter("cid");
		
		Connection conn = null; //DB 연결
		PreparedStatement psmt = null; //SQL 등록 및 실행
		ResultSet rs = null; // 조회 결과를 담음
		
		try
		{
			conn = DBConn.conn();
			
			String sql = "SELECT COUNT(*) AS cnt FROM company WHERE company_id = ?";
			
			psmt = conn.prepareStatement(sql); // 사용할 쿼리 등록
			psmt.setString(1,cid); // 쿼리 변수 값 등록
			
			rs = psmt.executeQuery();
			
			request.setCharacterEncoding("utf-8");
	        response.setContentType("text/html;charset=utf-8");
	        PrintWriter pw = response.getWriter();
	        
			if(rs.next()){
				int result = rs.getInt("cnt");
				if(result > 0){
				 System.out.print("isId"); // 아이디 존재 시 응답 데이터
					pw.append("isId");
				}else{
				 System.out.print("isNotId"); // 아이디 존재 않을 시 응답 데이터
					pw.append("isNotId");
				}
			}
			pw.flush();
			
		}catch(Exception e) 
		{
			e.printStackTrace();
			System.out.print(e.getMessage());
		}finally 
		{
			try
			{
				if(rs != null) rs.close();
				if(psmt != null) psmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) 
			{
				e.printStackTrace();
				System.out.print(e.getMessage());
			}
		}
		
		
	}
	
	//사업자등록번호 중복 확인
	public void BrcCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cbrcnum = request.getParameter("cbrcnum");
		
		Connection conn = null; //DB 연결
		PreparedStatement psmt = null; //SQL 등록 및 실행
		ResultSet rs = null; // 조회 결과를 담음
		
		try
		{
			conn = DBConn.conn();
			
			String sql = "SELECT COUNT(*) AS cnt FROM company WHERE company_brc_num = ?";
			
			psmt = conn.prepareStatement(sql); // 사용할 쿼리 등록
			psmt.setString(1,cbrcnum); // 쿼리 변수 값 등록
			
			rs = psmt.executeQuery();
			
			request.setCharacterEncoding("utf-8");
	        response.setContentType("text/html;charset=utf-8");
	        PrintWriter pw = response.getWriter();
			
			if(rs.next()){
				int result = rs.getInt("cnt");
				if(result > 0){
				 System.out.print("isBrc"); // 아이디 존재 시 응답 데이터
					pw.append("isBrc");
				}else{
				 System.out.print("isNotBrc"); // 아이디 존재 않을 시 응답 데이터
					pw.append("isNotBrc");
				}
			}
			
			
		}catch(Exception e) 
		{
			e.printStackTrace();
			System.out.print(e.getMessage());
		}finally 
		{
			try
			{
				if(rs != null) rs.close();
				if(psmt != null) psmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) 
			{
				e.printStackTrace();
				System.out.print(e.getMessage());
			}
		}
		
		
	}
	
	
	// 로그아웃
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession(false);
		 if (session != null) {
	            session.invalidate();
	        }
		 response.sendRedirect(request.getContextPath());
	}
	
	// 첨부파일 
	public void down(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 String fileName = request.getParameter("fileName");
		 
		 if (fileName != null && !fileName.isEmpty()) {
	            // 파일 경로 설정
	            String uploadPath = "D:\\KGW\\teamproject\\test\\src\\main\\webapp\\upload";
	            uploadPath = request.getServletContext().getRealPath("/upload");
	            File file = new File(uploadPath + "\\" + fileName);
	            
	            if (file.exists()) {
	                // 파일의 MIME 타입을 설정
	                response.setContentType("application/octet-stream");
	                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
	                
	                // 파일을 클라이언트로 전송
	                try (FileInputStream fis = new FileInputStream(file);
	                     ServletOutputStream out = response.getOutputStream()) {

	                    byte[] buffer = new byte[4096];
	                    int length;
	                    while ((length = fis.read(buffer)) > 0) {
	                        out.write(buffer, 0, length);
	                    }
	                }
	            } 
	            
		 }
	}
	
	
	
	
	
}
