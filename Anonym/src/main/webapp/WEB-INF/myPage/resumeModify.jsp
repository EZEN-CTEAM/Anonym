<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="teamproject.vo.*" %>
<%@ page import="java.util.*" %>
<%

request.setCharacterEncoding("UTF-8");
List<ResumeVO> rlist = (List<ResumeVO>) request.getAttribute("rlist");

%>
<%@ include file="../include/header.jsp" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/resume_styles.css" />
    
        <!-- 메인 컨텐츠 -->
        <main>
            <div class="main-container8">
                <h2>이력서 수정</h2>
                <hr>
                <!-- 이력서 수정 -->
                <section class="resumeWrite">
                  	<%
                  	for(ResumeVO rvo : rlist){
                  	%>
                    <form action="<%= request.getContextPath() %>/myPage/resumeModify.do?resume_no=<%= rvo.getResume_no()%>" method="post">
                    	<div>
                            <label for="title">제목</label>
                            <input type="text" name="resume_title" id="resume_title" value="<%= rvo.getResume_title() %>">
                        </div>
                        
                        <div>
                            <label for="title">이름</label>
                            <input type="text" name="resume_name" id="resume_name" value="<%= rvo.getResume_name() %>">
                        </div>
                        
                        <fieldset>
                            <legend>학력</legend>
                            <div class="form-group">
                                <label for="resume_school_name">학교명</label>
                                <input type="text" id="resume_school_name" name="resume_school_name" value="<%= rvo.getResume_school_name() %>">
                            </div>
                            <div class="form-group">
                                <label for="resume_major">전공명</label>
                                <input type="text" id="resume_major" name="resume_major" value="<%= rvo.getResume_major() %>">
                            </div>
                            <div class="form-group">
                                <label for="resume_graduation">졸업 일자</label>
                                <input type="date" id="resume_graduation" name="resume_graduation" value="<%= rvo.getResume_graduation() %>">
                            </div>
                      	</fieldset>

                        <fieldset>
                            <legend>경력</legend>
                            <div class="form-group">
                                <label for="resume_company_name">회사명</label>
                                <input type="text" id="resume_company_name" name="resume_company_name" value="<%= rvo.getResume_company_name() %>">
                            </div>
                            <div class="form-group">
                                <label for="resume_tenure">근무 기간</label>
                                <div class="date-container">
                                    <input type="date" id="resume_tenure_start" name="resume_tenure_start" value="<%= rvo.getResume_tenure_start() %>">
                                    <span class="date-separator">~</span>
                                    <input type="date" id="resume_tenure_end" name="resume_tenure_end" value="<%= rvo.getResume_tenure_end() %>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="resume_position">직급</label>
                                <input type="text" id="resume_position" name="resume_position" value="<%= rvo.getResume_position() %>">
                            </div>
                        </fieldset>

                        <div>
                            <label for="resume_area">희망 지역</label>
                            <select name="resume_area" id="resume_area" >
                            	<option value="<%= rvo.getResume_area() %>" disabled selected><%= rvo.getResume_area() %></option>
                                <option value="서울">서울</option>
                                <option value="인천/경기">인천/경기</option>
                                <option value="강원">강원</option>
                                <option value="대전/충남">대전/충남</option>
                                <option value="충북">충북</option>
                                <option value="광주/전남">광주/전남</option>
                                <option value="전북">전북</option>
                                <option value="부산/경남">부산/경남</option>
                                <option value="대구/경북">대구/경북</option>
                                <option value="제주">제주</option>
                            </select>
                        </div>

                        <div>
                            <label for="resume_job">희망 직무</label>
                            <select name="resume_job" id="resume_job" >
		                    	<option value="<%= rvo.getResume_job() %>" disabled selected><%= rvo.getResume_job() %></option>
								<option value="IT/소프트웨어">IT/소프트웨어</option>
								<option value="국가/공공기관">국가/공공기관</option>
								<option value="금융업">금융업</option>
								<option value="게임">게임</option>
								<option value="교육 서비스업">교육 서비스업</option>
								<option value="법무 서비스업">법무 서비스업</option>
								<option value="정보 서비스업">정보 서비스업</option>
								<option value="통신업">통신업</option>
								<option value="컨테츠/엔터테인먼트">컨텐츠/엔터테인먼트</option>
								<option value="자동차 및 부품 판매업">자동차 및 부품 판매업</option>
								<option value="기타 제조/수리업">기타 제조/수리업</option>
	                    	</select>
                        </div>

                        <div>
                            <label for="resume_salary">희망 연봉</label>
                            <input type="text" name="resume_salary" id="resume_salary" value="<%= rvo.getResume_salary() %>">
                        </div>

                        <div>
                            <label for="resume_info">자기 소개서</label>
                            <textarea name="resume_info" id="resume_info"><%= rvo.getResume_info() %></textarea>
                        </div>

                        <div class="w-button">
                            <button type="submit">저장</button>
                            <button type="button" onclick="location.href='resumeView.do?resume_no=<%= rvo.getResume_no() %>'">취소</button>
                        </div>
                    </form>
                   	<%	
                   	}
                   	%>
                </section>
            </div>
        </main>
        
<%@ include file="../include/footer.jsp" %>