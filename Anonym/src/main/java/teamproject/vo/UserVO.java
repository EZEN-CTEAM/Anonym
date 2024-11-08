package teamproject.vo;

// 개인 회원(관리자 포함) 
public class UserVO 
{
	
	private int user_no;                   // 회원번호
	private String user_id;                // 아이디
	private String user_pw;                // 비밀번호
	private String user_nickname;          // 닉네임
	private String user_employment;        // 재직유무
	private String user_company;           // 회사명
	private String user_registration_date; // 등록일
	private String user_registration_ip;   // 등록 ip
	private String user_registration_user; // 등록회원
	private String ustatuser_modify_date;  // 수정일
	private String user_modify_ip;         // 수정 ip
	private String user_modify_user;       // 수정회원
	private String user_type;              // 회원구분
	private String user_state;             // 상태
	
	public int getUser_no()                   { return user_no;                }
	public String getUser_id()                { return user_id;                }
	public String getUser_pw()                { return user_pw;                }
	public String getUser_nickname()          { return user_nickname;          }
	public String getUser_employment()        { return user_employment;        }
	public String getUser_company()           { return user_company;           }
	public String getUser_registration_date() { return user_registration_date; }
	public String getUser_registration_ip()   { return user_registration_ip;   }
	public String getUser_registration_user() { return user_registration_user; }
	public String getUstatuser_modify_date()  { return ustatuser_modify_date;  }
	public String getUser_modify_ip()         { return user_modify_ip;         }
	public String getUser_modify_user()       { return user_modify_user;       }
	public String getUser_type()              { return user_type;              }
	public String getUser_state()             { return user_state;             }
	
	public void setUser_no(int user_no)                                  { this.user_no = user_no;                               }
	public void setUser_id(String user_id)                               { this.user_id = user_id;                               }
	public void setUser_pw(String user_pw)                               { this.user_pw = user_pw;                               }
	public void setUser_nickname(String user_nickname)                   { this.user_nickname = user_nickname;                   }
	public void setUser_employment(String user_employment)               { this.user_employment = user_employment;               }
	public void setUser_company(String user_company)                     { this.user_company = user_company;                     }
	public void setUser_registration_date(String user_registration_date) { this.user_registration_date = user_registration_date; }
	public void setUser_registration_ip(String user_registration_ip)     { this.user_registration_ip = user_registration_ip;     }
	public void setUser_registration_user(String user_registration_user) { this.user_registration_user = user_registration_user; }
	public void setUstatuser_modify_date(String ustatuser_modify_date)   { this.ustatuser_modify_date = ustatuser_modify_date;   }
	public void setUser_modify_ip(String user_modify_ip)                 { this.user_modify_ip = user_modify_ip;                 }
	public void setUser_modify_user(String user_modify_user)             { this.user_modify_user = user_modify_user;             }
	public void setUser_type(String user_type)                           { this.user_type = user_type;                           }
	public void setUser_state(String user_state)                         { this.user_state = user_state;                         }
	
}
