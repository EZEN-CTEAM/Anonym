package teamproject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.do")
public class FrontCotroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FrontCotroller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		
		String contextPath = request.getContextPath();
		
		String comment = uri.substring(contextPath.length()+1);
		
		String[] comments = comment.split("/");
		System.out.println("comments[0] : " + comments[0]);
		
		if(comments[0].equals("user")) 
		{
			UserController user = new UserController(request, response, comments);
		}else if(comments[0].equals("companyReview")) 
		{
			companyReviewController companyReview = new companyReviewController(request, response, comments);
		}else if(comments[0].equals("companyServices")) 
		{
			companyServicesController companyServices = new companyServicesController(request, response, comments);
		}else if(comments[0].equals("freeBoard")) 
		{
			freeBoardController freeBaord = new freeBoardController(request, response, comments);
		}else if(comments[0].equals("jobPosting")) 
		{
			jobPostingController jobPosting = new jobPostingController(request, response, comments);
		}else if(comments[0].equals("myPage")) 
		{
			myPageController myPage = new myPageController(request, response, comments);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
