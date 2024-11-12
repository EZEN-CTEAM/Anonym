<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<link rel="stylesheet" href="../css/k_styles.css" />
<link rel="stylesheet" href="../css/login_styles.css" />

<%
    String errorMessage = (String) session.getAttribute("errorMessage");
    if (errorMessage != null) {
        session.removeAttribute("errorMessage");
%>
    <script type="text/javascript">
        alert("<%= errorMessage %>");
    </script>
<%
    }
%>
        <!-- 메인 컨텐츠 -->
        <main>
            <div class="main-container4">
                <h1>Login</h1><br><br>
				<section class="login-menu">
                    <a href="login_p.do" style="color:#ff5252">개인</a>
                    |
                    <a href="login_c.do">기업</a>
                </section>
                <section class="IP">
                	<form action="login_p.do" method="post">
	                   <div class="loginIP">아이디</div>
	                   <input type="text" name="user_id" >
	                   
	                   <div class="loginIP">비밀번호</div>
	                   <input type="password" name="user_pw" value="qq11!!">
	                   <br><br><br>
	                   <button class="cta-button">로그인</button>
                    </form>
                     <div class="join_btn">
                        계정이 없으신가요? <a href="join_p.do">회원가입</a>
                    </div>
                </section>
            </div>
        </main>

<%@ include file="../include/footer.jsp" %>