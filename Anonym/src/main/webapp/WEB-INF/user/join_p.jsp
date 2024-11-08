<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ include file="../include/header.jsp" %>
<% 
request.setCharacterEncoding("UTF-8");
List<Company> companies = (List<Company>) request.getAttribute("companies");
%>  
<link rel="stylesheet" href="../css/k_styles.css" />
<link rel="stylesheet" href="../css/join_styles.css" />
<script src="<%= request.getContextPath() %>/js/jquery-3.7.1.js"></script>
<!-- <script>
	function employmentField() {
	    const companyField = document.getElementById('ucompany');
	    const isUnemployed = document.querySelector('input[name="uemployment"]:checked').value === 'I';
	
	    if (isUnemployed) {
	        companyField.value = ''; // Clear the field if the user selects "무직"
	        companyField.setAttribute('readonly', true); // Set to readonly
	        companyField.style.backgroundColor = '#ddd';
	    } else {
	        companyField.removeAttribute('readonly'); // Remove readonly if "재직" is selected
	        companyField.style.backgroundColor = '';
	    }
	}
</script> -->

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
            <div class="main-container2">
                <section class="join-menu">
                    <a href="join_p.do" style="color:#ff5252">개인</a>
                    |
                    <a href="join_c.do">기업</a>
                </section>
                <section class="personIP">
                    <form action="join_p.do" method="post" onsubmit="return DojoinUp();">
                    
                    <div class="joinIP">아이디</div>
                    <input type="text" name="user_id" id="user_id" class="input"  placeholder="4자 이상 영문과 숫자만 입력하세요" onkeyup="checkID()" >
                    <div id="msgboxID" class="msgbox"></div>
                    
                    <div class="joinIP">비밀번호</div>
                    <input type="password" name="user_pw" id="user_pw" class="input" placeholder="6자 이상 영문, 숫자, 특수문자(!@#$%^&*())를 조합하여 입력하세요">
                    <div class="msgbox"></div>
                    
                    <div class="joinIP">비밀번호 확인</div>
                    <input type="password" name="user_pwc" id="user_pwc" class="input" placeholder="비밀번호를 다시 입력하세요">
                    <div class="msgbox"></div>
                    
                    <div class="joinIP">닉네임</div>
                    <input type="text" name="user_nickname" id="user_nickname" class="input" placeholder="2자 이상 입력하세요" onkeyup="checkNickname()">
                    <div id="msgboxNickname" class="msgbox"></div>
                    
                    <div class="joinIP">재직 상태</div>
                    <div class="radioButtonStyle">
                        <label class="radioStyle">
                            <input type="radio" name="user_employment" value="I" onclick="employmentField()">
                            <span>무직</span>
                        </label>
                        <label class="radioStyle">
                            <input type="radio" name="user_employment" value="D" onclick="employmentField()">
                            <span>재직</span>
                        </label>
                    </div>
                    <div class="msgbox"></div>
                    
                    <div class="joinIP" id="companySection" style="display: none;">회사명</div>
                    	<div id="companyInputs" style="display: none;">
		                     <select id="companySelect" name="user_company" onchange="toggleInputField()">
		                     	<option disabled selected>회사를 선택하세요</option>
		                    	<% 
		                    		for (Company company : companies) {
		                    		%>
		                    		<option value="<%= company.getCompanyName() %>"><%= company.getCompanyName() %></option>
		                    		<% 
		                    		}
		                   		%>			
		                   		<option>직접 입력</option>
		                    </select>
	                    	<input type="text" name="user_company" id="ucompany" placeholder="회사명을 입력하세요" style="display: none;">
                   		</div>
                    <br>
                    <button class="cta-button">가입하기</button>
                </form>
                	<div class="login_btn">
                        이미 계정이 있으신가요? <a href="login_p.do">로그인</a>
                    </div>
                </section>
            </div>
        </main>

