<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%

loginUser = (UserVO)session.getAttribute("loginUser");

request.setCharacterEncoding("UTF-8");
List<Company> companies = (List<Company>) request.getAttribute("companies");


%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage_styles.css" />

   <!-- 메인 컨텐츠 -->
   <main>
    <div class="main-container5">
        <h2>내 정보</h2>
        <br><br>
        <!-- 내 정보 -->
        <section class="modifyIP">
       		<form action="personModify.do?user_no=<%= loginUser.getUser_no()%>" method="post" onsubmit="return DoModify();">
       			<input type="hidden" name="user_no" value="<%= loginUser.getUser_no()%>">
       			
                <div class="joinIP">아이디</div>
                <div class="divID"><%= loginUser.getUser_id() %></div>
                
                
                <div class="joinIP">새 비밀번호</div>
                <input type="password" name="user_pw" placeholder="6자 이상 영문, 숫자, 특수문자(!@#$%^&*())를 포함하여 입력하세요" onkeyup="checkPW()">
                <div id="msgboxPW" class="msgbox"></div>
                
                <div class="joinIP">새 비밀번호 확인</div>
                <input type="password" name="pwc" placeholder="새 비밀번호를 다시 입력하세요">
                <div class="msgbox"></div>
                
                <div class="joinIP">닉네임</div>
                <input type="text" name="user_nickname" value="<%= loginUser.getUser_nickname() %>" onkeyup="checkNickname()">
                <div id="msgboxNickname" class="msgbox"></div>
                
                <div class="joinIP">재직 상태</div>
                <div class="radioButtonStyle">
                    <label class="radioStyle">
                        <input type="radio" name="user_employment" value="I" <%= loginUser.getUser_employment().equals("I") ? "checked" : "" %> onclick="employmentField()">
                        <span>무직</span>
                    </label>
                    <label class="radioStyle">
                        <input type="radio" name="user_employment" value="D" <%= loginUser.getUser_employment().equals("D") ? "checked" : "" %> onclick="employmentField()">
                        <span>재직</span>
                    </label>
                </div>
                
                <div class="joinIP" id="companySection" style="display: none;">회사명</div>
                	<div id="companyInputs" style="display: none;">
	                    <select id="companySelect" name="user_company" onchange="toggleInputField()">
	                        <option value="<%= loginUser.getUser_company() %>" disabled selected><%= loginUser.getUser_company() %></option>
	                        <% 
	                   		for (Company company : companies) {
	                   		%>
	                   		<option value="<%= company.getCompanyName() %>"><%= company.getCompanyName() %></option>
	                   		<% 
	                   		}
	                   		%>	
	                        <option>직접 입력</option>
	                    </select>
	                    <input type="text" id="ucompany" name="user_company" placeholder="회사명을 입력하세요" style="display: none;">
                    </div>
                <br><br>
                <button class="cta-button">저장</button>
                <button type="button" class="cta-button" onclick="location.href='<%= request.getContextPath() %>/myPage/personView.do?user_no=<%= loginUser.getUser_no()%>'">취소</button>
           	</form>
        </section>
    </div>
  </main>

