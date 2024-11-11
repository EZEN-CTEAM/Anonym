package teamproject.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import teamproject.util.DBConn;
import teamproject.vo.ApplicantVO;
import teamproject.vo.Company;
import teamproject.vo.CompanyVO;
import teamproject.vo.ComplaintVO;
import teamproject.vo.ResumeVO;
import teamproject.vo.UserVO;

public class myPageController 
{
	public myPageController(HttpServletRequest request, HttpServletResponse response, String[] comments) throws ServletException, IOException 
	{	
		// 개인 내 정보
		if(comments[comments.length-1].equals("personView.do"))
		{
			personView(request, response);
		}// 개인 내정보 수정
		else if(comments[comments.length-1].equals("personModify.do"))
		{
			if(request.getMethod().equals("GET"))
			{
				personModify(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				personModifyOk(request, response);
			}
		}// 비밀번호 확인	
		else if(comments[comments.length-1].equals("checkPW.do")) 
		{
			checkPW(request, response);
		}// 닉네임 중복 확인
		else if(comments[comments.length-1].equals("checkNickname.do")) 
		{
				checkNickname(request, response);
		}// 이력서list
		else if(comments[comments.length-1].equals("resumeList.do"))
		{
			resumeList(request, response);
		}// 대표 이력서 설정
		else if(comments[comments.length-1].equals("setTopResume.do"))
		{
			setTopResume(request, response);
		}//이력서 작성
		else if(comments[comments.length-1].equals("resumeRegister.do"))
		{
			if(request.getMethod().equals("GET"))
			{
				resumeRegister(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				resumeRegisterOk(request, response);
			}
		}// 이력서 view
		else if(comments[comments.length-1].equals("resumeView.do"))
		{
			resumeView(request, response);
		}// 이력서 modify
		else if(comments[comments.length-1].equals("resumeModify.do"))
		{
			if(request.getMethod().equals("GET"))
			{
				resumeModify(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				resumeModifyOk(request, response);
			}
		}// 이력서 삭제
		else if(comments[comments.length-1].equals("delete.do"))
		{
			System.out.println("delete(request, response)");
			delete(request, response);
		}// 지원 현황
		else if(comments[comments.length-1].equals("applicationStatus.do"))
		{
			applicationStatus(request, response);
		}
		
		// 기업 내 정보
		else if(comments[comments.length-1].equals("companyView.do"))
		{
			companyView(request, response);
		}// 기업 내정보 수정
		else if(comments[comments.length-1].equals("companyModify.do"))
		{
			if(request.getMethod().equals("GET"))
			{
				companyModify(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				companyModifyOk(request, response);
			}
		}// 기업 비밀번호 확인	
		else if(comments[comments.length-1].equals("checkPW.do")) 
		{
			checkPW(request, response);
		}// 사업자등록번호 중복 확인
		else if(comments[comments.length-1].equals("checkNickname.do")) 
		{
				checkNickname(request, response);
		}
		
		//관리자 기업승인관리
		else if(comments[comments.length-1].equals("admin.do"))
		{
			if(request.getMethod().equals("GET"))
			{
				admin(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				adminOk(request, response);
			}
		}// 글 신고 list
		else if(comments[comments.length-1].equals("adminReport.do"))
		{
			adminReport(request, response);

		}// 글 신고 list
		else if(comments[comments.length-1].equals("adminReportOk.do"))
		{
			adminReportOk(request, response);

		}// 비활성 회원 리스트
		else if(comments[comments.length-1].equals("adminUser.do"))
		{
			adminUser(request, response);
		}
		
		
		
	}
	
	
		
	// 개인 내 정보
			public void personView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
			{
				
				HttpSession session = request.getSession();
				UserVO loginUser = (UserVO)session.getAttribute("loginUser");
				if( loginUser == null) {
					response.sendRedirect(request.getContextPath()+"/user/login_p.do");
				}
				
				int user_no = loginUser.getUser_no();
				
				
				Connection conn = null;
				PreparedStatement psmt = null;
				ResultSet rs = null;
				
				try 
				{
					conn = DBConn.conn();
					
					String sql = "SELECT * from user where user_no = ?";
					
					psmt = conn.prepareStatement(sql);
					psmt.setInt(1, user_no);
					rs = psmt.executeQuery();
					
					if(rs.next()) {
						loginUser = new UserVO();
						
						loginUser.setUser_no(user_no);
						loginUser.setUser_id(rs.getString("user_id"));
						loginUser.setUser_pw(rs.getString("user_pw"));
						loginUser.setUser_nickname(rs.getString("user_nickname"));
						loginUser.setUser_employment(rs.getString("user_employment"));
						loginUser.setUser_company(rs.getString("user_company"));
					
						request.setAttribute("loginUser", loginUser);
					}
					
					request.getRequestDispatcher("/WEB-INF/myPage/personView.jsp").forward(request, response);

				}catch(Exception e)
				{
					session = request.getSession();
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
			// 개인 내 정보 수정
		public void personModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			HttpSession session = request.getSession();
			UserVO loginUser = (UserVO)session.getAttribute("loginUser");
			if( loginUser == null ) {
				response.sendRedirect(request.getContextPath()+"/user/login_p.do");
			}
			
			int user_no = loginUser.getUser_no();
			
			List<Company> companies = new ArrayList<>();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			PreparedStatement psmtCompany = null;
			ResultSet rsCompany = null;
			
			try 
			{
				conn = DBConn.conn();
				
				String sql = "SELECT * from user where user_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, user_no);
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					loginUser = new UserVO();
					
					loginUser.setUser_no(user_no);
					loginUser.setUser_id(rs.getString("user_id"));
					loginUser.setUser_pw(rs.getString("user_pw"));
					loginUser.setUser_nickname(rs.getString("user_nickname"));
					loginUser.setUser_employment(rs.getString("user_employment"));
					loginUser.setUser_company(rs.getString("user_company"));
				
					request.setAttribute("loginUser", loginUser);
				}
				
				String sqlCompany = "SELECT company_name FROM company WHERE company_state = 'E'";
				
				psmtCompany = conn.prepareStatement(sqlCompany);
				
				rsCompany = psmtCompany.executeQuery();
				
				while(rsCompany.next())
				{
					companies.add(new Company(rsCompany.getString("company_name")));
				}
				
				request.setAttribute("companies", companies);
				
				request.getRequestDispatcher("/WEB-INF/myPage/personModify.jsp").forward(request, response);
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}finally
			{
				try 
				{
					if (rs != null) rs.close();
			        if (psmt != null) psmt.close();
			        if (rsCompany != null) rsCompany.close();
			        if (psmtCompany != null) psmtCompany.close();
			        if (conn != null) conn.close();
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			
		}		
		public void personModifyOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			request.setCharacterEncoding("UTF-8");
			
			HttpSession session = request.getSession();
			UserVO loginUser = (UserVO)session.getAttribute("loginUser");
			if( loginUser == null ) {
				response.sendRedirect(request.getContextPath()+"/user/login_p.do");
			}
			
			int user_no = loginUser.getUser_no();
			String cno = loginUser.getUser_cno();
			String user_type = loginUser.getUser_type();
			String user_id = loginUser.getUser_id();
			String user_pw = request.getParameter("user_pw");
			String user_nickname = request.getParameter("user_nickname");
			String user_employment = request.getParameter("user_employment");
			String user_company = request.getParameter("user_company");
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			try{
				
				conn = DBConn.conn();
				
				String sql = "UPDATE user SET user_pw = ?, user_nickname = ?, user_employment = ?, user_company = ? WHERE user_no = ? ";
				
				
		        
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, user_pw);
				psmt.setString(2, user_nickname);  
		        psmt.setString(3, user_employment);
		        psmt.setString(4, user_company); 
		        psmt.setInt(5, user_no); 
				
				int result = psmt.executeUpdate();
				
				if (result >0) {
					
					loginUser = new UserVO();
					
					loginUser.setUser_pw(user_pw);
					loginUser.setUser_nickname(user_nickname);
					loginUser.setUser_employment(user_employment);
					loginUser.setUser_company(user_company);
					loginUser.setUser_type(user_type);
					loginUser.setUser_no(user_no);
					loginUser.setUser_id(user_id);
					loginUser.setUser_cno(cno);
					
					
					session = request.getSession();
					session.setAttribute("loginUser", loginUser);
					
		            response.sendRedirect(request.getContextPath() + "/myPage/personView.do?user_no=" + user_no);
		        } else {
		        	session = request.getSession();
		        	session.setAttribute("errorMessage", "정보 수정에 실패했습니다. 다시 시도해 주세요.");
		        	response.sendRedirect(request.getContextPath() + "/myPage/personModify.do?user_no=" + user_no);
		        }
				
			}catch(Exception e) {
				session = request.getSession();
		        session.setAttribute("errorMessage", "오류가 발생했습니다");
		        response.sendRedirect(request.getContextPath()+ "/myPage/personView.do?user_no=" + user_no);
				e.printStackTrace();
			}finally {
				try 
				{
					DBConn.close(psmt, conn);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}		
		
		//개인 비밀번호 확인	
		public void checkPW(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			int user_no = Integer.parseInt(request.getParameter("user_no"));
			String user_pw = request.getParameter("user_pw");
			
			Connection conn = null; 
			PreparedStatement psmt = null; 
			ResultSet rs = null; 
			
			try
			{
				conn = DBConn.conn();
				
				String sql = "SELECT COUNT(*) AS cnt FROM user WHERE user_no = ?  AND user_pw = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, user_no);
				psmt.setString(2,user_pw);
				
				rs = psmt.executeQuery();
				
				request.setCharacterEncoding("utf-8");
		        response.setContentType("text/html;charset=utf-8");
		        PrintWriter pw = response.getWriter();
		        
				if(rs.next()){
					int result = rs.getInt("cnt");
					if(result > 0){
					System.out.print("isPW");
						pw.append("isPW");
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
					DBConn.close(rs, psmt, conn);
				}catch(Exception e) 
				{
					e.printStackTrace();
					System.out.print(e.getMessage());
				}
			}
		}		
				
		//개인 닉네임 중복 체크
		public void checkNickname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			String user_nickname = request.getParameter("user_nickname");
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null; 
			
			try
			{
				conn = DBConn.conn();
				
				String sql = "SELECT COUNT(*) AS cnt FROM user WHERE user_nickname = ?";
				
				psmt = conn.prepareStatement(sql); 
				psmt.setString(1,user_nickname); 
				
				rs = psmt.executeQuery();
				
				request.setCharacterEncoding("utf-8");
		        response.setContentType("text/html;charset=utf-8");
		        PrintWriter pw = response.getWriter();
				
				if(rs.next()){
					int result = rs.getInt("cnt");
					if(result > 0){
						pw.append("isNickname");
					}else{
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
					DBConn.close(rs, psmt, conn);
				}catch(Exception e) 
				{
					e.printStackTrace();
					System.out.print(e.getMessage());
				}
			}
		}
		
		//이력서list
		public void resumeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			HttpSession session = request.getSession();
			UserVO loginUser = (UserVO)session.getAttribute("loginUser");
			if( loginUser == null ) {
				response.sendRedirect(request.getContextPath()+"/user/login_p.do");
			}
			
			int user_no = loginUser.getUser_no();
			List<ResumeVO> rlist = new ArrayList<ResumeVO>();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try {
				conn = DBConn.conn();
				
				String sql = "SELECT * FROM resume WHERE user_no = ? ";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, user_no);
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					ResumeVO rvo = new ResumeVO();
					
					rvo.setUser_no(user_no);
					rvo.setResume_no(rs.getInt("resume_no"));
					rvo.setResume_top_state(rs.getString("resume_top_state"));
					rvo.setResume_title(rs.getString("resume_title"));
					rvo.setResume_name(rs.getString("resume_name"));
					rvo.setResume_school_name(rs.getString("resume_school_name"));
					rvo.setResume_major(rs.getString("resume_major"));
					rvo.setResume_graduation(rs.getString("resume_graduation"));
					rvo.setResume_company_name(rs.getString("resume_company_name"));
					rvo.setResume_tenure_start(rs.getString("resume_tenure_start"));
					rvo.setResume_tenure_end(rs.getString("resume_tenure_end"));
					rvo.setResume_position(rs.getString("resume_position"));
					rvo.setResume_area(rs.getString("resume_area"));
					rvo.setResume_job(rs.getString("resume_job"));
					rvo.setResume_salary(rs.getString("resume_salary"));
					rvo.setResume_info(rs.getString("resume_info"));
					
					rlist.add(rvo);
				}
				request.setAttribute("rlist", rlist);
				
				request.getRequestDispatcher("/WEB-INF/myPage/resumeList.jsp").forward(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try 
				{
					DBConn.close(rs, psmt, conn);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		// 대표 이력서 설정
		public void setTopResume(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
	        response.setCharacterEncoding("UTF-8");
	        int resume_no = Integer.parseInt(request.getParameter("resume_no"));
			String topstate = request.getParameter("resume_top_state");

			Connection conn = null;
			PreparedStatement psmt = null;

	        try {
	        	
				conn = DBConn.conn();
				
				// 1. 기존의 대표 이력서 상태를 "B"로 변경
				String sql = " UPDATE resume SET resume_top_state = 'B' WHERE resume_top_state = 'T' ";
				psmt = conn.prepareStatement(sql);
				psmt.executeUpdate();
				
				// 2. 새로운 대표 이력서 상태를 "T"로 변경
				String resetTopResumeSql = " UPDATE resume SET resume_top_state = 'T' WHERE resume_no = ? ";
				psmt = conn.prepareStatement(resetTopResumeSql);
		        psmt.setInt(1, resume_no);
				
		        int result = psmt.executeUpdate();

				if(result > 0)
				{
					System.out.println("�긽�깭媛� 蹂�寃쎈릺�뿀�뒿�땲�떎.");
				}
				
				response.sendRedirect("resumeList.do");
				
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
					DBConn.close(psmt, conn);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
		}
		
		//이력서 작성
		public void resumeRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			request.getRequestDispatcher("/WEB-INF/myPage/resumeRegister.jsp").forward(request, response);
		}
		public void resumeRegisterOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			request.setCharacterEncoding("UTF-8");
			
			HttpSession session = request.getSession();
			List<ResumeVO> rlist = new ArrayList<ResumeVO>();
			session.setAttribute("rlist", rlist);
			
			int user_no = Integer.parseInt(request.getParameter("user_no"));
			String resume_title = request.getParameter("resume_title");
			String resume_name = request.getParameter("resume_name");
			String resume_school_name = request.getParameter("resume_school_name");
			String resume_major = request.getParameter("resume_major");
			String resume_graduation = request.getParameter("resume_graduation");
			String resume_company_name = request.getParameter("resume_company_name");
			String resume_tenure_start = request.getParameter("resume_tenure_start");
			String resume_tenure_end = request.getParameter("resume_tenure_end");
			String resume_position = request.getParameter("resume_position");
			String resume_area = request.getParameter("resume_area");
			String resume_job = request.getParameter("resume_job");
			String resume_salary = request.getParameter("resume_salary");
			String resume_info = request.getParameter("resume_info");
			
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			try {
				conn = DBConn.conn();
				
				String sql = "INSERT INTO anonym.resume(user_no, resume_title, resume_name, resume_school_name, resume_major, resume_graduation, resume_company_name, resume_tenure_start, resume_tenure_end, resume_position, resume_area, resume_job, resume_salary, resume_info) values (?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,?, ?, ?, ?)";
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, user_no);
				psmt.setString(2, resume_title);
				psmt.setString(3, resume_name);
				psmt.setString(4, resume_school_name);
				psmt.setString(5, resume_major);
				psmt.setString(6, resume_graduation);
				psmt.setString(7, resume_company_name);
				psmt.setString(8, resume_tenure_start);
				psmt.setString(9, resume_tenure_end);
				psmt.setString(10, resume_position);
				psmt.setString(11, resume_area);
				psmt.setString(12, resume_job);
				psmt.setString(13, resume_salary);
				psmt.setString(14, resume_info);
				
				int result = psmt.executeUpdate();
				
				if (result > 0) {
		            response.sendRedirect(request.getContextPath() + "/myPage/resumeList.do?user_no=" + user_no);
		        } else {
		            response.sendRedirect(request.getContextPath()+"/myPage/resumeRegister.do");
		        }
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try 
				{
					DBConn.close(psmt, conn);
				}
				catch(Exception e) 
				{
					e.printStackTrace();
				}
			}
			
			
		}
		
		//이력서 view
		public void resumeView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			int resume_no = Integer.parseInt(request.getParameter("resume_no"));
			List<ResumeVO> rlist = new ArrayList<ResumeVO>();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try {
				conn = DBConn.conn();
				
				String sql = "SELECT * FROM resume WHERE resume_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, resume_no);
				
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					ResumeVO rvo = new ResumeVO();
					
					rvo.setResume_no(resume_no);
					rvo.setResume_top_state(rs.getString("resume_top_state"));
					rvo.setResume_title(rs.getString("resume_title"));
					rvo.setResume_name(rs.getString("resume_name"));
					rvo.setResume_school_name(rs.getString("resume_school_name"));
					rvo.setResume_major(rs.getString("resume_major"));
					rvo.setResume_graduation(rs.getString("resume_graduation"));
					rvo.setResume_company_name(rs.getString("resume_company_name"));
					rvo.setResume_tenure_start(rs.getString("resume_tenure_start"));
					rvo.setResume_tenure_end(rs.getString("resume_tenure_end"));
					rvo.setResume_position(rs.getString("resume_position"));
					rvo.setResume_area(rs.getString("resume_area"));
					rvo.setResume_job(rs.getString("resume_job"));
					rvo.setResume_salary(rs.getString("resume_salary"));
					rvo.setResume_info(rs.getString("resume_info"));
					
					rlist.add(rvo);
				}
				request.setAttribute("rlist", rlist);
				
				request.getRequestDispatcher("/WEB-INF/myPage/resumeView.jsp").forward(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try 
				{
					DBConn.close(rs, psmt, conn);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		// 이력서 수정
		public void resumeModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			int resume_no = Integer.parseInt(request.getParameter("resume_no"));
			List<ResumeVO> rlist = new ArrayList<ResumeVO>();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try {
				conn = DBConn.conn();
				
				String sql = "SELECT * FROM resume WHERE resume_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, resume_no);
				
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					ResumeVO rvo = new ResumeVO();
					
					rvo.setResume_no(resume_no);
					rvo.setResume_top_state(rs.getString("resume_top_state"));
					rvo.setResume_title(rs.getString("resume_title"));
					rvo.setResume_name(rs.getString("resume_name"));
					rvo.setResume_school_name(rs.getString("resume_school_name"));
					rvo.setResume_major(rs.getString("resume_major"));
					rvo.setResume_graduation(rs.getString("resume_graduation"));
					rvo.setResume_company_name(rs.getString("resume_company_name"));
					rvo.setResume_tenure_start(rs.getString("resume_tenure_start"));
					rvo.setResume_tenure_end(rs.getString("resume_tenure_end"));
					rvo.setResume_position(rs.getString("resume_position"));
					rvo.setResume_area(rs.getString("resume_area"));
					rvo.setResume_job(rs.getString("resume_job"));
					rvo.setResume_salary(rs.getString("resume_salary"));
					rvo.setResume_info(rs.getString("resume_info"));
					
					rlist.add(rvo);
				}
				request.setAttribute("rlist", rlist);
				
				request.getRequestDispatcher("/WEB-INF/myPage/resumeModify.jsp").forward(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try 
				{
					DBConn.close(rs, psmt, conn);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			
		}
		public void resumeModifyOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			request.setCharacterEncoding("UTF-8");
			
			int resume_no = Integer.parseInt(request.getParameter("resume_no"));
			String resume_title = request.getParameter("resume_title");
			String resume_name = request.getParameter("resume_name");
			String resume_school_name = request.getParameter("resume_school_name");
			String resume_major = request.getParameter("resume_major");
			String resume_graduation = request.getParameter("resume_graduation");
			String resume_company_name = request.getParameter("resume_company_name");
			String resume_tenure_start = request.getParameter("resume_tenure_start");
			String resume_tenure_end = request.getParameter("resume_tenure_end");
			String resume_position = request.getParameter("resume_position");
			String resume_area = request.getParameter("resume_area");
			String resume_job = request.getParameter("resume_job");
			String resume_salary = request.getParameter("resume_salary");
			String resume_info = request.getParameter("resume_info");
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			try {
				
				conn = DBConn.conn();
				
				String sql = " UPDATE resume"
						   + "    SET resume_title = ?"
						   + "       ,resume_name = ?"
						   + "       ,resume_school_name = ?"
						   + "       ,resume_major = ?"
						   + "       ,resume_graduation = ?"
						   + "       ,resume_company_name = ?"
						   + "       ,resume_tenure_start = ?"
						   + "       ,resume_tenure_end = ?"
						   + "       ,resume_position = ?"
						   + "       ,resume_area = ?"
						   + "       ,resume_job = ?"
						   + "       ,resume_salary = ?"
						   + "       ,resume_info = ?"
						   + "  WHERE resume_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, resume_title);
				psmt.setString(2, resume_name);
				psmt.setString(3, resume_school_name);
				psmt.setString(4, resume_major);
				psmt.setString(5, resume_graduation);
				psmt.setString(6, resume_company_name);
				psmt.setString(7, resume_tenure_start);
				psmt.setString(8, resume_tenure_end);
				psmt.setString(9, resume_position);
				psmt.setString(10, resume_area);
				psmt.setString(11, resume_job);
				psmt.setString(12, resume_salary);
				psmt.setString(13, resume_info);
				psmt.setInt(14, resume_no);
				
				psmt.executeUpdate();
				
				response.sendRedirect(request.getContextPath() + "/myPage/resumeView.do?resume_no=" + resume_no);
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					DBConn.close(psmt, conn);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		// 이력서 삭제
		public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			int resume_no = Integer.parseInt(request.getParameter("resume_no"));
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			try {
				
				conn = DBConn.conn();
				
				String sql = "DELETE FROM resume WHERE resume_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, resume_no);
				
				psmt.executeUpdate();
				
				response.sendRedirect(request.getContextPath() + "/myPage/resumeList.do");
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					DBConn.close(psmt, conn);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			
		}
		//지원 현황
		public void applicationStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			HttpSession session = request.getSession();
			UserVO loginUser = (UserVO)session.getAttribute("loginUser");
			if( loginUser == null ) {
				response.sendRedirect(request.getContextPath()+"/user/login_p.do");
			}
			
			int user_no = loginUser.getUser_no();
			
			List<ApplicantVO> alist = new ArrayList<ApplicantVO>();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try {
					conn = DBConn.conn();
					
					String sql = "  SELECT a.applicant_no, a.applicant_state, date_format(a.applicant_registration_date, '%Y-%m-%d') as applicant_registration_date, c.company_name, r.resume_title"
								+ "  FROM applicant a"
								+ "  INNER JOIN company c ON a.company_no = c.company_no"
								+ "  INNER JOIN resume r ON a.resume_no = r.resume_no"
								+ "  WHERE a.user_no = ?";
					
					psmt = conn.prepareStatement(sql);
					psmt.setInt(1, user_no);
					
					rs = psmt.executeQuery();
					
					while(rs.next()) {
						ApplicantVO avo = new ApplicantVO();
						
						avo.setUser_no(rs.getInt(user_no));
						avo.setApplicant_no(rs.getInt("applicant_no"));
						avo.setResume_no(rs.getInt("resume_no"));
						avo.setCompany_no(rs.getInt("company_no"));
						avo.setApplicant_state(rs.getString("applicant_state"));
						avo.setApplicant_registration_date(rs.getString("applicant_registration_date"));
						avo.setCompany_name(rs.getString("company_name"));
						avo.setResume_title(rs.getString("resume_title"));
						
						alist.add(avo);
					}
					
					request.setAttribute("alist", alist);
					
					request.getRequestDispatcher("/WEB-INF/myPage/applicationStatus.jsp").forward(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					DBConn.close(rs, psmt, conn);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	
		//기업 내 정보
		public void companyView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			HttpSession session = request.getSession();
			CompanyVO loginUserc = (CompanyVO)session.getAttribute("loginUserc");
			if( loginUserc == null ) {
				response.sendRedirect(request.getContextPath()+"/user/login_c.do");
			}
			
			int cno = loginUserc.getCno();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try 
			{
				conn = DBConn.conn();
				
				String sql = "SELECT * from company where company_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, cno);
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					loginUserc = new CompanyVO();
					
					loginUserc.setCno(cno);
					loginUserc.setCid(rs.getString("company_id"));
					loginUserc.setCpw(rs.getString("company_pw"));
					loginUserc.setClogo(rs.getString("company_logo"));
					loginUserc.setCname(rs.getString("company_name"));
					loginUserc.setClocation(rs.getString("company_location"));
					loginUserc.setCemployee(rs.getString("company_employee"));
					loginUserc.setCindustry(rs.getString("company_industry"));
					loginUserc.setCanniversary(rs.getString("company_anniversary"));
					loginUserc.setCbrcnum(rs.getString("company_brc_num"));
					loginUserc.setCbrc(rs.getString("company_brc"));
					
				
					request.setAttribute("loginUserc", loginUserc);
				}
				
				request.getRequestDispatcher("/WEB-INF/myPage/companyView.jsp").forward(request, response);

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
		// 기업 내 정보 수정
		public void companyModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			HttpSession session = request.getSession();
			CompanyVO loginUserc = (CompanyVO)session.getAttribute("loginUserc");
			if( loginUserc == null ) {
				response.sendRedirect(request.getContextPath()+"/user/login_c.do");
			}
			
			int cno = loginUserc.getCno();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try 
			{
				conn = DBConn.conn();
				
				String sql = "SELECT * from company where company_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, cno);
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					loginUserc = new CompanyVO();
					
					loginUserc.setCno(cno);
					loginUserc.setCid(rs.getString("company_id"));
					loginUserc.setCpw(rs.getString("company_pw"));
					loginUserc.setClogo(rs.getString("company_logo"));
					loginUserc.setCname(rs.getString("company_name"));
					loginUserc.setClocation(rs.getString("company_location"));
					loginUserc.setCemployee(rs.getString("company_employee"));
					loginUserc.setCindustry(rs.getString("company_industry"));
					loginUserc.setCanniversary(rs.getString("company_anniversary"));
					loginUserc.setCbrcnum(rs.getString("company_brc_num"));
					loginUserc.setCbrc(rs.getString("company_brc"));
					loginUserc.setCstate(rs.getString("company_state"));
				
					request.setAttribute("loginUserc", loginUserc);
				}
				
				request.getRequestDispatcher("/WEB-INF/myPage/companyModify.jsp").forward(request, response);
				
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
		public void companyModifyOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			request.setCharacterEncoding("UTF-8");
			
			int cno = Integer.parseInt(request.getParameter("cno"));
		    String cpw = request.getParameter("cpw");
		    String clogo = request.getParameter("clogo");
		    String cname = request.getParameter("cname");
		    String cemployee = request.getParameter("cemployee");
		    String cindustry = request.getParameter("cindustry");
		    String canniversary = request.getParameter("canniversary");
		    String cbrcnum = request.getParameter("cbrcnum");
		    String cbrc = request.getParameter("cbrc");
			String[] addr = request.getParameterValues("clocation");
			
			String clocation = "";
			
			String firstItem = addr[0];

		    // �굹癒몄� �슂�냼�뱾�쓣 result�뿉 異붽�
		    for (int i = 1; i < addr.length; i++) {
		    	clocation += addr[i] + " "; // 怨듬갚 異붽�
		    }

		    // 愿꾪샇 �븞�뿉 泥� 踰덉㎏ �슂�냼 異붽�
		    clocation += "(" + firstItem + ")";
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			try{
				
				conn = DBConn.conn();
				
				String sql = "UPDATE company "
						+ "	   SET company_pw = ?, "
						+ "		   company_logo = ?, "
						+ "        company_name = ?, "
						+ "        company_location= ?, "
						+ "        company_employee = ?, "
						+ "        company_industry = ?, "
						+ "        company_anniversary = ?, "
						+ "        company_brc_num = ?, "
						+ "        company_brc = ? "
						+ "  WHERE company_no = ? ";
				
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, cpw);
				psmt.setString(2, clogo);
				psmt.setString(3, cname);
				psmt.setString(4, clocation);
				psmt.setString(5, cemployee);
				psmt.setString(6, cindustry);
				psmt.setString(7, canniversary);
				psmt.setString(8, cbrcnum);
				psmt.setString(9, cbrc);
				psmt.setInt(10, cno);
				
				psmt.executeUpdate();
				
				response.sendRedirect(request.getContextPath()+"/myPage/companyView.do?cno="+cno);
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				
			}
			
		}
		
		//기업 비밀번호 확인			
		public void PWCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			int cno = Integer.parseInt(request.getParameter("company_no"));
			
			String cpw = request.getParameter("company_pw");
			
			Connection conn = null; 
			PreparedStatement psmt = null; 
			ResultSet rs = null; 
			
			try
			{
				conn = DBConn.conn();
				
				String sql = "SELECT COUNT(*) AS cnt FROM company WHERE company_no = ?  AND company_pw = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, cno);
				psmt.setString(2,cpw);
				
				rs = psmt.executeQuery();
				
				request.setCharacterEncoding("utf-8");
		        response.setContentType("text/html;charset=utf-8");
		        PrintWriter pw = response.getWriter();
		        
				if(rs.next()){
					int result = rs.getInt("cnt");
					if(result > 0){
					System.out.print("pwIs");
						pw.append("pwIs");
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
					DBConn.close(rs, psmt, conn);
				}catch(Exception e) 
				{
					e.printStackTrace();
					System.out.print(e.getMessage());
				}
			}
		}		
				
		//기업 사업자등록번호 중복 체크
		public void CBRCCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			String cbrcnum = request.getParameter("cbrcnum");
			
			Connection conn = null; 
			PreparedStatement psmt = null; 
			ResultSet rs = null; 
			
			try
			{
				conn = DBConn.conn();
				
				String sql = "SELECT COUNT(*) AS cnt FROM company WHERE company_brc_num = ?";
				
				psmt = conn.prepareStatement(sql); 
				psmt.setString(1,cbrcnum); 
				
				rs = psmt.executeQuery();
				
				request.setCharacterEncoding("utf-8");
		        response.setContentType("text/html;charset=utf-8");
		        PrintWriter pw = response.getWriter();
				
				if(rs.next()){
					int result = rs.getInt("cnt");
					if(result > 0){
						pw.append("isCBRCN");
					}else{
						pw.append("isNotCBRCN");
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
					DBConn.close(rs, psmt, conn);
				}catch(Exception e) 
				{
					e.printStackTrace();
					System.out.print(e.getMessage());
				}
			}
		}
		// 기업 승인 list
		public void admin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			List<CompanyVO> clist = new ArrayList<CompanyVO>();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try {
				
				conn = DBConn.conn();
				
				String sql = "SELECT * FROM company WHERE company_state = 'W' ";
				
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					CompanyVO cvo = new CompanyVO();
					
					cvo.setCno(rs.getInt("company_no"));
					cvo.setCname(rs.getString("company_name"));
					cvo.setClocation(rs.getString("company_location"));
					cvo.setCemployee(rs.getString("company_employee"));
					cvo.setCindustry(rs.getString("company_industry"));
					cvo.setCanniversary(rs.getString("company_anniversary"));
					cvo.setCbrcnum(rs.getString("company_brc_num"));
					cvo.setCbrc(rs.getString("company_brc"));
					cvo.setClogo(rs.getString("company_logo"));
					cvo.setCstate(rs.getString("company_state"));
					
					clist.add(cvo);
				}
				request.setAttribute("clist", clist);
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					DBConn.close(rs, psmt, conn);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			request.getRequestDispatcher("/WEB-INF/myPage/admin.jsp").forward(request, response);
		}
		public void adminOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			int cno = Integer.parseInt(request.getParameter("company_no"));
		    String cstate = request.getParameter("company_state");
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			
			try {
				
				conn = DBConn.conn();
				
				String sql = "UPDATE company SET company_state = ? WHERE company_no = ? ";
				
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, cstate);
				psmt.setInt(2, cno);
				
				psmt.executeUpdate();
				
				response.sendRedirect(request.getContextPath()+"/myPage/admin.do");
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					DBConn.close(psmt, conn);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		// 글 신고 list
		public void adminReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			List<ComplaintVO> cplist = new ArrayList<ComplaintVO>();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try {
				conn = DBConn.conn();
				
				String sql = "SELECT  pc.post_complaint_no, "
						+ "			  pc.post_complaint_reason, "
						+ "           date_format(pc.post_complaint_registration_date, '%Y-%m-%d') as pcrdate, "
						+ "			  p.post_no, " 
						+ "           p.post_content, "
						+ "           u.user_id, "
						+ "           u.user_state "
						+ " FROM post_complaint pc "
						+ " INNER JOIN post p ON pc.post_no = p.post_no "
						+ " INNER JOIN user u ON pc.user_no = u.user_no ";
				
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				
				while (rs.next()) {
					ComplaintVO cpvo = new ComplaintVO();
					
					cpvo.setPost_complaint_no(rs.getString("post_complaint_no"));
					cpvo.setPost_complaint_reason(rs.getString("post_complaint_reason"));
					cpvo.setPost_content(rs.getString("post_content"));
					cpvo.setPost_no(rs.getString("post_no"));
					cpvo.setUser_id(rs.getString("user_id"));
					cpvo.setUser_state(rs.getString("user_state"));
					cpvo.setPost_complaint_registration_date(rs.getString("pcrdate"));
					
		            cplist.add(cpvo);
		        }

		        request.setAttribute("cplist", cplist);
		        
		        request.getRequestDispatcher("/WEB-INF/myPage/adminReport.jsp").forward(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					DBConn.close(rs, psmt, conn);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		public void adminReportOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String user_id = request.getParameter("user_id");
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			try {
				conn = DBConn.conn();
				
				String sql = "UPDATE user SET user_state = 'D' WHERE user_id = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, user_id);
				
				
				int result = psmt.executeUpdate();
				
				if (result > 0) {
					System.out.println("비활성화");
					
		            response.sendRedirect(request.getContextPath()+"/myPage/adminReport.do");
		        }
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					DBConn.close(psmt, conn);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		// 비활성 회원 리스트
		public void adminUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			List<UserVO> ulist = new ArrayList<UserVO>();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try {
				
				conn = DBConn.conn();
				
				String sql = "SELECT * FROM user WHERE user_state = 'D' ";
				
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					UserVO uvo = new UserVO();
					
					uvo.setUser_no(rs.getInt("user_no"));
					uvo.setUser_id(rs.getString("user_id"));
					uvo.setUser_nickname(rs.getString("user_nickname"));
					uvo.setUser_registration_date(rs.getString("user_registration_date"));
					uvo.setUser_employment(rs.getString("user_employment"));
					uvo.setUser_company(rs.getString("user_company"));
					
					ulist.add(uvo);
				}
				request.setAttribute("ulist", ulist);
				
				request.getRequestDispatcher("/WEB-INF/myPage/adminUser.jsp").forward(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					DBConn.close(rs, psmt, conn);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
		
				
}