<%@ include file="../include/footer.jsp" %>

        <script>
        	// 회사명
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
            
            
            
            function DojoinUp(){
                let ID = document.querySelector("#user_id");
                let PASSWORD = document.querySelector("#user_pw");
                let CPW = document.querySelector("#user_pwc");
                let NICKNAME = document.querySelector("#user_nickname");
                
                //ID
                let idPatten = /^[a-zA-Z0-9]{4,}$/;
                if(ID.value.trim() == ""){
                    let msgBox = ID.parentNode.getElementsByClassName("msgbox");
                    msgBox[0].innerHTML = "<span style='color: red;'>아이디를 입력하세요</span>";
                    ID.value = "";
                    ID.focus();
                    return false;
                }
                if(!idPatten.test(ID.value)){
                    let msgBox = ID.parentNode.getElementsByClassName("msgbox");
                    msgBox[0].innerHTML ="<span style='color: red;'>4자 이상 영문과 숫자만 입력 가능합니다</span>";
                    ID.value = "";
                    ID.focus();
                    return false;
                }
                //PASSWORD
                let passwordPatten = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()])[a-zA-Z\d!@#$%^&*()]{6,}$/;
                if(PASSWORD.value.trim() == ""){
                    let msgBox = PASSWORD.parentNode.getElementsByClassName("msgbox");
                    msgBox[1].innerHTML = "<span style='color: red;'>비밀번호를 입력하세요</span>";
                    PASSWORD.value = "";
                    PASSWORD.focus();
                    return false;
                }
                if(!passwordPatten.test(PASSWORD.value)){
                    let msgBox = PASSWORD.parentNode.getElementsByClassName("msgbox");
                    msgBox[1].innerHTML ="<span style='color: red;'>6자 이상 영문, 숫자, 특수문자(!@#$%^&*())를 조합하여 입력하세요</span>";
                    PASSWORD.value = "";
                    PASSWORD.focus();
                    return false;
                }
                
                //CPW
                if(PASSWORD.value != CPW.value){
                    let msgBox = CPW.parentNode.getElementsByClassName("msgbox");
                    msgBox[2].innerHTML = "<span style='color: red;'>비밀번호가 일치하지 않습니다</span>";
                    CPW.value = "";
                    CPW.focus();
                    return false;
                }
              //NICKNAME
                if(NICKNAME.value.trim() == ""){
                    let msgBox = NICKNAME.parentNode.getElementsByClassName("msgbox");
                    msgBox[3].innerHTML = "<span style='color: red;'>닉네임을 입력하세요</span>";
                    NICKNAME.value = "";
                    NICKNAME.focus();
                    return false;
                }
                if(NICKNAME.value.length < 2 ){
                    let msgBox = NICKNAME.parentNode.getElementsByClassName("msgbox");
                    msgBox[3].innerHTML = "<span style='color: red;' >2자 이상 입력하세요</span>";
                    NICKNAME.value = "";
                    NICKNAME.focus();
                    return false;
                }
                if (!checkRadio()) {
                    return false;
                }

                return true;
                
            }
            
	            // 재직 여부
	            function checkRadio() {
	            	const employmentStatus = document.querySelector('input[name="user_employment"]:checked');
	                let msgBox = document.querySelectorAll('.radioButtonStyle + .msgbox')[0];  // 재직 상태의 msgbox를 정확히 선택
	                if (!employmentStatus) {
	                    msgBox.innerHTML = "<span style='color: red;'>재직 상태를 선택하세요</span>";
	                    return false;
	                }
	                msgBox.innerHTML = ""; // 메시지를 초기화 (선택된 경우)
	                return true;
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
    							msgBox[3].innerHTML = "";
    						};
    					}

    				}
                }
            
       		// 아이디 중복 확인
            function checkID(){
        		let value = $("#user_id").val();
        		$.ajax({
        			url : "checkID.do", 
        			type : "POST",
        			data : "user_id=" + value,
        			success : function(data){
        				if(data.trim() == "isId")
        				{
        					$("#msgboxID").html("이미 존재 하는 아이디 입니다.").css("color", "red");
	        			}else
	        			{
	        				$("#msgboxID").html("사용 가능한 아이디 입니다.").css("color", "green");
	        			}
        				
        			}
        		});
        		
        	}
            
       		//닉네임 중복 확인
       		function checkNickname(){
        		let value = $("#user_nickname").val();
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
            
            
        </script>
