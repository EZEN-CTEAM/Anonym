<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%

loginUser = (UserVO)session.getAttribute("loginUser");


%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage_styles.css" />

        <!-- 메인 컨텐츠 -->
        <main>
            <div class="main-container5">
                <!-- 내 정보 -->
                <h2>내 정보</h2>
                <br><br>
                <section class="imformation">
                    <form action="personModify.do" method="get">
                    
                    	<input type="hidden" name="user_no" value="<%= loginUser.getUser_no()%>">
                    	
                        <div class="mypageIP">아이디</div>
                        <div class="myIP"><%= loginUser.getUser_id() %></div>
                        
                        <div class="mypageIP">닉네임</div>
                        <div class="myIP"><%= loginUser.getUser_nickname() %></div>
                        
                        <div class="mypageIP">재직 상태</div>
                        <div class="myIP">
                        <%
                        if(loginUser.getUser_employment().equals("I")){
                        %><div>무직</div><%	
                        }else{
                        %><div>재직 중</div><%	
                        }
                        %>
                        </div>
                        <%
                        if(loginUser.getUser_employment().equals("D")){
                        %>
                        <div class="mypageIP">회사명</div>
                        <div class="myIP"><%= loginUser.getUser_company() %></div>
                        <%	
                        }
                        %>
                        <br>
                        <button class="cta-button">수정</button>
                    </form>
                </section>
            </div>
        </main>

<%@ include file="../include/footer.jsp" %>