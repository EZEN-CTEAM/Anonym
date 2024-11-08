<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<% 
request.setCharacterEncoding("UTF-8");
PostVO pvo = (PostVO)request.getAttribute("pvo");
%>  
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/c_review_2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
<%@ include file="../include/companyInfo.jsp" %>

            <div class="main-container">
                <section class="review-form-section">
                    <div class="review-form">
                        <h2>리뷰 수정</h2>
                    
                        <form action="reviewModify.do" method="POST">
                            <input type="hidden" name="user_no" value="1">
							<input type="hidden" name="board_no" value="2">
							<input type="hidden" name="company_no" value="1">
							<input type="hidden" name="pno" value="<%= pvo.getPost_no() %>">
                    
                            <!-- 제목 입력 -->
                            <div class="form-group">
                                <label for="review-title">제목</label>
                                <input type="text" id="review-title" name="post_title" value="<%= pvo.getPost_title() %>"/>
                            </div>
							<div class="form-group st">
							    <label>커리어 향상</label>
							    <div class="stars">
 						            <input type="radio" id="career-5" name="career" value="5" <%= pvo.getPost_review_career() == 5 ? "checked" : "" %>/>
						            <label for="career-5"><i class="fas fa-star"></i></label>
						            <input type="radio" id="career-4" name="career" value="4" <%= pvo.getPost_review_career() == 4 ? "checked" : "" %>/>
						            <label for="career-4"><i class="fas fa-star"></i></label>
						            <input type="radio" id="career-3" name="career" value="3" <%= pvo.getPost_review_career() == 3 ? "checked" : "" %>/>
						            <label for="career-3"><i class="fas fa-star"></i></label>
						            <input type="radio" id="career-2" name="career" value="2" <%= pvo.getPost_review_career() == 2 ? "checked" : "" %>/>
						            <label for="career-2"><i class="fas fa-star"></i></label>
						            <input type="radio" id="career-1" name="career" value="1" <%= pvo.getPost_review_career() == 1 ? "checked" : "" %>/>
						            <label for="career-1"><i class="fas fa-star"></i></label> 
							    </div>
							</div>
							<div class="form-group st">
							    <label>업무와 삶의 균형</label>
							    <div class="stars">
						            <input type="radio" id="balance-5" name="balance" value="5" <%= pvo.getPost_review_balance() == 5 ? "checked" : "" %>/>
						            <label for="balance-5"><i class="fas fa-star"></i></label>
						            <input type="radio" id="balance-4" name="balance" value="4" <%= pvo.getPost_review_balance() == 4 ? "checked" : "" %>/>
						            <label for="balance-4"><i class="fas fa-star"></i></label>
						            <input type="radio" id="balance-3" name="balance" value="3" <%= pvo.getPost_review_balance() == 3 ? "checked" : "" %>/>
						            <label for="balance-3"><i class="fas fa-star"></i></label>
						            <input type="radio" id="balance-2" name="balance" value="2" <%= pvo.getPost_review_balance() == 2 ? "checked" : "" %>/>
						            <label for="balance-2"><i class="fas fa-star"></i></label>
						            <input type="radio" id="balance-1" name="balance" value="1" <%= pvo.getPost_review_balance() == 1 ? "checked" : "" %>/>
						            <label for="balance-1"><i class="fas fa-star"></i></label>
							    </div>
							</div>
							<div class="form-group st">
							    <label>급여 및 복지</label>
							    <div class="stars">
						            <input type="radio" id="salary-5" name="salary" value="5" <%= pvo.getPost_review_salary() == 5 ? "checked" : "" %>/>
						            <label for="salary-5"><i class="fas fa-star"></i></label>
						            <input type="radio" id="salary-4" name="salary" value="4" <%= pvo.getPost_review_salary() == 4 ? "checked" : "" %>/>
						            <label for="salary-4"><i class="fas fa-star"></i></label>
						            <input type="radio" id="salary-3" name="salary" value="3" <%= pvo.getPost_review_salary() == 3 ? "checked" : "" %>/>
						            <label for="salary-3"><i class="fas fa-star"></i></label>
						            <input type="radio" id="salary-2" name="salary" value="2" <%= pvo.getPost_review_salary() == 2 ? "checked" : "" %>/>
						            <label for="salary-2"><i class="fas fa-star"></i></label>
						            <input type="radio" id="salary-1" name="salary" value="1" <%= pvo.getPost_review_salary() == 1 ? "checked" : "" %>/>
						            <label for="salary-1"><i class="fas fa-star"></i></label>
							    </div>
							</div>
							<div class="form-group st">
							    <label>사내 문화</label>
							    <div class="stars">
						            <input type="radio" id="culture-5" name="culture" value="5" <%= pvo.getPost_review_culture() == 5 ? "checked" : "" %>/>
						            <label for="culture-5"><i class="fas fa-star"></i></label>
						            <input type="radio" id="culture-4" name="culture" value="4" <%= pvo.getPost_review_culture() == 4 ? "checked" : "" %>/>
						            <label for="culture-4"><i class="fas fa-star"></i></label>
						            <input type="radio" id="culture-3" name="culture" value="3" <%= pvo.getPost_review_culture() == 3 ? "checked" : "" %>/>
						            <label for="culture-3"><i class="fas fa-star"></i></label>
						            <input type="radio" id="culture-2" name="culture" value="2" <%= pvo.getPost_review_culture() == 2 ? "checked" : "" %>/>
						            <label for="culture-2"><i class="fas fa-star"></i></label>
						            <input type="radio" id="culture-1" name="culture" value="1" <%= pvo.getPost_review_culture() == 1 ? "checked" : "" %>/>
						            <label for="culture-1"><i class="fas fa-star"></i></label>
							    </div>
							</div>
							<div class="form-group st">
							    <label>경영진</label>
							    <div class="stars">
						            <input type="radio" id="executive-5" name="management" value="5" <%= pvo.getPost_review_management() == 5 ? "checked" : "" %>/>
						            <label for="executive-5"><i class="fas fa-star"></i></label>
						            <input type="radio" id="executive-4" name="management" value="4" <%= pvo.getPost_review_management() == 4 ? "checked" : "" %>/>
						            <label for="executive-4"><i class="fas fa-star"></i></label>
						            <input type="radio" id="executive-3" name="management" value="3" <%= pvo.getPost_review_management() == 3 ? "checked" : "" %>/>
						            <label for="executive-3"><i class="fas fa-star"></i></label>
						            <input type="radio" id="executive-2" name="management" value="2" <%= pvo.getPost_review_management() == 2 ? "checked" : "" %>/>
						            <label for="executive-2"><i class="fas fa-star"></i></label>
						            <input type="radio" id="executive-1" name="management" value="1" <%= pvo.getPost_review_management() == 1 ? "checked" : "" %>/>
						            <label for="executive-1"><i class="fas fa-star"></i></label>
							    </div>
							</div>
                    
                            <!-- 장점 입력 -->
                            <div class="form-group">
                                <label for="advantages">장점</label>
                                <textarea id="advantages" name="good"><%= pvo.getPost_content() %></textarea>
                            </div>
                    
                            <!-- 단점 입력 -->
                            <div class="form-group">
                                <label for="disadvantages">단점</label>
                                <textarea id="disadvantages" name="bad"><%= pvo.getPost_content2() %></textarea>
                            </div>
                    
                            <button type="submit" class="submit-btn">리뷰 수정</button>
                        </form>
                    </div>
                </section>

<%@ include file="../include/aside.jsp" %>
<%@ include file="../include/footer.jsp" %>
