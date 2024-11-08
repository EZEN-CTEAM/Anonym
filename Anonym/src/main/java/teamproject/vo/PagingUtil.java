package teamproject.vo;

public class PagingUtil 
{
	// * : 외부에서 받아올 연산에 필요한 데이터를 담을 필드
	// * 없음 : * 데이터들을 활용해서 연산된 결과를 담을 필드
	private int nowPage;   // 현재 페이지 번호(*)
	private int startPage; // 시작 페이지 번호
	private int endPage;   // 종료 페이지 번호
	private int total;     // 전체 게시글 수(*)
	private int perPage;   // 한 페이지당 게시글 개수(*)
	private int lastPage;  // 최종 페이지 번호
	private int start;     // 시작 게시글 번호
	private int end;       // 종료 게시글 번호
	private int cntPage = 5;  // 한 페이지에서 보여지는 페이징 번호 개수(*)
	
	public PagingUtil(){}
	public PagingUtil(int nowPage, int total, int perPage)
	{
		setNowPage(nowPage);
		setTotal(total);
		setPerPage(perPage);
		
		calcStartEnd(nowPage, perPage); // 시작번호 종료번호 연산 기능 호출
		calcLastEnd(total, perPage); // 최종 페이지 번호 연산 기능 호출
		calcStartEndPage(nowPage, cntPage); 
	}
	
	// 시작, 종료 게시글 번호 구하기
	public void calcStartEnd(int nowPage, int perPage)
	{
		int end = nowPage * perPage; // 게시글 종료번호(oracle에서 사용)
		/*
		  현재 페이지 : 1 / 게시글 노출 개수 : 5
		  종료번호 : 1 * 5 = 5
		  시작번호 : 종료번호 - 게시글 노출 개수(5 - 5 = 0);
		*/
		int start = end - perPage; // 게시글 시작번호 (oracle에서 사용)
		
		setEnd(end);
		setStart(start);
		
	}
	
	// 마지막 페이지번호 구하기
	public void calcLastEnd(int total, int perPage)
	{
		// 전체 게시글에서 페이지당 게시글 수를 나눈 실수를 올림처리 한 값을 반환.
		int lastPage = (int)Math.ceil((double)total/perPage);
		
		setLastPage(lastPage);
	}
	
	// 시작, 종료 페이지 구하기
	// 현재 페이지 : 3 / 시작 페이지 번호 : 1 / 종료 페이지 번호 : 10
	// 현재 페이지 : 13 / 시작 페이지 번호 : 11 / 종료 페이지 번호 : 20
	public void calcStartEndPage(int nowPage, int cntPage) 
	{
		// 현재페이지의 10의 자리를 구해와 +1한 후 페이지당 노출 페이지개수 곱하기 
		endPage = (int)Math.ceil((double)nowPage / cntPage) * cntPage;
		
		startPage = endPage - cntPage + 1;

		if(endPage > lastPage)
		{
			endPage = lastPage;
		}
		
		setStartPage(startPage);
		setEndPage(endPage);
	}
	
	public int getNowPage()   { return nowPage;   }
	public int getStartPage() { return startPage; }
	public int getEndPage()   { return endPage;   }
	public int getTotal()     { return total;     }
	public int getPerPage()   { return perPage;   }
	public int getLastPage()  { return lastPage;  }
	public int getStart()     { return start;     }
	public int getEnd()       { return end;       }
	public int getCntPage()   { return cntPage;   }
	
	public void setNowPage(int nowPage)     { this.nowPage = nowPage;     }
	public void setStartPage(int startPage) { this.startPage = startPage; }
	public void setEndPage(int endPage)     { this.endPage = endPage;     }
	public void setTotal(int total)         { this.total = total;         }
	public void setPerPage(int perPage)     { this.perPage = perPage;     }
	public void setLastPage(int lastPage)   { this.lastPage = lastPage;   }
	public void setStart(int start)         { this.start = start;         }
	public void setEnd(int end)             { this.end = end;             }
	public void setCntPage(int cntPage)     { this.cntPage = cntPage;     }
}
