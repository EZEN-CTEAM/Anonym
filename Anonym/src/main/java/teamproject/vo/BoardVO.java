package teamproject.vo;

// 게시판
public class BoardVO 
{
	
	private String board_no;                // 게시판일련번호
	private String board_name;              // 게시판 이름
	private String board_use_notice;        // 공지사항사용여부
	private String board_use_secret;        // 비밀글사용여부
	private String board_use_reply;         // 댓글사용여부
	private String board_use_attachment;    // 첨부파일사용여부
	private String board_user_permission;   // 사용 권한
	private String board_is_employee;       // 현직자 전용 여부
	private String board_use_star;          // 별점 사용 여부
	private String board_registration_date; // 등록일자
	private String board_registration_ip;   // 등록 ip
	private String board_registration_user; // 등록회원
	private String board_modify_date;       // 수정일자
	private String board_modify_ip;         // 수정 ip
	private String board_modify_user;       // 수정회원
	
	public String getBoard_no()                { return board_no;                }
	public String getBoard_name()              { return board_name;              }
	public String getBoard_use_notice()        { return board_use_notice;        }
	public String getBoard_use_secret()        { return board_use_secret;        }
	public String getBoard_use_reply()         { return board_use_reply;         }
	public String getBoard_use_attachment()    { return board_use_attachment;    }
	public String getBoard_user_permission()   { return board_user_permission;   }
	public String getBoard_is_employee()       { return board_is_employee;       }
	public String getBoard_use_star()          { return board_use_star;          }
	public String getBoard_registration_date() { return board_registration_date; }
	public String getBoard_registration_ip()   { return board_registration_ip;   }
	public String getBoard_registration_user() { return board_registration_user; }
	public String getBoard_modify_date()       { return board_modify_date;       }
	public String getBoard_modify_ip()         { return board_modify_ip;         }
	public String getBoard_modify_user()       { return board_modify_user;       }
	
	public void setBoard_no(String board_no)                               { this.board_no = board_no;                               }
	public void setBoard_name(String board_name)                           { this.board_name = board_name;                           }
	public void setBoard_use_notice(String board_use_notice)               { this.board_use_notice = board_use_notice;               }
	public void setBoard_use_secret(String board_use_secret)               { this.board_use_secret = board_use_secret;               }
	public void setBoard_use_reply(String board_use_reply)                 { this.board_use_reply = board_use_reply;                 }
	public void setBoard_use_attachment(String board_use_attachment)       { this.board_use_attachment = board_use_attachment;       }
	public void setBoard_user_permission(String board_user_permission)     { this.board_user_permission = board_user_permission;     }
	public void setBoard_is_employee(String board_is_employee)             { this.board_is_employee = board_is_employee;             }
	public void setBoard_use_star(String board_use_star)                   { this.board_use_star = board_use_star;                   }
	public void setBoard_registration_date(String board_registration_date) { this.board_registration_date = board_registration_date; }
	public void setBoard_registration_ip(String board_registration_ip)     { this.board_registration_ip = board_registration_ip;     }
	public void setBoard_registration_user(String board_registration_user) { this.board_registration_user = board_registration_user; }
	public void setBoard_modify_date(String board_modify_date)             { this.board_modify_date = board_modify_date;             }
	public void setBoard_modify_ip(String board_modify_ip)                 { this.board_modify_ip = board_modify_ip;                 }
	public void setBoard_modify_user(String board_modify_user)             { this.board_modify_user = board_modify_user;             }
	
}
