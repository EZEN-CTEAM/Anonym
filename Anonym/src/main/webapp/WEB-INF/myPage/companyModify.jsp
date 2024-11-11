<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="teamproject.vo.*" %>
<%@ page import="java.util.*" %> 
<%@ include file="../include/header.jsp" %>
<%

request.setCharacterEncoding("UTF-8");

loginUserc = (CompanyVO)session.getAttribute("loginUserc");



%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/k_styles.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage_styles.css" />
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="<%= request.getContextPath() %>/js/jquery-3.7.1.js"></script>
<script>

  /*   function addressSearch() {
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
    	
    	function PWCheck(){
    		// 사용자가 입력한 아이디가 DB에 중복 여부 확인하기
    		let value = $("input[name=upw]").val();
    		$.ajax({
    			url : "PWCheck.do?cno=<%= loginUserc.getCno() %>",
    			type : "get",
    			data : "upw=" + value,
    			success : function(data){
    				if(data.trim() == "pwIs"){
    					$("#msgboxPW").html("비밀번호가 일치 합니다").css("color", "green");
    			}else{
    				$("#msgboxPW").html("비밀번호가 일치하지 않습니다").css("color", "red");
    			}
    				
    			}
    		});
    	}
    	
    	function CBRCCheck(){
    		let value = $("input[name=cbrcnum]").val();
    		$.ajax({
    			url : "CBRCCheck.do?cno=<%= loginUserc.getCno() %>",
    			type : "get",
    			data : "cbrcnum=" + value,
    			success : function(data){
    				if(data.trim() == "isCBRCN"){
    					$("#msgboxCBRCN").html("비밀번호가 일치 합니다").css("color", "green");
    			}else{
    				$("#msgboxCBRCN").html("비밀번호가 일치하지 않습니다").css("color", "red");
    			}
    				
    			}
    		});
    	}
    	
    	
    	
    	function DoModify(){
        	let PW = document.querySelector("input[name=cpwc]");
            let PASSWORD = document.querySelector("input[name=cpw]");
            let CPW = document.querySelector("input[name=pwc]");
            let COMPANYNAME = document.querySelector("input[name=cname]");
            let EMPLOYEE = document.querySelector("input[name=cemployee]");
            let BRCNUM = document.querySelector("input[name=cbrcnum]");
            
            //PW
            if(PW.value.trim() == ""){
                let msgBox = PW.parentNode.getElementsByClassName("msgbox");
                msgBox[0].innerHTML = "<span style='color: red;'>비밀번호를 입력하세요</span>";
                PW.value = "";
                PW.focus();
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
                msgBox[1].innerHTML ="<span style='color: red;'>6자 이상 영문, 숫자, 특수문자(!@#$%^&*())를 포함하여 입력하세요</span>";
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
          	//COMPANYNAME
            if(COMPANYNAME.value.trim() == ""){
                let msgBox = COMPANYNAME.parentNode.getElementsByClassName("msgbox");
                msgBox[3].innerHTML = "<span style='color: red;'>회사명을 입력하세요</span>";
                COMPANYNAME.value = "";
                COMPANYNAME.focus();
                return false;
            }
          //EMPLOYEE
            if(EMPLOYEE.value.trim() == ""){
                let msgBox = EMPLOYEE.parentNode.getElementsByClassName("msgbox");
                msgBox[4].innerHTML = "<span style='color: red;'>직원수를 입력하세요</span>";
                EMPLOYEE.value = "";
                EMPLOYEE.focus();
                return false;
            }
            let employeePatten = /^[0-9]+$/;
            if(!employeePatten.test(EMPLOYEE.value)){
                let msgBox = EMPLOYEE.parentNode.getElementsByClassName("msgbox");
                msgBox[4].innerHTML ="<span style='color: red;'>숫자만 입력 가능합니다</span>";
                EMPLOYEE.value = "";
                EMPLOYEE.focus();
                return false;
            }
            //BRCNUM
            if(BRCNUM.value.trim() == ""){
                let msgBox = BRCNUM.parentNode.getElementsByClassName("msgbox");
                msgBox[5].innerHTML = "<span style='color: red;'>사업자등록번호를 입력하세요</span>";
                BRCNUM.value = "";
                BRCNUM.focus();
                return false;
            }
            let brcPatten = /^(?=.*[0-9])(?=.*-)[0-9\-]{12}$/;
            if(!brcPatten.test(BRCNUM.value)){
                let msgBox = BRCNUM.parentNode.getElementsByClassName("msgbox");
                msgBox[5].innerHTML ="<span style='color: red;'>숫자와 하이픈(-)을 포함한 12자를 입력하세요</span>";
                BRCNUM.value = "";
                BRCNUM.focus();
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
    					msgBox[3].innerHTML = "";
    					msgBox[4].innerHTML = "";
    					msgBox[5].innerHTML = "";
    				};
    			}

    		}
        }
        
    
</script>

        <!-- 메인 컨텐츠 -->
        <main>
            <div class="main-container6">
                <h2>내 정보</h2>
                <br><br>
                <!-- 내 정보 -->
                <section class="modifyIP">
                <form action="<%= request.getContextPath() %>/myPage/companyModify.do?cno=<%= loginUserc.getCno() %>" method="post" onsubmit="return DoModify();">
       				<input type="hidden" name="cno" value="<%= loginUserc.getCno() %>">
       				
                    <div class="joinIP">아이디</div>
                    <div class="divID"><%= loginUserc.getCid() %></div>
	                
	                <div class="joinIP">새 비밀번호</div>
	                <input type="password" name="cpw" placeholder="6자 이상 영문, 숫자, 특수문자(!@#$%^&*())를 포함하여 입력하세요">
	                <div class="msgbox"></div>
	                
	                <div class="joinIP">새 비밀번호 확인</div>
	                <input type="password" name="pwc" placeholder="새 비밀번호를 다시 입력하세요">
	                <div class="msgbox"></div>


                    <div class="joinIP">회사명</div>
                    <input type="text" name="cname" id="cname" value="<%= loginUserc.getCname() %>">
                    <div class="msgbox"></div>

                    <!-- <div class="joinIP">위치</div>
                    <div class="postcode-container">
                        <input type="text" id="postcode" placeholder="우편번호">
                        <input type="button" onclick="addressSearch()" id="postcodeButton" value="우편번호 찾기">
                    </div>
                    
                    <input type="text" id="address" placeholder="주소" value=""><br>
                    <input type="text" id="detailAddress" placeholder="상세주소">
                    <input type="text" id="extraAddress" placeholder="참고항목"> -->
                    
                    <div class="form-group joinIP">
							<label for="userPostCode">우편번호</label>
							<div class="input-group">
								<input type="text" id="userPostCode" name="clocation" class="input"  placeholder="우편번호" readonly>&nbsp;&nbsp;
								<button type="button" onclick="searchAddress();" class="sbtn">검색</button>&nbsp;
								<button type="button" onclick="cancelAddress();" class="cbtn">취소</button>
							</div>
						</div>
						<div class="form-group joinIP">
						  	<label for="userAddress joinIP">주소</label>
					  		<input type="text" id="userAddress" name="clocation" placeholder="주소" readonly >
						</div>
						<div class="form-group joinIP">
						  <label for="userDtlAddress">상세주소</label>
						  <input type="text" id="userDtlAddress" name="clocation" maxlength="100" placeholder="상세주소" readonly>
						</div>

                    <div class="joinIP">직원수</div>
                    <%-- <input type="text" name="cemployee" id="cemployee" value="<%= loginUserc.getCemployee() %>"> --%>
                    	<select name="cemployee">
	                    	<option value="<%= loginUserc.getCemployee() %>" disabled selected><%= loginUserc.getCemployee() %></option>
	                    	<option value="1~50">1~50</option>
	                    	<option value="51~200">51~200</option>
	                    	<option value="201~500">201~500</option>
	                    	<option value="501~1,000">501~1,000</option>
	                    	<option value="1,001~5,000">1,001~5,000</option>
	                    	<option value="5,001~10,000">5,001~10,000</option>
	                    	<option value="10,000+">10,000+</option>
                    	</select>
                    <div class="msgbox"></div>

                    <div class="joinIP">업계</div>
	                    <select name="cindustry">
	                    	<option value="<%= loginUserc.getCindustry() %>" disabled selected><%= loginUserc.getCindustry() %></option>
							<option value="국가/공공기관">국가/공공기관</option>
							<option value="금융업">금융업</option>
							<option value="게임">게임</option>
							<option value="교육 서비스업">교육 서비스업</option>
							<option value="법무 서비스업">법무 서비스업</option>
							<option value="정보 서비스업">정보 서비스업</option>
							<option value="통신업">통신업</option>
							<option value="컨테츠/엔터테인먼트">컨텐츠/엔터테인먼트</option>
							<option value="자동차 및 부품 판매업">자동차 및 부품 판매업</option>
							<option value="기타 제조/수리업">기타 제조/수리업</option>
                    	</select>

                    <div class="joinIP">설립일</div>
                    <input type="date" name="canniversary" id="canniversary" value="<%= loginUserc.getCanniversary() %>"><br>

                    <div class="joinIP">사업자등록번호</div>
                    <input type="text" name="cbrcnum" id="cbrcnum" value="<%= loginUserc.getCbrcnum() %>" onkeyup="CBRCCheck()">
                    <div id="msgboxCBRCN" class="msgbox"></div>

                    <div class="joinIP">사업자등록증</div>
                    <input type="file" name="cbrc" id="cbrc" value="<%= loginUserc.getCbrc() %>">
                    
                    <div class="joinIP">회사 로고</div>
                    <input type="file" name="clogo" id="clogo" value="<%= loginUserc.getClogo() %>">

                    <br><br>
                    <button class="cta-button">저장</button>
                    <button type="button" class="cta-button" onclick="location.href='<%= request.getContextPath() %>/myPage/companyView.do?cno=<%= loginUserc.getCno()%>'">취소</button>
                </form>
                </section>
            </div>
        </main>

<%@ include file="../include/footer.jsp" %>