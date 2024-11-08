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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import teamproject.util.DBConn;
import teamproject.vo.CompanyVO;
import teamproject.vo.JobpostingVO;

public class companyServicesController 
{
	public companyServicesController(HttpServletRequest request, HttpServletResponse response, String[] comments) throws ServletException, IOException 
	{
		System.out.println(comments[comments.length-1].equals("cjobView.do"));
		System.out.println(comments[comments.length-1]);
		
		// 기업서비스 - 채용공고관리
		if(comments[comments.length-1].equals("cjobList.do"))
		{
				cjobList(request, response);
		// 채용공고관리 - 현재 진행 중 (더보기)
		}else if(comments[comments.length-1].equals("cjobListInProgress.do"))
		{
			if(request.getMethod().equals("POST"))
			{
				cjobListInProgress(request, response);
			}
		// 채용공고관리 - 마감 (더보기)	
		}else if(comments[comments.length-1].equals("cjobListClosed.do")) 
		{
			if(request.getMethod().equals("POST"))
			{
				cjobListClosed(request, response);
			}
		// 공고 작성
		}else if(comments[comments.length-1].equals("cjobRegister.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				cjobRegister(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				cjobRegisterOk(request, response);
			}
		}else if(comments[comments.length-1].equals("cjobView.do")) 
		{
				cjobView(request, response);	
		}else if(comments[comments.length-1].equals("cjobModify.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				cjobModify(request, response);
			}else if(request.getMethod().equals("POST"))
			{
				cjobModifyOk(request, response);
			}
		}else if(comments[comments.length-1].equals("cjobDelete.do")) 
		{
				cjobDeleteOk(request, response);
		}else if(comments[comments.length-1].equals("applicantManagementList.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				applicantManagementList(request, response);
			}
		}else if(comments[comments.length-1].equals("searchApplicant.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				searchApplicant(request, response);
			}
		}else if(comments[comments.length-1].equals("changeStateE.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				changeStateE(request, response);
			}
		}else if(comments[comments.length-1].equals("changeStateD.do")) 
		{
			if(request.getMethod().equals("GET"))
			{
				changeStateD(request, response);
			}
		}
	}
	
	// 기업서비스 - 채용공고관리
	public void cjobList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<JobpostingVO> jpIpList = new ArrayList<JobpostingVO>();
		List<JobpostingVO> jpCList = new ArrayList<JobpostingVO>();
		
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
//			경로 확인 필요
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
			
			Connection conn = null;
			PreparedStatement psmtIp = null;
			ResultSet rsIp = null;
			
			PreparedStatement psmtC = null;
			ResultSet rsC = null;
			
