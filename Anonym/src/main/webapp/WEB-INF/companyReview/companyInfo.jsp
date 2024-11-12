<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<% 
request.setCharacterEncoding("UTF-8");

PostVO rvo = (PostVO)request.getAttribute("pvo");

double avgCareer = (double)request.getAttribute("avgCareer");
double avgBalance = (double)request.getAttribute("avgBalance");
double avgCulture = (double)request.getAttribute("avgCulture");
double avgManagement = (double)request.getAttribute("avgManagement");
double avgSalary = (double)request.getAttribute("avgSalary");
double avgtotal = (double)request.getAttribute("avgtotal");

//검색어
String searchValue = (String)request.getParameter("searchValue");
if(searchValue == null || searchValue.equals("null")) searchValue = "";

//좋아요 상태
String plstate = "D";
if(request.getAttribute("plstate") != null) plstate = (String)request.getAttribute("plstate");

//좋아요 개수
int lpcnt = 0;
if(request.getAttribute("lpcnt") != null) lpcnt = (Integer)request.getAttribute("lpcnt");


double avg = 0.0; 

if (rvo != null) {
    double sum = rvo.getPost_review_starrating();
    avg = sum / 5;
} 

String stars = "";
String starsTotal = "";

if (avg >= 5) {
    stars = "⭐⭐⭐⭐⭐";
} else if (avg >= 4) {
    stars = "⭐⭐⭐⭐☆";
} else if (avg >= 3) {
    stars = "⭐⭐⭐☆☆";
} else if (avg >= 2) {
    stars = "⭐⭐☆☆☆";
} else if (avg >= 1) {
    stars = "⭐☆☆☆☆";
} else {
    stars = "☆☆☆☆☆";
}

if (avgtotal >= 5) {
	starsTotal = "⭐⭐⭐⭐⭐";
} else if (avgtotal >= 4) {
	starsTotal = "⭐⭐⭐⭐☆";
} else if (avgtotal >= 3) {
	starsTotal = "⭐⭐⭐☆☆";
} else if (avgtotal >= 2) {
	starsTotal = "⭐⭐☆☆☆";
} else if (avgtotal >= 1) {
	starsTotal = "⭐☆☆☆☆";
} else {
	starsTotal = "☆☆☆☆☆";
}

