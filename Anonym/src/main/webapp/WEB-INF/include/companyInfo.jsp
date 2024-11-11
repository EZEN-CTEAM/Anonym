<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="teamproject.vo.*" %>
<%@ page import="java.util.*" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
<% 
request.setCharacterEncoding("UTF-8");

// 회사 정보 조회

CompanyVO vo = (CompanyVO)request.getAttribute("vo");

String cno = (String)request.getParameter("cno");

//회사 추천 상태

String crstate = "D";
int like_count = 0;
int dislike_count = 0;

if(vo != null)
{
	if(vo.getCrstate() != null) crstate = vo.getCrstate() ;
	if(vo.getClcount() != 0) like_count = (Integer) vo.getClcount();
	if((vo.getCdlcount() != 0)) dislike_count = (Integer) vo.getCdlcount();
	
	System.out.println("Like Count: " + like_count);	
	System.out.println("Dislike Count: " + dislike_count);
}

//인기글 목록
List<CompanyVO> cList = null;
if(request.getAttribute("cList") != null) cList = (List<CompanyVO>)request.getAttribute("cList");

%>   

        <!-- 메인 -->
        <main>
            <section class="black-section">
                <!-- <h1>기업 정보 플랫폼</h1>
                <p>투명한 기업 리뷰와 채용 정보를 제공합니다.</p> -->
            </section>
            
            <script>
            	function DoAJAXCall(obj, state){
            		// 좋아요 상태인가, 싫어요 상태인가, 선택하지 않았는가
    				$.ajax({
    					url : "recommendOk.do",
    					type : "get",
    					data : { 
    						"cno" : <%= cno %> ,
    						"crstate" : state
    						},
    						
    						success : function(data){
    			                
    							const response = data.trim().split('|');  
    							console.log(response);
    			                const isSuccess = response[0]; 
    			                const like_count = response[1];
    							console.log(like_count);
    			                const dislike_count = response[2];
    							console.log(dislike_count);

    			                // 좋아요 상태인 버튼
    						const btn_Y = `<button class="recommend-btn y" style="width: 80px; padding: 10px;" onclick="DoAJAXCall(this,'D');">
	<img src="https://img.icons8.com/?size=100&id=85638&format=png&color=ffffff">
	<span class="count-text" style="color: white;">` + like_count + `%</span>
</button>
<button class="recommend-btn b" style="width: 80px; padding: 10px;" onclick="DoAJAXCall(this,'N');">
	<img src="https://img.icons8.com/?size=100&id=87695&format=png&color=fb5757">
	<span class="count-text">` + dislike_count + `%</span>
</button>`
							// 중립 상태의 버튼들
							const btn_D = `<button class="recommend-btn g" onclick="DoAJAXCall(this,'Y');">
	<img src="https://img.icons8.com/?size=100&id=85608&format=png&color=46b7bd">
</button>
<button class="recommend-btn b" onclick="DoAJAXCall(this,'N');">
	<img src="https://img.icons8.com/?size=100&id=87695&format=png&color=fb5757">
</button>`
							// 싫어요 상태의 버튼들
							const btn_N = ` <button class="recommend-btn g" style="width: 80px; padding: 10px;" onclick="DoAJAXCall(this,'Y');">
	<img src="https://img.icons8.com/?size=100&id=85608&format=png&color=46b7bd">
	<span class="count-text">` + like_count + `%</span>
</button>
<button class="recommend-btn n" style="width: 80px; padding: 10px;" onclick="DoAJAXCall(this,'D');">
	<img src="https://img.icons8.com/?size=100&id=87737&format=png&color=ffffff">
	<span class="count-text" style="color: white;">` + dislike_count + `%</span>
</button>`
    						switch(state){
    							case "Y" :
	    						// 중립에서 -> 좋아요
	    						$(obj).parent().html(btn_Y);
	    						break;
	    						
    						case "N" :
        						// 중립에서 -> 싫어요
        						$(obj).parent().html(btn_N);
	    						break;
	    						
    						case "D" :
	    						// 좋아요 -> 취소
	    						$(obj).parent().html(btn_D);
	    						break;
    						}
    					}
    				});
            		
            	}
            </script>

            <div style="background-color: #fff;">
                <section class="company-header">
                    <div class="company-details">
                        <%-- <img src="<%= request.getContextPath() %>/image/potato.jpeg" alt="회사 로고" class="company-logo"> --%>
                        <img src="<%= request.getContextPath() %>/user/down.do?fileName=<%= vo.getClogo() %>" class="company-logo">
                        <div class="company-info">
                            <h1><%= vo.getCname() %></h1>
                            <div>
                            	<%
