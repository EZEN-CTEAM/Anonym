<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>     
<%@ include file="../include/header.jsp" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage_styles.css" />

  <!-- 메인 컨텐츠 -->
  <main>
    <div class="main-container5">
        <!-- 내 정보 -->
        <h2>내 정보</h2>
        <br><br>
        <section class="imformation">
            <form action="companyModify.do?cno=<%= loginUserc.getCno() %>" method="get">
            
            	<div class="mypageIP">로고</div>
                <div class="myIP"><img src="<%= request.getContextPath() %>/user/down.do?fileName=<%= loginUserc.getClogo() %>"></div>
                
                <div class="mypageIP">아이디</div>
                <div class="myIP"><%= loginUserc.getCid() %></div>

                <div class="mypageIP">회사명</div>
                <div class="myIP"><%= loginUserc.getCname() %></div>
                
                <div class="mypageIP">위치</div>
                <div class="myIP"><%= loginUserc.getClocation() %></div>
                
                <div class="mypageIP">직원수</div>
                <div class="myIP"><%= loginUserc.getCemployee() %></div>
                
                <div class="mypageIP">업계</div>
                <div class="myIP"><%= loginUserc.getCindustry() %></div>
                
                <div class="mypageIP">설립일</div>
                <div class="myIP"><%= loginUserc.getCanniversary() %></div>
                
                <div class="mypageIP">사업자등록번호</div>
                <div class="myIP"><%= loginUserc.getCbrcnum() %></div>
                
                <div class="mypageIP">사업자등록증</div>
                <div class="myIP"><img src="<%= request.getContextPath() %>/user/down.do?fileName=<%= loginUserc.getCbrc() %>"></div>
                <br>
                <button class="cta-button">수정</button>
            </form>
        </section>
    </div>
  </main>

<%@ include file="../include/footer.jsp" %>