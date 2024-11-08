<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="teamproject.vo.*" %>
<%@ page import="java.util.*" %> 
<%@ include file="../include/header.jsp" %>
<% 
request.setCharacterEncoding("UTF-8");
// 글목록
List<PostVO> List = (List<PostVO>)request.getAttribute("List");
// 검색어
String searchValue = (String)request.getParameter("searchValue");
if(searchValue == null || searchValue.equals("null")) searchValue = "";
// 페이징
PagingUtil paging = (PagingUtil)request.getAttribute("paging");
int nowPage = paging.getNowPage();
%>  
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/freeboard_list.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />

        <!-- 메인 -->
        <main>
            <div class="main-container">
                <section class="free_list-section">
                    <div class="adv">
                   		<img src="<%= request.getContextPath() %>/image/banner.jpg" style="height:100%;">
                        <!-- <img src="https://d2u3dcdbebyaiu.cloudfront.net/img/web_banner/web_banner_kr_1727835628.png" style="height:100%;"> -->
                    </div>
                    <form action="freeList.do" method="get">
	                    <div class="search-container">
	                        <i class="fas fa-search search-icon"></i>
	                        <input type="text" onkeyup="enterkey();" class="search-input" name="searchValue" value="<%= searchValue %>" placeholder="검색어를 입력하세요...">
	                    </div>
                    </form>
                    <div class="rbtn-container">
                    <%
                    if(loginUser != null)
			        {
                    	%>
                    	<button class="rbtn" onclick="location.href='freeRegister.do'">글쓰기</button>
                    	<%
			        }
                    %>
                    </div>
                    <div class="free_list">
                    <%
					int total = List.size();
				 	if(total == 0)
					{
							%>
							<div class="card">
								등록된 글이 없습니다.
                            </div>
							<%
					}
					for(PostVO vo : List)
					{
					%>
                        <div class="card">
                            <a href="freeView.do?pno=<%= vo.getPost_no() %>">
                            <h2><%= vo.getPost_title() %></h2>
                            <% 
                            	String content = vo.getPost_content(); 
                            	
                            	// 이미지 안보이게
                            	while(true)
                            	{
	                            	//String imgtagStartStr = content.substring(content.indexOf("<img"));
	                            	if(content.indexOf("<img")>-1){
		                            	String imgtagStartStr = content.substring(content.indexOf("<img"));
		                            	String imgtagStr = imgtagStartStr.substring(0,imgtagStartStr.indexOf(">")+1);
		                            	content = content.replace(imgtagStr,"");
	                            	}else
	                            	{
	                            		break;
	                            	}
                            	}
                            %>
                            <div style="height: 56px;  overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                            	<%= content %>
                            </div>
                            <div class="meta">
                                <span>
                                <%
                                if(vo.getUser_company() != null && !vo.getUser_company().equals(""))
                                {
                                	%>
                                		<%= vo.getUser_company() %>
                                	<%
                                }else
                                {
                                	%>
	                                	무직
                                	<%
                                }
                                %>
                                	 · <%= vo.getUser_id() %> · <%= vo.getPost_registration_date() %>
                                </span>
                                <span>조회 <%= vo.getPost_hit() %> · 댓글 <%= vo.getPccount() %></span>
                            </div>
                            </a>
                        </div>
                
                    <%
                    }
                    %>
                    </div>
                    <div class="pagination">
					<%
					if(paging.getStartPage() > 1)
					{
						// 시작페이지가 1보다 큰 경우 이전 페이지 존재
						%>
						<!-- 클릭시 현재 페이지의 시작 페이지 번호 이전 페이지로 이동(13->10)-->
						<a href="freeList.do?nowPage=<%= paging.getStartPage() - 1 %>&searchValue=<%= searchValue %>">&lt;</a>
						<%
					}
					for(int i = paging.getStartPage(); i <= paging.getEndPage(); i++)
					{
						if(i == nowPage)
						{
							%>
							<a class="active"><%= i %></a>
							<%
						}else
						{
							%>
							<a href="freeList.do?nowPage=<%= i %>&searchValue=<%= searchValue %>"><%= i %></a>
							<%
						}
					}
					if(paging.getLastPage() > paging.getEndPage())
					{
						%>
						<!-- 클릭시 현재 페이지의 마지막 페이지 번호 다음 페이지로 이동(13->20)-->
						<a href="freeList.do?nowPage=<%= paging.getEndPage() + 1 %>&searchValue=<%= searchValue %>">&gt;</a>
						<%
					}
					%>
                    </div>
                </section>
            </div>
        </main>

<%@ include file="../include/footer.jsp" %>
