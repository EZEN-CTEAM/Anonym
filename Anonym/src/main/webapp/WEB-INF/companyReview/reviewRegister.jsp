<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/c_review_2.css" />
<%@ include file="../include/companyInfo.jsp" %>

            <div class="main-container">
                <section class="review-form-section">
                    <div class="review-form">
                        <h2>리뷰 등록</h2>
                    
                        <form action="reviewRegister.do" method="POST">
                            <input type="hidden" name="user_no" value="<%= loginUser.getUser_no() %>">
							<input type="hidden" name="cno" value="<%= cno %>">
                            <!-- 제목 입력 -->
                            <div class="form-group">
                                <label for="review-title">제목</label>
                                <input type="text" id="review-title" name="post_title" placeholder="리뷰 제목을 입력하세요" required />
                            </div>
							<div class="form-group st">
							    <label>커리어 향상</label>
							    <div class="stars">
<!-- 							    	<input id="career-5" name="career" type="radio" value="5"/><label for="career-5">★</label>
							    	<input id="career-4" name="career" type="radio" value="4"/><label for="career-4">★</label>
							    	<input id="career-3" name="career" type="radio" value="3"/><label for="career-3">★</label>
							    	<input id="career-2" name="career" type="radio" value="2"/><label for="career-2">★</label>
							    	<input id="career-1" name="career" type="radio" value="1"/><label for="career-1">★</label> -->
 						            <input type="radio" id="career-5" name="career" value="5"/>
						            <label for="career-5"><i class="fas fa-star"></i></label>
						            <input type="radio" id="career-4" name="career" value="4"/>
						            <label for="career-4"><i class="fas fa-star"></i></label>
						            <input type="radio" id="career-3" name="career" value="3"/>
						            <label for="career-3"><i class="fas fa-star"></i></label>
						            <input type="radio" id="career-2" name="career" value="2"/>
						            <label for="career-2"><i class="fas fa-star"></i></label>
						            <input type="radio" id="career-1" name="career" value="1"/>
						            <label for="career-1"><i class="fas fa-star"></i></label> 
							    </div>
							</div>
							<div class="form-group st">
							    <label>업무와 삶의 균형</label>
							    <div class="stars">
						            <input type="radio" id="balance-5" name="balance" value="5"/>
						            <label for="balance-5"><i class="fas fa-star"></i></label>
						            <input type="radio" id="balance-4" name="balance" value="4"/>
						            <label for="balance-4"><i class="fas fa-star"></i></label>
						            <input type="radio" id="balance-3" name="balance" value="3"/>
						            <label for="balance-3"><i class="fas fa-star"></i></label>
						            <input type="radio" id="balance-2" name="balance" value="2"/>
						            <label for="balance-2"><i class="fas fa-star"></i></label>
						            <input type="radio" id="balance-1" name="balance" value="1"/>
						            <label for="balance-1"><i class="fas fa-star"></i></label>
							    </div>
							</div>
							<div class="form-group st">
							    <label>급여 및 복지</label>
							    <div class="stars">
						            <input type="radio" id="salary-5" name="salary" value="5"/>
						            <label for="salary-5"><i class="fas fa-star"></i></label>
						            <input type="radio" id="salary-4" name="salary" value="4"/>
						            <label for="salary-4"><i class="fas fa-star"></i></label>
						            <input type="radio" id="salary-3" name="salary" value="3"/>
						            <label for="salary-3"><i class="fas fa-star"></i></label>
						            <input type="radio" id="salary-2" name="salary" value="2"/>
						            <label for="salary-2"><i class="fas fa-star"></i></label>
						            <input type="radio" id="salary-1" name="salary" value="1"/>
						            <label for="salary-1"><i class="fas fa-star"></i></label>
							    </div>
							</div>
							<div class="form-group st">
							    <label>사내 문화</label>
							    <div class="stars">
						            <input type="radio" id="culture-5" name="culture" value="5"/>
						            <label for="culture-5"><i class="fas fa-star"></i></label>
						            <input type="radio" id="culture-4" name="culture" value="4"/>
						            <label for="culture-4"><i class="fas fa-star"></i></label>
						            <input type="radio" id="culture-3" name="culture" value="3"/>
						            <label for="culture-3"><i class="fas fa-star"></i></label>
						            <input type="radio" id="culture-2" name="culture" value="2"/>
						            <label for="culture-2"><i class="fas fa-star"></i></label>
						            <input type="radio" id="culture-1" name="culture" value="1"/>
						            <label for="culture-1"><i class="fas fa-star"></i></label>
							    </div>
							</div>
							<div class="form-group st">
							    <label>경영진</label>
							    <div class="stars">
						            <input type="radio" id="executive-5" name="management" value="5"/>
						            <label for="executive-5"><i class="fas fa-star"></i></label>
						            <input type="radio" id="executive-4" name="management" value="4"/>
						            <label for="executive-4"><i class="fas fa-star"></i></label>
						            <input type="radio" id="executive-3" name="management" value="3"/>
						            <label for="executive-3"><i class="fas fa-star"></i></label>
						            <input type="radio" id="executive-2" name="management" value="2"/>
						            <label for="executive-2"><i class="fas fa-star"></i></label>
						            <input type="radio" id="executive-1" name="management" value="1"/>
						            <label for="executive-1"><i class="fas fa-star"></i></label>
							    </div>
							</div>
							
                            <!-- 장점 입력 -->
                            <div class="form-group">
                                <label for="advantages">장점</label>
                                <textarea id="advantages" name="good" placeholder="장점을 작성하세요" required></textarea>
                            </div>
                    
                            <!-- 단점 입력 -->
                            <div class="form-group">
                                <label for="disadvantages">단점</label>
                                <textarea id="disadvantages" name="bad" placeholder="단점을 작성하세요" required></textarea>
                            </div>
                    
                            <button type="submit" class="submit-btn">리뷰 등록</button>
                        </form>
                    </div>
                </section>

<%@ include file="../include/aside.jsp" %>
<%@ include file="../include/footer.jsp" %>
