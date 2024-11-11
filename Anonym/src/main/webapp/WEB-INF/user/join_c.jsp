<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/join_styles.css" />
        <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
        <script src="<%= request.getContextPath() %>/js/jquery-3.7.1.js"></script>
        <script>
/*             function addressSearch() {
                new daum.Postcode({
                    oncomplete: function(data) {
                        var addr = ''; 
                        var extraAddr = ''; 
        
                        if (data.userSelectedType === 'R') { 
                            addr = data.roadAddress;
                        } else { 
                            addr = data.jibunAddress;
                        }
        
                        if(data.userSelectedType === 'R'){
                            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                                extraAddr += data.bname;
                            }
                            if(data.buildingName !== '' && data.apartment === 'Y'){
                                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                            }
                            if(extraAddr !== ''){
                                extraAddr = ' (' + extraAddr + ')';
                            }
                            document.getElementById("extraAddress").value = extraAddr;
                        
                        } else {
                            document.getElementById("extraAddress").value = '';
                        }
        
                        document.getElementById('postcode').value = data.zonecode;
                        document.getElementById("address").value = addr;
                        document.getElementById("detailAddress").focus();
                    }
                }).open();
            } */
               /*  function addressSearch() {
                    new daum.Postcode({
                        oncomplete: function(data) {
                            var addr = ''; 
                            var extraAddr = ''; 
            
                            if (data.userSelectedType === 'R') { 
                                addr = data.roadAddress;
                            } else { 
                                addr = data.jibunAddress;
                            }
            
                            if(data.userSelectedType === 'R'){
                                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                                    extraAddr += data.bname;
                                }
                                if(data.buildingName !== '' && data.apartment === 'Y'){
                                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                                }
                                if(extraAddr !== ''){
                                    extraAddr = ' (' + extraAddr + ')';
                                }
                                document.getElementById("extraAddress").value = extraAddr;
                            
                            } else {
                                document.getElementById("extraAddress").value = '';
                            }
            
                            document.getElementById('postcode').value = data.zonecode;
                            document.getElementById("address").value = addr;
                            document.getElementById("detailAddress").focus();
                        }
                    }).open();
                } */
            	
            	function searchAddress() {
            	new daum.Postcode({
            	oncomplete: function(data) { // 선택시 입력값 세팅
            	document.getElementById("userAddress").value = data.address; // 주소 넣기
            	document.getElementById("userPostCode").value = data.zonecode; // 우편번호 넣기
            	var inputDtlAddr = document.getElementById("userDtlAddress"); // 주소란 읽기전용 설정
            	inputDtlAddr.readOnly = false;
            	}
            	}).open();
            	}
            	
            	function cancelAddress() {
            	var inputPostCode = document.getElementById("userPostCode");
            	inputPostCode.value = "" // 우편번호 초기화
            	var inputAddr = document.getElementById("userAddress");
            	inputAddr.value = "" // 주소란 초기화
            	var inputDtlAddr = document.getElementById("userDtlAddress");
            	inputDtlAddr.value = "" // 상세주소란 초기화
            	inputDtlAddr.readOnly = true; // 상세주소란 읽기전용 해제
            	}
            	
            	// 유효성 검사
            	function DojoinUp(){
				    let ID = document.querySelector("input[name=cid]");
				    let PASSWORD = document.querySelector("input[name=cpw]");
				    let CPW = document.querySelector("input[name=pwc]");
				    let COMPANYNAME = document.querySelector("input[name=cname]");
				    let clocation = document.querySelector("input[name=pwc]");
				    let SBTN = document.querySelector("button[class='sbtn']");
				    let EMPLOYEE = document.querySelector("input[name=cemployee]");
				    let INDUSTRY = document.querySelector("select[name=cindustry]");
				    let anniversary = document.querySelector("input[name=canniversary]");
				    let BRCNUM = document.querySelector("input[name=cbrcnum]");
				    let BRCCERTIFICATE = document.querySelector("input[name=cbrc]");
				    let LOGO = document.querySelector("input[name=clogo]");
				    
				    // 아이디 유효성 검사
				    let idPattern = /^[a-zA-Z0-9]{4,}$/;
				    if(ID.value.trim() == ""){
				        let msgBox = ID.parentNode.getElementsByClassName("msgbox");
				        msgBox[0].innerHTML = "<span style='color: red;'>아이디를 입력하세요</span>";
				        ID.value = "";
				        ID.focus();
				        return false;
				    }
				    if(!idPattern.test(ID.value)){
				        let msgBox = ID.parentNode.getElementsByClassName("msgbox");
				        msgBox[0].innerHTML = "<span style='color: red;'>4자 이상의 영문과 숫자만 입력 가능합니다</span>";
				        ID.value = "";
				        ID.focus();
				        return false;
				    }
				
				    // 비밀번호 유효성 검사
				    let passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()])[a-zA-Z\d!@#$%^&*()]{6,}$/;
				    if(PASSWORD.value.trim() == ""){
				        let msgBox = PASSWORD.parentNode.getElementsByClassName("msgbox");
				        msgBox[1].innerHTML = "<span style='color: red;'>비밀번호를 입력하세요</span>";
				        PASSWORD.value = "";
				        PASSWORD.focus();
				        return false;
				    }
				    if(!passwordPattern.test(PASSWORD.value)){
				        let msgBox = PASSWORD.parentNode.getElementsByClassName("msgbox");
				        msgBox[1].innerHTML = "<span style='color: red;'>6자 이상 영문, 숫자, 특수문자(!@#$%^&*())를 포함하여 입력하세요</span>";
				        PASSWORD.value = "";
				        PASSWORD.focus();
				        return false;
				    }
				
				    // 비밀번호 확인
				    if(PASSWORD.value != CPW.value){
				        let msgBox = CPW.parentNode.getElementsByClassName("msgbox");
				        msgBox[2].innerHTML = "<span style='color: red;'>비밀번호가 일치하지 않습니다</span>";
				        CPW.value = "";
				        CPW.focus();
				        return false;
				    }
				
				    // 회사명 유효성
				    if(COMPANYNAME.value.trim() == ""){
				        let msgBox = COMPANYNAME.parentNode.getElementsByClassName("msgbox");
				        msgBox[3].innerHTML = "<span style='color: red;'>회사명을 입력하세요</span>";
				        COMPANYNAME.value = "";
				        COMPANYNAME.focus();
				        return false;
				    }
				    // 위치
				    if (clocation.value.trim() == "") {
				        let msgBox = userPostCode.parentNode.getElementsByClassName("msgbox");
				        msgBox[4].innerHTML = "<span style='color: red;'>우편번호를 입력하세요</span>";
				        SBTN.focus();
				        return false;  // 폼 제출 막기
				    }
				
				    // 직원수 유효성 검사
				    if(EMPLOYEE.value.trim() == ""){
				        let msgBox = EMPLOYEE.parentNode.getElementsByClassName("msgbox");
				        msgBox[5].innerHTML = "<span style='color: red;'>직원수를 입력하세요</span>";
				        EMPLOYEE.value = "";
				        EMPLOYEE.focus();
				        return false;
				    }
				    let employeePattern = /^[0-9]+$/;
				    if(!employeePattern.test(EMPLOYEE.value)){
				        let msgBox = EMPLOYEE.parentNode.getElementsByClassName("msgbox");
				        msgBox[5].innerHTML = "<span style='color: red;'>숫자만 입력 가능합니다</span>";
				        EMPLOYEE.value = "";
				        EMPLOYEE.focus();
				        return false;
				    }
				    
					 // INDUSTRY
				    if (INDUSTRY.value == "") {
				        let msgBox = INDUSTRY.parentNode.getElementsByClassName("msgbox");
				        msgBox[6].innerHTML = "<span style='color: red;'>업계를 선택하세요</span>";
				        INDUSTRY.focus();
				        return false;
				    }
				
				 	// 설립일 유효성 검사
				    if (anniversary.value.trim() == "") {
				        let msgBox = anniversary.parentNode.getElementsByClassName("msgbox");
				        msgBox[7].innerHTML = "<span style='color: red;'>설립일을 입력하세요</span>";
				        anniversary.focus();
				        return false;  // 폼 제출 막기
				    }

				    // Check if date is in the correct format (YYYY-MM-DD)
				    let datePattern = /^\d{4}-\d{2}-\d{2}$/;
				    if (!datePattern.test(establishmentDate)) {
				        let msgBox = anniversary.parentNode.getElementsByClassName("msgbox");
				        msgBox[7].innerHTML = "<span style='color: red;'>올바른 날짜 형식(YYYY-MM-DD)을 입력하세요</span>";
				        anniversary.value = "";
				        anniversary.focus();
				        return false;
				    } 
					 
				    // 사업자등록번호 유효성 검사
				    if(BRCNUM.value.trim() == ""){
				        let msgBox = BRCNUM.parentNode.getElementsByClassName("msgbox");
				        msgBox[8].innerHTML = "<span style='color: red;'>사업자등록번호를 입력하세요</span>";
				        BRCNUM.value = "";
				        BRCNUM.focus();
				        return false;
				    }
				    let brcPattern = /^[0-9]{3}-[0-9]{2}-[0-9]{5}$/;
				    if(!brcPattern.test(BRCNUM.value)){
				        let msgBox = BRCNUM.parentNode.getElementsByClassName("msgbox");
				        msgBox[8].innerHTML = "<span style='color: red;'>하이픈(-)을 포함한 000-00-00000 형식으로 입력하세요 </span>";
				        BRCNUM.value = "";
				        BRCNUM.focus();
				        return false;
				    }
				
				
				    // 사업자등록증과 로고 파일 유효성 검사
				    if(BRCCERTIFICATE.files.length == 0){
				        alert("사업자등록증을 첨부해주세요.");
				        return false;
				    }
				    if(LOGO.files.length == 0){
				        alert("로고를 첨부해주세요.");
				        return false;
				    }
				
				    return true;
				}
            	
            	window.onload = function () {
            	    let inputs = document.getElementsByTagName("input");
            	    inputs[0].focus();

            	    for (let item of inputs) {
            	        let msgBox = item.parentElement.getElementsByClassName("msgbox");
            	        if (msgBox.length > 0) {
            	            item.onkeydown = () => {
            	                // msgbox[0]부터 msgbox[6]까지 초기화
            	                for (let i = 0; i < msgBox.length; i++) {
            	                    msgBox[i].innerHTML = "";
            	                }
            	            };
            	        }
            	    }
            	};
            	
            	// 아이디 중복 체크
            	function IDCheck(){
	        		let value = $("input[name=cid]").val();
		        		$.ajax({
		        			url : "IDCheck.do", 
		        			type : "POST",
		        			data : "cid=" + value,
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
            	
            /* 	// 닉네임 중복 체크
            	function NicknameCheck(){
	        		let value = $("input[name=cnickname]").val();
		        		$.ajax({
		        			url : "NicknameCheck.do", 
		        			type : "post",
		        			data : "cnickname=" + value,
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
            	 */
            	
            	// 사업자번호 중복 체크
            	function BrcCheck(){
	        		let value = $("input[name=cbrcnum]").val();
		        		$.ajax({
		        			url : "BrcCheck.do", 
		        			type : "post",
		        			data : "cbrcnum=" + value,
		        			success : function(data){
		        				if(data.trim() == "isBrc")
		        				{
		        					$("#msgboxBRC").html("이미 존재 하는 사업자번호 입니다").css("color", "red");
			        			}else
			        			{
			        				$("#msgboxBRC").html("등록 가능 합니다").css("color", "green");
			        			}
		        				
		        			}
		        		});
        		
        		}
        </script>

        <!-- 메인 컨텐츠 -->
        <main>
            <div class="main-container3">
                <section class="join-menu">
                    <a href="join_p.do">개인</a>
                    |
                    <a href="join_c.do" style="color:#ff5252">기업</a>
                </section>
                <section class="companyIP">
	               	<form action="join_c.do" enctype="multipart/form-data" method="post" onsubmit="return DojoinUp();" >
	               	
	                    <div class="joinIP">아이디</div>
	                    <input type="text" name="cid" class="input" placeholder="4자 이상의 영문과 숫자만 입력하세요" onkeyup="IDCheck()">
	                    <div id="msgboxID" class="msgbox"></div>
	                    
	                    <div class="joinIP">비밀번호</div>
	                    <input type="password" name="cpw" class="input" placeholder="6자 이상 영문, 숫자, 특수문자(!@#$%^&*())를 포함하여 입력하세요">
	                    <div class="msgbox"></div>
	                    
	                    <div class="joinIP">비밀번호 확인</div>
	                    <input type="password" name="pwc" class="input" placeholder="비밀번호를 다시 입력하세요">
	                    <div class="msgbox"></div>
	                    
	                    <div class="joinIP">회사명</div>
	                    <input type="text" name="cname" class="input" placeholder="회사명을 입력하세요">
	                    <div class="msgbox"></div>
	                    
	                    <!-- <div class="joinIP">닉네임</div>
	                    <input type="text" name="cnickname" class="input" placeholder="닉네임을 입력하세요" onkeyup="NicknameCheck()">
	                    <div id="msgboxNickname" class="msgbox"></div> -->
	                    
	                    <!-- <div class="joinIP">위치</div>
	                    <input type="text" name="clocation"> -->
	                    <div class="form-group joinIP">
							<label for="userPostCode">우편번호</label>
							<div class="input-group">
								<input type="text" id="userPostCode" name="clocation" class="input"  placeholder="우편번호" readonly>&nbsp;&nbsp;
								<button type="button" onclick="searchAddress();" class="sbtn">검색</button>&nbsp;
								<button type="button" onclick="cancelAddress();" class="cbtn">취소</button>
							</div>
						</div>
						<div class="msgbox" style="color:red;">우편번호를 검색하세요</div>
						
						<div class="form-group joinIP">
						  	<label for="userAddress joinIP">주소</label>
					  		<input type="text" id="userAddress" name="clocation" placeholder="주소" readonly >
						</div>
						<div class="form-group joinIP">
						  <label for="userDtlAddress">상세주소</label>
						  <input type="text" id="userDtlAddress" name="clocation" maxlength="100" placeholder="상세주소" readonly>
						</div>
						
	                    <div class="joinIP">직원수</div>
	                    <input type="text" name="cemployee" placeholder="직원수를 입력하세요">
	                    <div class="msgbox"></div>
	                    
	                    <div class="joinIP">업계</div>
	                    <select name="cindustry">
	                    	<option value="" disabled selected>업계를 선택하세요</option>
	                    	<option value="제조업">제조업</option>
	                    	<option value="건설업">건설업</option>
	                    	<option value="도매 및 소매업">도매 및 소매업</option>
	                    	<option value="숙박 및 음식점업">숙박 및 음식점업</option>
	                    	<option value="운수업">운수업</option>
	                    	<option value="통신업">통신업</option>
	                    	<option value="금융 및 보험업">금융 및 보험업</option>
	                    	<option value="사업서비스업">사업서비스업</option>
                    	</select>
                    	<div class="msgbox"></div>
						
	                    <div class="joinIP">설립일</div>
	                    <input type="date" name="canniversary">
	                    <div class="msgbox"></div>
	                    
	                    <div class="joinIP">사업자등록번호</div>
	                    <input type="text" name="cbrcnum" placeholder="하이픈(-)을 포함한 000-00-00000 형식으로 입력하세요" onkeyup="BrcCheck()">
	                    <div id="msgboxBRC" class="msgbox"></div>
	                    
	                    <div class="joinIP">사업자등록증</div>
	                    <input type="file" name="cbrc">
	                    <div class="msgbox" style="color:red;">등록증을 첨부하세요</div>
	                    
	                    <div class="joinIP">로고</div>
	                    <input type="file" name="clogo">
	                    <div class="msgbox" style="color:red;">로고를 첨부하세요</div>
	                    <br><br>
	                    <button class="cta-button">가입하기</button>
	                </form>
	                <div class="login_btn">
                        이미 계정이 있으신가요? <a href="login_c.do">로그인</a>
                    </div>
                </section>
            </div>
        </main>

<%@ include file="../include/footer.jsp" %>
