package teamproject.vo;

// 별점
public class PostStarVO 
{

	private String post_review_no;                // 리뷰번호
	private int post_review_starrating;           // 별점
	private int post_review_career;               // 커리어향상
	private int post_review_balance;              // 워라벨
	private int post_review_salary;               // 급여복지
	private int post_review_culture;              // 사내문화
	private int post_review_management;           // 경영진
	private String post_review_registration_date; // 등록일
	private String post_review_registration_ip;   // 등록 ip
	private String post_review_registration_user; // 등록회원
	private String post_review_modify_date;       // 수정일
	private String post_review_modify_ip;         // 수정 ip
	private String post_review_modify_user;       // 수정회원
	private String company_no;                    // 회사번호
	private String post_no;                       // 게시물번호
	
	public String getPost_review_no()          { return post_review_no;                      }
	public int getPost_review_starrating()     { return post_review_starrating;              }
	public int getPost_review_career()         { return post_review_career;                  }
	public int getPost_review_balance()        { return post_review_balance;                 }
	public int getPost_review_salary()         { return post_review_salary;                  }
	public int getPost_review_culture()        { return post_review_culture;                 }
	public int getPost_review_management()     { return post_review_management;              }
	public String getPost_review_registration_date() { return post_review_registration_date; }
	public String getPost_review_registration_ip()   { return post_review_registration_ip;   }
	public String getPost_review_registration_user() { return post_review_registration_user; }
	public String getPost_review_modify_date() { return post_review_modify_date;             }
	public String getPost_review_modify_ip()   { return post_review_modify_ip;               }
	public String getPost_review_modify_user() { return post_review_modify_user;             }
	public String getCompany_no()              { return company_no;                          }
	public String getPost_no()                 { return post_no;                             }
	
	public void setPost_review_no(String post_review_no)                           { this.post_review_no = post_review_no;                                   }
	public void setPost_review_starrating(int post_review_starrating)              { this.post_review_starrating = post_review_starrating;                   }
	public void setPost_review_career(int post_review_career)                      { this.post_review_career = post_review_career;                           }
	public void setPost_review_balance(int post_review_balance)                    { this.post_review_balance = post_review_balance;                         }
	public void setPost_review_salary(int post_review_salary)                      { this.post_review_salary = post_review_salary;                           }
	public void setPost_review_culture(int post_review_culture)                    { this.post_review_culture = post_review_culture;                         }
	public void setPost_review_management(int post_review_management)              { this.post_review_management = post_review_management;                   }
	public void setPost_review_registration_date(String post_review_registration_date) { this.post_review_registration_date = post_review_registration_date; }
	public void setPost_review_registration_ip(String post_review_registration_ip) { this.post_review_registration_ip = post_review_registration_ip;         }
	public void setPost_review_registration_user(String post_review_registration_user) { this.post_review_registration_user = post_review_registration_user; }
	public void setPost_review_modify_date(String post_review_modify_date)         { this.post_review_modify_date = post_review_modify_date;                 }
	public void setPost_review_modify_ip(String post_review_modify_ip)             { this.post_review_modify_ip = post_review_modify_ip;                     }
	public void setPost_review_modify_user(String post_review_modify_user)         { this.post_review_modify_user = post_review_modify_user;                 }
	public void setCompany_no(String company_no)                                   { this.company_no = company_no;                                           }
	public void setPost_no(String post_no)                                         { this.post_no = post_no;                                                 }
	
}