			try 
			{
				conn = DBConn.conn();
				
				String sqlIp = "SELECT c.company_no"
							 + " , company_name"
					         + " , company_logo"
					         + " , job_posting_no"
					         + " , job_posting_title"
					         + " FROM company c, job_posting j"
					         + " WHERE c.company_no = j.company_no"
					         + " AND job_posting_state = 'E'"
					         + " AND c.company_no = ?"
					         + " ORDER BY job_posting_registration_date desc"
					         + " LIMIT 0, 4";
				
				psmtIp = conn.prepareStatement(sqlIp);
				psmtIp.setInt(1, companyNo);
				rsIp = psmtIp.executeQuery();
				
				while(rsIp.next())
				{
					
					JobpostingVO jpIpvo = new JobpostingVO();
					
					jpIpvo.setCompany_no(rsIp.getInt("company_no"));
					jpIpvo.setCompany_logo(rsIp.getString("company_logo"));
					jpIpvo.setCompany_name(rsIp.getString("company_name"));
					jpIpvo.setJob_posting_no(rsIp.getInt("job_posting_no"));
					jpIpvo.setJob_posting_title(rsIp.getString("job_posting_title"));
					
					jpIpList.add(jpIpvo);				
				}
				request.setAttribute("jpIpList", jpIpList);
				
				String sqlC = "SELECT c.company_no"
							+ " , company_name"
							+ " , company_logo"
							+ " , job_posting_no"
							+ " , job_posting_title"
							+ " FROM company c, job_posting j"
							+ " WHERE c.company_no = j.company_no"
							+ " AND job_posting_state = 'D'"
							+ " AND c.company_no = ?"
							+ " ORDER BY job_posting_period desc"
							+ " LIMIT 0, 4";
				
				psmtC = conn.prepareStatement(sqlC);
				psmtC.setInt(1, companyNo);
				rsC = psmtC.executeQuery();
				
				while(rsC.next())
				{
					
					JobpostingVO jpCvo = new JobpostingVO();
					
//					jpCvo.setCompany_no(rsC.getInt("company_no"));
					jpCvo.setCompany_logo(rsC.getString("company_logo"));
					jpCvo.setCompany_name(rsC.getString("company_name"));
					jpCvo.setJob_posting_no(rsC.getInt("job_posting_no"));
					jpCvo.setJob_posting_title(rsC.getString("job_posting_title"));
					
					jpCList.add(jpCvo);				
				}
				request.setAttribute("jpCList", jpCList);
				
				request.getRequestDispatcher("/WEB-INF/companyServices/cjobList.jsp").forward(request, response);
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}finally
			{
				try 
				{
					DBConn.close(rsIp, psmtIp, conn);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void cjobListInProgress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<JobpostingVO> jpList = new ArrayList<JobpostingVO>();
		
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
//			경로 확인 필요
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try {
				conn = DBConn.conn();
				
				String sql = "SELECT c.company_no"
						   + " , company_name"
				           + " , company_logo"
				           + " , job_posting_no"
				           + " , job_posting_title"
				           + " FROM company c, job_posting j"
				           + " WHERE c.company_no = j.company_no"
				           + " AND job_posting_state = 'E'"
				           + " AND c.company_no = ?"
				           + " ORDER BY job_posting_registration_date desc"
				           + " LIMIT 0, 9";
			
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, companyNo);
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					JobpostingVO jpIpvo = new JobpostingVO();
					
//					jpIpvo.setCompany_no(rs.getInt("company_no"));
					jpIpvo.setCompany_logo(rs.getString("company_logo"));
					jpIpvo.setCompany_name(rs.getString("company_name"));
					jpIpvo.setJob_posting_no(rs.getInt("job_posting_no"));
					jpIpvo.setJob_posting_title(rs.getString("job_posting_title"));
					
					jpList.add(jpIpvo);		
				}
					request.setAttribute("jpList", jpList);	
					
					response.sendRedirect(request.getContextPath()+"/companyServices/cjobListInProgress.do");
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					DBConn.close(rs, psmt, conn);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void cjobListClosed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<JobpostingVO> jpList = new ArrayList<JobpostingVO>();
		
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
//			경로 확인 필요
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try {
				conn = DBConn.conn();
				
				String sql = "SELECT c.company_no"
						+ " , company_name"
						+ " , company_logo"
						+ " , job_posting_no"
						+ " , job_posting_title"
						+ " FROM company c, job_posting j"
						+ " WHERE c.company_no = j.company_no"
						+ " AND job_posting_state = 'D'"
						+ " AND c.company_no = ?"
						+ " ORDER BY job_posting_period desc"
						+ " LIMIT 0, 9";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, companyNo);
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					JobpostingVO jpCvo = new JobpostingVO();
					
//					jpCvo.setCompany_no(rs.getInt("company_no"));
					jpCvo.setCompany_logo(rs.getString("company_logo"));
					jpCvo.setCompany_name(rs.getString("company_name"));
					jpCvo.setJob_posting_no(rs.getInt("job_posting_no"));
					jpCvo.setJob_posting_title(rs.getString("job_posting_title"));
					
					jpList.add(jpCvo);		
				}
				request.setAttribute("jpList", jpList);
				
				response.sendRedirect(request.getContextPath()+"/companyServices/cjobListClosed.do");
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					DBConn.close(rs, psmt, conn);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// 공고 작성
	public void cjobRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.getRequestDispatcher("/WEB-INF/companyServices/cjobRegister.jsp").forward(request, response);
	}
	
	public void cjobRegisterOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