//글목록
List<PostVO> List = (List<PostVO>)request.getAttribute("List");
%>  
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/c_info.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
<%@ include file="../include/companyInfo.jsp" %>

            	<div style="width:800px;">
	                <section class="company-section">
	                    <div class="company-card">
	                        <h1><%= vo.getCname() %> 회사소개</h1>
	                        <ul>
	                            <li>
	                                <strong>본사</strong>
	                                <%= vo.getClocation() %>
	                            </li>
	                            <li>
	                                <strong>업계</strong>
	                                <%= vo.getCindustry() %>
	                            </li>
	                            <li>
	                                <strong>직원수</strong>
	                                <%= vo.getCemployee() %>(명)
	                            </li>
	                            <li>
	                                <strong>설립</strong>
	                                <%= vo.getCanniversary() %>
	                            </li>
	                        </ul>
	                    </div>
	                </section>
	                
	                <section class="review-section">
	                    <div class="review-info">
	                    <%
	                    if (rvo != null) {
	                    %>
	                        <h1><%= vo.getCname() %> 리뷰</h1>
	                        <div class="all">
	                            <div class="left">
	                                <div class="overall-rating">
	                                    <span class="score"><strong><%= avgtotal %></strong> <%= starsTotal %></span>
	                                    <%-- <span class="stars"><%= stars %></span> --%>
	                                </div>
	                                <ul class="rating-bars">
	                                    <li>5 ⭐ <div class="bar"><div class="fill" style="width: 20%;"></div></div></li>
	                                    <li>4 ⭐ <div class="bar"><div class="fill" style="width: 10%;"></div></div></li>
	                                    <li>3 ⭐ <div class="bar"><div class="fill" style="width: 30%;"></div></div></li>
	                                    <li>2 ⭐ <div class="bar"><div class="fill" style="width: 25%;"></div></div></li>
	                                    <li>1 ⭐ <div class="bar"><div class="fill" style="width: 15%;"></div></div></li>
	                                </ul>
	                            </div>
	                            <div class="vertical-line"></div>
	                            <div class="right">
	                                <p>항목별 평점</p>
	                                <ul>
	                                    <li>
	                                        <strong><%= String.format("%.1f", avgCareer) %> ⭐</strong>
	                                        <span>커리어 향상</span>
	                                    </li>
	                                    <li>
	                                        <strong><%= String.format("%.1f", avgBalance) %> ⭐</strong>
	                                        <span>업무와 삶의 균형</span>
	                                    </li>
	                                    <li>
	                                        <strong><%= String.format("%.1f", avgCulture) %> ⭐</strong>
	                                        <span>급여 및 복지</span>
	                                    </li>
	                                    <li>
	                                        <strong><%= String.format("%.1f", avgManagement) %> ⭐</strong>
	                                        <span>사내 문화</span>
	                                    </li>
	                                    <li>
	                                        <strong><%= String.format("%.1f", avgSalary) %> ⭐</strong>
	                                        <span>경영진</span>
	                                    </li>
	                                </ul>
	                            </div>
	                        </div>
	                    </div>
                        <div class="review-list">
                            <div>
                                <div class="overall-rating">
                                    <span class="score"><%= avg %></span>
                                    <span class="stars"><%= stars %></span>
                                </div>
                               <!--  <ul>
                                    <li>커리어 향상 ⭐ 2.8</li>
                                    <li>업무와 삶의 균형 ⭐ 2.4</li>
                                    <li>급여 및 복지 ⭐ 2.9</li>
                                    <li>사내 문화 ⭐ 2.0</li>
                                    <li>경영진 ⭐ 1.8</li>
                                </ul> -->
                            </div>
                  		    <div class="review-content" style="width: 550px;">
                            <div>
                                <p class="pt"><strong>“<%= rvo.getPost_title() %>”</strong></p>
                                <strong class="s1">
                                    <span class="r1">
                                        <img src="https://img.icons8.com/?size=100&id=41816&format=png&color=000000" class="check"> 현직원
                                    </span>
                                    <span class="r2">
                                        · <%= rvo.getUser_id() %> - <%= rvo.getPost_registration_date() %>
                                    </span> 
                                </strong>
                                <p class="gb"><strong>장점</strong></p>
                                <span>
                                    <%= rvo.getPost_content() %>
                                </span>
                                <p class="gb"><strong>단점</strong></p>
                                <span>
                                    <%= rvo.getPost_content2() %>
                                </span><br>
<%--                                 <button class="like-button">
                                	<img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=000000">
                                	도움이 돼요(<%= rvo.getPlcnt() %>)
                              		</button> --%>
                            </div>
                        </div>
                    </div>
                	    <div class="ViewMore">
                            <a href="reviewList.do?cno=<%= cno %>"><bold><%= vo.getCname() %></bold> 리뷰 모두 보기 ></a>
                        </div>
                        <%
	                    }else{
	                    	%>
	                        <h1><%= vo.getCname() %> 리뷰</h1>
	                        <div class="all">
	                            <div class="left">
	                                <div class="overall-rating">
	                                    <span class="score"><strong><%= avgtotal %></strong><!-- ⭐⭐⭐ --></span>
	                                    <!-- <span class="stars">⭐⭐⭐</span> -->
	                                </div>
	                                <ul class="rating-bars">
	                                    <li>5 ⭐ <div class="bar"><div class="fill" style="width: 0;"></div></div></li>
	                                    <li>4 ⭐ <div class="bar"><div class="fill" style="width: 0;"></div></div></li>
	                                    <li>3 ⭐ <div class="bar"><div class="fill" style="width: 0;"></div></div></li>
	                                    <li>2 ⭐ <div class="bar"><div class="fill" style="width: 0;"></div></div></li>
	                                    <li>1 ⭐ <div class="bar"><div class="fill" style="width: 0;"></div></div></li>
	                                </ul>
	                            </div>
	                            <div class="vertical-line"></div>
	                            <div class="right">
	                                <p>항목별 평점</p>
	                                <ul>
	                                    <li>
	                                        <strong>0.0 ⭐</strong>
	                                        <span>커리어 향상</span>
	                                    </li>
	                                    <li>
	                                        <strong>0.0 ⭐</strong>
	                                        <span>업무와 삶의 균형</span>
	                                    </li>
	                                    <li>
	                                        <strong>0.0 ⭐</strong>
	                                        <span>급여 및 복지</span>
	                                    </li>
	                                    <li>
	                                        <strong>0.0 ⭐</strong>
	                                        <span>사내 문화</span>
	                                    </li>
	                                    <li>
	                                        <strong>0.0 ⭐</strong>
	                                        <span>경영진</span>
	                                    </li>
	                                </ul>
	                            </div>
	                        </div>
	                    </div>
                        <div class="review-list" style="padding: 0">
                  		    <div class="review-content" style="width: 550px; padding: 25px 40px;">
                            등록된 리뷰가 없습니다.
                        </div>
                    </div>
                	    <div class="ViewMore">
                            <a href="reviewList.do?cno=<%= cno %>"><bold><%= vo.getCname() %></bold> 리뷰 모두 보기 ></a>
                        </div>
                        <%
	                    }
                        %>
	                </section>
    <%
    	if(loginUser != null && cno.equals(loginUser.getUser_cno()))
    	{
    %> 	                
	                <section class="company-community-section">
	                    <div class="company-community">
                    	<form action="communityList.do" method="get">
                    		<input type="hidden" name="cno" value="<%= cno %>">
	                        <div class="search-container">
	                            <input type="text" onkeyup="enterkey();" name="searchValue" value="<%= searchValue %>" placeholder="연봉, 면접 등 키워드로 검색해보세요.">
	                        </div>
                    	</form>
	                        <div class="post-list">
                            <%
							int total = List.size();
						 	if(total == 0)
							{
								%>
								<div class="card">
									등록된 글이 없습니다.
	                            </div>
								<%
							}
							for(PostVO pvo : List)
							{
								%>
	                            <div class="post-item">
	                                <a href="communityView.do?pno=<%= pvo.getPost_no() %>&cno=<%= cno %>">
	                                <h2><%= pvo.getPost_title() %></h2>
	                                <% 
	                                String content = pvo.getPost_content();
	                                
	                             	// 이미지 안보이게
                            		while(true)
                            		{
		                            	//String imgtagStartStr = content.substring(content.indexOf("<img"));
		                            	if(content.indexOf("<img")>-1)
		                            	{
			                            	String imgtagStartStr = content.substring(content.indexOf("<img"));
			                            	String imgtagStr = imgtagStartStr.substring(0,imgtagStartStr.indexOf(">")+1);
			                            	content = content.replace(imgtagStr,"");
		                            	}else
		                            	{
		                            		break;
		                            	}
                           			}
                           			%>
                                    <div style="height: 56px;  overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                            			<%= content %>
                            		</div>
		                            <div class="meta">
		                                <span><%= pvo.getUser_id() %> · <%= pvo.getPost_registration_date() %></span>
		                                <span>조회 <%= pvo.getPost_hit() %> · 댓글 <%= pvo.getPccount() %></span>
		                            </div>
	                                </a>
	                            </div>
							    <%
		                    }
		                    %>
	                        </div>
                            <div class="ViewMore">
                                <a href="communityList.do?cno=<%= cno %>">게시글 더보기 ></a>
                            </div>
	                    </div>
	                </section>
	                	<%
    	}
	%>
                </div>
                
                
<%@ include file="../include/aside.jsp" %>
<%@ include file="../include/footer.jsp" %>
