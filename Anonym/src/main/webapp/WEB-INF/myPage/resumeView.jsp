<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="teamproject.vo.*" %>
<%@ page import="java.util.*" %>
<%

List<ResumeVO> rlist = (List<ResumeVO>)request.getAttribute("rlist");

%> 
<%@ include file="../include/header.jsp" %>
<link rel="stylesheet" href="../css/k_styles.css" />
<link rel="stylesheet" href="../css/resume_styles.css" />

        <!-- 메인 컨텐츠 -->
        <main>
            <div class="main-container8">
                <h2>이력서</h2>
                <hr>
                <!-- 이력서 -->
                <section class="resumeWrite">
                	<%
                	for(ResumeVO rvo : rlist){
                	%>
                    <div>
                        <div class="resumeMenu">제목</div>
                        <div class="resume-value"><%= rvo.getResume_title() %></div>
                    </div>
                    <div>
                        <div class="resumeMenu">이름</div>
                        <div class="resume-value"><%= rvo.getResume_name() %></div>
                    </div>
                    
                    <fieldset>
                        <legend>학력</legend>
                        <div class="form-group">
                            <span class="resumeMenu">학교명</span>
                            <span class="resume-value"><%= rvo.getResume_school_name() %></span>
                        </div>
                        <div class="form-group">
                            <span class="resumeMenu">전공명</span>
                            <span class="resume-value"><%= rvo.getResume_major() %></span>
                        </div>
                        <div class="form-group">
                            <span class="resumeMenu">졸업 일자</span>
                            <span class="resume-value"><%= rvo.getResume_graduation() %></span>
                        </div>
                    </fieldset>
    
                    <fieldset>
                        <legend>경력</legend>
                        <div class="form-group">
                            <span class="resumeMenu">회사명</span>
                            <span class="resume-value"><%= rvo.getResume_company_name() %></span>
                        </div>
                        <div class="form-group">
                            <span class="resumeMenu">근무 기간</span>
                            <div class="date-container">
                                <span class="resume-value"><%= rvo.getResume_tenure_start() %></span>
                                <span class="date-separator">~</span>
                                <span class="resume-value"><%= rvo.getResume_tenure_end() %></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <span class="resumeMenu">직급</span>
                            <span class="resume-value"><%= rvo.getResume_position() %></span>
                        </div>
                    </fieldset>
    
                    <div>
                        <div class="resumeMenu">희망 지역</div>
                        <div class="resume-value"><%= rvo.getResume_area() %></div>
                    </div>
    
                    <div>
                        <div class="resumeMenu">희망 직무</div>
                        <div class="resume-value"><%= rvo.getResume_job() %></div>
                    </div>
    
                    <div>
                        <div class="resumeMenu">희망 연봉</div>
                        <div class="resume-value"><%= rvo.getResume_salary() %></div>
                    </div>
    
                    <div>
                        <div class="resumeMenu">자기 소개서</div>
                        <div class="resume-value"><%= rvo.getResume_info().replace("\n", "<br>") %></div>
                    </div>
                    
                    <div class="w-button">
                        <button onclick="location.href='resumeList.do?user_no=<%= loginUser.getUser_no()%>'">목록</button>
                        <button onclick="location.href='resumeModify.do?resume_no=<%= rvo.getResume_no() %>'">수정</button>
                    </div>
                	<%	
                	}
                	%>
    
                </section>
            </div>
        </main>
        
<%@ include file="../include/footer.jsp" %>