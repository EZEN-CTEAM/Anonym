<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%

List<ResumeVO> rlist = (List<ResumeVO>)request.getAttribute("rlist");

String topstate = "B";
if(request.getAttribute("topstate") != null) topstate = (String)request.getAttribute("topstate");

%> 
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/resume_styles.css" />

        <!-- 메인 컨텐츠 -->
        <main>
            <div class="main-container7">
                <!-- 이력서 목록 -->
                <section class="resumeList">
                    <button class="write-button" onclick="location.href='resumeRegister.do'">이력서 작성</button>
                    
                     	<%
						boolean hasTopResume = false;
						for (ResumeVO rvo : rlist) {
						    if ("T".equals(rvo.getResume_top_state())) {
						        hasTopResume = true;
									%>
									<div class="resumeBoard">
									    <div onclick="location.href='resumeView.do?resume_no=<%= rvo.getResume_no() %>'">
									        <input type="hidden" name="resume_no" value="<%= rvo.getResume_no() %>">
									        <span>이력서 제목 : <%= rvo.getResume_title() %></span>
									        <div>학력 : <%= rvo.getResume_school_name() %> <%= rvo.getResume_major() %></div>
									        <div>경력 : <%= rvo.getResume_company_name() %> <%= rvo.getResume_position() %></div>
									        <div>희망 지역 : <%= rvo.getResume_area() %></div>
									        <div>희망 직무 : <%= rvo.getResume_job() %></div>
									        <div>희망 연봉 : <%= rvo.getResume_salary() %></div>
									    </div>
									    <button type="button" class="resumeBoardBtn" onclick="location.href='delete.do?resume_no=<%= rvo.getResume_no() %>'">삭제</button>
									</div>
									<%
						        break;  // 대표 이력서가 하나만 보이도록 반복문 종료
						    }
						}
						%>
				    
                    <p class="total-count">총 개수 : <span id="resume-count"><%= rlist.size() %></span></p>
                    <hr>
                    <%
                    int index = 1;
                    for(ResumeVO rvo : rlist){
                    %>
                    <div class="resumeBoard">
                    <div  onclick="location.href='resumeView.do?resume_no=<%= rvo.getResume_no()%>'">
                    	<input type="hidden" name="resume_no" value="<%= rvo.getResume_no() %>">
                        <span>이력서 제목 : <%= rvo.getResume_title() %></span>
                        <span>..............</span>
                        <span><%= index %></span>
                        <div>학력 : <%= rvo.getResume_school_name() %> <%= rvo.getResume_major() %></div>
                        <div>경력 : <%= rvo.getResume_company_name() %> <%= rvo.getResume_position() %></div>
                        <div>희망 지역 : <%= rvo.getResume_area() %></div>
                        <div>희망 직무 : <%= rvo.getResume_job() %></div>
                        <div>희망 연봉 : <%= rvo.getResume_salary() %></div>
                    </div>
                         <% if ("T".equals(rvo.getResume_top_state())) { %>
					        <button type="button" class="resumeBoardBtn" onclick="location.href='delete.do?resume_no=<%= rvo.getResume_no() %>'">삭제</button>
					    <% } else { %>
					        <button class="resumeBoardBtn" onclick="location.href='setTopResume.do?resume_no=<%= rvo.getResume_no() %>&topstate=T'">대표 이력서 설정</button>
					        <button type="button" class="resumeBoardBtn" onclick="location.href='delete.do?resume_no=<%= rvo.getResume_no() %>'">삭제</button>
					    <% } %>
                    </div>
                    <%
                    	index++;
                    }
                    %>

                </section>
            </div>
        </main>
        
<%@ include file="../include/footer.jsp" %>
