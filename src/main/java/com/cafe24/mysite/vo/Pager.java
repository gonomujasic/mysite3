package com.cafe24.mysite.vo;

import org.apache.ibatis.type.Alias;

@Alias("pager")
public class Pager {

	private Integer totalNo;
	private Integer page = 1;
	private final Integer ARTICLE_COUNT_PER_PAGE = 10;

	private final Integer PAGE_PER_ORDER = 5;
	private Integer maxPage;
	private Integer startPage;
	private Integer endPage;
	
	private Integer pageNo;
	
	private String keyword;

	public void setConfig() {
		maxPage = totalNo / ARTICLE_COUNT_PER_PAGE + 1;
		startPage = (page - 1) / PAGE_PER_ORDER * PAGE_PER_ORDER + 1;
		endPage = startPage + PAGE_PER_ORDER - 1;
	}

	public Integer getMaxPage() {
		return maxPage;
	}

	public Integer getStartPage() {
		return startPage;
	}

	public Integer getEndPage() {
		return endPage;
	}

	public Integer getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(Integer totalNo) {
		this.totalNo = totalNo;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getARTICLE_COUNT_PER_PAGE() {
		return ARTICLE_COUNT_PER_PAGE;
	}

	public Integer getPAGE_PER_ORDER() {
		return PAGE_PER_ORDER;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/*
	 * 디비에서 필요한 정보 : 페이지 번호, 게시글 수
	 * 
	 * 들어오는 정보: 1. 페이지 버튼 누른 경우: 해당 페이지 select * from board where .. order by no
	 * desc limit ?+1,? 찾은 후 startPage, endPage, page를 뷰로 보내야 함. 2. 이전 버튼 누른 경우: 현재
	 * 페이지
	 * 
	 * 
	 */

}
