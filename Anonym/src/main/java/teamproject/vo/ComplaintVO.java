package teamproject.vo;

//게시글 신고
public class ComplaintVO 
{

	private String post_complaint_no;                // 번호
	private String post_complaint_reason;            // 신고사유
	private String post_complaint_reason_other;      // 기타신고사유
	private String post_complaint_registration_date; // 등록일
	private String post_complaint_registration_ip;   // 등록 ip
	private String post_complaint_registration_user; // 등록회원
	private String post_complaint_modify_date;       // 수정일자
	private String post_complaint_modify_ip;         // 수정 ip
	private String post_complaint_modify_user;       // 수정회원
	private String post_no;                          // 게시물번호
	private String user_no;                          // 회원번호
	/*관리자*/
    private String post_complaint_state;
    private String post_complaint_state2;
    private String post_content;
    private String user_id;
    private String user_state;
	
   
	public String getPost_complaint_no()                { return post_complaint_no;                                                                  }
	public String getPost_complaint_reason()            { return post_complaint_reason;                                                              }
	public String getPost_complaint_reason_other()      { return post_complaint_reason_other;                                                        }
	public String getPost_complaint_registration_date() { return post_complaint_registration_date;                                                   }
	public String getPost_complaint_registration_ip()   { return post_complaint_registration_ip;                                                     }
	public String getPost_complaint_registration_user() { return post_complaint_registration_user;                                                   }
	public String getPost_complaint_modify_date()       { return post_complaint_modify_date;                                                         }
	public String getPost_complaint_modify_ip()         { return post_complaint_modify_ip;                                                           }
	public String getPost_complaint_modify_user()       { return post_complaint_modify_user;                                                         }
	public String getPost_no()                          { return post_no;                                                                            }
	public String getUser_no()                          { return user_no;                                                                            }
	/*관리자*/
	public String getPost_complaint_state() { return post_complaint_state;}
	public String getPost_complaint_state2() { return post_complaint_state2;}
	public String getPost_content() { return post_content;}
	public String getUser_id() { return user_id;}
	public String getUser_state() { return user_state;}
	
	public void setPost_complaint_no(String post_complaint_no)                               { this.post_complaint_no = post_complaint_no;                               }
	public void setPost_complaint_reason(String post_complaint_reason)                       { this.post_complaint_reason = post_complaint_reason;                       }
	public void setPost_complaint_reason_other(String post_complaint_reason_other)           { this.post_complaint_reason_other = post_complaint_reason_other;           }
	public void setPost_complaint_registration_date(String post_complaint_registration_date) { this.post_complaint_registration_date = post_complaint_registration_date; }
	public void setPost_complaint_registration_ip(String post_complaint_registration_ip)     { this.post_complaint_registration_ip = post_complaint_registration_ip;     }
	public void setPost_complaint_registration_user(String post_complaint_registration_user) { this.post_complaint_registration_user = post_complaint_registration_user; }
	public void setPost_complaint_modify_date(String post_complaint_modify_date)             { this.post_complaint_modify_date = post_complaint_modify_date;             }
	public void setPost_complaint_modify_ip(String post_complaint_modify_ip)                 { this.post_complaint_modify_ip = post_complaint_modify_ip;                 }
	public void setPost_complaint_modify_user(String post_complaint_modify_user)             { this.post_complaint_modify_user = post_complaint_modify_user;             }
	public void setPost_no(String post_no)                                                   { this.post_no = post_no;                                                   }
	public void setUser_no(String user_no)                                                   { this.user_no = user_no;                                                   }
	/*관리자*/
	public void setPost_complaint_state(String post_complaint_state)  {this.post_complaint_state = post_complaint_state;}
	public void setPost_complaint_state2(String post_complaint_state2)  {this.post_complaint_state2 = post_complaint_state2;}
	public void setPost_content(String post_content) { this.post_content = post_content;}
	public void setUser_id(String user_id) { this.user_id = user_id;}
	public void setUser_state(String user_state) { this.user_state = user_state;}
	
}
