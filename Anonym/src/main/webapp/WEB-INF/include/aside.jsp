<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
                <!-- aside -->
                <aside class="sidebar">
                    <section class="ad-section">
                        <div class="ad">
                            <h2>해당 기업의 인사담당자이신가요?</h2>
                            <p>
                                채용 고민과 브랜딩 전략,<br>
                                모두 블라인드 기업서비스와 함께면 간단해집니다.<br>
                                지금 바로 시작하세요!<br>
                            </p>
                            <a href="<%= request.getContextPath() %>/companyServices/cjobList.do">블라인드 기업서비스 바로가기</a>
                        </div>
                    </section>
                    <section class="related-companies-section">
                        <h2><%= vo.getCindustry() %> 회사</h2>
                        <ul class="related-companies">
			  		        <%
			  		      	int count = 0;
					        for(CompanyVO company : cList)
							{
					        	 if (company != null && company.getCindustry().equals(vo.getCindustry())) 
					        	 {
							        %>
		                            <li>
		                                <a  href="companyInfo.do?cno=<%= company.getCno() %>">
		                                    <span class="img">
		                                    <img src="<%= request.getContextPath() %>/user/down.do?fileName=<%= company.getClogo() %>" alt="">
		                                </span>
		                                <div>
		                                	<strong><%= company.getCname() %></strong>
		                                </div>
		                                    <!-- <span class="star">★ 3.8</span> -->
		                                </a>
		                            </li>
		                            <%
		                            count++;
		                            if (count >= 5) 
		                            {
		                                break; 
		                            }
								}
	                        }
				            %>
                        </ul>
                    </section>
                </aside>
            </div>
        </main>