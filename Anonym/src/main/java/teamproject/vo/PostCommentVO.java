package teamproject.vo;
// 게시글 댓글
public class PostCommentVO 
{
	
	private String post_comment_no;                // 댓글번호
	private String post_comment_content;           // 댓글내용
	private String post_comment_parent_no;         // 부모댓글번호
	private String post_comment_registration_date; // 등록일
	private String post_comment_registration_ip;   // 등록 ip
	private String post_comment_registration_user; // 등록회원
	private String post_comment_modify_date;       // 수정일
	private String post_comment_modify_ip;         // 수정 ip
	private String post_comment_modify_user;       // 수정회원
	private String post_comment_state;             // 댓글상태
	private String post_no;                        // 게시물번호
	private String user_no;                        // 회원번호
	/* 댓글 개수 추가 */
	private int pccount;                           // 댓글 개수
	/* 회원 아이디 추가 */
	private String user_id;                        // 회원 아이디
	private String user_company;                   // 회사명    
	
	public String getPost_comment_no()                { return post_comment_no;                }
	public String getPost_comment_content()           { return post_comment_content;           }
	public String getPost_comment_parent_no()         { return post_comment_parent_no;         }
	public String getPost_comment_registration_date() { return post_comment_registration_date; }
	public String getPost_comment_registration_ip()   { return post_comment_registration_ip;   }
	public String getPost_comment_registration_user() { return post_comment_registration_user; }
	public String getPost_comment_modify_date()       { return post_comment_modify_date;       }
	public String getPost_comment_modify_ip()         { return post_comment_modify_ip;         }
	public String getPost_comment_modify_user()       { return post_comment_modify_user;       }
	public String getPost_comment_state()             { return post_comment_state;             }
	public String getPost_no()                        { return post_no;                        }
	public String getUser_no()                        { return user_no;                        }
	/* 댓글 개수 추가 */
	public int getPccount()                           { return pccount;                        }
	/* 회원 아이디 추가 */
	public String getUser_id()                        { return user_id;                        }
	public String getUser_company()                   { return user_company;                   }
	
	public void setPost_comment_no(String post_comment_no)                           { this.post_comment_no = post_comment_no;                                   }
	public void setPost_comment_content(String post_comment_content)                 { this.post_comment_content = post_comment_content;                         }
	public void setPost_comment_parent_no(String post_comment_parent_no)             { this.post_comment_parent_no = post_comment_parent_no;                     }
	public void setPost_comment_registration_date(String post_comment_registration_date) { this.post_comment_registration_date = post_comment_registration_date; }
	public void setPost_comment_registration_ip(String post_comment_registration_ip) { this.post_comment_registration_ip = post_comment_registration_ip;         }
	public void setPost_comment_registration_user(String post_comment_registration_user) { this.post_comment_registration_user = post_comment_registration_user; }
	public void setPost_comment_modify_date(String post_comment_modify_date)         { this.post_comment_modify_date = post_comment_modify_date;                 }
	public void setPost_comment_modify_ip(String post_comment_modify_ip)             { this.post_comment_modify_ip = post_comment_modify_ip;                     }
	public void setPost_comment_modify_user(String post_comment_modify_user)         { this.post_comment_modify_user = post_comment_modify_user;                 }
	public void setPost_comment_state(String post_comment_state)                     { this.post_comment_state = post_comment_state;                             }
	public void setPost_no(String post_no)                                           { this.post_no = post_no;                                                   }
	public void setUser_no(String user_no)                                           { this.user_no = user_no;                                                   }
	/* 댓글 개수 추가 */
	public void setPccount(int pccount)                                              { this.pccount = pccount;                                                   } 
	/* 회원 아이디 추가 */
	public void setUser_id(String user_id)                                           { this.user_id = user_id;                                                   }
	public void setUser_company(String user_company)                                 { this.user_company = user_company;                                         }
}
