<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/c_review_2.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
<%@ include file="../include/companyInfo.jsp" %>

                <section class="company-community-section">
                    <div class="company-community">
                    	<form action="communityList.do" method="get">
                    		<input type="hidden" name="cno" value="<%= cno %>">
	                        <div class="search-container">
	                            <input type="text" onkeyup="enterkey();" name="searchValue" value="<%= searchValue %>" placeholder="연봉, 면접 등 키워드로 검색해보세요.">
	                        </div>
                    	</form>
                        
                        <!-- <div class="tabs">
                            <button class="active">인기순</button>
                            <button>최신순</button>
                        </div> -->
						<div class="rbtn-container">
		                <%
	                    if(loginUser != null)
				        {
	                    	%>
	                    		<button class="rbtn" onclick="location.href='communityRegister.do?cno=<%= cno %>'">글쓰기</button>
	                    	<%
			        	}
                    	%>
                    	</div>
                        
                        <div class="post-list">
                            <!-- <h1>인천환경공단 게시글</h1> -->
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
							for(PostVO pvo : List)
							{
								%>
	                            <div class="post-item">
	                                <a href="communityView.do?pno=<%= pvo.getPost_no() %>&cno=<%= cno %>">
	                                <h2><%= pvo.getPost_title() %></h2>
	                                <% 
	                                String content = pvo.getPost_content();
	                                
	                             	// 이미지 안보이게
                            		while(true)
                            		{
		                            	//String imgtagStartStr = content.substring(content.indexOf("<img"));
		                            	if(content.indexOf("<img")>-1)
		                            	{
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
		                                <span><%= pvo.getUser_id() %> · <%= pvo.getPost_registration_date() %></span>
		                                <span>조회 <%= pvo.getPost_hit() %> · 댓글 <%= pvo.getPccount() %></span>
		                            </div>
	                                </a>
	                            </div>
							    <%
		                    }
		                    %>
                            <div class="pagination">
								<%
								if(paging.getStartPage() > 1)
								{
									// 시작페이지가 1보다 큰 경우 이전 페이지 존재
									%>
									<!-- 클릭시 현재 페이지의 시작 페이지 번호 이전 페이지로 이동(13->10)-->
									<a href="communityList.do?cno=<%= cno %>&nowPage=<%= paging.getStartPage() - 1 %>&searchValue=<%= searchValue %>">&lt;</a>
									<%
								}
								for(int i = paging.getStartPage(); i <= paging.getEndPage(); i++)
								{
									if(i == nowPage)
									{
										%>
										<a class="active2"><%= i %></a>
										<%
									}else
									{
										%>
										<a href="communityList.do?cno=<%= cno %>&nowPage=<%= i %>&searchValue=<%= searchValue %>"><%= i %></a>
										<%
									}
								}
								if(paging.getLastPage() > paging.getEndPage())
								{
									%>
									<!-- 클릭시 현재 페이지의 마지막 페이지 번호 다음 페이지로 이동(13->20)-->
									<a href="communityList.do?cno=<%= cno %>&nowPage=<%= paging.getEndPage() + 1 %>&searchValue=<%= searchValue %>">&gt;</a>
									<%
								}
								%>
                            </div>
                            
                        </div>
                    </div>
                </section>

<%@ include file="../include/aside.jsp" %>
<%@ include file="../include/footer.jsp" %>