//			경로 확인 필요
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			String jobPostingTitle = request.getParameter("job_posting_title");
			String jobPostingKind = request.getParameter("job_posting_kind");
			String jobPostingContent = request.getParameter("job_posting_content");
			String jobPostingPeriod = request.getParameter("job_posting_period");
			
			try
			{
				conn = DBConn.conn();
				
				String sql = " INSERT INTO job_posting ("
						+ " job_posting_title"
						+ " , job_posting_kind"
						+ " , job_posting_content"
						+ " , job_posting_period"
						+ " , company_no"
						+ " ) VALUES ("
						+ " ?"
						+ " , ?"
						+ " , ?"
						+ " , ?"
						+ " , ?"
						+ " )";
				
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, jobPostingTitle);
				psmt.setString(2, jobPostingKind);
				psmt.setString(3, jobPostingContent);
				psmt.setString(4, jobPostingPeriod);
				psmt.setInt(5, companyNo);
				
				psmt.executeUpdate();
				
				response.sendRedirect(request.getContextPath()+"/companyServices/cjobList.do");
			} catch(Exception e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					DBConn.close(psmt, conn);
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void cjobView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<JobpostingVO> jpList = new ArrayList<JobpostingVO>();
		
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
			int jobPostingNo = Integer.parseInt(request.getParameter("job_posting_no"));
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try
			{
				conn = DBConn.conn();
				
				String sql = " SELECT company_name"
						   + " , company_location"
						   + " , company_employee"
						   + " , company_industry"
						   + " , company_anniversary"
						   + " , job_posting_no"
						   + " , job_posting_period"
						   + " , job_posting_title"
						   + " , job_posting_content"
						   + " FROM company c, job_posting j"
						   + " WHERE c.company_no = j.company_no"
						   + " AND job_posting_state = 'E'"
						   + " AND c.company_no = ?"
						   + " AND j.job_posting_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, companyNo);
				psmt.setInt(2, jobPostingNo);
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					JobpostingVO jpvo = new JobpostingVO();
					
					jpvo.setCompany_name(rs.getString("company_name"));
					jpvo.setCompany_location(rs.getString("company_location"));
					jpvo.setCompany_employee(rs.getString("company_employee"));
					jpvo.setCompany_industry(rs.getString("company_industry"));
					jpvo.setCompany_anniversary(rs.getString("company_anniversary"));
					jpvo.setJob_posting_no(rs.getInt("job_posting_no"));
					jpvo.setJob_posting_period(rs.getString("job_posting_period"));
					jpvo.setJob_posting_title(rs.getString("job_posting_title"));
					jpvo.setJob_posting_content(rs.getString("job_posting_content"));
					
					jpList.add(jpvo);		
				}
				request.setAttribute("jpList", jpList);
				
				request.getRequestDispatcher("/WEB-INF/companyServices/cjobView.jsp").forward(request, response);
			} catch(Exception e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					DBConn.close(rs, psmt, conn);
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void cjobModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		List<JobpostingVO> jpList = new ArrayList<JobpostingVO>();
		
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
//			경로 확인 필요
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
//			String jpno = request.getParameter("job_posting_no");
//			if(jpno == null || jpno.equals("")) {
//				
//			}
			int jobPostingNo = Integer.parseInt(request.getParameter("job_posting_no"));
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try
			{
				conn = DBConn.conn();
				
				String sql = " SELECT job_posting_title"
						   + " , job_posting_kind"
						   + " , job_posting_content"
						   + " , job_posting_period"
						   + " FROM job_posting"
						   + " WHERE company_no = ?"
						   + " AND job_posting_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, companyNo);
				psmt.setInt(2, jobPostingNo);
				
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					JobpostingVO jpvo = new JobpostingVO();
					
					jpvo.setJob_posting_title(rs.getString("job_posting_title"));
					jpvo.setJob_posting_kind(rs.getString("job_posting_kind"));
					jpvo.setJob_posting_content(rs.getString("job_posting_content"));
					jpvo.setJob_posting_period(rs.getString("job_posting_period"));
					
					jpList.add(jpvo);
				}
				request.setAttribute("jpList", jpList);
				
				request.getRequestDispatcher("/WEB-INF/companyServices/cjobModify.jsp").forward(request, response);
			} catch(Exception e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					DBConn.close(psmt, conn);
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void cjobModifyOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
//			경로 확인 필요
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
			int jobPostingNo = Integer.parseInt(request.getParameter("job_posting_no"));
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			String jobPostingTitle = request.getParameter("job_posting_title");
			String jobPostingKind = request.getParameter("job_posting_kind");
			String jobPostingContent = request.getParameter("job_posting_content");
			String jobPostingPeriod = request.getParameter("job_posting_period");
			
			try
			{
				conn = DBConn.conn();
				
				String sql = " UPDATE job_posting "
						   + " SET job_posting_title = ?"
						   + " , job_posting_kind = ?"
						   + " , job_posting_content = ?"
						   + " , job_posting_period = ?"
						   + " WHERE company_no = ?"
						   + " AND job_posting_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, jobPostingTitle);
				psmt.setString(2, jobPostingKind);
				psmt.setString(3, jobPostingContent);
				psmt.setString(4, jobPostingPeriod);
				psmt.setInt(5, companyNo);
				psmt.setInt(6, jobPostingNo);
				
				psmt.executeUpdate();
				
				response.sendRedirect(request.getContextPath()+"/companyServices/cjobList.do");
			} catch(Exception e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					DBConn.close(psmt, conn);
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void cjobDeleteOk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
//			경로 확인 필요
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
			int jobPostingNo = Integer.parseInt(request.getParameter("job_posting_no"));
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			try
			{
				conn = DBConn.conn();
				
			String sql = " UPDATE job_posting "
					   + " SET job_posting_state = 'D'"
					   + " WHERE company_no = ?"
					   + " AND job_posting_no = ?";
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, companyNo);
			psmt.setInt(2, jobPostingNo);
			
			psmt.executeUpdate();
			
			response.sendRedirect(request.getContextPath()+"/companyServices/cjobList.do");
			} catch(Exception e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					DBConn.close(psmt, conn);
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void applicantManagementList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<JobpostingVO> jpList = new ArrayList<JobpostingVO>();
		
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
//			경로 확인 필요
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
//			int jobPostingNo = Integer.parseInt(request.getParameter("job_posting_no"));
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			PreparedStatement psmtD = null;
			ResultSet rsD = null;
			
			try 
			{
				conn = DBConn.conn();
				
				String sql = " SELECT c.company_no"
						   + " , c.company_name"
						   + " , c.company_logo"
						   + " , j.job_posting_no"
						   + " , j.job_posting_title"
						   + " , COUNT(a.applicant_no) AS applicant_count"
						   + " FROM company c"
						   + " JOIN job_posting j ON c.company_no = j.company_no"
						   + " LEFT JOIN applicant a ON j.job_posting_no = a.job_posting_no"
						   + " WHERE j.job_posting_state = 'E'"
						   + " AND c.company_no = ?"
						   + " GROUP BY c.company_no, c.company_name, c.company_logo, j.job_posting_no, j.job_posting_title"
						   + " ORDER BY applicant_count DESC"
						   + " LIMIT 0, 4";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, companyNo);
				rs = psmt.executeQuery();
				
				while(rs.next())
				{
					
					JobpostingVO jpvo = new JobpostingVO();
					
					jpvo.setCompany_no(rs.getInt("company_no"));
					jpvo.setCompany_logo(rs.getString("company_logo"));
					jpvo.setCompany_name(rs.getString("company_name"));
					jpvo.setJob_posting_no(rs.getInt("job_posting_no"));
					jpvo.setJob_posting_title(rs.getString("job_posting_title"));
					
					jpList.add(jpvo);				
				}
				request.setAttribute("jpList", jpList);
				
				request.getRequestDispatcher("/WEB-INF/companyServices/applicantManagementList.jsp").forward(request, response);
				
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
	}
	
	public void searchApplicant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
//			경로 확인 필요
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
			int jobPostingNo = Integer.parseInt(request.getParameter("job_posting_no"));
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			try 
			{
				conn = DBConn.conn();
				
				String sql = "SELECT a.applicant_no"
						   + " , r.resume_no"
						   + " , r.resume_name"
						   + " , j.job_posting_title"
						   + " , a.applicant_state"
						   + " , a.user_no"
						   + " FROM applicant a"
						   + " JOIN resume r ON a.resume_no = r.resume_no"
						   + " JOIN job_posting j ON a.job_posting_no = j.job_posting_no"
						   + " WHERE a.job_posting_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, jobPostingNo);
				rs = psmt.executeQuery();
				
				JSONArray jsonList = new JSONArray();
				
				while(rs.next())
				{
					JSONObject json = new JSONObject();
					
					json.put("applicant_no", rs.getInt("applicant_no"));
					json.put("resume_no", rs.getInt("resume_no"));
					json.put("resume_name", rs.getString("resume_name"));
					json.put("job_posting_title", rs.getString("job_posting_title"));
					json.put("applicant_state", rs.getString("applicant_state"));
					json.put("user_no", rs.getInt("user_no"));
					
					jsonList.add(json);
				}
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
	            response.getWriter().print(jsonList.toString());
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
	}
	
	public void changeStateE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
//			경로 확인 필요
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
			int applicantNO = Integer.parseInt(request.getParameter("applicant_no"));
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			try 
			{
				conn = DBConn.conn();
				
				String sql = " UPDATE applicant"
						   + " SET applicant_state = 'E'"
						   + " WHERE applicant_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, applicantNO);
				psmt.executeUpdate();

			}catch(Exception e)
			{
				e.printStackTrace();
			}finally
			{
				try 
				{
					DBConn.close(psmt, conn);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void changeStateD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		CompanyVO loginUserc = (CompanyVO) session.getAttribute("loginUserc");
		
		if(loginUserc == null) {
//			경로 확인 필요
			response.sendRedirect(request.getContextPath()+"/user/login_c.do");
		} else {
			
			int companyNo = loginUserc.getCno();
			int applicantNO = Integer.parseInt(request.getParameter("applicant_no"));
			
			Connection conn = null;
			PreparedStatement psmt = null;
			
			try 
			{
				conn = DBConn.conn();
				
				String sql = " UPDATE applicant"
						   + " SET applicant_state = 'D'"
						   + " WHERE applicant_no = ?";
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, applicantNO);
				psmt.executeUpdate();

			}catch(Exception e)
			{
				e.printStackTrace();
			}finally
			{
				try 
				{
					DBConn.close(psmt, conn);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}
