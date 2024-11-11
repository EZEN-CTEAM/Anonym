<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<% 
request.setCharacterEncoding("UTF-8");
List<PostVO> pList = (List<PostVO>)request.getAttribute("pList");
System.out.println(pList.size());

String pno = (String)request.getAttribute("pno");

double avgCareer = (double)request.getAttribute("avgCareer");
double avgBalance = (double)request.getAttribute("avgBalance");
double avgCulture = (double)request.getAttribute("avgCulture");
double avgManagement = (double)request.getAttribute("avgManagement");
double avgSalary = (double)request.getAttribute("avgSalary");
double avgtotal = (double)request.getAttribute("avgtotal");

double avg = 0.0; 

//좋아요 상태

String plstate = (String) request.getAttribute("plstate");

System.out.print("plstate : " +plstate);

if (plstate == null) {
 plstate = "D";
}

//좋아요 개수
int lpcnt = 0;
if(request.getAttribute("lpcnt") != null) lpcnt = (Integer)request.getAttribute("lpcnt");

String stars = "";
String starsTotal = "";

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

%>  
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/c_review_2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
<%@ include file="../include/companyInfo.jsp" %>
                    <div style="width: 100%;">
                    <%
                    	int total = pList.size();
					 	if(total == 0)
						{
								%>
								                <section class="review-section">
                    <div class="review-info">
                        <h1><%= vo.getCname() %> 리뷰</h1>
                        <div class="all">
                            <div class="left">
                                <div class="overall-rating">
                                    <span class="score"><strong><%= avgtotal %></strong> <%= starsTotal %></span>
                                    <!-- <span class="stars">⭐⭐⭐</span> -->
                                </div>
                                <ul class="rating-bars">
                                    <li>5 ⭐ <div class="bar"><div class="fill" style="width: 0;"></div></div></li>
                                    <li>4 ⭐ <div class="bar"><div class="fill" style="width: 0;"></div></div></li>
                                    <li>3 ⭐ <div class="bar"><div class="fill" style="width: 0%;"></div></div></li>
                                    <li>2 ⭐ <div class="bar"><div class="fill" style="width: 0%;"></div></div></li>
                                    <li>1 ⭐ <div class="bar"><div class="fill" style="width: 0%;"></div></div></li>
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
									등록된 리뷰가 없습니다.
	                            </div>
								<%
						}else{
							%>
                <section class="review-section">
                    <div class="review-info">
                        <h1><%= vo.getCname() %> 리뷰</h1>
                        <div class="all">
                            <div class="left">
                                <div class="overall-rating">
                                    <span class="score"><strong><%= avgtotal %></strong> <%= starsTotal %></span>
                                    <!-- <span class="stars">⭐⭐⭐</span> -->
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
<%
}
					 	if(isUserLoggedIn)
					 	{
							for(PostVO pvo : pList)
							{
			                	double sum = pvo.getPost_review_starrating(); 
			            		avg = sum / 5;  
									
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
			            		
			            		plstate = pvo.getPost_like_state();
								%>
		                        <div class="review-list">
		                            <div>
		                                <div class="overall-rating">
		                                    <span class="score"><%= avg %></span>
		                                    <span class="stars-rating"><%= stars %></span>
		                                </div>
<%-- 		                               <ul>
	                						<li>커리어 향상 ⭐ <%= pvo.getPost_review_career() %></li>
	                						<li>업무와 삶의 균형 ⭐ <%= pvo.getPost_review_balance() %></li>
	                						<li>급여 및 복지 ⭐ <%= pvo.getPost_review_salary() %></li>
	                						<li>사내 문화 ⭐ <%= pvo.getPost_review_culture() %></li>
	                						<li>경영진 ⭐ <%= pvo.getPost_review_management() %></li>
		                                </ul> --%>
		                            </div>
		                            <div class="review-content" style="width: 550px;">
		                            	<div>
			                            	<div style="display:flex; justify-content: space-between;">
				                                <span class="pt"><strong>“<%= pvo.getPost_title() %>”</strong></span>
				                                <%
				                                if(loginUser != null && Integer.toString(loginUser.getUser_no()).equals(pvo.getUser_no()))
				                                {
					                                %>
													<div class="post-info2 dropdown">
													    <span class="dropdown_btn">
													        <img src="https://img.icons8.com/?size=100&id=SyMGbCSBNAUh&format=png&color=000000" alt="Dropdown Button">
													    </span>
													    <div class="dropdown_menu" style="left: 13px;">
												        	<div class="dropdown_link">
													            <a href="reviewModify.do?pno=<%= pvo.getPost_no() %>&cno=<%= cno %>">수정</a>
													        </div>
													        <div class="dropdown_link">
												            	<a href="reviewDelete.do?pno=<%= pvo.getPost_no() %>&cno=<%= cno %>">삭제</a>
													        </div>
													    </div>
													</div>
													<%
				                                }
												%>
			                            	</div>
			                                <strong class="s1">
			                                    <span class="r1">
			                                        <img src="https://img.icons8.com/?size=100&id=41816&format=png&color=000000" class="check"> 현직원
			                                    </span>
			                                    <span class="r2">
			                                        · <%= pvo.getUser_id() %> - <%= pvo.getPost_registration_date() %>
			                                    </span> 
			                                </strong>
		                                </div>
		                                <div>
			                                <p class="gb"><strong>장점</strong></p>
			                                <span>
			                                   <%= pvo.getPost_content() %>
			                                </span>
			                                <p class="gb"><strong>단점</strong></p>
			                                <span>
												<%= pvo.getPost_content2() %>
			                                </span><br>
 											<%
							             /*    if(loginUser != null &&  plstate.equals("D"))
									      	{ */
							                    %>
				                                <%-- <button class="like-button" onclick="likeOk.do?pno=<%= pvo.getPost_no() %>&plstate=E&cno=<%= cno %>">
				                                	<img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=000000">
				                                	도움이 돼요(<%= pvo.getPlcnt() %>) PNO = <%= pvo.getPost_no() %>
				                           		</button> --%>
												<% 
											/* }else if(loginUser != null &&  plstate.equals("E"))
											{ */
												%>
				                                <%-- <button class="like-button" onclick="likeOk.do?pno=<%= pvo.getPost_no() %>&plstate=D&cno=<%= cno %>">
				                                	<img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=000000">
				                                	도움이 돼요(<%= pvo.getPlcnt() %>) PNO = <%= pvo.getPost_no() %>
				                           		</button> --%>
							   					<%
										/* 	} */
											%>	                                
		                                </div>
		                            </div>
		                        </div>
		                        <%
							}
					 	}else // if(!isUserLoggedIn)
					 	{
					 		int reviewIndex = 0;
					 		
							for(PostVO pvo : pList)
							{
			                	double sum = pvo.getPost_review_starrating(); 
			            		avg = sum / 5;  
									
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
			                    // 첫 번째 리뷰인지 확인
			                    String reviewClass = (reviewIndex == 0) ? "review-list" : "review-list-blur";
			                    reviewIndex++; // 인덱스를 증가시킴
			                    
						 		%>
		                        <div class="<%= reviewClass %>">
		                            <div>
		                                <div class="overall-rating">
		                                    <span class="score"><%= avg %></span>
		                                    <span class="stars-rating"><%= stars %></span>
		                                </div>
		                                <!-- <ul>
		                                    <li>커리어 향상 ⭐ 2.8</li>
		                                    <li>업무와 삶의 균형 ⭐ 2.4</li>
		                                    <li>급여 및 복지 ⭐ 2.9</li>
		                                    <li>사내 문화 ⭐ 2.0</li>
		                                    <li>경영진 ⭐ 1.8</li>
		                                </ul> -->
		                            </div>
		                            <div class="review-content" style="width: 550px;">
		                                <div>
		                                    <p class="pt"><strong>“<%= pvo.getPost_title() %>”</strong></p>
		                                    <strong class="s1">
		                                        <span class="r1">
		                                            <img src="https://img.icons8.com/?size=100&id=41816&format=png&color=000000" class="check"> 현직원
		                                        </span>
		                                        <span class="r2">
		                                            · <%= pvo.getUser_id() %> - <%= pvo.getPost_registration_date() %>
		                                        </span> 
		                                    </strong>
		                                </div>
		                                <% 
		                                if (reviewClass.equals("review-list")) 
		                                { 
		                                	%>
		                                <div>
		                                	<p class="gb"><strong>장점</strong></p>
			                                <span>
			                                   <%= pvo.getPost_content() %>
			                                </span>
			                                <p class="gb"><strong>단점</strong></p>
			                                <span>
												<%= pvo.getPost_content2() %>
			                                </span><br>
                         				    <button class="like-button" onclick="location.href='<%= request.getContextPath() %>/user/login_p.do'">
			                                	<img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=000000">
			                                	도움이 돼요(<%= pvo.getPlcnt() %>) PNO = <%= pvo.getPost_no() %>
			                           		</button>
		                                </div>
		                                <%
		                                }else if (!reviewClass.equals("review-list")) 
		                                { 
		                                	%>
			                                <div class="blur">
			                                    <p class="gb"><strong>장점</strong></p>
			                                    <span>
			                                        배울 점이 많은 엘리트 선배들, 워라벨, 복지, 좋은 분위기, 주니어들에게도<br>
			                                        양질의 경험이 쌓여서 일 배우기 좋음.<br>
			                                        경영진은 회사에 돈 벌어다주는거 하나 없으면서 매년 수억씩 가져가고, 어떻게든<br>
			                                    </span>
			                                    <p class="gb"><strong>단점</strong></p>
			                                    <span>
			                                        연구원 인건비 후려깔 궁리만 하고, 그렇게 확보한 현금은 대주주로 배당<br> 
			                                        올릴 생각 밖에 안함.상황이 이렇다 보니 경쟁사에 연봉 역전되고 사측에 진절머리<br> 
			                                        허리급 연구원들 무더기로 퇴사하고,그러면 실무자 부족으로 주니어들은 쥐꼬리만<br> 
			                                    </span>
			                               <%--      <button class="like-button">
					                                <img src="https://img.icons8.com/?size=100&id=3RR8QoUJMAri&format=png&color=000000">
					                                도움이 돼요(<%= pvo.getPlcnt() %>) 
					                           	</button> --%>
			                                </div>
			                                <div class="login-message">
			                                    <img src="https://img.icons8.com/?size=100&id=2862&format=png&color=000000">
			                                    <p>로그인을 하고 전체 리뷰를 확인하세요!</p>
			                                    <button class="login-btn" onclick="location.href = '<%= request.getContextPath() %>/user/login_p.do'">로그인</button>
			                                </div>
			                                <%
		                                }
		                                %>
		                            </div>
		                        </div>
						 		<%
							}
					 	}
                        %>
                    </div>
                </section>
                
<%@ include file="../include/aside.jsp" %>
<%@ include file="../include/footer.jsp" %>

<script>
document.addEventListener('DOMContentLoaded', function () {
    const dropdownButtons = document.querySelectorAll('.dropdown_btn');
    const dropdownMenus = document.querySelectorAll('.dropdown_menu');

    // 각각의 드롭다운에 이벤트 바인딩
    dropdownButtons.forEach((btn, index) => {
        btn.addEventListener('click', function (event) {
            event.stopPropagation(); // 이벤트 전파 방지
            closeAllDropdowns(); // 다른 드롭다운 닫기
            dropdownMenus[index].classList.toggle('show');
        });
    });

    // 외부 클릭 시 모든 드롭다운 닫기
    window.addEventListener('click', function () {
        closeAllDropdowns();
    });

    // 모든 드롭다운 닫기 함수
    function closeAllDropdowns() {
        dropdownMenus.forEach(menu => menu.classList.remove('show'));
    }
});
</script>
