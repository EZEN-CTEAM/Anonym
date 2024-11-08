<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/c_review1.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
<style>
.search-container 
{
    position: relative;
    width: 100%;
    max-width: 700px;
}

.search-input 
{
    width: 700px;
    padding: 15px 20px 15px 50px;
    border-radius: 30px;
    border: 1px solid #ddd;
    outline: none;
    font-size: 1rem;
    transition: all 0.3s;
}

.search-input:focus 
{
    border-color: #ff5252;
    box-shadow: 0 0 8px rgba(255, 82, 82, 0.5);
}

.search-icon 
{
    position: absolute;
    left: 15px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 1.5rem;
    color: #aaa;
    transition: color 0.3s; 
}

#result 
{
    margin-top: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    max-height: 200px;
    overflow-y: auto;
}

#result ul 
{
    list-style-type: none;
    padding: 0;
    margin: 0;
}

#result li 
{
    padding: 5px 10px;
    cursor: pointer;
    text-align: left;
}

#result li:hover 
{
    background-color: #f0f0f0;
}
</style>
        <!-- 메인 컨텐츠 -->
        <main>
            <div class="main-container">
                <section class="intro">
                    <h1>진짜 현직자들의 회사 리뷰</h1>
                    <p>실제 직원들이 평가하는 회사는 어떤지 확인 해보세요.</p>
                    <div class="search-container">
                        <i class="fas fa-search search-icon"></i>
                        <input type="text" class="search-input" id="search-box" placeholder="회사를 검색하세요">
                        <div id="result"></div>
                    </div>
                </section>
                
                <section style="width: 1160px;">
                    <div class="image-review-container">
                        <div class="review">
                            <h3>내 회사 리뷰하기</h3>
                            <p>회원님의 리뷰는 구직자들이 회사를<br> 검토하는데 도움이 됩니다.</p>
                            <button class="review-button" onclick="location.href='communityRegister.do'">리뷰 쓰기</button>
                        </div>
                        <div class="image-container">
                            <img src="<%= request.getContextPath() %>/image/bg-company-main.png" alt="회사 리뷰 이미지" />
                        </div>
                    </div>
                </section>
                
                <!-- 인기 게시판 목록 -->
                <section class="board-container">
                    <h2>인기 회사</h2>
                    <div class="boards">
                        <div  class="board">
                            <img src="<%= request.getContextPath() %>/image/potato.jpeg" alt="회사 이미지" />
                            <div class="">
                                <h3><a href="companyInfo.do?cno=1">회사명</a></h3>
                                <p>평점: ⭐⭐⭐⭐☆</p>
                            </div>
                        </div>
                        <div  class="board">
                            <img src="<%= request.getContextPath() %>/image/potato.jpeg" alt="회사 이미지" />
                            <div class="">
                                <h3><a href="#">회사명</a></h3>
                                <p>평점: ⭐⭐⭐⭐☆</p>
                            </div>
                        </div>
                        <div  class="board">
                            <img src="<%= request.getContextPath() %>/image/potato.jpeg" alt="회사 이미지" />
                            <div class="">
                                <h3><a href="#">회사명</a></h3>
                                <p>평점: ⭐⭐⭐⭐☆</p>
                            </div>
                        </div>
                        <div  class="board">
                            <img src="<%= request.getContextPath() %>/image/potato.jpeg" alt="회사 이미지" />
                            <div class="">
                                <h3><a href="#">회사명</a></h3>
                                <p>평점: ⭐⭐⭐⭐☆</p>
                            </div>
                        </div>
                        <div  class="board">
                            <img src="<%= request.getContextPath() %>/image/potato.jpeg" alt="회사 이미지" />
                            <div class="">
                                <h3><a href="#">회사명</a></h3>
                                <p>평점: ⭐⭐⭐⭐☆</p>
                            </div>
                        </div>
                        <div  class="board">
                            <img src="<%= request.getContextPath() %>/image/potato.jpeg" alt="이미지" />
                            <div class="">
                                <h3><a href="#">회사명</a></h3>
                                <p>평점: ⭐⭐⭐⭐☆</p>
                            </div>
                        </div>
                        <div  class="board">
                            <img src="<%= request.getContextPath() %>/image/potato.jpeg" alt="회사 이미지" />
                            <div class="">
                                <h3><a href="#">회사명</a></h3>
                                <p>평점: ⭐⭐⭐⭐☆</p>
                            </div>
                        </div>
                        <div  class="board">
                            <img src="<%= request.getContextPath() %>/image/potato.jpeg" alt="회사 이미지" />
                            <div class="">
                                <h3><a href="#">회사명</a></h3>
                                <p>평점: ⭐⭐⭐⭐☆</p>
                            </div>
                        </div>
                        <div  class="board">
                            <img src="<%= request.getContextPath() %>/image/potato.jpeg" alt="회사 이미지" />
                            <div class="">
                                <h3><a href="#">회사명</a></h3>
                                <p>평점: ⭐⭐⭐⭐☆</p>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </main>

<%@ include file="../include/footer.jsp" %>

<script>
$(document).ready(function() {
    $('#search-box').on('keyup', function() {
        var searchValue = $(this).val();

        // 검색어가 비어 있지 않은 경우에만 AJAX 요청
        if (searchValue.trim() !== '') {
            // AJAX 요청
            $.ajax({
                url: '<%= request.getContextPath() %>/companyReview/companySearch.do', 
                method: 'GET',
                data: { searchValue: searchValue },
                dataType: 'json',
                success: function(response) {
                    var resultHtml = '';

                    if (response.message) {
                        resultHtml = response.message;
                    } else {
                        resultHtml = '<ul>';
                        $.each(response, function(index, item) {
                            resultHtml += '<li>' + item.company + '</li>';
                        });
                        resultHtml += '</ul>';
                    }

                    $('#result').html(resultHtml);
                },
                error: function(xhr, status, error) {
                    console.error('AJAX 오류 발생:', error);
                    $('#result').html('오류가 발생했습니다.');
                }
            });
        } else {
            // 검색어가 비어 있으면 결과 영역을 비움
            $('#result').empty();
        }
    });
});
</script>
