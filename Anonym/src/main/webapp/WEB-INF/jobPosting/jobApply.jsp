<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="teamproject.vo.*"%>
<%@ include file="../include/header.jsp" %>
<%
//	List<JobpostingVO> jpList = (List<JobpostingVO>)request.getAttribute("jpList");
	JobpostingVO jpvo = (JobpostingVO)request.getAttribute("jpvo");
	List<ResumeVO> rList = (List<ResumeVO>)request.getAttribute("rList");
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/recruitment_information_apply.css" />

  <!-- 메인 컨텐츠 -->
  <main>
    <div class="main-container">
      <div>
      <!-- 기업정보 요약 -->
        <section class="board-container">
          <div class="summation_title">
            <h3>기업 요약정보</h3>
          </div>
          <div>
            <div class="summation_container">
              <div class="summation_list_container">
                <div class="summation_list_itembox">
                  <div class="summation_list_item">회사명</div>
                  <div class="summation_list_item">위치</div>
                  <div class="summation_list_item">직원수</div>
                </div>
                <div class="summation_list_resultbox">
                  <div class="summation_list_item"><%= jpvo.getCompany_no() %></div>
                <div class="summation_list_item"><%= jpvo.getCompany_location() %></div>
                <div class="summation_list_item"><%= jpvo.getCompany_employee() %></div>
                </div>
              </div>
              <div class="summation_list_container">
                <div class="summation_list_itembox">
                  <div class="summation_list_item">업계</div>
                  <div class="summation_list_item">설립</div>
                  <div class="summation_list_item">마감일</div>
                </div>
                <div class="summation_list_resultbox">
                  <div class="summation_list_item"><%= jpvo.getCompany_industry() %></div>
                <div class="summation_list_item"><%= jpvo.getCompany_location() %></div>
                <div class="summation_list_item"><%= jpvo.getJob_posting_period() %></div>
                </div>
              </div>
            </div>
        </section>

        <!-- 채용 정보 -->
        <section class="board-container">
          <div class="apply">
            <h3>채용 정보</h3>
          </div>
          <div class="apply_container">
            <div class="apply_title"><%= jpvo.getJob_posting_title() %></div>
          	<div class="apply_addr"><%= jpvo.getCompany_location() %></div>
          </div>
          <div class="apply_container">
            <%= jpvo.getJob_posting_content() %>
          </div>
        </section>
        <section>
            <a href="jobApply.do?job_posting_no=<%= jpvo.getJob_posting_no() %>" class="apply_button" onclick="">지원하기</a>
        </section>
      </div>
    </div>
    <div class="apply_modal">
      <div class="modal_content">
        <div class="modal_content_title">
          지원하기
        </div>
        <div class="modal_ment">이력서를 선택해주세요.</div>
        <div class="modal_curriculum">
          <select id="resume_select" onchange="resumeDetail(this.value)">
          <!-- 이력서 조회 -->
          <%
          	int resume_default = rList.get(0).getResume_no(); 
          	for(ResumeVO rvo : rList) {

          %>
          	<option value="<%= rvo.getResume_no() %>"><%= rvo.getResume_title() %></option>
          <%	
          	}
          %>
          </select>
        </div>
          <script src="<%= request.getContextPath() %>/js/jquery-3.7.1.js"></script> 
          <script>
          	function resumeDetail(value) {
          		$.ajax({
          			url: "getresume.do",
          			type: "get",
          			data: "resume_no=" + value,
          			dataType: "json",
          			success: function(data){
          				console.log(data);
           				if(data != null) {
							$("#resume_tenure").html(data.resume_tenure_start + " ~ " + data.resume_tenure_end);
							$("#resume_job").html(data.resume_job);
							$("#resume_area").html(data.resume_area);
							$("#resume_salary").html(data.resume_salary);
							$("#resume_no").val(data.resume_no);
							// input 객체를 찾아서 input의 value를 인자로 받은 value의 값으로 바꿈
							
						}
          			}
          		});
          	}
          	
          	$(document).ready(function(){
          		resumeDetail(<%= resume_default %>)          		
          	});
          </script>
        <div class="modal_company">
          <div class="modal_company_container">
            <div class="modal_company_container_first">
              <div class="item_first">경력</div>
              <div class="item_second">희망직무</div>
            </div>
            <div class="modal_company_container_second">
              <div class="item_first" id="resume_tenure"></div>
              <div class="item_second" id="resume_job"></div>
            </div>
          </div>
          <div class="modal_company_container">
            <div class="modal_company_container_first">
              <div class="item_first">희망지역</div>
              <div class="item_second">희망연봉</div>
            </div>
            <div class="modal_company_container_second">
              <div class="item_first" id="resume_area"></div>
              <div class="item_second" id="resume_salary"></div>
            </div>
          </div>
        </div>
        <form action="<%= request.getContextPath() %>/jobPosting/jobApply.do" method="post">
        	<input type="hidden" name="job_posting_no" value="<%= jpvo.getJob_posting_no() %>">
        	<input type="hidden" name="company_no" value="<%= jpvo.getCompany_no() %>">
        	<input type="hidden" id="resume_no" name="resume_no" value="">
          <div class="apply_detail_button">
            <input type="submit" value="지원" class="modal_apply" onclick="successAlert(event)">
            <input type="reset" value="취소" class="modal_cancle" onclick="location.href='<%= request.getContextPath() %>/jobPosting/jobView.do?job_posting_no=<%= jpvo.getJob_posting_no()  %>'">
          </div>
        </form>
        <script>
	        function successAlert(event) {
	            event.preventDefault(); // 기본 폼 제출 방지
	            Swal.fire({
	              icon: "success",
	              title: "지원이 완료되었습니다.",
	              showConfirmButton: false,
	              timer: 1500,
	              width: '400px'
	            }).then(() => {
	              // 알림 후 폼 제출
	              event.target.form.submit();
	            });
	          }
        </script>
      </div>
    </div>
  </main>

<%@ include file="../include/footer.jsp" %>