/*                             	out.print(session.getAttribute("loginUser")); */
                            	if(session.getAttribute("loginUser") == null)
                            	{
                            		%>
                                	<button class="recommend-btn g" onclick="location.href='<%= request.getContextPath() %>/user/login_p.do'">
                                		<img src="https://img.icons8.com/?size=100&id=85608&format=png&color=46b7bd">
                               		</button>
                                	<button class="recommend-btn b" onclick="location.href='<%= request.getContextPath() %>/user/login_p.do'">
                               			<img src="https://img.icons8.com/?size=100&id=87695&format=png&color=fb5757">
                             		</button>
	                             	<%
                            	}else
                            	{
	                            	if(crstate.equals("D"))
	                            	{
	                            		%>
	                                	<button class="recommend-btn g" onclick="DoAJAXCall(this,'Y');">
	                                		<img src="https://img.icons8.com/?size=100&id=85608&format=png&color=46b7bd">
	                               		</button>
	                                	<button class="recommend-btn b" onclick="DoAJAXCall(this,'N');">
	                               			<img src="https://img.icons8.com/?size=100&id=87695&format=png&color=fb5757">
	                             		</button>
		                             	<%
	                            	}else if(crstate.equals("Y"))
							      	{
					                    %>
					                    <button class="recommend-btn y" style="width: 80px; padding: 10px;" onclick="DoAJAXCall(this,'D');">
					                    	<img src="https://img.icons8.com/?size=100&id=85638&format=png&color=ffffff">
					                    	<span class="count-text" style="color: white;"><%= like_count  %>%</span>
				                    	</button>
	                                	<button class="recommend-btn b" style="width: 80px; padding: 10px;" onclick="DoAJAXCall(this,'N');">
	                                		<img src="https://img.icons8.com/?size=100&id=87695&format=png&color=fb5757">
					                    	<span class="count-text"><%= dislike_count %>%</span>
	                               		</button>
										<% 
									}else if(crstate.equals("N"))
									{
										%>
					                    <button class="recommend-btn g" style="width: 80px; padding: 10px;" onclick="DoAJAXCall(this,'Y');">
					                    	<img src="https://img.icons8.com/?size=100&id=85608&format=png&color=46b7bd">
					                    	<span class="count-text"><%= like_count %>%</span>
				                    	</button>
	                                	<button class="recommend-btn n" style="width: 80px; padding: 10px;" onclick="DoAJAXCall(this,'D');">
	                                		<img src="https://img.icons8.com/?size=100&id=87737&format=png&color=ffffff">
					                    	<span class="count-text" style="color: white;"><%= dislike_count %>%</span>
	                               		</button>
					   					<%
									}
                            	}
								%>	 
                            </div>
                        </div>
                    </div>
                    <div>
                        <button class="review-btn" onclick="location.href='reviewRegister.do?cno=<%= cno %>'">이 회사 리뷰하기</button>
                    </div>
                </section>
            </div>
            <nav>
            
<div class="tab-menu">
    <a href="companyInfo.do?cno=<%= cno %>" class="<%= (request.getRequestURI().contains("companyInfo.do")) ? "active" : "" %>">소개</a>
    <a href="reviewList.do?cno=<%= cno %>" class="<%= (request.getRequestURI().contains("reviewList.do")) ? "active" : "" %>">리뷰</a>
    <%
    	if(loginUser != null && cno.equals(loginUser.getUser_cno()))
    	{
    %> 
    <a href="communityList.do?cno=<%= cno %>" class="<%= (request.getRequestURI().contains("communityList.do")) ? "active" : "" %>">커뮤니티</a>
	<%
    	}
	%>
</div>

<script>
    window.onload = function() {
        var currentUrl = window.location.href;
        var tabs = document.querySelectorAll('.tab-menu a');
        
        tabs.forEach(function(tab) {
            if (currentUrl.includes(tab.getAttribute('href'))) {
                tab.classList.add('active');
            } else {
                tab.classList.remove('active');
            }
        });
    };
</script>

			</nav>
			<div class="main-container">
				<!-- 내 용 -->				
