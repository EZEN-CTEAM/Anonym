package teamproject.vo;

// 기업 회원
public class CompanyVO 
{
	
	private int cno;             // 기업번호
	private String cid;          // 기업아이디
	private String cpw;          // 기업비밀번호
	private String clogo;        // 회사로고
	private String cname;        // 회사명
	private String clocation;    // 위치
	private String cemployee;    // 직원수
	private String cindustry;    // 기업업종
	private String canniversary; // 설립일자
	private String crdate;       // 등록일
	private String cbrcnum;      // 사업자등록번호
	private String cbrc;         // 사업자등록증
	private String cstate;       // 상태
	/* 추천 */
    private String crstate;      // 회사 추천 상태
    private int crcount;         // 회사 추천 개수
    /* 별점 */
	private int post_review_starrating;           // 별점
	private int post_review_career;               // 커리어향상
	private int post_review_balance;              // 워라벨
	private int post_review_salary;               // 급여복지
	private int post_review_culture;              // 사내문화
	private int post_review_management;           // 경영진
	/* 채용공고 */
	private int job_posting_no;
	private String job_posting_title;
	
	public int getCno()          { return cno;             }
	public String getCid()       { return cid;             }
	public String getCpw()       { return cpw;             }
	public String getClogo()     { return clogo;           }
	public String getCname()     { return cname;           }
	public String getClocation() { return clocation;       }
	public String getCemployee() { return cemployee;       }
	public String getCindustry() { return cindustry;       }
	public String getCanniversary() { return canniversary; }
	public String getCrdate()    { return crdate;          }
	public String getCbrcnum()   { return cbrcnum;         }
	public String getCbrc()      { return cbrc;            }
	public String getCstate()    { return cstate;          }
	/* 추천 */
    public String getCrstate()   { return crstate;         }
    public int getCrcount()      { return crcount;         }
    /* 별점 */
	public int getPost_review_starrating()     { return post_review_starrating;              }
	public int getPost_review_career()         { return post_review_career;                  }
	public int getPost_review_balance()        { return post_review_balance;                 }
	public int getPost_review_salary()         { return post_review_salary;                  }
	public int getPost_review_culture()        { return post_review_culture;                 }
	public int getPost_review_management()     { return post_review_management;              }
	/* 채용공고 */
	public int getJob_posting_no() { return job_posting_no; }
	public String getJob_posting_title() { return job_posting_title; }
	

	public void setCno(int cno)                { this.cno = cno;                         }
	public void setCid(String cid)             { this.cid = cid;                         }
	public void setCpw(String cpw)             { this.cpw = cpw;                         }
	public void setClogo(String clogo)         { this.clogo = clogo;                     }
	public void setCname(String cname)         { this.cname = cname;                     }
	public void setClocation(String clocation) { this.clocation = clocation;             }
	public void setCemployee(String cemployee) { this.cemployee = cemployee;             }
	public void setCindustry(String cindustry) { this.cindustry = cindustry;             }
	public void setCanniversary(String canniversary) { this.canniversary = canniversary; }
	public void setCrdate(String crdate)       { this.crdate = crdate;                   }
	public void setCbrcnum(String cbrcnum)     { this.cbrcnum = cbrcnum;                 }
	public void setCbrc(String cbrc)           { this.cbrc = cbrc;                       }
	public void setCstate(String cstate)       { this.cstate = cstate;                   }
	/* 추천 */
	public void setCrstate(String crstate)     { this.crstate = crstate;                 }
	public void setCrcount(int crcount)        { this.crcount = crcount;                 }
	/* 별점 */
	public void setPost_review_starrating(int post_review_starrating)              { this.post_review_starrating = post_review_starrating;                   }
	public void setPost_review_career(int post_review_career)                      { this.post_review_career = post_review_career;                           }
	public void setPost_review_balance(int post_review_balance)                    { this.post_review_balance = post_review_balance;                         }
	public void setPost_review_salary(int post_review_salary)                      { this.post_review_salary = post_review_salary;                           }
	public void setPost_review_culture(int post_review_culture)                    { this.post_review_culture = post_review_culture;                         }
	public void setPost_review_management(int post_review_management)              { this.post_review_management = post_review_management;                   }
	/* 채용공고 */
	public void setJob_posting_no(int job_posting_no) { this.job_posting_no = job_posting_no; }
	public void setJob_posting_title(String job_posting_title) { this.job_posting_title = job_posting_title; }

	@Override
	public String toString() {
		return "CompanyVO [cno=" + cno + ", cid=" + cid + ", cpw=" + cpw + ", clogo=" + clogo + ", cname=" + cname
				+ ", clocation=" + clocation + ", cemployee=" + cemployee + ", cindustry=" + cindustry
				+ ", canniversary=" + canniversary + ", crdate=" + crdate + ", cbrcnum=" + cbrcnum + ", cbrc=" + cbrc
				+ ", cstate=" + cstate + ", crstate=" + crstate + ", crcount=" + crcount + ", post_review_starrating="
				+ post_review_starrating + ", post_review_career=" + post_review_career + ", post_review_balance="
				+ post_review_balance + ", post_review_salary=" + post_review_salary + ", post_review_culture="
				+ post_review_culture + ", post_review_management=" + post_review_management + "]";
	}
}