<%@ include file="../include/footer.jsp" %>
<script src="<%= request.getContextPath() %>/js/jquery-3.7.1.js"></script>
  <script>
	//회사명
	function employmentField() {
	    const employmentStatus = document.querySelector('input[name="user_employment"]:checked').value;
	    const companySection = document.getElementById("companySection");
	    const companyInputs = document.getElementById("companyInputs");
	    const manualInput = document.getElementById("ucompany");
	
	    if (employmentStatus === "D") {
	        // 재직 상태일 경우 회사명 입력 필드 보이기
	        companySection.style.display = "block";
	        companyInputs.style.display = "block"; // 드롭다운 및 입력 필드 보이기
	    } else {
	        // 무직일 경우 회사명 입력 필드 숨기기
	        companySection.style.display = "none";
	        companyInputs.style.display = "none"; // 드롭다운 및 입력 필드 숨기기
	        manualInput.style.display = "none"; // 직접 입력 필드 숨기기
	        manualInput.value = ""; // 입력값 초기화
	    }
	}
  
	// 회사명 직접 입력창
    function toggleInputField() {
        const selectElement = document.getElementById('companySelect');
        const customInput = document.getElementById('ucompany');

        // 선택된 값이 "직접 입력"인지 확인
        if (selectElement.value === "직접 입력") {
            customInput.style.display = "block"; // 입력 필드 보이기
            customInput.focus(); // 입력 필드에 포커스
        } else {
            customInput.style.display = "none"; // 입력 필드 숨기기
            customInput.value = ""; // 입력 필드 초기화
        }
    }
	
    function checkPW(){
		let value = $("input[name=user_pw]").val();
		$.ajax({
			url : "checkPW.do?user_no=<%= loginUser.getUser_no()%>",
			type : "post",
			data : "user_pw=" + value,
			success : function(data){
				if(data.trim() == "isPW"){
					$("#msgboxPW").html("기존의 비밀번호와 다르게 입력하세요").css("color", "red");
				}
			}
		});
	}
    
    function checkNickname(){
		let value = $("input[name=user_nickname]").val();
		$.ajax({
			url : "checkNickname.do", 
			type : "post",
			data : "user_nickname=" + value,
			success : function(data){
				if(data.trim() == "isNickname")
				{
					$("#msgboxNickname").html("이미 존재 하는 닉네임 입니다.").css("color", "red");
    			}else
    			{
    				$("#msgboxNickname").html("사용 가능한 닉네임 입니다.").css("color", "green");
    			}
				
			}
		});
		
	}
	
    function DoModify(){
        let PASSWORD = document.querySelector("input[name=user_pw]");
        let CPW = document.querySelector("input[name=pwc]");
        let NICKNAME = document.querySelector("input[name=user_nickname]");
    
        //PASSWORD
        let passwordPatten = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()])[a-zA-Z\d!@#$%^&*()]{6,}$/;
        if(PASSWORD.value.trim() == ""){
            let msgBox = PASSWORD.parentNode.getElementsByClassName("msgbox");
            msgBox[0].innerHTML = "<span style='color: red;'>비밀번호를 입력하세요</span>";
            PASSWORD.value = "";
            PASSWORD.focus();
            return false;
        }
        if(!passwordPatten.test(PASSWORD.value)){
            let msgBox = PASSWORD.parentNode.getElementsByClassName("msgbox");
            msgBox[0].innerHTML ="<span style='color: red;'>6자 이상 영문, 숫자, 특수문자(!@#$%^&*())를 포함하여 입력하세요</span>";
            PASSWORD.value = "";
            PASSWORD.focus();
            return false;
        }
        //CPW
        if(PASSWORD.value != CPW.value){
            let msgBox = CPW.parentNode.getElementsByClassName("msgbox");
            msgBox[1].innerHTML = "<span style='color: red;'>비밀번호가 일치하지 않습니다</span>";
            CPW.value = "";
            CPW.focus();
            return false;
        }
      	//NICKNAME
        if(NICKNAME.value.trim() == ""){
            let msgBox = NICKNAME.parentNode.getElementsByClassName("msgbox");
            msgBox[2].innerHTML = "<span style='color: red;'>닉네임을 입력하세요</span>";
            NICKNAME.value = "";
            NICKNAME.focus();
            return false;
        }
        if(NICKNAME.value.length < 2 ){
            let msgBox = NICKNAME.parentNode.getElementsByClassName("msgbox");
            msgBox[2].innerHTML = "<span style='color: red;' >2자 이상 입력하세요</span>";
            NICKNAME.value = "";
            NICKNAME.focus();
            return false;
        }
     
	}
	
	window.onload = function (){
		let inputs = document.getElementsByTagName("input");
		inputs[0].focus();

		for( let item of inputs ){
			let msgBox = item.parentElement.getElementsByClassName("msgbox");
			if( msgBox.length > 0 )
			{	
				item.onkeydown = () => {
					msgBox[0].innerHTML = "";
					 msgBox[1].innerHTML = "";
					msgBox[2].innerHTML = "";
				};
			}

		}
    }
    
    
    
    
    
</script>
