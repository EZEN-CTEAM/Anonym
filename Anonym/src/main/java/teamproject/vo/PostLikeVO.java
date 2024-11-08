package teamproject.vo;

// 게시글 좋아요
public class PostLikeVO 
{

	private String post_like_no;                // 번호 
	private String post_like_state;             // 상태
	private String post_like_registration_date; // 등록일
	private String post_like_registration_ip;   // 등록 ip
	private String post_like_registration_user; // 등록회원
	private String post_like_modify_date;       // 수정일
	private String post_like_modify_ip;         // 수정 ip
	private String post_like_modify_user;       // 수정회원
	private String post_no;                     // 게시물번호
	private String user_no;                     // 회원번호
	
	public String getPost_like_no()                { return post_like_no;                }
	public String getPost_like_state()             { return post_like_state;             }
	public String getPost_like_registration_date() { return post_like_registration_date; }
	public String getPost_like_registration_ip()   { return post_like_registration_ip;   }
	public String getPost_like_registration_user() { return post_like_registration_user; }
	public String getPost_like_modify_date()       { return post_like_modify_date;       }
	public String getPost_like_modify_ip()         { return post_like_modify_ip;         }
	public String getPost_like_modify_user()       { return post_like_modify_user;       }
	public String getPost_no()                     { return post_no;                     }
	public String getUser_no()                     { return user_no;                     }
	
	public void setPost_like_no(String post_like_no)                           { this.post_like_no = post_like_no;                                   }
	public void setPost_like_state(String post_like_state)                     { this.post_like_state = post_like_state;                             }
	public void setPost_like_registration_date(String post_like_registration_date) { this.post_like_registration_date = post_like_registration_date; }
	public void setPost_like_registration_ip(String post_like_registration_ip) { this.post_like_registration_ip = post_like_registration_ip;         }
	public void setPost_like_registration_user(String post_like_registration_user) { this.post_like_registration_user = post_like_registration_user; }
	public void setPost_like_modify_date(String post_like_modify_date)         { this.post_like_modify_date = post_like_modify_date;                 }
	public void setPost_like_modify_ip(String post_like_modify_ip)             { this.post_like_modify_ip = post_like_modify_ip;                     }
	public void setPost_like_modify_user(String post_like_modify_user)         { this.post_like_modify_user = post_like_modify_user;                 }
	public void setPost_no(String post_no)                                     { this.post_no = post_no; }
	public void setUser_no(String user_no)                                     { this.user_no = user_no; }
	
}
