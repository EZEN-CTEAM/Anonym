<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="teamproject.vo.*"%>
<%@ include file="../include/header.jsp"%>
<%
	List<JobpostingVO> jpList = (List<JobpostingVO>) request.getAttribute("jpList");
%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/volunteer_management.css" />

<!-- 메인 컨텐츠 -->
<main>
	<div class="main_apply">
		<img src="<%=request.getContextPath()%>/image/인덱스 가로 배너.PNG">
	</div>
	<div class="main-container">
		<!-- 사이드 배너 -->
		<aside>
			<div class="ad_banner" width="150px" height="300px">
				<img src="<%=request.getContextPath()%>/image/인덱스 새로 배너.PNG"
					width="200px" height="400px">
			</div>
		</aside>

		<!-- 현재 진행 중인 공고 -->
		<section class="board-container">
			<div class="apply_title">
				<h3>
					<img
						src="https://img.icons8.com/?size=100&id=39372&format=png&color=000000"
						width="17px"> 지원자가 있는 공고
				</h3>
				<a href="cjobList1.do">더보기 ></a>
			</div>
			<div class="apply_list">
				<%
				for (JobpostingVO jpvo : jpList) {
					int jobPostingNo = jpvo.getJob_posting_no();
				%>
				<div class="apply_list_area">
					<a href="#" onclick="searchApplicant(<%=jobPostingNo%>)"> <input
						type="hidden" name="job_posting_no"
						value="<%=jpvo.getJob_posting_no()%>">
						<div class="company_logo">
							<img
								src="<%= request.getContextPath() %>/user/down.do?fileName=<%= jpvo.getCompany_logo() %>"
								width="164px" height="82px">
						</div>
						<div class="company_name">
							<%=jpvo.getCompany_name()%>
						</div>
						<div class="company_title">
							<%=jpvo.getJob_posting_title()%>
						</div>
					</a>
				</div>
				<%
				}
				%>
			</div>
			<script src="<%=request.getContextPath()%>/js/jquery-3.7.1.js"></script>
			<script>
			function searchApplicant(jobPostingNo) {
          		$.ajax({
          			url: "searchApplicant.do",
          			type: "get",
          			data: "job_posting_no=" + jobPostingNo,
          			dataType: "json",
          			success: function(data) {
						let html="";
						if(data == null){
							html += "지원자가 없습니다.";
						}else{
          	        		 data.forEach(applicant => {
          	        			 html += '<div class="apply_state_list">';
	          	        		 html += '<div class="apply_state_name" name="resume_name">'+applicant.resume_name+'</div>';
	          	        		 html += '<div class="apply_state_title" name="resume_title">'+applicant.job_posting_title+'</div>';
	          	        		 html += '<div class="apply_state_function">';
	          	        		 
	          	        		 /* this는 a태그 자신을 뜻함 */
	          	        		if(applicant.applicant_state == "W"){
	          	        			console.log(applicant.resume_no);
	          	        			html += "<div class='function_item_1st' name='resume'><a href='resumeView.do?resume_no=" + applicant.resume_no + "'>이력서 보기</a></div>"
	        	        	             + "<div class='function_item_2nd' name='pass'><a href='#' onclick='event.preventDefault(); changeStateE(" + applicant.applicant_no + ",this)'>합격 처리</a></div>"
	         	        	             + "<div class='function_item_3rd' name='fail'><a href='#' onclick='event.preventDefault(); changeStateD(" + applicant.applicant_no + ",this)'>불합격 처리</a></div>"
	         	        	             + "</div>";
	          	        		}else if(applicant.applicant_state == "E"){
	          	        			html += "<div class='function_item_4th'>심사 완료(합격)</div>"
	          	        			     + "</div>";
	          	        		}else {
	          	        		    html += "<div class='function_item_5th'>심사 완료(불합격)</div>"
	          	        		  		 + "</div>";
	          	        		}
	          	        		html += "</div>";
       	        			}); 
	          			$('.apply_container>div:eq(1)').html(html);
						}
       	        	},
		       	        error: function(xhr,error){
		       	        	alert(error);
		       	        }
   				});
       		}
			
			function changeStateE(applicantNo,obj) {
				$.ajax({
					url: "changeStateE.do",
          			type: "get",
          			data: "applicant_no=" + applicantNo,
          			success: function(response) {
          				$(obj).parent().parent().html("<div class='function_item_4th'>심사 완료(합격)</div>");
        			},
	       	        error: function(xhr,error){
	       	        	alert(error);
	       	        }
				});	 
			 }
			
			function changeStateD(applicantNo,obj) {
				$.ajax({
					url: "changeStateE.do",
          			type: "get",
          			data: "applicant_no=" + applicantNo,
          			success: function(response) {
          				$(obj).parent().parent().html("<div class='function_item_5th'>심사 완료(불합격)</div>");
        			},
	       	        error: function(xhr,error){
	       	        	alert(error);
	       	        }
				});	 
			 }
          </script>
		</section>

		<!-- 지원자 관리 -->
		<section class="board-container">
			<div class="apply_title">
				<h3>
					<img
						src="https://img.icons8.com/?size=100&id=37174&format=png&color=000000"
						width="17px"> 지원자 현황
				</h3>
			</div>
			<div class="apply_container">
				<div class="apply_state">
					<div class="apply_state_name">지원자명</div>
					<div class="apply_state_title">제목</div>
					<div class="apply_state_function">기능</div>
				</div>
				<div>
					<!-- <div class="apply_state_list">
						<div class="apply_state_name" name="resume_name">1</div>
						<div class="apply_state_title" name="resume_title"2></div>
						<div class="apply_state_function">
						</div>
					</div> -->
				</div>
			</div>
		</section>
	</div>
</main>

<%@ include file="../include/footer.jsp"%>
;