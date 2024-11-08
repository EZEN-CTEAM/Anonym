package teamproject.vo;

// 게시글
public class PostVO 
{
	
	private String post_no;                // 게시물번호
	private String post_title;             // 제목
	private String post_content;           // 내용(장점)
	private String post_content2;          // 내용2(단점)
	private String post_hit;               // 조회수
	private String post_registration_date; // 등록일
	private String post_registration_ip;   // 등록 ip
	private String post_registration_user; // 등록회원
	private String post_modify_date;       // 수정일
	private String post_modify_ip;         // 수정 ip
	private String post_modify_user;       // 수정회원
	private String board_no;               // 게시판일련번호
	private String user_no;                // 회원번호
	private String post_state;             // 게시글 상태
	/* 회원 정보 추가 */
	private String user_id;                // 회원아이디
	private String user_company;           // 회사명               
	/* 댓글 개수 추가 */
	private String pccount;                // 댓글 개수
	/* 좋아요 상태 추가  */
	private String post_like_state;        // 좋아요 상태
	private String plcnt;        			// 좋아요 갯수
	/* 별점 추가 */
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
	/* 채용공고 */
	private String company_logo;
	private String company_name;
	private int company_employee;
	private String company_location;
	private String company_industry;
	private String company_anniversary;
	private String job_posting_period;
	
	public String getPost_no()                { return post_no;                }
	public String getPost_title()             { return post_title;             }
	public String getPost_content()           { return post_content;           }
	public String getPost_content2()          { return post_content2;          }
	public String getPost_hit()               { return post_hit;               }
	public String getPost_registration_date() { return post_registration_date; }
	public String getPost_registration_ip()   { return post_registration_ip;   }
	public String getPost_registration_user() { return post_registration_user; }
	public String getPost_modify_date()       { return post_modify_date;       }
	public String getPost_modify_ip()         { return post_modify_ip;         }
	public String getPost_modify_user()       { return post_modify_user;       }
	public String getBoard_no()               { return board_no;               }
	public String getUser_no()                { return user_no;                }
	public String getPost_state()             { return post_state;             }
	/* 회원 정보 추가 */
	public String getUser_id()                { return user_id;                }
	public String getUser_company()           { return user_company;           }
	/* 댓글 개수 추가 */
	public String getPccount()                { return pccount;                }
	/* 좋아요 상태 추가  */
	public String getPost_like_state()        { return post_like_state;        }
	public String getPlcnt()                  { return plcnt;                  }
	/* 별점 추가 */
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
	/* 채용공고 */
	public String getCompany_logo() 		  { return company_logo;			}
	public String getCompany_name() 		  { return company_name;			}
	public int getCompany_employee() 		  { return company_employee;		}
	public String getCompany_location() 	  { return company_location;		}
	public String getCompany_industry() 	  { return company_industry;		}
	public String getCompany_anniversary() 	  { return company_anniversary;		}
	public String getJob_posting_period() 	  { return job_posting_period;		}
	
	
	public void setPost_no(String post_no)                               { this.post_no = post_no;                               }
	public void setPost_title(String post_title)                         { this.post_title = post_title;                         }
	public void setPost_content(String post_content)                     { this.post_content = post_content;                     }
	public void setPost_content2(String post_content2)                   { this.post_content2 = post_content2;                   }
	public void setPost_hit(String post_hit)                             { this.post_hit = post_hit;                             }
	public void setPost_registration_date(String post_registration_date) { this.post_registration_date = post_registration_date; }
	public void setPost_registration_ip(String post_registration_ip)     { this.post_registration_ip = post_registration_ip;     }
	public void setPost_registration_user(String post_registration_user) { this.post_registration_user = post_registration_user; }
	public void setPost_modify_date(String post_modify_date)             { this.post_modify_date = post_modify_date;             }
	public void setPost_modify_ip(String post_modify_ip)                 { this.post_modify_ip = post_modify_ip;                 }
	public void setPost_modify_user(String post_modify_user)             { this.post_modify_user = post_modify_user;             }
	public void setBoard_no(String board_no)                             { this.board_no = board_no;                             }
	public void setUser_no(String user_no)                               { this.user_no = user_no;                               }
	public void setPost_state(String post_state)                         { this.post_state = post_state;                         }
	/* 회원 정보 추가 */
	public void setUser_id(String user_id)                               { this.user_id = user_id;                               }
	public void setUser_company(String user_company)                     { this.user_company = user_company;                     }
	/* 댓글 개수 추가 */
	public void setPccount(String pccount)                               { this.pccount = pccount;                               }
	/* 좋아요 상태 추가  */
	public void setPost_like_state(String post_like_state)               { this.post_like_state = post_like_state;               }
	public void setPlcnt(String plcnt)                                   { this.plcnt = plcnt;                                   }
	/* 별점 추가 */
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
	/* 채용공고 */
	public void setCompany_logo(String company_logo) 					 { this.post_state = company_logo;							}
	public void setCompany_name(String company_name) 					 { this.post_state = company_name;							}
	public void setCompany_employee(int company_employee) 				 { this.company_employee = company_employee;				}
	public void setCompany_location(String company_location) 			 { this.company_location = company_location;				}
	public void setCompany_industry(String company_industry) 			 { this.company_industry = company_industry;				}
	public void setCompany_anniversary(String company_anniversary) 		 { this.company_anniversary = company_anniversary;			}
	public void setJob_posting_period(String job_posting_period) 		 { this.job_posting_period = job_posting_period;			}
	
	
	
}